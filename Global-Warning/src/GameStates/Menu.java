package GameStates;

import java.awt.Color;
import java.awt.Graphics;
import static Utilities.Constants.GAME_WIDTH;
import static Utilities.Atlas.MENUBACKGROUND_ATLAS;
import static Utilities.Atlas.MENUBUTTON_ATLAS;
import static Utilities.Constants.GAME_HEIGHT;
import Main.Game;
import java.awt.event.*;
import java.awt.image.BufferedImage;

import static Utilities.Atlas.*;
import java.awt.Graphics2D;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;

public class Menu extends State implements KeyListener{

    private int  height = GAME_HEIGHT;
    private int  width = GAME_WIDTH;
    BufferedImage imgbackground = getSpriteAtlas(MENUBACKGROUND_ATLAS); 

    public Menu (Game game){
       super(game);
       //intialize();
    }

    public void update() {
    }

    public void draw(Graphics g) {
        g.setColor(Color.WHITE);
        //g.drawString("Press Enter to Start", GAME_WIDTH/2, GAME_HEIGHT/2);
        g.drawImage(this.imgbackground, 0, 0, this.width, this.height, null);

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