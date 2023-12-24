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
import Levels.Levels;
import static Utilities.Constants.GAME_HEIGHT;
import static Utilities.Constants.GAME_WIDTH;
import static Utilities.Constants.EnemyConstants.*;

public class EnemyManager {

        ArrayList<Enemy2> pirate = new ArrayList<Enemy2>(); 
        ArrayList<Enemy1> Fireboi = new ArrayList<Enemy1>(); 
        Player player; 

    public EnemyManager(Player player){
        this.player = player; 

    }
        public void generateEnemies() {
        pirate.add(new Enemy2(100, GAME_HEIGHT-80)) ;
        Fireboi.add(new Enemy1(100, GAME_HEIGHT-600)); 
        }

        public void update() { 
           for (Enemy2 o : pirate) { 
            o.move(player); 
           }

           for (Enemy1 f : Fireboi) { 
            f.move(player);
           }
        }

        public void draw(Graphics g) { 
            for (Enemy2 o : pirate) { 
            o.draw(g);
           }

             for (Enemy1 f : Fireboi) { 
             f.draw(g);
            }

        }
}