package Entities;

import static Utilities.Constants.*;
import static Utilities.Constants.PlayerConstants.*;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import Main.Game;

import static Utilities.Constants.Directions.*;
import static Entities.Enemy.*; 
import static Utilities.Atlas.*;

public class Player extends Entity {

    private BufferedImage[][] animations;
    private boolean moving = false;
    public boolean left, right, up, down;
    private int playerDir = -1;
    private float gravity = 0.04f * Game.SCALE;
    private float airFrictionX = 0.1f * Game.SCALE;
    private float airFrictionY = 0.1f * Game.SCALE;
    public float moveSpeed = 2.0f  * Game.SCALE;
    private float jumpSpeed = -2.75f * Game.SCALE;
    private float dashXSpeed = 0;
    private float dashYSpeed = 0;
    private int dashUpdates = 0;
    private int maxDashUpdates = 20;
    public boolean isDashing = false;
    public boolean canDash = true;
    private int xFlipped = 0;
    private int wFlipped = 1;
    private Enemy enemy; 

    public Player(float x, float y, int width, int height) {
        super(x, y, width, height);
        this.maxHealth = 100;
		this.currentHealth = maxHealth;
        this.state = IDLE;
        this.inAir = true;
        Animations();
        initialize();

    }

    /**
     * Updates the player (position and direction currently)
     * 
     * @author Ryder Hodgson
     * @since December 16, 2023
     */

    public void update() {
        moving = false; // Stop the player movement animation in case they stop moving this update
        xSpeed = 0;

      //  System.out.println(currentHealth);
        if (airSpeed != 0) {
            inAir = true;
        }

        if (this.currentHealth <= 0){ 
            airSpeed -= .05;     
        }

        if(!inAir) {
            airSpeed = 0;
        }

        if (isDashing) {

            if (dashUpdates < maxDashUpdates) { // Use dash speed instead of movement speed for a specific amount of
                                                // updates
                dashUpdates++;
                xSpeed = dashXSpeed;
                airSpeed = dashYSpeed;
            } else {
                if (dashYSpeed < 0) { // Make the transition out of up dashes less choppy
                    airSpeed = -1;
                } // Reset everything
                isDashing = false;
                dashXSpeed = 0;
                dashYSpeed = 0;
                dashUpdates = 0;
            }

        } else {
            if (right && !left) {
                xSpeed = moveSpeed;
                playerDir = RIGHT;
                moving = true;
            } else if (left && !right) {
                xSpeed = -moveSpeed;
                playerDir = LEFT;
                moving = true;
            }
            if (inAir) {
                airSpeed += gravity;
            } else {
                airSpeed = 0;
            }

        }

        if (canMove(hitbox.x + xSpeed, hitbox.y, hitbox.width, hitbox.height)) { // Move on the horizontal if possible
            hitbox.x += xSpeed;
        } else {
            moving = false;
        }
        if (canMove(hitbox.x, hitbox.y + airSpeed, hitbox.width, hitbox.height)) { // Move on the vertical if possible
            hitbox.y += airSpeed;
        }

        else if(checkFloor(hitbox.x, hitbox.y + airSpeed, hitbox.width, hitbox.height)) { // Check if player is on ground
            inAir = false;
            airSpeed = 0;
            if(dashXSpeed == 0) {
                isDashing = false;
                canDash = true;
                dashUpdates = 0;
            }
        }
        updateAnimationTick();
        setAnimation();
    }

    public void draw(Graphics g) {
        drawHitbox(g);
        g.drawImage(animations[state][animationIndex], (int) hitbox.x + xFlipped, (int) hitbox.y, (55 * wFlipped) * (int) Game.SCALE, 65 * (int) Game.SCALE,
                null);

    }

    /**
     * Makes the player jump if they are on the ground
     * 
     * @author Ryder Hodgson
     * @since December 16, 2023
     */

    public void jump() {
        if (inAir || Math.abs(dashXSpeed) >= 1) {
            return;
        }
        inAir = true;
        airSpeed = jumpSpeed;
    }

    public void dash() {
        if (isDashing || !canDash) {
            return;
        }
        airSpeed = 0;
        isDashing = true;
        canDash = false;
        if (left && !right) {
            dashXSpeed = -moveSpeed * 2;
            airFrictionX = -Math.abs(airFrictionX);

        } else if (right && !left) {
            dashXSpeed = moveSpeed * 2;
            airFrictionX = Math.abs(airFrictionX);

        }
        if (up && !down) {
            dashYSpeed = -moveSpeed * 2;
            airFrictionY = -Math.abs(airFrictionY);
        } else if (down && !up && inAir) {
            dashYSpeed = moveSpeed * 2;
            airFrictionY = Math.abs(airFrictionY);
        }

        if (up && right || up && left) {
            dashXSpeed /= 1.2f;
            dashYSpeed /= 1.2f;

        }

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

    /**
     * Loads the animations from the sprite atlas.
     * 
     * @author Hamad Mohammed
     * @since December 16, 2023
     */
    public void Animations() {
        BufferedImage img = getSpriteAtlas(PLAYER_ATLAS);
        animations = new BufferedImage[4][4];
        for (int i = 0; i < animations.length; i++) {
            for (int j = 0; j < animations[i].length; j++) {
                animations[i][j] = img.getSubimage(j * 64, i * 64, 64, 64);
            }
        }
    }

    /**
     * Helps change images making it animate.
     * 
     * @author Hamad Mohammed
     * @since December 16, 2023
     */
    private void updateAnimationTick() {
        animationTick++;
        if (animationTick >= animationSpeed) {
            animationTick = 0;
            animationIndex++;
            if (animationIndex >= GetSpriteAmount(state))
                animationIndex = 0;
        }
    }

    /**
     * Changes animations based on input.
     * 
     * @author Hamad Mohammed
     * @since December 16, 2023
     */
    private void setAnimation() {
        if (moving && playerDir == 2) {
            state = RUNNING;
            xFlipped = 0;
            wFlipped = 1;
        } else if (!moving && playerDir == RIGHT)
            state = IDLE;
        else if (moving && playerDir == LEFT) {
            state = RUNNING;
            xFlipped = width;
            wFlipped = -1;
        } else if (!moving && playerDir == 0)
            state = IDLE;
    }

    /**
     * Sets the direction the player is facing.
     * 
     * @author Hamad Mohammed
     * @since December 16, 2023
     */
    public void setDirection(int direction) {
        this.playerDir = direction;
        moving = true;
    }

    public float getMoveSpeed() {
        return moveSpeed;
    }

    public float getXSpeed() {
        return xSpeed;
    }

    /**
     * Changes the players health depending on enemy.
     * @author Hamad Mohammed
     * @param value Damage being done 
     * @since December 16, 2023
     */
     public void changeHealth(int value) {
		currentHealth += value;
		currentHealth = Math.max(Math.min(currentHealth, maxHealth), 0);
	}

    /**
     * Adds knockback to player when attacked. 
     * @author Hamad Mohammed
     * @since December 22, 2023
     */
    public void knockBack() { 

    }



}