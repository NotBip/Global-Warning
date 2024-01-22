package Objects;

import static Utilities.Atlas.POTION_ATLAS;
import static Utilities.Atlas.BOMB_ATLAS;
import static Utilities.Atlas.getSpriteAtlas;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
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
  
    protected boolean chestInteract = false, chestOpen = false, chestOpened = false, marker1,marker2 =false; 
    private Player player;   
    BufferedImage img = getSpriteAtlas(POTION_ATLAS);
    public float yItem = 0;
    public int wItem, hItem, offset= 0;
    public long lastTime = 0;

    public Chest(int x, int y, int object, Player player) { 
        super(x, y, object);    
        this.aniSpeed = 25;
        this.player = player; 
        initHitbox(68,52);
        xDrawOffset = 0;
        yDrawOffset = (int)(Game.SCALE * (32-this.hitbox.height));
        hitbox.y += yDrawOffset;
        this.yItem = getHitbox().y;
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
            img = getSpriteAtlas(POTION_ATLAS);
            wItem = 90;
            hItem = 100;
            offset =20;
            break;
            case 2:
            item = "Bomb";
            img = getSpriteAtlas(BOMB_ATLAS);
            wItem = 40;
            hItem = 40;
            offset = 0;
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

    public void drawItem (Graphics g, int x) {
        //System.out.println("Hello");
        long time1 = System.currentTimeMillis();

        if (time1 > lastTime + 1000 ) {
           
            if (marker1){
                marker2= true;
             }

            marker1 = true;
            lastTime = time1;
           
        } else if (!marker2 ){
            g.drawImage(img, x-offset, (int)yItem, wItem, hItem, null );
          
            if (yItem> getHitbox().y -40){
                    yItem-=7;
                }
            } 
    }

    public void resetAnimation () {
        yItem = getHitbox().y;
        marker1 = false;
        marker2 = false;
    }

    /**
	 * @Method Name: resetChests
	 * @author Nusayba Hamou
	 * @since 21 JAN 2024
	 * @Description: resets chests every time a save is loaded
	 * @Parameters: N/A
	 * @returns:N/A
	 * @Dependencies: N/A
	 * @Throws/Exceptions: N/A
	 **/

    public void resetChests () {
        chestInteract = false;
        chestOpen = false;
        chestOpened = false;   
    }





}