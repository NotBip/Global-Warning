package Entities;

import java.awt.Graphics;
import java.util.List;
import Entities.Planet1Enemies.Enemy1;
import Entities.Planet1Enemies.Enemy2;
import GameStates.Playing;
import Levels.Level;
import Objects.ObjectManager;
import Objects.Weapons.Bullets;


public class EnemyManager {

  Player player;
  private Level currentLevel;

  public EnemyManager(Player player) {
    this.player = player;
  }

  public void loadEnemies(Level level) {
    this.currentLevel = level;
  }

  public void update(int[][] lvlData, List<Bullets> bullet, Playing playing, ObjectManager objectManager) {
  try { 
    for (Enemy2 o : currentLevel.getWaterBoi()) {
      if(!o.isDead()) {
        o.move(player, lvlData);
        o.enemyHit(bullet, playing);
      }
    }

    for (Enemy1 f : currentLevel.getFireBoi()) {
      if(!f.isDead()) {
        f.move(player, lvlData);
        f.enemyHit(bullet, playing);
      }
      
      
    }
  }  catch (Exception e) {}
  }


  public void draw(Graphics g, int xOffset) {
    for (Enemy2 o : currentLevel.getWaterBoi()) {
      if(!o.isDead()) {
        o.draw(g, xOffset);
      }
      
    }

    for (Enemy1 f : currentLevel.getFireBoi()) {
      if(!f.isDead()) {
        f.draw(g, xOffset);
      }
      
    }

  }


  public void resetEnemies() {
    for (Enemy2 e : currentLevel.getWaterBoi()) {
      e.resetEnemy();
    }

    for (Enemy1 e : currentLevel.getFireBoi()) {
      e.resetEnemy();
    }
  }
}