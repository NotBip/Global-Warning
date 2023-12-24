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



    public Enemy(float x, float y, int width, int height, int EnemyType) {
        super(x, y, width, height);
        this.enemyType = EnemyType; 
        this.inAir = true; 
        maxHealth = getMaxEnemyHealth(EnemyType);
        currentHealth = maxHealth; 
        Animations(); 
        initialize();
    }

    public Enemy(float x, float y, int width, int height, int EnemyType, int arrI, int arrJ, int enemyW, int enemyH, String Atlas, int xFlipped, int wFlipped, float speed, int sizeX, int sizeH) {
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
        this.enemyRangeX = x - x;
        this.enemyRangeY = y - 200; 
        this.enemyRangeH = height + 220; 
        this.enemyRangeW = width + 300; 
        maxHealth = getMaxEnemyHealth(EnemyType);
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

    public void move(Player player) {
        System.out.println(state);
        if (player.hitbox.intersects(hitbox)){
            aniSpeed = 45;
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
            if (player.hitbox.x < this.hitbox.x && direction == RIGHT && !isVisible) {
            isVisible = true; 
            direction = LEFT; 
            wFlipped = flipW(); 
            xFlipped = flipX();
            leftwall = false;
            System.out.println("CHANGE");
            }
            
            if (player.hitbox.x > this.hitbox.x && direction == LEFT && isVisible) { 
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


        if (canMove(this.hitbox.x, this.hitbox.y, this.hitbox.width, this.hitbox.height) && !isAttack && !leftwall) {
            if(!player.hitbox.intersects(enemyRange))
            state = WALK; 
            else 
            state = RUN; 
            hitbox.x -= xSpeed;
            enemyRange.x -= xSpeed; 
        }

        else if ((!canMove(this.hitbox.x, this.hitbox.y, this.hitbox.width, this.hitbox.height) && !isAttack) && !leftwall) {
            direction = RIGHT; 
            leftwall = true;
            wFlipped = flipW(); 
            xFlipped = flipX(); 
            enemyRange.x += xSpeed; 
            hitbox.x += xSpeed; 
        }

       if ((canMove(this.hitbox.x, this.hitbox.y, this.hitbox.width, this.hitbox.height) && !isAttack) && leftwall) { 
            if(!player.hitbox.intersects(enemyRange))
            state = WALK; 
            else 
            state = RUN;       
            hitbox.x += xSpeed;
            enemyRange.x += xSpeed; 
        }

        else if (!isAttack && leftwall) {
            direction = LEFT; 
            hitbox.x -= xSpeed; 
            enemyRange.x -= xSpeed; 
            wFlipped = flipW(); 
            xFlipped = flipX();
            leftwall = false;

        }

        if(inAir) { // Fall
            hitbox.y += airSpeed;
            enemyRange.y += airSpeed; 
            airSpeed += gravity;
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

    public void draw(Graphics g) {
        drawHitbox(g);
        g.drawImage(animations[findState(this.enemyType, state)][animationIndex], (int) hitbox.x + xFlipped, (int) hitbox.y, Ewidth * wFlipped, Eheight, null);
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
        if (hitCooldown >= aniSpeed*2) {
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