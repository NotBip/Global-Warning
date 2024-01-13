package Objects.Saving;

import static Utilities.Atlas.*;
import java.awt.Graphics;
import java.io.IOException;

import Entities.Entity;
import Entities.Player;
import UserInterface.SaveButton;

public class Checkpoint extends Entity {
   // variables
	protected float x, y;
	protected int width, height;
	public static String fileName;
	public static int fileNum;
	boolean reached = false;

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
	 * @throws IOException 
	 * @since January 11, 2024
	 */

	public void draw(Graphics g, Player player) throws IOException {
	
		g.drawImage(getSpriteAtlas(FLAG_ATLAS), (int)x, (int)y, width, height, null);

		if (player.getHitbox().intersects(hitbox) && !reached){
			//reached = true;
			reached = true;
			System.out.println("ah~"+fileName);
			System.out.println(fileNum);
			SaveButton.writeSave(fileName, fileNum);
		
	
		}
		 if (reached){
			g.drawImage(getSpriteAtlas(SAVED_ATLAS), (int)x-30, (int)y-40, width+50, height-30, null);
		 }

		drawHitbox(g, 10);
	
	}

	public static void setNumName(String name, int num)  {
		fileName = name;
		fileNum = num;
		System.out.println(fileName);
		System.out.println(fileNum);
	}

   



}