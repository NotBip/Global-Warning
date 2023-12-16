package Entities;

import java.awt.geom.Rectangle2D;

public class Entity {
    private float x;
    private float y;
    private float width;
    private float height;
    private float xSpeed;
    private float airSpeed;
    private Rectangle2D.Float hitbox;
    private int state;
    private boolean inAir;
    private int maxHealth;
    private int currentHealth;
    private int animationTick;


    public Entity(float x, float y, float width, float height) {
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

    public int getState() {
        return state;
    }

    
}