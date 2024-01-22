/**
***********************************************
* @Author : Nusayba Hamou
* @Originally made : 3 JAN, 2024
* @Last Modified: 22 JAN, 2024
* @Description: SaveState loads saves using save buttons
***********************************************
*/

package GameStates;

import java.awt.Graphics;
import static Utilities.Constants.GAME_WIDTH;
import static Utilities.Constants.Buttons.PAUSE_B_HEIGHT;
import static Utilities.Constants.Buttons.PAUSE_B_WIDTH;
import static Utilities.Constants.Buttons.SAVE_B_HEIGHT;
import static Utilities.Constants.Buttons.SAVE_B_WIDTH;
import static Utilities.Constants.Buttons.RESET_B_WIDTH;
import static Utilities.Constants.Buttons.RESET_B_HEIGHT;
import static Utilities.Constants.GAME_HEIGHT;
import Main.Game;
import Objects.Saving.Checkpoint;
import UserInterface.InGameButton;
import UserInterface.ResetSaveButton;
import UserInterface.SaveButton;

import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import GameStates.Playing;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import static Utilities.Atlas.*;

public class SaveState extends State implements KeyListener, MouseListener {

    // variables
    private int offset = 100;
    private int height = GAME_HEIGHT;
    private int width = GAME_WIDTH;
    Playing playing;
    BufferedImage imgbackground = getSpriteAtlas(MENUBACKGROUND_ATLAS);
    BufferedImage imgtitle = getSpriteAtlas(MENUTITLE_ATLAS);
    public static SaveButton[] buttons = new SaveButton[3];
    private ResetSaveButton[] resetbuttons = new ResetSaveButton[3];
    private InGameButton menuBack;
    public Game game;

    // constructor
    public SaveState(Game game) {
        super(game);
        this.game = game;
        this.playing = game.getPlaying();
        makeButtons();
    }

    /**
     * @Method Name: update
     * @author Nusayba Hamou
     * @since 3 JAN 2024
     * @Description: updates buttons for the state
     * @Parameters: N/A
     * @returns:N/A
     * @Dependencies: Button
     * @Throws/Exceptions: N/A
     **/

    public void update() {
        for (SaveButton sb : buttons)
            sb.update();
        menuBack.update();
        for (ResetSaveButton rb : resetbuttons)
            rb.update();

    }

    /**
     * @Method Name: makeButtons
     * @author Nusayba Hamou
     * @since 3 JAN 2024
     * @Description: makes button for the state
     * @Parameters: N/A
     * @returns:N/A
     * @Dependencies: SaveButton, ResetSaveButton, InGameButton
     * @Throws/Exceptions: N/A
     **/

    public void makeButtons() {
        buttons[0] = new SaveButton(GAME_WIDTH / 2 - offset, GAME_HEIGHT / 2 + offset - 160, SAVE_B_WIDTH,
                SAVE_B_HEIGHT, 0, GameState.PLAYING, "BinarySaveInfoSlot1", 1);
        buttons[1] = new SaveButton(GAME_WIDTH / 2 - offset, GAME_HEIGHT / 2 + offset - 80, SAVE_B_WIDTH, SAVE_B_HEIGHT,
                1, GameState.PLAYING, "BinarySaveInfoSlot2", 2);
        buttons[2] = new SaveButton(GAME_WIDTH / 2 - offset, GAME_HEIGHT / 2 + offset, SAVE_B_WIDTH, SAVE_B_HEIGHT, 2,
                GameState.PLAYING, "BinarySaveInfoSlot3", 3);
        menuBack = new InGameButton(GAME_WIDTH / 2 - 10, GAME_HEIGHT / 2 + 190, PAUSE_B_WIDTH, PAUSE_B_HEIGHT, 2,
                GameState.MENU);
        resetbuttons[0] = new ResetSaveButton(GAME_WIDTH / 2 + 120, GAME_HEIGHT / 2 - 40, RESET_B_WIDTH, RESET_B_HEIGHT,
                "BinarySaveInfoSlot1", 1);
        resetbuttons[1] = new ResetSaveButton(GAME_WIDTH / 2 + 120, GAME_HEIGHT / 2 + 35, RESET_B_WIDTH, RESET_B_HEIGHT,
                "BinarySaveInfoSlot2", 2);
        resetbuttons[2] = new ResetSaveButton(GAME_WIDTH / 2 + 120, GAME_HEIGHT / 2 + 110, RESET_B_WIDTH,
                RESET_B_HEIGHT, "BinarySaveInfoSlot3", 3);
    }

    /**
     * @Method Name: draw
     * @author Nusayba Hamou
     * @since 3 JAN 2024
     * @Description: draws save state with save buttons
     * @Parameters: Graphics g
     * @returns:N/A
     * @Dependencies: Constants, Button
     * @Throws/Exceptions: N/A
     **/

