/**
***********************************************
* @Author : Nusayba Hamou
* @Originally made : 1 JAN, 2024
* @Last Modified: 22 JAN, 2024
* @Description: Inventory state for when player opens inventory
***********************************************
*/

package GameStates;

import java.awt.Graphics;
import static Utilities.Constants.GAME_WIDTH;
import static Utilities.Constants.Buttons.B_WIDTH;
import static Utilities.Constants.Buttons.B_HEIGHT;
import static Utilities.Constants.GAME_HEIGHT;
import Utilities.SoundLibrary;

import Main.Game;
import UserInterface.MenuButton;
import UserInterface.SaveButton;
import GameStates.Playing;

import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import static Utilities.Atlas.*;

public class Menu extends State implements KeyListener, MouseListener {

    // variables
    private int offset = 70;
    private int height = GAME_HEIGHT;
    private int width = GAME_WIDTH;
    Playing playing;
    // SoundLibrary soundlibrary;
    BufferedImage imgbackground = getSpriteAtlas(MENUBACKGROUND_ATLAS);
    BufferedImage teachControls = getSpriteAtlas(CONTROLS_ATLAS);
    BufferedImage imgtitle = getSpriteAtlas(MENUTITLE_ATLAS);
    private MenuButton[] buttons = new MenuButton[3];

    // constructor
    public Menu(Game game) {
        super(game);
        this.playing = game.getPlaying();
        makeButtons();
    }

    /**
     * @Method Name: update
     * @referenced: https://github.com/KaarinGaming/PlatformerTutorial
     * @author KaarinGaming
     * @author Nusayba Hamou
     * @since 1 JAN 2024
     * @Description: updates buttons for the state
     * @Parameters: N/A
     * @returns:N/A
     * @Dependencies: Button
     * @Throws/Exceptions: N/A
     **/

    public void update() {
        for (MenuButton mb : buttons)
            mb.update();

    }

    /**
     * @Method Name: makeButtons
     * @author Nusayba Hamou
     * @since 1 JAN 2024
     * @Description: makes buttons for the state
     * @Parameters: N/A
     * @returns:N/A
     * @Dependencies: MenuButton
     * @Throws/Exceptions: N/A
     **/

    public void makeButtons() {
        buttons[0] = new MenuButton(GAME_WIDTH / 2 - offset, GAME_HEIGHT / 2 + offset - 140, B_WIDTH, B_HEIGHT, 0,
                GameState.SAVE);
        buttons[1] = new MenuButton(GAME_WIDTH / 2 - offset, GAME_HEIGHT / 2 + offset - 70, B_WIDTH, B_HEIGHT, 1,
                GameState.OPTIONS);
        buttons[2] = new MenuButton(GAME_WIDTH / 2 - offset, GAME_HEIGHT / 2 + offset, B_WIDTH, B_HEIGHT, 2,
                GameState.QUIT);
    }

    /**
     * @Method Name: draw
     * @author Nusayba Hamou
     * @since 1 JAN 2024
     * @Description: draws menu overlay screen with buttons
     * @Parameters: Graphics g
     * @returns:N/A
     * @Dependencies: Constants, Button
     * @Throws/Exceptions: N/A
     **/

    public void draw(Graphics g) {

        g.drawImage(this.imgbackground, 0, 0, this.width, this.height, null);
        g.drawImage(this.imgtitle, GAME_WIDTH / 3 + 40, 0, 350, 200, null);
        for (MenuButton mb : buttons)
            mb.draw(g);

    }

    /**
     * @Method Name: resetButtons
     * @referenced: https://github.com/KaarinGaming/PlatformerTutorial
     * @author KaarinGaming
     * @author Nusayba Hamou
     * @since 1 JAN 2024
     * @Description: resets all buttons
     * @Parameters: N/A
     * @returns: N/A
     * @Dependencies: Button
     * @Throws/Exceptions: N/A
     **/

    private void resetButtons() {
        for (MenuButton mb : buttons)
            mb.resetButtons();

    }

    /**
     * @Method Name: isIn
     * @referenced: https://github.com/KaarinGaming/PlatformerTutorial
     * @author KaarinGaming
     * @author Nusayba Hamou
     * @since 1 JAN 2024
     * @Description: checks if mouse is in button bounds
     * @Parameters: MouseEvent e, MenuButton b
     * @returns: N/A
     * @Dependencies: Button
     * @Throws/Exceptions: N/A
     **/

    private boolean isIn(MouseEvent e, MenuButton b) {
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
     * @since 1 JAN 2024
     * @Description: Checks if mouse position overlaps bounds (cursor on button)
     * @Parameters: MouseEvent e
     * @returns: N/A
     * @Dependencies: Button
     * @Throws/Exceptions: N/A
     **/

    public void mouseMoved(MouseEvent e) {
        for (MenuButton mb : buttons)
            mb.setMouseOver(false);

        for (MenuButton mb : buttons)
            if (isIn(e, mb)) {
                mb.setMouseOver(true);
                break;

            }
    }

    public void mouseClicked(MouseEvent e) {

    }

    public void mouseDragged(MouseEvent e) {

    }

    /**
     * @Method Name: mousePressed
     * @author Nusayba Hamou
     * @since 1 JAN 2024
     * @Description: Checks if mouse is clicked on button (cursor clicks button)
     * @Parameters: MouseEvent e
     * @returns: N/A
     * @Dependencies: Button
     * @Throws/Exceptions: N/A
     **/

    @Override
    public void mousePressed(MouseEvent e) {
        for (MenuButton mb : buttons) {
            if (isIn(e, mb)) {
                mb.setMousePressed(true);
                playing.getSoundLibrary().playSound("Select");
            }

        }
    }

 
    // public void playSound() {
    //     String filepath = "Global-Warning/res/audio/button1.wav";
    //     File musicPath = new File(filepath);
    //     try {
    //         AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath);
    //         Clip clip = AudioSystem.getClip();
    //         clip.open(audioInput);
    //         clip.start();
    //     } catch (Exception e) {
    //         System.out.println(e);
    //     }
    // }

    /**
     * @Method Name: mouseReleased
     * @author Nusayba Hamou
     * @since 1 JAN 2024
     * @Description:Checks if mouse is released from button bounds
     * @Parameters: MouseEvent e
     * @returns: N/A
     * @Dependencies: Button
     * @Throws/Exceptions: N/A
     **/

    @Override
    public void mouseReleased(MouseEvent e) {
        for (MenuButton mb : buttons) {
            if (isIn(e, mb)) {
                if (mb.getMousePressed())
                    mb.applyGamestate();
                break;
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