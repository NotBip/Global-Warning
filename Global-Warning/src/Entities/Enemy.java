package Entities;

import static Utilities.Atlas.*;
import static Utilities.Constants.GAME_HEIGHT;
import static Utilities.Constants.GAME_WIDTH;
import static Utilities.Constants.animationSpeed;
import static Utilities.Constants.EnemyConstants.*;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import Utilities.Atlas;

import static Utilities.Constants.Directions.LEFT;
import static Utilities.Constants.Directions.RIGHT;

public class Enemy extends Entity {
    protected int aniIndex, enemyState, enemyType;
	protected int aniTick, aniSpeed = 25;
    protected int direction = RIGHT; 
    private BufferedImage[][] animations; 
    private int xFlipped; 
    private int wFlipped; 
    private int arrI, arrJ, enemyW, enemyH, Ewidth, Eheight;
    private String Atlas; 
    private float xSpeed; 
    private float gravity = 0.04f;
    private boolean isAttack = false; 
    Player player; 


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

        this.xSpeed = speed; 
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
        maxHealth = getMaxEnemyHealth(EnemyType);
        currentHealth = maxHealth; 
        Animations(); 
        initialize();
    }

    protected void turnTowardsPlayer(Player player) {
		if (player.hitbox.x > hitbox.x)
			direction = RIGHT;
		else
			direction = LEFT;
	}

    public void move(Player player) {

        if (player.hitbox.intersects(hitbox)){
            System.out.println("ATTACK!!!");
            xSpeed = 0; 
            if(!isAttack && enemyType == Pirate){
            newState(ATTACK);
            isAttack = true; 
            }
            if(!isAttack && enemyType == Zombie){ 
                newState(zombieAttack);
                isAttack = true; 
            }
        }
        else { 
        xSpeed = 2f;  
        isAttack = false; 
        }

        if ((hitbox.x + xSpeed == GAME_WIDTH - hitbox.width-1 || player.hitbox.x < hitbox.x) && !isAttack){
            changeDirection();

            if (enemyType == Zombie){
                xFlipped = 0; 
                wFlipped = 1; 
            }

            else if(enemyType == Pirate){ 
                xFlipped = width; 
                wFlipped = -1; 
            }
    
        } else if (((hitbox.x + xSpeed)-1 == 0 || player.hitbox.x > hitbox.x) && !isAttack ){
            changeDirection();

            if (enemyType == Zombie){
                xFlipped = width; 
                wFlipped = -1; 
            }

            else if(enemyType == Pirate){ 
                xFlipped = 0; 
                wFlipped = 1; 
                
            }
        }

        if ((player.hitbox.x > hitbox.x && direction == RIGHT && hitbox.x + xSpeed < GAME_WIDTH - hitbox.width) && !isAttack) {
            state = RUNNING; 
            hitbox.x += xSpeed;
        } else if ((player.hitbox.x < hitbox.x && direction == LEFT && hitbox.x + xSpeed > 0) && !isAttack) {
            state = RUNNING; 
            hitbox.x -= xSpeed;
        }

        if(inAir) { // Fall
            hitbox.y += airSpeed;
            airSpeed += gravity;
        }

        
        if(hitbox.y+hitbox.height > GAME_HEIGHT && inAir) {
            inAir = false;
            hitbox.y = GAME_HEIGHT-hitbox.height;
        }

        

        updateAnimationTick(); 
    }

	protected void changeDirection() {
		if (direction == LEFT)
			direction = RIGHT;
		else
			direction = LEFT;
	}
    
    public int flipX() {
		if (direction == RIGHT)
			return width;
		else
			return 0;
	}

	public int flipW() {
		if (direction == RIGHT)
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
		if (animationTick >= animationSpeed) {
			animationTick = 0;
			animationIndex++;
			if (animationIndex >= GetSpriteAmount(enemyType, state))
				animationIndex = 0;
		}
	}

    public void draw(Graphics g) {
        drawHitbox(g);
        g.drawImage(animations[state][animationIndex], (int) hitbox.x + xFlipped, (int) hitbox.y, Ewidth * wFlipped, Eheight, null);
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

	public int getEnemyState() {
		return enemyState;
	}

}