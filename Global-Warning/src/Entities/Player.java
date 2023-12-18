package Entities;
import static Utilities.Constants.*;
import static Utilities.Constants.PlayerConstants.*;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import static Utilities.Constants.Directions.*;
import javax.imageio.ImageIO;

import static Utilities.Atlas.*;

public class Player extends Entity {

    private BufferedImage[][] animations; 
    private boolean moving = false; 
    private boolean left, right, up, down;
    private int playerDir = -1; 
	private BufferedImage img;
    private float gravity = 0.04f;
    private float airFriction = 0.1f;
    private float moveSpeed = 2.0f;
    private float jumpSpeed = -2.25f;
    private float defaultDashSpeed = 5f;
    private float dashSpeed = 0;
    private boolean isDashing = false;
    public boolean canDash = false;

    public Player(float x, float y, int width, int height) {
        super(x, y, width, height);
        this.state = IDLERIGHT; 
        this.inAir = true;
        //Animations(); 
        initialize();
    }

    /**
     * Updates the player (position and direction currently) 
     * @author Ryder Hodgson
     * @since December 16, 2023
     */

    public void update() {
        moving = false; // Stop the player movement animation in case they stop moving this update
        xSpeed = 0;
    
        if(hitbox.y+hitbox.height > GAME_HEIGHT && inAir) {
            inAir = false;
            hitbox.y = GAME_HEIGHT-hitbox.height;
        }

        if(isDashing) {
            if(Math.abs(dashSpeed) >= 0.2) { // Dash in the player direction
            xSpeed += dashSpeed;
            dashSpeed -= airFriction;
            } else {
                dashSpeed = 0;
                isDashing = false; 
            }
        } else {
            if(!inAir) {
                    dashSpeed = 0;
                    canDash = true;
                }
        }
        if (right && !left) {
            xSpeed += moveSpeed;
            moving = right;
            playerDir = RIGHT;
        } else if (!right && left) {
            xSpeed -= moveSpeed;
            moving = left;
            playerDir = LEFT;
        }

        if(inAir) {
            hitbox.y += airSpeed;
            airSpeed += gravity;
        } 
            
    if(hitbox.x + xSpeed > 0 && hitbox.x + hitbox.width + xSpeed < GAME_WIDTH) {
        hitbox.x += xSpeed;
    } else {
        dashSpeed = 0;
    }
        updateAnimationTick();
		setAnimation();
    }

    public void draw(Graphics g) {
        drawHitbox(g);
        //g.drawImage(animations[state][animationIndex], (int) hitbox.x, (int) hitbox.y, null);
    }

    /**
     * Makes the player jump if they are on the ground 
     * @author Ryder Hodgson
     * @since December 16, 2023
     */
    
    public void jump() {
        if(inAir) {
            return;
        }
        inAir = true;
        airSpeed = jumpSpeed;
    }

    public void dash() {
        if(isDashing || !canDash) {
            return;
        }
        isDashing = true;
        canDash = false;
        if(playerDir == LEFT) {
            dashSpeed = -defaultDashSpeed;
            airFriction = -Math.abs(airFriction);
        } else {
            dashSpeed = defaultDashSpeed;
            airFriction = Math.abs(airFriction);
        }
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public void setInAir(boolean inAir) {
        this.inAir = inAir;
    }

    public void setUp(boolean up) {
        this.up = up;
    }

    public void setDown(boolean down) {
        this.down = down; 
    }

    /**
     * Loads the animations from the sprite atlas. 
     * @author Hamad Mohammed
     * @since December 16, 2023
     */
    public void Animations() {
        BufferedImage img = getSpriteAtlas(PLAYER_ATLAS);
        animations = new BufferedImage[4][4]; 
        for (int i = 0; i < animations.length; i++){
            for (int j = 0; j < animations[i].length; j++){
                animations[i][j] = img.getSubimage(j*64, i*64, 64, 64);
            }
        }
    }

    /**
     * Helps change images making it animate. 
     * @author Hamad Mohammed
     * @since December 16, 2023
     */
    private void updateAnimationTick() {
		animationTick++;
		if (animationTick >= animationSpeed) {
			animationTick = 0;
			animationIndex++;
			if (animationIndex >= GetSpriteAmount(state))
				animationIndex = 0;
		}
	}

    /**
     * Changes animations based on input. 
     * @author Hamad Mohammed
     * @since December 16, 2023
     */
	private void setAnimation() {
		if(moving && playerDir == 2)
		state = RUNNINGRIGHT; 
		else if (moving && playerDir == 0)
		state = RUNNINGLEFT; 
		else if (!moving && playerDir == 2) 
		state = IDLERIGHT; 
		else if (!moving && playerDir == 0)
		state = IDLELEFT;	
	}

    /**
     * Sets the direction the player is facing. 
     * @author Hamad Mohammed
     * @since December 16, 2023
     */
    public void setDirection(int direction){
		this.playerDir = direction; 
		moving = true; 
	}


}