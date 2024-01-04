package UserInterface;

import GameStates.GameState;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.text.Bidi;

import GameStates.*;
import static Utilities.Atlas.*;
import static Utilities.Constants.Buttons.*;

public class SaveButton extends Button {

 private int xPos, yPos, rowIndex, index;
	private GameState state;
	private BufferedImage[] imgs;
	//private BufferedImage img;

	public SaveButton(int xPos, int yPos, int width, int height, int rowIndex, GameState state) {
		super (xPos, yPos,width, height);

		this.xPos = xPos;
		this.yPos = yPos;
		this.rowIndex = rowIndex;
		this.state = state;

		loadImgs();
		getBounds();
	}

	private void loadImgs() {
		imgs = new BufferedImage[3];
		BufferedImage temp = getSpriteAtlas(SAVEBUTTON_ATLAS); 
		for (int i = 0; i <2; i++)
			imgs[i] = temp.getSubimage(i*SAVE_B_WIDTH, rowIndex * SAVE_B_HEIGHT, SAVE_B_WIDTH, SAVE_B_HEIGHT);
	}

	public void draw(Graphics g) {
		g.drawImage(imgs[index], xPos, yPos, SAVE_B_WIDTH, SAVE_B_HEIGHT, null);
	}

	public void update() {
		index = 0;
		if (getMouseOver())
			index = 1;
	}

	public void applyGamestate() {
		GameState.currentState = state;
	}

}

