package Entities.Planet1Enemies;

import static Utilities.Atlas.*;
import static Utilities.Constants.EnemyConstants.*;

import Entities.Enemy;

public class Enemy2 extends Enemy {

    public Enemy2(float x, float y, int[][] lvldata) {
        super(x, y, WATERBOI_WIDTH+20, WATERBOI_HEIGHT, Waterboi, waterArrI, waterArrJ, waterW, waterH, WATERBOI_ATLAS, WATERBOI_WIDTH, -1, waterSpeed, waterSizeX, waterSizeY);    
        super.inAir = true; 
        super.lvlData = lvldata; 
    }
    
}
