package Entities;

import static Utilities.Atlas.*;
import static Utilities.Constants.GAME_HEIGHT;
import static Utilities.Constants.GAME_WIDTH;
import static Utilities.Constants.animationSpeed;
import static Utilities.Constants.EnemyConstants.*;

import java.awt.Color;
import java.awt.Graphics;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import Entities.Planet1Enemies.Fireballs;
import GameStates.Playing;
import Objects.Chest;
import Objects.Object;
import Objects.ObjectManager;
import Objects.Weapons.Bombs;
import Objects.Weapons.Bullets;
import Utilities.Constants;
import Utilities.Constants.PlayerConstants;

import static Utilities.Constants.Directions.LEFT;
import static Utilities.Constants.Directions.RIGHT;

public class Enemy extends Entity {
    protected int lvlData[][];
    protected int aniIndex, enemyState, enemyType;
	protected int aniTick, hitCooldown, aniSpeed;
    protected int direction = LEFT; 
    private String state = WALK; 
    private BufferedImage[][] animations; 
    public int xFlipped, xEnemyFlipped; 
    public int wFlipped, wEnemyFlipped; 
    private int arrI, arrJ, enemyW, enemyH, Ewidth, Eheight;
    private String Atlas; 
    private float xSpeed, moveSpeed; 
    private float gravity = 0.04f;
    private boolean isAttack = false, leftwall = false, isVisible = false; 
    private int enemyRangeWidth = 200;
    private int enemyRangeHeight = 80; 
    private boolean dead = false; 
    private boolean deadOver = false; 
    public float healthBarWidth = 100; 
    public float healthBarHeight = 10;
    private float currentHealthBarLen = healthBarWidth;
    public boolean isActive = false; 
    protected boolean isBoss = false;
    public int fireballCooldown = 40; // How long it takes after choosing a position for lightning to strike
    private ArrayList<Fireballs> fireballs = new ArrayList<Fireballs>(); 
    public int fireballUpdate = 0; 

    public Enemy(float x, float y, int width, int height, int EnemyType, int arrI, int arrJ, int enemyW, int enemyH, String Atlas, int xFlipped, int wFlipped, float speed, int sizeX, int sizeH) {
        super(x, y, width, height); 

        maxHealth = getMaxEnemyHealth(EnemyType);
        currentHealth = maxHealth; 
        this.moveSpeed = speed; 
        this.xSpeed = this.moveSpeed; 
        this.xFlipped = xFlipped; 
        this.wFlipped = wFlipped;
        this.wEnemyFlipped = -1; 
        this.xEnemyFlipped = enemyW; 
        this.Atlas = Atlas; 
        this.enemyType = EnemyType; 
        this.arrI = arrI;
        this.arrJ = arrJ;
        this.enemyW = enemyW;
        this.enemyH = enemyH;
        this.Eheight = sizeH; 
        this.Ewidth = sizeX; 
        this.enemyRangeX = x-enemyRangeWidth;
        this.enemyRangeY = y-enemyRangeHeight; 
        this.enemyRangeH = height+2*enemyRangeHeight; 
        this.enemyRangeW = width+2*enemyRangeWidth;
        

        Animations(); 
        initialize();
    }

    protected void turnTowardsPlayer(Player player) {
    if (isPlayerVisible(player) && !dead) { 
		if (player.hitbox.x > hitbox.x)
			direction = RIGHT;
		else
			direction = LEFT;
        }
	}

    public void loadLevelData(int[][] lvlData) {
        this.lvlData = lvlData;
    }

