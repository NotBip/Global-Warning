package UserInterface;


import static Utilities.Atlas.getSpriteAtlas;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import GameStates.Playing;
import Objects.Weapons.Bullets;

import static Utilities.Atlas.INVENTORYSLOT_ATLAS;
import static Utilities.Atlas.*;
import static Utilities.Constants.*;
import static Utilities.Constants.Buttons.*;

public class InventorySlot extends Button {
    // variables
	private int xPos, yPos, index;
	public int item;
	public boolean select = false;
	private BufferedImage[] imgs;
	private BufferedImage[] hvr;

	/**
	 * Constructor to create button for inventory slot
	 * 
	 * @author Nusayba Hamou
	 * @since January 5, 2024
	 */

	public InventorySlot(int xPos, int yPos, int width, int height, int item) {
		super(xPos, yPos, width, height);

		this.xPos = xPos;
		this.yPos = yPos;
		this.item = item;

		
		loadImgs();
		loadHoverImgs();
		getBounds();
	}

	/**
	 * loads animations for slot
	 * 
	 * @author Nusayba Hamou
	 * @since January 5, 2024
	 */

	private void loadImgs() {
		imgs = new BufferedImage[2];
		BufferedImage temp = getSpriteAtlas(INVENTORYSLOT_ATLAS);
		for (int i = 0; i < imgs.length; i++)
			imgs[i] = temp.getSubimage(i * INVENTORY_B_WIDTH, INVENTORY_B_HEIGHT, INVENTORY_B_WIDTH, INVENTORY_B_HEIGHT);
		
	}

	private void loadHoverImgs() {
		hvr = new BufferedImage[5];
		BufferedImage temp = getSpriteAtlas(HOVER_ATLAS);
		for (int i = 0; i < 4; i++){
			hvr[i] = temp.getSubimage(i * HOVER_WIDTH, 0, HOVER_WIDTH, HOVER_HEIGHT);
		}
		hvr[4] = temp.getSubimage(0, HOVER_HEIGHT, HOVER_WIDTH, HOVER_HEIGHT);
	}



	/**
	 * Draws specific sprite for slot
	 * 
	 * @author Nusayba Hamou
	 * @since January 5, 2024
	 */

	public void draw(Graphics g) {
		//temporary number for amount of items
		int [] num = new int[4];
		num[0] = 3;

		//draw inventory slot
		g.drawImage(imgs[index], xPos, yPos, 80, 80, null);
		
		if (select ==true){
			g.drawImage(getSpriteAtlas(SELECTION_ATLAS), xPos, yPos, 80, 80, null);
		}
	

	switch(item) {
        case 1:
           g.drawImage(getSpriteAtlas(WEAPON1_ATLAS), xPos+15, yPos+15, 50, 50, null);
		   if (index == 1)	{
				g.drawImage(hvr[4], xPos-90, yPos-110, 130, 150, null);
				g.drawString("Speed: "+Bullets.speed1, xPos-80,yPos-40);
				g.drawString("Fire Rate: "+Playing.fireRateWeapon1, xPos-80,yPos-10);
				g.drawString("Damage: "+num[0], xPos-80,yPos+20);
			}
			
			
            break;
        case 2:
           g.drawImage(getSpriteAtlas(WEAPON2_ATLAS), xPos+15, yPos+15, 50, 50, null);
		   if (index == 1)	{
				g.drawImage(hvr[4], xPos+40, yPos-110, 130, 150, null);
				g.drawString("Speed: "+Bullets.speed2, xPos+55,yPos-40);
				g.drawString("Fire Rate: "+Playing.fireRateWeapon2, xPos+55,yPos-10);
				g.drawString("Damage: "+num[0], xPos+55,yPos+20);
			}

            break;
        case 3:
			g.drawImage(getSpriteAtlas(BOMB_ATLAS), xPos+15, yPos+15, 50, 50, null); 
			if (index == 1)	{
				g.drawImage(hvr[0], xPos-80, yPos-70, 100, 90, null);
				g.drawString("Amount: "+num[0], xPos-75,yPos-20);
			}
            break;
        case 4:
			g.drawImage(getSpriteAtlas(POTION_ATLAS), xPos-20, yPos-20, 120, 120, null); 
			if (index == 1)	{
				g.drawImage(hvr[1], xPos+50, yPos-70, 100, 90, null);
				g.drawString("Amount: "+num[0], xPos+55,yPos-20);
			}
            break;
        case 5:
			g.drawImage(getSpriteAtlas(KEY_ATLAS), xPos+15, yPos+15, 50, 50, null); 
			if (index == 1)	{
				g.drawImage(hvr[2], xPos-80, yPos-70, 100, 90, null);
				g.drawString("Amount: "+num[0], xPos-75,yPos-20);
			}
            break;
		case 6:
			g.drawImage(getSpriteAtlas(GEM_ATLAS), xPos+15, yPos+15, 50, 50, null); 
			if (index == 1)	{
				g.drawImage(hvr[3], xPos+50, yPos-70, 100, 90, null);
				g.drawString("Amount: "+num[0], xPos+55,yPos-20);
			}
            break;
		default:
			break;
       }

	   

	}


	/**
	 * Updates button sprite based on mouse postion
	 * 
	 * @author Nusayba Hamou
	 * @since January 5, 2024
	 */

	public void update() {
		index = 0;
		if (getMouseOver())
			index = 1;

	}

}