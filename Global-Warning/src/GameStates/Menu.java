package GameStates;

import java.awt.Color;
import java.awt.Graphics;
import static Utilities.Constants.GAME_WIDTH;
import static Utilities.Constants.GAME_HEIGHT;
import Main.Game;
import java.awt.event.*;

public class Menu extends State implements KeyListener{

    public Menu (Game game){
       super(game);
       //intialize();
    }

    public void update() {
    }

    public void draw(Graphics g) {
        g.setColor(Color.BLACK);
        g.drawString("Press Enter to Start", GAME_WIDTH/2, GAME_HEIGHT/2);

    }
    @Override
    public void keyPressed(KeyEvent e) {
      if (e.getKeyCode() == KeyEvent.VK_ENTER){
           GameState.currentState = GameState.PLAYING;
           System.out.println("Enter!");
      }
    }

    @Override
    public void keyReleased(KeyEvent e) {
   
    }

    @Override
    public void keyTyped(KeyEvent e) {
        
    }

    public void mouseMoved(MouseEvent e){


        }


     public void mouseClicked(MouseEvent e) {
       
     }
     

    public void mouseDragged(MouseEvent e) {
          
        }    
        

    
}