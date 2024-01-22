/**
***********************************************
* @Author : Nusayba Hamou
* @Originally made : 6 JAN, 2024
* @Last Modified: 22 JAN, 2024
* @Description: Option state for when options are opened from menu
***********************************************
*/

package GameStates;

import java.awt.Graphics;
import static Utilities.Constants.GAME_WIDTH;
import static Utilities.Constants.Buttons.SOUND_B_WIDTH;
import static Utilities.Constants.Buttons.PAUSE_B_HEIGHT;
import static Utilities.Constants.Buttons.PAUSE_B_WIDTH;
import static Utilities.Constants.Buttons.SOUND_B_HEIGHT;
import static Utilities.Constants.GAME_HEIGHT;

import Main.Game;
import UserInterface.InGameButton;
import UserInterface.SoundButton;

import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import static Utilities.Atlas.*;

public class OptionState extends State implements KeyListener, MouseListener {

    // variables
    private int offset = 70;
    private int height = GAME_HEIGHT;
    private int width = GAME_WIDTH;
    BufferedImage imgbackground = getSpriteAtlas(MENUBACKGROUND_ATLAS);
    BufferedImage imgtitle = getSpriteAtlas(MENUTITLE_ATLAS);
    private SoundButton[] buttons = new SoundButton[2];
    private InGameButton menuBack;

    // constructor
    public OptionState(Game game) {
        super(game);
        makeButtons();
    }

    /**
     * @Method Name: update
     * @author Nusayba Hamou
     * @since 6 JAN 2024
     * @Description: updates buttons for the state
     * @Parameters: N/A
     * @returns:N/A
     * @Dependencies: Button
     * @Throws/Exceptions: N/A
     **/

    public void update() {
        for (SoundButton sb : buttons)
            sb.update();
        menuBack.update();

    }

    /**
     * @Method Name: makeButtons
     * @author Nusayba Hamou
     * @since 6 JAN 2024
     * @Description: makes buttons for the state
     * @Parameters: N/A
     * @returns:N/A
     * @Dependencies: SoundButton, InGameButton
     * @Throws/Exceptions: N/A
     **/

    public void makeButtons() {
        buttons[0] = new SoundButton(GAME_WIDTH / 2 - offset, GAME_HEIGHT / 2 + offset - 140, SOUND_B_WIDTH,
                SOUND_B_HEIGHT, 0);
        buttons[1] = new SoundButton(GAME_WIDTH / 2 + offset, GAME_HEIGHT / 2 + offset - 140, SOUND_B_WIDTH,
                SOUND_B_HEIGHT, 1);
        menuBack = new InGameButton(GAME_WIDTH / 2 - 10, GAME_HEIGHT / 2 - offset, PAUSE_B_WIDTH, PAUSE_B_HEIGHT, 2,
                GameState.MENU);
    }

    /**
     * @Method Name: draw
     * @author Nusayba Hamou
     * @since 6 JAN 2024
     * @Description: draws options with buttons
     * @Parameters: Graphics g
     * @returns:N/A
     * @Dependencies: Constants, Button
     * @Throws/Exceptions: N/A
     **/

    public void draw(Graphics g) {

        g.drawImage(this.imgbackground, 0, 0, this.width, this.height, null);
        g.drawImage(this.imgtitle, GAME_WIDTH / 3 + 50, 0, 350, 200, null);

        for (SoundButton sb : buttons)
            sb.draw(g);
        menuBack.draw(g);

    }

    /**
     * @Method Name: resetButtons
     * @author Nusayba Hamou
     * @since 6 JAN 2024
     * @Description: resets all buttons
     * @Parameters: N/A
     * @returns: N/A
     * @Dependencies: Button
     * @Throws/Exceptions: N/A
     **/

    private void resetButtons() {
        for (SoundButton sb : buttons)
            sb.resetButtons();
        menuBack.resetButtons();
    }

    /**
     * @Method Name: isIn
     * @author Nusayba Hamou
     * @since 6 JAN 2024
     * @Description: checks if mouse is in button bounds
     * @Parameters: MouseEvent e, SoundButton b
     * @returns: N/A
     * @Dependencies: Button
     * @Throws/Exceptions: N/A
     **/

    private boolean isIn(MouseEvent e, SoundButton b) {
        return b.getBounds().contains(e.getX(), e.getY());
    }

    /* Mouse events */
    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    /**
     * @Method Name: mouseMoved
     * @author Nusayba Hamou
     * @since 6 JAN 2024
     * @Description: Checks if mouse position overlaps bounds (cursor on button)
     * @Parameters: MouseEvent e
     * @returns: N/A
     * @Dependencies: Button
     * @Throws/Exceptions: N/A
     **/

    public void mouseMoved(MouseEvent e) {
        for (SoundButton sb : buttons)
            sb.setMouseOver(false);
        menuBack.setMouseOver(false);

        for (SoundButton sb : buttons)
            if (isIn(e, sb)) {
                sb.setMouseOver(true);
                break;
            }
        if (Pause.isIn(e, menuBack)) {
            menuBack.setMouseOver(true);
        }
    }

    public void mouseClicked(MouseEvent e) {

    }

    public void mouseDragged(MouseEvent e) {

    }

    /**
     * @Method Name: mousePressed
     * @author Nusayba Hamou
     * @since 6 JAN 2024
     * @Description: Checks if mouse is clicked on button (cursor clicks button)
     * @Parameters: MouseEvent e
     * @returns: N/A
     * @Dependencies: Button, Constants
     * @Throws/Exceptions: N/A
     **/

    @Override
    public void mousePressed(MouseEvent e) {
        for (SoundButton sb : buttons) {
            if (isIn(e, sb)) {
                sb.setMousePressed(true);
                if (e.getX() > (GAME_WIDTH / 2)) {
                    playSound(2);
                } else {
                    playSound(1);
                }
            }
            if (Pause.isIn(e, menuBack)) {
                menuBack.setMousePressed(true);
                playSound(3);
            }

        }
    }

    public void playSound(int sound) {
        String filepath;
        if (sound == 1) {
            filepath = "Global-Warning/res/audio/on.wav";
        } else if (sound == 2) {
            filepath = "Global-Warning/res/audio/off.wav";
        } else {
            filepath = "Global-Warning/res/audio/button2.wav";
        }
        File musicPath = new File(filepath);
        try {
            AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath);
            Clip clip = AudioSystem.getClip();
            clip.open(audioInput);
            clip.start();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * @Method Name: mouseReleased
     * @author Nusayba Hamou
     * @since 6 JAN 2024
     * @Description:Checks if mouse is released from button bounds
     * @Parameters: MouseEvent e
     * @returns: N/A
     * @Dependencies: Button
     * @Throws/Exceptions: N/A
     **/
    @Override
    public void mouseReleased(MouseEvent e) {
        for (SoundButton sb : buttons) {
            if (isIn(e, sb)) {
                if (sb.getMousePressed())
                    break;
            }
        }

        if (Pause.isIn(e, menuBack)) {
            if (menuBack.getMousePressed()) {
                menuBack.applyGamestate();
            }

        }

        resetButtons();

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

}