package Objects.Saving;

import static Utilities.Atlas.*;
import java.awt.Graphics;
import java.io.IOException;
import Main.Game;
import Entities.Entity;
import Entities.Player;
import GameStates.Playing;
import Main.Game;
import UserInterface.SaveButton;

public class Checkpoint extends Entity {
   // variables
	protected float x, y;
	protected int width, height;
	public static String fileName;
	public static  int fileNum;
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

	public void update(Game game) throws IOException {
		if (playing.getPlayer().getHitbox().intersects(hitbox) && !reached){
			reached = true;
			System.out.println(game.getSave().getSaveButtons1().save1.getHealth());
			switch (fileNum) {
				case 1:
					game.getSave().getSaveButtons1().save1.setHealth(playing.getPlayer().currentHealth);
					game.getSave().getSaveButtons1().save1.setHold(Playing.gunIndex);
					game.getSave().getSaveButtons1().save1.setCooldown1((int)Playing.fireRateWeapon1);
					game.getSave().getSaveButtons1().save1.setCooldown2((int)Playing.fireRateWeapon2);
				break;

				case 2:
					game.getSave().getSaveButtons2().save2.setHealth(playing.getPlayer().currentHealth);
					game.getSave().getSaveButtons2().save2.setHold(Playing.gunIndex);
					game.getSave().getSaveButtons2().save2.setCooldown1((int)Playing.fireRateWeapon1);
					game.getSave().getSaveButtons2().save2.setCooldown2((int)Playing.fireRateWeapon2);
				break;

				case 3:
				game.getSave().getSaveButtons3().save3.setHealth(playing.getPlayer().currentHealth);
				game.getSave().getSaveButtons3().save3.setHold(Playing.gunIndex);
				game.getSave().getSaveButtons3().save3.setCooldown1((int)Playing.fireRateWeapon1);
				game.getSave().getSaveButtons3().save3.setCooldown2((int)Playing.fireRateWeapon2);
					
					break;
			
				default:
					break;
			}
			
			game.getSave().getSaveButtons1().writeSave(fileName, fileNum);
			game.getSave().getSaveButtons2().writeSave(fileName, fileNum);
			game.getSave().getSaveButtons3().writeSave(fileName, fileNum);
		
	
		}
	}

	public static void setNumName(String name, int num)  {
		fileName = name;
		fileNum = num;
		System.out.println(fileName);
		System.out.println(fileNum);
	}

   



}