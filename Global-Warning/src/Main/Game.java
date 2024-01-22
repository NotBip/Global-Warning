package Main;

import java.awt.Graphics;
import java.io.IOException;

import GameStates.*;


public class Game implements Runnable {
    private static final int FPS = 60; // Frames per second
    private static final int UPS = 120; // Updates (behind the scenes stuff) per second
    public static final float SCALE = 1f; 
    private Thread gameThread;
    public Playing playing;
    private Menu menu;
    private OptionState options;
    private SaveState save;
    private gamePanel panel;


    public Game() {
        initialize();
        panel = new gamePanel(this);
        new gameFrame(panel);
        panel.setFocusable(true);
        panel.requestFocus();
        startGame();
    }

    /*
	* Method Name: update
	* Author: Ryder Hodgson
	* Creation Date: December 16th, 2023
	* Modified Date: December 16th, 2023
*//** Description: Starts the initial game loop
	* @return n/a
	* Dependencies: Thread
	* Throws/Exceptions: n/a
	*/

    public void startGame() {
        gameThread = new Thread(this);
        gameThread.start();
    }

     /*
	* Method Name: update
	* Author: Ryder Hodgson
	* Creation Date: December 16th, 2023
	* Modified Date: December 16th, 2023
*//** Description: Does all of the background teask every time the game updates (120 time per second)
	* @return n/a
	* Dependencies: Playing, Menu, SaveState, OptionState 
	* Throws/Exceptions: IOException
	*/

    public void update() throws IOException {
        switch (GameState.currentState) {
            case PLAYING:
                playing.update();
                break;
            case MENU:
                menu.update();
                break;
            case SAVE:
                save.update();
                break;
            case OPTIONS:
                options.update();
                break;
            case QUIT:
            default:
                System.exit(0);
                break;
        }
    }

     /*
	* Method Name: draw
	* Author: Ryder Hodgson
	* Creation Date: December 16th, 2023
	* Modified Date: December 16th, 2023
*//** Description: Draws everything to the screen every frame (60FPS)
	* @return n/a
    * @param g What it actually uses to draw
	* Dependencies: Playing, Menu, OptionState, SaveState
	* Throws/Exceptions: IOException
	*/

    public void draw(Graphics g) throws IOException {
        
        switch (GameState.currentState) {
            case PLAYING:
                playing.draw(g);
                break;
            case MENU:
                menu.draw(g);
                break;
            case SAVE:
                save.draw(g);
                break;
            case OPTIONS:
                options.draw(g);
                break;
            case QUIT:
                break;
        }
    }
    /*
	* Method Name: initialize
	* Author: Ryder Hodgson
	* Creation Date: December 16th, 2023
	* Modified Date: December 16th, 2023
*//** Description: Initializes all the important classes necessary for the game to function
	* @return n/a
	* Dependencies: Playing, Menu, OptionState, SaveState
	* Throws/Exceptions: n/a
	*/

    public void initialize() {
        playing = new Playing(this);
        menu = new Menu(this);
        save = new SaveState(this);
        options = new OptionState(this);
    }

     /*
	* Method Name: run
	* Author: Ryder Hodgson
	* Creation Date: December 16th, 2023
	* Modified Date: December 16th, 2023
*//** Description: Runs at the start of the game thread. Is THE game. Keeps track of framerate and updates
	* @return n/a
	* Dependencies: gamePanel, update
	* Throws/Exceptions: IOException
	*/

    @Override
    public void run() {
        double frameTime = 1000000000.0 / FPS;
        double updateTime = 1000000000.0 / UPS;

        long lastTime = System.nanoTime();

        double timeSinceLastFrame = 0;
        double timeSinceLastUpdate = 0;

        while (true) {
            long currentTime = System.nanoTime();

            timeSinceLastFrame += (currentTime - lastTime) / frameTime;
            timeSinceLastUpdate += (currentTime - lastTime) / updateTime;
            lastTime = currentTime;

            if (timeSinceLastFrame >= 1) { 
                panel.repaint();
                timeSinceLastFrame--; // Don't set to 0 as a means of catching up if frames are lost
            }

            if (timeSinceLastUpdate >= 1) {
                try {
                    update();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                timeSinceLastUpdate--; // Don't set to 0 as a means of catching up if updates are lost
            }

        }
    }
    
    
    public Playing getPlaying() {
        return playing;
    }

    public Menu getMenu() {
        return menu;
    }

     public SaveState getSave() {
        return save;
    }

     public OptionState getOptions() {
        return options;
    }

    public gamePanel getPanel() {
        return panel;
    }



}