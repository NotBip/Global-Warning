package GameStates;

import Entities.*;
import Entities.Planet1Enemies.Enemy1;
import Entities.Planet1Enemies.Enemy2;
import Levels.LevelManager;
import Main.Game;
import Objects.ObjectManager;

import static Utilities.Constants.GAME_HEIGHT;
import static Utilities.Constants.GAME_WIDTH;
import static Utilities.Constants.TILE_SIZE;
import Entities.EnemyManager.*;
import java.awt.Graphics;
import java.awt.event.*;

public class Playing extends State implements KeyListener {
    private Player player;
    private EnemyManager enemyManager;
    private LevelManager levelManager;
    private ObjectManager objectManager;
    private boolean paused;
    private int borderLen = (int) (0.4 * GAME_WIDTH);
    private int xOffset;
    private int maxOffsetX;

    public Playing(Game game) {
        super(game);
        initialize();
    }

    /**
     * Loads the new level
     * 
     * @author Ryder Hodgson
     * @since January 1st, 2024
     * @param spawnType 0 = checkpoint, 1 = next room left, 2 = next room right
     */

    public void loadNextLevel(int spawnType) {
        levelManager.loadNextLevel();
        switch(spawnType) {
            case 1: player.setSpawn(levelManager.getCurrentLevel().getLeftSpawn()); break;
            case 2: player.setSpawn(levelManager.getCurrentLevel().getRightSpawn()); break;
            case 0:
            default: player.setSpawn(levelManager.getCurrentLevel().getPlayerSpawn());
        }
    }

    public void initialize() {
        levelManager = new LevelManager(this);
        player = new Player(GAME_WIDTH / 2, GAME_HEIGHT / 2 - 50, 45, 63); // Default spawn point
        enemyManager = new EnemyManager(player);
        levelManager.loadNextLevel();
        player.loadLevelData(levelManager.getCurrentLevel().getLevelData());
        try{ // Catch errors if the room has no default spawn point
            player.setSpawn(levelManager.getCurrentLevel().getPlayerSpawn());
        } catch(Exception e) { // Will spawn the player at its initialization spawning coordinates
            System.out.println("no default spawn point found");
        }
        enemyManager.loadEnemies(levelManager.getCurrentLevel());

    }

    public void update() {
        player.update();
        checkBorder();
        checkTransition();
        enemyManager.update(levelManager.getCurrentLevel().getLevelData());
    }

     /**
     * Checks if that player has gone past the camera border
     * 
     * @author Ryder Hodgson
     * @since January 1st, 2024
     */

    public void checkBorder() {
        int distance = (int) player.getHitbox().x - xOffset; // The distance between the player and the camera border

        if (distance > GAME_WIDTH - borderLen) { // Check for if the player has passed the rightmost border
            xOffset += distance - (GAME_WIDTH - borderLen);
        }
        if (distance < borderLen) { // Check for if the player has passed the leftmost border
            xOffset += distance - borderLen;
        }

        if (xOffset >= maxOffsetX) { // Lock the camera to the right on the right edge of the room
            xOffset = maxOffsetX;
        }
        if (xOffset <= 0) { // Lock the camera to the left on the left edge of the room
            xOffset = 0;
        }
    }

    /**
     * Checks if that player has touched a transition point, if so, put them into the next corresponding room
     * 
     * @author Ryder Hodgson
     * @since January 1st, 2024
     */

    public void checkTransition() {
        // Check left door
        if (levelManager.getCurrentLevel().getLeftTransition() != null) { // Is there a left door?
            if (player.getHitbox().x <= levelManager.getCurrentLevel().getLeftTransition().x // Checking if touching left door
                    && levelManager.getLevelIndex() > 0) { // Make sure there is a level to transition into

                // Load previous room
                levelManager.setLevelIndex(levelManager.getLevelIndex() - 1); 
                if(levelManager.getCurrentLevel().getRightSpawn() != null) { // Make sure the room has a point to send you to, otherwise send you to default point
                    loadNextLevel(2);
                } else {
                    System.out.println("no right spawn point found");
                    loadNextLevel(0);
                }
                
                return;
            }
        }

        // Check right door
        if (levelManager.getCurrentLevel().getRightTransition() != null) { // Is there a right door?
            if (player.getHitbox().x + player.getHitbox().width >= levelManager.getCurrentLevel().getRightTransition().x // Check if touching right door
                    && levelManager.getLevelIndex() < levelManager.getAmountOfLevels()-1) { // Make sure there is a level to transition into
                
                // Load next room
                levelManager.setLevelIndex(levelManager.getLevelIndex() + 1);
                if(levelManager.getCurrentLevel().getRightSpawn() != null) { // Make sure the room has a point to send you to, otherwise send you to default point
                    loadNextLevel(1);
                } else {
                    System.out.println("no left spawn point found");
                    loadNextLevel(0);
                }
                return;
            }
        }
    }

    public void draw(Graphics g) {
        player.draw(g, xOffset);
        enemyManager.draw(g, xOffset);
        levelManager.draw(g, xOffset);
    }

    public void reset() {

    }

    public void setMaxLvlOffset(int lvlOffset) {
        this.maxOffsetX = lvlOffset;
    }

    public Player getPlayer() {
        return player;
    }

    public LevelManager getLevelManager() {
        return levelManager;
    }

    public ObjectManager getObjectManager() {
        return objectManager;
    }

    public EnemyManager getEnemyManager() {
        return enemyManager;
    }

    public void keyPressed(KeyEvent e) {

        switch (e.getKeyCode()) {
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
                player.dash();
                break;

        }
    }

    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
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