package Objects.Weapons;

import GameStates.Playing;

import static Utilities.Constants.GAME_WIDTH;
import static Utilities.Atlas.*;
import static Utilities.Atlas.getSpriteAtlas;
import static Utilities.Constants.GAME_HEIGHT;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Bullets extends Entities.Entity implements MouseListener {

    // variables
    private double x, y, speed;
    private double directionX, directionY;
    private Weapon1 weapon;
    private Playing playing;

    /**
     * Constructor to create bullets
     * 
     * @author Nusayba Hamou
     * @since December 19, 2023
     */

    public Bullets(Weapon1 weapon, Playing playing, double startX, double startY, double targetX, double targetY) {
        super((float) startX, (float) startY, 10, 10);
        this.weapon = weapon;
        this.playing = playing;
        this.x = startX;
        this.y = startY;

        // I increased the speed to compensate for the cooldown
        this.speed = 10.0;
        setDirection(targetX, targetY);
        initialize();
    }

    /**
     * sets direction for the gun
     * 
     * @author Hamad Mohammed
     * @since December 21, 2023
     */

    public void setDirection(double targetX, double targetY) {
        double angle = Math.atan2(targetY - y, targetX - x);
        this.directionX = Math.cos(angle);
        this.directionY = Math.sin(angle);
    }

    /**
     * shoots bullets towards mouse
     * 
     * @author Hamad Mohammed
     * @since December 25, 2023
     */

    public void move() {
        x += speed * directionX;
        y += speed * directionY;
        hitbox.x += speed * directionX;
        hitbox.y += speed * directionX;
    }

    /**
     * draw bullets
     * 
     * @author Hamad Mohammed
     * @since December 19, 2023
     */

    public void draw(Graphics g) {

        // bullet spawns
        Graphics2D g2d = (Graphics2D) g;

        int drawX = (int) Math.round(x);
        int drawY = (int) Math.round(y);

        hitbox.x = drawX - 5;
        hitbox.y = drawY - 5;

        if (Playing.gunIndex == 1){
            g2d.setColor(Color.PINK);
            g2d.fillOval(drawX - 5, drawY - 5, 10, 10);
        } else if (Playing.gunIndex ==2 ){
            g2d.setColor(Color.BLUE);
            g2d.fillOval(drawX - 5, drawY - 5, 10, 10);
        } else {
            //g2d.setColor(Color.ORANGE);
           // g2d.fillOval(drawX - 5, drawY - 5, 10, 10);
        }


        // draw bullet hitbox
        drawHitbox(g);

        // if there is at least one bullet in the list...
        if (playing.bullets.size() > 0) {

            // if it gets out of specified bounds...
            if (drawX >= weapon.x + 400 || drawX >= GAME_WIDTH || drawX <= 0 || drawX <= weapon.x - 400 || drawY <= 0
                    || drawY >= GAME_HEIGHT) {

                // remove a bullet
                playing.removeBullet();
            }
        }

    }

    /**
     * update bullets for shooting animation
     * 
     * @author Hamad Mohammed
     * @since December 19, 2023
     */

    public void updateBullets() {
        for (Bullets bullet : playing.bullets) {
            bullet.move();
        }
    }

    /* Getters */

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
