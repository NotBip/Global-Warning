/**
 * @author: Hamad Mohammed
 * @since: 15 Jan 2024
 * @Last Modified: 20 Jan 2024
 * @Description: Class used for environmental mechanics such as lightning and wind. 
 */

package Entities;

import static Utilities.Atlas.LIGHTNING_ATLAS;
import static Utilities.Atlas.WIND_ATLAS;
import static Utilities.Atlas.getSpriteAtlas;
import static Utilities.Constants.GAME_HEIGHT;
import static Utilities.Constants.GAME_WIDTH;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import GameStates.Playing;

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


     /** 
     * @MethodName: update()
     * @author: Hamad Mohammed
     * @since: 20 Jan 2024
     * @param N/A
     * @Description: Updates the animation tick. 
     * @returns: N/A
     * @Dependencies: N/A
     * @Throws/Exceptions: N/A
     */
    public void update() { 
        updateAnimationTick(); 
    }


     /** 
     * @MethodName: draw()
     * @author: Hamad Mohammed
     * @since Jan 20 2024
     * @param xOffset The x-Position offset of the image. 
     * @param g Graphics from the java awt library. 
     * @Description: draws the wind animation. 
     * @returns: N/A
     * @Dependencies: Playing.java
     * @Throws/Exception: N/A
     */
    public void draw(Graphics g, int xOffset) {
        if(playing.getPlayer().getWindy()) { 
            g.drawImage(this.windImg[0][animationWindIndex], (int) (0 - xOffset)-100 + GAME_WIDTH, 200, -GAME_WIDTH, GAME_HEIGHT-500, null);
            g.drawImage(this.windImg[0][animationWindIndex], (int) (0 - xOffset) + GAME_WIDTH, 100, -GAME_WIDTH+200, GAME_HEIGHT-600, null);
        }
    }

     /** 
     * @MethodName: draw()
     * @author: Hamad Mohammed
     * @since Jan 20 2024
     * @param xOffset The x-Position offset of the image. 
     * @param g Graphics from the java awt library. 
     * @Description: draws the lightning animation. 
     * @returns: N/A
     * @Dependencies: N/A
     * @Throws/Exception: N/A
     */
    public void drawLightning(Graphics g, int xOffset) { 
        g.drawImage(this.lightningImg[0][animationLightningIndex], (int) playing.lightningHitbox.x - xOffset - 20, (int) -(GAME_HEIGHT-playing.lightningHeight), (int) 214, (int) GAME_HEIGHT, null);
    }



    /** 
     * @MethodName: windAnimations()
     * @author: Hamad Mohammed
     * @since Jan 20 2024
     * @param N/A
     * @Description: loads the wind sprite image to be used later on for animations. 
     * @returns: N/A
     * @Dependencies: Atlas.java
     * @Throws/Exceptions: N/A
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
     * @MethodName: lightningAnimations()
     * @author: Hamad Mohammed
     * @since Jan 20 2024
     * @param N/A
     * @Description: loads the lightning sprite image to be used later on for animations. 
     * @returns: N/A
     * @Dependencies: Atlas.java
     * @Throws/Exceptions: N/A
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
     * @MethodName: updateAnimationTick()
     * @author: Hamad Mohammed
     * @since Jan 20 2024
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
			animationWindIndex++;
            animationLightningIndex++; 
			if (animationWindIndex >= 15)
				animationWindIndex = 0;
            if (animationLightningIndex >= 12)
                animationLightningIndex = 0; 
		}
	}
    
}