package Objects.Weapons;

import Entities.*;
import GameStates.Playing;
import UserInputs.MouseInputs;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import static Utilities.Atlas.*;
import java.awt.Graphics2D;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;

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
        AffineTransform oldXForm = g2d.getTransform();
        g2d.rotate(playing.getAngle() , this.x+30, this.y+50);

         if (playing.mouseX < x){
            this.xFlipped = 0; 
            this. wFlipped = 1; 
         }
         else {
            this.xFlipped = 70;
            this.wFlipped = -1; 
         }
     
        g.drawImage(this.img, (int) this.x+this.xFlipped, (int) this.y+20, this.width*this.wFlipped, this.height, null);
        g2d.setTransform(oldXForm);



   //update();

     }

     public float getX() {
        return this.x;
     }
      public float getY() {
        return this.y;
     }

     public void update() {
        this.x = player.getHitbox().x;
        this.y = player.getHitbox().y-20;
     }

    public void getImage() {
        this.img = getSpriteAtlas(WEAPON_ATLAS); 
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

