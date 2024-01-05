package GameStates;


import java.awt.Graphics;
import static Utilities.Constants.GAME_WIDTH;
import static Utilities.Constants.Buttons.B_WIDTH;
import static Utilities.Constants.Buttons.B_HEIGHT;
import static Utilities.Constants.GAME_HEIGHT;

import Main.Game;
import UserInterface.MenuButton;

import java.awt.event.*;
import java.awt.image.BufferedImage;

import static Utilities.Atlas.*;


public class Pause  {
    private Playing playing;
	private BufferedImage backgroundImg =   getSpriteAtlas(PAUSE_ATLAS);
	//private int bgX, bgY, bgW, bgH;
	//private UrmButton menuB, unpauseB;

	public Pause(Playing playing) {
		this.playing = playing;
		//loadBackground();
		//createUrmButtons();

	}
/* 
	private void createUrmButtons() {
		int menuX = (int) (313 * Game.SCALE);
		int unpauseX = (int) (462 * Game.SCALE);
		int bY = (int) (325 * Game.SCALE);

		menuB = new UrmButton(menuX, bY, URM_SIZE, URM_SIZE, 2);
		unpauseB = new UrmButton(unpauseX, bY, URM_SIZE, URM_SIZE, 0);

	}
   

	private void loadBackground() {
		backgroundImg = LoadSave.GetSpriteAtlas(LoadSave.PAUSE_BACKGROUND);
		bgW = (int) (backgroundImg.getWidth() * Game.SCALE);
		bgH = (int) (backgroundImg.getHeight() * Game.SCALE);
		bgX = Game.GAME_WIDTH / 2 - bgW / 2;
		bgY = (int) (25 * Game.SCALE);

	}

     */

	public void update() {

		//menuB.update();
		//unpauseB.update();

	}

	public void draw(Graphics g) {
		// Background
		g.drawImage(backgroundImg, GAME_WIDTH/2 -80, 100, 200,200, null);


		// UrmButtons
		//menuB.draw(g);
		//unpauseB.draw(g);
	}

	public void mouseDragged(MouseEvent e) {
	

	}
/* 
	public void mousePressed(MouseEvent e) {
		if (isIn(e, menuB))
			menuB.setMousePressed(true);
		else if (isIn(e, unpauseB))
			unpauseB.setMousePressed(true);
	}

	public void mouseReleased(MouseEvent e) {
		if (isIn(e, menuB)) {
			if (menuB.isMousePressed()) {
				Gamestate.state = Gamestate.MENU;
				playing.unpauseGame();
			}
		} else if (isIn(e, unpauseB)) {
			if (unpauseB.isMousePressed())
				playing.unpauseGame();
		}

		menuB.resetBools();
		unpauseB.resetBools();

	}

	public void mouseMoved(MouseEvent e) {
		menuB.setMouseOver(false);
		unpauseB.setMouseOver(false);

		if (isIn(e, menuB))
			menuB.setMouseOver(true);
		else if (isIn(e, unpauseB))
			unpauseB.setMouseOver(true);

	}

	private boolean isIn(MouseEvent e, PauseButton b) {
		return b.getBounds().contains(e.getX(), e.getY());
	}
*/

}