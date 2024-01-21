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
    // public Rectangle2D.Float fireballHitbox; 
    private Playing playing; 
    
    public Fireballs(float x, float y) {
        super(x, y, 70, 20);
        this.x = x; 
        this.y = y; 
        // fireballHitbox = new Rectangle2D.Float(this.x, this.y, 100, 80); 
        super.inAir = true; 
        loadImage();
        initialize();
    }


    public void update(Playing playing) { 
        this.playing = playing;
        hitbox.y += 2; 
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
        g2d.rotate(Math.PI/2, hitbox.x, hitbox.y);
        // g.drawRect((int) fireballHitbox.x, (int)  fireballHitbox.y, (int)  fireballHitbox.width, (int) fireballHitbox.height);
        g.drawImage(fireballAnimations[0][animationIndex], (int) hitbox.x - 40 - xOffset, (int) hitbox.y-34, 120, 80, null);
        drawHitbox(g, xOffset);

        g2d.setTransform(oldXForm);
    }



    
}
