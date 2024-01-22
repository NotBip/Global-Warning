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
import GameStates.Playing;

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
    Playing playing;
    BufferedImage imgbackground = getSpriteAtlas(MENUBACKGROUND_ATLAS);
    BufferedImage imgtitle = getSpriteAtlas(MENUTITLE_ATLAS);
    private SoundButton[] buttons = new SoundButton[2];
    private InGameButton menuBack;

    /**
     * Constructor for options
     * 
     * @author Nusayba Hamou
     * @since January 6, 2024
     */

    public OptionState(Game game) {
        super(game);
        this.playing = game.getPlaying();
        makeButtons();
    }

    /**
     * Updates option buttons
     * 
     * @author Nusayba Hamou
     * @since January 6, 2024
     */

    public void update() {
        for (SoundButton sb : buttons)
            sb.update();
        menuBack.update();

    }

    /**
     * Adds buttons to options
     * 
     * @author Nusayba Hamou
     * @since January 6, 2024
     */

    public void makeButtons() {
        buttons[0] = new SoundButton(GAME_WIDTH / 2 - offset, GAME_HEIGHT / 2 + offset - 140, SOUND_B_WIDTH,
                SOUND_B_HEIGHT, 0);
        buttons[1] = new SoundButton(GAME_WIDTH / 2 + offset, GAME_HEIGHT / 2 + offset - 140, SOUND_B_WIDTH,
                SOUND_B_HEIGHT, 1);
        menuBack = new InGameButton(GAME_WIDTH / 2 -10, GAME_HEIGHT / 2-offset, PAUSE_B_WIDTH, PAUSE_B_HEIGHT, 2,
                GameState.MENU);
    }

    /**
     * Draws menu background, menu title, and buttons
     * 
     * @author Nusayba Hamou
     * @since January 6, 2024
     */

    public void draw(Graphics g) {

        g.drawImage(this.imgbackground, 0, 0, this.width, this.height, null);
        g.drawImage(this.imgtitle, GAME_WIDTH / 3 +50, 0, 350, 200, null);

        for (SoundButton sb : buttons)
            sb.draw(g);
        menuBack.draw(g);

    }

    /**
     * Resets all buttons
     * 
     * @author Nusayba Hamou
     * @since January 6, 2024
     */
    private void resetButtons() {
        for (SoundButton sb : buttons)
            sb.resetButtons();
        menuBack.resetButtons();
    }

    /**
     * Checks if cursor X and Y position overlap button bounds
     * 
     * @author Nusayba Hamou
     * @since January 6, 2024
     */

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
     * Checks if mouse position overlaps bounds (cursor on button)
     * 
     * @author Nusayba Hamou
     * @since January 6, 2024
     */

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
     * Checks if mouse is clicked on button (cursor clicks button)
     * 
     * @author Nusayba Hamou
     * @since January 6, 2024
     */

    @Override
    public void mousePressed(MouseEvent e) {
        for (SoundButton sb : buttons) {
            if (isIn(e, sb)) {
                sb.setMousePressed(true);
                if (e.getX() > (GAME_WIDTH/2)){
                playing.getSoundLibrary().playSound("Off");
                }
                else {
                    playing.getSoundLibrary().playSound("On");
                }
            }
            if (Pause.isIn(e, menuBack)) {
                menuBack.setMousePressed(true);
                playing.getSoundLibrary().playSound("Deselect");
            }

        }
    }

    /**
     * Checks if mouse is released from button bounds
     * 
     * @author Nusayba Hamou
     * @since January 6, 2024
     */

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