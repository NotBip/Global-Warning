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
import Objects.*;

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

    protected float x=0;
    protected float y=0;
    private Bullets bullets = new Bullets(this, playing);

    public Weapon1 (Player player, Bullets bullets, Playing playing) {
        this.bullets = bullets;
        this.player = player;
        this.playing = playing; 
        getImage();
  }

    public double setAngle(double centerY, double mouseY, double centerX, double mouseX) { 
        return Math.atan2(centerY - mouseY, centerX - mouseX) - Math.PI / 2;
    }
  
    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D)g; 
       
        //positive offset
        //g2d.rotate(playing.getAngle() , x+30, y+50);

        g2d.rotate(playing.getAngle() , x+30, y+50);

         if (playing.mouseX < x){
             xFlipped = 0; 
            wFlipped = 1; 
           // System.out.println("flip once");
         }
         else {
            xFlipped = 70;
            wFlipped = -1; 
            //dSystem.out.println("flip twice");
         }
     
         //positive img
        g.drawImage(img, (int) x+xFlipped, (int) y+20, width*wFlipped, height, null);
        //bullets.draw(g);

       // g.drawImage(img, (int) x+70, (int) y+20, width*-1, height, null);



   update();

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
        //bullets.update();
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

