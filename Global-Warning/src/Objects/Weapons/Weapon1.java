package Objects.Weapons;

import java.awt.geom.Rectangle2D;
import Entities.*;
import UserInputs.MouseInputs;
import javax.swing.JPanel;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.lang.Object;

import static Utilities.Constants.*;
import static Utilities.Constants.PlayerConstants.*;

import static Utilities.Constants.Directions.*;
import javax.imageio.ImageIO;

import static Utilities.Atlas.*;
import java.util.Objects; 
import Utilities.Atlas;
import java.awt.Graphics2D;
import java.awt.Point;


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
   protected int x=10;
   protected int y=10;
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
   
   private Point imagePosition = new Point(x,y);
   private double imageAngleRad = 0;


   public Weapon1 (Player player) {
        this.player = player;
         getImage();
  }

   public void draw(Graphics g) {
    Graphics2D g2 = (Graphics2D)g;
      g2.drawImage(img, (int) x, (int) y, width, height, null);
      g2.rotate(angle);
  }

  public double getAngle(int mouseX, int mouseY){
    angle = Math.atan2(mouseX, mouseY); 
    System.out.println("angle is "+angle);
    return angle;
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

