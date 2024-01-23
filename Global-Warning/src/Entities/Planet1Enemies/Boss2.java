package Entities.Planet1Enemies;

import Entities.Enemy;

import static Utilities.Atlas.COOLBOI_ATLAS;
import static Utilities.Atlas.KEY_ATLAS;
import static Utilities.Atlas.getSpriteAtlas;
import static Utilities.Constants.GAME_WIDTH;
import static Utilities.Constants.EnemyConstants.*;

import java.awt.Graphics;

public class Boss2 extends Enemy {

    private long lastTime = 0; 
    protected boolean chestInteract = false, chestOpen = false, chestOpened = false, marker1,marker2= false;
    public float yItem = 0;
    public int wItem, hItem =40;
    private int x; 

    public Boss2(float x, float y, int[][] lvlData) {
        super(x, y, COOLBOI_WIDTH, COOLBOI_HEIGHT, Coolboi, coolboiArrI, coolboiArrJ, coolboiW, coolboiH, COOLBOI_ATLAS, COOLBOI_WIDTH, -1 , coolSpeed, coolSizeX, coolSizeY);
        
        super.inAir = true; 
        super.lvlData = lvlData; 
        super.isBoss = true; 
        super.healthBarWidth = 500; 
        super.healthBarHeight = 50; 
        super.enemyRangeWidth = GAME_WIDTH/2; 
         super.bossXOffset = -90; 
         super.bossYOffset = 35; 
    }

        public void keyAnimation (Graphics g, int x) {
            
        long time1 = System.currentTimeMillis();
        if (time1 > lastTime + 1000 ) {
           
            if (marker1){
                marker2= true;
             }

            marker1 = true;
            lastTime = time1;
           
        } else if (!marker2 ){
            g.drawImage(getSpriteAtlas(KEY_ATLAS), (int) hitbox.x, (int)yItem, wItem, hItem, null );
          
            if (yItem> getHitbox().y - 200){
                    yItem-=7;
                }
            } 
    }
    
}
