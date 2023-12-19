package GameStates;

import Entities.*;
import Entities.Planet1Enemies.Enemy1;
import Entities.Planet1Enemies.Enemy2;
import Levels.LevelManager;
import Main.Game;
import Objects.ObjectManager;

import static Utilities.Constants.GAME_HEIGHT;
import static Utilities.Constants.GAME_WIDTH;
import static Utilities.Constants.EnemyConstants.Zombie;

import java.awt.Graphics;
import java.awt.event.*;

public class Playing extends State implements KeyListener{
    private Player player;
    private Enemy1 enemy;
    private Enemy2 pirate;  
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
        player = new Player(10, GAME_HEIGHT-100, 60, 80);
        enemy = new Enemy1(10, GAME_HEIGHT-80); 
        pirate = new Enemy2(GAME_WIDTH-50, GAME_HEIGHT-80);
    }

    public void update() {
        player.update();
        enemy.move(player); 
        pirate.move(player);
    }

    public void checkBorder() {

    }

    public void draw(Graphics g) {
            player.draw(g);
            enemy.draw(g);
            pirate.draw(g);
        
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
