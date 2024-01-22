/**
***********************************************
* @Author : Nusayba Hamou
* @Originally made : 19 DEC, 2024
* @Last Modified: 21 JAN, 2024
* @Description: Bullets class for bullets shot from weapons
***********************************************
*/

package Objects.Weapons;

import GameStates.Playing;
//import javafx.event.ActionEvent;

import static Utilities.Constants.GAME_WIDTH;
import static Utilities.Constants.GAME_HEIGHT;
import static Utilities.Atlas.BOMB_ATLAS;
import static Utilities.Atlas.getSpriteAtlas;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ConcurrentModificationException;

public class Bullets extends Entities.Entity implements MouseListener {

    // variables
    BufferedImage img;
    private BufferedImage[][] animations;
    private int animationTick, animationIndex, aniSpeed = 15;
    private double x, y, vertX, vertY, initX, initY, targetX, targetY;
    public static double speed1, speed2, speed3;
    private double directionX, directionY;
    private Weapon1 weapon;
    private Playing playing;
    private int[][] lvlData;
    private double time;
    private boolean explode = false;
    private int xFlipped = 0;
    private int wFlipped = 1;
    private int explodePosX = 0;
    private int explodePosY = 0;

    // upgradeable abilities (fire-rate in playing class)
    // no concept of damage yet, but public static double upgradeDamage;

    // constructor

    public Bullets(Weapon1 weapon, Playing playing, double startX, double startY, double targetX, double targetY,
            int xOffset, int[][] lvlData, double time) {
        super((float) startX, (float) startY, 10, 10);
        this.weapon = weapon;
        this.playing = playing;
        this.x = startX;
        this.y = startY;
        this.initX = startX;
        this.initY = startY;
        this.vertX = targetX - startX + xOffset;
        this.vertY = targetY - startY;
        this.targetX = targetX + xOffset - startX;
        this.targetY = targetY - startY;
        System.out.println("X: " + x + " vertX: " + vertX);
        System.out.println("Y: " + y + " vertY: " + vertY);
        this.lvlData = lvlData;
        this.time = time;

        // I increased the speed to compensate for the cooldown
        speed1 = 10.0;
        speed2 = 7.0;
        speed3 = 1.0;
        setDirection(targetX, targetY, xOffset);
        setTime();
        // System.out.println("X: " + (directionX * speed3) + " Y: " + (directionY *
        // speed3));
        initialize();
        playSound();

    }

