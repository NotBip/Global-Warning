package Objects.Weapons;

import Entities.*;
import UserInputs.MouseInputs;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import static Utilities.Atlas.*;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.AffineTransform;


public class Weapon1 {
   //entends entity for hitbox stuffs


   //private MouseInputs mouseInputs;
   private int bulletDamage = 1;
   private int bulletAmount = 1;
   private double bulletRange = 5.5;
   private double bulletSpeed = 2.2;
   private boolean canShoot = false;
   private Player player;
  // private Rotate rotate;

   //protected Rectangle2D.Float gunbox;
   protected double x=10;
   protected double y=10;
  private double mouseX=10;
   private double mouseY=10;


   protected int width=50;
   protected int height=50;

   //private int mouseX;
   //private  int mouseY;
   private MouseInputs mouseInputs;
   private double  angle;

   BufferedImage img;
   private boolean left, right, up, down;
   private float jumpSpeed = -2.25f;
   private float gravity = 0.04f;
   
   private double imageAngleRad = 0;


   public Weapon1 (Player player) {
        this.player = player;
         getImage();
  }

   public void draw(Graphics g) {
      Graphics2D g2 = (Graphics2D)g;
      double x = mouseX - width / 2d;
      double y = mouseY - height / 2d;

      double theta = Math.atan2(this.x, this.y);
      g2.rotate(-theta);
      g2.translate(100,100);
      g2.drawImage(img, (int) x, (int) y, width, height, null);
  }

  public void getMouse(double mouseX, double mouseY){
    mouseX = this.mouseX;
    mouseY = this.mouseY;
  }


public int geX(){
      return (int)x;
  }

public int geY(){
      return (int)y;
  }

  public void getImage(){
      img =  getSpriteAtlas(WEAPON_ATLAS);
  }


   public void update() {
      
        x = (int) player.getHitbox().x;
        y = (int) player.getHitbox().y-20;

        
        /* 
        if(hitbox.y+hitbox.height > GAME_HEIGHT && inAir) {
            inAir = false;
            hitbox.y = GAME_HEIGHT-hitbox.height-20;
        }
        if (right && !left && hitbox.x + xSpeed < GAME_WIDTH - hitbox.width) {
            hitbox.x += xSpeed;
        } else if (!right && left && hitbox.x + xSpeed > 0) {
            hitbox.x -= xSpeed;
        }

        if(inAir) {
            hitbox.y += airSpeed;
            airSpeed += gravity;
        }
        */
  }


/* 
  public void jump() {
   if(inAir) {
       return;
   }
   inAir = true;
   airSpeed = jumpSpeed;
}


  public void setLeft(boolean left) {
   this.left = left;
}

public void setRight(boolean right) {
   this.right = right;
}

public void setInAir(boolean inAir) {
   this.inAir = inAir;
}

public void setUp(boolean up) {
   this.up = up;
}

public void setDown(boolean down) {
   this.down = down; 
}

*/

}

