package UserInputs;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.Timer;

import GameStates.GameState;
import Main.gamePanel;

public class MouseInputs implements MouseMotionListener, MouseListener, ActionListener{

    gamePanel panel;
    Timer timer;
    private MouseEvent lastMouseEvent;

    public MouseInputs(gamePanel panel) {
        this.panel = panel;
        timer = new Timer(1, this);

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        lastMouseEvent = e;
        timer.stop();   
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
        lastMouseEvent = e;
        timer.stop();   
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
        lastMouseEvent = e; 
        timer.stop();   
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
        lastMouseEvent = e;
        timer.start();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        timer.stop();
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

    @Override
    public void actionPerformed(ActionEvent e) {
        
                switch(GameState.currentState) {
                    case PLAYING:
                       panel.getGame().getPlaying().mousePressed(lastMouseEvent);
                       break;
                    case MENU:
                        panel.getGame().getMenu().mousePressed(lastMouseEvent);
                        break;
                    case SAVE:
                        panel.getGame().getSave().mousePressed(lastMouseEvent);  
                        break;
                     case OPTIONS:
                        panel.getGame().getOptions().mousePressed(lastMouseEvent);  
                        break;
                    case QUIT:
                        break;
    }
        
}
}