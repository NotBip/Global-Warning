package Objects.Weapons;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import Entities.*;

public class Weapon1 {
   //entends entity for hitbox stuffs


   //private MouseInputs mouseInputs;
   private int bulletDamage = 1;
   private int bulletAmount = 1;
   private double bulletRange = 5.5;
   private double bulletSpeed = 2.2;
   private boolean canShoot = false;
   private Player player;

   protected Rectangle2D.Float gunbox;
   protected float x;
    protected float y;
    protected int width;
    protected int height;

   private int mouseX;
   private  int mouseY;

   

   //bulletSize is a hitbox, ish, kinda things with dimensions

   public Weapon1(Player player) {
      this.player = player;
  }

    protected void initialize() {
      gunbox = new Rectangle2D.Float(x, y, width, height);
      
   }

   public Rectangle2D.Float getGunbox() {
      return gunbox;
   }

   public void draw(Graphics g) {
      drawGunbox(g);
     // g.drawImage(animations[state][animationIndex], (int) hitbox.x, (int) hitbox.y, null);
  }



public void drawGunbox(Graphics g) {
   g.drawRect((int) gunbox.x, (int) gunbox.y, (int) gunbox.width, (int) gunbox.height);
      
}
   
   
}

