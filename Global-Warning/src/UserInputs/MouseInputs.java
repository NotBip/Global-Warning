package UserInputs;

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
            panel.getGame().getPlaying().mouseClicked(e);
            break;
        case MENU:
            panel.getGame().getMenu().mouseClicked(e);
            break;
        case SAVE:
            panel.getGame().getSave().mouseClicked(e);  
            break;
        case OPTIONS:
            panel.getGame().getOptions().mouseClicked(e);  
            break;
        case QUIT:
            break;
       }
    
    }

    @Override
    public void mouseEntered(MouseEvent e) {
      //works
    }

    @Override
    public void mouseDragged(MouseEvent e) {
    switch(GameState.currentState) {
        case PLAYING:
            panel.getGame().getPlaying().mouseDragged(e);
            break;
        case MENU:
            panel.getGame().getMenu().mouseDragged(e);
            break;
        case SAVE:
            panel.getGame().getSave().mouseDragged(e);  
            break;
         case OPTIONS:
            panel.getGame().getOptions().mouseDragged(e);  
            break;
        case QUIT:
            break;
       }
    }

    @Override
    public void mouseMoved(MouseEvent e) {       
       switch(GameState.currentState) {
        case PLAYING:
            panel.getGame().getPlaying().mouseMoved(e);
            break;
        case MENU:
            panel.getGame().getMenu().mouseMoved(e);
            break;
        case SAVE:
            panel.getGame().getSave().mouseMoved(e);  
            break; 
         case OPTIONS:
            panel.getGame().getOptions().mouseMoved(e);  
            break;
        case QUIT:
            break;   

       }
       
    }

    @Override
    public void mouseExited(MouseEvent e) {
         //remain stagnant if gone
    }

    @Override
    public void mousePressed(MouseEvent e) {
      switch(GameState.currentState) {
        case PLAYING:
           panel.getGame().getPlaying().mousePressed(e);
           break;
        case MENU:
            panel.getGame().getMenu().mousePressed(e);
            break;
        case SAVE:
            panel.getGame().getSave().mousePressed(e);  
            break;
         case OPTIONS:
            panel.getGame().getOptions().mousePressed(e);  
            break;
        case QUIT:
            break;
       
       }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
      switch(GameState.currentState) {
        case PLAYING:
           panel.getGame().getPlaying().mouseReleased(e);
             break;
        case MENU:
            panel.getGame().getMenu().mouseReleased(e);
            break;
        case SAVE:
            panel.getGame().getSave().mouseReleased(e);  
            break;
         case OPTIONS:
            panel.getGame().getOptions().mouseReleased(e);  
            break;
        case QUIT:
            break;
       
       }
        
    }
}