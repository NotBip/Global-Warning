package UserInterface;

import GameStates.GameState;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.text.Bidi;

import GameStates.*;
import static Utilities.Atlas.*;
import static Utilities.Constants.Buttons.*;

public class SaveButton extends MenuButton{

 private int xPos, yPos, rowIndex, index;
	private GameState state;
	private BufferedImage[] imgs;
	//private BufferedImage img;
	private boolean mouseOver, mousePressed;
	private Rectangle bounds;

	public SaveButton(int xPos, int yPos, int rowIndex, GameState state) {
		super (xPos,  yPos,rowIndex, state);
		this.xPos = xPos;
		this.yPos = yPos;
		this.rowIndex = rowIndex;
		this.state = state;
		loadImgs();
		inButtonBounds();
	}

	private void inButtonBounds() {
		bounds = new Rectangle(xPos-70 , yPos+30, B_WIDTH+100, B_HEIGHT+25);

	}

	private void loadImgs() {
		imgs = new BufferedImage[2];
		BufferedImage temp = getSpriteAtlas(MENUSAVEUNCLICK_ATLAS); 
		//img = temp;
		for (int i = 0; i < imgs.length; i++)
			imgs[i] = temp.getSubimage(i * B_WIDTH, rowIndex * B_HEIGHT, B_WIDTH+140, B_HEIGHT-13);
	}

	public void draw(Graphics g) {
		g.drawImage(imgs[index], xPos-70, yPos+30, B_WIDTH+100, B_HEIGHT+25, null);
		g.drawRect(xPos-70 , yPos+30, B_WIDTH+100, B_HEIGHT+25);
		//g.drawImage(img, xPos ,yPos, 200, 200, null);
	}

	public void update() {
		if (mouseOver)
			imgs[0] = getSpriteAtlas(MENUSAVECLICK_ATLAS);
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

