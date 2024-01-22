package Objects;

import java.util.Random;

import Entities.Player;
import Main.Game;

/**
***********************************************
* @Author : Bobby Walden
* @Originally made : 21 JAN, 2024
* @Last Modified: 21 JAN, 2024
* @Description: Creates and calls the action for chests with keys.
***********************************************
*/

public class KeyChest extends Object {
  
    protected boolean chestInteract = false, chestOpen = false, chestOpened = false;
    private Player player;   

    // Initializes Key Chest
    public KeyChest(int x, int y, int object, Player player) { 
        super(x, y, object);
        this.player = player;
        initHitbox(68, 52);
        xDrawOffset = 0;
        yDrawOffset = (int)(Game.SCALE * (32-this.hitbox.height));
        hitbox.y += yDrawOffset;
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


}