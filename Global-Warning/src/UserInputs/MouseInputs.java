package UserInputs;

import static Utilities.Constants.MENU;
import static Utilities.Constants.PLAYING;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import GameStates.GameState;
import Main.gamePanel;

public class MouseInputs implements MouseListener{

    gamePanel panel;

    public MouseInputs(gamePanel panel) {
        this.panel = panel;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        switch(GameState.currentState) {
        case PLAYING:
            panel.getGame().getPlaying().mouseClicked(e);
            System.out.println("mouse click lol");
        case MENU:
       }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        //gamePanel.setRectPos(e.getX(), e.getY());
    }

    @Override
    public void mouseExited(MouseEvent e) {
         //remain stagnant if gone
    }

    @Override
    public void mousePressed(MouseEvent e) {
       
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        
    }
}