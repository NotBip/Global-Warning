package Entities.Planet1Enemies;

import static Utilities.Atlas.PIRATE_ATLAS;
import static Utilities.Constants.EnemyConstants.*;

import Entities.Enemy;

public class Enemy2 extends Enemy {

    public Enemy2(float x, float y) {
        super(x, y, PIRATE_WIDTH, PIRATE_HEIGHT, Pirate, pirateArrI, pirateArrJ, pirateW, pirateH, PIRATE_ATLAS, PIRATE_WIDTH, -1, pirateSpeed, pirateSizeX, pirateSizeY);    
        super.inAir = true; 

    }
    
}
