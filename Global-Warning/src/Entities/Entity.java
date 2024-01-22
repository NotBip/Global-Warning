package Entities;

import static Utilities.Constants.GAME_HEIGHT;
import static Utilities.Constants.TILE_SIZE;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;

public class Entity {
    protected float x; // x coordinate of left side of the entity
    protected float y; // y coordinate of top side of the entity
    protected int width; // width of the entity
    protected int height; // height of the entity
    protected float xSpeed; // horizontal speed of the entity
    protected float airSpeed; // vertical speed of the entity
    protected Rectangle2D.Float hitbox; // hitbox of the entity
    protected int state; // current state of entity
    protected boolean inAir; // is the entity in the air?
    public int maxHealth; // maximum health of the entity
    public int currentHealth; // current health of the entity
    protected int animationTick; // ticker that increases every update, change animation index based on animation speed
    protected int animationIndex; // current index/sprite of animation to be drawn, updates when animation tick exceeds animation speed
    protected int[][] lvlData; // data of the current room

    protected float healthBarWidth; // The default width of the entity's health bar
    protected float healthBarHeight;; // The default height of the entity's health bar
    public float currentHealthBarLen; // The current width of the entity's health bar (depending on damage taken)
    public boolean stuckBehindDoor = false;

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

    public void drawHitbox(Graphics g, int offset) {
        g.setColor(Color.white);
        g.drawRect((int) hitbox.x - offset, (int) hitbox.y, (int) hitbox.width, (int) hitbox.height);
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

     /*
	* Method Name: canMove
	* Author: Ryder Hodgson
	* Creation Date: December 20th, 2023
	* Modified Date: December 20th, 2023
*//** Description: Checks the next position of an entity to see if it is a possible position
	* @param x the next x position of the entity
    * @param y the next y position of the entity
    * @param width the width of the entity
    * @param height the height of the entity
    * @param lvlData the data of the current level
	* @return Can the entity move here?
	* Dependencies: solidTile
	* Throws/Exceptions: false
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

    /*
	* Method Name: solidTile
	* Author: Ryder Hodgson
	* Creation Date: December 20th, 2023
	* Modified Date: January 12th, 2024
*//** Description: Checks the next position of an entity to see if it is a solid tile
	* @param x the next x position of the entity
    * @param y the next y position of the entity
    * @param lvlData the data of the current level
	* @return Is the next position a solid tile?
	* Dependencies: 
	* Throws/Exceptions: false
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
                if(lvlData[lvlY][lvlX] == 48 || lvlData[lvlY][lvlX] == 49) { // Check water tiles
                    return false;
                }    
                return true;
                    }
        } catch(Exception e) {
            return false;
        }
        return false;
    }

    /*
	* Method Name: checkFloor
	* Author: Ryder Hodgson
	* Creation Date: December 21st, 2023
	* Modified Date: December 21st, 2023
*//** Description: Checks below the entity to see if they are on a floor
	* @param x the x position of the entity
    * @param y the next y position of the entity
    * @param width the width of the entity
    * @param height the height of the entity
    * @param lvlData the data of the current level
	* @return Is the entity on a floor?
	* Dependencies: solidTile
	* Throws/Exceptions: true
	*/

    public boolean checkFloor(float x, float y, float width, float height, int[][] lvlData) {
        if(!solidTile(x, y + height + 1, lvlData)) {
            if(!solidTile(x + width, y + height + 1, lvlData)) {
                return false;
            }        
        }
        return true;
        }

     /*
	* Method Name: fixYPos
	* Author: Kaarin Gaming / Edited by Ryder Hodgson
	* Creation Date: December 25th, 2023
	* Modified Date: December 25th, 2023
*//** Description: If the entity hits the ground or a ceiling while moving, sets the y position to compensate for the lost movement
	* @param hitbox the entities hitbox
    * @param airSpeed the current vertical speed of the entity
	* @return the value to change the entities y position to
	* Dependencies: n/a
	* Throws/Exceptions: n/a
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

     /*
	* Method Name: fixYPos
	* Author: Kaarin Gaming / Edited by Ryder Hodgson
	* Creation Date: January 2nd, 2024
	* Modified Date: January 2nd, 2024
*//** Description: If the player hits a wall while moving, sets the x position to compensate for the lost movement
	* @param hitbox the entities hitbox
    * @param xSpeed the current horizontal speed of the entity
	* @return the value to change the entities x position to
	* Dependencies: n/a
	* Throws/Exceptions: n/a
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

    public void setBehindDoor(boolean stuckBehindDoor) {
        this.stuckBehindDoor = stuckBehindDoor;
    }

}
