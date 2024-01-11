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
	private Playing playing;
	private BufferedImage backgroundImg = getSpriteAtlas(PAUSE_ATLAS);
	private InGameButton menuBackB;

	/**
	 * Constructor to create pause overlay
	 * 
	 * @author Nusayba Hamou
	 * @since January 5, 2024
	 */

	public Pause(Playing playing) {
		this.playing = playing;
		makeButton();

	}

	/**
	 * Adds button to pause menu
	 * 
	 * @author Nusayba Hamou
	 * @since January 5, 2024
	 */

	public void makeButton() {
		menuBackB = new InGameButton(GAME_WIDTH / 2 - 10, GAME_HEIGHT / 2, PAUSE_B_WIDTH, PAUSE_B_HEIGHT, 2,
				GameState.MENU);

	}

	/**
	 * Updates button in pause menu
	 * 
	 * @author Nusayba Hamou
	 * @since January 5, 2024
	 */

	public void update() {
		menuBackB.update();
	}

	/**
	 * Draws pause menu and button
	 * 
	 * @author Nusayba Hamou
	 * @since January 5, 2024
	 */

	public void draw(Graphics g) {
		//dark background
		g.setColor(new Color(0,0,0,90));
		g.fillRect(0,0, GAME_WIDTH, GAME_HEIGHT);

		//color for other
		g.setColor(Color.BLACK);
		
		g.drawImage(backgroundImg, GAME_WIDTH / 2 - 80, GAME_HEIGHT/3, 200, 200, null);
		menuBackB.draw(g);
	}

	/**
	 * Resets all buttons
	 * 
	 * @author Nusayba Hamou
	 * @since January 5, 2024
	 */

	private void resetButtons() {
		menuBackB.resetButtons();

	}

	/**
	 * Checks if cursor X and Y position overlap button bounds
	 * 
	 * @author Nusayba Hamou
	 * @since January 5, 2024
	 */

	public static boolean isIn(MouseEvent e, InGameButton b) {
		return b.getBounds().contains(e.getX(), e.getY());
	}

	/**
	 * Checks if mouse position overlaps bounds (cursor on button)
	 * 
	 * @author Nusayba Hamou
	 * @since January 5, 2024
	 */

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
	 * Checks if mouse is clicked on button (cursor clicks button)
	 * 
	 * @author Nusayba Hamou
	 * @since January 5, 2024
	 */

	// @Override
	public void mousePressed(MouseEvent e) {
		if (isIn(e, menuBackB)) {
			menuBackB.setMousePressed(true);

		}
	}

	/**
	 * Checks if mouse is released from button bounds
	 * 
	 * @author Nusayba Hamou
	 * @since January 5, 2024
	 */

	public void mouseReleased(MouseEvent e) {
		if (isIn(e, menuBackB)) {
			if (menuBackB.getMousePressed())
				menuBackB.applyGamestate();
				Playing.paused = false;

		}

		resetButtons();

	}

}
