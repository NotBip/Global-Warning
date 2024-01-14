package Objects;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import Entities.Player;
import Entities.Planet1Enemies.Enemy2;
import GameStates.Playing;
import UserInterface.LoadSave;
import Utilities.Atlas;
import Utilities.Atlas.*;
import Objects.Object;
import Objects.Chest;
import Levels.Level;
import static Utilities.Constants.GAME_HEIGHT;
import static Utilities.Constants.GAME_WIDTH;
import static Utilities.Constants.*;

public class ObjectManager{
    
  Player player;      
  Object object; 
        private Level currentLevel; 

    public ObjectManager(Object object){
        this.object = object; 
    }

    public void loadObjects(Level level) { 
      this.currentLevel = level;  
    }

        public void draw(Graphics g, int xOffset) { 
            for (Chest o : currentLevel.getChest()) { 
               o.draw(g, xOffset);
           }

          //    for (Chest f : Fireboi) { 
          //    f.draw(g);
          //   }

        }

        public void update(int[][] levelData) {
          for (Chest o : currentLevel.getChest()) { 
            o.interactPlayer(player); 
          }
        }
}