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
import static Utilities.Constants.EnemyConstants.Zombie;
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
    private boolean checkingTransition = false;

    public Playing(Game game) {
        super(game);
        initialize();
    }

    /**
     * Loads the new level
     * 
     * @author Ryder Hodgson
     * @since January 1st, 2024
     * @param spawnType whether the player is transitioning from a door, or spawning at a checkpoint (0 = checkpoint, 1 = left, 2 = right)
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
        // enemyManager = new EnemyManager(player);
        levelManager = new LevelManager(this);

        player = new Player(GAME_WIDTH / 4, GAME_HEIGHT / 2 - 50, 45, 63);
        levelManager.loadNextLevel();
        player.loadLevelData(levelManager.getCurrentLevel().getLevelData());
        player.setSpawn(levelManager.getCurrentLevel().getPlayerSpawn());

        // enemyManager.generateEnemies();
    }

    public void update() {
        player.update();
        checkBorder();
        checkTransition();

        // checkingTransition = false;
        // enemyManager.update();
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

        if (xOffset >= maxOffsetX) { // Lock the camera to the right
            xOffset = maxOffsetX;
        }
        if (xOffset <= 0) { // Lock the camera to the left
            xOffset = 0;
        }
    }

    public void checkTransition() {
        if (levelManager.getCurrentLevel().getLeftTransition() != null) {
            if (player.getHitbox().x <= levelManager.getCurrentLevel().getLeftTransition().x
                    && levelManager.getLevelIndex() > 0) {

                levelManager.setLevelIndex(levelManager.getLevelIndex() - 1);
                loadNextLevel(2);
                return;

            }
        }
        if (levelManager.getCurrentLevel().getRightTransition() != null) {
            if (player.getHitbox().x+ player.getHitbox().width >= levelManager.getCurrentLevel().getRightTransition().x
                    && levelManager.getLevelIndex() < levelManager.getAmountOfLevels()-1) {

                levelManager.setLevelIndex(levelManager.getLevelIndex() + 1);
                loadNextLevel(1);
                return;
            }
        }
    }

    public void draw(Graphics g) {
        player.draw(g, xOffset);
        // enemyManager.draw(g, offset);
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
