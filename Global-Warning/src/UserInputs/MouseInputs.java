package UserInputs;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import Main.gamePanel;

public class MouseInputs implements MouseListener{

    gamePanel panel;

    public MouseInputs(gamePanel panel) {
        this.panel = panel;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        
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