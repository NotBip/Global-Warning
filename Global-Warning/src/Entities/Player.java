package Entities;

import static Utilities.Constants.*;
import static Utilities.Constants.PlayerConstants.*;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import static Utilities.Constants.Directions.*;

import static Utilities.Atlas.*;

public class Player extends Entity {

    private BufferedImage[][] animations;
    private boolean moving = false;
    private boolean left, right, up, down;
    private int playerDir = -1;
    private float gravity = 0.04f;
    private float moveSpeed = 2.0f;
    private float jumpSpeed = -2.75f;
    private float dashXSpeed = 0;
    private float dashYSpeed = 0;
    private int dashUpdates = 0;
    private int maxDashUpdates = 20;
    public boolean isDashing = false;
    public boolean canDash = true;
    private int xFlipped = 0;
    private int wFlipped = 1;

    public Player(float x, float y, int width, int height) {
        super(x, y, width, height);
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

        if (isDashing) {
            if (dashUpdates < maxDashUpdates) { // Use dash speed instead of movement speed for a specific amount of updates
                dashUpdates++;
                xSpeed = dashXSpeed;
                airSpeed = dashYSpeed;
                if(airSpeed < 0) { // Set in air if dashing upward
                    inAir = true;
                }
            } else {
                if (dashYSpeed < 0) { // Make the transition out of up dashes less choppy
                    airSpeed = -1;
                } // Reset everything
                isDashing = false;
                dashXSpeed = 0;
                dashYSpeed = 0;
                dashUpdates = 0;
            }
        } else { // Regular movement if not dashing
            if (right && !left) { // Right
                xSpeed = moveSpeed;
                playerDir = RIGHT;
                moving = true;
            } else if (left && !right) { // Left
                xSpeed = -moveSpeed;
                playerDir = LEFT;
                moving = true;
            }
        }

        if (!inAir) { // Checking if the player has just gone into the air
            if (!checkFloor(hitbox.x, hitbox.y, hitbox.width, hitbox.height)) { // Check if player is not on ground
                inAir = true;
            } else {
                if(!isDashing) { // If not dashing on the ground, give dash back
                    canDash = true;
                    dashXSpeed = 0;
                    dashUpdates = 0;
                }
            }
        }

        if (inAir) { // Moving while in the air
            if (canMove(hitbox.x, hitbox.y + airSpeed, hitbox.width, hitbox.height)) { // Move on the vertical if possible
                hitbox.y += airSpeed;
                airSpeed += gravity;
                if (canMove(hitbox.x + xSpeed, hitbox.y, hitbox.width, hitbox.height)) { // Move on the horizontal if possible
                    hitbox.x += xSpeed;
                } else {
                    moving = false;
                }
            } else {
                if (airSpeed >= 0) { // Reset everything to do with air
                    inAir = false;
                    airSpeed = 0;
                } 
            }
        } else { // Moving on the ground
            if (canMove(hitbox.x + xSpeed, hitbox.y, hitbox.width, hitbox.height)) { // Move on the horizontal if possible
                hitbox.x += xSpeed;
            }
        }
        updateAnimationTick();
        setAnimation();
    }

    public void draw(Graphics g) {
        // drawHitbox(g);
        g.drawImage(animations[state][animationIndex], (int) hitbox.x + xFlipped, (int) hitbox.y, 55 * wFlipped, 65, null);
    }

    /**
     * Makes the player jump if they are on the ground
     * 
     * @author Ryder Hodgson
     * @since December 16, 2023
     */

    public void jump() {
        if (inAir) {
            return;
        }
        inAir = true;
        airSpeed = jumpSpeed;
    }

    /**
     * Sets the player speed to dash if they are allowed to
     * 
     * @author Ryder Hodgson
     * @since December 18, 2023
     */

    public void dash() {
        if (isDashing || !canDash) {
            return;
        }
        airSpeed = 0;
        isDashing = true;
        canDash = false;
        if (left && !right) { // Set dash left
            dashXSpeed = -moveSpeed * 2;
        } else if (right && !left) { // Set dash right
            dashXSpeed = moveSpeed * 2;
        }
        if (up && !down) { // Set dash up
            dashYSpeed = -moveSpeed * 2;
        } else if (down && !up && inAir) { // Set dash right
            dashYSpeed = moveSpeed * 2;
        }

        if (up && right || up && left) { // Make the dash cover the same distance but diagonally 
            dashXSpeed /= 1.4f;
            dashYSpeed /= 1.4f;

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

}