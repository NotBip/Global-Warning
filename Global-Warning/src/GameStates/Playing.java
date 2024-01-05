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

public class Playing extends State implements KeyListener {
    private Player player;
    private Weapon1 weapon;
    public int bulletCount;
    public List<Bullets> bullets;
    public Iterator<Bullets> it;
    private LevelManager levelManager;
    private ObjectManager objectManager;
    private boolean paused;
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
    }

    public void update() {
        player.update();
        weapon.update();

        for (int i = 0; i < bullets.size(); i++) {
            bullets.get(i).updateBullets();
        }

    }

    public void checkBorder() {

    }

    public void draw(Graphics g) {
        for (int i = 0; i < bullets.size(); i++) {
            bullets.get(i).draw(g);
        }

        weapon.draw(g);
        player.draw(g);

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

       // System.out.println("NEW BULLET! ");
        Bullets bullet = new Bullets(weapon, this, weapon.getX() + 50, weapon.getY() + 35, x, y);
        bullets.add(bullet);

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

        if (mouseX < weapon.getX()) {

            offset = 1.6;
        } else {
            offset = -1.5;
        }

        double deltaX = weapon.getX() - mouseX;
        double deltaY = weapon.getY() - mouseY;
        weaponAngle = -Math.atan2(deltaX, deltaY) + offset;

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
    }

}
