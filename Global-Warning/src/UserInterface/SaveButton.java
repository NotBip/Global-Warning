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
import static Utilities.Constants.Buttons.*;

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
	//0,save1.getHealth(),(int)Playing.fireRateWeapon1,(int)Playing.fireRateWeapon2,0,0,0,0,0,1

	/**
	 * Constructor to create button for save slots
	 * 
	 * @author Nusayba Hamou
	 * @since January 1, 2024
	 */

	public SaveButton(int xPos, int yPos, int width, int height, int rowIndex, GameState state, String filename, int filenum) {
		super(xPos, yPos, width, height);

		this.xPos = xPos;
		this.yPos = yPos;
		this.rowIndex = rowIndex;
		this.state = state;

		//saving
		this.fileName = filename;
		this.fileNum = filenum;

		loadImgs();
		getBounds();
	}

	/**
	 * loads animations for save slots
	 * 
	 * @author Nusayba Hamou
	 * @since January 4, 2024
	 */

	private void loadImgs() {
		imgs = new BufferedImage[2];
		BufferedImage temp = getSpriteAtlas(SAVEBUTTON_ATLAS);
		for (int i = 0; i < imgs.length; i++)
			imgs[i] = temp.getSubimage(i * SAVE_B_WIDTH, rowIndex * SAVE_B_HEIGHT, SAVE_B_WIDTH, SAVE_B_HEIGHT);
	}

	/**
	 * Draws specific sprite for save slot
	 * 
	 * @author Nusayba Hamou
	 * @since January 4, 2024
	 */

	public void draw(Graphics g) {
		g.drawImage(imgs[index], xPos, yPos, SAVE_B_WIDTH, SAVE_B_HEIGHT, null);
	}

	/**
	 * Updates button sprite based on mouse postion
	 * 
	 * @author Nusayba Hamou
	 * @since January 4, 2024
	 */

	public void update() {
		index = 0;
		if (getMouseOver())
			index = 1;
	}

	/**
	 * Applies gamestate if button is clicked
	 * 
	 * @author Nusayba Hamou
	 * @throws IOException 
	 * @since January 3, 2024
	 */

	public void applyGamestate() {
		GameState.currentState = state;
	}



	public void readSave(Playing playing)throws IOException {

		readNewBinFile(fileName, fileNum);
		loadSave(playing);
		
		
	}

	public static void writeSave(String name, int num)throws IOException {
		
		writeNewBinFile(name, num);
		
	}

	public static void writeNewBinFile(String filename, int filenum) throws IOException{
	    RandomAccessFile raf = new RandomAccessFile(filename,"rw");
        switch (filenum) {
            case 1: save1.writeRec(raf);break;
            case 2:save2.writeRec(raf);break;
            case 3:save3.writeRec(raf);break;
            default:
                break;
        }
		raf.close();
	} // end writeNewBinFile

	public static void readNewBinFile(String filename, int filenum) throws IOException{
		RandomAccessFile raf = new RandomAccessFile(filename,"rw");

        switch (filenum) {
            case 1:save1.readRec(raf);
			System.out.println("Gun Index: "+ save1.getHold());break;
            case 2:save2.readRec(raf);
			System.out.println("Gun Index: "+ save2.getHold());break;
            case 3:save3.readRec(raf);
			System.out.println("Gun Index: "+ save3.getHold());break;
            default:
                break;
        }
		
		raf.close();
	} // end readNewBinFile

	public void loadSave(Playing playing){
        switch (fileNum) {
			case 1:
			System.out.println("save 1: loaded");
			playing.player.currentHealth = save1.getHealth();
			Playing.gunIndex = save1.getHold();
			Playing.fireRateWeapon1 = save1.getCooldown1();
           	Playing.fireRateWeapon1 = save1.getCooldown2();
			LevelManager.lvlIndex = save1.getLevel();

			if (save1.getHealth() == 0){
				playing.player.currentHealth = playing.player.maxHealth;
			}

				break;
			case 2:
			System.out.println("save 2: loaded");
			playing.player.currentHealth = save2.getHealth();
			Playing.gunIndex = save2.getHold();
			Playing.fireRateWeapon1 = save2.getCooldown1();
            Playing.fireRateWeapon1 = save2.getCooldown2();
			LevelManager.lvlIndex = save2.getLevel();

			if (save2.getHealth() == 0){
				playing.player.currentHealth = playing.player.maxHealth;
			}
				break;
			case 3:
			System.out.println("save 3: loaded");
			playing.player.currentHealth = save3.getHealth();
			Playing.gunIndex = save3.getHold();
			Playing.fireRateWeapon1 = save3.getCooldown1();
            Playing.fireRateWeapon1 = save3.getCooldown2();
			LevelManager.lvlIndex = save3.getLevel();

			if (save3.getHealth() == 0){
				playing.player.currentHealth = playing.player.maxHealth;
			}

				break;
		
			default:
				break;
        }


			playing.player.changeHealth(0);
			Playing.weapon.getImage();
			playing.loadNextLevel(0);
			
    }

	public String getFileName(){
		return fileName;
	}

	public  int getFileNum(){
		return fileNum;
	}

}
