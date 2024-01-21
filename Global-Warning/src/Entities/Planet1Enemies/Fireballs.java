package Entities.Planet1Enemies;

import static Utilities.Atlas.FIREBALL_ATLAS;
import static Utilities.Atlas.getSpriteAtlas;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import Entities.Entity;
import GameStates.Playing;

public class Fireballs extends Entity {

    private BufferedImage[][] fireballAnimations;
    private int animationTick, animationIndex, aniSpeed = 15;
    private float x, y; 
    public Rectangle2D.Float fireballHitbox; 
    private Playing playing; 
    
    public Fireballs(float x, float y) {
        super(x, 100, 120, 80);
        this.x = x; 
        this.y = 100; 
        fireballHitbox = new Rectangle2D.Float(this.x, this.y, 100, 60); 
        super.inAir = true; 
        loadImage();
    }


    public void update(Playing playing) { 
        this.playing = playing; 
        fireballHitbox.y += 2; 
        updateAnimationTick(); 

    }

    private void loadImage() { 
        BufferedImage fireball = getSpriteAtlas(FIREBALL_ATLAS); 
        fireballAnimations = new BufferedImage[1][5]; 
        for (int i = 0; i < fireballAnimations.length; i++) {
            for (int j = 0; j < fireballAnimations[i].length; j++) {
                fireballAnimations[i][j] = fireball.getSubimage(j * 66, i * 34, 66, 34);
            }
        }
    }

    private void updateAnimationTick() {
        animationTick++;
        if (animationTick >= aniSpeed) {
            animationTick = 0;
            animationIndex++;
            if (animationIndex >= 5)
                animationIndex = 0;
        }
    }

    public void drawFireBall(Graphics g, int xOffset) { 
        Graphics2D g2d = (Graphics2D)g; 
        AffineTransform oldXForm = g2d.getTransform();
        g2d.rotate(Math.PI/2, fireballHitbox.x, fireballHitbox.y);
        g.drawRect((int) fireballHitbox.x, (int)  fireballHitbox.y, (int)  fireballHitbox.width, (int) fireballHitbox.height);
        g.drawImage(fireballAnimations[0][animationIndex], (int) fireballHitbox.x - xOffset, (int) fireballHitbox.y, 120, 80, null);


        g2d.setTransform(oldXForm);
    }



    
}
