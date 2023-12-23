package Entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import GameStates.Playing;
import UserInterface.LoadSave;
import Utilities.Atlas;
import Utilities.Atlas.*;
import Entities.Entity.*;
import Entities.Planet1Enemies.Enemy2;
import Levels.Levels;
import static Utilities.Constants.GAME_HEIGHT;
import static Utilities.Constants.GAME_WIDTH;
import static Utilities.Constants.EnemyConstants.*;

public class EnemyManager {

        Enemy2 pirate; 
        Player player; 

    public EnemyManager(Player player){
        this.player = player; 
    }
        public void generateEnemies() {
            pirate = new Enemy2(100, GAME_HEIGHT-80);
        }

        public void update() { 
            pirate.move(player);
        }

        public void draw(Graphics g) { 
             pirate.draw(g);
        }
}