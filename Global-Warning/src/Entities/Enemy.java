package Entities;

import static Utilities.Atlas.*;
import static Utilities.Constants.GAME_HEIGHT;
import static Utilities.Constants.GAME_WIDTH;
import static Utilities.Constants.animationSpeed;
import static Utilities.Constants.EnemyConstants.*;

import java.awt.Graphics;
import java.awt.Robot;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import Utilities.Atlas;

import static Utilities.Constants.Directions.LEFT;
import static Utilities.Constants.Directions.RIGHT;

public class Enemy extends Entity {
    protected int lvlData[][];
    protected int aniIndex, enemyState, enemyType;
	protected int aniTick, hitCooldown, aniSpeed = 15;
    protected int direction = LEFT; 
    private String state = WALK; 
    private BufferedImage[][] animations; 
    private int xFlipped; 
    private int wFlipped; 
    private int arrI, arrJ, enemyW, enemyH, Ewidth, Eheight;
    private String Atlas; 
    private float xSpeed, moveSpeed; 
    private float gravity = 0.04f;
    private boolean isAttack = false, leftwall = false, isVisible = false;
    private int enemyHP;


    public Enemy(float x, float y, int width, int height, int EnemyType, int arrI, int arrJ, int enemyW, int enemyH, String Atlas, int xFlipped, int wFlipped, float speed, int sizeX, int sizeH, int enemyHP) {
        super(x, y, width, height); 

        this.moveSpeed = speed; 
        this.xSpeed = this.moveSpeed; 
        this.xFlipped = xFlipped; 
        this.wFlipped = wFlipped;
        this.Atlas = Atlas; 
        this.enemyType = EnemyType; 
        this.arrI = arrI;
        this.arrJ = arrJ;
        this.enemyW = enemyW;
        this.enemyH = enemyH;
        this.Eheight = sizeH; 
        this.Ewidth = sizeX; 
        this.enemyRangeX = x-50;
        this.enemyRangeY = y-100; 
        this.enemyRangeH = height+200; 
        this.enemyRangeW = width+100; 
        maxHealth = getMaxEnemyHealth(EnemyType);
        this.enemyHP = enemyHP;
        currentHealth = maxHealth; 
        Animations(); 
        initialize();
    }

    protected void turnTowardsPlayer(Player player) {
    if (isPlayerVisible(player)) { 
		if (player.hitbox.x > hitbox.x)
			direction = RIGHT;
		else
			direction = LEFT;
        }
	}

    public void loadLevelData(int[][] lvlData) {
        this.lvlData = lvlData;
    }

    public void move(Player player, int[][] lvllData) {
        enemyRange.x = this.hitbox.x-50; 
        enemyRange.y = this.hitbox.y-100; 
        enemyRange.height = (int) this.hitbox.height+200; 
        enemyRange.width = (int) this.hitbox.width+100; 

        if (!player.hitbox.intersects(enemyRange))
        state = WALK; 

        if (player.hitbox.intersects(hitbox)){
            aniSpeed = getAttackSpeed(this.enemyType);
            checkPlayerHit(player);
            xSpeed = 0; 
            if(!isAttack){
            state = ATTACK; 
            isAttack = true; 
            }
        }
        else { 
        if(!player.hitbox.intersects(enemyRange))
        xSpeed = moveSpeed - .5f; 
        else { 
        xSpeed = moveSpeed;
        state = RUN; 
        }
        aniSpeed = animationSpeed; 
        isAttack = false; 
        }

        if (player.hitbox.intersects(enemyRange) && !isAttack) {
            if (player.hitbox.x < this.hitbox.x && direction == RIGHT) {
            isVisible = true; 
            direction = LEFT; 
            wFlipped = flipW(); 
            xFlipped = flipX();
            leftwall = false;
            System.out.println("CHANGE");
            }
            
            if (player.hitbox.x > this.hitbox.x && direction == LEFT) { 
            isVisible = false; 
            direction = RIGHT; 
            wFlipped = flipW(); 
            xFlipped = flipX();
            leftwall = true;
            System.out.println("CHANGE");
            }
        else if (!player.hitbox.intersects(enemyRange)) { 
            xSpeed = moveSpeed - .5f; 
        }
        }

        if (canMove(this.hitbox.x + xSpeed, this.hitbox.y, this.hitbox.width, this.hitbox.height, lvllData) && !isAttack && !leftwall) {
            if(!player.hitbox.intersects(enemyRange))
            state = WALK; 
            else 
            state = RUN; 
            hitbox.x -= xSpeed;
        }

        else if ((!canMove(this.hitbox.x + xSpeed, this.hitbox.y, this.hitbox.width, this.hitbox.height, lvllData) && !isAttack) && !leftwall && state != RUN) {
            hitbox.x = fixXPos(hitbox, xSpeed); 
          //  enemyRange.x = fixXPos(enemyRange, xSpeed);
            direction = flipD(); 
            leftwall = true;
            wFlipped = flipW(); 
            xFlipped = flipX(); 

        }

       if ((canMove(this.hitbox.x + xSpeed, this.hitbox.y, this.hitbox.width, this.hitbox.height, lvllData) && !isAttack) && leftwall) { 
            if(!player.hitbox.intersects(enemyRange))
            state = WALK; 
            else 
            state = RUN;       
            hitbox.x += xSpeed;
        }

        else if (!isAttack && leftwall && state != RUN) {
            hitbox.x = fixXPos(hitbox, xSpeed); 
            //enemyRange.x = fixXPos(enemyRange, xSpeed);
            direction = flipD(); 
            wFlipped = flipW(); 
            xFlipped = flipX();
            leftwall = false;


        }

        // Moving vertically
        if (inAir) {
            if (canMove(hitbox.x, hitbox.y + airSpeed, hitbox.width, hitbox.height, lvlData)) { // Move on the vertical if possible
                hitbox.y += airSpeed;
                enemyRange.y += airSpeed; 
                airSpeed += gravity;
            } else {
                hitbox.y = fixYPos(hitbox, airSpeed);
                inAir = false;
                airSpeed = 0;
            }
        } 
        
        if(hitbox.y+hitbox.height > GAME_HEIGHT && inAir) {
            inAir = false;
            hitbox.y = GAME_HEIGHT-hitbox.height;
        }
        updateAnimationTick(); 
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

    public int flipD() { 
        if (direction == RIGHT)
            return LEFT; 
        else
            return RIGHT; 
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
			if (animationIndex >= GetSpriteAmount(enemyType, state))
				animationIndex = 0;
		}
	}

    public void draw(Graphics g, int xOffset) {
        drawHitbox(g, xOffset);
        g.drawImage(animations[findState(this.enemyType, state)][animationIndex], (int) (hitbox.x - xOffset) + xFlipped, (int) hitbox.y, Ewidth * wFlipped, Eheight, null);
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
                animations[i][j] = img.getSubimage(j*enemyW, i*enemyH, enemyW, enemyH);
            }
        }
    }

    public int getAniIndex() {
		return aniIndex;
	}

	public void getEnemyState() {

    }

    protected void checkPlayerHit(Player player) {
        hitCooldown++; 
        if (hitCooldown >= 110) {
			hitCooldown = 0;
			player.changeHealth(-getEnemyDamage(enemyType));
        }
    }

    /**
     * Checks if the player is visible to the enemy or not
     * @author Hamad Mohammed
     * @since December 24, 2023
     * @param player The instance of the player being detected. 
     * @return Is the player visible or not
     */
    public boolean isPlayerVisible(Player player) { 

        if (this.hitbox.intersects(player.hitbox)) 
            return true; 
        else 
            return false; 
    }
}