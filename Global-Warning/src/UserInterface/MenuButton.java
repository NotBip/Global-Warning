package UserInterface;
import static Utilities.Atlas.getSpriteAtlas;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import GameStates.*;
import static Utilities.Atlas.MENUBUTTON_ATLAS;
import static Utilities.Constants.Buttons.*;


public class MenuButton extends Button {
    private int xPos, yPos, width, height, index, rowIndex;
	//private int xOffsetCenter = B_WIDTH / 2;
	private GameState state;
	private BufferedImage[] imgs;

	public MenuButton(int xPos, int yPos, int width, int height, int rowIndex, GameState state) {
		super (xPos,  yPos,width, height);
		this.xPos = xPos;
		this.yPos = yPos;
		this.state = state;
		this.rowIndex = rowIndex;
		loadImgs();
		getBounds();
	}

	private void loadImgs() {
		imgs = new BufferedImage[3];
		BufferedImage temp = getSpriteAtlas(MENUBUTTON_ATLAS); 
		for (int i = 0; i < imgs.length; i++)
			imgs[i] = temp.getSubimage(i * B_WIDTH, rowIndex * B_HEIGHT, B_WIDTH, B_HEIGHT);
	}

	public void draw(Graphics g) {
		g.drawImage(imgs[index], xPos , yPos, B_WIDTH, B_HEIGHT, null);
		//g.drawRect(xPos , yPos, B_WIDTH, B_HEIGHT);
	}

	public void update() {
		index = 0;
		if (getMouseOver())
			index = 1;
		if (getMousePressed())
			index = 2;
	}

	public void applyGamestate() {
		GameState.currentState = state;
	}



}

