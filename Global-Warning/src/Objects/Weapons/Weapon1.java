package Objects.Weapons;

import Entities.*;
import GameStates.Playing;
import UserInputs.MouseInputs;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import static Utilities.Atlas.*;
import static Utilities.Constants.GAME_WIDTH;
import static GameStates.Playing.*; 
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;
import UserInputs.MouseInputs.*; 



public class Weapon1 implements MouseMotionListener {
    private Player player;
    BufferedImage img;
    Graphics2D g2d; 
    MouseInputs mouse;
    Playing playing;

    private int xFlipped = 0; 
    private int wFlipped = 1; 

    protected int width=50;
    protected int height=50;

    protected float x=10;
    protected float y=10;

    public Weapon1 (Player player, Playing playing) {
        this.player = player;
        this.playing = playing; 
        getImage();
  }

    public double setAngle(double centerY, double mouseY, double centerX, double mouseX) { 
        return Math.atan2(centerY - mouseY, centerX - mouseX) - Math.PI / 2;
    }
  
    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D)g; 
       //double x = GAME_WIDTH/2+50; 
       // double y = 200+50;
        //g2d.rotate(playing.getAngle() , GAME_WIDTH/2+50, 200+50);
       //g2d.rotate(playing.getAngle() , x+50, y+50);
        // if (playing.mouseX < x){
        //     xFlipped = 0; 
        //     wFlipped = 1; 
        // }
        // else {
        //     xFlipped = 100;
        //     wFlipped = -1; 
        // }
       //g2d.drawImage(img, GAME_WIDTH/2 + xFlipped, 200, 100 * wFlipped, 100, null); 
       g2d.drawImage(img,  GAME_WIDTH/2 + (int)x, (int) y, width, height, null);
      g2d.drawImage(img, (int) x, (int) y, width, height, null);
   //  g.drawImage(img, (int) x, (int) y, width, height, null);

     }

     public float getX() {
        return x;
     }
      public float getY() {
        return y;
     }

     public void update() {

        x = player.getHitbox().x;
        y = player.getHitbox().y-20;
        
     }

    public void getImage() {
        img = getSpriteAtlas(WEAPON_ATLAS); 
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'mouseDragged'");
    }
}

