package Objects;

import java.util.Random;

import Entities.Player;
import Main.Game;

/**
***********************************************
* @Author : Bobby Walden
* @Originally made : 15 JAN, 2024
* @Last Modified: 21 JAN, 2024
* @Description: Creates and calls the action for chests.
***********************************************
*/

public class Chest extends Object {
  
    public boolean chestInteract = false, chestOpen = false, chestOpened = false;  
    private Player player;   

    public Chest(int x, int y, int object) { 
        super(x, y, object);    
        this.aniSpeed = 25;
        initHitbox(68,52);
        xDrawOffset = 0;
        yDrawOffset = (int)(Game.SCALE * (32-this.hitbox.height));
        hitbox.y += yDrawOffset;
    }

    public void update() { 
        updateAnimationTick(); 
    }

    public void giveItem(Player player) {
        Random r = new Random();
        int randnum = r.nextInt(3-1) + 1;
        int quantity = 0;
        String item = "";
        switch (randnum) {
            case 1:
            item = "Potion";
            break;
            case 2:
            item = "Bomb";
            break;
        }
        randnum = r.nextInt(4 - 1) + 1;
        switch (randnum) {
            case 1:
            quantity = 1;
            break;
            case 2:
            if (item == "Potion") {
                quantity = 2;
            }
            else {
            quantity = 3;
            }
            break;
            case 3:
            if (item == "Potion") {
                quantity = 3;
            }
            else {
            quantity = 5;
            }
            break;
        }
        //inventory add "item", quantity
        System.out.println("You got " + quantity + " " + item + "!");
        player.gainItem(item, quantity);
    }


}