/**
 * @author: Hamad Mohammed
 * @since: 1 Jan 2024
 * @Last Modified: 21 Jan 2024
 * @Description: Class used to initialize an enemy using the Enemy Constructor. 
 */

package Entities.Planet1Enemies;

import static Utilities.Atlas.*;
import static Utilities.Constants.EnemyConstants.*;
import Entities.Enemy;

public class Enemy2 extends Enemy {

    public Enemy2(float x, float y, int[][] lvlData) {
        super(x, y, WATERBOI_WIDTH+20, WATERBOI_HEIGHT, Waterboi, waterArrI, waterArrJ, waterW, waterH, WATERBOI_ATLAS, WATERBOI_WIDTH, -1, waterSpeed, waterSizeX, waterSizeY);    
        super.inAir = true; 
        super.lvlData = lvlData; 
        super.maxHealth = getMaxEnemyHealth(Waterboi);
        super.isBoss = false; 
        bossXOffset = 0; 
        bossYOffset = 0; 

    }
} // End Class
