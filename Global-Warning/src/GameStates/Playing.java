package GameStates;

import Entities.*;
import Entities.Planet1Enemies.Enemy2;
import Levels.LevelManager;
import Main.Game;
import Objects.ObjectManager;

import static Utilities.Constants.GAME_HEIGHT;
import static Utilities.Constants.GAME_WIDTH;
import Entities.EnemyManager.*;
import java.awt.Graphics;
import java.awt.event.*;

public class Playing extends State implements KeyListener{
    private Player player;
    private EnemyManager enemyManager; 
    private LevelManager levelManager;
    private ObjectManager objectManager;
    private boolean paused;
    private float borderLen;

    public Playing(Game game) {
        super(game);
        initialize();
    }


    public void loadNextLevel() {

    }

    public void initialize() {
        player = new Player(80, GAME_HEIGHT-100,45, 62);
        enemyManager = new EnemyManager(player); 
        enemyManager.generateEnemies();
    }

    public void update() {
        player.update();
        enemyManager.update();
    }

    public void checkBorder() {

    }

    public void draw(Graphics g) {
            player.draw(g);
            enemyManager.draw(g);
        
    }

    public void reset() {

    }

    public Player getPlayer() {
        return player;
    }

    public LevelManager getLevelManager() {
        return levelManager;
    }

    public ObjectManager getObjectManager(){
        return objectManager;
    }

    public void keyPressed(KeyEvent e) {

        switch(e.getKeyCode()) {
            case KeyEvent.VK_A:
            player.setLeft(true);
            break;
            case KeyEvent.VK_D:
            player.setRight(true);
            break;
            case KeyEvent.VK_W:
            player.setUp(true);
            break;
            case KeyEvent.VK_S:
            player.setDown(true);
            break;
            case KeyEvent.VK_SPACE:
            
            player.jump();
            break;
            case KeyEvent.VK_SHIFT:
            if(player.canDash) {
                player.dash();
            }
            
        }
    }

    public void keyReleased(KeyEvent e) {
        switch(e.getKeyCode()) {
            case KeyEvent.VK_A:
            player.setLeft(false);
            break;
            case KeyEvent.VK_D:
            player.setRight(false);
            break;
            case KeyEvent.VK_W:
            player.setUp(false);
            break;
            case KeyEvent.VK_S:
            player.setDown(false);
            break;
        }
    }


    @Override
    public void keyTyped(KeyEvent e) {
        
    }

}
