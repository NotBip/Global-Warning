package Objects;

import static Utilities.Atlas.getSpriteAtlas;
import static Utilities.Atlas.KEY_ATLAS;
import java.awt.Graphics;
import Entities.Player;
import Main.Game;

/**
***********************************************
* @Author : Bobby Walden
* @Originally made : 21 JAN, 2024
* @Last Modified: 22 JAN, 2024
* @Description: Creates and calls the action for chests with keys.
***********************************************
*/

public class KeyChest extends Object {
  
    protected boolean chestInteract = false, chestOpen = false, chestOpened = false, marker1,marker2= false;
    private Player player;   
    public float yItem = 0;
    //public int wItem, hItem =0;
    public long lastTime = 0;

    // Initializes Key Chest
    public KeyChest(int x, int y, int object, Player player) { 
        super(x, y, object);
        this.player = player;
        this.aniSpeed = 25;
        initHitbox(68, 52);
        this.yItem = hitbox.y; 
        xDrawOffset = 0;
        yDrawOffset = (int)(Game.SCALE * (32-this.hitbox.height));
        hitbox.y += yDrawOffset;
    }
    
      /**
	 * @Method Name: drawItem
	 * @author Nusayba Hamou
	 * @since 21 JAN 2024
	 * @Description: resets getting item animation
	 * @Parameters: Graphics g, int x for offset
	 * @returns:N/A
	 * @Dependencies: Object, Atlas
	 * @Throws/Exceptions: N/A
	 **/

    public void drawItem (Graphics g, int x) {
        long time1 = System.currentTimeMillis();

        if (time1 > lastTime + 1000 ) {
           
            if (marker1){
                marker2= true;
             }

            marker1 = true;
            lastTime = time1;
           
        } else if (!marker2 ){
            g.drawImage(getSpriteAtlas(KEY_ATLAS), x, (int)yItem, 40,40, null );
          
            if (yItem> getHitbox().y -40){
                    yItem-=7;
                }
            } 
    }

    /**
	@Method Name: update
	@Author: Bobby Walden
	@Creation Date: 21 JAN, 2024
	@Modified Date: 21 JAN, 2024
	@Description: Updates the animations for the chest.
	@Parameters: N/A
	@Returns: N/A
	@Dependencies: Object
	@Throws/Exceptions: N/A
	*/
    public void update() { 
        updateAnimationTick(); 
    }

    /**
	@Method Name: giveItem
	@Author: Bobby Walden
	@Creation Date: 21 JAN, 2024
	@Modified Date: 21 JAN, 2024
	@Description: Gives the player a key.
	@Parameters: Player player
	@Returns: N/A
	@Dependencies: Player
	@Throws/Exceptions: N/A
	*/
    public void giveItem(Player player) {
        String item = "Key";
        System.out.println("You got a " + item + "!");
        player.gainItem(item, 1);
    }
/**
	 * @Method Name: resetKeyChests
	 * @author Nusayba Hamou
	 * @since 22 JAN 2024
	 * @Description: resets key chests every time a save is loaded
	 * @Parameters: N/A
	 * @returns:N/A
	 * @Dependencies: N/A
	 * @Throws/Exceptions: N/A
	 **/

     public void resetKeyChests () {
        chestInteract = false;
        chestOpen = false;
        chestOpened = false;   
    }

}