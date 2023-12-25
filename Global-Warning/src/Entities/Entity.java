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
    protected int maxHealth;
    protected int currentHealth;
    protected int animationTick;
    protected int animationIndex;
    protected int[][] lvlData;

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

    public void drawHitbox(Graphics g) {
        g.drawRect((int) hitbox.x, (int) hitbox.y, (int) hitbox.width, (int) hitbox.height);
    }

    /**
     * @author Ryder Hodgson
     * @since December 20th, 2023
     * @param x
     * @param y
     * @param width
     * @param height
     * @param lvlData
     * @return if the player can move
     */

    public boolean canMove(float x, float y, float width, float height, int[][] lvlData) {
        if (!solidTile(x + width, y, lvlData)) {
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
     * @return if the player is touching a solid tile
     */

    public boolean solidTile(float x, float y, int[][] lvlData) {
        if(x > GAME_WIDTH || x < 0) {
            return true;
        }
        if(y > GAME_HEIGHT || y < 0) {
            return true;
        }
        int lvlX = (int) (x / TILE_SIZE); // The current tile you are on in the horizontal
        int lvlY = (int) (y / TILE_SIZE); // The current tile you are on in the vertical

        if (lvlData[lvlY][lvlX] != 11) { // Check if you are on an air tile
            return true;
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
     * @return if the player is touching the floor
     */

    public boolean checkFloor(float x, float y, float width, float height, int[][] lvlData) {
        if(!solidTile(x, y + height + 1, lvlData)) {
            if(!solidTile(x + width, y + height + 1, lvlData)) {
                return false;
            }        
        }
        return true;
        }

    /**
     * if the player hits the ground or a ceiling while moving, sets the y position to compensate for the lost movement
     * @author Kaarin Gaming / Edited by Ryder Hodgson
     * @since December 25th, 2023
     * @param hitbox
     * @param airSpeed
     * @return the new yPos
     */
public static float fixYPos(Rectangle2D.Float hitbox, float airSpeed) {
		int currentTile = (int) ((hitbox.y+hitbox.height) / TILE_SIZE); // The current tile the bottom of the player is on
		if (airSpeed > 0) { // Falling or touching floor
			int tileY = currentTile * TILE_SIZE;
			int yOffset = (int) (TILE_SIZE - hitbox.height);
			return tileY + yOffset - 1;
		} else { // Jumping or dashing
            return currentTile * TILE_SIZE - TILE_SIZE;
        }	
	}


    protected void newState(int state) {
		this.state = state;
		animationTick = 0;
		animationIndex = 0;
	}

}