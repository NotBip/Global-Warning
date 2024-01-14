package Entities;

import java.awt.Graphics;
import java.util.List;

import Entities.Planet1Enemies.Enemy1;
import Entities.Planet1Enemies.Enemy2;
import GameStates.Playing;
import Levels.Level;
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

  public void update(int[][] lvlData, List<Bullets> bullet, Playing playing) {
  try { 
    for (Enemy2 o : currentLevel.getWaterBoi()) {
      o.move(player, lvlData);
      o.enemyHit(bullet, playing);
      if(o.isDead())
      currentLevel.getWaterBoi().remove(o); 
    }

    for (Enemy1 f : currentLevel.getFireBoi()) {
      f.move(player, lvlData);
      f.enemyHit(bullet, playing);
      if(f.isDead())
      currentLevel.getFireBoi().remove(f); 
    }
  }  catch (Exception e) {}
  }


  public void draw(Graphics g, int xOffset) {
    for (Enemy2 o : currentLevel.getWaterBoi()) {
      o.draw(g, xOffset);
    }

    for (Enemy1 f : currentLevel.getFireBoi()) {
      f.draw(g, xOffset);
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