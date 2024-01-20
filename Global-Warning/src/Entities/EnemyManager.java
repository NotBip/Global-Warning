package Entities;

import java.awt.Graphics;
import java.util.List;

import Entities.Planet1Enemies.Boss;
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
        o.move(player, lvlData, playing);
        o.enemyHit(bullet, playing);
        o.checkLightningIntersect(playing);
      }
    }

    for (Enemy1 f : currentLevel.getFireBoi()) {
      if(!f.isDead()) {
        f.move(player, lvlData, playing);
        f.enemyHit(bullet, playing);
        f.checkLightningIntersect(playing);
      }
    }

    for (Boss b : currentLevel.getDemonBoi()) { 
      if(!b.isDead()) { 
        b.move(player, lvlData, playing);
        b.enemyHit(bullet, playing);
      }
    }
  }  catch (Exception e) {}
  }


  public void draw(Graphics g, int xOffset) {
    for (Enemy2 o : currentLevel.getWaterBoi()) {
      if(!o.isDead()) {
        o.draw(g, xOffset);
        o.drawHealth(g, xOffset);
      }
    }

    for (Enemy1 f : currentLevel.getFireBoi()) {
      if(!f.isDead()) {
        f.draw(g, xOffset);
        f.drawHealth(g, xOffset);
      }
    }

    for (Boss b : currentLevel.getDemonBoi()) { 
      if(!b.isDead()) { 
        b.draw(g, xOffset);
        b.drawHealth(g, xOffset);
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
    for (Boss b : currentLevel.getDemonBoi()) { 
      b.resetEnemy();
    }
  }
}