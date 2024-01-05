package GameStates;


import java.awt.Graphics;
import static Utilities.Constants.GAME_WIDTH;
import static Utilities.Constants.GAME_HEIGHT;

import UserInterface.InGameButton;
import UserInterface.InventorySlot;
import UserInterface.MenuButton;

import java.awt.event.*;
import java.awt.image.BufferedImage;
import static Utilities.Constants.Buttons.*;

import static Utilities.Atlas.*;

public class InventoryState {
    // variables
	private Playing playing;
	private BufferedImage backgroundImg = getSpriteAtlas(INVENTORY_ATLAS);
	private InventorySlot[] slots= new InventorySlot[6];

	/**
	 * Constructor to create pause overlay
	 * 
	 * @author Nusayba Hamou
	 * @since January 5, 2024
	 */

	public InventoryState(Playing playing) {
		this.playing = playing;
		makeSlots();

	}

	/**
	 * Adds button to pause menu
	 * 
	 * @author Nusayba Hamou
	 * @since January 5, 2024
	 */

	public void makeSlots(){
		slots[0] = new InventorySlot(GAME_WIDTH / 2 +10, GAME_HEIGHT / 2, INVENTORY_B_WIDTH, INVENTORY_B_HEIGHT);
        slots[1] = new InventorySlot(GAME_WIDTH / 2 , GAME_HEIGHT / 2, INVENTORY_B_WIDTH, INVENTORY_B_HEIGHT);
        slots[2] = new InventorySlot(GAME_WIDTH / 2 , GAME_HEIGHT / 2, INVENTORY_B_WIDTH, INVENTORY_B_HEIGHT);
        slots[3] = new InventorySlot(GAME_WIDTH / 2 , GAME_HEIGHT / 2, INVENTORY_B_WIDTH, INVENTORY_B_HEIGHT);
        slots[4] = new InventorySlot(GAME_WIDTH / 2 , GAME_HEIGHT / 2, INVENTORY_B_WIDTH, INVENTORY_B_HEIGHT);
        slots[5] = new InventorySlot(GAME_WIDTH / 2 , GAME_HEIGHT / 2, INVENTORY_B_WIDTH, INVENTORY_B_HEIGHT);
		

	}

	/**
	 * Updates button in pause menu
	 * 
	 * @author Nusayba Hamou
	 * @since January 5, 2024
	 */

	public void update() {
        for (InventorySlot slot : slots)
            slot.update();
	}

	/**
	 * Draws pause menu and button
	 * 
	 * @author Nusayba Hamou
	 * @since January 5, 2024
	 */

	public void draw(Graphics g) {
		g.drawImage(backgroundImg, GAME_WIDTH / 2 -100, 50, 500, 400, null);
		 for (InventorySlot slot : slots)
            slot.draw(g);
	}

	/**
	 * Resets all buttons
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
            if (isIn(e, slot)) {
			    if (slot.getMousePressed())
                    System.out.print("Inventory slot clicked lool");
            }

		}

		resetButtons();

	}
}