    public void move(Player player, int[][] lvllData, Playing playing) {
    
    if (this.currentHealth <= 0 && state != DEAD) { 
        dead = true; 
        state = DEAD;
        animationIndex = 0;
    }


    if (!dead){ 
        fireballUpdate++; 
        magicAttack(); 
        for (Fireballs f : fireballs) { 
            if(canMove(f.fireballHitbox.x, f.fireballHitbox.y, f.fireballHitbox.width, f.fireballHitbox.height, lvllData)) { 
                f.update();
            }
            else 
                fireballs.remove(f); 
        }
        
        for (Bombs b : playing.getBombs()) { 
            if(b.explode)
                if(b.hitbox.intersects(this.hitbox)) 
                    this.changeHealth(-50);

        }
        enemyRange.x = this.hitbox.x-enemyRangeWidth; 
        enemyRange.y = this.hitbox.y-enemyRangeWidth; 
        enemyRange.height = (int) this.hitbox.height+2*enemyRangeWidth; 
        enemyRange.width = (int) this.hitbox.width+2*enemyRangeWidth; 
        if (!player.hitbox.intersects(enemyRange))
        state = WALK; 

        if (player.hitbox.intersects(hitbox)){
            aniSpeed = getAttackSpeed(this.enemyType);
            
            checkPlayerHit(player);
            xSpeed = 0; 
            if(!isAttack){
            state = MAGIC; 
            isAttack = true; 
            }
        }
        else { 
        if(!player.hitbox.intersects(enemyRange))
        xSpeed = moveSpeed; 
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
            flipW(); 
            flipX();
            leftwall = false;
            }
            
            if (player.hitbox.x > this.hitbox.x && direction == LEFT) { 
            isVisible = false; 
            direction = RIGHT; 
            flipW(); 
            flipX();
            leftwall = true;
            }
        else if (!player.hitbox.intersects(enemyRange)) { 
            xSpeed = moveSpeed; 
        }
        }

        if(direction == RIGHT && !solidTile(hitbox.x + hitbox.width + 5, hitbox.y + hitbox.height + 5, lvlData) && state != RUN) {
           
            direction = LEFT; 
            flipW(); 
            flipX();
            leftwall = false;
        } else if (direction == LEFT && !solidTile(hitbox.x - 5, hitbox.y + hitbox.height + 5, lvlData) && state != RUN) {
             
            direction = RIGHT; 
            flipW(); 
            flipX();
            leftwall = true;
        }

        if (canMove(this.hitbox.x - xSpeed, this.hitbox.y, this.hitbox.width, this.hitbox.height, lvllData) && !isAttack && !leftwall) {
            if(!player.hitbox.intersects(enemyRange))
            state = WALK; 
            else 
            state = RUN; 
            hitbox.x -= xSpeed;
        }

        else if ((!canMove(this.hitbox.x - xSpeed, this.hitbox.y, this.hitbox.width, this.hitbox.height, lvllData) && !isAttack)) {
            hitbox.x = fixXPos(hitbox, xSpeed); 
            direction = flipD(); 
            leftwall = true;
            flipW(); 
            flipX(); 
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
            flipW(); 
            flipX();
            leftwall = false;


        }

        if (!inAir) {
            if (!checkFloor(hitbox.x, hitbox.y, hitbox.width, hitbox.height, lvlData)) { // Check if player is not on ground
                inAir = true;
            }
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
    }
    updateAnimationTick(); 
    }

    
    public void flipX() {
		if (xFlipped == 0){  
			xFlipped = width;
        }
        else 
            xFlipped = 0; 

        if(xEnemyFlipped == 0)  
            xEnemyFlipped = enemyRangeW; 
        else
            xEnemyFlipped = 0; 
        

        }

