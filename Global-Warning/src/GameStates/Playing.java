package GameStates;

import Entities.*;
import Objects.Weapons.Weapon1;
import Levels.LevelManager;
import Main.Game;
import Objects.ObjectManager;

import static Utilities.Constants.GAME_HEIGHT;
import static Utilities.Constants.GAME_WIDTH;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.*;

public class Playing extends State implements KeyListener{
    private Player player;
    private Weapon1 weapon;
    private LevelManager levelManager;
    private ObjectManager objectManager;
    private boolean paused;
    private float borderLen;
    private double weaponAngle = 0; 
    public double mouseX; 
    public double mouseY; 

    public Playing(Game game) {
        super(game);
        initialize();
    }


    public void loadNextLevel() {

    }

    public void initialize() {
        player = new Player(10, GAME_HEIGHT-100, 60, 80, this);
        weapon= new Weapon1(player, this);
       // weapon = new Weapon1(player);
    }

    public void update() {
        player.update();
    }

    public void checkBorder() {

    }

    public void draw(Graphics g) {
            player.draw(g);
           // weapon.draw(g);
        
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

    public ObjectManager getObjectManager(){
        return objectManager;
    }

     public double getAngle() { 
        return weaponAngle; 
    }


    public void keyPressed(KeyEvent e) {

        switch(e.getKeyCode()) {
            case KeyEvent.VK_A:
            player.setLeft(true);
           // weapon.setLeft(true);
            break;
            case KeyEvent.VK_D:
            player.setRight(true);
           // weapon.setRight(true);
            break;
            case KeyEvent.VK_W:
            player.setUp(true);
          //  weapon.setUp(true);
            break;
            case KeyEvent.VK_S:
            player.setDown(true);
          //  weapon.setDown(true);
            break;
            case KeyEvent.VK_SPACE:
            player.jump();
          //  weapon.jump();
            break;
        }
    }

    public void keyReleased(KeyEvent e) {
        switch(e.getKeyCode()) {
            case KeyEvent.VK_A:
            player.setLeft(false);
           // weapon.setLeft(false);
            break;
            case KeyEvent.VK_D:
            player.setRight(false);
           // weapon.setRight(false);
            break;
            case KeyEvent.VK_W:
            player.setUp(false);
           // weapon.setUp(false);
            break;
            case KeyEvent.VK_S:
            player.setDown(false);
          //  weapon.setDown(false);
            break;
        }
    }
    
  
    public void mouseMoved(MouseEvent e){

       double deltaX = weapon.getX() - e.getX();
       double deltaY = weapon.getY() - e.getY();

        weaponAngle = -Math.atan2(deltaX, deltaY) + 1.75;
      //  System.out.println(weaponAngle);
    }



    @Override
    public void keyTyped(KeyEvent e) {
        
    }

}
