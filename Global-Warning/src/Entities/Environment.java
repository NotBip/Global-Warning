package Entities;

import static Utilities.Atlas.LIGHTNING_ATLAS;
import static Utilities.Atlas.WIND_ATLAS;
import static Utilities.Atlas.getSpriteAtlas;
import static Utilities.Constants.GAME_HEIGHT;
import static Utilities.Constants.GAME_WIDTH;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import GameStates.Playing;
import Levels.Level;
import Levels.LevelManager;

public class Environment {
        
        private BufferedImage[][] windImg, lightningImg; 
        protected int aniTick, hitCooldown, aniSpeed = 20;
        protected int animationTick;
        protected int animationWindIndex, animationLightningIndex;
        private Playing playing; 

    public Environment(Playing playing) { 
        this.playing = playing; 
        windAnimations();
        lightningAnimations();
    }



    public void update() { 
        updateAnimationTick(); 
    }

    // g.fillRect((int) lightningHitbox.x - xOffset, (int) lightningHitbox.y, (int) lightningHitbox.width, (int) lightningHitbox.height);


    public void draw(Graphics g, int xOffset) {
        if(playing.getPlayer().getWindy()) { 
            g.drawImage(this.windImg[0][animationWindIndex], (int) (0 - xOffset)-100 + GAME_WIDTH, 200, -GAME_WIDTH, GAME_HEIGHT-500, null);
            g.drawImage(this.windImg[0][animationWindIndex], (int) (0 - xOffset) + GAME_WIDTH, 100, -GAME_WIDTH+200, GAME_HEIGHT-600, null);
        }
    }

    public void drawLightning(Graphics g, int xOffset) { 
        System.out.println("asdasd");
            g.drawImage(this.lightningImg[0][animationLightningIndex], (int) playing.lightningHitbox.x - xOffset - 20, (int) -100, (int) 214, (int) GAME_HEIGHT, null);
    }



    /**
     * Loads the animations from the sprite atlas. 
     * @author Hamad Mohammed
     * @since December 16, 2023
     */
    public void windAnimations() {
        BufferedImage img = getSpriteAtlas(WIND_ATLAS);
        this.windImg = new BufferedImage[1][16]; 
        for (int i = 0; i < this.windImg.length; i++){
            for (int j = 0; j < this.windImg[i].length; j++)
                this.windImg[i][j] = img.getSubimage(j*514, i*514, 514, 514);
        }       
    }

    /**
     * Loads the animations from the sprite atlas. 
     * @author Hamad Mohammed
     * @since December 16, 2023
     */
    public void lightningAnimations() {
        BufferedImage img = getSpriteAtlas(LIGHTNING_ATLAS);
        this.lightningImg = new BufferedImage[1][12]; 
        for (int i = 0; i < this.lightningImg.length; i++){
            for (int j = 0; j < this.lightningImg[i].length; j++)
                this.lightningImg[i][j] = img.getSubimage(j*114 , i*114 , 114, 114 );
        }       
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
			animationWindIndex++;
            animationLightningIndex++; 
			if (animationWindIndex >= 15)
				animationWindIndex = 0;
            if (animationLightningIndex >= 12)
                animationLightningIndex = 0; 
		}
	}
    
}