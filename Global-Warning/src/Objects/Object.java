package Objects; 

import static Utilities.Constants.objectConstants.Chest;
import static Utilities.Constants.objectConstants.Door;
import static Utilities.Constants.objectConstants.GetSpriteAmount;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;

import Main.Game;


public class Object {

	protected int x, y, objType;
	protected Rectangle2D.Float hitbox;
	protected boolean doAnimation, active = true;
	protected int aniTick, aniIndex, aniSpeed;
	protected int xDrawOffset, yDrawOffset;

	public Object(int x, int y, int objType) {
		this.x = x;
		this.y = y;
		this.objType = objType;
	}

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

	public void reset() {
		aniIndex = 0;
		aniTick = 0;
		active = true;

		if (objType == Door || objType == Chest )
			doAnimation = false;
		else
			doAnimation = true;
	}

	protected void initHitbox(float x, int width, int height) {
		hitbox = new Rectangle2D.Float(x, y, (int) (width * Game.SCALE), (int) (height * Game.SCALE));
	}

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