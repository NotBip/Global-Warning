package Objects.Weapons;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import Entities.Entity;

public class Weapon1 extends Entities.Player{
   //entends entity for hitbox stuffs


   //private MouseInputs mouseInputs;
   private int bulletDamage = 1;
   private int bulletAmount = 1;
   private double bulletRange = 5.5;
   private double bulletSpeed = 2.2;
   private boolean canShoot = false;
   protected Rectangle2D.Float gunBox;

   

   //bulletSize is a hitbox, ish, kinda things with dimensions

   public Weapon1(float x, float y, int width, int height) {
     super(x, y, width, height);
      initialize();
  }

  public void draw(Graphics g) {
   drawHitbox(g);
}



   
}

