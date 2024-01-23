package Entities.Planet1Enemies;

import Entities.Enemy;

import static Utilities.Atlas.COOLBOI_ATLAS;
import static Utilities.Constants.GAME_WIDTH;
import static Utilities.Constants.EnemyConstants.*;

public class Boss2 extends Enemy {

    public Boss2(float x, float y, int[][] lvlData) {
        super(x, y, COOLBOI_WIDTH, COOLBOI_HEIGHT, Coolboi, coolboiArrI, coolboiArrJ, coolboiW, coolboiH, COOLBOI_ATLAS, COOLBOI_WIDTH, -1 , coolSpeed, coolSizeX, coolSizeY);
        super.inAir = true; 
        super.lvlData = lvlData; 
        super.isBoss = true; 
        super.healthBarWidth = 500; 
        super.healthBarHeight = 50; 
        super.enemyRangeWidth = GAME_WIDTH-200; 
         super.bossXOffset = 100; 
         super.bossYOffset = 35; 
    }
    
}
