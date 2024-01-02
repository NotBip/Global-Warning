package UserInterface;
import static Utilities.Atlas.getSpriteAtlas;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import GameStates.*;
import static Utilities.Atlas.MENUBUTTON_ATLAS;
import static Utilities.Constants.Buttons.*;


public class MenuButton {
    private int xPos, yPos, rowIndex, index;
	//private int xOffsetCenter = B_WIDTH / 2;
	private GameState state;
	private BufferedImage[] imgs;
	private boolean mouseOver, mousePressed;
	private Rectangle bounds;

	public MenuButton(int xPos, int yPos, int rowIndex, GameState state) {
		this.xPos = xPos;
		this.yPos = yPos;
		this.rowIndex = rowIndex;
		this.state = state;
		loadImgs();
		inButtonBounds();
	}

	private void inButtonBounds() {
		bounds = new Rectangle(xPos , yPos, B_WIDTH, B_HEIGHT);

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
		if (mouseOver)
			index = 1;
		if (mousePressed)
			index = 2;
	}

	public boolean getMouseOver() {
		return mouseOver;
	}

	public void setMouseOver(boolean mouseOver) {
		this.mouseOver = mouseOver;
	}

	public boolean getMousePressed() {
		return mousePressed;
	}

	public void setMousePressed(boolean mousePressed) {
		this.mousePressed = mousePressed;
	}

	public Rectangle getBounds() {
		return bounds;
	}

	public void applyGamestate() {
		GameState.currentState = state;
	}

	public void resetButtons() {
		mouseOver = false;
		mousePressed = false;
	}

}