	public void flipW() {
		if (wFlipped == 1)
			wFlipped = -1;
        else 
            wFlipped = 1;

        if (wEnemyFlipped == 1)
        wEnemyFlipped = -1;
        else 
        wEnemyFlipped = 1; 
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
        g.setColor(Color.white);
        drawHitbox(g, xOffset);
        if (!deadOver)
        g.drawImage(animations[findState(this.enemyType, state)][animationIndex], (int) (hitbox.x - xOffset) + xFlipped, (int) hitbox.y, Ewidth * wFlipped, Eheight, null);
        if (dead && animationIndex == GetSpriteAmount(this.enemyType, DEAD) - 1) { 
            deadOver = true; 
        }

        for (Fireballs f : fireballs) { 
            f.drawFireBall(g, xOffset);
        }
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
                if (!deadOver)
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
        if(!player.isImmune() && !isBoss && animationIndex == GetSpriteAmount(enemyType, ATTACK) - 1 && animationTick >= animationSpeed - 1) 
            player.changeHealth(-getEnemyDamage(enemyType)); 
        else if (!player.isImmune() && isBoss && getFinalAttack(enemyType) == animationIndex)
            player.changeHealth(-getEnemyDamage(enemyType));  
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

    public void resetEnemy() {
        hitbox.x = x;
        hitbox.y = y;
        currentHealth = maxHealth;
        state = WALK;
        inAir = true;
        dead = false;
        deadOver = false;
        currentHealthBarLen = healthBarWidth;
        isActive = false;
      }

      public void checkLightningIntersect(Playing playing) {
        if(playing.lightningHitbox != null && playing.lightningUpdates >= playing.lightningPosCooldown + playing.lightningSpawnCooldown)
        if(hitbox.intersects(playing.lightningHitbox) && playing.lightningHasPos) { // fix when this happens
            dead();
        }
    }
          /**
     * Changes the players health depending on enemy.
     * @author Hamad Mohammed
     * @param value Damage being done 
     * @since December 16, 2023
     */
    public void changeHealth(int value) {
		this.currentHealth += value;
		this.currentHealth = Math.max(Math.min(this.currentHealth, this.maxHealth), 0);
        currentHealthBarLen = healthBarWidth * ((float)currentHealth / (float) maxHealth);
	}

    /**
     * Changes enemy hp based on damage done. 
     * @author Hamad Mohammed
     */
    public void enemyHit(List<Bullets> bullet, Playing playing) { 
        for (Bullets b : bullet) { 
            if(b.getHitbox().intersects(this.hitbox) && playing.gunIndex != 3) {
                isActive = true;  
                playing.removeBullet();
                changeHealth(-PlayerConstants.getPlayerDamage(playing));
            }
        }
        /*for (Explosions e : explosion) { 
            if(e.getHitbox().intersects(this.hitbox)) {
                isActive = true;  
                playing.removeBullet();
                changeHealth(PlayerConstants.getPlayerDamage(playing) * 5);
            }
        }*/
    }

    public boolean isDead() { 
        if (deadOver)
            return true; 
        else 
            return false; 
    }

    public void dead() {
        this.currentHealth = 0;
        this.currentHealthBarLen = 0; 
        animationIndex = 0;
    }

    public void drawHealth(Graphics g, int xOffset) {
        if (isActive && !isBoss) { 
        g.setColor(Color.red);
        g.fillRect((int) ((this.getHitbox().x - this.getHitbox().width/4)) - xOffset, (int) this.getHitbox().y - 20, (int) healthBarWidth, (int) healthBarHeight);
        g.setColor(Color.green);
        g.fillRect((int) (this.getHitbox().x - (this.getHitbox().width/4)) - xOffset, (int) this.getHitbox().y - 20, (int) currentHealthBarLen, (int) healthBarHeight);
        g.setColor(Color.black);
        g.drawRect((int) (this.getHitbox().x - (this.getHitbox().width/4)) - xOffset, (int) this.getHitbox().y - 20, (int) healthBarWidth, (int) healthBarHeight);
         }
         else if (isBoss)
         g.setColor(Color.red);
         g.fillRect((int) ((this.getHitbox().width/4)) - xOffset, GAME_HEIGHT - 30, (int) healthBarWidth, (int) healthBarHeight);
         g.setColor(Color.green);
         g.fillRect((int) ((this.getHitbox().width/4)) - xOffset, GAME_HEIGHT - 30, (int) currentHealthBarLen, (int) healthBarHeight);
         g.setColor(Color.black);
         g.drawRect((int) ((this.getHitbox().width/4)) - xOffset, GAME_HEIGHT - 30, (int) healthBarWidth, (int) healthBarHeight);

    }

    public void magicAttack() { 
        if(fireballUpdate  >= fireballCooldown)
            if(this.enemyType == Demonboi)
                if(state == MAGIC){ 
                    fireballs.add(new Fireballs((int) (Math.random()*(GAME_WIDTH-200)+100), 100));
                    fireballUpdate = 0; 
                }
    }

    // private ArrayList<fure getFireballs() { 

    // }
    
}