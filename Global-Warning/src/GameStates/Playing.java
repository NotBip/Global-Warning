package GameStates;

import Entities.*;
import Objects.Weapons.*;
import UserInterface.SaveButton;
import Objects.Saving.*;
import Utilities.LoadSave;

import Levels.LevelManager;
import Main.Game;
import Objects.ObjectManager;
import Objects.Spike;
import Objects.Saving.Checkpoint;
import static Utilities.Atlas.MENUBACKGROUND_ATLAS;
import static Utilities.Constants.GAME_HEIGHT;
import static Utilities.Constants.GAME_WIDTH;
import static Utilities.Constants.HEIGHT_IN_TILES;
import static Utilities.Constants.TILE_SIZE;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.*;
import java.io.IOException;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

public class Playing extends State implements KeyListener, MouseListener {
    public static Weapon1 weapon;
    public  Player player;

    public int bulletCount;
    public List<Bullets> bullets;
    private EnemyManager enemyManager;
    private ObjectManager objectManager;
    private int borderLen = (int) (0.4 * GAME_WIDTH);
    private int xOffset;
    private int maxOffsetX;
    public int numFile = Checkpoint.fileNum;
    public static LevelManager levelManager;
    private Pause pauseScreen;
    private InventoryState inventoryState;
    private Environment environment; 
    private Image backgroundImage;
    public static boolean paused, inventory = false;
    private double weaponAngle = 0;
    public static int gunIndex = 1;
    public double mouseX;
    public double mouseY;
    public double offset;
    private boolean playerDying; 

    //cooldown for firerate (later to be upgradeable to lower cooldown)
    public long lastBullet = 0;
    public static long fireRateWeapon1 = 300; // 300 milliseconds
    public static long fireRateWeapon2 = 250; // 300 milliseconds
   // public  int num = SaveButton.getFileNum();


    private int lightningUpdates; // The total updates that have passed before a complete lightning cycle
    private int lightningPosCooldown = 480; // How long it takes before the lightning chooses where to strike
    private int lightningSpawnCooldown = 60; // How long it takes after choosing a position for lightning to strike
    public float lightningPosX;
    public float lightningHeight;
    public Rectangle2D.Float lightningHitbox;
    public boolean lightningHasPos = false;

    public Playing(Game game) {
        super(game);
        System.out.println("we're in session!");
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
        enemyManager.resetEnemies();
        levelManager.loadNextLevel();
        resetLightning();
        enemyManager.loadEnemies(levelManager.getCurrentLevel());
        switch(spawnType) {
            case 1: player.setSpawn(levelManager.getCurrentLevel().getLeftSpawn()); break;
            case 2: player.setSpawn(levelManager.getCurrentLevel().getRightSpawn()); break;
            case 0:
            default: player.setSpawn(levelManager.getCurrentLevel().getPlayerSpawn());
        }
    }

    public void initialize() {
        levelManager = new LevelManager(this);
        player = new Player(1200, GAME_HEIGHT / 2 - 50, 62, 68); // Default spawn point
        objectManager = new ObjectManager(this); 
        objectManager.loadObjects(levelManager.getCurrentLevel().getLevelData());
        enemyManager = new EnemyManager(player);
        levelManager.loadNextLevel();
        weapon = new Weapon1(player, this);
        bullets = new ArrayList<>();
       // savepoint = new Checkpoint(GAME_WIDTH / 2-300, 100, 45, 63, this);
        pauseScreen = new Pause(this);
        inventoryState = new InventoryState(this);
        player.loadLevelData(levelManager.getCurrentLevel().getLevelData());
        backgroundImage = LoadSave.GetSpriteAtlas(MENUBACKGROUND_ATLAS);
        this.environment = new Environment(this); 

        try{ // Catch errors if the room has no default spawn point
            player.setSpawn(levelManager.getCurrentLevel().getPlayerSpawn());
        } catch(Exception e) { // Will spawn the player at its initialization spawning coordinates
            System.out.println("no default spawn point found");
        }
        enemyManager.loadEnemies(levelManager.getCurrentLevel());
    }

