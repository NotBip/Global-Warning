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
import java.awt.event.MouseMotionListener;
import Objects.Weapons.Weapon1;

public class Bullets extends Entities.Entity{

  Playing playing;
   private int bulletDamage = 1;
   private int bulletAmount = 1;
   private double bulletRange = 5.5;
   private double bulletSpeed = 2.2;
   private boolean canShoot = false;
   Weapon1 weapon;


   public Bullets (float x, float y, int width, int height, Weapon1 weapon, Playing playing) {
        super(x, y, width, height);
        this.weapon = weapon;
        this.playing = playing;
  }
   
  public void draw(Graphics g) {
      drawHitbox(g);
      update();
 }

 public void update() {

  hitbox.x = weapon.getX();
  hitbox.y = weapon.getY();
  
}

}