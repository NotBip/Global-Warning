package Entities;

import static Utilities.Constants.*;
import static Utilities.Constants.PlayerConstants.*;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import static Utilities.Constants.Directions.*;

import static Utilities.Atlas.*;

public class Player extends Entity {

    private BufferedImage[][] animations;
    private int lvlData[][];
    private boolean moving = false; // Is the player moving?
    private boolean left, right, up, down; // What direction is being pressed
    private int playerDir = RIGHT; // The player's direction
    private float gravity = 0.04f; // Speed of gravity
    private float moveSpeed = 2.0f; // Default walk speed of the player
    private float jumpSpeed = -2.75f; // Jump speed of the player
    private float dashXSpeed = 0; // Speed of the player's dash on the horizontal
    private float dashYSpeed = 0; // Speed of the player's dash on the vertical
    private int dashUpdates = 0; // The amount of updates that have passed while dashing
    private int maxDashUpdates = 20; // The maximum amount of updates that can pass while dashing
    private int updatesBetweenDash = 0; // The amount of updates that have passed since the last dash
    private int maxUpdatesBetweenDash = 60; // The cooldown between dashing
    private float dashSpeedMultiplier = 2.5f; // The multiplier towards the player speed when dashing
    public boolean isDashing = false; // Is the player dashing?
    public boolean canDash = true; // Can the player dash?
    private boolean isWindy = false; // If the level the player is on is windy
    private float windSpeed = -1.0f; // A speed added to the player at all times (except when dashing) if the level is windy
    private int xFlipped = 0;
    private int wFlipped = 1;
    private int wallJumpUpdates = 0; // The amount of updates that have passed since the last wall jump
    private int maxWallJumpUpdates = 60; // The cooldown between walljumping
    private boolean touchingWall = false; // Is the player running into a wall?

    private float healthBarWidth = 100;
    private float healthBarHeight = 30;
    private float currentHealthBarLen;

    public Player(float x, float y, int width, int height) {
        super(x, y, width, height);
        this.maxHealth = 100;
		this.currentHealth = maxHealth;
        this.state = IDLE;
        this.inAir = true;
        currentHealthBarLen = healthBarWidth * (currentHealth / maxHealth);
        Animations();
        initialize();

    }

    public void loadLevelData(int[][] lvlData) {
        this.lvlData = lvlData;
    }

    public void setSpawn(Point spawn) {
        int spawnOffset = (int) (this.hitbox.height / 2) + 10; // Don't spawn the player inside the block the spawn point in on top of
        this.x = spawn.x;
        this.y = spawn.y - spawnOffset;
        hitbox.x = x;
        hitbox.y = y;
    }

    /**
     * Updates the player's position, direction and sprite 
     * 
     * @author Ryder Hodgson
     * @since December 16, 2023
     */

