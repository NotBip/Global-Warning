package Objects;

import static Utilities.Constants.GAME_HEIGHT;
import static Utilities.Constants.GAME_WIDTH;
import static Utilities.Constants.HEIGHT_IN_TILES;
import static Utilities.Constants.TILE_SIZE;
import static Utilities.Constants.WIDTH_IN_TILES;
import static Utilities.Constants.ObjectConstants.GetSpriteAmount;
import static Utilities.Constants.ObjectConstants.IDLE;
import static Utilities.Constants.ObjectConstants.findState;
import static Utilities.Atlas.getSpriteAtlas;
import static Utilities.Constants.Directions;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import Entities.Entity;
import Entities.Player;

public class Object extends Entity{
    protected int lvlData[][];
    protected int aniIndex, objectState, objectType;
	protected int aniTick, hitCooldown, aniSpeed = 15;
    private String state = IDLE; 
    private BufferedImage[][] animations; 
    private int xFlipped; 
    private int wFlipped; 
    private int arrI, arrJ, objectW, objectH, Owidth, Oheight;
    private String Atlas; 
    private float xSpeed, moveSpeed; 
    private float gravity = 0.04f;
    private boolean isAttack = false, leftwall = false, isVisible = false;
    protected Rectangle2D.Float objectRange; 
    protected float objectRangeX, objectRangeY; 
    protected int objectRangeW, objectRangeH;
    protected Rectangle2D.Float hitbox; 


    public Object(float x, float y, int width, int height, int ObjectType, int arrI, int objectW, int objectH, String Atlas, int xFlipped, int wFlipped, int sizeX, int sizeH) {
        super(x, y, width, height); 
        this.xSpeed = this.moveSpeed; 
        this.xFlipped = xFlipped; 
        this.wFlipped = wFlipped;
        this.Atlas = Atlas; 
        this.objectType = ObjectType; 
        this.arrI = arrI;
        this.objectW = objectW;
        this.objectH = objectH;
        this.Oheight = sizeH; 
        this.Owidth = sizeX; 
        this.objectRangeX = x-50;
        this.objectRangeY = y-100; 
        this.objectRangeH = height+200; 
        this.objectRangeW = width+100; 
        Animations(); 
        initialize();
    }

    public void loadLevelData(int[][] lvlData) {
        this.lvlData = lvlData;
    }


    public int flipX() {
		if (xFlipped == 0)  
			return width;
         else 
			return 0;
        }

	public int flipW() {
		if (wFlipped == 1)
			return -1;
        else 
			return 1;
	}


     /**
     * Helps change images making it animate. 
     * @author Hamad Mohammed
     * @since December 16, 2023
     */
    protected void updateAnimationTick() {
		animationTick++;
		if (animationTick >= aniSpeed) {    
			animationTick = 0;
			animationIndex++;
			if (animationIndex >= GetSpriteAmount(objectType, state))
				animationIndex = 0;
		}
	}

    public void draw(Graphics g, int xOffset) {
        drawHitbox(g, xOffset);
        g.drawImage(animations[findState(this.objectType, state)][animationIndex], (int) (hitbox.x - xOffset) + xFlipped, (int) hitbox.y, Owidth * wFlipped, Oheight, null);
    }

    /**
     * Loads the animations from the sprite atlas. 
     * @author Hamad Mohammed
     * @since December 16, 2023
     */
    public void Animations() {
        BufferedImage img = getSpriteAtlas(this.Atlas);
        animations = new BufferedImage[arrI][arrJ]; 
        for (int i = 0; i < animations.length; i++){
            for (int j = 0; j < animations[i].length; j++){
                animations[i][j] = img.getSubimage(j*objectW, i*objectH, objectW, objectH);
            }
        }
    }

    public int getAniIndex() {
		return aniIndex;
	}

	public void getobjectState() {

    }

    /**
     * Checks if the player is visible to the object or not
     * @author Hamad Mohammed
     * @since December 24, 2023
     * @param player The instance of the player being detected. 
     * @return Is the player visible or not
     */
    public boolean isPlayerVisible(Player player) { 

        if (this.hitbox.intersects(player.getHitbox())) 
            return true; 
        else 
            return false; 
    }

}