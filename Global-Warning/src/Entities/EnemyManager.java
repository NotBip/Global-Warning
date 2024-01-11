package Entities;

import java.awt.Graphics;
import Entities.Planet1Enemies.Enemy2;
import Levels.Level;

public class EnemyManager {

        Player player; 
        private Level currentLevel; 

    public EnemyManager(Player player){
        this.player = player; 
    }

    public void loadEnemies(Level level) { 
      this.currentLevel = level;  
    }

        public void update(int[][] lvlData) { 
          for (Enemy2 o : currentLevel.getWaterBoi()) { 
           o.move(player, lvlData); 
         }

          //  for (Enemy1 f : Fireboi) { 
          //   f.move(player);
          //  }
        }

        public void draw(Graphics g, int xOffset) { 
            for (Enemy2 o : currentLevel.getWaterBoi()) { 
               o.draw(g, xOffset);
           }

          //    for (Enemy1 f : Fireboi) { 
          //    f.draw(g);
          //   }

        }
        
        public void resetEnemies() {
          for(Enemy2 e : currentLevel.getWaterBoi()) {
            e.resetEnemy();
          }
        }
}