    public void playSound() {
        String filepath = "Global-Warning/res/audio/Gun.wav";
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
     * @Method Name: setDirection
     * @author Hamad Mohammed
     * @since 21 DEC 2023
     * @Description: sets direction for bullets relative to mouse
     * @Parameters: double targetX, double targetY, int xOffset (targets and offset
     *              for mouse position)
     * @returns:N/A
     * @Dependencies: N/A
     * @Throws/Exceptions: N/A
     **/

    public void setDirection(double targetX, double targetY, int xOffset) {
        // something goes wrong here
        double angle = Math.atan2(targetY - y, targetX - x + xOffset);

        this.directionX = Math.cos(angle);
        // System.out.println("DirectionX: " + directionX);
        this.directionY = Math.sin(angle);
    }

    /**
     * @Method Name: move
     * @author Hamad Mohammed
     * @since 25 DEC 2023
     * @Description: updates bullets position/moves towards mouse
     * @Parameters: N/A
     * @returns:N/A
     * @Dependencies: Playing, Entitiy
     * @Throws/Exceptions: N/A
     **/

    public void move() {

        double speed = 0;
        double tempChange = 0;

        // different guns have different speeds
        if (Playing.gunIndex == 1) {
            speed = speed1;
        } else if (Playing.gunIndex == 2) {
            speed = speed2;
        } else if (Playing.gunIndex == 3) {
            speed = speed3;
        }

        if (Playing.gunIndex < 3) {
            if (canMove((float) (hitbox.x + speed * directionX), (float) (hitbox.y + speed * directionY), hitbox.width,
                    hitbox.height, lvlData)) {
                x += speed * directionX;
                y += speed * directionY;
                hitbox.x += speed * directionX;
                hitbox.y += speed * directionY;
            } else {
                playing.removeBullet();
            }
        }

    }

    /**
     * @Method Name: draw
     * @author Hamad Mohammed
     * @since 19 DEC 2023
     * @Description: draws bullets
     * @Parameters: Graphics g, int xOffset for player position vs gun position
     * @returns:N/A
     * @Dependencies: Playing, Entity
     * @Throws/Exceptions: N/A
     **/

    public void draw(Graphics g, int xOffset) {

        // bullet spawns
        Graphics2D g2d = (Graphics2D) g;

        getImage();

        int drawX = (int) Math.round(x - xOffset);
        int drawY = (int) Math.round(y);

        hitbox.x = drawX - 5 + xOffset;
        hitbox.y = drawY - 5;

        if (!Playing.inventory) {
            if (!Playing.paused) {
                // adding ten sorta fixes the offset
                // x = hitbox.x;
            }
        }

        // System.out.println(drawX);

        if (Playing.gunIndex == 1) {
            g2d.setColor(Color.PINK);
            g2d.fillOval(drawX - 5, drawY - 5, 10, 10);
        } else if (Playing.gunIndex == 2) {
            g2d.setColor(Color.BLUE);
            g2d.fillOval(drawX - 5, drawY - 5, 10, 10);
        }
        // else if (Playing.gunIndex == 3){
        // g.drawImage(this.img, (int) this.x+this.xFlipped - xOffset, (int) this.y,
        // WEAPON_WIDTH*this.wFlipped / 2, WEAPON_HEIGHT / 2, null);
        // }

        // draw bullet hitbox
        g2d.setColor(Color.BLACK);
        drawHitbox(g, xOffset);

        // if there is at least one bullet in the list...
        if (playing.bullets.size() > 0) {

            // if it gets out of specified bounds...
            if (drawX >= weapon.x + 400 - xOffset || drawX >= GAME_WIDTH || drawX <= 0
                    || drawX <= weapon.x - xOffset - 400 || drawY <= 0
                    || drawY >= GAME_HEIGHT) {

                // remove a bullet
                playing.removeBullet();
            }
        }

        // if (explode){
        // System.out.println("EXPLOSION AT: " + explodePosX + "y: " + explodePosY);
        // }
        // g.drawImage(animations[9][animationIndex], (int) hitbox.x-xOffset , (int)
        // hitbox.y, 64, 64, null);

    }

    public void getImage() {
        /*
         * if (Playing.gunIndex == 1){
         * this.img = getSpriteAtlas(WEAPON1_ATLAS);
         * } else if (Playing.gunIndex == 2){
         * this.img = getSpriteAtlas(WEAPON2_ATLAS);
         * } else
         */if (Playing.gunIndex == 3) {
            this.img = getSpriteAtlas(BOMB_ATLAS);
        }
    }

    // private void loadImage() {
    // BufferedImage img = getSpriteAtlas(BOMBEXPLODE_ATLAS);
    // animations = new BufferedImage[10][15];
    // for (int i = 0; i < animations.length; i++) {
    // for (int j = 0; j < animations[i].length; j++) {
    // animations[i][j] = img.getSubimage(j * 32, i * 32, 32, 32);
    // }
    // }
    // }

    // private void updateAnimationTick() {
    // animationTick++;
    // if (animationTick >= animationSpeed) {
    // animationTick = 0;
    // animationIndex++;
    // if (animationIndex >= 6)
    // animationIndex = 0;
    // }
    // }

    /**
     * @Method Name: updateBullets
     * @author Hamad Mohammed
     * @since 19 DEC 2023
     * @Description: updates bullets for shooting animation
     * @Parameters: N/A
     * @returns:N/A
     * @Dependencies: N/A
     * @Throws/Exceptions: N/A
     **/
    public void updateBullets() {
        try {
            for (Bullets bullet : playing.bullets) {
                bullet.move();
                this.time += 0.008;
            }
            ;
        } catch (ConcurrentModificationException e) {

        }

    }

    /* Getters */

    public void setTime() {
        this.time = 0;
    }

    public double getTime() {
        return this.time;
    }

    public int getDrawX() {
        return (int) Math.round(x);
    }

    public int getDrawY() {
        return (int) Math.round(y);
    }

    /* Mouse events */

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

}
