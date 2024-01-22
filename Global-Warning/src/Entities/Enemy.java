/**
 * @author: Hamad Mohammed
 * @since: Dec 15 2024
 * @Last Modified: 21 Jan 2024
 * @Description: Class used for the main enemy AI and animating enemies and other mechanics including damage etc. 
 */

package Entities;

import static Utilities.Atlas.*;
import static Utilities.Constants.GAME_HEIGHT;
import static Utilities.Constants.GAME_WIDTH;
import static Utilities.Constants.animationSpeed;
import static Utilities.Constants.EnemyConstants.*;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import Entities.Planet1Enemies.Fireballs;
import GameStates.Playing;
import Objects.Weapons.Bombs;
import Objects.Weapons.Bullets;
import Utilities.Constants.PlayerConstants;
import static Utilities.Constants.Directions.LEFT;
import static Utilities.Constants.Directions.RIGHT;

public class Enemy extends Entity {
    protected int lvlData[][]; 
    protected int enemyState, enemyType; 
	protected int aniSpeed;   
    protected int direction = LEFT;    
    private String state = WALK;   
    private BufferedImage[][] animations;
    public int xFlipped, xEnemyFlipped; // Used to flip the images of the enemies when they switch directions. 
    public int wFlipped, wEnemyFlipped; 
    private int arrI, arrJ, enemyW, enemyH, Ewidth, Eheight; // The number of rows, columns, size of each enemy sprite. 
    private String Atlas; // The Atlas for each enemy. 
    private float xSpeed, moveSpeed; 
    private float gravity = 0.04f;
    private boolean isAttack = false, leftwall = false; 
    protected int enemyRangeWidth = 200; // enemy detection hitbox width. 
    protected int enemyRangeHeight = 80; // enemy detection hitbox height. 
    private boolean dead = false;
    private boolean deadOver = false; // used to see if deadAnimation is done.  
    public float healthBarWidth = 100; // healthbar size for enemies
    public float healthBarHeight = 10;
    private float currentHealthBarLen = healthBarWidth;
    public boolean isActive = false; 
    protected boolean isBoss = false;
    public int fireballCooldown = 40; // cooldown between each fireball shot by the boss. 
    private ArrayList<Fireballs> fireballs = new ArrayList<Fireballs>(); 
    public int fireballUpdate = 0;
    public boolean bombHit = false;
    private int bossXOffset = 0; 
    private int bossYOffset = 0; 
    public int magicTimer = 600; // How long the boss can stay in MAGIC state. 
    public int magicState = 0; 
    private boolean check = false; 

    public Enemy(float x, float y, int width, int height, int EnemyType, int arrI, int arrJ, int enemyW, int enemyH,
            String Atlas, int xFlipped, int wFlipped, float speed, int sizeX, int sizeH) {
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
        currentHealthBarLen = healthBarWidth;

        Animations();
        initialize();
    }


    public void loadLevelData(int[][] lvlData) {
        this.lvlData = lvlData;
    }


