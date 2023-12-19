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

import static Utilities.Constants.*;
import static Utilities.Constants.PlayerConstants.*;

import static Utilities.Constants.Directions.*;
import javax.imageio.ImageIO;

import static Utilities.Atlas.*;
import Utilities.Atlas;

public class Weapon1 extends Entities.Entity {
   //entends entity for hitbox stuffs


   //private MouseInputs mouseInputs;
   private int bulletDamage = 1;
   private int bulletAmount = 1;
   private double bulletRange = 5.5;
   private double bulletSpeed = 2.2;
   private boolean canShoot = false;
   private Player player;

   //protected Rectangle2D.Float gunbox;
   protected float x;
   protected float y;
   protected int width;
   protected int height;

   private int mouseX;
   private  int mouseY;
   private MouseInputs mouseInputs;

   BufferedImage img;
   private boolean left, right, up, down;
   private float jumpSpeed = -2.25f;
   private float gravity = 0.04f;


   public Weapon1(float x, float y, int width, int height) {
      super(x, y, width, height);
      this.xSpeed = 2.0f;
      this.inAir = true;
      getImage();
      initialize();
  }

   public void draw(Graphics g) {
      drawHitbox(g);
      g.drawImage(img, (int) hitbox.x, (int) hitbox.y, null);
  }


  public void getImage(){
      img =  getSpriteAtlas(WEAPON_ATLAS);
  }


   public void update() {
      
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
  }

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



}

