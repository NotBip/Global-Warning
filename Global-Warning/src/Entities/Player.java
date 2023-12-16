package Entities;

import static Utilities.Constants.GAME_HEIGHT;
import static Utilities.Constants.GAME_WIDTH;

import java.awt.Graphics;

public class Player extends Entity {

    private boolean left, right, up, down;

    public Player(float x, float y, int width, int height) {
        super(x, y, width, height);
        this.xSpeed = 5.0f;
        initialize();
    }

    public void update() {
        if (right && !left && hitbox.x + xSpeed < GAME_WIDTH) {
            hitbox.x += xSpeed;
        } else if (!right && left && hitbox.x + xSpeed > 0) {
            hitbox.x -= xSpeed;
        }
        if (up && !down && hitbox.y + xSpeed > 0) {
            hitbox.y -= xSpeed;
        } else if (!up && down && hitbox.y + xSpeed < GAME_HEIGHT) {
            hitbox.y += xSpeed;
        }
        

        
    }

    public void draw(Graphics g) {
        drawHitbox(g);
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public void setUp(boolean up) {
        this.up = up;
    }

    public void setDown(boolean down) {
        this.down = down;
    }

}