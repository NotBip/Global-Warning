package UserInputs;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import GameStates.GameState;
import Main.gamePanel;

public class KeyInputs implements KeyListener{

    private gamePanel panel;

    public KeyInputs(gamePanel panel) {
        this.panel = panel;
    }

    @Override
    public void keyPressed(KeyEvent e) {
       switch(GameState.currentState) {
        case PLAYING:
            panel.getGame().getPlaying().keyPressed(e);
        case MENU:
            panel.getGame().getMenu().keyPressed(e);
       }
    }

    @Override
    public void keyReleased(KeyEvent e) {
       switch(GameState.currentState) {
        case PLAYING:
            panel.getGame().getPlaying().keyReleased(e);
        case MENU:
            panel.getGame().getMenu().keyReleased(e);
       }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        
    }
    
}
