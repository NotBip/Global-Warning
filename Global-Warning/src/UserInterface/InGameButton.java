/**
***********************************************
* @Author : Nusayba Hamou
* @Originally made : 5 JAN. 2024
* @Last Modified: 21 JAN, 2024
* @Description: In-game buttons for replaying, returning to home menu from game, etc.
***********************************************
*/

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

	// constructor
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
	 * @Method Name: loadImgs
	 * @author Nusayba Hamou
	 * @since 5 JAN 2024
	 * @Description: loads images for in game buttons
	 * @Parameters: N/A
	 * @returns:N/A
	 * @Dependencies: Atlas, Constants
	 * @Throws/Exceptions: N/A
	 **/

	private void loadImgs() {
		imgs = new BufferedImage[3];
		BufferedImage temp = getSpriteAtlas(GAMEBUTTONS_ATLAS);
		for (int i = 0; i < imgs.length; i++)
			imgs[i] = temp.getSubimage(i * PAUSE_B_WIDTH, rowIndex * PAUSE_B_HEIGHT, PAUSE_B_WIDTH, PAUSE_B_HEIGHT);
	}

	/**
	 * @Method Name: draw
	 * @author Nusayba Hamou
	 * @since 5 JAN 2024
	 * @Description: draws img for in-game buttons
	 * @Parameters: Graphics g
	 * @returns:N/A
	 * @Dependencies: Constants
	 * @Throws/Exceptions: N/A
	 **/

	public void draw(Graphics g) {
		g.drawImage(imgs[index], xPos, yPos, PAUSE_B_WIDTH, PAUSE_B_HEIGHT, null);
	}

	/**
	 * @Method Name: update
	 * @author Nusayba Hamou
	 * @since 5 JAN 2024
	 * @Description: updates button based on mouse position
	 * @Parameters: N/A
	 * @returns:N/A
	 * @Dependencies: Button
	 * @Throws/Exceptions: N/A
	 **/

	public void update() {
		index = 0;
		if (getMouseOver())
			index = 1;
		if (getMousePressed())
			index = 2;
	}

	/**
	 * @Method Name: applyGamestate
	 * @author Nusayba Hamou
	 * @since 5 JAN 2024
	 * @Description: applies the current state to the gamestate
	 * @Parameters: N/A
	 * @returns:N/A
	 * @Dependencies: GameState
	 * @Throws/Exceptions: N/A
	 **/

	public void applyGamestate() {
		GameState.currentState = state;
	}

}
