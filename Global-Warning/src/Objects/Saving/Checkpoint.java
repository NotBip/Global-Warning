package Objects.Saving;

import static Utilities.Atlas.*;
import java.awt.Graphics;
import java.io.IOException;

import Entities.Entity;
import Entities.Player;
import GameStates.Playing;
import Levels.LevelManager;
import UserInterface.SaveButton;

public class Checkpoint extends Entity {
   // variables
	protected float x, y;
	protected int width, height;
	public static String fileName;
	public static int fileNum;
	boolean reached = false;
	private Playing playing;

	/**
	 * Constructor to create a checkpoint
	 * 
	 * @author Nusayba Hamou
	 * @since January 11, 2024
	 */

	public Checkpoint(float x, float y, int width, int height, Playing playing) {
        super(x, y, width, height);
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.playing = playing;

		
		initialize();
	}


	/**
	 * Draws checkpoint
	 * 
	 * @author Nusayba Hamou
	 * @throws IOException 
	 * @since January 11, 2024
	 */

	public void draw(Graphics g, int xOffset) {
	
		g.drawImage(getSpriteAtlas(FLAG_ATLAS), (int) x - xOffset, (int) y, width, height, null);

		 if (reached){
			g.drawImage(getSpriteAtlas(SAVED_ATLAS), (int)x - 30 - xOffset, (int)y-40, width+50, height-30, null);
		 }

		drawHitbox(g, xOffset);
	
	}

	public void update() throws IOException {
		if (playing.getPlayer().getHitbox().intersects(hitbox) && !reached){
			reached = true;

			switch (fileNum) {
				case 1:
				SaveButton.save1.setHealth(playing.getPlayer().currentHealth);
				SaveButton.save1.setHold(Playing.gunIndex);
				SaveButton.save1.setCooldown1((int)Playing.fireRateWeapon1);
				SaveButton.save1.setCooldown2((int)Playing.fireRateWeapon2);
				SaveButton.save1.setLevel(LevelManager.lvlIndex);
				

					break;
				case 2:
				SaveButton.save2.setHealth(playing.getPlayer().currentHealth);
				SaveButton.save2.setHold(Playing.gunIndex);
				SaveButton.save2.setCooldown1((int)Playing.fireRateWeapon1);
				SaveButton.save2.setCooldown2((int)Playing.fireRateWeapon2);
				SaveButton.save2.setLevel(LevelManager.lvlIndex);
				
					break;
				case 3:
				SaveButton.save3.setHealth(playing.getPlayer().currentHealth);
				SaveButton.save3.setHold(Playing.gunIndex);
				SaveButton.save3.setCooldown1((int)Playing.fireRateWeapon1);
				SaveButton.save3.setCooldown2((int)Playing.fireRateWeapon2);
				SaveButton.save3.setLevel(LevelManager.lvlIndex);
					
					break;
			
				default:
					break;
			}
			
			SaveButton.writeSave(fileName, fileNum);
		
	
		}
	}

	public static void setNumName(String name, int num)  {
		fileName = name;
		fileNum = num;
		System.out.println(fileName);
		System.out.println(fileNum);
	}

   



}