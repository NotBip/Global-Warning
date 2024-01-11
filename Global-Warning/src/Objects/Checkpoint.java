package Objects;

import static Utilities.Atlas.*;

import Objects.Weapons.*;



import java.awt.Graphics;
import java.awt.Rectangle;

public class Checkpoint {
   // variables
	protected int x, y, width, height;
	protected Rectangle bounds;

	/**
	 * Constructor to create a checkpoint
	 * 
	 * @author Nusayba Hamou
	 * @since January 11, 2024
	 */

	public Checkpoint(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
        createBounds();
	}


	/**
	 * Draws checkpoint
	 * 
	 * @author Nusayba Hamou
	 * @since January 11, 2024
	 */

	public void draw(Graphics g) {
	
		g.drawImage(getSpriteAtlas(FLAG_ATLAS), x, y, width, height, null);
        g.drawImage(getSpriteAtlas(SAVED_ATLAS), x, y-30, width, height-70, null);
	
	}

    /**
	 * Create bounds
	 * 
	 * @author Nusayba Hamou
	 * @since January 11, 2024
	 */

	private void createBounds() {
		bounds = new Rectangle(x, y, width, height);
	}



}