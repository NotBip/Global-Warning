package Entities.Planet1Enemies;

import Entities.Enemy;

import static Utilities.Atlas.COOLBOI_ATLAS;
import static Utilities.Atlas.KEY_ATLAS;
import static Utilities.Atlas.getSpriteAtlas;
import static Utilities.Constants.GAME_HEIGHT;
import static Utilities.Constants.GAME_WIDTH;
import static Utilities.Constants.Directions.LEFT;
import static Utilities.Constants.Directions.RIGHT;
import static Utilities.Constants.EnemyConstants.*;

import java.awt.Graphics;

public class Boss2 extends Enemy {

    private long lastTime = 0; 
    protected boolean chestInteract = false, chestOpen = false, chestOpened = false, marker1,marker2= false;
    public float yItem = 0;
    public int wItem, hItem = 20;
    private int x; 
    public boolean droppedKey = false; 

    public Boss2(float x, float y, int[][] lvlData) {
        super(x, y, COOLBOI_WIDTH, COOLBOI_HEIGHT, Coolboi, coolboiArrI, coolboiArrJ, coolboiW, coolboiH, COOLBOI_ATLAS, 0, 1, coolSpeed, coolSizeX, coolSizeY);
        
        super.inAir = true; 
        super.lvlData = lvlData; 
        super.isBoss = true; 
        super.healthBarWidth = 500; 
        super.healthBarHeight = 50; 
        super.enemyRangeWidth = GAME_WIDTH/3; 
        super.enemyRangeHeight = GAME_HEIGHT; 
        super.bossXOffset = 20; 
        super.bossYOffset = 30;
        this.yItem = hitbox.y + hitbox.height;  
        this.direction = RIGHT; 
        System.out.println("ASD");
    }


    public void keyAnimation (Graphics g, int xOffset) {
        long time1 = System.currentTimeMillis();

        if (time1 > lastTime + 1000 ) {
           
            if (marker1){
                marker2= true;
             }

            marker1 = true;
            lastTime = time1;
           
        } else if (!marker2 ){
            g.drawImage(getSpriteAtlas(KEY_ATLAS), (int) this.hitbox.x - xOffset, (int) yItem, 40,40, null );
          
            if (yItem > 400){
                    yItem-= 3;
                }
            } 
    }
    
}
