package Objects.Weapons;

import GameStates.Playing;

import static Utilities.Constants.GAME_WIDTH;
import static Utilities.Constants.GAME_HEIGHT;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ConcurrentModificationException;

public class Bullets extends Entities.Entity implements MouseListener {

    // variables
    private double x, y;
    public static double speed1,speed2;
    private double directionX, directionY;
    private Weapon1 weapon;
    private Playing playing;
    private int[][] lvlData;

    //upgradeable abilities (fire-rate in playing class)
    //no concept of damage yet, but public static double upgradeDamage;
    

    /**
     * Constructor to create bullets
     * 
     * @author Nusayba Hamou
     * @since December 19, 2023
     */

    public Bullets(Weapon1 weapon, Playing playing, double startX, double startY, double targetX, double targetY, int xOffset, int[][] lvlData) {
        super((float) startX, (float) startY, 10, 10);
        this.weapon = weapon;
        this.playing = playing;
        this.x = startX;
        this.y = startY;
        this.lvlData = lvlData;

        // I increased the speed to compensate for the cooldown
        speed1 = 10.0;
        speed2 = 7.0;
        setDirection(targetX, targetY, xOffset);
        initialize();
    }

    /**
     * sets direction for the gun
     * 
     * @author Hamad Mohammed
     * @since December 21, 2023
     */

    public void setDirection(double targetX, double targetY, int xOffset) {
        //something goes wrong here
        double angle = Math.atan2(targetY - y, targetX - x + xOffset);

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

        double speed = 0;

        //different guns have different speeds
        if (Playing.gunIndex == 1){
            speed = speed1;
        } else if(Playing.gunIndex == 2){
            speed = speed2;
        }

        if(canMove((float) (hitbox.x + speed * directionX), (float) (hitbox.y + speed * directionY), hitbox.width, hitbox.height, lvlData)) {
            x += speed * directionX;
            y += speed * directionY;
            hitbox.x += speed * directionX;
            hitbox.y += speed * directionX;
        } else {
            playing.removeBullet();
        }
        
    }

    /**
     * draw bullets
     * 
     * @author Hamad Mohammed
     * @since December 19, 2023
     */

    public void draw(Graphics g, int xOffset) {

        // bullet spawns
        Graphics2D g2d = (Graphics2D) g;

        int drawX = (int) Math.round(x - xOffset);
        int drawY = (int) Math.round(y);

        hitbox.x = drawX - 5 + xOffset;
        hitbox.y = drawY - 5;
        if (!Playing.dead){
            if (!Playing.inventory){
                if (!Playing.paused){
                    //adding ten sorta fixes the offset
                    x = hitbox.x;
                }
            }
         }

        //System.out.println(drawX);

        if (Playing.gunIndex == 1){
            g2d.setColor(Color.PINK);
            g2d.fillOval(drawX - 5 , drawY - 5, 10, 10);
        } else if (Playing.gunIndex ==2 ){
            g2d.setColor(Color.BLUE);
            g2d.fillOval(drawX - 5 , drawY - 5, 10, 10);
        } else {
            //g2d.setColor(Color.ORANGE);
           // g2d.fillOval(drawX - 5, drawY - 5, 10, 10);
        }


        // draw bullet hitbox
        g2d.setColor(Color.BLACK);
        drawHitbox(g, xOffset);

        // if there is at least one bullet in the list...
        if (playing.bullets.size() > 0) {

            // if it gets out of specified bounds...
            if (drawX >= weapon.x + 400-xOffset || drawX >= GAME_WIDTH || drawX <= 0 || drawX <= weapon.x-xOffset - 400 || drawY <= 0
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
        try {
            for (Bullets bullet : playing.bullets) {
                bullet.move();
            };
        } catch (ConcurrentModificationException e) {

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
