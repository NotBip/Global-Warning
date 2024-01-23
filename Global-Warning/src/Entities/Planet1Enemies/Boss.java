/**
 * @author: Hamad Mohammed
 * @since: 1 Jan 2024
 * @Last Modified: 21 Jan 2024
 * @Description: Class used to initialize an enemy using the Enemy Constructor. 
 */

package Entities.Planet1Enemies;

import static Utilities.Atlas.DEMONBOI_ATLAS;
import static Utilities.Constants.GAME_WIDTH;
import static Utilities.Constants.EnemyConstants.*;

import Entities.Enemy;

public class Boss extends Enemy {

    public Boss(float x, float y, int[][] lvlData) {
        super(x, y, DEMONBOI_WIDTH, DEMONBOI_HEIGHT, Demonboi, demonboiArrI, demonboiArrJ, demonboiW, demonboiH, DEMONBOI_ATLAS, 0, 1, demonSpeed, demonSizeX, demonSizeY);
        super.inAir = true; 
        super.lvlData = lvlData; 
        super.isBoss = true; 
        super.healthBarWidth = 500; 
        super.currentHealthBarLen = 500;
        super.healthBarHeight = 50; 
        super.enemyRangeWidth = GAME_WIDTH; 
        super.isActive = true;
    }
    
} // End Class