     /** 
     * @MethodName: move() 
     * @author: Hamad Mohammed
     * @since: Dec 15 2024
     * @param player The protagonist 
     * @param playing The instance of the playing class. 
     * @Description: basic enemy AI including movement, damage and fighting mechanics.  
     * @returns: N/A
     * @Dependencies: Playing.java, Atlas.java, Planet1Enemies
     * @Throws/Exceptions: N/A
     */
    public void move(Player player, Playing playing) {
    // if the enemy is a boss change the x and y offset. 
    if (isBoss){
        bossXOffset = 200; 
        bossYOffset = 115; 
        if(direction == RIGHT)
        bossXOffset *= -1;
    }
    else { 
        bossXOffset = 0; 
        bossYOffset = 0; 
    }

    // if the enemy's health is less than 0 set their state to dead and reset the animations. 
    if (this.currentHealth <= 0 && state != DEAD) { 
        dead = true; 
        state = DEAD;
        animationIndex = 0;
    }

    if (!dead){ 
        enemyRange.x = this.hitbox.x-enemyRangeWidth; 
        enemyRange.y = this.hitbox.y-enemyRangeWidth; 
        enemyRange.height = (int) this.hitbox.height+2*enemyRangeWidth; 
        enemyRange.width = (int) this.hitbox.width+2*enemyRangeWidth; 
        if (!player.hitbox.intersects(enemyRange)) 
        state = WALK; 
        if(direction == LEFT) {
            xSpeed = -moveSpeed;
        } else {
            xSpeed = moveSpeed;
        }

        // Changing states when player interacts with enemy. 
        if (player.hitbox.intersects(hitbox)) {
            aniSpeed = getAttackSpeed(this.enemyType);
            checkPlayerHit(player);
            xSpeed = 0; 
            state = ATTACK; 
            isAttack = true; 
        }
        else { 
        aniSpeed = animationSpeed; 
        isAttack = false; 
        }
        if (player.hitbox.intersects(enemyRange) && !isAttack) {
            state = RUN;
            xSpeed  *= 1.5;
            if (player.hitbox.x < this.hitbox.x && direction == RIGHT) {
            direction = LEFT; 
            wFlipped = flipW(); 
            xFlipped = flipX();
            }
            
            if (player.hitbox.x > this.hitbox.x && direction == LEFT) { 
            direction = RIGHT; 
            wFlipped = flipW(); 
            xFlipped = flipX();
            }
        }

        // Patrolling
        if(direction == RIGHT && !solidTile(hitbox.x + hitbox.width + 5, hitbox.y + hitbox.height + 5, lvlData) && state != RUN) {  
            direction = LEFT; 
            wFlipped = flipW(); 
            xFlipped = flipX();  
        } else if (direction == LEFT && !solidTile(hitbox.x - 5, hitbox.y + hitbox.height + 5, lvlData) && state != RUN) { 
            direction = RIGHT; 
            wFlipped = flipW(); 
            xFlipped = flipX();
        }

        if(stuckBehindDoor) {
            
            if(state != RUN) {
                hitbox.x -= xSpeed;
                xFlipped = flipX();
                wFlipped = flipW();
                direction = flipD();
            }
            
            xSpeed = -xSpeed;
        }

        // Horizontal movement
        if(canMove(hitbox.x + xSpeed, hitbox.y, hitbox.width, hitbox.height, lvlData) && !isAttack) {
            if(!player.hitbox.intersects(enemyRange))
            state = WALK; 
            else 
            state = RUN; 
            hitbox.x += xSpeed;
        } else {
            if(!isAttack && state != RUN) {
                xSpeed = -xSpeed;
                xFlipped = flipX();
                wFlipped = flipW();
                direction = flipD();
            }
            
        }}

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
     * @MethodName: updateAnimationTick()
     * @author: Hamad Mohammed
     * @since: Dec 15 2024
     * @param N/A
     * @Description: Updates the animatiion tick which updates the animation Index which is then used to move onto the next image in a sprite sheet. 
     * @returns: N/A
     * @Dependencies: N/A
     * @Throws/Exceptions: N/A
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


     /** 
     * @MethodName: draw()
     * @author: Hamad Mohammed
     * @since: Dec 15 2024
     * @param xOffset The x-Position offset of the image. 
     * @param g Graphics from the java awt library. 
     * @Description: draws enemy sprites with animations also incharge of calling other draw methods from different classes related to enemies to update them. 
     * @returns: N/A
     * @Dependencies: Planet1Enemies.
     * @Throws/Exception: N/A
     */
    public void draw(Graphics g, int xOffset) {
        for (Fireballs f : fireballs) 
            f.drawFireBall(g, xOffset);
      
        if (!deadOver)
            g.drawImage(animations[findState(this.enemyType, state)][animationIndex], (int) ((hitbox.x - xOffset) + xFlipped) - bossXOffset , (int) hitbox.y - bossYOffset, Ewidth * wFlipped, Eheight, null);
     
        if (dead && animationIndex == GetSpriteAmount(this.enemyType, DEAD) - 1) 
            deadOver = true; 
   
    }

    /** 
     * @MethodName: Animations()
     * @author: Hamad Mohammed
     * @since: Dec 15 2024
     * @param N/A
     * @Description: Loads the sprite sheet for each enemies to be used to for animations later on. 
     * @returns: N/A
     * @Dependencies: Atlas.java, Planet1Enemies
     * @Throws/Exceptions: N/A
>>>>>>> origin/Hamad's-Branch
     */
    public void Animations() {
        BufferedImage img = getSpriteAtlas(this.Atlas);
        animations = new BufferedImage[arrI][arrJ];
        for (int i = 0; i < animations.length; i++) {
            for (int j = 0; j < animations[i].length; j++) {
                if (!deadOver)
                    animations[i][j] = img.getSubimage(j * enemyW, i * enemyH, enemyW, enemyH);
            }
        }
    }

    /** 
     * @MethodName: checkPlayerHit()
     * @author: Hamad Mohammed
     * @since: Dec 15 2024
     * @param player The Protaganist
     * @Description: Checks if the player has been hit and reduces the damage if yes, also checks for immunity frames. 
     * @returns: N/A
     * @Dependencies: Player.java, Atlas.java
     * @Throws/Exceptions: N/A
     */
    protected void checkPlayerHit(Player player) {
        if(!player.isImmune() && !isBoss && animationIndex == GetSpriteAmount(enemyType, ATTACK) - 1 && animationTick >= animationSpeed - 1) 
            player.changeHealth(-getEnemyDamage(enemyType));

        else if (!player.isImmune() && isBoss && getFinalAttack(enemyType) == animationIndex)  
            player.changeHealth(-getEnemyDamage(enemyType));  

    }

    /** 
     * @MethodName: resetEnemy()
     * @author: Ryder Hodgson
     * @since: Dec 15 2024
     * @param: N/A
     * @Description: reset the different attributes for enemies. 
     * @returns: N/A
     * @Dependencies: N/A
     * @Throws/Exceptions: N/A
     */
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

    /** 
     * @MethodName: checkLightningIntersect()
     * @author: Ryder Hodgson
     * @since: Dec 17 2024
     * @param Playing The Protaganist
     * @Description: Kill enemies if they get struck by lightning. 
     * @returns: N/A
     * @Dependencies: N/A
     * @Throws/Exceptions: N/A
     */
    public void checkLightningIntersect(Playing playing) {
        if(playing.lightningHitbox != null && playing.lightningUpdates >= playing.lightningPosCooldown + playing.lightningSpawnCooldown)
            if(hitbox.intersects(playing.lightningHitbox) && playing.lightningHasPos)
                dead();
    }
    

    /** 
     * @MethodName: changeHealth()
     * @author: Hamad Mohammed
     * @since: Dec 17 2024
     * @param value The damage taken by the enemy. (Positive if adding enemy and Negative if subtracting). 
     * @Description: Add or remove hp from enemies. 
     * @returns: N/A
     * @Dependencies: N/A
     * @Throws/Exceptions: N/A
     */
    public void changeHealth(int value) {
        this.currentHealth += value;
        this.currentHealth = Math.max(Math.min(this.currentHealth, this.maxHealth), 0);
        currentHealthBarLen = healthBarWidth * ((float) currentHealth / (float) maxHealth);
    }


     /** 
     * @MethodName: enemyHit()
     * @author: Hamad Mohammed
     * @since: Dec 17 2024
     * @param bullet The arrayList of bullets being used by the player. 
     * @param playing Current instance of the playing class.  
     * @Description: Changes enemy health if they get hit by bullets. 
     * @returns: N/A
     * @Dependencies: playing.java, Bullets.java
     * @Throws/Exceptions: N/A
     */
    public void enemyHit(List<Bullets> bullet, Playing playing) {
        for (Bullets b : bullet) {
            if (b.getHitbox().intersects(this.hitbox) && playing.gunIndex != 3) {
                isActive = true;
                playing.removeBullet();
                changeHealth(-PlayerConstants.getPlayerDamage(playing));
            }
        }
    }


     /** 
     * @MethodName: isDead()
     * @author: Hamad Mohammed
     * @since: Dec 17 2024
     * @Description: Determines if the enemy should be dead or not depending if their animation is over or not. 
     * @return boolean true if the Enemy's animation is over. 
     * @Dependencies: N/A
     * @Throws/Exceptions: N/A
     */
    public boolean isDead() { 
        if (deadOver)
            return true;
        else
            return false;
    }


    /** 
     * @MethodName: dead()
     * @author: Hamad Mohammed
     * @since: Dec 17 2024
     * @Description: Changes all attributes of the enemy to be dead when called. 
     * @returns: N/A 
     * @Dependencies: N/A
     * @Throws/Exceptions: N/A
     */
    public void dead() {
        if (!dead) {
            state = DEAD;
            dead = true;
            this.isActive = true;
            this.currentHealth = 0;
            this.currentHealthBarLen = 0;
            animationIndex = 0;
        }

    }



     /** 
     * @MethodName: drawHealth()
     * @author: Hamad Mohammed
     * @since: Dec 15 2024
     * @param xOffset The x-Position offset of the image. 
     * @param g Graphics from the java awt library. 
     * @Description: Draws the Enemy Hp bar if its boss it draws the boss hp bar. 
     * @returns: N/A
     * @Dependencies: N/A
     * @Throws/Exception: N/A
     */
    public void drawHealth(Graphics g, int xOffset) {
        if (isActive && !isBoss) { 
            g.setColor(Color.red);
            g.fillRect((int) ((this.getHitbox().x - this.getHitbox().width/4)) - xOffset, (int) this.getHitbox().y - 20, (int) healthBarWidth, (int) healthBarHeight);
            g.setColor(Color.green);
            g.fillRect((int) (this.getHitbox().x - (this.getHitbox().width/4)) - xOffset, (int) this.getHitbox().y - 20, (int) currentHealthBarLen, (int) healthBarHeight);
            g.setColor(Color.black);
            g.drawRect((int) (this.getHitbox().x - (this.getHitbox().width/4)) - xOffset, (int) this.getHitbox().y - 20, (int) healthBarWidth, (int) healthBarHeight);

        } else if (isActive && isBoss) {
            g.setColor(Color.red);
            g.fillRect((int) (GAME_WIDTH/3.3 - xOffset), GAME_HEIGHT - 60, (int) healthBarWidth, (int) healthBarHeight);
            g.setColor(Color.green);
            g.fillRect((int) (GAME_WIDTH/3.3 - xOffset), GAME_HEIGHT - 60, (int) currentHealthBarLen, (int) healthBarHeight);
            g.setColor(Color.black);
            g.drawRect((int) (GAME_WIDTH/3.3  - xOffset), GAME_HEIGHT - 60, (int) healthBarWidth, (int) healthBarHeight);
        }

    }


     /** 
     * @MethodName: magicAttack()
     * @author: Hamad Mohammed
     * @since: Dec 15 2024
     * @param playing The Protagoninst 
     * @Description: Changes the enemy state to Magic to summon fireballs if certain conditions are met. 
     * @returns: N/A
     * @Dependencies: Planet1Enemies.
     * @Throws/Exception: N/A
     */
    public void magicAttack(Playing playing) { 
        if(state == MAGIC)
            magicState++; 
        else 
            magicState= 0; 

        if(enemyType == Demonboi)
            if(!hitbox.intersects(playing.getPlayer().getHitbox()) && ((Math.random()*10000) + 1) < 20 && state != MAGIC)
                state = MAGIC;

        if(magicState >= magicTimer) { 
            state = WALK; 
            magicState = 0; 
        }

        if(fireballUpdate  >= fireballCooldown)
            if(this.enemyType == Demonboi)
                if(state == MAGIC){ 
                    fireballs.add(new Fireballs((int) (Math.random()*(GAME_WIDTH-200)+100), 100, playing));
                    fireballUpdate = 0; 
                }
    }

} // End Class
