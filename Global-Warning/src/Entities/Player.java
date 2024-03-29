package Entities;

/**
***********************************************
* @Author : Ryder Hodgson
* @Originally made : December 14th, 2023
* @Last Modified: 22 JAN, 2024
* @Description: The player themself. How the user interacts with the game through movement, interaction, and attacking
***********************************************
*/

import static Utilities.Constants.*;
import static Utilities.Constants.PlayerConstants.*;
import Items.Key;
import Items.Bomb;
import Items.UpgradeGem;
import Items.HealPotion;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;

import GameStates.Playing;
import Objects.Weapons.Bombs;
import UserInterface.SaveButton;

import static Utilities.Constants.Directions.*;

import static Utilities.Atlas.*;

public class Player extends Entity {

    private BufferedImage[][] animations; // All of the different player sprites
    private int lvlData[][]; // Data of the current level
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
    private int xFlipped = 0; // 0 = origin of where the player is drawn is from the top left, width = top right
    private int wFlipped = 1; // 1 = width goes from left to right, -1 = width goes from right to left
    private int wallJumpUpdates = 0; // The amount of updates that have passed since the last wall jump
    private final int maxWallJumpUpdates = 60; // The cooldown between walljumping
    private boolean touchingWall = false; // Is the player running into a wall?
    private boolean immune = true; // Is the player immune to damage?
    private int immunityUpdates = 0; // How long the player has been immune to damage
    private int maxImmunityUpdates = 60; // The maximum time the player can be immune for
    private int walkingUpdates = 45; // Updates between playing the step sound
    private boolean checkedWater = false; // Used to stop water from affecting y speed multiple times
    private int waterUpdates = 0; // The amount of updates that the user has been in the water / must be out of the water before regaining all of their oxygen
    private final int maxWaterUpdates = 1200; // The amount of updates the user can be in the water before starting to take damage
    private boolean isDead = false; // Is the player dead?
    public HealPotion heal = new HealPotion(this); // Health potion class initialization
    public Bomb bomb = new Bomb(this); // Bomb class initialization
    public Key key = new Key(this); // Key class initialization
    public UpgradeGem upgrade = new UpgradeGem(this); // Updgrade gem initialization
    private Playing playing; // Importing in the playing class for player interactions

    private final float oxygenBarWidth = 200; // The default width of the player's oxygen bar
    private float currentOxygenBarLen; // The current width of the player's oxygen bar (depending on how long they have been the water)
    private boolean deathSound = false; // Play the death sound?
    public boolean bombHit = false; // Has the player been hit by a bomb?

    public Player(float x, float y, int width, int height, Playing playing) {
        super(x, y, width, height);
        this.maxHealth = 200;
		this.currentHealth = maxHealth;
        this.playing = playing; 
        this.state = IDLE;
        this.inAir = true;
        this.healthBarWidth = 2 * maxHealth;
        this.healthBarHeight = 30;
        this.currentHealthBarLen = (int) (healthBarWidth * ((double)currentHealth / (double)maxHealth));
        this.currentOxygenBarLen = oxygenBarWidth;
        this.playing = playing;
        Animations();
        initialize();
    }

    public void loadLevelData(int[][] lvlData) {
        this.lvlData = lvlData;
    }

 /*
	* Method Name: canMove
	* Author: Ryder Hodgson
	* Creation Date: January 2nd, 2024
	* Modified Date: January 2nd, 2024
*//** Description: Sets the room spawn of the player
	* @param spawn the x and y coordinates to be spawned at
	* @return n/a
	* Dependencies: Point
	* Throws/Exceptions: n/a
	*/
    
    public void setSpawn(Point spawn) {
        int spawnOffset = (int) (this.hitbox.height / 2) + 10; // Don't spawn the player inside the block the spawn point in on top of
        this.x = spawn.x;
        this.y = spawn.y - spawnOffset;
        hitbox.x = x;
        hitbox.y = y;
    }

/*
	* Method Name: reset
	* Author: Ryder Hodgson
	* Creation Date: January 18th, 2024
	* Modified Date: January 18th, 2024
*//** Description: Resets everything to do with the player
	* @return n/a
	* Dependencies: n/a
	* Throws/Exceptions: n/a
	*/

