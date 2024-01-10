package UserInterface;

import GameStates.GameState;

import static Utilities.Atlas.getSpriteAtlas;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import static Utilities.Atlas.GAMEBUTTONS_ATLAS;
import static Utilities.Constants.Buttons.*;

public class InGameButton extends Button {

	// variables
	private int xPos, yPos, rowIndex, index;
	private GameState state;
	private BufferedImage[] imgs;

	/**
	 * Constructor to create button for pause menu
	 * 
	 * @author Nusayba Hamou
	 * @since January 5, 2024
	 */

	public InGameButton(int xPos, int yPos, int width, int height, int rowIndex, GameState state) {
		super(xPos, yPos, width, height);

		this.xPos = xPos;
		this.yPos = yPos;
		this.rowIndex = rowIndex;
		this.state = state;

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
		BufferedImage temp = getSpriteAtlas(GAMEBUTTONS_ATLAS);
		for (int i = 0; i < imgs.length; i++)
			imgs[i] = temp.getSubimage(i * PAUSE_B_WIDTH, rowIndex * PAUSE_B_HEIGHT, PAUSE_B_WIDTH, PAUSE_B_HEIGHT);
	}

	/**
	 * Draws specific sprite for button
	 * 
	 * @author Nusayba Hamou
	 * @since January 5, 2024
	 */

	public void draw(Graphics g) {
		g.drawImage(imgs[index], xPos, yPos, PAUSE_B_WIDTH, PAUSE_B_HEIGHT, null);
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
		if (getMousePressed())
			index = 2;
	}

	/**
	 * Applies gamestate if button is clicked
	 * 
	 * @author Nusayba Hamou
	 * @since January 5, 2024
	 */

	public void applyGamestate() {
		GameState.currentState = state;
	}

}
