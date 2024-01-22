/**
***********************************************
* @Author : Nusayba Hamou
* @Originally made : 5 JAN, 2024
* @Last Modified: 22 JAN, 2024
* @Description: Pause state for in-game pause overlay and pause buttons
***********************************************
*/

package GameStates;

import java.awt.Color;
import java.awt.Graphics;
import static Utilities.Constants.GAME_WIDTH;
import static Utilities.Constants.Buttons.PAUSE_B_HEIGHT;
import static Utilities.Constants.Buttons.PAUSE_B_WIDTH;
import static Utilities.Constants.GAME_HEIGHT;

import UserInterface.InGameButton;

import java.awt.event.*;
import java.awt.image.BufferedImage;

import static Utilities.Atlas.*;

public class Pause {

	// variables
	private BufferedImage backgroundImg = getSpriteAtlas(PAUSE_ATLAS);
	private InGameButton menuBackB;
	private Playing playing; 
	
	// constructor
	public Pause(Playing playing) {
		this.playing = playing;
		makeButton();
	}

	/**
	 * @Method Name: makeButton
	 * @author Nusayba Hamou
	 * @since 5 JAN 2024
	 * @Description: makes button for the state
	 * @Parameters: N/A
	 * @returns:N/A
	 * @Dependencies: InGameButton
	 * @Throws/Exceptions: N/A
	 **/

	public void makeButton() {
		menuBackB = new InGameButton(GAME_WIDTH / 2 - 10, GAME_HEIGHT / 2, PAUSE_B_WIDTH, PAUSE_B_HEIGHT, 2,
				GameState.MENU);

	}

	/**
	 * @Method Name: update
	 * @author Nusayba Hamou
	 * @since 5 JAN 2024
	 * @Description: updates buttons for the state
	 * @Parameters: N/A
	 * @returns:N/A
	 * @Dependencies: Button
	 * @Throws/Exceptions: N/A
	 **/

	public void update() {
		menuBackB.update();
	}

	 /**
     * @Method Name: draw
     * @author Nusayba Hamou
     * @since 5 JAN 2024
     * @Description: draws pause overlay and home button
     * @Parameters: Graphics g
     * @returns:N/A
     * @Dependencies: Constants, Button
     * @Throws/Exceptions: N/A
     **/

	public void draw(Graphics g) {
		// dark background
		g.setColor(new Color(0, 0, 0, 90));
		g.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);

		// color for other
		g.setColor(Color.BLACK);

		g.drawImage(backgroundImg, GAME_WIDTH / 2 - 80, GAME_HEIGHT / 3, 200, 200, null);
		menuBackB.draw(g);
	}

	/**
	 * @Method Name: resetButtons
	 * @author Nusayba Hamou
	 * @since 5 JAN 2024
	 * @Description: resets all buttons (or the one)
	 * @Parameters: N/A
	 * @returns: N/A
	 * @Dependencies: Button
	 * @Throws/Exceptions: N/A
	 **/

	private void resetButtons() {
		menuBackB.resetButtons();

	}

	/**
	 * @Method Name: isIn
	 * @author Nusayba Hamou
	 * @since 5 JAN 2024
	 * @Description: checks if mouse is in button bounds
	 * @Parameters: MouseEvent e, InGameButton b
	 * @returns: N/A
	 * @Dependencies: Button
	 * @Throws/Exceptions: N/A
	 **/

	public static boolean isIn(MouseEvent e, InGameButton b) {
		return b.getBounds().contains(e.getX(), e.getY());
	}

	/**
	 * @Method Name: mouseMoved
	 * @author Nusayba Hamou
	 * @since 5 JAN 2024
	 * @Description: Checks if mouse position overlaps bounds (cursor on button)
	 * @Parameters: MouseEvent e
	 * @returns: N/A
	 * @Dependencies: Button
	 * @Throws/Exceptions: N/A
	 **/

	public void mouseMoved(MouseEvent e) {
		menuBackB.setMouseOver(false);

		if (isIn(e, menuBackB)) {
			menuBackB.setMouseOver(true);
		}
	}

	public void mouseClicked(MouseEvent e) {

	}

	public void mouseDragged(MouseEvent e) {

	}

	/**
	 * @Method Name: mousePressed
	 * @author Nusayba Hamou
	 * @since 5 JAN 2024
	 * @Description: Checks if mouse is clicked on button (cursor clicks button)
	 * @Parameters: MouseEvent e
	 * @returns: N/A
	 * @Dependencies: Button
	 * @Throws/Exceptions: N/A
	 **/

	// @Override
	public void mousePressed(MouseEvent e) {
		if (isIn(e, menuBackB)) {
			menuBackB.setMousePressed(true);

		}
	}

	/**
	 * @Method Name: mouseReleased
	 * @author Nusayba Hamou
	 * @since 5 JAN 2024
	 * @Description:Checks if mouse is released from button bounds
	 * @Parameters: MouseEvent e
	 * @returns: N/A
	 * @Dependencies: Button
	 * @Throws/Exceptions: N/A
	 **/

	public void mouseReleased(MouseEvent e) {
		if (isIn(e, menuBackB)) {
			if (menuBackB.getMousePressed())
				menuBackB.applyGamestate();
			Playing.paused = false;
			Playing.dead = false;
		}

		resetButtons();

	}

}
