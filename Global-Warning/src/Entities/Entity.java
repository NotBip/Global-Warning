package Entities;

import static Utilities.Constants.GAME_HEIGHT;
import static Utilities.Constants.GAME_WIDTH;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;

public class Entity {
    protected float x;
    protected float y;
    protected int width;
    protected int height;
    protected float xSpeed;
    protected float airSpeed;
    protected Rectangle2D.Float hitbox;
    protected int state;
    protected boolean inAir;
    protected int maxHealth;
    protected int currentHealth;
    protected int animationTick;
    protected int animationIndex;
    protected Rectangle2D.Float enemyRange; 
    protected float enemyRangeX, enemyRangeY; 
    protected int enemyRangeW, enemyRangeH; 

    public Entity(float x, float y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    protected void initialize() {
        enemyRange = new Rectangle2D.Float(enemyRangeX, enemyRangeY, enemyRangeW, enemyRangeH); 
        hitbox = new Rectangle2D.Float(x, y, width, height);
    }

    public Rectangle2D.Float getHitbox() {
        return hitbox;
    }

    public float hitboxX() {
        return this.x; 
    }

    public float hitboxY() {
        return this.y; 
    }

    public int getState() {
        return state;
    }

    public void drawHitbox(Graphics g) {
        g.drawRect((int) hitbox.x, (int) hitbox.y, (int) hitbox.width, (int) hitbox.height);
        g.drawRect((int) enemyRange.x, (int) enemyRange.y, (int) enemyRange.width, (int) enemyRange.height);
    }

    public boolean canMove(float x, float y, float width, float height) {
        if (!solidTile(x + width, y)) {
            if (!solidTile(x, y + height)) {
                if (!solidTile(x, y)) {
                    if (!solidTile(x + width, y + height)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean solidTile(float x, float y) {
        if(x > GAME_WIDTH || x < 0) {
            return true;
        }
        if(y > GAME_HEIGHT || y < 0) {
            return true;
        }

        return false;
    }

    public boolean checkFloor(float x, float y, float width, float height) {
        if(!solidTile(x, y + height + 1)) {
            if(!solidTile(x + width, y + height + 1)) {
                return false;
            }        
        }
        return true;
        }
    

    protected void newState(int state) {
		this.state = state;
		animationTick = 0;
		animationIndex = 0;
	}

}