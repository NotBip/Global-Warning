/**
***********************************************
* @Author : Nusayba Hamou
* @Originally made : 19 DEC, 2023
* @Last Modified: 21 JAN, 2024
* @Description: Weapon class for all weapons+bombs (called weapon1 early on)
***********************************************
*/

package Objects.Weapons;

import Entities.*;
import GameStates.Playing;
import UserInputs.MouseInputs;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import static Utilities.Atlas.*;
import java.awt.Graphics2D;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;
import static Utilities.Constants.*;
import static Utilities.Constants.Directions.RIGHT;

public class Weapon1 implements MouseMotionListener {

    // variables
    private Player player;
    BufferedImage img;
    Graphics2D g2d;
    MouseInputs mouse;
    Playing playing;

    private int xFlipped = 0;
    private int wFlipped = 1;

    protected float x = 0;
    protected float y = 0;

    // constructor
    public Weapon1(Player player, Playing playing) {
        this.player = player;
        this.playing = playing;
        getImage();
    }

    /**
     * @Method Name: setAngle
     * @author Hamad Mohammed
     * @since 21 DEC 2023
     * @Description: sets angle for weapon to aim towards mouse
     * @Parameters: double centerY, double mouseY, double centerX, double mouseX
     *              (mouse positions)
     * @returns:N/A
     * @Dependencies: N/A
     * @Throws/Exceptions: N/A
     **/

    public double setAngle(double centerY, double mouseY, double centerX, double mouseX) {
        return Math.atan2(centerY - mouseY, centerX - mouseX) - Math.PI / 2;
    }

    /**
     * @Method Name: draw
     * @author Nusayba Hamou
     * @since 19 DEC 2023
     * @Description: draws the gun
     * @Parameters: Graphics g, int xOffset for character offset from gun
     * @returns:N/A
     * @Dependencies: Playing
     * @Throws/Exceptions: N/A
     **/

    public void draw(Graphics g, int xOffset) {
        Graphics2D g2d = (Graphics2D) g;
        AffineTransform oldXForm = g2d.getTransform();
        g2d.rotate(playing.getAngle(), this.x + 30 - xOffset, this.y + 50);

        if (!Playing.paused && !Playing.inventory && !Playing.dead) {
            // System.out.println(playing.getAngle());
            if (Playing.gunIndex < 3) {
                if (playing.mouseX < this.x - xOffset) {
                    this.xFlipped = 0;
                    this.wFlipped = 1;
                } else {
                    this.xFlipped = 70;
                    this.wFlipped = -1;
                }
            } else {
                if (playing.getPlayer().getDirection() == RIGHT) {
                    this.xFlipped = 0;
                    this.wFlipped = 1;
                } else {
                    this.xFlipped = 70;
                    this.wFlipped = -1;
                }
            }
        }
        g.drawImage(this.img, (int) this.x + this.xFlipped - xOffset, (int) this.y + 20, WEAPON_WIDTH * this.wFlipped,
                WEAPON_HEIGHT, null);
        g2d.setTransform(oldXForm);

    }

    /**
     * @Method Name: update
     * @author Nusayba Hamou
     * @since 19 DEC 2023
     * @Description: updates gun position
     * @Parameters: N/A
     * @returns:N/A
     * @Dependencies: Player
     * @Throws/Exceptions: N/A
     **/

    public void update() {
        this.x = player.getHitbox().x;
        this.y = player.getHitbox().y - 20;
    }

    /**
     * @Method Name: getImage
     * @author Nusayba Hamou
     * @since 19 DEC 2023
     * @Description: gets the correct weapon image
     * @Parameters: N/A
     * @returns:N/A
     * @Dependencies: Playing
     * @Throws/Exceptions: N/A
     **/

    public void getImage() {
        if (Playing.gunIndex == 1) {
            this.img = getSpriteAtlas(WEAPON1_ATLAS);
        } else if (Playing.gunIndex == 2) {
            this.img = getSpriteAtlas(WEAPON2_ATLAS);
        } else if (Playing.gunIndex == 3) {
            if (playing.getPlayer().getItemQuantity(2) > 0) {
                this.img = getSpriteAtlas(BOMB_ATLAS);
            } else {
                this.img = getSpriteAtlas(NOTHING_ATLAS);
            }
        }
    }

    /* Getters */

    public float getX() {
        return this.x;
    }

    public float getY() {
        return this.y;
    }

    /* Mouse events */

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'mouseDragged'");
    }
}