    public void reset() {
        xSpeed = 0;
        airSpeed = 0;
        dashXSpeed = 0;
        dashYSpeed = 0;
        dashUpdates = 0;
        updatesBetweenDash = 0;
        isDashing = false;
        canDash = true;
        playerDir = RIGHT;
        pauseReset();
        wallJumpUpdates = 0;
        moving = false;
        animationIndex = 0;
        animationTick = 0;
        state = IDLE;
        inAir = true;
        waterUpdates = 0;
        immunityUpdates = 1;
        currentOxygenBarLen = oxygenBarWidth;
        bombHit = false;
        currentHealthBarLen = (int) (healthBarWidth * ((double)currentHealth / (double)maxHealth));
    }

/*
	* Method Name: pauseReset
	* Author: Ryder Hodgson
	* Creation Date: January 19th, 2024
	* Modified Date: January 19th, 2024
*//** Description: Resets the player movement booleans when paused
	* @return n/a
	* Dependencies: n/a
	* Throws/Exceptions: n/a
	*/

    public void pauseReset() {
        left = false;
        right = false;
    }

    /**
     * Updates the player's position, direction and sprite 
     * 
     * @author Ryder Hodgson
     * @since December 16, 2023
     */

     /*
	* Method Name: update
	* Author: Ryder Hodgson
	* Creation Date: Decemeber 16th, 2023
	* Modified Date: January 14th, 2024
*//** Description: Updates everything to do with the player
	* @return n/a
	* Dependencies: setPlayerDying, GetSpriteAmount, updateAnimationTick, canMove, checkFloor, checkWater, changeHealth, fixYPos, fixXPos, setAnimation
	* Throws/Exceptions: n/a
	*/

    public void update() {
        moving = false; // Stop the player movement animation in case they stop moving this update




        if (currentHealth <= 0) { 
            if (state != DEAD) { 
                isDead = true;
                state = DEAD;
                animationTick = 0;
                animationIndex = 0; 
            }
             else if (animationIndex == GetSpriteAmount(DEAD) - 1 && animationTick >= animationSpeed - 1) { 
                Playing.dead = true;
            } else {
             updateAnimationTick();
        }
        return; 
    }   

        isDead = false;
            for (Bombs b : playing.getBombs()) { 
                if(b.explode)
                    if(b.getExplosionHitbox().intersects(this.hitbox) && !bombHit){ 
                        this.changeHealth(-maxHealth/4);
                        bombHit=true;
                    } 
            }
            // Set the player's default speed at the start of the update before editing it later in the method 
        if(isWindy) {
            xSpeed = windSpeed;
        } else {
            xSpeed = 0;    
        }
        
        if(immunityUpdates > 0 && immunityUpdates < maxImmunityUpdates) { // Checking for if the player is immune from damage
            immunityUpdates++;
            immune = true;
        } else {
            immunityUpdates = 0;
            immune = false;   
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
                
                if(dashYSpeed != 0) { // Set the airspeed to dash speed (but allow for jumping out of a dash)
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
                // Jump off of the wall rather than just up it
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
                    if (inAir == false && walkingUpdates <= 0){
                    playing.getSoundLibrary().playSound("Walk");
                    walkingUpdates = 45;
                }
                } else if (left && !right) { // Left
                    xSpeed -= moveSpeed;
                    playerDir = LEFT;
                    moving = true;
                    if (inAir == false && walkingUpdates <= 0) {
                    playing.getSoundLibrary().playSound("Walk");
                    walkingUpdates = 45;
                    }
                }
                walkingUpdates --;
            }
        }