    public void update() throws IOException {
        System.out.println(this.getLevelManager().getCurrentLevel().getStormy());
        if (paused) {
			pauseScreen.update();
		} else if (inventory && !paused){
			inventoryState.update();
		} else {
            player.update(this);
            weapon.update();
            if (getLevelManager().getCurrentLevel().getIsCheckpoint())
            getLevelManager().getCurrentLevel().getCheckpoint().update();
         for (int i = 0; i < bullets.size(); i++) {
            bullets.get(i).updateBullets();
         }
        updateLightning();
        checkLightningIntersect();
        checkBorder();
        checkTransition();
        
        enemyManager.update(levelManager.getCurrentLevel().getLevelData(), bullets, this, getObjectManager());
        objectManager.update();
        environment.update();
    }
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

    private void updateLightning() {
        if(levelManager.getCurrentLevel().getStormy()) {
            lightningUpdates++;
            if(lightningUpdates >= lightningPosCooldown && !lightningHasPos) {
                lightningPosX = player.getHitbox().x + player.getMoveSpeed();
                int currentTileX = (int) (lightningPosX / TILE_SIZE);
                 for(int i = 0; i < HEIGHT_IN_TILES; i++) {
                    if(levelManager.getCurrentLevel().getLevelData()[i][currentTileX] != 11) {
                        lightningHeight = i * TILE_SIZE;
                        break;
                    }
                }
                lightningHasPos = true;
                lightningHitbox = new Rectangle2D.Float(lightningPosX, 0, 32, lightningHeight);
            }
            if(lightningUpdates >= lightningPosCooldown + lightningSpawnCooldown * 2) {
               resetLightning();
            }
        } else {
            resetLightning();
        }
    }

    private void drawLightning(Graphics g, int xOffset) {
        if(lightningUpdates >= lightningPosCooldown && lightningHasPos) {
            g.setColor(Color.RED);
            g.drawRect((int) lightningHitbox.x - xOffset, (int) lightningHitbox.y, (int) lightningHitbox.width, (int) lightningHitbox.height);
        }

        if(lightningUpdates >= lightningPosCooldown + lightningSpawnCooldown && lightningHasPos && lightningHitbox != null) 
            environment.drawLightning(g, xOffset);
        
    }

    private void checkLightningIntersect() {
        if(lightningHitbox != null && lightningUpdates >= lightningPosCooldown + lightningSpawnCooldown)
        if(player.getHitbox().intersects(lightningHitbox) && lightningHasPos && !player.isImmune()) { // fix when this happens
            player.changeHealth(-50);
        }
    }

    private void resetLightning() {
        lightningHasPos = false;
        lightningUpdates = 0;
        lightningHitbox = null;
    }

    public void draw(Graphics g) throws IOException {
        g.drawImage(backgroundImage, 0, 0, null);
        environment.draw(g, xOffset);
        weapon.draw(g, xOffset);
        player.draw(g, xOffset);
        enemyManager.draw(g, xOffset);
        levelManager.draw(g, xOffset);
        if (getLevelManager().getCurrentLevel().getIsCheckpoint())
        getLevelManager().getCurrentLevel().getCheckpoint().draw(g, xOffset);
        objectManager.draw(g, xOffset);
        drawLightning(g, xOffset);
        player.drawHealthBar(g);
        player.drawOxygenBar(g);
        
        for (int i = 0; i < bullets.size(); i++) {
            bullets.get(i).draw(g, xOffset);
        }

        if (inventory) {
            inventoryState.draw(g);
        }

        if (paused) {
			pauseScreen.draw(g);
		}
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

    public void bulletCooldown(int x, int y) {
        long time1 = System.currentTimeMillis();
        long rate = 0;

        //check which gun is being used
        if (gunIndex ==1 ){
            rate = fireRateWeapon1;
        }else if (gunIndex ==2 ) {
            rate = fireRateWeapon2;
        }

        //cooldown according to the firerate of gun
        if (time1 > lastBullet + rate) {
            spawnBullet(x, y);
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
            Bullets bullet = new Bullets(weapon, this, weapon.getX() + 50, weapon.getY() + 35, x, y, xOffset, levelManager.getCurrentLevel().getLevelData());
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

    public void setPlayerDying(boolean die) { 
        this.playerDying = die; 
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
            case KeyEvent.VK_E: 
                objectManager.setChestInteract();;
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
            if (mouseX < weapon.getX() - xOffset) {
                offset = 1.7;
            } else {
                offset = -1.8;
            }

            double deltaX = weapon.getX() - mouseX - xOffset;
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
        bulletCooldown((int) mouseX, (int) mouseY);
    }


    /**
	 * Applies cooldown if bullet is shot (mouse dragged; click with movement)
	 * 
	 * @author Hamad Mohammed
	 * @since December 30, 2023
	 */

    public void mouseDragged(MouseEvent e) {
        

        mouseX = e.getX();
        mouseY = e.getY();
        bulletCooldown((int) mouseX, (int) mouseY);
        
        if (!paused && !inventory) {
            if (mouseX < weapon.getX() - xOffset) {
                offset = 1.7;
            } else {
                offset = -1.8;
            }

            double deltaX = weapon.getX() - mouseX - xOffset;
            double deltaY = weapon.getY() - mouseY;

            
            weaponAngle = -Math.atan2(deltaX, deltaY) + offset;
        }

       if (paused)
			pauseScreen.mouseMoved(e);

         if (inventory)
			inventoryState.mouseMoved(e);
        

    }

   
    @Override
    public void mousePressed(MouseEvent e) {
        bulletCooldown((int) mouseX, (int) mouseY);
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
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'keyTyped'");
    }


}