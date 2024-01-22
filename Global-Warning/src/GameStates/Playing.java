package GameStates;

import Entities.*;
import Entities.Planet1Enemies.Enemy1;
import Entities.Planet1Enemies.Enemy2;
import Objects.Weapons.*;
import UserInterface.HealthBar;
import UserInterface.SaveButton;
import Objects.Saving.*;
import Utilities.LoadSave;
import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JOptionPane;

import Levels.LevelManager;
import Main.Game;
import Objects.BarrierDoor;
import Objects.ObjectManager;
import Objects.Sign;
import Objects.Saving.Checkpoint;
import static Utilities.Atlas.MENUBACKGROUND_ATLAS;
import static Utilities.Constants.GAME_HEIGHT;
import static Utilities.Constants.GAME_WIDTH;
import static Utilities.Constants.HEIGHT_IN_TILES;
import static Utilities.Constants.TILE_SIZE;
import Utilities.SoundLibrary;

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
    public Player player;
    private Game game;

    public int bulletCount; // How many bullets are on screen
    public List<Bullets> bullets; // A list of bullets currently in use
    public List<Bombs> bombs; // A list of bombs currently in use
    private EnemyManager enemyManager; // Manage all enemies
    private ObjectManager objectManager; // Manage all objects
    private int borderLen = (int) (0.4 * GAME_WIDTH); // The distance between the window and the player that must be reached before the camera starts moving
    private int xOffset; // The current camera offset of the current room
    private int maxOffsetX; // The maximum camera offset of the current room
    public int numFile = Checkpoint.fileNum; // Which file the player is on
    public static LevelManager levelManager; // Manage all levels
    private Pause pauseScreen; // The state for when the player is paused
    private Death gameOver; // The state for when the player has died
    private InventoryState inventoryState; // The state for when the player is in the inventory
    private Environment environment; // Environmental effects/obstacles
    private Image backgroundImage; // The background of the game
    public static boolean paused, inventory, dead = false; // Player states where they should not be moving
    private double weaponAngle = 0; // The angle of the current weapon being held
    public static int gunIndex = 1; // Which gun is being used
    public double mouseX; // Position of the mouse horizontally
    public double mouseY; // Position of the mouse vertically
    public double offset; // Offset for weapon
    private HealthBar healthBar; 
    private SoundLibrary soundlibrary;
    public long loadtime = 0;
    private boolean playerDying; 
    public boolean BombReady = true;
    private String filepath = "";
    //cooldown for firerate (later to be upgradeable to lower cooldown)
    public long lastBomb = 0; 
    public long lastBullet = 0;
    public static long fireRateWeapon1 = 0; // 300 milliseconds
    public static long fireRateWeapon2 = 0; // 250 milliseconds
    public static long fireRateWeapon3 = 1000; // 500 milliseconds
    public static int damageWeapon1 = 10;
    public static int OGdamageWeapon1 = 10;
    public static int damageWeapon2 = 20;
    public static int OGdamageWeapon2 = 20;
   // public  int num = SaveButton.getFileNum();
    public int lightningUpdates; // The total updates that have passed before a complete lightning cycle
    public int lightningPosCooldown = 480; // How long it takes before the lightning chooses where to strike
    public int lightningSpawnCooldown = 120; // How long it takes after choosing a position for lightning to strike
    public float lightningPosX; // X position of the lightning hitbox
    public float lightningHeight; // Height of the lightning hitbox (so it doesn't strike through tiles)
    public Rectangle2D.Float lightningHitbox; // The lightning hitbox
    public boolean lightningHasPos = false; // Has the lightning chosen the position it is going to strike?

    public Playing(Game game) {
        super(game);
        this.game = game;
        System.out.println("we're in session!");
        initialize();
    }

     /*
	* Method Name: loadNextLevel
	* Author: Ryder Hodgson
	* Creation Date: January 1st, 2024
	* Modified Date: January 15th, 2024
*//** Description: Loads the next room
	* @param spawnType 0 = checkpoint/default point, 1 = next room let, 2 = next room right
	* @return n/a
	* Dependencies: enemyManager, levelManager, resetLightning
	* Throws/Exceptions: n/a
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

/*
	* Method Name: initialize
	* Author: Ryder Hodgson
	* Creation Date: Decemeber 16th, 2023
	* Modified Date: January 16th, 2024
*//** Description: Initializes all necessary classes and default things they all need
	* @return n/a
	* Dependencies: EnemyManager, LevelManager, ObjectManager, Player, Bullet, Weapon1, Pause, InventoryState, Death, Bomb, LoadSave
	* Throws/Exceptions: n/a
	*/

    public void initialize() {
        levelManager = new LevelManager(this);
        player = new Player(1200, GAME_HEIGHT / 2 - 50, 58, 64, this); // Default spawn point
        objectManager = new ObjectManager(this); 
        enemyManager = new EnemyManager(player);
        levelManager.loadNextLevel();
        weapon = new Weapon1(player, this);
        bullets = new ArrayList<>();
        healthBar = new HealthBar(this); 
       // savepoint = new Checkpoint(GAME_WIDTH / 2-300, 100, 45, 63, this);
        pauseScreen = new Pause(this);
        inventoryState = new InventoryState(this);
        gameOver = new Death(this);
        player.loadLevelData(levelManager.getCurrentLevel().getLevelData());
        backgroundImage = LoadSave.GetSpriteAtlas(MENUBACKGROUND_ATLAS);
        this.environment = new Environment(this); 
        bombs = new ArrayList<>();
        soundlibrary = new SoundLibrary(this);

        try{ // Catch errors if the room has no default spawn point
            player.setSpawn(levelManager.getCurrentLevel().getPlayerSpawn());
        } catch(Exception e) { // Will spawn the player at its initialization spawning coordinates
            System.out.println("no default spawn point found");
        }
        enemyManager.loadEnemies(levelManager.getCurrentLevel());
    }


    private void playMusic() {
        filepath = "Global-Warning/res/audio/Main.wav";
        try {
            File musicPath = new File(filepath);
            if (musicPath.exists()) {
                AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath);
                Clip clip = AudioSystem.getClip();
                clip.open(audioInput);
                clip.loop(Clip.LOOP_CONTINUOUSLY);
                clip.start();
            }
            else {
                System.out.println("Can't Find Music File");
            }
        }
        catch(Exception e) {
            System.out.println(e);
        }
    }

    /*
	* Method Name: update
	* Author: Ryder Hodgson
	* Creation Date: Decemeber 16th, 2023
	* Modified Date: January 20th, 2024
*//** Description: updates everything in the class
	* @return n/a
	* Dependencies: EnemyManager, LevelManager, ObjectManager, Player, Bullet, Weapon1, Pause, InventoryState,
    * Death, Bomb, updateLightning, checkLightningIntersect, checkBorder, checkTransition, checkTouchingSign, Environment
	* Throws/Exceptions: n/a
	*/
    public void update() throws IOException {
        if(!game.getPanel().inFocus) {
            paused = true;
        }

        if(dead){
            gameOver.update();
        }else if (paused) {
			pauseScreen.update();
		} else if (inventory && !paused){
			inventoryState.update();
		} else {
            player.update();
            weapon.update();
            if (getLevelManager().getCurrentLevel().getIsCheckpoint())
            getLevelManager().getCurrentLevel().getCheckpoint().update();
         for (int i = 0; i < bullets.size(); i++) {
            bullets.get(i).updateBullets();
         }
         for (int i = 0; i < bombs.size(); i++) { 
            bombs.get(i).updateBombs();
         }
        checkTouchingDoor();
        updateLightning(); 
        checkLightningIntersect();
        checkBorder();
        checkTouchingSign();
        checkTransition();
        
        enemyManager.update(levelManager.getCurrentLevel().getLevelData(), bullets, this, getObjectManager());
        objectManager.update();
        environment.update();
        healthBar.update();
    }
    }

    public void updateUpgrade() {
        fireRateWeapon1 += (this.getPlayer().getUpgradeGem().getFirerateBoost() * this.getPlayer().getUpgradeGem().getNumUpgrades());
        System.out.println("Firerate 1: " + fireRateWeapon1);
        fireRateWeapon2 += (this.getPlayer().getUpgradeGem().getFirerateBoost() * this.getPlayer().getUpgradeGem().getNumUpgrades());
        System.out.println("Firerate 2: " + fireRateWeapon2);
        damageWeapon1 = OGdamageWeapon1 + (this.getPlayer().getUpgradeGem().getDamageBoost() * this.getPlayer().getUpgradeGem().getNumUpgrades());
        damageWeapon2 = OGdamageWeapon2 + (this.getPlayer().getUpgradeGem().getDamageBoost() * this.getPlayer().getUpgradeGem().getNumUpgrades());
    }

    /**
     * Checks if that player has gone past the camera border
     * 
     * @author Ryder Hodgson
     * @since January 1st, 2024
     */

     /*
	* Method Name: checkBorder
	* Author: Ryder Hodgson
	* Creation Date: January 1st, 2024
	* Modified Date: January 1st, 2024
*//** Description: Checks if the player has gone past the camera border, updating the camera position if so
	* @return n/a
	* Dependencies: Player
	* Throws/Exceptions: n/a
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

     /*
	* Method Name: checkTransition
	* Author: Ryder Hodgson
	* Creation Date: January 2nd, 2024
	* Modified Date: January 2nd, 2024
*//** Description: Checks if the player has has touched a transition point, if so, put them into the next corresponding room
	* @return n/a
	* Dependencies: LevelManager, Player
	* Throws/Exceptions: n/a
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
                if(levelManager.getCurrentLevel().getLeftSpawn() != null) { // Make sure the room has a point to send you to, otherwise send you to default point
                    loadNextLevel(1);
                } else {
                    System.out.println("no left spawn point found");
                    loadNextLevel(0);
                }
                return;
            }
        }
    }

    /*
	* Method Name: checkLightningIntersect
	* Author: Ryder Hodgson
	* Creation Date: January 15th, 2024
	* Modified Date: January 16th, 2024
*//** Description: Checks if the player is intersecting the lightning hitbox
	* @return n/a
	* Dependencies: Player
	* Throws/Exceptions: n/a
	*/

    public void checkLightningIntersect() {
        if(lightningHitbox != null && lightningUpdates >= lightningPosCooldown + lightningSpawnCooldown)
        if(player.getHitbox().intersects(lightningHitbox) && lightningHasPos && !player.isImmune()) { // fix when this happens
            player.changeHealth(-50);
        }
    }