    public void update() {
        moving = false; // Stop the player movement animation in case they stop moving this update

        // Set the player's default speed at the start of the update before editing it later in the method 
        if(isWindy) {
            xSpeed = windSpeed;
        } else {
            xSpeed = 0;    
        }

        // So the player can't infinitely jump up a wall super quickly
        if(wallJumpUpdates > 0) {
            wallJumpUpdates++;
            if(wallJumpUpdates >= maxWallJumpUpdates) {
                wallJumpUpdates = 0;
            }
        }

        // So the player can't infinitely dash on a flat floor super quickly
        if(updatesBetweenDash > 0) {
            updatesBetweenDash++;
            if(updatesBetweenDash >= maxUpdatesBetweenDash) {
                updatesBetweenDash = 0;
            }
        }

        if (isDashing) {
            if (dashUpdates < maxDashUpdates) { // Use dash speed instead of movement speed for a specific amount of updates
                dashUpdates++;
                xSpeed = dashXSpeed;
                
                if(dashYSpeed != 0) { // Set the airspeed to dash speed 
                    airSpeed = dashYSpeed;
                }
                
                if (airSpeed < 0) { // Set in air if dashing upward
                    inAir = true;
                }
            } else {
                if (dashYSpeed < 0 && canMove(hitbox.x, hitbox.y + dashYSpeed, hitbox.width, hitbox.height, lvlData)) { // Make the transition out of up dashes less choppy
                    airSpeed = -1;
                } // Reset everything
                isDashing = false;
                dashXSpeed = 0;
                dashYSpeed = 0;
                dashUpdates = 0;
            }
        } else { // Regular movement if not dashing
            if (right && !left) { // Right
                xSpeed += moveSpeed;
                playerDir = RIGHT;
                moving = true;
            } else if (left && !right) { // Left
                xSpeed -= moveSpeed;
                playerDir = LEFT;
                moving = true;
            }
        }

        // Checking if the player has just gone into the air
        if (!inAir) {
            if (!checkFloor(hitbox.x, hitbox.y, hitbox.width, hitbox.height, lvlData)) { // Check if player is not on ground
                inAir = true;
            } else {
                wallJumpUpdates = 0;
                if (!isDashing) { // If not dashing on the ground, give dash back
                    canDash = true;
                    dashXSpeed = 0;
                    dashUpdates = 0;
                }
            }
        }

        // Moving vertically
        if (inAir) {
            if (canMove(hitbox.x, hitbox.y + airSpeed, hitbox.width, hitbox.height, lvlData)) { // Move on the vertical if possible
                hitbox.y += airSpeed;
                airSpeed += gravity;
            } else {
                // Reset everything to do with air
                hitbox.y = fixYPos(hitbox, airSpeed);
                //isDashing = false;
                dashYSpeed = 0; 
                inAir = false;
                airSpeed = 0;
            }
        } 
        // Moving horizontally
            if (canMove(hitbox.x + xSpeed, hitbox.y, hitbox.width, hitbox.height, lvlData)) { // Move on the horizontal if possible
                hitbox.x += xSpeed;
                touchingWall = false;
            } else {
                touchingWall = true;
                moving = false;
                hitbox.x = fixXPos(hitbox, xSpeed);
            }
        updateAnimationTick();
        setAnimation();
    }

    public void draw(Graphics g, int offset) {
        //drawHitbox(g, offset);
        drawHealthBar(g);
        g.drawImage(animations[state][animationIndex], (int) hitbox.x + xFlipped - offset, (int) hitbox.y, 55 * wFlipped, 65, null);
    }

    /**
     * Makes the player jump if they are on the ground or running into a wall
     * 
     * @author Ryder Hodgson
     * @since December 16, 2023
     */

    public void jump() {
        if (inAir && !touchingWall) {
            return;
        }
        // Jump only if you are able to jump off a wall (or the ground, as hitting the ground resets wallJumpUpdates)
        if(wallJumpUpdates == 0) {
            if(touchingWall) {
                wallJumpUpdates = 1;
            }
        
        inAir = true;
        airSpeed = jumpSpeed;
        
        // Allow the player to jump out of a dash, keeping the dashing momentum
        canDash = true;
        dashUpdates = 0;
        updatesBetweenDash = 0;
        }
    }

    /**
     * Sets the player speed to dash if they are allowed to
     * 
     * @author Ryder Hodgson
     * @since December 18, 2023
     */

    public void dash() {
        if (isDashing || !canDash || updatesBetweenDash > 0) { // Immediately return if the player is already dashing or unable to dash
            return;
        }
        updatesBetweenDash = 1;
        isDashing = true;
        canDash = false;
        if ((left && !right) || (!up && !down && playerDir == LEFT)) { // Set dash left
            dashXSpeed = -moveSpeed * dashSpeedMultiplier;
        } else if (right && !left || (!up && !down && playerDir == RIGHT)) { // Set dash right
            dashXSpeed = moveSpeed * dashSpeedMultiplier;
        }
        if (up && !down) { // Set dash up
            dashYSpeed = -moveSpeed * dashSpeedMultiplier;
        } else if (down && !up && inAir) { // Set dash down
            dashYSpeed = moveSpeed * dashSpeedMultiplier;
        }

        if (up && (right || left)) { // Make the dash cover the same general distance if dashing diagonally using special triangle math
            dashXSpeed /= Math.sqrt(2);
            dashYSpeed /= Math.sqrt(2);
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

    private void drawHealthBar(Graphics g) {
        g.drawRect(800, 20, (int) healthBarWidth, (int) healthBarHeight);
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

    public void setDirection(int direction) {
        this.playerDir = direction;
    }

    public float getMoveSpeed() {
        return moveSpeed;
    }

    public float getXSpeed() {
        return xSpeed;
    }

    public void setWindy(boolean isWindy) {
        this.isWindy = isWindy;
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

}