package UserInterface;


import static Utilities.Atlas.getSpriteAtlas;

import java.awt.Graphics;
import java.awt.image.BufferedImage;


import static Utilities.Atlas.INVENTORYSLOT_ATLAS;
import static Utilities.Atlas.*;
import static Utilities.Constants.*;
import static Utilities.Constants.Buttons.*;

public class InventorySlot extends Button {
    // variables
	private int xPos, yPos, index, rowIndex;
	public int item;
	private BufferedImage[] imgs;
	private BufferedImage[] hvr;

	/**
	 * Constructor to create button for inventory slot
	 * 
	 * @author Nusayba Hamou
	 * @since January 5, 2024
	 */

	public InventorySlot(int xPos, int yPos, int width, int height,int rowIndex, int item) {
		super(xPos, yPos, width, height);

		this.xPos = xPos;
		this.yPos = yPos;
		this.item = item;
		this.rowIndex = rowIndex;

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
		hvr = new BufferedImage[4];
		BufferedImage temp = getSpriteAtlas(ITEM_HOVER_ATLAS);

		hvr[0] = temp.getSubimage(0*ITEM_HOVER_WIDTH, 0*ITEM_HOVER_HEIGHT, ITEM_HOVER_WIDTH, ITEM_HOVER_HEIGHT);
		hvr[1] = temp.getSubimage(ITEM_HOVER_WIDTH, 0*ITEM_HOVER_HEIGHT, ITEM_HOVER_WIDTH, ITEM_HOVER_HEIGHT);
		hvr[2] = temp.getSubimage(0*ITEM_HOVER_WIDTH, ITEM_HOVER_HEIGHT, ITEM_HOVER_WIDTH, ITEM_HOVER_HEIGHT);
		hvr[3] = temp.getSubimage(ITEM_HOVER_WIDTH, ITEM_HOVER_HEIGHT, ITEM_HOVER_WIDTH, ITEM_HOVER_HEIGHT);
		
	}



	/**
	 * Draws specific sprite for slot
	 * 
	 * @author Nusayba Hamou
	 * @since January 5, 2024
	 */

	public void draw(Graphics g) {
		//int num = 0;
		g.drawImage(imgs[index], xPos, yPos, 80, 80, null);

	switch(item) {
        case 1:
           g.drawImage(getSpriteAtlas(WEAPON1_ATLAS), xPos+15, yPos+15, 50, 50, null);
		   if (index == 1)	{
				g.drawImage(getSpriteAtlas(WEAPON_HOVER_ATLAS), xPos+40, yPos-110, 130, 150, null);
			}
            break;
        case 2:
           g.drawImage(getSpriteAtlas(WEAPON2_ATLAS), xPos+15, yPos+15, 50, 50, null);
		   if (index == 1)	{
				g.drawImage(getSpriteAtlas(WEAPON_HOVER_ATLAS), xPos+40, yPos-110, 130, 150, null);
			}

            break;
        case 3:
			g.drawImage(getSpriteAtlas(BOMB_ATLAS), xPos+15, yPos+15, 50, 50, null); 
			if (index == 1)	{
				g.drawImage(hvr[0], xPos+40, yPos-110, 130, 150, null);
			}
            break;
        case 4:
			g.drawImage(getSpriteAtlas(POTION_ATLAS), xPos-20, yPos-20, 120, 120, null); 
			if (index == 1)	
				g.drawImage(hvr[1], xPos+40, yPos-110, 130, 150, null);
            break;
        case 5:
			g.drawImage(getSpriteAtlas(KEY_ATLAS), xPos+15, yPos+15, 50, 50, null); 
			if (index == 1)	
				g.drawImage(hvr[2], xPos+40, yPos-110, 130, 150, null);
            break;
		case 6:
			g.drawImage(getSpriteAtlas(GEM_ATLAS), xPos+15, yPos+15, 50, 50, null); 
			if (index == 1)	
				g.drawImage(hvr[3], xPos+40, yPos-110, 130, 150, null);
				//g.drawString("Amount: "+num, 20, 20);
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