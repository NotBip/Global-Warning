package Objects;

import static Utilities.Atlas.getSpriteAtlas;
import static Utilities.Atlas.KEY_ATLAS;

import java.awt.Graphics;
import java.util.Random;

import Entities.Player;
import Main.Game;

public class KeyChest extends Object {
  
    protected boolean chestInteract = false, chestOpen = false, chestOpened = false, marker1,marker2= false;
    private Player player;   
    public float yItem = 0;
    public int wItem, hItem =40;
    public long lastTime = 0;

    public KeyChest(int x, int y, int object, Player player) { 
        super(x, y, object);
        this.player = player;
        initHitbox(68, 52);
        xDrawOffset = 0;
        yDrawOffset = (int)(Game.SCALE * (32-this.hitbox.height));
        hitbox.y += yDrawOffset;
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
            g.drawImage(getSpriteAtlas(KEY_ATLAS), x, (int)yItem, wItem, hItem, null );
          
            if (yItem> getHitbox().y -40){
                    yItem-=7;
                }
            } 
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