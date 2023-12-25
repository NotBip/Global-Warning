package Objects.Weapons;


import Entities.*;
import GameStates.Playing;
import UserInputs.MouseInputs;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import static Utilities.Atlas.*;
import static GameStates.Playing.*; 
import java.awt.Graphics2D;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import Objects.Weapons.Weapon1;

public class Bullets extends Entities.Entity implements MouseListener {

  Playing playing;

  
   private int bulletDamage = 1;
   private int bulletAmount = 1;
   private double bulletRange = 5.5;
   //private double bulletSpeed = 2.2f;
   private boolean canShoot = false;
   private boolean SHOT;
   private double mouseX;
   private double mouseY;
   private float increase;


   protected float x=0;
    protected float y=0;

  // private int width = 100;
  // private int height = 100;

   MouseInputs mouse;
   Weapon1 weapon;


   public Bullets (Weapon1 weapon, Playing playing) {
       super(weapon.getX(), weapon.getY(), 100,  100);
        this.playing = playing;
        this.weapon = weapon;
        this.xSpeed = 5.0f;
        //SHOT = false;
        initialize();
  }
   
  public void draw(Graphics g) {
    drawHitbox(g);
 }

 public void updateStartPosition() {
    //System.out.println(SHOT);
   if (SHOT != true&& SHOT == false){
   //  System.out.println("1");

    hitbox.x = weapon.getX();
    hitbox.y = weapon.getY();

    } 
    
    if (SHOT == true && SHOT != false) {
       
        SHOT = true;
        System.out.println("SHOTTTTT");
      
        hitbox.x += (float)xSpeed;
        hitbox.y += (float)xSpeed;
    
   }

}

public void setSHOT (boolean SHOT){
    this.SHOT = SHOT;
}


public void shot (double mouseX, double mouseY){
    updateStartPosition();
    //System.out.println(SHOT);
    
     this.mouseX= mouseX;
     this.mouseY = mouseY;
    //setSHOT(true);
    System.out.println("SHOT END");

}

@Override
    public void mouseClicked(MouseEvent e) {
        setSHOT(true);
    }

   @Override
    public void mouseEntered(MouseEvent e) {
      throw new UnsupportedOperationException("Unimplemented method 'mouseEntered'");
    } 

    @Override
    public void mouseExited(MouseEvent e) {
throw new UnsupportedOperationException("Unimplemented method 'mouseExited'");
    }

    @Override
    public void mousePressed(MouseEvent e) {
throw new UnsupportedOperationException("Unimplemented method 'mousePressed'");
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        throw new UnsupportedOperationException("Unimplemented method 'mouseReleased'");
    }


}