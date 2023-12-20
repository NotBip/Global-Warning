package Entities;

import static Utilities.Constants.*;
import static Utilities.Constants.PlayerConstants.*;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import static Utilities.Constants.Directions.*;

import static Utilities.Atlas.*;

public class Player extends Entity {

    private BufferedImage[][] animations;
    private boolean moving = false;
    private boolean left, right, up, down;
    private int playerDir = -1;
    private float gravity = 0.04f;
    private float airFrictionX = 0.1f;
    private float airFrictionY = 0.2f;
    private float moveSpeed = 2.0f;
    private float jumpSpeed = -2.25f;
    private float defaultDashSpeed = 5f;
    private float dashXSpeed = 0;
    private float dashYSpeed = 0;
    private boolean isDashing = false;
    public boolean canDash = true;
    private int xFlipped = 0; 
    private int wFlipped = 1; 

    

    public Player(float x, float y, int width, int height) {
        super(x, y, width, height);
        this.state = IDLE;
        this.inAir = true;
        Animations();
        initialize();
    }

    /**
     * Updates the player (position and direction currently)
     * 
     * @author Ryder Hodgson
     * @since December 16, 2023
     */

    public void update() {
        moving = false; // Stop the player movement animation in case they stop moving this update
        xSpeed = 0;
       // airSpeed = 0;

        
            airSpeed += gravity;
        
        
        if(right && !left) {
            xSpeed = moveSpeed;
            playerDir = RIGHT;
            moving = true;
        } else if (left && !right) {
            xSpeed = -moveSpeed;
            playerDir = LEFT;
            moving = true;
        }

        if(isDashing) {
            if(Math.abs(dashXSpeed) >= 0.2) {
            xSpeed += dashXSpeed;
            dashXSpeed -= airFrictionX; 
            } else {
                isDashing = false;
            }

            if(Math.abs(dashYSpeed) >= 0.2) {
            airSpeed += dashYSpeed;
            dashYSpeed -= airFrictionY; 
            } else {
                isDashing = false;
            }
        }

        if(canMove(hitbox.x+xSpeed, hitbox.y, hitbox.width, hitbox.height)) {
            hitbox.x+=xSpeed;
        } else {
            moving = false;
            dashXSpeed = 0;
        }
        if(canMove(hitbox.x, hitbox.y+airSpeed, hitbox.width, hitbox.height)) {
            hitbox.y+=airSpeed;
        } else {
            if(!(airSpeed < 0)) {
                inAir = false;
            }
            
            dashYSpeed = 0;
            if(!isDashing) {
                canDash = true;
            }
        }

        // if (hitbox.y + hitbox.height + airSpeed >= GAME_HEIGHT && inAir) {
        //     inAir = false;
        //     hitbox.y = GAME_HEIGHT - hitbox.height;
        //     dashYSpeed = 0;
        //     if(!isDashing) {
        //             canDash = true;
        //     }
        // }

        // if (isDashing) {
        //     if (Math.abs(dashXSpeed) >= 0.2) { // Dash in the player direction
        //         xSpeed += dashXSpeed;
        //         dashXSpeed -= airFriction;
        //     } else {
        //         dashXSpeed = 0;
        //     }
        //     if (Math.abs(dashYSpeed) >= 0.2) { // Dash in the player direction
        //         airSpeed += dashYSpeed;
        //         dashYSpeed += gravity*5;
        //         System.out.println(airSpeed);
        //     } else {
        //         dashYSpeed = 0;
        //     }
        //     if (dashXSpeed == 0 && dashYSpeed == 0) {
        //         isDashing = false;
        //     }
        // }
        // if (right && !left) { // Move right
        //     xSpeed += moveSpeed;
        //     moving = right;
        //     playerDir = RIGHT;
        // } else if (!right && left) { // Move left
        //     xSpeed -= moveSpeed;
        //     moving = left;
        //     playerDir = LEFT;
        // }

        // if (hitbox.x + xSpeed > 0 && hitbox.x + hitbox.width + xSpeed < GAME_WIDTH) { // Check for collisions with game boarders
        //     hitbox.x += xSpeed;
        // } else { // Don't let the player dash through walls
        //     moving = false;
        //     dashXSpeed = 0;
        // }
        // if (hitbox.y + airSpeed > 0 && hitbox.y + hitbox.height + airSpeed < GAME_HEIGHT) {
            
        //     if (inAir) { // Fall
        //         hitbox.y += airSpeed;
        //         airSpeed += gravity;
        //     } 
  
                
        // }
        updateAnimationTick();
        setAnimation();
    }

    public void draw(Graphics g) {
        // drawHitbox(g);
        g.drawImage(animations[state][animationIndex], (int) hitbox.x + xFlipped, (int) hitbox.y, 55 * wFlipped, 65, null);

    }

    /**
     * Makes the player jump if they are on the ground
     * 
     * @author Ryder Hodgson
     * @since December 16, 2023
     */

    public void jump() {
        if (inAir) {
            return;
        }
        inAir = true;
        airSpeed = jumpSpeed;
    }

    public void dash() {  
        if (isDashing || !canDash) {
            return;
        }
        isDashing = true;
        canDash = false;
        if (left) {
            dashXSpeed = -defaultDashSpeed;
            airFrictionX = -Math.abs(airFrictionX);
        } else if (right) {
            dashXSpeed = defaultDashSpeed;
            airFrictionX = Math.abs(airFrictionX);
        }
        if (up) {
            dashYSpeed = -(defaultDashSpeed);
            airFrictionY = -Math.abs(airFrictionY);
        } else if (down && inAir) {
            dashYSpeed = defaultDashSpeed;
            airFrictionY = Math.abs(airFrictionY);
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
     * 
     * @author Hamad Mohammed
     * @since December 16, 2023
     */
    public void Animations() {
        BufferedImage img = getSpriteAtlas(PLAYER_ATLAS);
        animations = new BufferedImage[4][4];
        for (int i = 0; i < animations.length; i++) {
            for (int j = 0; j < animations[i].length; j++) {
                animations[i][j] = img.getSubimage(j * 64, i * 64, 64, 64);
            }
        }
    }

    /**
     * Helps change images making it animate.
     * 
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
     * 
     * @author Hamad Mohammed
     * @since December 16, 2023
     */
	private void setAnimation() {
		if(moving && playerDir == 2){
            state = RUNNING; 
            xFlipped = 0; 
            wFlipped = 1; 
        }
		else if (!moving && playerDir == RIGHT) 
		state = IDLE; 	
        else if(moving && playerDir == LEFT){
            state = RUNNING; 
            xFlipped = width; 
            wFlipped = -1; 
        }
		else if (!moving && playerDir == 0) 
		state = IDLE; 	
	}

    /**
     * Sets the direction the player is facing.
     * 
     * @author Hamad Mohammed
     * @since December 16, 2023
     */
    public void setDirection(int direction) {
        this.playerDir = direction;
        moving = true;
    }

}