/*
	* Method Name: updateLightning
	* Author: Ryder Hodgson
	* Creation Date: January 15th, 2024
	* Modified Date: January 16th, 2024
*//** Description: Update lightning (cooldown between picking position, spawning, and making it finally strike)
	* @return n/a
	* Dependencies: Player, LevelManager, resetLightning
	* Throws/Exceptions: n/a
	*/

    private void updateLightning() {
        if(levelManager.getCurrentLevel().getStormy()) {
            lightningUpdates++;
            if(lightningUpdates >= lightningPosCooldown && !lightningHasPos) {
                lightningPosX = player.getHitbox().x + player.getXSpeed();
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
            if(lightningUpdates >= lightningPosCooldown + lightningSpawnCooldown * 1.5) {
               resetLightning();
            }
        } else {
            resetLightning();
        }
    }

/*
	* Method Name: updateLightning
	* Author: Ryder Hodgson
	* Creation Date: January 15th, 2024
	* Modified Date: January 20th, 2024
*//** Description: Draw the lightning warning and the lightning itself
    * @param g What it actually draws with
    * @param xOffset the offset to the x position based on the current camera position
	* @return n/a
	* Dependencies: Graphics, Environment
	* Throws/Exceptions: n/a
	*/

    private void drawLightning(Graphics g, int xOffset) {
        if(lightningUpdates >= lightningPosCooldown && lightningHasPos && lightningUpdates <= lightningPosCooldown + lightningSpawnCooldown) {
            g.setColor(Color.RED);
            if(lightningUpdates % 10 <= 3) { // Flash the lightning warning before it strikes
                g.fillRect((int) lightningHitbox.x - xOffset, (int) lightningHitbox.y, (int) lightningHitbox.width, (int) lightningHitbox.height);
            }
        }
        if(lightningUpdates >= lightningPosCooldown + lightningSpawnCooldown && lightningHasPos && lightningHitbox != null) {
            environment.drawLightning(g, xOffset);
        }
        
    }

    /*
	* Method Name: resetLightning
	* Author: Ryder Hodgson
	* Creation Date: January 15th, 2024
	* Modified Date: January 15th, 2024
*//** Description: Reset everything to do with lightning after a strike
	* @return n/a
	* Dependencies: n/a
	* Throws/Exceptions: n/a
	*/

    private void resetLightning() {
        lightningHasPos = false;
        lightningUpdates = 0;
        lightningHitbox = null;
    }

    /*
	* Method Name: checkTouchingSign
	* Author: Ryder Hodgson
	* Creation Date: January 20th, 2024
	* Modified Date: January 20th, 2024
*//** Description: Check if the player is touching the sign hitbox
	* @return n/a
	* Dependencies: LevelManager, Player, Sign
	* Throws/Exceptions: n/a
	*/

    private void checkTouchingSign() {
        for(Sign s : levelManager.getCurrentLevel().getSigns()) {
            if(player.getHitbox().intersects(s.getHitbox()) && !s.hasBeenRead()) {
                s.setHasBeenRead(true);
            }
        }
    }

    private void checkTouchingDoor() {
        for(BarrierDoor d : levelManager.getCurrentLevel().getDoor()) {
            if(player.getHitbox().intersects(d.getHitbox()) && !d.doorOpen && !d.doorOpened) {
                player.setBehindDoor(true);
                break;
            } else {
                player.setBehindDoor(false);
            }

            for(Enemy2 e : levelManager.getCurrentLevel().getWaterBoi()) {
                if(e.getHitbox().intersects(d.getHitbox()) && !d.doorOpen && !d.doorOpened) {
                    e.setBehindDoor(true);
                    break;
                } else {
                    e.setBehindDoor(false);
                }
            }

            for(Enemy1 e : levelManager.getCurrentLevel().getFireBoi()) {
                if(e.getHitbox().intersects(d.getHitbox()) && !d.doorOpen && !d.doorOpened) {
                    e.setBehindDoor(true);
                    break;
                } else {
                    e.setBehindDoor(false);
                }
            }
        }
    }

    /*
	* Method Name: draw
	* Author: Ryder Hodgson
	* Creation Date: January 16th, 2024
	* Modified Date: January 20th, 2024
*//** Description: Draw everything to the screen when actually playing the game (e.g. not in a menu)
    * @param g What it actually draws with
	* @return n/a
	* Dependencies: Graphics, Environment, Weapon1, Player, LevelManager, EnemyManager, ObjectManager, Sign, drawLightning, Bullet, Bomb, InventoryState, Pause, Death,
	* Throws/Exceptions: IOException
	*/

    public void draw(Graphics g) throws IOException {
        g.drawImage(backgroundImage, 0, 0, null);
        environment.draw(g, xOffset);
        weapon.draw(g, xOffset);
        player.draw(g, xOffset);
        levelManager.draw(g, xOffset);
        enemyManager.draw(g, xOffset);

        for(Sign s: levelManager.getCurrentLevel().getSigns()) { // Only for the tutorial
            if(s.hasBeenRead()) {
                s.drawText(g, xOffset, s.getText());
            }
        }

        if (getLevelManager().getCurrentLevel().getIsCheckpoint())
        getLevelManager().getCurrentLevel().getCheckpoint().draw(g, xOffset);
        objectManager.draw(g, xOffset);
        drawLightning(g, xOffset);
        // player.drawHealthBar(g);
        // player.drawOxygenBar(g);
        healthBar.draw(g, xOffset);
        for (int i = 0; i < bullets.size(); i++) {
            bullets.get(i).draw(g, xOffset);
        }

        for (int i = 0; i < bombs.size(); i++) {
            bombs.get(i).draw(g, xOffset);
        }

        if (inventory) {
            inventoryState.draw(g);
        
        }

        if (paused) {
			pauseScreen.draw(g);
		}

        if (dead){
            gameOver.draw(g);
        }
    }
       
/*
	* Method Name: resetAll
	* Author: Ryder Hodgson
	* Creation Date: January 18th, 2024
	* Modified Date: January 19th, 2024
*//** Description: Reset literally everything to do with playing the game at all
	* @return n/a
	* Dependencies: Player, EnemyManager, resetLightning, Bullet, Bomb
	* Throws/Exceptions: n/a
	*/

    public void resetAll() {
        player.reset();
        enemyManager.resetEnemies();
        resetLightning();
        bullets.clear();
        bulletCount = 0;
        bombs.clear();
        lastBomb = 0;
        BombReady = true;
    }

     public static void setGunIndex(int item){
        if (item < 4){
            gunIndex = item;
            weapon.getImage();
        }
    }

    public List<Bombs> getBombs() { 
        return bombs; 
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

    public SoundLibrary getSoundLibrary() {
        return soundlibrary;
    }

    public double getAngle() {
        return weaponAngle;
    }

    public HealthBar getHealthBar() { 
        return healthBar; 
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
        // else if (gunIndex == 3) {
        //     rate = fireRateWeapon3;
        // }

        //cooldown according to the firerate of gun
        if (time1 > lastBullet + rate) {
            spawnBullet(x, y);
            lastBullet = time1;
        }
    }


    public void bombCooldown(int x, int y) {
        long time1 = System.currentTimeMillis();
        long rate = 0;

            rate = fireRateWeapon3;
        

        //cooldown according to the firerate of gun
        if (time1 > lastBomb + rate && BombReady) {
            spawnBomb(x, y);
            lastBomb = time1;
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
            Bullets bullet = new Bullets(weapon, this, player.getHitbox().x + player.getHitbox().width/2, weapon.getY() + 35, x, y, xOffset, levelManager.getCurrentLevel().getLevelData(), 0);
             bullets.add(bullet);
            }
        }

    private void spawnBomb(int x, int y) {
        
        if (!paused && !inventory && player.getItemQuantity(2) > 0){
             Bombs bomb = new Bombs(this, weapon, levelManager.getCurrentLevel().getLevelData(), 0, weapon.getX() + 50, weapon.getY(), x, y, xOffset);
             bombs.add(bomb);
             player.useItem(2, this);
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

    public void removeBomb() {
        bombs.remove(0);
    }

    public void setBackGround(Image backgroundImage) {
        this.backgroundImage = backgroundImage;
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
            if(!inventory && !paused && !dead)
                player.jump();
                break;
            case KeyEvent.VK_ESCAPE:
                if (!dead){
                    if (inventory){
                        inventory = false;
                        inventoryState.reset();
                    } else {
                        paused = !paused;
                        player.pauseReset();
                    }
                 }
                 break;
             case KeyEvent.VK_I:
                 if (!paused && !dead){
			         inventory = !inventory;
                     inventoryState.reset();
                     player.pauseReset();
                 }
                 break;
            case KeyEvent.VK_SHIFT:
            if(!inventory && !paused && !dead) {
                player.dash();
            }
                break;
            case KeyEvent.VK_E: 
            if(!inventory && !paused && !dead) {
                objectManager.checkInteracts();
            }
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
        
        if (!paused && !inventory &&!dead) {
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

         if (dead)
			gameOver.mouseMoved(e);

    }

     /**
	 * Applies cooldown if bullet is shot (mouse clicked)
	 * 
	 * @author Nusayba Hamou
	 * @since December 29, 2023
	 */

    public void mouseClicked(MouseEvent e) {
        if(gunIndex != 3)
        bulletCooldown((int) mouseX, (int) mouseY);
        else if (gunIndex == 3) {
bombCooldown((int) mouseX, (int) mouseY);

        }
        
        
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
        if(gunIndex != 3)
        bulletCooldown((int) mouseX, (int) mouseY);
        else if (gunIndex == 3)
        bombCooldown((int) mouseX, (int) mouseY);

        
        if (!paused && !inventory && !dead) {
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
        
        if (dead)
			gameOver.mouseMoved(e);
        

    }

   
    @Override
    public void mousePressed(MouseEvent e) {
        if(gunIndex != 3)
        bulletCooldown((int) mouseX, (int) mouseY);
        else if(gunIndex == 3)
        bombCooldown((int) mouseX, (int) mouseY);

       if (paused)
			pauseScreen.mousePressed(e);

        if (inventory)
            inventoryState.mousePressed(e);

        if (dead)
            gameOver.mousePressed(e);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
       if (paused)
			pauseScreen.mouseReleased(e);

        if (inventory)
            inventoryState.mouseReleased(e);
        
        if (dead)
            gameOver.mouseReleased(e);
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