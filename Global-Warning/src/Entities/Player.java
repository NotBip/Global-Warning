package Entities;

import static Utilities.Constants.*;
import static Utilities.Constants.PlayerConstants.*;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;

import UserInterface.SaveButton;

import static Utilities.Constants.Directions.*;

import static Utilities.Atlas.*;

public class Player extends Entity {

    private BufferedImage[][] animations;
    private int lvlData[][];
    private boolean moving = false; // Is the player moving?
    private boolean left, right, up, down; // What direction is being pressed
    private int playerDir = RIGHT; // The player's direction
    private final float gravity = 0.04f; // Speed of gravity
    private final float moveSpeed = 2.0f; // Default walk speed of the player
    private final float jumpSpeed = -2.75f; // Jump speed of the player
    private float dashXSpeed = 0; // Speed of the player's dash on the horizontal
    private float dashYSpeed = 0; // Speed of the player's dash on the vertical
    private int dashUpdates = 0; // The amount of updates that have passed while dashing
    private final int maxDashUpdates = 20; // The maximum amount of updates that can pass while dashing
    private int updatesBetweenDash = 0; // The amount of updates that have passed since the last dash
    private final int maxUpdatesBetweenDash = 60; // The cooldown between dashing
    private final float dashSpeedMultiplier = 2.5f; // The multiplier towards the player speed when dashing
    public boolean isDashing = false; // Is the player dashing?
    public boolean canDash = true; // Can the player dash?
    private boolean isWindy = false; // If the level the player is on is windy
    private final float windSpeed = -1.0f; // A speed added to the player at all times (except when dashing) if the level is windy
    private int xFlipped = 0;
    private int wFlipped = 1;
    private int wallJumpUpdates = 0; // The amount of updates that have passed since the last wall jump
    private final int maxWallJumpUpdates = 60; // The cooldown between walljumping
    private boolean touchingWall = false; // Is the player running into a wall?
    private boolean checkedWater = false; // Used to stop water from affecting y speed multiple times
    private int waterUpdates = 0; // The amount of updates that the user has been in the water / must be out of the water before regaining all of their oxygen
    private final int maxWaterUpdates = 1200; // The amount of updates the user can be in the water before starting to take damage

    private float healthBarWidth; // The default width of the player's health bar
    private final float healthBarHeight = 30; // The default height of the player's health bar
    public float currentHealthBarLen; // The current width of the player's health bar (depending on damage taken)

    private final float oxygenBarWidth = 200; // The default width of the player's oxygen bar
    private float currentOxygenBarLen; // The current width of the player's oxygen bar (depending on how long they have been the water)

