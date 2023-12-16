package Entities;

import static Utilities.Constants.GAME_HEIGHT;
import static Utilities.Constants.GAME_WIDTH;

import java.awt.Graphics;

public class Player extends Entity {

    private boolean left, right, up, down;
    private float gravity = 0.04f;
    private float jumpSpeed = -2.25f;



    public Player(float x, float y, int width, int height) {
        super(x, y, width, height);
        this.xSpeed = 2.0f;
        this.inAir = true;
        initialize();
    }

    public void update() {

        if(hitbox.y+hitbox.height > GAME_HEIGHT && inAir) {
            inAir = false;
            hitbox.y = GAME_HEIGHT-hitbox.height;
        }

        if (right && !left && hitbox.x + xSpeed < GAME_WIDTH) {
            hitbox.x += xSpeed;
        } else if (!right && left && hitbox.x + xSpeed > 0) {
            hitbox.x -= xSpeed;
        }
        // if (up && !down && hitbox.y + xSpeed > 0) {
        //     hitbox.y -= xSpeed;
        // } else if (!up && down && hitbox.y + xSpeed < GAME_HEIGHT) {
        //     hitbox.y += xSpeed;
        // }
        if(inAir) {
            hitbox.y += airSpeed;
            airSpeed += gravity;
        }

        
    }

    public void draw(Graphics g) {
        drawHitbox(g);
    }

    public void jump() {
        if(inAir) {
            return;
        }
        inAir = true;
        airSpeed = jumpSpeed;
    }


    public void setLeft(boolean left) {
        this.left = left;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public void setInAir(boolean inAir) {
        this.inAir = inAir;
    }

    public void setUp(boolean up) {
        this.up = up;
    }

    public void setDown(boolean down) {
        this.down = down;
    }

}