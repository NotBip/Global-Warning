package UserInterface;

import GameStates.GameState;
import GameStates.Playing;
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

	public static Save save1 = new Save(0,(int)Playing.fireRateWeapon1,(int)Playing.fireRateWeapon2,0,0,0,0,0,1);
    public static Save save2 = new Save(0,(int)Playing.fireRateWeapon1,(int)Playing.fireRateWeapon2,0,0,0,0,0,2);
    public static Save save3 = new Save(0,(int)Playing.fireRateWeapon1,(int)Playing.fireRateWeapon2,0,0,0,0,0,3);

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

	public void applySave()throws IOException {
		//writeNewBinFile(fileName, fileNum);
		readNewBinFile(fileName, fileNum);
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
            case 1: save1.readRec(raf);break;
            case 2:save2.readRec(raf);break;
            case 3:save3.readRec(raf);break;
            default:
                break;
        }
		
		raf.close();
	} // end readNewBinFile

}
