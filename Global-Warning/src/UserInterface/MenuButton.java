/**
***********************************************
* @Author : Nusayba Hamou
* @Originally made : 1 JAN, 2024
* @Last Modified: 21 JAN, 2024
* @Description: Menubuttons for menu state (play,options and quit)
***********************************************
*/

package UserInterface;

import static Utilities.Atlas.getSpriteAtlas;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import GameStates.*;
import static Utilities.Atlas.MENUBUTTON_ATLAS;
import static Utilities.Constants.Buttons.*;

public class MenuButton extends Button {

	// variables
	private int xPos, yPos, index, rowIndex;
	private GameState state;
	private BufferedImage[] imgs;

	// constructor
	public MenuButton(int xPos, int yPos, int width, int height, int rowIndex, GameState state) {
		super(xPos, yPos, width, height);
		this.xPos = xPos;
		this.yPos = yPos;
		this.state = state;
		this.rowIndex = rowIndex;
		loadImgs();
		getBounds();
	}

	/**
	 * @Method Name: loadImgs
	 * @referenced: https://github.com/KaarinGaming/PlatformerTutorial
	 * @author KaarinGaming
	 * @author Nusayba Hamou
	 * @since 1 JAN 2024
	 * @Description: loads images for menu buttons
	 * @Parameters: N/A
	 * @returns:N/A
	 * @Dependencies: Atlas, Constants
	 * @Throws/Exceptions: N/A
	 **/

	private void loadImgs() {
		imgs = new BufferedImage[3];
		BufferedImage temp = getSpriteAtlas(MENUBUTTON_ATLAS);
		for (int i = 0; i < imgs.length; i++)
			imgs[i] = temp.getSubimage(i * B_WIDTH, rowIndex * B_HEIGHT, B_WIDTH, B_HEIGHT);
	}

	/**
	 * @Method Name: draw
	 * @author Nusayba Hamou
	 * @since 1 JAN 2024
	 * @Description: draws menu button
	 * @Parameters: Graphics g
	 * @returns:N/A
	 * @Dependencies: Constants
	 * @Throws/Exceptions: N/A
	 **/

	public void draw(Graphics g) {
		g.drawImage(imgs[index], xPos, yPos, B_WIDTH, B_HEIGHT, null);
	}

	/**
	 * @Method Name: update
	 * @referenced: https://github.com/KaarinGaming/PlatformerTutorial
	 * @author KaarinGaming
	 * @author Nusayba Hamou
	 * @since 1 JAN 2024
	 * @Description: updates menu buttons based on mouse position
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
	 * @referenced: https://github.com/KaarinGaming/PlatformerTutorial
	 * @author KaarinGaming
	 * @author Nusayba Hamou
	 * @since 1 JAN 2024
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
