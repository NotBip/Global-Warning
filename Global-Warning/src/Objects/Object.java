package Objects; 

import static Utilities.Constants.objectConstants.Chest;
import static Utilities.Constants.objectConstants.Door;
import static Utilities.Constants.objectConstants.GetSpriteAmount;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;

import Main.Game;

/**
***********************************************
* @Author : Bobby Walden
* @Originally made : 15 JAN, 2024
* @Last Modified: 21 JAN, 2024
* @Description: Initializes and generalizes objects in the game.
***********************************************
*/


public class Object {

	// Variables
	protected int x, y, objType;
	protected Rectangle2D.Float hitbox;
	protected boolean doAnimation, active = true;
	protected int aniTick, aniIndex, aniSpeed;
	protected int xDrawOffset, yDrawOffset;

	// Initialize Object
	public Object(int x, int y, int objType) {
		this.x = x;
		this.y = y;
		this.objType = objType;
	}

	/**
	@Method Name: updateAnimationTick
	@Author: Bobby Walden
	@Creation Date: 15 JAN, 2024
	@Modified Date: 16 JAN, 2024
	@Description: Updates the animations for the objects.
	@Parameters: N/A
	@Returns: N/A
	@Dependencies: objectConstants
	@Throws/Exceptions: N/A
	*/
	protected void updateAnimationTick() {
		aniTick++;
		if (aniTick >= aniSpeed) {
			aniTick = 0;
			aniIndex++;
			if (aniIndex >= GetSpriteAmount(objType)) {
				aniIndex = 0;
				if (objType == Door || objType == Chest)
					doAnimation = false;
					active = false;
		}
	}
}

	/**
	@Method Name: reset
	@Author: Bobby Walden
	@Creation Date: 15 JAN, 2024
	@Modified Date: 16 JAN, 2024
	@Description: Resets animations for objects.
	@Parameters: N/A
	@Returns: N/A
	@Dependencies: objectConstants
	@Throws/Exceptions: N/A
	*/
	public void reset() {
		aniIndex = 0;
		aniTick = 0;
		active = true;

		if (objType == Door || objType == Chest )
			doAnimation = false;
		else
			doAnimation = true;
	}

	/**
	@Method Name: initHitbox
	@Author: Bobby Walden
	@Creation Date: 15 JAN, 2024
	@Modified Date: 15 JAN, 2024
	@Description: Initializes the hitbox for the object.
	@Parameters: int width, int height
	@Returns: N/A
	@Dependencies: N/A
	@Throws/Exceptions: N/A
	*/
	protected void initHitbox(int width, int height) {
		hitbox = new Rectangle2D.Float(x, y, (int) (width * Game.SCALE), (int) (height * Game.SCALE));
		
	}

	/**
	@Method Name: drawHitbox
	@Author: Bobby Walden
	@Creation Date: 15 JAN, 2024
	@Modified Date: 15 JAN, 2024
	@Description: Draws the hitbox for the object.
	@Parameters: Graphics g, int xLvlOffset
	@Returns: N/A
	@Dependencies: N/A
	@Throws/Exceptions: N/A
	*/
	public void drawHitbox(Graphics g, int xLvlOffset) {
		g.setColor(Color.PINK);
		g.drawRect((int) hitbox.x - xLvlOffset, (int) hitbox.y, (int) hitbox.width, (int) hitbox.height);
	}

	public int getObjType() {
		return objType;
	}

	public Rectangle2D.Float getHitbox() {
		return hitbox;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public void setAnimation(boolean doAnimation) {
		this.doAnimation = doAnimation;
	}

	public int getxDrawOffset() {
		return xDrawOffset;
	}

	public int getyDrawOffset() {
		return yDrawOffset;
	}

	public int getAniIndex() {
		return aniIndex;
	}

	public int getAniTick() {
		return aniTick;
	}

}