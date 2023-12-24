package Utilities;

import java.awt.image.BufferedImage;
import java.io.InputStream;

import javax.imageio.ImageIO;

public class Atlas {
    // Importing Sprite Sheets and assigning them names
    public static final String PLAYER_ATLAS = "MC.png";
    public static final String ZOMBIE_ATLAS = "RobotEnemy.png";
    public static final String PIRATE_ATLAS = "player_sprites.png"; 
    public static final String FIREBOI_ATLAS = "Fireboi.png"; 

     /**
      * 
      * Takes in a image filename and returns a buffered image QOL
      * @param filename The name of the image file being read. 
      * @return Buffered Image of the image that has been read. 
      * @author Hamad Mohammed
      * @since December 16, 2023
      */
    public static BufferedImage getSpriteAtlas(String filename){
        BufferedImage img = null; 
        InputStream is = Atlas.class.getResourceAsStream("/" + filename); 
        try { 
            img = ImageIO.read(is); 
        } catch (Exception e) {

        }
        finally {
            try {
                is.close();
            } catch (Exception e) {}
        }
        return img; 
    }

    
}
