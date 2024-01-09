package GameStates;

import Entities.*;
import Objects.Weapons.*;
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
import java.util.ArrayList;
import java.util.List;

public class Playing extends State implements KeyListener, MouseListener {
    private Player player;
    private static Weapon1 weapon;
    public int bulletCount;
    public List<Bullets> bullets;
    private EnemyManager enemyManager;
    private ObjectManager objectManager;
    private int borderLen = (int) (0.4 * GAME_WIDTH);
    private int xOffset;
    private int maxOffsetX;
    private LevelManager levelManager;
    private ObjectManager objectManager;
    private Pause pauseScreen;
    private InventoryState inventoryState;
    public static boolean paused, inventory = false;
    //private float borderLen;
    private double weaponAngle = 0;
    public static int gunIndex = 1;
    public double mouseX;
    public double mouseY;
    public double offset;

    public long lastBullet = 0;
    public long coolDown = 300; // 300 milliseconds


    public Playing(Game game) {
        super(game);
        initialize();
    }

    public void loadNextLevel() {

    }

    public void initialize() {
        player = new Player(10, GAME_HEIGHT - 100, 60, 80, this);
        weapon = new Weapon1(player, this);
        bullets = new ArrayList<>();

        pauseScreen = new Pause(this);
        inventoryState = new InventoryState(this);
    }

    public void update() {

        if (paused) {
			pauseScreen.update();
		} else if (inventory && !paused){
			inventoryState.update();
		} else {
            player.update();
            weapon.update();
         for (int i = 0; i < bullets.size(); i++) {
            bullets.get(i).updateBullets();
         }
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
        weapon.draw(g);
        for (int i = 0; i < bullets.size(); i++) {
            bullets.get(i).draw(g);
        }

        if (inventory) {
            inventoryState.draw(g);
        }

        if (paused) {
			pauseScreen.draw(g);
		} 
        player.draw(g, xOffset);
        enemyManager.draw(g, xOffset);
        levelManager.draw(g, xOffset);
    }

    public void reset() {

    }

     public static void setGunIndex(int item){
        if (item <3){
            gunIndex = item;
            weapon.getImage();
        }

    }

    public Weapon1 getWeapon1() {
        return weapon;
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

    public double getAngle() {
        return weaponAngle;
    }

    /**
	 * Adds a cooldown between bullets shot
	 * 
	 * @referenced: https://gamedev.stackexchange.com/questions/158616/how-do-i-create-a-delay-or-cooldown-timer
	 * @author Nusayba Hamou
	 * @since December 29, 2023
	 */

    public void bulletCooldown(MouseEvent e) {
        long time1 = System.currentTimeMillis();
        if (time1 > lastBullet + coolDown) {
            spawnBullet(e.getX(), e.getY());
            lastBullet = time1;
        }
    }

    /**
	 * Creates a new bullet and adds it to the bullet list
	 * 
	 * @author Hamad Mohammed
	 * @since December 25, 2023
	 */

    private void spawnBullet(int x, int y) {

       if (!paused && !inventory){
            Bullets bullet = new Bullets(weapon, this, weapon.getX() + 50, weapon.getY() + 35, x, y);
             bullets.add(bullet);
        }

    }

    /**
	 * Removes bullet from bullet list
	 * 
	 * @author Hamad Mohammed
	 * @since December 25, 2023
	 */

    public void removeBullet() {
        bullets.remove(0);
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
            case KeyEvent.VK_ESCAPE:
                if (inventory){
                    inventory = false;
                } else {
                    paused = !paused;
                }
                 break;
             case KeyEvent.VK_I:
                 if (!paused){
			         inventory = !inventory;
                 }
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

    /**
	 * Gets location of mouse cursor and updates weapon angle
	 * 
	 * @author Hamad Mohammed
	 * @since December 25, 2023
	 */

    public void mouseMoved(MouseEvent e) {

        mouseX = e.getX();
        mouseY = e.getY();

        if (!paused && !inventory) {
            if (mouseX < weapon.getX()) {

                offset = 1.6;
            } else {
                offset = -1.5;
            }

            double deltaX = weapon.getX() - mouseX;
            double deltaY = weapon.getY() - mouseY;

            
            weaponAngle = -Math.atan2(deltaX, deltaY) + offset;
        }

       if (paused)
			pauseScreen.mouseMoved(e);

         if (inventory)
			inventoryState.mouseMoved(e);
        

    }

     /**
	 * Applies cooldown if bullet is shot (mouse clicked)
	 * 
	 * @author Nusayba Hamou
	 * @since December 29, 2023
	 */

    public void mouseClicked(MouseEvent e) {
        bulletCooldown(e);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    /**
	 * Applies cooldown if bullet is shot (mouse dragged; click with movement)
	 * 
	 * @author Hamad Mohammed
	 * @since December 30, 2023
	 */

    public void mouseDragged(MouseEvent e) {
        bulletCooldown(e);

        if (paused)
			pauseScreen.mouseDragged(e);

        if (inventory)
            inventoryState.mouseDragged(e);
    }

   
    @Override
    public void mousePressed(MouseEvent e) {
       if (paused)
			pauseScreen.mousePressed(e);

        if (inventory)
            inventoryState.mousePressed(e);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
       if (paused)
			pauseScreen.mouseReleased(e);

        if (inventory)
            inventoryState.mouseReleased(e);
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'mouseEntered'");
    }

    @Override
    public void mouseExited(MouseEvent e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'mouseExited'");
    @Override
    public void keyTyped(KeyEvent e) {

    }

}