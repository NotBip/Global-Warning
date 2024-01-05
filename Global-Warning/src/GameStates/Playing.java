package GameStates;

import Entities.*;
import Objects.Weapons.*;
import Levels.LevelManager;
import Main.Game;
import Objects.ObjectManager;

import static Utilities.Constants.GAME_HEIGHT;
import java.awt.Graphics;

import java.awt.event.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Playing extends State implements KeyListener, MouseListener {
    private Player player;
    private Weapon1 weapon;
    public int bulletCount;
    public List<Bullets> bullets;
    public Iterator<Bullets> it;
    private LevelManager levelManager;
    private ObjectManager objectManager;
    private Pause pauseScreen;
    private InventoryState inventoryState;
    public static boolean paused, inventory = false;
    private float borderLen;
    private double weaponAngle = 0;
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
		} else if (inventory){
			inventoryState.update();
		} else {
            player.update();
            weapon.update();
         for (int i = 0; i < bullets.size(); i++) {
            bullets.get(i).updateBullets();
         }
        }

    }

    public void checkBorder() {

    }

    public void draw(Graphics g) {

        player.draw(g);
        weapon.draw(g);
        for (int i = 0; i < bullets.size(); i++) {
            bullets.get(i).draw(g);
        }

        if (paused) {
			pauseScreen.draw(g);
		} 

        if (inventory) {
            inventoryState.draw(g);
        }

    }

    public void reset() {

    }

    public Weapon1 getWeapon1() {
        return weapon;
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

       if (!paused){
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
			     paused = !paused;
                 break;
             case KeyEvent.VK_I:
			     inventory = !inventory;
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

    public void unpauseGame() {
		paused = false;
	}

    public void unInventory() {
		inventory = false;
	}

    @Override
    public void mousePressed(MouseEvent e) {
       if (paused)
			pauseScreen.mousePressed(e);

        if (paused)
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
    }

}
