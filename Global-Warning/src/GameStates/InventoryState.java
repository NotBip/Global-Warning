/**
***********************************************
* @Author : Nusayba Hamou
* @Originally made : 5 JAN, 2024
* @Last Modified: 22 JAN, 2024
* @Description: Inventory state for when player opens inventory
***********************************************
*/

package GameStates;

import java.awt.Color;
import java.awt.Graphics;
import static Utilities.Constants.GAME_WIDTH;
import static Utilities.Constants.GAME_HEIGHT;
import Items.HealPotion;
import Items.UpgradeGem;

import UserInterface.InventorySlot;

import java.awt.event.*;
import java.awt.image.BufferedImage;
import static Utilities.Constants.Buttons.*;

import static Utilities.Atlas.*;

public class InventoryState {
	// variables
	private int position = 100;
	private Playing playing;
	private HealPotion heal;
	private UpgradeGem upgrade;

	private BufferedImage backgroundImg = getSpriteAtlas(INVENTORY_ATLAS);
	private BufferedImage[] loadImgs;
	private InventorySlot[] slots = new InventorySlot[6];

	// constructor
	public InventoryState(Playing playing) {
		this.playing = playing;
		makeSlots();

	}

	/**
	 * @Method Name: makeSlots
	 * @author Nusayba Hamou
	 * @since 5 JAN 2024
	 * @Description: makes slots for the state
	 * @Parameters: N/A
	 * @returns:N/A
	 * @Dependencies: InventorySlot
	 * @Throws/Exceptions: N/A
	 **/
	public void makeSlots() {
		// first slot
		slots[0] = new InventorySlot(GAME_WIDTH / 2, GAME_HEIGHT / 2 - position, INVENTORY_B_WIDTH, INVENTORY_B_HEIGHT,
				1, playing);
		// second slot
		slots[1] = new InventorySlot(GAME_WIDTH / 2 + position, GAME_HEIGHT / 2 - position, INVENTORY_B_WIDTH,
				INVENTORY_B_HEIGHT, 2, playing);

		// 3rd slot (under first slot)
		slots[2] = new InventorySlot(GAME_WIDTH / 2, GAME_HEIGHT / 2, INVENTORY_B_WIDTH, INVENTORY_B_HEIGHT, 3,
				playing);
		// 4th slot
		slots[3] = new InventorySlot(GAME_WIDTH / 2 + position, GAME_HEIGHT / 2, INVENTORY_B_WIDTH, INVENTORY_B_HEIGHT,
				4, playing);

		// 5th slot (final row)
		slots[4] = new InventorySlot(GAME_WIDTH / 2, GAME_HEIGHT / 2 + position, INVENTORY_B_WIDTH, INVENTORY_B_HEIGHT,
				5, playing);
		// 6th slot
		slots[5] = new InventorySlot(GAME_WIDTH / 2 + position, GAME_HEIGHT / 2 + position, INVENTORY_B_WIDTH,
				INVENTORY_B_HEIGHT, 6, playing);

	}

	/**
	 * @Method Name: update
	 * @author Nusayba Hamou
	 * @since 5 JAN 2024
	 * @Description: updates all slots
	 * @Parameters: N/A
	 * @returns:N/A
	 * @Dependencies: Button
	 * @Throws/Exceptions: N/A
	 **/

	public void update() {
		for (InventorySlot slot : slots)
			slot.update();
	}

	/**
	 * @Method Name: draw
	 * @author Nusayba Hamou
	 * @since 5 JAN 2024
	 * @Description: draws inventory overlay screen with slots
	 * @Parameters: Graphics g
	 * @returns:N/A
	 * @Dependencies: Constants, Playing, Button
	 * @Throws/Exceptions: N/A
	 **/

	public void draw(Graphics g) {
		// dark background
		g.setColor(new Color(0, 0, 0, 90));
		g.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);

		// color for other
		g.setColor(Color.BLACK);

		g.drawImage(backgroundImg, GAME_WIDTH / 2 - 250, GAME_HEIGHT / 5, 500, 420, null);
		for (InventorySlot slot : slots)
			slot.draw(g);

