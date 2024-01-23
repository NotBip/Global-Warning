package GameStates;


import java.awt.Color;
import java.awt.Graphics;
import static Utilities.Constants.GAME_WIDTH;
import static Utilities.Constants.HOVER_WIDTH;
import static Utilities.Constants.GAME_HEIGHT;
import Utilities.Atlas.*;
import Items.HealPotion;
import Items.UpgradeGem;

import UserInterface.InventorySlot;
import Items.HealPotion;
import Items.UpgradeGem;

import java.awt.event.*;
import java.awt.image.BufferedImage;
import static Utilities.Constants.Buttons.*;

import static Utilities.Atlas.*;

public class InventoryState {
    // variables
    private int position = 100;
	private Playing playing;
	private HealPotion heal;
	private UpgradeGem upgrade;

	private BufferedImage backgroundImg = getSpriteAtlas(INVENTORY_ATLAS);
	private BufferedImage [] loadImgs;
	private InventorySlot[] slots= new InventorySlot[6];

	/**
	 * Constructor to create inventory overlay
	 * 
	 * @author Nusayba Hamou
	 * @since January 5, 2024
	 */

	public InventoryState(Playing playing) {
		this.playing = playing;
		makeSlots();

	}

	/**
	 * Adds slots to inventory
	 * 
	 * @author Nusayba Hamou
	 * @since January 5, 2024
	 */

	public void makeSlots(){
		//first slot
		slots[0] = new InventorySlot(GAME_WIDTH / 2 , GAME_HEIGHT / 2-position, INVENTORY_B_WIDTH, INVENTORY_B_HEIGHT,1, playing); 
		//second slot
		slots[1] = new InventorySlot(GAME_WIDTH / 2 +position, GAME_HEIGHT / 2 -position , INVENTORY_B_WIDTH, INVENTORY_B_HEIGHT,2, playing);
		
		//3rd slot (under first slot)
       slots[2] = new InventorySlot(GAME_WIDTH / 2 , GAME_HEIGHT / 2 , INVENTORY_B_WIDTH, INVENTORY_B_HEIGHT,3, playing);
	   //4th slot
        slots[3] = new InventorySlot(GAME_WIDTH / 2 +position, GAME_HEIGHT / 2 , INVENTORY_B_WIDTH, INVENTORY_B_HEIGHT,4, playing);

		//5th slot (final row)
        slots[4] = new InventorySlot(GAME_WIDTH / 2 , GAME_HEIGHT / 2 +position, INVENTORY_B_WIDTH, INVENTORY_B_HEIGHT,5, playing);
		//6th slot
        slots[5] = new InventorySlot(GAME_WIDTH / 2 +position, GAME_HEIGHT / 2 + position, INVENTORY_B_WIDTH, INVENTORY_B_HEIGHT,6, playing);
		

	}

	private void loadLoadImgs() {
		loadImgs = new BufferedImage[2];
		BufferedImage temp = getSpriteAtlas(LOADING_ATLAS);
		for (int i = 0; i < loadImgs.length; i++){
			loadImgs[i] = temp.getSubimage(i * LOAD_WIDTH, 0, LOAD_WIDTH, LOAD_HEIGHT);
		}
	}

	/**
	 * Updates slots in inventory
	 * 
	 * @author Nusayba Hamou
	 * @since January 5, 2024
	 */

	public void update() {
        for (InventorySlot slot : slots)
            slot.update();
	}

	/**
	 * Draws inventory and slots
	 * 
	 * @author Nusayba Hamou
	 * @since January 5, 2024
	 */

	public void draw(Graphics g) {
		//dark background
		g.setColor(new Color(0,0,0,90));
		g.fillRect(0,0, GAME_WIDTH, GAME_HEIGHT);

		//color for other
		g.setColor(Color.BLACK);
		
		g.drawImage(backgroundImg, GAME_WIDTH / 2 -250, GAME_HEIGHT/5, 500, 420, null);
		 for (InventorySlot slot : slots)
            slot.draw(g);

	}

	/**
	 * Resets all slots
	 * 
	 * @author Nusayba Hamou
	 * @since January 5, 2024
	 */

	private void resetButtons() {
		 for (InventorySlot slot : slots)
            slot.resetButtons();

	}

	/**
	 * Checks if cursor X and Y position overlap button bounds
	 * 
	 * @author Nusayba Hamou
	 * @since January 5, 2024
	 */

	private boolean isIn(MouseEvent e, InventorySlot b) {
		return b.getBounds().contains(e.getX(), e.getY());
	}

	/**
	 * Checks if mouse position overlaps bounds (cursor on button)
	 * 
	 * @author Nusayba Hamou
	 * @since January 5, 2024
	 */

	public void mouseMoved(MouseEvent e) {
         for (InventorySlot slot : slots)
            slot.setMouseOver(false);
        
         for (InventorySlot slot : slots){
            if (isIn(e, slot)) {
			    slot.setMouseOver(true);
		    }
     }
	}

	public void mouseClicked(MouseEvent e) {
	
	}

	public void mouseDragged(MouseEvent e) {

	}

	/**
	 * Checks if mouse is clicked on button (cursor clicks button)
	 * 
	 * @author Nusayba Hamou
	 * @since January 5, 2024
	 */

	// @Override
	public void mousePressed(MouseEvent e) {
		for (InventorySlot slot : slots){
            if (isIn(e, slot)) {
			    slot.setMousePressed(true);
		    }
     }
	}

	/**
	 * Checks if mouse is released from button bounds
	 * 
	 * @author Nusayba Hamou
	 * @since January 5, 2024
	 */

	public void mouseReleased(MouseEvent e) {
		 
        for (InventorySlot slot : slots){
		slot.select = false;
            if (isIn(e, slot)) {
			    if (slot.getMousePressed() && !Playing.paused){
					if(slot.item < 4) {
					Playing.setGunIndex(slot.item);
					}
					else if (slot.item == 4) {
						playing.getPlayer().useItem(1);
					}
					else if (slot.item == 6) {
						playing.getPlayer().useItem(4);
					}
				}
					slot.select = true;
					
				}
            }
		resetButtons();

	}

	public void reset() {
		for (InventorySlot slot : slots){
			slot.select = false;
		
		}

	}
}