    public Player(float x, float y, int width, int height) {
        super(x, y, width, height);
        this.maxHealth = 200;
		this.currentHealth = maxHealth;
        this.state = IDLE;
        this.inAir = true;
        this.healthBarWidth = 2 * maxHealth;
        this.currentHealthBarLen = healthBarWidth * (currentHealth / maxHealth);
        this.currentOxygenBarLen = oxygenBarWidth;
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
            wallJumpUpdates = 0;
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
        } else { // Checking for wall jumping
            if(wallJumpUpdates < maxWallJumpUpdates/2 && wallJumpUpdates > 0) {
                if(playerDir == RIGHT) {
                    xSpeed += moveSpeed;
                } else {
                    xSpeed -= moveSpeed;
                }
            } else { // Regular movement if not dashing or walljumping
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

        // Check if the player is in the water
        if(checkWater(hitbox.x, hitbox.y, lvlData)) {
            waterUpdates++;
            if(waterUpdates % 120 == 0 && waterUpdates > 0 && waterUpdates <= maxWaterUpdates) { // Decrease oxygen bar by 10%
                currentOxygenBarLen -= oxygenBarWidth / 10;
            }
            if(waterUpdates % 120 == 0 && waterUpdates > maxWaterUpdates) { // Decrease health by 10%
                changeHealth(-maxHealth/10);
                currentOxygenBarLen = 0;
            }
            if(!isDashing) { // Make the player move slower horizontally if in water if not dashing
                xSpeed /= 2;
            }
        } else { // Not in water
            if(waterUpdates > 0) { // Slowly increase the oxygen bar back up to its maximum
                if(waterUpdates > maxWaterUpdates + 120) {
                    waterUpdates = maxWaterUpdates + 120;
                    currentOxygenBarLen = 0;
                }
                waterUpdates--;
                if(waterUpdates % 120 == 0 && currentOxygenBarLen < oxygenBarWidth) { // Increase oxygen bar by 10%
                    currentOxygenBarLen += oxygenBarWidth / 10;
                }
            } else {
                waterUpdates = 0;
            }
            checkedWater = false;
        }

        // Moving vertically
        if (inAir) {
            if (canMove(hitbox.x, hitbox.y + airSpeed, hitbox.width, hitbox.height, lvlData)) { // Move on the vertical if possible
                hitbox.y += airSpeed;
                if(checkWater(hitbox.x, hitbox.y, lvlData)) {
                    if(!checkedWater) {
                        airSpeed/=2;
                        checkedWater = true;
                    }
                    airSpeed += gravity/2;
                } else {
                    airSpeed += gravity;
                }
                
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
                if(playerDir == RIGHT) {
                    playerDir = LEFT;
                } else {
                    playerDir = RIGHT;
                }
                updateAnimationTick();
                setAnimation();
            }
        
        inAir = true;
        if(checkWater(hitbox.x, hitbox.y, lvlData)) {
            airSpeed = jumpSpeed/1.5f;
        } else {
            airSpeed = jumpSpeed;
        }
        
        
        // Allow the player to jump out of a dash, keeping the dashing momentum
        if(!touchingWall) {
            canDash = true;
            dashUpdates = 0;
            updatesBetweenDash = 0;
        }
        
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
        if ((left && !right) || (!up && !down && !left && !right && playerDir == LEFT)) { // Set dash left
            dashXSpeed = -moveSpeed * dashSpeedMultiplier;

            playerDir = LEFT;
        } else if (right && !left || (!up && !down && !left && !right && playerDir == RIGHT)) { // Set dash right
            dashXSpeed = moveSpeed * dashSpeedMultiplier;
            playerDir = RIGHT;
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

    public void drawHealthBar(Graphics g) {
        g.setColor(Color.red);
        g.fillRect(20, 20, (int) healthBarWidth, (int) healthBarHeight);
        g.setColor(Color.green);
        g.fillRect(20, 20, (int) currentHealthBarLen, (int) healthBarHeight);
        g.setColor(Color.black);
        g.drawRect(20, 20, (int) healthBarWidth, (int) healthBarHeight);
    }

    public void drawOxygenBar(Graphics g) {
        if(waterUpdates > 0) {
            g.setColor(Color.BLUE);
            g.fillRect(20, 60, (int) oxygenBarWidth, (int) healthBarHeight);
            g.setColor(Color.WHITE);
            g.fillRect(20, 60, Math.max((int) currentOxygenBarLen, 0), (int) healthBarHeight);
            g.setColor(Color.black);
            g.drawRect(20, 60, (int) oxygenBarWidth, (int) healthBarHeight);
        }
        
    }

    /**
     * Changes animations based on input.
     * 
     * @author Hamad Mohammed
     * @since December 16, 2023
     */
    private void setAnimation() {
        if(playerDir == RIGHT) {
            xFlipped = 0;
            wFlipped = 1;
            if(moving) {
                state = RUNNING;
            } else {
                state = IDLE;
            }
        } else if (playerDir == LEFT) {
            xFlipped = width;
            wFlipped = -1;
            if(moving) {
                state = RUNNING;
            } else {
                state = IDLE;
            }
        }
        
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
        currentHealthBarLen = healthBarWidth * ((float)currentHealth / (float)maxHealth);
	}

    
}
