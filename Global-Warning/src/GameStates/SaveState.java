package GameStates;

import java.awt.Graphics;
import static Utilities.Constants.GAME_WIDTH;
import static Utilities.Constants.Buttons.SAVE_B_HEIGHT;
import static Utilities.Constants.Buttons.SAVE_B_WIDTH;
import static Utilities.Constants.GAME_HEIGHT;
import Main.Game;
import UserInterface.SaveButton;

import java.awt.event.*;
import java.awt.image.BufferedImage;

import static Utilities.Atlas.*;

public class SaveState extends State implements KeyListener, MouseListener {

    // variables
    private int offset = 100;
    private int height = GAME_HEIGHT;
    private int width = GAME_WIDTH;
    BufferedImage imgbackground = getSpriteAtlas(MENUBACKGROUND_ATLAS);
    BufferedImage imgtitle = getSpriteAtlas(MENUTITLE_ATLAS);
    private SaveButton[] buttons = new SaveButton[3];

    /**
     * Constructor for save state
     * 
     * @author Nusayba Hamou
     * @since January 3, 2024
     */

    public SaveState(Game game) {
        super(game);
        makeButtons();
    }

    /**
     * Updates save slots/buttons in save state
     * 
     * @author Nusayba Hamou
     * @since January 3, 2024
     */

    public void update() {
        for (SaveButton sb : buttons)
            sb.update();

    }

    /**
     * Add save slots to save state
     * 
     * @author Nusayba Hamou
     * @since January 3, 2024
     */

    public void makeButtons() {
        buttons[0] = new SaveButton(GAME_WIDTH / 2 - offset, GAME_HEIGHT / 2 + offset - 160, SAVE_B_WIDTH,
                SAVE_B_HEIGHT, 0, GameState.PLAYING);
        buttons[1] = new SaveButton(GAME_WIDTH / 2 - offset, GAME_HEIGHT / 2 + offset - 80, SAVE_B_WIDTH, SAVE_B_HEIGHT,
                1, GameState.PLAYING);
        buttons[2] = new SaveButton(GAME_WIDTH / 2 - offset, GAME_HEIGHT / 2 + offset, SAVE_B_WIDTH, SAVE_B_HEIGHT, 2,
                GameState.PLAYING);
    }

    /**
     * Draws save background and save buttons
     * 
     * @author Nusayba Hamou
     * @since January 3, 2024
     */

    public void draw(Graphics g) {

        g.drawImage(this.imgbackground, 0, 0, this.width, this.height, null);
        g.drawImage(this.imgtitle, GAME_WIDTH / 3, 0, 300, 150, null);
        for (SaveButton sb : buttons)
            sb.draw(g);

    }

    /**
     * Resets all save buttons
     * 
     * @author Nusayba Hamou
     * @since January 3, 2024
     */

    private void resetButtons() {
        for (SaveButton sb : buttons)
            sb.resetButtons();

    }

    /**
     * Checks if cursor X and Y position overlap button bounds
     * 
     * @author Nusayba Hamou
     * @since January 3, 2024
     */

    private boolean isIn(MouseEvent e, SaveButton b) {
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
     * @since January 3, 2024
     */

    public void mouseMoved(MouseEvent e) {
        for (SaveButton sb : buttons)
            sb.setMouseOver(false);

        for (SaveButton sb : buttons)
            if (isIn(e, sb)) {
                sb.setMouseOver(true);
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
     * @author Nusayba Hamou
     * @since January 3, 2024
     */
    @Override
    public void mousePressed(MouseEvent e) {
        for (SaveButton sb : buttons) {
            if (isIn(e, sb)) {
                sb.setMousePressed(true);
            }

        }
    }

    /**
     * Checks if mouse is released from button bounds
     * 
     * @author Nusayba Hamou
     * @since January 3, 2024
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        for (SaveButton sb : buttons) {
            if (isIn(e, sb)) {
                if (sb.getMousePressed())
                    sb.applyGamestate();
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
