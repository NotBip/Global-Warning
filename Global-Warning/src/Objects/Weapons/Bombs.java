package Objects.Weapons;

import static Utilities.Atlas.BOMBEXPLODE_ATLAS;
import static Utilities.Atlas.BOMB_ATLAS;
import static Utilities.Atlas.getSpriteAtlas;
import static Utilities.Constants.WEAPON_HEIGHT;
import static Utilities.Constants.WEAPON_WIDTH;
import static Utilities.Constants.animationSpeed;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ConcurrentModificationException;

import Entities.Entity;
import GameStates.Playing;

public class Bombs extends Entity {

    BufferedImage img, bombImg;
    private BufferedImage[][] animations;
    private  int animationTick, animationIndex, aniSpeed = 5;
    private double x, y, vertX, vertY, initX, initY, targetX, targetY;
    public static double speed = 1;
    private double directionX, directionY;
    private Playing playing;
    private int[][] lvlData;
    private double time;
    private boolean explode = false;
    private int xFlipped = 0; 
    private int wFlipped = 1;
    private int explodePosX = 0; 
    private int explodePosY = 0; 
    private Weapon1 weapon;


    public Bombs(Playing playing, Weapon1 weapon, int[][] lvlData, double time, double startX, double startY, double targetX, double targetY, int xOffset) {
        super((float)startX, (float)startY, 32, 32);
        this.lvlData = lvlData;
        this.time = time;
        this.x = startX;
        this.y = startY;
        this.initX = startX;
        this.initY = startY;
        this.vertX = targetX - startX + xOffset;
        this.vertY = targetY - startY;
        this.targetX = targetX + xOffset - startX;
        this.targetY = targetY - startY;
        this.playing = playing; 
        this.weapon = weapon; 
        loadImage(); 
        initialize();
    }

    public void setDirection(double targetX, double targetY, int xOffset) {
        //something goes wrong here
        double angle = Math.atan2(targetY - y, targetX - x + xOffset);

        this.directionX = Math.cos(angle);
        this.directionY = Math.sin(angle);
    }

    public void update() { 

        double tempChange = 0;

    if (Playing.gunIndex == 3) {
        if(canMove((float) (hitbox.x + speed * directionX), (float) (hitbox.y - speed * directionY), hitbox.width, hitbox.height, lvlData)) {
            // Change x of bomb
            if(playing.BombReady)
            playing.BombReady = false; 

            tempChange = Math.min(speed * ((vertX / initX) * 10), 3 * speed); // cap out the speed at 3 * the speed
            if(tempChange < 0) { // cap out if bomb is moving at negative speed
                tempChange = Math.max(tempChange, -3);
            }
            x += tempChange;
            hitbox.x += tempChange;

            tempChange = speed * (0.5 * 9.8 * Math.pow((this.time + (vertY / initY)), 2));
            if ((time + (vertY / initY)) > 0){
            y += tempChange;
            hitbox.y += tempChange;
            }
            else {
                y -= tempChange;
                hitbox.y -= tempChange; 
            }
            explodePosX = (int) hitbox.x; 
            explodePosY = (int) hitbox.y;
            
        } else {
            playing.BombReady = false; 
            explode = true; 
            //playing.removeBomb();
        }
    }
    }

    public void draw(Graphics g, int xOffset) {
        Graphics2D g2d = (Graphics2D) g;
        int drawX = (int) Math.round(x - xOffset);
        int drawY = (int) Math.round(y);

        hitbox.x = drawX - 5 + xOffset;
        hitbox.y = drawY - 5;


        g.drawImage(this.bombImg, (int) this.x+this.xFlipped - xOffset, (int) this.y, WEAPON_WIDTH*this.wFlipped / 2, WEAPON_HEIGHT / 2, null);

        g2d.setColor(Color.WHITE);
        drawHitbox(g, xOffset);

        if(explode){ 
            updateAnimationTick();   
            drawBombAnimation(g, xOffset);
            if(animationIndex >= 5 && animationTick >= aniSpeed - 1){ 
            playing.BombReady = true; 
            playing.removeBomb();
            }
        }
    }

    public void drawBombAnimation(Graphics g, int xOffset) { 
        g.drawImage(animations[9][animationIndex], (int) hitbox.x-32-xOffset , (int) hitbox.y-32, 128, 128, null);
    }

    private void loadImage() { 

        bombImg = getSpriteAtlas(BOMB_ATLAS);

        BufferedImage img = getSpriteAtlas(BOMBEXPLODE_ATLAS); 
        animations = new BufferedImage[10][15]; 
        for (int i = 0; i < animations.length; i++) {
            for (int j = 0; j < animations[i].length; j++) {
                animations[i][j] = img.getSubimage(j * 32, i * 32, 32, 32);
            }
        }
    }

    private void updateAnimationTick() {
        animationTick++;
        if (animationTick >= aniSpeed) {
            animationTick = 0;
            animationIndex++;
            if (animationIndex >= 6)
                animationIndex = 0;
        }
    }

    
    public void setTime() {
        this.time = 0;
    }

    public double getTime() {
        return this.time;
    }

    public int getDrawX() {
        return (int) Math.round(x);
    }

    public int getDrawY() {
        return (int) Math.round(y);
    }

    
    public void updateBombs() {
        try {
            for (Bombs bomb : playing.bombs) {
                bomb.update();
                this.time += 0.008;
            };
        } catch (ConcurrentModificationException e) {

        }
       
    }
    

    


    
}
