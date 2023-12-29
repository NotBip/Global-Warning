    package GameStates;

    import Entities.*;
    import Objects.Weapons.*;
    import Levels.LevelManager;
    import Main.Game;
    import Objects.ObjectManager;

    import static Utilities.Constants.GAME_HEIGHT;
    import static Utilities.Constants.GAME_WIDTH;
    import java.awt.Graphics;
    import java.awt.Graphics2D;
    import java.awt.event.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


    public class Playing extends State implements KeyListener{
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
        public long coolDown = 300; // 500 milliseconds

        public Playing(Game game) {
            super(game);
            initialize();
        }


        public void loadNextLevel() {

        }

        public void initialize() {
            player = new Player(10, GAME_HEIGHT-100, 60, 80, this);
            weapon= new Weapon1(player, this);
            bullets = new ArrayList<>();         
        }

        public void update() {
            player.update();
            weapon.update();

            for(Bullets bullet : bullets) { 
                bullet.updateBullets();
            }

        }

        public void checkBorder() {

        }

        public void draw(Graphics g) {
            for(Bullets bullet : bullets) { 
            bullet.draw(g);  
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

        public ObjectManager getObjectManager(){
            return objectManager;
        }

        public double getAngle() { 
            return weaponAngle; 
        }

        private void spawnBullet(int x, int y) {
            //there can only be 1 bullet shot at a time
            //this is a temporary cooldown system 
          // if (bullets.size() < 3){ //there must be 0 bullets on-screen for a new bullet to spawn
            System.out.println("NEW BULLET! ");
            Bullets bullet = new Bullets(weapon, this, weapon.getX()+50, weapon.getY()+35, x, y);
            bullets.add(bullet);
          
        }
    
        public void removeBullet() {

            //iterator now has all elements in the array list
            it = bullets.iterator();  
            //while (it.hasNext()){

                //get next element in list
               Bullets draw = it.next();
                
            //   if(draw.getDrawX() >= weapon.getX()+400 || draw.getDrawX() >= GAME_WIDTH || draw.getDrawX() <= 0 || draw.getDrawX() <= weapon.getX()-400){
                   //System.out.println("getDraw " + draw.getDrawX());
                  // System.out.println("getWeapon "+ weapon.getX());
                   //System.out.println("REMOVE");
                       
                  //  System.out.println("size: " + bullets.size() );  
                   System.out.println("REMOVE");
                    //remove from iterator then remove from the array list
                    it.remove(); 
                     bullets.remove(draw);

                  //   System.out.println("size: " + bullets.size());  
              // }

           // }
            
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

        mouseX = e.getX();
            mouseY = e.getY();

            if (mouseX < weapon.getX()){

            offset = 1.6;
            }
            else {
            offset = -1.5;
            }
        
            double deltaX = weapon.getX() - mouseX;
        double deltaY = weapon.getY() - mouseY;
            weaponAngle = -Math.atan2(deltaX, deltaY)+offset;

        // weaponAngle = -Math.atan2(deltaX, deltaY) + 1.75;
        //  System.out.println(weaponAngle);
        }


        public void mouseClicked(MouseEvent e) {
            long time1 = System.currentTimeMillis();

            if (time1 > lastBullet + coolDown) {
                spawnBullet(e.getX(), e.getY());
                lastBullet = time1;
            } 
    } 
            

            //} else {
              
           // }
        
        @Override
        public void keyTyped(KeyEvent e) {
            
        }


        public void mouseDragged(MouseEvent e) {
                 
        }

    }
