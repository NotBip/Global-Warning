/**
***********************************************
* @Author : Nusayba Hamou
* @Originally made : 4 JAN. 2024
* @Last Modified: 21 JAN, 2024
* @Description: Made for all buttons with the same button properties (hover, bounds, etc.)
***********************************************
*/

package UserInterface;

import java.awt.Rectangle;

public class Button {

	// variables
	protected int x, y, width, height;
	protected Rectangle bounds;
	private boolean mouseOver, mousePressed, mouseReleased;

	// constructor
	public Button(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		createBounds();
	}

	/**
	 * @Method Name: createBounds
	 * @referenced: https://github.com/KaarinGaming/PlatformerTutorial
	 * @author KaarinGaming
	 * @author Nusayba Hamou
	 * @since 4 JAN 2024
	 * @Description: creates bounds for buttons
	 * @Parameters: N/A
	 * @returns:N/A
	 * @Dependencies: N/A
	 * @Throws/Exceptions: N/A
	 **/

	private void createBounds() {
		bounds = new Rectangle(x, y, width, height);
	}

	/**
	 * @Method Name: resetButtons
	 * @author Nusayba Hamou
	 * @since 4 JAN 2024
	 * @Description: resets buttons relative to mouse positions
	 * @Parameters: N/A
	 * @returns:N/A
	 * @Dependencies: N/A
	 * @Throws/Exceptions: N/A
	 **/
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
