/**
***********************************************
* @Author : Nusayba Hamou
* @Originally made : 1 JAN, 2024
* @Last Modified: 21 JAN, 2024
* @Description: Buttons found in the save state to load save files
***********************************************
*/

package UserInterface;

import GameStates.GameState;
import GameStates.Playing;
import Levels.LevelManager;

import Objects.Saving.Save;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.RandomAccessFile;

import static Utilities.Atlas.*;

import Objects.Chest;
import static Utilities.Constants.Buttons.*;
import static GameStates.Playing.*;

public class SaveButton extends Button {

	// variables
	private int xPos, yPos, rowIndex, index;
	private GameState state;
	private BufferedImage[] imgs;

	public String fileName;
	public int fileNum;

	public static Save save1 = new Save();
	public static Save save2 = new Save();
	public static Save save3 = new Save();

	//constructor
	public SaveButton(int xPos, int yPos, int width, int height, int rowIndex, GameState state, String filename,
			int filenum) {
		super(xPos, yPos, width, height);

		this.xPos = xPos;
		this.yPos = yPos;
		this.rowIndex = rowIndex;
		this.state = state;

		// saving
		this.fileName = filename;
		this.fileNum = filenum;

		loadImgs();
		getBounds();
	}

	/**
	 * @Method Name: loadImgs
	 * @author Nusayba Hamou
	 * @since 4 JAN 2024
	 * @Description: loads images for save buttons
	 * @Parameters: N/A
	 * @returns:N/A
	 * @Dependencies: Atlas, Constants
	 * @Throws/Exceptions: N/A
	 **/

	private void loadImgs() {
		imgs = new BufferedImage[2];
		BufferedImage temp = getSpriteAtlas(SAVEBUTTON_ATLAS);
		for (int i = 0; i < imgs.length; i++)
			imgs[i] = temp.getSubimage(i * SAVE_B_WIDTH, rowIndex * SAVE_B_HEIGHT, SAVE_B_WIDTH, SAVE_B_HEIGHT);
	}

	/**
	 * @Method Name: draw
	 * @author Nusayba Hamou
	 * @since 4 JAN 2024
	 * @Description: draws img for save slot
	 * @Parameters: Graphics g
	 * @returns:N/A
	 * @Dependencies: Constants
	 * @Throws/Exceptions: N/A
	 **/

	public void draw(Graphics g) {
		g.drawImage(imgs[index], xPos, yPos, SAVE_B_WIDTH, SAVE_B_HEIGHT, null);
	}

	/**
	 * @Method Name: update
	 * @author Nusayba Hamou
	 * @since 4 JAN 2024
	 * @Description: updates button based on mouse position
	 * @Parameters: N/A
	 * @returns:N/A
	 * @Dependencies: Button
	 * @Throws/Exceptions: N/A
	 **/

	public void update() {
		index = 0;
		if (getMouseOver())
			index = 1;
	}

	/**
	 * @Method Name: applyGamestate
	 * @author Nusayba Hamou
	 * @since 3 JAN 2024
	 * @Description: applies the current state to the gamestate
	 * @Parameters: N/A
	 * @returns:N/A
	 * @Dependencies: GameState
	 * @Throws/Exceptions: N/A
	 **/
	public void applyGamestate() {
		GameState.currentState = state;
	}

	/**
	 * @Method Name: readSave
	 * @author Nusayba Hamou
	 * @since 5 JAN 2024
	 * @Description: reads the save file and loads the save
	 * @Parameters: Playing
	 * @returns:N/A
	 * @Dependencies: N/A
	 * @Throws/Exceptions: IO Exception
	 **/

	public void readSave(Playing playing) throws IOException {

		readNewBinFile(fileName, fileNum);
		loadSave(playing);

	}

	/**
	 * @Method Name: writeSave
	 * @author Nusayba Hamou
	 * @since 5 JAN 2024
	 * @Description: reads the save file and loads the save
	 * @Parameters: file name and #
	 * @returns:N/A
	 * @Dependencies: N/A
	 * @Throws/Exceptions: IO Exception
	 **/

	public static void writeSave(String name, int num) throws IOException {

		writeNewBinFile(name, num);

	}

	/**
	 * @Method Name: writeNewBinFile
	 * @author Nusayba Hamou
	 * @since 5 JAN 2024
	 * @Description: writes the save records to their respective binary files
	 * @Parameters: file name and #
	 * @returns:N/A
	 * @Dependencies: Save
	 * @Throws/Exceptions: IO Exception
	 **/

