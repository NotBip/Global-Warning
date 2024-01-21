package GameStates;

import java.awt.Graphics;
import static Utilities.Constants.GAME_WIDTH;
import static Utilities.Constants.Buttons.B_WIDTH;
import static Utilities.Constants.Buttons.B_HEIGHT;
import static Utilities.Constants.GAME_HEIGHT;

import Main.Game;
import UserInterface.MenuButton;
import UserInterface.SaveButton;

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
    BufferedImage imgbackground = getSpriteAtlas(MENUBACKGROUND_ATLAS);
    BufferedImage teachControls = getSpriteAtlas(CONTROLS_ATLAS);
    BufferedImage imgtitle = getSpriteAtlas(MENUTITLE_ATLAS);
    private MenuButton[] buttons = new MenuButton[3];

    /**
     * Constructor for menu
     * 
     * @referenced: Kaarin Gaming
     * @author Nusayba Hamou
     * @since January 1, 2024
     */

    public Menu(Game game) {
        super(game);
        makeButtons();
    }

    /**
     * Updates menu buttons in menu
     * 
     * @referenced: Kaarin Gaming
     * @author Nusayba Hamou
     * @since January 1, 2024
     */

    public void update() {
        for (MenuButton mb : buttons)
            mb.update();

    }

    /**
     * Adds menu buttons to menu
     * 
     * @author Nusayba Hamou
     * @since January 1, 2024
     */

    public void makeButtons() {
        buttons[0] = new MenuButton(GAME_WIDTH / 2 - offset, GAME_HEIGHT / 2 + offset - 140, B_WIDTH, B_HEIGHT, 0,
                GameState.SAVE);
        buttons[1] = new MenuButton(GAME_WIDTH / 2 - offset, GAME_HEIGHT / 2 + offset - 70, B_WIDTH, B_HEIGHT, 1,
                GameState.OPTIONS);
        buttons[2] = new MenuButton(GAME_WIDTH / 2 - offset, GAME_HEIGHT / 2 + offset, B_WIDTH, B_HEIGHT, 2,
                GameState.QUIT);
    }

    /**
     * Draws menu background, menu title, and menu buttons
     * 
     * @author Nusayba Hamou
     * @since January 1, 2024
     */

    public void draw(Graphics g) {

        g.drawImage(this.imgbackground, 0, 0, this.width, this.height, null);
        g.drawImage(this.imgtitle, GAME_WIDTH / 3 +50, 0, 350, 200, null);
        g.drawImage(this.teachControls, GAME_WIDTH-350, 40, 300, 400, null);
        for (MenuButton mb : buttons)
            mb.draw(g);

    }

    /**
     * Resets all menu buttons
     * 
     * @referenced: Kaarin Gaming
     * @author Nusayba Hamou
     * @since January 1, 2024
     */
    private void resetButtons() {
        for (MenuButton mb : buttons)
            mb.resetButtons();

    }

    /**
     * Checks if cursor X and Y position overlap button bounds
     * 
     * @referenced: Kaarin Gaming
     * @author Nusayba Hamou
     * @since January 1, 2024
     */

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
     * Checks if mouse position overlaps bounds (cursor on button)
     * 
     * @referenced: Kaarin Gaming
     * @author Nusayba Hamou
     * @since January 1, 2024
     */

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
     * Checks if mouse is clicked on button (cursor clicks button)
     * 
     * @referenced: Kaarin Gaming
     * @author Nusayba Hamou
     * @since January 1, 2024
     */

    @Override
    public void mousePressed(MouseEvent e) {
        for (MenuButton mb : buttons) {
            if (isIn(e, mb)) {
                mb.setMousePressed(true);
                playSound();
            }

        }
    }

    public void playSound() {
        String filepath = "Global-Warning/res/audio/button1.wav";
        File musicPath = new File(filepath);
        try {
        AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath);
        Clip clip = AudioSystem.getClip();
        clip.open(audioInput);
        clip.start();
        }
        catch(Exception e) {
            System.out.println(e);
        }
    }

    /**
     * Checks if mouse is released from button bounds
     * 
     * @referenced: Kaarin Gaming
     * @author Nusayba Hamou
     * @since January 1, 2024
     */

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