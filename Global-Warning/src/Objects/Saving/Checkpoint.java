package Objects.Saving;

import static Utilities.Atlas.*;

import Objects.Weapons.*;



import java.awt.Graphics;
import java.awt.Rectangle;

import Entities.Entity;

public class Checkpoint extends Entity {
   // variables
	protected float x, y;
	protected int width, height;
	//protected Rectangle bounds;

	/**
	 * Constructor to create a checkpoint
	 * 
	 * @author Nusayba Hamou
	 * @since January 11, 2024
	 */

	public Checkpoint(float x, float y, int width, int height) {
        super(x, y, width, height);
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		initialize();
	}


	/**
	 * Draws checkpoint
	 * 
	 * @author Nusayba Hamou
	 * @since January 11, 2024
	 */

	public void draw(Graphics g) {
	
		g.drawImage(getSpriteAtlas(FLAG_ATLAS), (int)x, (int)y, width, height, null);
        g.drawImage(getSpriteAtlas(SAVED_ATLAS), (int)x, (int)y-30, width, height-70, null);
	
	}

   



}