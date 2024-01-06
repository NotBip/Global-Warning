package UserInterface;


import static Utilities.Atlas.getSpriteAtlas;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import static Utilities.Atlas.INVENTORYSLOT_ATLAS;
import static Utilities.Constants.Buttons.*;

public class InventorySlot extends Button {
    // variables
	private int xPos, yPos, index;
	private BufferedImage[] imgs;

	/**
	 * Constructor to create button for inventory slot
	 * 
	 * @author Nusayba Hamou
	 * @since January 5, 2024
	 */

	public InventorySlot(int xPos, int yPos, int width, int height) {
		super(xPos, yPos, width, height);

		this.xPos = xPos;
		this.yPos = yPos;

		loadImgs();
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
			//sub image height for some reason won't go past 80
			imgs[i] = temp.getSubimage(i * INVENTORY_B_WIDTH, INVENTORY_B_HEIGHT, INVENTORY_B_WIDTH, INVENTORY_B_HEIGHT);
	}

	/**
	 * Draws specific sprite for slot
	 * 
	 * @author Nusayba Hamou
	 * @since January 5, 2024
	 */

	public void draw(Graphics g) {
		g.drawImage(imgs[index], xPos, yPos, 50, 50, null);
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