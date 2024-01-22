/**
***********************************************
* @Author : Nusayba Hamou
* @Originally made : 18 JAN. 2024
* @Last Modified: 21 JAN, 2024
* @Description: Buttons found in the save state made to reset save files by replacing them with empty saves
***********************************************
*/

package UserInterface;

import static Utilities.Atlas.getSpriteAtlas;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import GameStates.Playing;
import Objects.Chest;
import Objects.Saving.Save;
import static Utilities.Atlas.RESETBUTTON_ATLAS;
import static Utilities.Constants.Buttons.*;

public class ResetSaveButton extends Button {

	// variables
	private int xPos, yPos, index;
	private BufferedImage[] imgs;
	public int fileNum;
	public String fileName;

	// constructor
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
	 * @Method Name: loadImgs
	 * @author Nusayba Hamou
	 * @since 18 JAN 2024
	 * @Description: loads images for reset save buttons
	 * @Parameters: N/A
	 * @returns:N/A
	 * @Dependencies: Atlas, Constants
	 * @Throws/Exceptions: N/A
	 **/

	private void loadImgs() {
		imgs = new BufferedImage[2];
		BufferedImage temp = getSpriteAtlas(RESETBUTTON_ATLAS);
		for (int i = 0; i < imgs.length; i++)
			imgs[i] = temp.getSubimage(i * RESET_B_WIDTH, RESET_B_HEIGHT, RESET_B_WIDTH, RESET_B_HEIGHT);
	}

	/**
	 * @Method Name: draw
	 * @author Nusayba Hamou
	 * @since 18 JAN 2024
	 * @Description: draws img for reset save buttons
	 * @Parameters: Graphics g
	 * @returns:N/A
	 * @Dependencies: Constants
	 * @Throws/Exceptions: N/A
	 **/

	public void draw(Graphics g) {
		g.drawImage(imgs[index], xPos, yPos, RESET_B_WIDTH, RESET_B_HEIGHT, null);
	}

	/**
	 * @Method Name: update
	 * @author Nusayba Hamou
	 * @since 18 JAN 2024
	 * @Description: updates button based on mouse position
	 * @Parameters: N/A
	 * @returns:N/A
	 * @Dependencies: Button
	 * @Throws/Exceptions: N/A
	 **/

	public void update() {
		index = 0;
		if (getMouseOver()) {
			index = 1;
		}

	}

	/**
	 * @Method Name: update
	 * @author Nusayba Hamou
	 * @since 18 JAN 2024
	 * @Description: resets save file by replacing it with an empty file
	 * @Parameters: N/A
	 * @returns:N/A
	 * @Dependencies: SaveButton, Playing, Chest
	 * @Throws/Exceptions: IO Exception
	 **/

	public void resetSave() throws IOException {
		System.out.println(fileNum);
		if (Playing.levelManager.getCurrentLevel().getCheckpoint() != null) {
			Playing.levelManager.getCurrentLevel().getCheckpoint().resetReached();
		}

		if (Playing.levelManager.getCurrentLevel().getChest() != null) {
			for (Chest chest : Playing.levelManager.getCurrentLevel().getChest()) {
				chest.resetChests();
			}

		}

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
