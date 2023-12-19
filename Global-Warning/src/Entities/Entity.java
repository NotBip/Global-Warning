package Entities;

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


    public Entity(float x, float y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    protected void initialize() {
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
    }

    protected void newState(int state) {
		this.state = state;
		animationTick = 0;
		animationIndex = 0;
	}

    
}