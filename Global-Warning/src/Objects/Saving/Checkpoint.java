package Objects.Saving;

import static Utilities.Atlas.*;
import java.awt.Graphics;
import java.io.IOException;

import Entities.Entity;
import Entities.Player;
import GameStates.Playing;
import Levels.LevelManager;
import UserInterface.SaveButton;
import Utilities.Constants.PlayerConstants;

public class Checkpoint extends Entity {
   // variables
	protected float x, y;
	protected int width, height;
	public static String fileName;
	public static int fileNum;
	public boolean reached = false;
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

				SaveButton.save1.setKey(playing.player.key.getQuantity());
				SaveButton.save1.setGem(playing.player.upgrade.getQuantity());
				SaveButton.save1.setBomb(playing.player.bomb.getQuantity());
				SaveButton.save1.setPotion(playing.player.heal.getQuantity());
				
				//SaveButton.save1.setDamage1(PlayerConstants.getPlayerDamage(playing, 1));
				//SaveButton.save1.setDamage2(PlayerConstants.getPlayerDamage(playing, 2));
				

					break;
				case 2:
				SaveButton.save2.setHealth(playing.getPlayer().currentHealth);
				SaveButton.save2.setHold(Playing.gunIndex);
				SaveButton.save2.setCooldown1((int)Playing.fireRateWeapon1);
				SaveButton.save2.setCooldown2((int)Playing.fireRateWeapon2);
				SaveButton.save2.setLevel(LevelManager.lvlIndex);

				SaveButton.save2.setKey(playing.player.key.getQuantity());
				SaveButton.save2.setGem(playing.player.upgrade.getQuantity());
				SaveButton.save2.setBomb(playing.player.bomb.getQuantity());
				SaveButton.save2.setPotion(playing.player.heal.getQuantity());

				//SaveButton.save2.setDamage1(PlayerConstants.getPlayerDamage(playing, 1));
				//SaveButton.save2.setDamage2(PlayerConstants.getPlayerDamage(playing, 2));
				
					break;
				case 3:
				SaveButton.save3.setHealth(playing.getPlayer().currentHealth);
				SaveButton.save3.setHold(Playing.gunIndex);
				SaveButton.save3.setCooldown1((int)Playing.fireRateWeapon1);
				SaveButton.save3.setCooldown2((int)Playing.fireRateWeapon2);
				SaveButton.save3.setLevel(LevelManager.lvlIndex);

				SaveButton.save3.setKey(playing.player.key.getQuantity());
				SaveButton.save3.setGem(playing.player.upgrade.getQuantity());
				SaveButton.save3.setBomb(playing.player.bomb.getQuantity());
				SaveButton.save3.setPotion(playing.player.heal.getQuantity());

			//	SaveButton.save3.setDamage1(PlayerConstants.getPlayerDamage(playing, 1));
			//	SaveButton.save3.setDamage2(PlayerConstants.getPlayerDamage(playing, 2));
					
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

	public void resetReached()  {
		reached = false;

	}

   



}