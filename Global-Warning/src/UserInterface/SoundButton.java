/**
***********************************************
* @Author : Nusayba Hamou
* @Originally made : 6 JAN, 2024
* @Last Modified: 21 JAN, 2024
* @Description: Buttons found in the options state for volume adjustment
***********************************************
*/

package UserInterface;

import static Utilities.Atlas.getSpriteAtlas;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import static Utilities.Atlas.SOUNDBUTTON_ATLAS;
import static Utilities.Constants.Buttons.*;

public class SoundButton extends Button {

	// variables
	private int xPos, yPos, index, rowIndex;
	private BufferedImage[] imgs;

	// constructor
	public SoundButton(int xPos, int yPos, int width, int height, int rowIndex) {
		super(xPos, yPos, width, height);

		this.xPos = xPos;
		this.yPos = yPos;
		this.rowIndex = rowIndex;

		loadImgs();
		getBounds();
	}

	/**
	 * @Method Name: loadImgs
	 * @author Nusayba Hamou
	 * @since 5 JAN 2024
	 * @Description: loads images for sound buttons
	 * @Parameters: N/A
	 * @returns:N/A
	 * @Dependencies: Atlas, Constants
	 * @Throws/Exceptions: N/A
	 **/

	private void loadImgs() {
		imgs = new BufferedImage[3];
		BufferedImage temp = getSpriteAtlas(SOUNDBUTTON_ATLAS);
		for (int i = 0; i < imgs.length; i++)
			imgs[i] = temp.getSubimage(i * SOUND_B_WIDTH, rowIndex * SOUND_B_HEIGHT, SOUND_B_WIDTH, SOUND_B_HEIGHT);
	}

	/**
	 * @Method Name: draw
	 * @author Nusayba Hamou
	 * @since 5 JAN 2024
	 * @Description: draws img for sound buttons
	 * @Parameters: Graphics g
	 * @returns:N/A
	 * @Dependencies: Constants
	 * @Throws/Exceptions: N/A
	 **/
	public void draw(Graphics g) {
		g.drawImage(imgs[index], xPos, yPos, SOUND_B_WIDTH, SOUND_B_HEIGHT, null);
	}

	/**
	 * @Method Name: update
	 * @author Nusayba Hamou
	 * @since 5 JAN 2024
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
		if (getMousePressed()) {
			index = 2;
		}
	}

}
