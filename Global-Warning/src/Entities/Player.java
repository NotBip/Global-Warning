package Entities;
import static Utilities.Constants.*;
import static Utilities.Constants.PlayerConstants.*;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import static Utilities.Constants.Directions.*;
import javax.imageio.ImageIO;
import Objects.Weapons.*;


import static Utilities.Atlas.*;
import Utilities.Atlas;

public class Player extends Entity {

    private BufferedImage[][] animations; 
    private boolean moving = false; 
    private boolean left, right, up, down;
    private int playerDir = -1; 
	private BufferedImage img;
    private float gravity = 0.04f;
    private float jumpSpeed = -2.25f;

    public Player(float x, float y, int width, int height) {
        super(x, y, width, height);
        this.state = IDLERIGHT; 
        //this.weapon = weapon;
        this.xSpeed = 2.0f;
        this.inAir = true;
        Animations(); 
        initialize();
    }


    public void update() {
        moving = false;
        if(hitbox.y+hitbox.height > GAME_HEIGHT && inAir) {
            inAir = false;
            hitbox.y = GAME_HEIGHT-hitbox.height;
        }

        if (right && !left && hitbox.x + xSpeed < GAME_WIDTH - hitbox.width) {
            hitbox.x += xSpeed;
            moving = right;
            playerDir = RIGHT;
        } else if (!right && left && hitbox.x + xSpeed > 0) {
            hitbox.x -= xSpeed;
            moving = left;
            playerDir = LEFT;
        }

        if(inAir) {
            hitbox.y += airSpeed;
            airSpeed += gravity;
        }

        updateAnimationTick();
		setAnimation();
     //   weapon.update();
        
    }

    public void draw(Graphics g) {
        //hamad did this
       // weapon.draw(g);
        drawHitbox(g);
        g.drawImage(animations[state][animationIndex], (int) hitbox.x, (int) hitbox.y, null);
    }

    public void jump() {
        if(inAir) {
            return;
        }
        inAir = true;
        airSpeed = jumpSpeed;
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