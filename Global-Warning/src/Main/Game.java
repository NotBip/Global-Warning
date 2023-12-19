package Main;

import java.awt.Graphics;

import GameStates.*;
import Levels.LevelManager;


public class Game implements Runnable {
    private static final int FPS = 60; // Frames per second
    private static final int UPS = 120; // Updates (behind the scenes stuff) per second
    private Thread gameThread;
    private Playing playing;
    private Menu menu;
    private gamePanel panel;

    public Game() {
        initialize();
        panel = new gamePanel(this);
        new gameFrame(panel);
        panel.requestFocusInWindow();
        startGame();
    }

    /**
     * Starts the initial game loop
     * @author Ryder Hodgson
     * @since December 16th, 2024
     */

    public void startGame() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    /**
     * Does all of the background tasks every time the game updates (120 times per second)
     * @author Ryder Hodgson
     * @since December 16th, 2024
     */

    public void update() {
        switch (GameState.currentState) {
            case PLAYING:
                playing.update();
                break;
            case MENU:
            menu.update();
        }
    }

    /**
     * Draws everything to the screen every frame (60FPS) depending on the game state
     * @author Ryder Hodgson
     * @param g What it uses to actually draw
     * @since December 16th, 2024
     */

    public void draw(Graphics g) {
        
        switch (GameState.currentState) {
            case PLAYING:
                playing.draw(g);
                break;
            case MENU:
            menu.draw(g);
        }
    }
    /**
     * Initializes each class
     * @author Ryder Hodgson
     * @since December 16th, 2024
     */

    public void initialize() {
        playing = new Playing(this);
        menu = new Menu();
    }

    /**
     * Runs at the start of the game thread. Is THE game. Keeps track of framerate and updates
     * @author Ryder Hodgson
     * @since December 16th, 2024
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
                update();
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


}