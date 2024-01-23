/**
 * @author: Hamad Mohammed
 * @since: 20 Jan 2024
 * @Last Modified: 21 Jan 2024
 * @Description: Class used for the animations and mecahnics of the fireballs spawned by the first boss in the game. 
 */

 package Entities.Planet1Enemies;

 import static Utilities.Atlas.FIREBALL_ATLAS;
 import static Utilities.Atlas.OBELISK_ATLAS;
 import static Utilities.Atlas.getSpriteAtlas;
 import java.awt.Graphics;
 import java.awt.image.BufferedImage;
 import GameStates.Playing;
 
 public class Obelisk {
 
     private BufferedImage[][] ObeliskAnimations;
     private int animationTick, animationIndex, aniSpeed = 15;
     private Playing playing; 
     private float x, y; 
 
     public Obelisk(float x, float y, Playing playing) {
         this.playing = playing;
         this.x = x; 
         this.y = y; 
         loadImage();
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
         BufferedImage obelisk = getSpriteAtlas(OBELISK_ATLAS); 
         ObeliskAnimations = new BufferedImage[1][13]; 
         for (int i = 0; i < ObeliskAnimations.length; i++) {
             for (int j = 0; j < ObeliskAnimations[i].length; j++) {
                ObeliskAnimations[i][j] = obelisk.getSubimage(j * 200, i * 400, 200, 400);
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
      * @MethodName: drawObelisk()
      * @author: Hamad Mohammed
      * @since Jan 20 2024
      * @param xOffset The x-Position offset of the image. 
      * @param g Graphics from the java awt library. 
      * @Description: Method responsible for drawing Obelisk 
      * @returns: N/A
      * @Dependencies: N/A
      * @Throws/Exception: N/A
      */
     public void drawObelisk(Graphics g, int xOffset) { 
         g.drawImage(ObeliskAnimations[0][animationIndex], (int)x - xOffset, (int) y, 32, 32, null);
     }
 
 } // End Class
 