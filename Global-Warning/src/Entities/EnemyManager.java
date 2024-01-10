package Entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import GameStates.Playing;
import UserInterface.LoadSave;
import Utilities.Atlas;
import Utilities.Atlas.*;
import Entities.Entity.*;
import Entities.Planet1Enemies.Enemy1;
import Entities.Planet1Enemies.Enemy2;
import Levels.Level;
import static Utilities.Constants.GAME_HEIGHT;
import static Utilities.Constants.GAME_WIDTH;
import static Utilities.Constants.EnemyConstants.*;

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