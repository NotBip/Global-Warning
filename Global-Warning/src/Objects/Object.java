package Objects;

import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import static Utilities.Constants.objectConstants.*;
import Main.Game;

public class Object{
    
    protected int x, y, object;
	protected Rectangle2D.Float hitbox;
	protected boolean doAnimation, active = true;
	protected int aniTick, aniIndex, aniSpeed = 15; ;
	protected int xDrawOffset, yDrawOffset;
    
    public Object(int x, int y, int object) {
		this.x = x;
		this.y = y;
		this.object = object;
	}

	protected void updateAnimationTick() {
		aniTick++;
		if (aniTick >= aniSpeed) {
			aniTick = 0;
			aniIndex++;
			if (aniIndex >= GetSpriteAmount(object, IDLE)) {
				aniIndex = 0;
            }
        }   
    }

	public void reset() {
		aniIndex = 0;
		aniTick = 0;
		active = true;
	}

	protected void initHitbox(int width, int height) {
		hitbox = new Rectangle2D.Float(x, y, (int) (width * Game.SCALE), (int) (height * Game.SCALE));
	}

	public void drawHitbox(Graphics g, int xLvlOffset) {
		g.drawRect((int) hitbox.x - xLvlOffset, (int) hitbox.y, (int) hitbox.width, (int) hitbox.height);
	}

	public int getObjType() {
		return object;
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