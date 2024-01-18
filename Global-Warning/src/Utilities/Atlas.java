package Utilities;

import java.awt.image.BufferedImage;
import java.io.InputStream;

import javax.imageio.ImageIO;

public class Atlas {
    // Importing Sprite Sheets and assigning them names
    public static final String PLAYER_ATLAS = "cautionaut.png";
    public static final String ZOMBIE_ATLAS = "RobotEnemy.png";
    public static final String WATERBOI_ATLAS = "Waterboi.png"; 
    public static final String FIREBOI_ATLAS = "Fireboi.png"; 

    public static final String WEAPON1_ATLAS = "gunSmol.png";
    public static final String WEAPON2_ATLAS = "pixel_weap_blue.png";
    public static final String WEAPON3_ATLAS = "pixel_weap_yellow.png";

     public static final String BOMB_ATLAS = "bomb_transparent.png";
     public static final String NOTHING_ATLAS = "no_bomb.png";

     public static final String KEY_ATLAS = "key_sprite.png";
     public static final String POTION_ATLAS = "healing_potion.png";
     public static final String GEM_ATLAS = "gem_blue.png";


    public static final String MENUBUTTON_ATLAS = "menuButtonRecolor.png";
    public static final String MENUBACKGROUND_ATLAS = "backgroundImage.png";
    public static final String MENUBACKGROUND_ATLAS_STORM = "storm_background.png";
    public static final String MENUBACKGROUND_ATLAS_FIRE = "fire_background.png";
    public static final String MENUBACKGROUND_ATLAS_ICE = "ice_background.png";
    public static final String MENUTITLE_ATLAS = "menuTitle.png";

    public static final String PAUSE_ATLAS = "pause_screen.png";
    public static final String OVER_ATLAS = "game_over.png";
    public static final String GAMEBUTTONS_ATLAS = "gamepause_buttons.png";

    public static final String SAVEBUTTON_ATLAS = "add_save_buttons.png";
    public static final String RESETBUTTON_ATLAS = "reset_save_buttons.png";
    public static final String SOUNDBUTTON_ATLAS = "sound_buttons.png";

    public static final String INVENTORY_ATLAS = "nice_inventory-overlay.png";
    public static final String INVENTORYSLOT_ATLAS = "inventory_slot_new.png";
    public static final String LOADING_ATLAS = "loading_sprites.png";

    public static final String HOVER_ATLAS = "all_hoverboxes.png";
    public static final String SELECTION_ATLAS = "select.png";
    public static final String CONTROLS_ATLAS = "controls_explain.png";

    public static final String WIND_ATLAS = "wind.png"; 
    public static final String SPIKE_ATLAS = "spike.png"; 
    public static final String CHEST_ATLAS = "Chests.png"; 
    public static final String LIGHTNING_ATLAS = "Lightning.png"; 
    public static final String DOOR_ATLAS = "door.png";
    public static final String BOMBEXPLODE_ATLAS = "bombExplode.png"; 

    public static final String FLAG_ATLAS = "flag_green.png";
    public static final String SAVED_ATLAS = "saved_text.png";






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
