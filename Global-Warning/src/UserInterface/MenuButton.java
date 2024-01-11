package UserInterface;

import static Utilities.Atlas.getSpriteAtlas;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import GameStates.*;
import static Utilities.Atlas.MENUBUTTON_ATLAS;
import static Utilities.Constants.Buttons.*;

public class MenuButton extends Button {

	// variables
	private int xPos, yPos, index, rowIndex;
	private GameState state;
	private BufferedImage[] imgs;

	/**
	 * Constructor to create button for menu
	 * 
	 * @author Nusayba Hamou
	 * @since January 1, 2024
	 */

	public MenuButton(int xPos, int yPos, int width, int height, int rowIndex, GameState state) {
		super(xPos, yPos, width, height);
		this.xPos = xPos;
		this.yPos = yPos;
		this.state = state;
		this.rowIndex = rowIndex;
		loadImgs();
		getBounds();
	}

	/**
	 * loads animations for menu buttons
	 * 
	 * @referenced: Kaarin Gaming
	 * @author Nusayba Hamou
	 * @since January 1, 2024
	 */

	private void loadImgs() {
		imgs = new BufferedImage[3];
		BufferedImage temp = getSpriteAtlas(MENUBUTTON_ATLAS);
		for (int i = 0; i < imgs.length; i++)
			imgs[i] = temp.getSubimage(i * B_WIDTH, rowIndex * B_HEIGHT, B_WIDTH, B_HEIGHT);
	}

	/**
	 * Draws menu button
	 * 
	 * @author Nusayba Hamou
	 * @since January 1, 2024
	 */

	public void draw(Graphics g) {
		g.drawImage(imgs[index], xPos, yPos, B_WIDTH, B_HEIGHT, null);
	}

	/**
	 * Updates menu button animations based on mouse position
	 * 
	 * @referenced: Kaarin Gaming
	 * @author Nusayba Hamou
	 * @since January 1, 2024
	 */

	public void update() {
		index = 0;
		if (getMouseOver())
			index = 1;
		if (getMousePressed())
			index = 2;
	}

	/**
	 * Applies gamestate if menu button is clicked
	 * 
	 * @referenced: Kaarin Gaming
	 * @author Nusayba Hamou
	 * @since January 1, 2024
	 */

	public void applyGamestate() {
		GameState.currentState = state;
	}

}
