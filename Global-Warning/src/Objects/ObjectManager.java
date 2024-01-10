package Objects;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

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
    
        Object object; 
        private Level currentLevel; 

    public ObjectManager(Object object){
        this.object = object; 
    }

    public void loadObjects(Level level) { 
      this.currentLevel = level;  
    }

        public void update(int[][] lvlData) { 
          for (Chest o : currentLevel.getChest()) {
             if (chestGet == false) {

             }
             else {

             }
         }
        }
}