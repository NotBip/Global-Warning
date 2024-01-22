/**
***********************************************
* @Author : Nusayba Hamou
* @Originally made : 18 JAN, 2024
* @Last Modified: 22 JAN, 2024
* @Description: Death state for when player diea in-game
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

import Objects.Saving.Checkpoint;

import static Utilities.Atlas.*;

public class Death {

	// variables
	private Playing playing;
	private BufferedImage backgroundImg = getSpriteAtlas(OVER_ATLAS);
	private InGameButton menuBackB, replayB;

	// constructor
	public Death(Playing playing) {
		this.playing = playing;
		makeButton();

	}

	/**
	 * @Method Name: makeButton
	 * @author Nusayba Hamou
	 * @since 18 JAN 2024
	 * @Description: makes button for the state
	 * @Parameters: N/A
	 * @returns:N/A
	 * @Dependencies: InGameButton
	 * @Throws/Exceptions: N/A
	 **/

	public void makeButton() {
		menuBackB = new InGameButton(GAME_WIDTH / 2 - 50, GAME_HEIGHT / 2, PAUSE_B_WIDTH, PAUSE_B_HEIGHT, 2,
				GameState.MENU);
		replayB = new InGameButton(GAME_WIDTH / 2 + 50, GAME_HEIGHT / 2, PAUSE_B_WIDTH, PAUSE_B_HEIGHT, 1,
				GameState.PLAYING);

	}

	/**
	 * @Method Name: update
	 * @author Nusayba Hamou
	 * @since 18 JAN 2024
	 * @Description: updates all buttons
	 * @Parameters: N/A
	 * @returns:N/A
	 * @Dependencies: Button
	 * @Throws/Exceptions: N/A
	 **/

	public void update() {
		menuBackB.update();
		replayB.update();
	}

	/**
	 * @Method Name: draw
	 * @author Nusayba Hamou
	 * @since 18 JAN 2024
	 * @Description: draws death overlay screen with buttons
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

		g.drawImage(backgroundImg, 500, 150, 300, 300, null);
		menuBackB.draw(g);
		replayB.draw(g);
	}

	/**
	 * @Method Name: resetButtons
	 * @author Nusayba Hamou
	 * @since 18 JAN 2024
	 * @Description: resets all buttons
	 * @Parameters: N/A
	 * @returns:N/A
	 * @Dependencies: Button
	 * @Throws/Exceptions: N/A
	 **/

	private void resetButtons() {
		menuBackB.resetButtons();
		replayB.resetButtons();

	}

	/**
	 * @Method Name: isIn
	 * @author Nusayba Hamou
	 * @since 18 JAN 2024
	 * @Description: checks if mouse is in button bounds
	 * @Parameters: MouseEvent e, InGameButton b
	 * @returns: true or false based on bounds
	 * @Dependencies: Button
	 * @Throws/Exceptions: N/A
	 **/

	public static boolean isIn(MouseEvent e, InGameButton b) {
		return b.getBounds().contains(e.getX(), e.getY());
	}

	/**
	 * @Method Name: mouseMoved
	 * @author Nusayba Hamou
	 * @since 18 JAN 2024
	 * @Description:Checks if mouse position overlaps bounds (cursor on button)
	 * @Parameters: MouseEvent e
	 * @returns: N/A
	 * @Dependencies: Button
	 * @Throws/Exceptions: N/A
	 **/

	public void mouseMoved(MouseEvent e) {
		menuBackB.setMouseOver(false);
		replayB.setMouseOver(false);

		if (isIn(e, menuBackB)) {
			menuBackB.setMouseOver(true);
		}

		if (isIn(e, replayB)) {
			replayB.setMouseOver(true);
		}
	}

	public void mouseClicked(MouseEvent e) {

	}

	public void mouseDragged(MouseEvent e) {

	}

	/**
	 * @Method Name: mousePressed
	 * @author Nusayba Hamou
	 * @since 18 JAN 2024
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
		if (isIn(e, replayB)) {
			replayB.setMousePressed(true);

		}
	}

	/**
	 * @Method Name: mouseReleased
	 * @author Nusayba Hamou
	 * @since 18 JAN 2024
	 * @Description:Checks if mouse is released from button bounds
	 * @Parameters: MouseEvent e
	 * @returns: N/A
	 * @Dependencies: Button, Playing, SaveState
	 * @Throws/Exceptions: N/A
	 **/

	public void mouseReleased(MouseEvent e) {
		if (isIn(e, menuBackB)) {
			if (menuBackB.getMousePressed())
				menuBackB.applyGamestate();
			Playing.paused = false;
			Playing.dead = false;
		}

		if (isIn(e, replayB)) {
			if (replayB.getMousePressed())

				switch (Checkpoint.fileNum) {
					case 1:
						SaveState.buttons[0].loadSave(playing);
						break;
					case 2:
						SaveState.buttons[1].loadSave(playing);
						break;
					case 3:
						SaveState.buttons[2].loadSave(playing);
						break;
					default:
						break;
				}

			replayB.applyGamestate();
			Playing.paused = false;
			Playing.dead = false;
		}

		resetButtons();

	}

}
