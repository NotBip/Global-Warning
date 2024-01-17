package UserInterface;

import static Utilities.Atlas.getSpriteAtlas;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import GameStates.GameState;
import Objects.Saving.Save;

import static Utilities.Atlas.RESETBUTTON_ATLAS;
import static Utilities.Atlas.SOUNDBUTTON_ATLAS;
import static Utilities.Constants.Buttons.*;

public class ResetSaveButton extends Button {

	// variables
	private int xPos, yPos, index;
	private BufferedImage[] imgs;
	public  int fileNum;
	public String fileName;

	/**
	 * Constructor to create sound button
	 * 
	 * @author Nusayba Hamou
	 * @since January 6, 2024
	 */

	public ResetSaveButton(int xPos, int yPos, int width, int height, String filename, int filenum) {
		super(xPos, yPos, width, height);

		this.xPos = xPos;
		this.yPos = yPos;
		this.fileNum = filenum;
		this.fileName = filename;

		loadImgs();
		getBounds();
	}

	/**
	 * loads animations for button
	 * 
	 * @author Nusayba Hamou
	 * @since January 5, 2024
	 */

	private void loadImgs() {
		imgs = new BufferedImage[2];
		BufferedImage temp = getSpriteAtlas(RESETBUTTON_ATLAS);
		for (int i = 0; i < imgs.length; i++)
			imgs[i] = temp.getSubimage(i * RESET_B_WIDTH, RESET_B_HEIGHT, RESET_B_WIDTH, RESET_B_HEIGHT);
	}

	/**
	 * Draws specific sprite for button
	 * 
	 * @author Nusayba Hamou
	 * @since January 5, 2024
	 */

	public void draw(Graphics g) {
		g.drawImage(imgs[index], xPos, yPos, RESET_B_WIDTH, RESET_B_HEIGHT, null);
	}

	/**
	 * Updates button sprite based on mouse postion
	 * 
	 * @author Nusayba Hamou
	 * @since January 5, 2024
	 */

	public void update() {
		index = 0;
		if (getMouseOver()){
            index = 1;
        }
			
            
	}

	public void resetSave() throws IOException{
		System.out.println(fileNum);
		switch (fileNum) {
			case 1:
			SaveButton.save1 = new Save();
			SaveButton.writeNewBinFile(fileName, fileNum);

				break;
			case 2:
			SaveButton.save2 = new Save();
			SaveButton.writeNewBinFile(fileName, fileNum);
				
				break;
			case 3:
			SaveButton.save3 = new Save();
			SaveButton.writeNewBinFile(fileName, fileNum);
				
				break;
		
		
			default:
				break;
		}

	}

}