    public void draw(Graphics g) {

        g.drawImage(this.imgbackground, 0, 0, this.width, this.height, null);
        g.drawImage(this.imgtitle, GAME_WIDTH / 3 + 50, 0, 350, 200, null);
        for (SaveButton sb : buttons)
            sb.draw(g);
        menuBack.draw(g);
        for (ResetSaveButton rb : resetbuttons)
            rb.draw(g);
    }

    /**
     * @Method Name: resetButtons
     * @author Nusayba Hamou
     * @since 3 JAN 2024
     * @Description: resets all buttons
     * @Parameters: N/A
     * @returns: N/A
     * @Dependencies: Button
     * @Throws/Exceptions: N/A
     **/

    private void resetButtons() {
        for (SaveButton sb : buttons)
            sb.resetButtons();
        menuBack.resetButtons();
        for (ResetSaveButton rb : resetbuttons)
            rb.resetButtons();

    }

    /**
     * @Method Name: isIn
     * @author Nusayba Hamou
     * @since 3 JAN 2024
     * @Description: checks if mouse is in button bounds
     * @Parameters: MouseEvent e, SaveButton b
     * @returns: N/A
     * @Dependencies: Button
     * @Throws/Exceptions: N/A
     **/

    private boolean isIn(MouseEvent e, SaveButton b) {
        return b.getBounds().contains(e.getX(), e.getY());
    }

    /**
     * @Method Name: isIn
     * @author Nusayba Hamou
     * @since 19 JAN 2024
     * @Description: checks if mouse is in button bounds
     * @Parameters: MouseEvent e, ResetSaveButton b
     * @returns: N/A
     * @Dependencies: Button
     * @Throws/Exceptions: N/A
     **/

    public static boolean isIn(MouseEvent e, ResetSaveButton b) {
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
     * @since 3 JAN 2024
     * @Description: Checks if mouse position overlaps bounds (cursor on button)
     * @Parameters: MouseEvent e
     * @returns: N/A
     * @Dependencies: Button
     * @Throws/Exceptions: N/A
     **/

    public void mouseMoved(MouseEvent e) {
        for (SaveButton sb : buttons)
            sb.setMouseOver(false);
        menuBack.setMouseOver(false);
        for (ResetSaveButton rb : resetbuttons)
            rb.setMouseOver(false);

        for (SaveButton sb : buttons)
            if (isIn(e, sb)) {
                sb.setMouseOver(true);
                break;

            }
        if (Pause.isIn(e, menuBack)) {
            menuBack.setMouseOver(true);
        }

        for (ResetSaveButton rb : resetbuttons) {
            if (isIn(e, rb)) {
                rb.setMouseOver(true);
            }
        }
    }

    public void mouseClicked(MouseEvent e) {

    }

    public void mouseDragged(MouseEvent e) {

    }

    /**
     * @Method Name: mousePressed
     * @author Nusayba Hamou
     * @since 3 JAN 2024
     * @Description: Checks if mouse is clicked on button (cursor clicks button)
     * @Parameters: MouseEvent e
     * @returns: N/A
     * @Dependencies: Button
     * @Throws/Exceptions: N/A
     **/

    @Override
    public void mousePressed(MouseEvent e) {
        for (SaveButton sb : buttons) {
            if (isIn(e, sb)) {
                sb.setMousePressed(true);
                playing.getSoundLibrary().playSound("Begin");
            }

        }

        if (Pause.isIn(e, menuBack)) {
            menuBack.setMousePressed(true);
            playing.getSoundLibrary().playSound("Deselect");
        }

        for (ResetSaveButton rb : resetbuttons) {
            if (isIn(e, rb)) {
                rb.setMousePressed(true);
                playing.getSoundLibrary().playSound("Off");
            }
        }
    }

    /**
     * @Method Name: mouseReleased
     * @author Nusayba Hamou
     * @since 3 JAN 2024
     * @Description:Checks if mouse is released from button bounds
     * @Parameters: MouseEvent e
     * @returns: N/A
     * @Dependencies: Button
     * @Throws/Exceptions: IO Exception & try-catch
     **/

    @Override
    public void mouseReleased(MouseEvent e) {
        for (SaveButton sb : buttons) {
            if (isIn(e, sb)) {
                if (sb.getMousePressed())
                    try {
                        game.playing.resetAll();
                        sb.readSave(game.playing);
                        System.out.println("check");
                        Checkpoint.setNumName(sb.fileName, sb.fileNum);
                        sb.applyGamestate();
                    } catch (IOException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                break;
            }
        }

        if (Pause.isIn(e, menuBack)) {
            if (menuBack.getMousePressed()) {
                menuBack.applyGamestate();
            }

        }

        for (ResetSaveButton rb : resetbuttons) {
            if (isIn(e, rb)) {
                if (rb.getMousePressed()) {
                    try {
                        rb.resetSave();
                    } catch (IOException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                    System.out.print("reset!");

                }
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
