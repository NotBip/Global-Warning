package Objects.Weapons;

import GameStates.Playing;

import static Utilities.Constants.GAME_WIDTH;
import static Utilities.Constants.GAME_HEIGHT;
import static Utilities.Atlas.BOMB_ATLAS;
import static Utilities.Atlas.BULLETS_ATLAS;
import static Utilities.Atlas.getSpriteAtlas;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JOptionPane;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;

public class Bullets extends Entities.Entity implements MouseListener {

    // variables
    BufferedImage img, bulletBlue, bulletPurple;
    private BufferedImage[][] animations;
    private  int animationTick, animationIndex, aniSpeed = 15;
    private double x, y, vertX, vertY, initX, initY, targetX, targetY;
    public static double speed1,speed2,speed3;
    private double directionX, directionY;
    private Weapon1 weapon;
    private Playing playing;
    private int[][] lvlData;
    private double time;
    private boolean shoot = false; 
    private double theta = 0; 
    private boolean explode = false;
    private int xFlipped = 0; 
    private int wFlipped = 1;
    private int explodePosX = 0; 
    private int explodePosY = 0; 

    

    //upgradeable abilities (fire-rate in playing class)
    //no concept of damage yet, but public static double upgradeDamage;
    

    /**
     * Constructor to create bullets
     * 
     * @author Nusayba Hamou
     * @since December 19, 2023
     */

