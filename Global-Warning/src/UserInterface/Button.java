package UserInterface;

import java.awt.Rectangle;

public class Button {

	// variables
	protected int x, y, width, height;
	protected Rectangle bounds;
	private boolean mouseOver, mousePressed, mouseReleased;

	/**
	 * Constructor to create a button
	 * 
	 * @author Nusayba Hamou
	 * @since January 4, 2024
	 */

	public Button(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		createBounds();
	}

	/**
	 * Create bounds
	 * 
	 * @referenced: Kaarin Gaming
	 * @author Nusayba Hamou
	 * @since January 4, 2024
	 */

	private void createBounds() {
		bounds = new Rectangle(x, y, width, height);
	}

	/**
	 * Resets button animations to unclicked
	 * 
	 * @author Nusayba Hamou
	 * @since January 4, 2024
	 */

	public void resetButtons() {
		mouseOver = false;
		mousePressed = false;
	}

	/* Getters and Setters */

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public Rectangle getBounds() {
		return bounds;
	}

	public void setBounds(Rectangle bounds) {
		this.bounds = bounds;
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

	public boolean getMouseReleased() {
		return mouseReleased;
	}

	public void setMouseReleased(boolean mouseReleased) {
		this.mouseReleased = mouseReleased;
	}

	

}