	public static void writeNewBinFile(String filename, int filenum) throws IOException {
		RandomAccessFile raf = new RandomAccessFile(filename, "rw");
		switch (filenum) {
			case 1:
				save1.writeRec(raf);
				break;
			case 2:
				save2.writeRec(raf);
				break;
			case 3:
				save3.writeRec(raf);
				break;
			default:
				break;
		}
		raf.close();
	} // end writeNewBinFile

	/**
	 * @Method Name: readNewBinFile
	 * @author Nusayba Hamou
	 * @since 5 JAN 2024
	 * @Description: reads the save records from their respective binary files
	 * @Parameters: file name and #
	 * @returns:N/A
	 * @Dependencies: Save
	 * @Throws/Exceptions: IO Exception
	 **/

	public static void readNewBinFile(String filename, int filenum) throws IOException {
		RandomAccessFile raf = new RandomAccessFile(filename, "rw");

		switch (filenum) {
			case 1:
				save1.readRec(raf);
				System.out.println("Gun Index: " + save1.getHold());
				break;
			case 2:
				save2.readRec(raf);
				System.out.println("Gun Index: " + save2.getHold());
				break;
			case 3:
				save3.readRec(raf);
				System.out.println("Gun Index: " + save3.getHold());
				break;
			default:
				break;
		}

		raf.close();
	} // end readNewBinFile

	/**
	 * @Method Name: loadSave
	 * @author Nusayba Hamou
	 * @since 15 JAN 2024
	 * @Description: sets all necessary elements to save file contents
	 * @Parameters: Playing
	 * @returns:N/A
	 * @Dependencies: Save, LevelManager, Chest, Playing
	 * @Throws/Exceptions: N/A
	 **/

	public void loadSave(Playing playing) {
		if (levelManager.getCurrentLevel().getCheckpoint() != null) {
			levelManager.getCurrentLevel().getCheckpoint().resetReached();
		}
		if (levelManager.getCurrentLevel().getChest() != null) {
			for (Chest chest : levelManager.getCurrentLevel().getChest()) {
				chest.resetChests();
			}

		}

		switch (fileNum) {
			case 1:
				System.out.println("save 1: loaded");
				playing.player.changeHealth(save1.getHealth());
				Playing.gunIndex = save1.getHold();
				Playing.fireRateWeapon1 = save1.getCooldown1();
				Playing.fireRateWeapon2 = save1.getCooldown2();
				LevelManager.lvlIndex = save1.getLevel();

				// get items
				playing.player.key.setQuantity(save1.getKey());
				playing.player.heal.setQuantity(save1.getPotion());
				playing.player.bomb.setQuantity(save1.getBomb());
				playing.player.upgrade.setQuantity(save1.getGem());

				if (save1.getHealth() == 0) {
					playing.player.changeHealth(playing.player.maxHealth);
				}

				break;
			case 2:
				System.out.println("save 2: loaded");
				playing.player.changeHealth(save2.getHealth());
				Playing.gunIndex = save2.getHold();
				Playing.fireRateWeapon1 = save2.getCooldown1();
				Playing.fireRateWeapon2 = save2.getCooldown2();
				LevelManager.lvlIndex = save2.getLevel();

				// get items
				playing.player.key.setQuantity(save2.getKey());
				playing.player.heal.setQuantity(save2.getPotion());
				playing.player.bomb.setQuantity(save2.getBomb());
				playing.player.upgrade.setQuantity(save2.getGem());

				if (save2.getHealth() == 0) {
					playing.player.changeHealth(playing.player.maxHealth);
				}
				break;
			case 3:
				System.out.println("save 3: loaded");
				playing.player.changeHealth(save3.getHealth());
				Playing.gunIndex = save3.getHold();
				Playing.fireRateWeapon1 = save3.getCooldown1();
				Playing.fireRateWeapon2 = save3.getCooldown2();
				LevelManager.lvlIndex = save3.getLevel();

				// get items
				playing.player.key.setQuantity(save3.getKey());
				playing.player.heal.setQuantity(save3.getPotion());
				playing.player.bomb.setQuantity(save3.getBomb());
				playing.player.upgrade.setQuantity(save3.getGem());

				if (save3.getHealth() == 0) {
					playing.player.changeHealth(playing.player.maxHealth);
				}

				break;

			default:
				break;
		}

		playing.player.changeHealth(0);
		Playing.weapon.getImage();
		playing.loadNextLevel(0);

	}

	/*getters */

	public String getFileName() {
		return fileName;
	}

	public int getFileNum() {
		return fileNum;
	}

}
