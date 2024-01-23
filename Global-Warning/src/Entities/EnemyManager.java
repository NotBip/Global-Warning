/**
 * @author: Hamad Mohammed
 * @since: 20 Dec 2024
 * @Last Modified: 15 Jan 2024
 * @Description: Class used to manage each enemy instance. 
 */

package Entities;

import java.awt.Graphics;
import java.util.List;
import Entities.Planet1Enemies.Boss;
import Entities.Planet1Enemies.Enemy1;
import Entities.Planet1Enemies.Enemy2;
import GameStates.GameState;
import GameStates.Playing;
import GameStates.ToBeContinued;
import Levels.Level;
import Objects.ObjectManager;
import Objects.Weapons.Bullets;


public class EnemyManager {

  Player player;
  private Level currentLevel;

  public EnemyManager(Player player) {
    this.player = player;
  }
 
  
   /** 
   * @MethodName: loadEnemies()
   * @author: Hamad Mohammed
   * @since: 20 Dec 2024
   * @param level current Level. 
   * @Description: Load the level data for each level. 
   * @returns: N/A
   * @Dependencies: N/A
   * @Throws/Exceptions: N/A
   */
  public void loadEnemies(Level level) {
    this.currentLevel = level;
  }


   /** 
   * @MethodName: update()
   * @author: Hamad Mohammed
   * @since: 20 Dec 2024
   * @param lvlData the int 2D array which contains the lvlData
   * @param bullet The ArrayList of bullets. 
   * @param playing The current instance of the playing class. 
   * @param objectManger The current instance of the object Manager for interactions.
   * @Description: updates the action for each enemy and each instance of that enemy. 
   * @returns: N/A
   * @Dependencies: Playing.java, ObjectManager.java, Bullets.java, Level.java
   * @Throws/Exceptions: Exception e
   */
  public void update(int[][] lvlData, List<Bullets> bullet, Playing playing, ObjectManager objectManager) {
  try { 
    for (Enemy2 o : currentLevel.getWaterBoi()) {
      if(!o.isDead()) {
        o.move(player, playing);
        o.enemyHit(bullet, playing);
        o.checkLightningIntersect(playing);
      }
    }

    for (Enemy1 f : currentLevel.getFireBoi()) {
      if(!f.isDead()) {
        f.move(player, playing);
        f.enemyHit(bullet, playing);
        f.checkLightningIntersect(playing);
      }
    }

    for (Boss b : currentLevel.getDemonBoi()) { 
      if(!b.isDead()) { 
        b.move(player, playing);
        b.enemyHit(bullet, playing);
      }
    }
  }  catch (Exception e) {}
  }


     /** 
     * @MethodName: draw()
     * @author: Hamad Mohammed
     * @since: Dec 15 2024
     * @param xOffset The x-Position offset of the image. 
     * @param g Graphics from the java awt library. 
     * @Description: draws each enemy sprite and their hp bars. 
     * @returns: N/A
     * @Dependencies: Enemy.java, Level.java
     * @Throws/Exception: N/A
     */
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
      } else{
        Playing.endGame = true;
      }
    }
  }

 

   /** 
   * @MethodName: resetEnemies()
   * @author: Hamad Mohammed
   * @since: Dec 15 2024
   * @param: N/A 
   * @description: Responsible for resetting all instances of enemies when called. 
   * @returns: N/A
   * @Dependencies: Enemy.java, Level.java
   * @Throws/Exception: N/A
   */
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