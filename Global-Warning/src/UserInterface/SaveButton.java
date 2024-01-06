package UserInterface;

import GameStates.GameState;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import static Utilities.Atlas.*;
import static Utilities.Constants.Buttons.*;

public class SaveButton extends Button {

	// variables
	private int xPos, yPos, rowIndex, index;
	private GameState state;
	private BufferedImage[] imgs;

	/**
	 * Constructor to create button for save slots
	 * 
	 * @author Nusayba Hamou
	 * @since January 1, 2024
	 */

	public SaveButton(int xPos, int yPos, int width, int height, int rowIndex, GameState state) {
		super(xPos, yPos, width, height);

		this.xPos = xPos;
		this.yPos = yPos;
		this.rowIndex = rowIndex;
		this.state = state;

		loadImgs();
		getBounds();
	}

	/**
	 * loads animations for save slots
	 * 
	 * @author Nusayba Hamou
	 * @since January 4, 2024
	 */

	private void loadImgs() {
		imgs = new BufferedImage[2];
		BufferedImage temp = getSpriteAtlas(SAVEBUTTON_ATLAS);
		for (int i = 0; i < imgs.length; i++)
			imgs[i] = temp.getSubimage(i * SAVE_B_WIDTH, rowIndex * SAVE_B_HEIGHT, SAVE_B_WIDTH, SAVE_B_HEIGHT);
	}

	/**
	 * Draws specific sprite for save slot
	 * 
	 * @author Nusayba Hamou
	 * @since January 4, 2024
	 */

	public void draw(Graphics g) {
		g.drawImage(imgs[index], xPos, yPos, SAVE_B_WIDTH, SAVE_B_HEIGHT, null);
	}

	/**
	 * Updates button sprite based on mouse postion
	 * 
	 * @author Nusayba Hamou
	 * @since January 4, 2024
	 */

	public void update() {
		index = 0;
		if (getMouseOver())
			index = 1;
	}

	/**
	 * Applies gamestate if button is clicked
	 * 
	 * @author Nusayba Hamou
	 * @since January 3, 2024
	 */

	public void applyGamestate() {
		GameState.currentState = state;
	}

}
