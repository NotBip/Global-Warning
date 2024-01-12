package Entities.Planet1Enemies;

import Entities.Enemy;

import static Utilities.Atlas.FIREBOI_ATLAS;
import static Utilities.Constants.EnemyConstants.*;

public class Enemy1 extends Enemy {

   public Enemy1(float x, float y, int[][] lvlData) {
        super(x, y, FIREBOI_WIDTH, FIREBOI_HEIGHT, Fireboi, fireboiArrI, fireboiArrJ, fireboiW, fireboiH, FIREBOI_ATLAS, FIREBOI_WIDTH, -1, fireSpeed,  fireSizeX, fireSizeY);
        super.inAir = true;
        super.lvlData = lvlData;
}


}