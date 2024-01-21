package Objects;

import java.util.Random;

import Entities.Player;
import Main.Game;

public class KeyChest extends Object {
  
    protected boolean chestInteract = false, chestOpen = false, chestOpened = false;
    private Player player;   

    public KeyChest(int x, int y, int object, Player player) { 
        super(x, y, object);
        this.player = player;
        initHitbox(68, 52);
        xDrawOffset = 0;
        yDrawOffset = (int)(Game.SCALE * (32-this.hitbox.height));
        hitbox.y += yDrawOffset;
    }

    public void update() { 
        updateAnimationTick(); 
    }

    public void giveItem(Player player) {
        String item = "Key";
        System.out.println("You got a " + item + "!");
        player.gainItem(item, 1);
    }


}