    public Bullets(Weapon1 weapon, Playing playing, double startX, double startY, double targetX, double targetY, int xOffset, int[][] lvlData, double time) {
        super((float) startX - 5, (float) startY - 5, 22, 22);
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
        // System.out.println("X: " + x + " vertX: " + vertX);
        // System.out.println("Y: " + y + " vertY: " + vertY);
        this.lvlData = lvlData;
        this.time = time;

        // I increased the speed to compensate for the cooldown
        speed1 = 10.0;
        speed2 = 7.0;
        speed3 = 1.0;
        setDirection(targetX, targetY, xOffset);
        setTime();
        //System.out.println("X: " + (directionX * speed3) + " Y: " + (directionY * speed3));
        getImage(); 
        initialize();
        playing.getSoundLibrary().playSound("Shoot");

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
        else if (Playing.gunIndex == 3) {
            speed = speed3;
        }

        if (Playing.gunIndex < 3) {
        if(canMove((float) (hitbox.x + speed * directionX), (float) (hitbox.y + speed * directionY), hitbox.width, hitbox.height, lvlData)) {
            x += speed * directionX;
            y += speed * directionY;
            hitbox.x += speed * directionX;
            hitbox.y += speed * directionY;
        } else {
            playing.removeBullet();
        }
    }
    // else if (Playing.gunIndex == 3) {
    //     if(canMove((float) (hitbox.x + speed * directionX), (float) (hitbox.y - speed * directionY), hitbox.width, hitbox.height, lvlData)) {
    //         //System.out.println("Time: " + time + ", directionX: " + directionX + ", directionY: " + directionY);
    //         // Change x of bomb
    //         tempChange = speed * ((vertX / initX) * 10);
    //         //System.out.print("    OldX: " + x + ", XDel: " + tempChange );
    //         x += tempChange;
    //         hitbox.x += tempChange;
    //         //System.out.print(", NewX: " + x + "; OldY: " + y );

    //         tempChange = speed * (0.5 * 9.8 * Math.pow((this.time + (vertY / initY)), 2));
    //         if ((time + (vertY / initY)) > 0){
    //         y += tempChange;
    //         hitbox.y += tempChange;
    //         }
    //         else {
    //             y -= tempChange;
    //             hitbox.y -= tempChange; 
    //         }
    //         //System.out.println(", YDel: " + tempChange + ", NewY: " + y);
    //         explodePosX = (int) hitbox.x; 
    //         explodePosY = (int) hitbox.y;
            
    //     } else {
    //         System.out.println("EXPLOSION ASD AT: " + hitbox.x + "y: " + hitbox.y);
    //         explode = true; 
    //         System.out.println("Initiate Explosion");
    //         playing.removeBullet();
    //     }
    // }
    // updateAnimationTick();    
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
        AffineTransform oldXForm = g2d.getTransform();
 
            // if(theta > Math.PI/2)
            //     theta; 

            if(theta < 0){ 
            hitbox.y = (float) y;
            hitbox.x = (float) (x);
            } else {  
            hitbox.y = (float) y - 25; 
            hitbox.x = (float) x - 25; 
            }
        
            if(!shoot){ 
                theta = playing.getAngle(); 

            if(weapon.xFlipped == 0) { 
                theta += Math.PI;  
            }

                
            if(!playing.bullets.isEmpty())
                shoot = true; 
            }
            g2d.rotate(theta , drawX - 5 , drawY - 5);

        if (!Playing.inventory){
            if (!Playing.paused){
                //adding ten sorta fixes the offset
                //x = hitbox.x;
            }
         }

        //System.out.println(drawX);

        if (Playing.gunIndex == 1){
            g2d.setColor(Color.PINK);
            // g2d.fillOval(drawX - 5 , drawY - 5, 64, 64);
            g.drawImage(bulletPurple, drawX - 5 , drawY - 5, 42, 42, null);


        } else if (Playing.gunIndex ==2 ){
            g2d.setColor(Color.BLUE);
            // g2d.fillOval(drawX - 5 , drawY - 5, 10, 10);
            g.drawImage(bulletBlue, drawX - 5 , drawY - 5, 42, 42, null);

        } 
        g2d.setTransform(oldXForm);

        
        // else if (Playing.gunIndex == 3){
        //     g.drawImage(this.img, (int) this.x+this.xFlipped - xOffset, (int) this.y, WEAPON_WIDTH*this.wFlipped / 2, WEAPON_HEIGHT / 2, null);
        // }

        // draw bullet hitbox
        // g2d.setColor(Color.WHITE);
        // drawHitbox(g, xOffset);

        // if there is at least one bullet in the list...
        if (playing.bullets.size() > 0) {

            // if it gets out of specified bounds...
            if (drawX >= weapon.x + 400-xOffset || drawX >= GAME_WIDTH || drawX <= 0 || drawX <= weapon.x-xOffset - 400 || drawY <= 0
                    || drawY >= GAME_HEIGHT) {

                // remove a bullet
                playing.removeBullet();
            }
        }

        // if (explode){ 
        //     System.out.println("EXPLOSION AT: " + explodePosX + "y: " + explodePosY);
        // }
       // g.drawImage(animations[9][animationIndex], (int) hitbox.x-xOffset , (int) hitbox.y, 64, 64, null);

    }

    public void getImage() {
        BufferedImage img = getSpriteAtlas(BULLETS_ATLAS); 
        bulletBlue = img.getSubimage(0, 0, 42, 56);
        bulletPurple = img.getSubimage(0, 56, 42, 56); 

        /*if (Playing.gunIndex == 1){
            this.img = getSpriteAtlas(WEAPON1_ATLAS); 
        } else if (Playing.gunIndex == 2){
            this.img = getSpriteAtlas(WEAPON2_ATLAS);
        } else */if (Playing.gunIndex == 3){
                this.img = getSpriteAtlas(BOMB_ATLAS);
        }
    }

    // private void loadImage() { 
    //     BufferedImage img = getSpriteAtlas(BOMBEXPLODE_ATLAS); 
    //     animations = new BufferedImage[10][15]; 
    //     for (int i = 0; i < animations.length; i++) {
    //         for (int j = 0; j < animations[i].length; j++) {
    //             animations[i][j] = img.getSubimage(j * 32, i * 32, 32, 32);
    //         }
    //     }
    // }

    // private void updateAnimationTick() {
    //     animationTick++;
    //     if (animationTick >= animationSpeed) {
    //         animationTick = 0;
    //         animationIndex++;
    //         if (animationIndex >= 6)
    //             animationIndex = 0;
    //     }
    // }
    


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
                this.time += 0.008;
            };
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
