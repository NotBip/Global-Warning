/**
***********************************************
* @Author : Nusayba Hamou
* @Originally made : 22 JAN, 2024
* @Last Modified: 22 JAN, 2024
* @Description: To Be Continued state for the sequel to come~
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

public class ToBeContinued {

	// variables
	private BufferedImage backgroundImg = getSpriteAtlas(CONTINUE_ATLAS);
	private InGameButton menuBackB;
	private Playing playing; 
    int color =0;
	
	// constructor
	public ToBeContinued(Playing playing) {
		this.playing = playing;
		makeButton();
	}

	/**
	 * @Method Name: makeButton
	 * @author Nusayba Hamou
	 * @since 22 JAN 2024
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
	 * @since 22 JAN 2024
	 * @Description: updates button for the state
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
     * @since 22 JAN 2024
     * @Description: draws to be continued and home button
     * @Parameters: Graphics g
     * @returns:N/A
     * @Dependencies: Constants, Button
     * @Throws/Exceptions: N/A
     **/

	public void draw(Graphics g) {
		// dark background
		
        if (color<250){
            g.setColor(new Color(0, 0, 0, color));
            g.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);
            color+=2;
        } else{
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);
            g.drawImage(backgroundImg, -50, 400, 520, 400, null);
            menuBackB.draw(g);
        }

		
	}

    /**
     * @Method Name: resetColor
     * @author Nusayba Hamou
     * @since 22 JAN 2024
     * @Description: resets color for fading background
     * @Parameters: N/A
     * @returns:N/A
     * @Dependencies: N/A
     * @Throws/Exceptions: N/A
     **/

    public void resetColor (){
        color = 0;
    }

	/**
	 * @Method Name: resetButtons
	 * @author Nusayba Hamou
	 * @since 22 JAN 2024
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
	 * @Method Name: mouseMoved
	 * @author Nusayba Hamou
	 * @since 22 JAN 2024
	 * @Description: Checks if mouse position overlaps bounds (cursor on button)
	 * @Parameters: MouseEvent e
	 * @returns: N/A
	 * @Dependencies: Button, Pause
	 * @Throws/Exceptions: N/A
	 **/

	public void mouseMoved(MouseEvent e) {
		menuBackB.setMouseOver(false);

		if (Pause.isIn(e, menuBackB)) {
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
	 * @since 22 JAN 2024
	 * @Description: Checks if mouse is clicked on button (cursor clicks button)
	 * @Parameters: MouseEvent e
	 * @returns: N/A
	 * @Dependencies: Button, Pause
	 * @Throws/Exceptions: N/A
	 **/

	// @Override
	public void mousePressed(MouseEvent e) {
		if (Pause.isIn(e, menuBackB)) {
			menuBackB.setMousePressed(true);

		}
	}

	/**
	 * @Method Name: mouseReleased
	 * @author Nusayba Hamou
	 * @since 22 JAN 2024
	 * @Description:Checks if mouse is released from button bounds
	 * @Parameters: MouseEvent e
	 * @returns: N/A
	 * @Dependencies: Button, Pause
	 * @Throws/Exceptions: N/A
	 **/

	public void mouseReleased(MouseEvent e) {
		if (Pause.isIn(e, menuBackB)) {
			if (menuBackB.getMousePressed()){
				menuBackB.applyGamestate();
                resetColor();
            }
			Playing.paused = false;
			Playing.dead = false;
            Playing.endGame = false;
		}

		resetButtons();

	}

}