		drawHealth(g);
	}

	public void drawHealth(Graphics g) {
		g.setColor(Color.red);
		g.fillRect(400, 500, playing.player.getHealthWidth() / 2, playing.player.getHealthHeight() / 2);
		g.setColor(Color.green);
		g.fillRect(400, 500, playing.player.getHealthLength() / 2, playing.player.getHealthHeight() / 2);
		g.setColor(Color.black);
		g.drawRect(400, 500, playing.player.getHealthWidth() / 2, playing.player.getHealthHeight() / 2);

	}

	/**
	 * @Method Name: resetButtons
	 * @author Nusayba Hamou
	 * @since 5 JAN 2024
	 * @Description: resets all slots (called buttons cause it extends button class)
	 * @Parameters: N/A
	 * @returns:N/A
	 * @Dependencies: Button
	 * @Throws/Exceptions: N/A
	 **/

	private void resetButtons() {
		for (InventorySlot slot : slots)
			slot.resetButtons();

	}

	/**
	 * @Method Name: isIn
	 * @author Nusayba Hamou
	 * @since 5 JAN 2024
	 * @Description: checks if mouse is in button bounds
	 * @Parameters: MouseEvent e, InventorySlot b
	 * @returns: true or false based on bounds
	 * @Dependencies: Button
	 * @Throws/Exceptions: N/A
	 **/

	private boolean isIn(MouseEvent e, InventorySlot b) {
		return b.getBounds().contains(e.getX(), e.getY());
	}

	/**
	 * @Method Name: mouseMoved
	 * @author Nusayba Hamou
	 * @since 5 JAN 2024
	 * @Description: Checks if mouse position overlaps bounds (cursor on button)
	 * @Parameters: MouseEvent e
	 * @returns: N/A
	 * @Dependencies: Button
	 * @Throws/Exceptions: N/A
	 **/

	public void mouseMoved(MouseEvent e) {
		for (InventorySlot slot : slots)
			slot.setMouseOver(false);

		for (InventorySlot slot : slots) {
			if (isIn(e, slot)) {
				slot.setMouseOver(true);
			}
		}
	}

	public void mouseClicked(MouseEvent e) {

	}

	public void mouseDragged(MouseEvent e) {

	}

	/**
	 * @Method Name: mousePressed
	 * @author Nusayba Hamou
	 * @since 5 JAN 2024
	 * @Description: Checks if mouse is clicked on button (cursor clicks button)
	 * @Parameters: MouseEvent e
	 * @returns: N/A
	 * @Dependencies: Button
	 * @Throws/Exceptions: N/A
	 **/

	// @Override
	public void mousePressed(MouseEvent e) {
		for (InventorySlot slot : slots) {
			if (isIn(e, slot)) {
				slot.setMousePressed(true);
			}
		}
	}

	/**
	 * @Method Name: mouseReleased
	 * @author Nusayba Hamou
	 * @since 5 JAN 2024
	 * @Description:Checks if mouse is released from button bounds
	 * @Parameters: MouseEvent e
	 * @returns: N/A
	 * @Dependencies: Button, Playing
	 * @Throws/Exceptions: N/A
	 **/

	public void mouseReleased(MouseEvent e) {

		for (InventorySlot slot : slots) {
			slot.select = false;
			if (isIn(e, slot)) {
				if (slot.getMousePressed() && !Playing.paused) {
					if (slot.item < 4) {
						Playing.setGunIndex(slot.item);
					} else if (slot.item == 4) {
						playing.getPlayer().useItem(1, playing);
					} else if (slot.item == 6) {
						playing.getPlayer().useItem(4, playing);
					}
				}
				slot.select = true;

			}
		}
		resetButtons();

	}

	/**
	 * @Method Name: reset
	 * @author Nusayba Hamou
	 * @since 5 JAN 2024
	 * @Description: resets all slots to false
	 * @Parameters: N/A
	 * @returns: N/A
	 * @Dependencies: Button
	 * @Throws/Exceptions: N/A
	 **/

	public void reset() {
		for (InventorySlot slot : slots) {
			slot.select = false;

		}

	}
}
