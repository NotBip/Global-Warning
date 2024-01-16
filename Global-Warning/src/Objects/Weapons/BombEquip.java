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
import static Utilities.Constants.Directions.LEFT;

public class BombEquip implements MouseMotionListener {

    //variables
    private Player player;
    BufferedImage img;
    Graphics2D g2d; 
    MouseInputs mouse;
    Playing playing;
    
    private int xFlipped = 0; 
    private int wFlipped = 1; 
    
    protected float x=0;
    protected float y=0;



    /**
     * Constructor to create weapon
     * @author Hamad Mohammed
     * @since December 19, 2023
     */
    
    public BombEquip (Player player, Playing playing) {
        this.player = player;
        this.playing = playing; 
        getImage();
  }

  /**
     * Sets angle for weapon to aim towards mouse
     * @author Hamad Mohammed
     * @since December 21, 2023
     */

    /*public double setAngle(double centerY, double mouseY, double centerX, double mouseX) { 
        return Math.atan2(centerY - mouseY, centerX - mouseX) - Math.PI / 2;
    }*/
  
    /**
     * Draws the gun
     * @author Nusayba Hamou
     * @since December 19, 2023
     */

    public void draw(Graphics g, int xOffset) {
        Graphics2D g2d = (Graphics2D)g; 
        AffineTransform oldXForm = g2d.getTransform();
        //g2d.rotate(playing.getAngle() , this.x+30-xOffset, this.y+50);

        if (!Playing.paused && !Playing.inventory){
           // System.out.println(playing.getAngle());
            if (playing.getPlayer().getDirection() == RIGHT){
                this.xFlipped = 0; 
                this. wFlipped = 1; 
            }
            else {
                this.xFlipped = 70;
                this.wFlipped = -1; 
            }
        }
        g.drawImage(this.img, (int) this.x+this.xFlipped - xOffset, (int) this.y+20, WEAPON_WIDTH*this.wFlipped, WEAPON_HEIGHT, null);
        g2d.setTransform(oldXForm);

     }

    /**
     * Updates gun position to follow player movement
     * @author Nusayba Hamou
     * @since December 19, 2023
     */

    public void update() {
        this.x = player.getHitbox().x;
        this.y = player.getHitbox().y-20;
     }

       /**
     * Gets gun sprite
     * @author Nusayba Hamou
     * @since December 19, 2023
     */ 


    public void getImage() {
        if (playing.getPlayer().getBomb() == true){
        this.img = getSpriteAtlas(BOMB_ATLAS);
        }
        else {
        this.img = getSpriteAtlas(NOTHING_ATLAS);
        }
    }


     /*Getters  */

     public float getX() {
        return this.x;
     }
      public float getY() {
        return this.y;
     }

     /* Mouse events  */
    

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'mouseDragged'");
    }
}

