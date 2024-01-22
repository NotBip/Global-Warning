/**
 * @author: Hamad Mohammed
 * @since: 20 Jan 2024
 * @Last Modified: 21 Jan 2024
 * @Description: Class used to draw the player's heatlh bar. 
 */


package UserInterface;

import static Utilities.Atlas.*;
import static Utilities.Constants.PlayerConstants.DEAD;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import GameStates.Playing;

public class HealthBar {

    private BufferedImage healthFrame, healthBar, oxygenBar; 
    public float widthHP = 1443/4, widthO2 = 1443/4; 
    public float widthHPOffset = 0, widthO2Offset = 0;
    private boolean removeHP = false; 
    private boolean addHP = false; 
    private boolean removeO2 = false; 
    private boolean addO2 = false; 
    private Playing playing; 

    public HealthBar(Playing playing) { 
        loadImage(); 
        widthHP = 1443/4; 
        widthO2 = 1443/4;
        widthHPOffset = 0;
        widthO2Offset = 0;
        this.playing = playing; 
        
    }

     /** 
     * @MethodName: update()
     * @author: Hamad Mohammed
     * @since Jan 20 2024
     * @param N/A
     * @Description: updates the player hp bar and oxygen bar. 
     * @returns: N/A
     * @Dependencies: N/A
     * @Throws/Exceptions: N/A
     */
    public void update() { 

        if (playing.getPlayer().checkWater(playing.getPlayer().getHitbox().x, playing.getPlayer().getHitbox().y, playing.getLevelManager().getCurrentLevel().lvlData))
            removeO2();
        else 
            addO2();  

        if(playing.getPlayer().currentHealth == playing.getPlayer().maxHealth) {
            widthHP = 1443/4;
            widthHPOffset = 0; 
        }
    }
    


     /** 
     * @MethodName: removeHP()
     * @author: Hamad Mohammed
     * @since Jan 20 2024
     * @param percent Percent of HP being removed from the healthbar ex: 50%, 20%, 10%.
     * @Description: reduces the player hp depending on the percentage. 
     * @returns: N/A
     * @Dependencies: Playing.java
     * @Throws/Exceptions: N/A
     */
    public void removeHP(double percent) { 
        if(playing.getPlayer().getState() != DEAD) { 
            if(!removeHP) { 
                widthHPOffset = 0;
                widthHP = 1443/4; 
                removeHP = true; 
            }

            if(widthHP <= 1443/4) { 
                widthHP = widthHP - (int) ((1443/4)*(percent));
                widthHPOffset += (int) ((1443/4)*(percent)) / 3.27;
            }
            else 
            widthHP = 0; 
        }
    }
    


     /** 
     * @MethodName: addHP()
     * @author: Hamad Mohammed
     * @since Jan 20 2024
     * @param percent Percent of HP being added to the healthbar ex: 50%, 20%, 10%.
     * @Description: adds the player hp depending on the percentage. 
     * @returns: N/A
     * @Dependencies: Playing.java
     * @Throws/Exceptions: N/A
     */
    public void addHP(double percent) { 
        if(!addHP) { 
            widthHPOffset = 115;
            widthHP = 0; 
            addHP = true; 
        } 

        if(widthHP <= 1443/4) { 
            widthHP = widthHP + (int) ((1443/4)*(percent));;
            widthHPOffset -= (int) ((1443/4)*(percent)) / 3.27;
        }

    }



    /** 
     * @MethodName: removeO2()
     * @author: Hamad Mohammed
     * @since Jan 20 2024
     * @param: N/A
     * @Description: slowly reduces the player's oxygen bar if the player is underwater. 
     * @returns: N/A
     * @Dependencies: Playing.java
     * @Throws/Exceptions: N/A
     */
    public void removeO2() { 
        if(playing.getPlayer().getState() != DEAD) { 
            if(!removeO2) { 
                widthO2Offset = 0;
                widthO2 = 1443/4; 
                removeO2 = true; 
            }

            if(widthO2 >= 0) { 
                widthO2 -= .5/1.7;
                widthO2Offset += .157/1.7;
            }
        }
    }

    

    /** 
     * @MethodName: addO2()
     * @author: Hamad Mohammed
     * @since Jan 20 2024
     * @param: N/A
     * @Description: slowly increases the player's oxygen bar if the player is underwater. 
     * @returns: N/A
     * @Dependencies: Playing.java
     * @Throws/Exceptions: N/A
     */
    public void addO2() { 
        if(playing.getPlayer().getState() != DEAD) { 
            if(!addO2) { 
                widthO2Offset = 115;
                widthO2 = 0; 
                addO2 = true; 
            } 

            if(widthO2 <= 1443/4) { 
                widthO2 += .5/1.7;
                widthO2Offset -= .157/1.7;
            }
        }
    }



    /** 
     * @MethodName: loadImage()
     * @author: Hamad Mohammed
     * @since Jan 20 2024
     * @param N/A
     * @Description: Loads the sprite sheet for the healthFrame, health bar and oxygen bar. 
     * @returns: N/A
     * @Dependencies: Atlas.java
     * @Throws/Exceptions: N/A
     */
    private void loadImage() { 
        BufferedImage img = getSpriteAtlas(HEALTH_ATLAS);
        healthFrame = img.getSubimage(0, 0, 1443, 450); 
        healthBar = img.getSubimage(0, 450, 1443, 450); 
        oxygenBar = img.getSubimage(0, 900, 1443, 450); 
    }



     /** 
     * @MethodName: draw()
     * @author: Hamad Mohammed
     * @since Jan 20 2024
     * @param xOffset The x-Position offset of the image. 
     * @param g Graphics from the java awt library. 
     * @Description: draws all the images needed for the health bar. 
     * @returns: N/A
     * @Dependencies: N/A
     * @Throws/Exception: N/A
     */
    public void draw(Graphics g, int xOffset) { 
        g.drawImage(healthFrame, 50, 30, 1443/4, 450/4, null);
        g.drawImage(healthBar, 50 + (int) widthHPOffset, 43, (int) widthHP, 450/4, null);
        g.drawImage(oxygenBar, 50 + (int) widthO2Offset, 79, (int) widthO2, 450/4, null);
    }


    
}
