package UserInputs;

import static Utilities.Constants.MENU;
import static Utilities.Constants.PLAYING;

import Objects.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import GameStates.GameState;
import Main.gamePanel;

public class MouseInputs implements MouseMotionListener, MouseListener{

    gamePanel panel;
    public MouseInputs(gamePanel panel) {
        this.panel = panel;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        switch(GameState.currentState) {
        case PLAYING:
            panel.getGame().getPlaying().mouseMoved(e);
        case MENU:
       }
    
    }

    @Override
    public void mouseEntered(MouseEvent e) {
      //works
    }

    @Override
    public void mouseDragged(MouseEvent e) {
       //doesn't work
    }

    @Override
    public void mouseMoved(MouseEvent e) {       
       switch(GameState.currentState) {
        case PLAYING:
            panel.getGame().getPlaying().mouseMoved(e);
        case MENU:
       }
       
    }

    @Override
    public void mouseExited(MouseEvent e) {
         //remain stagnant if gone
    }

    @Override
    public void mousePressed(MouseEvent e) {
        //works
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        
    }
}