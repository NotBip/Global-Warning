/**
 * @author: Hamad Mohammed
 * @since: 20 Jan 2024
 * @Last Modified: 21 Jan 2024
 * @Description: Class used for the animations and mecahnics of the fireballs spawned by the first boss in the game. 
 */

package Entities.Planet1Enemies;

import static Utilities.Atlas.FIREBALL_ATLAS;
import static Utilities.Atlas.getSpriteAtlas;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import Entities.Entity;
import GameStates.Playing;

public class Fireballs extends Entity {

    private BufferedImage[][] fireballAnimations;
    private int animationTick, animationIndex, aniSpeed = 15;
    private Playing playing; 

    public Fireballs(float x, float y, Playing playing) {
        super(x, y, 70, 20);
        this.x = x; 
        this.y = y; 
        super.inAir = true; 
        this.playing = playing;
        loadImage();
        playing.getSoundLibrary().playSound("Fireball");
        initialize();
    }


    /** 
     * @MethodName: update()
     * @author: Hamad Mohammed
     * @since Jan 20 2024
     * @param N/A
     * @Description: Updates the animation tick and moves the fireball down on the y axis. 
     * @returns: N/A
     * @Dependencies: N/A
     * @Throws/Exceptions: N/A
     */
    public void update() { 
        hitbox.y += 2; // move the fireball down by 2 pixels each tick.
        updateAnimationTick(); 
    }


    /** 
     * @MethodName: loadImage()
     * @author: Hamad Mohammed
     * @since Jan 20 2024
     * @param N/A
     * @Description: Loads the sprite sheet for the fireball to be used to for animations later on. 
     * @returns: N/A
     * @Dependencies: Atlas.java
     * @Throws/Exceptions: N/A
     */
    private void loadImage() { 
        BufferedImage fireball = getSpriteAtlas(FIREBALL_ATLAS); 
        fireballAnimations = new BufferedImage[1][5]; 
        for (int i = 0; i < fireballAnimations.length; i++) {
            for (int j = 0; j < fireballAnimations[i].length; j++) {
                fireballAnimations[i][j] = fireball.getSubimage(j * 66, i * 34, 66, 34);
            }
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
    private void updateAnimationTick() {
        animationTick++;
        if (animationTick >= aniSpeed) {
            animationTick = 0;
            animationIndex++;
            if (animationIndex >= 5)
                animationIndex = 0;
        }
    }

    /** 
     * @MethodName: drawFireBall()
     * @author: Hamad Mohammed
     * @since Jan 20 2024
     * @param xOffset The x-Position offset of the image. 
     * @param g Graphics from the java awt library. 
     * @Description: rotates the Fireball image and draws it. 
     * @returns: N/A
     * @Dependencies: N/A
     * @Throws/Exception: N/A
     */
    public void drawFireBall(Graphics g, int xOffset) { 
        Graphics2D g2d = (Graphics2D)g; 
        AffineTransform oldXForm = g2d.getTransform();
        g2d.rotate(Math.PI/2, hitbox.x, hitbox.y); // Rotate the fireball image 90 degrees or pi/2 radians. 
        g.drawImage(fireballAnimations[0][animationIndex], (int) hitbox.x - 40 - xOffset, (int) hitbox.y-34, 120, 80, null);
        g2d.setTransform(oldXForm); // reset so it doesn't flip anything else. 
    }

} // End Class
