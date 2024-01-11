package UserInterface;

import static Utilities.Atlas.getSpriteAtlas;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import static Utilities.Atlas.SOUNDBUTTON_ATLAS;
import static Utilities.Constants.Buttons.*;

public class SoundButton extends Button {

	// variables
	private int xPos, yPos, index, rowIndex;
	private BufferedImage[] imgs;

	/**
	 * Constructor to create sound button
	 * 
	 * @author Nusayba Hamou
	 * @since January 6, 2024
	 */

	public SoundButton(int xPos, int yPos, int width, int height, int rowIndex) {
		super(xPos, yPos, width, height);

		this.xPos = xPos;
		this.yPos = yPos;
		this.rowIndex = rowIndex;

		loadImgs();
		getBounds();
	}

	/**
	 * loads animations for button
	 * 
	 * @author Nusayba Hamou
	 * @since January 5, 2024
	 */

	private void loadImgs() {
		imgs = new BufferedImage[3];
		BufferedImage temp = getSpriteAtlas(SOUNDBUTTON_ATLAS);
		for (int i = 0; i < imgs.length; i++)
			imgs[i] = temp.getSubimage(i * SOUND_B_WIDTH, rowIndex * SOUND_B_HEIGHT, SOUND_B_WIDTH, SOUND_B_HEIGHT);
	}

	/**
	 * Draws specific sprite for button
	 * 
	 * @author Nusayba Hamou
	 * @since January 5, 2024
	 */

	public void draw(Graphics g) {
		g.drawImage(imgs[index], xPos, yPos, SOUND_B_WIDTH, SOUND_B_HEIGHT, null);
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
		if (getMousePressed()) {
			index = 2;
		}
	}

}
