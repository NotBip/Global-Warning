package Entities.Planet1Enemies;

import static Utilities.Atlas.FIREBALL_ATLAS;
import static Utilities.Atlas.getSpriteAtlas;

import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import Entities.Entity;

public class Fireballs extends Entity {

    private BufferedImage[][] fireballAnimations;
    private int animationTick, animationIndex, aniSpeed = 5;
    private float x, y; 
    private Rectangle2D.Float fireballHitbox; 
    public Fireballs(float x, float y) {
        super(100, 100, 120, 80);
        x = 100; 
        y = 100; 
        fireballHitbox = new Rectangle2D.Float(100, 100, 120, 80); 
        loadImage();
    }


    public void update() { 
        y += 1.5; 
        fireballHitbox.y = y; 
        updateAnimationTick(); 
    }

    private void loadImage() { 
        BufferedImage fireball = getSpriteAtlas(FIREBALL_ATLAS); 
        fireballAnimations = new BufferedImage[1][5]; 
        for (int i = 0; i < fireballAnimations.length; i++) {
            for (int j = 0; j < fireballAnimations[i].length; j++) {
                fireballAnimations[i][j] = fireball.getSubimage(j * 60, i * 40, 60, 40);
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
        System.out.println(y);
        g.drawRect((int) fireballHitbox.x, (int)  fireballHitbox.y, (int)  fireballHitbox.width, (int) fireballHitbox.height);
        g.drawImage(fireballAnimations[0][animationIndex], (int) 100 - xOffset, (int) y, 120, 80, null); 
    }



    
}
