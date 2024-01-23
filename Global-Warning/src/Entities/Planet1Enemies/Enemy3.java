package Entities.Planet1Enemies;

import static Utilities.Atlas.SHARDBOI_ATLAS;
import static Utilities.Constants.EnemyConstants.*;
import Entities.Enemy;

public class Enemy3 extends Enemy {

    public Enemy3(float x, float y, int[][] lvlData) {
        super(x, y, SHARDBOI_WIDTH, SHARDBOI_HEIGHT, Shardboi, shardboiArrI, shardboiArrJ, shardboiW, shardboiH, SHARDBOI_ATLAS, SHARDBOI_WIDTH, -1, shardSpeed, shardSizeX, shardSizeY);
        super.inAir = true; 
        super.lvlData = lvlData; 
        super.maxHealth = getMaxEnemyHealth(Waterboi);
        super.isBoss = false; 
        bossXOffset = 0; 
        bossYOffset = 55; 
    }
}
