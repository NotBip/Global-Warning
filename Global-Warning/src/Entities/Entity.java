package Entities;

import static Utilities.Constants.GAME_HEIGHT;
import static Utilities.Constants.GAME_WIDTH;
import static Utilities.Constants.HEIGHT_IN_TILES;
import static Utilities.Constants.TILE_SIZE;
import static Utilities.Constants.WIDTH_IN_TILES;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;

import Main.Game;

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
    public int maxHealth;
    public int currentHealth;
    protected int animationTick;
    protected int animationIndex;
    protected int[][] lvlData;
    protected Rectangle2D.Float enemyRange; 
    protected float enemyRangeX, enemyRangeY; 
    protected int enemyRangeW, enemyRangeH; 
    protected boolean inWater;

    protected float healthBarWidth; // The default width of the player's health bar
    protected float healthBarHeight;; // The default height of the player's health bar
    public float currentHealthBarLen; // The current width of the player's health bar (depending on damage taken)

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

    public void drawHitbox(Graphics g, int offset) {
        g.setColor(Color.white);
        g.drawRect((int) hitbox.x - offset, (int) hitbox.y, (int) hitbox.width, (int) hitbox.height);
        g.drawRect((int) enemyRange.x-offset, (int) enemyRange.y, (int) enemyRange.width, (int) enemyRange.height);
    }

    /* 
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

    

    /**
     * Checks all of the corners of the entity's possible next position to see if that position can exist 
     * 
     * @author Ryder Hodgson
     * @since December 20th, 2023
     * @param x
     * @param y
     * @param width
     * @param height
     * @param lvlData
     * @return if the entity can move
     */
    public boolean canMove(float x, float y, float width, float height, int[][] lvlData) {
        if (!solidTile(x + width, y, lvlData) ) {
            if (!solidTile(x, y + height, lvlData)) {
                if (!solidTile(x, y, lvlData)) {
                    if (!solidTile(x + width, y + height, lvlData)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * @author Ryder Hodgson
     * @since December 20th, 2023
     * @param x
     * @param y
     * @param width
     * @param height
     * @param lvlData
     * @return if the entity is touching a solid tile
     */
    public boolean solidTile(float x, float y, int[][] lvlData) {
         if(x > lvlData[0].length * TILE_SIZE || x < 0) { // Right of left of the game window
             return true;
         }
        if(y > GAME_HEIGHT || y < 0) { // Bottom or top of the game window
            return true;
        }
        int lvlX = (int) (x / TILE_SIZE); // The current tile the entity is on in the horizontal
        int lvlY = (int) (y / TILE_SIZE); // The current tile the entity is on in the vertical
        
        try { // Catch possible errors where the x and/or y tiles are still somehow calculated to be out of the bounds of the window
            if (lvlData[lvlY][lvlX] != 11) { // Check if the entity is on an air tile
                if(lvlData[lvlY][lvlX] == 48 || lvlData[lvlY][lvlX] == 49) {
                    return false;
                }    
                return true;
                    }
        } catch(Exception e) {
            return false;
        }
        return false;
    }

    /**
     * @author Hamad Mohammed & Ryder Hodgson
     * @param x
     * @param y
     * @param width
     * @param height
     * @param lvlData
     * @return
     */
        public boolean onSpikes(float x, float y, int[][] lvlData) { 
            
            int lvlX = (int) (x / TILE_SIZE); // The current tile the entity is on in the horizontal
            int lvlY = (int) (y / TILE_SIZE); // The current tile the entity is on in the vertical
            try { // Catch possible errors where the x and/or y tiles are still somehow calculated to be out of the bounds of the window
                if (lvlData[lvlY][lvlX] == 6) { // Check if the entity is on an air tile
                    System.out.println("oop");
                            return true;
                        }
            } catch(Exception e) {
                return false;
            }
            return false;
        }

    /**
     * @author Ryder Hodgson
     * @since December 21st, 2023
     * @param x
     * @param y
     * @param width
     * @param height
     * @param lvlData
     * @return if the entity is touching the floor
     */

    public boolean checkFloor(float x, float y, float width, float height, int[][] lvlData) {
        if(!solidTile(x, y + height + 1, lvlData)) {
            if(!solidTile(x + width, y + height + 1, lvlData)) {
                return false;
            }        
        }
        return true;
        }

    public boolean checkWater(float x, float y, int[][] lvlData) {
        int lvlX = (int) (x / TILE_SIZE); // The current tile the entity is on in the horizontal
        int lvlY = (int) (y / TILE_SIZE); // The current tile the entity is on in the vertical

        if(lvlData[lvlY][lvlX] == 48 || lvlData[lvlY][lvlX] == 49) {
            this.inWater = true;
            return true;
        } 

        return false;
    }

    /**
     * if the entity hits the ground or a ceiling while moving, sets the y position to compensate for the lost movement
     * @author Kaarin Gaming / Edited by Ryder Hodgson
     * @since December 25th, 2023
     * @param hitbox
     * @param airSpeed
     * @return the new yPos
     */
protected static float fixYPos(Rectangle2D.Float hitbox, float airSpeed) {
		int currentTile = (int) ((hitbox.y+hitbox.height) / TILE_SIZE - 0.3); // The current tile the bottom of the entity is on, subtract 0.3 to fix inconsistency issue with the tile calculation
        
		if (airSpeed > 0) { // Falling or touching floor
			int tileY = currentTile * TILE_SIZE;
			int yOffset = (int) (TILE_SIZE - hitbox.height);
			return tileY + yOffset - 1;
		} else { // Jumping or dashing up
            return currentTile * TILE_SIZE - TILE_SIZE;
        }	
	}

    /**
     * if the player hits a wall while moving, sets the x position to compensate for the lost movement
     * @author Kaarin Gaming / Edited by Ryder Hodgson
     * @since January 2nd, 2024
     * @param hitbox
     * @param xSpeed
     * @return the new xPos
     */
protected static float fixXPos(Rectangle2D.Float hitbox, float xSpeed) {
		int currentTile = (int) ((hitbox.x+hitbox.width) / TILE_SIZE - 0.2); // The current tile the right of the entity is on, subtract 0.2 to fix inconsistency issue with the tile calculation
		if (xSpeed > 0) { // Moving the right
			int tileX = currentTile * TILE_SIZE; // The current x position of the tile the entity is on 
			int xOffset = (int) (TILE_SIZE - hitbox.width); // Offset the new x position since moving right takes into account the right side, not the left (origin of x pos)
			return tileX + xOffset - 1;
		} else { // Moving to the left
            return currentTile * TILE_SIZE - TILE_SIZE;
        }	
	}

    protected void newState(int state) {
		this.state = state;
		animationTick = 0;
		animationIndex = 0;
	}

    

}
