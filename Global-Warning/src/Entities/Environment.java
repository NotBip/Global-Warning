package Entities;

import static Utilities.Atlas.WIND_ATLAS;
import static Utilities.Atlas.getSpriteAtlas;
import static Utilities.Constants.GAME_HEIGHT;
import static Utilities.Constants.GAME_WIDTH;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import Levels.Level;
import Levels.LevelManager;

public class Environment {
        
        private BufferedImage[][] img; 
        protected int aniTick, hitCooldown, aniSpeed = 20;
        protected int animationTick;
        protected int animationIndex;
        private Player player; 

    public Environment(Player player) { 
        this.player = player;
        Animations();
    }



    public void update() { 
        updateAnimationTick(); 
    }



    public void draw(Graphics g, int xOffset) {
        if(player.getWindy()) { 
        g.drawImage(this.img[0][animationIndex], (int) (0 - xOffset)-100 + GAME_WIDTH, 200, -GAME_WIDTH, GAME_HEIGHT-500, null);
        g.drawImage(this.img[0][animationIndex], (int) (0 - xOffset) + GAME_WIDTH, 100, -GAME_WIDTH+200, GAME_HEIGHT-600, null);
        }
        }



    /**
     * Loads the animations from the sprite atlas. 
     * @author Hamad Mohammed
     * @since December 16, 2023
     */
    public void Animations() {
        BufferedImage img = getSpriteAtlas(WIND_ATLAS);
        this.img = new BufferedImage[1][16]; 
        for (int i = 0; i < this.img.length; i++){
            for (int j = 0; j < this.img[i].length; j++)
                this.img[i][j] = img.getSubimage(j*514, i*514, 514, 514);
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
			animationIndex++;
			if (animationIndex >= 15)
				animationIndex = 0;
		}
	}
    
}