        // Checking if the player has just gone into the air
        if (!inAir) {
            if (!checkFloor(hitbox.x, hitbox.y, hitbox.width, hitbox.height, lvlData)) { // Check if player is not on ground
                inAir = true;
            } else {
                wallJumpUpdates = 0;
                if (!isDashing) { // If not dashing and on the ground, give dash back
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
            if(!isDashing) { // Make the player walk slower horizontally in the water 
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
                dashYSpeed = 0; 
                inAir = false;
                playing.getSoundLibrary().playSound("Land");
                airSpeed = 0;
            }
        } 
        // Moving horizontally
            if (canMove(hitbox.x + xSpeed, hitbox.y, hitbox.width, hitbox.height, lvlData) && !stuckBehindDoor) { // Move on the horizontal if possible
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
            g.drawImage(animations[state][animationIndex], (int) hitbox.x + xFlipped - offset, (int) hitbox.y, 63 * wFlipped, 68, null);
            // drawHitbox(g, offset);
    }

      /*
	* Method Name: jump
	* Author: Ryder Hodgson
	* Creation Date: Decemeber 17th, 2023
	* Modified Date: January 12th, 2024
*//** Description: Makes the player jump if they are on the ground or running into a wall
	* @return n/a
	* Dependencies: updateAnimationTick, checkWater, setAnimation
	* Throws/Exceptions: n/a
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
        playing.getSoundLibrary().playSound("Jump");
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

      /*
	* Method Name: dash
	* Author: Ryder Hodgson
	* Creation Date: December 18th, 2023
	* Modified Date: January 8th, 2024
*//** Description: Sets the player speed to dashspeed if they are allowed to
	* @return n/a
	* Dependencies: Math
	* Throws/Exceptions: n/a
	*/

    public void dash() {
        if (isDashing || !canDash || updatesBetweenDash > 0) { // Immediately return if the player is already dashing or unable to dash
            return;
        }
        updatesBetweenDash = 1;
        isDashing = true;
        playing.getSoundLibrary().playSound("Dash");
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

     /*
	* Method Name: checkWater
	* Author: Ryder Hodgson
	* Creation Date: Decemeber 12th, 2023
	* Modified Date: January 12th, 2024
*//** Description: Checks if the player is in water
	* @param x the x position of the player
    * @param y the y position of the player
    * @param lvlData the data of the current room
	* @return Is the player in water?
	* Dependencies: n/a
	* Throws/Exceptions: false
	*/

    public boolean checkWater(float x, float y, int[][] lvlData) {
        int lvlX = (int) (x / TILE_SIZE); // The current tile the entity is on in the horizontal
        int lvlY = (int) (y / TILE_SIZE); // The current tile the entity is on in the vertical

        if(lvlData[lvlY][lvlX] == 48 || lvlData[lvlY][lvlX] == 49) {
            return true;
        } 

        return false;
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
        animations = new BufferedImage[4][7];
        for (int i = 0; i < animations.length; i++) {
            for (int j = 0; j < animations[i].length; j++) {
                animations[i][j] = img.getSubimage(j * 113, i * 113, 113, 113);
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

/*
	* Method Name: drawHealthBar
	* Author: Ryder Hodgson
	* Creation Date: January 10th, 2024
	* Modified Date: January 10th, 2024
*//** Description: draws the player's health bar to the screen
	* @param g What it actually draws with
	* @return n/a
	* Dependencies: Graphics
	* Throws/Exceptions: n/a
	*/

    // public void drawHealthBar(Graphics g) {
    //     g.setColor(Color.red);
    //     g.fillRect(20, 20, (int) healthBarWidth, (int) healthBarHeight);
    //     g.setColor(Color.green);
    //     g.fillRect(20, 20, (int) currentHealthBarLen, (int) healthBarHeight);
    //     g.setColor(Color.black);
    //     g.drawRect(20, 20, (int) healthBarWidth, (int) healthBarHeight);
    // }

/*
	* Method Name: drawOxygenBar
	* Author: Ryder Hodgson
	* Creation Date: January 12th, 2024
	* Modified Date: January 12th, 2024
*//** Description: draws the player's oxygen bar to the screen
	* @param g What it actually draws with
	* @return n/a
	* Dependencies: Graphics
	* Throws/Exceptions: n/a
	*/

    // public void drawOxygenBar(Graphics g) {
    //     if(waterUpdates > 0) {
    //         g.setColor(Color.BLUE);
    //         g.fillRect(20, 60, (int) oxygenBarWidth, (int) healthBarHeight);
    //         g.setColor(Color.WHITE);
    //         g.fillRect(20, 60, Math.max((int) currentOxygenBarLen, 0), (int) healthBarHeight);
    //         g.setColor(Color.black);
    //         g.drawRect(20, 60, (int) oxygenBarWidth, (int) healthBarHeight);
    //     }

    public int getHealthWidth(){
        return (int)healthBarWidth;
    }

    public int getHealthHeight(){
        return (int)healthBarHeight;
    }

    public int getHealthLength(){
        return (int)currentHealthBarLen;
    }
    // }

    public void drawItem (Graphics g){
        //g.setColor(Color.black);
        g.setColor(new Color(0,0,0,110));
        g.fillRect(170, 30, 50, 50);
        
        BufferedImage imageItem;

        if (Playing.gunIndex == 1){
            imageItem = getSpriteAtlas(WEAPON1_ATLAS); 
        } else if (Playing.gunIndex == 2){
            imageItem = getSpriteAtlas(WEAPON2_ATLAS); 
        } else{
            imageItem = getSpriteAtlas(BOMB_ATLAS);       
        }
        g.drawImage(imageItem, 170,30, WEAPON_WIDTH, WEAPON_HEIGHT, null);
    }

    /**
     * Changes animations based on input.
     * 
     * @author Hamad Mohammed
     * @since December 16, 2023
     */
    private void setAnimation() {
        if(playerDir == RIGHT) {
            xFlipped = width;
            wFlipped = -1;
            if(moving) {
                state = RUNNING;
            } else {
                state = IDLE;
            }
        } else if (playerDir == LEFT) {
            xFlipped = 0;
            wFlipped = 1;
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

    public float getDirection() {
        return this.playerDir;
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
		if (value < 0 && currentHealth > 0){
            playing.getSoundLibrary().playSound("Damage");
            deathSound = false;
            }
        currentHealth += value;
        if (value < 0 && currentHealth < 0 && deathSound == false){
        playing.getSoundLibrary().playSound("Explode");
        deathSound = true;
        }
		currentHealth = Math.max(Math.min(currentHealth, maxHealth), 0);
        currentHealthBarLen = healthBarWidth * ((float)currentHealth / (float)maxHealth);
        immunityUpdates = 1;
        if(0 > Math.signum(value))
        playing.getHealthBar().removeHP((float) Math.abs(value)/maxHealth);
        if(0 < Math.signum(value) || currentHealth == maxHealth )
        playing.getHealthBar().addHP((float) Math.abs(value)/maxHealth);
        
	}

    public boolean getWindy() { 
        return isWindy; 
    }

    public boolean isImmune() {
        return immune;
    }

    public void dead() { 
        this.currentHealth = 0; 
    }

    public boolean isDead() { 
        return isDead;
    }

    /**
	@Method Name: getItemQuantity
	@Author: Bobby Walden
	@Creation Date: 18 JAN, 2024
	@Modified Date: 19 JAN, 2024
	@Description: Returns the quantity of the respective item.
	@Parameters: int item
	@Returns: int
	@Dependencies: Items
	@Throws/Exceptions: N/A
	*/
    public int getItemQuantity(int item) {
        switch (item) {
            case 1:
            return heal.getQuantity();
            case 2:
            return bomb.getQuantity();
            case 3:
            return key.getQuantity();
            case 4:
            return upgrade.getQuantity();
            default:
            return 0;
        }
    }

    /**
	@Method Name: useItem
	@Author: Bobby Walden
	@Creation Date: 18 JAN, 2024
	@Modified Date: 22 JAN, 2024
	@Description: Updates and uses the respective item.
	@Parameters: int item
	@Returns: N/A
	@Dependencies: Items
	@Throws/Exceptions: N/A
	*/
    public void useItem(int item) {
        if (getItemQuantity(item) > 0) {
        switch (item) {
            case 1:
            heal.useItem();
            changeHealth(heal.getHealingAmount());
            playing.getSoundLibrary().playSound("Heal");
            break;
            case 2:
            bomb.useItem();
            break;
            case 3:
            key.useItem();
            break;
            case 4:
            upgrade.useItem();
            playing.updateUpgrade();
            playing.getSoundLibrary().playSound("Heal");
            break;
            default:
        }
    }
    else {
        System.out.println("You don't have enough!");
        playing.getSoundLibrary().playSound("Denied");
    }
    }

    /**
	@Method Name: gainItem
	@Author: Bobby Walden
	@Creation Date: 18 JAN, 2024
	@Modified Date: 19 JAN, 2024
	@Description: Adds the number of items for the respective item.
	@Parameters: String item, int quantity
	@Returns: N/A
	@Dependencies: Items
	@Throws/Exceptions: N/A
	*/
    public void gainItem(String item, int quantity) {
        switch (item) {
            case "Potion":
            heal.addItem(quantity);
            break;
            case "Bomb":
            bomb.addItem(quantity);
            break;
            case "Key":
            key.addItem(quantity);
            break;
            case "Gem":
            upgrade.addItem(quantity);
            break;
            default:
        }
}

    public UpgradeGem getUpgradeGem() {
        return upgrade;
    }
}
