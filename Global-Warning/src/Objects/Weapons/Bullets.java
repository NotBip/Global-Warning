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
   private double bulletSpeed = 2.2;
   private boolean canShoot = false;
   private boolean SHOT;

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
        initialize();
  }
   
  public void draw(Graphics g) {
    drawHitbox(g);
 }

 public void updateStartPosition() {
    //System.out.println(SHOT);
    if (SHOT != true){
  //  System.out.println("1");
     hitbox.x = weapon.getX();
    hitbox.y = weapon.getY();

    } else {
        System.out.println("0");
    }

}



public void shot (double mouseX, double mouseY){
    SHOT = true;
    updateStartPosition();
    //System.out.println(SHOT);
    
    hitbox.x = (float)mouseX;
    hitbox.y = (float)mouseY;

   /* 
   while (hitbox.y != mouseY && hitbox.x != mouseX){
    if (hitbox.x < mouseX){
        hitbox.x += 0.1;
    
    } 

    if (hitbox.y < mouseY){
        hitbox.y += 0.1;
    }
    
    if (hitbox.x > mouseX){
        hitbox.x -= 0.1;
    } 
    
    if (hitbox.y > mouseY){
        hitbox.y -= 0.1;
    }
   }
   */
    System.out.println("SHOT END");

}

@Override
    public void mouseClicked(MouseEvent e) {
        //lol random thing
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