package GameStates;

import java.awt.Color;
import java.awt.Graphics;
import static Utilities.Constants.GAME_WIDTH;
import static Utilities.Constants.GAME_HEIGHT;
import Main.Game;
import UserInterface.MenuButton;

import java.awt.event.*;
import java.awt.image.BufferedImage;

import static Utilities.Atlas.*;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import GameStates.State.*;

public class Menu extends State implements KeyListener, MouseListener{

    private int offset = 70;
    private int  height = GAME_HEIGHT;
    private int  width = GAME_WIDTH;
    BufferedImage imgbackground = getSpriteAtlas(MENUBACKGROUND_ATLAS); 
    BufferedImage imgtitle = getSpriteAtlas(MENUTITLE_ATLAS); 
    private MenuButton[] buttons = new MenuButton[3];

    public Menu (Game game){
       super(game);
       makeButtons();
    }

    public void update() {
        for (MenuButton mb : buttons)
			mb.update();
	
    }

    public void makeButtons (){
        buttons[0] = new MenuButton(GAME_WIDTH / 2-offset, GAME_HEIGHT/2+offset-140, 0, GameState.SAVE);
		buttons[1] = new MenuButton(GAME_WIDTH / 2-offset, GAME_HEIGHT/2+offset-70, 1, GameState.OPTIONS);
		buttons[2] = new MenuButton(GAME_WIDTH / 2-offset, GAME_HEIGHT/2+offset,  2, GameState.QUIT);
    }

    public void draw(Graphics g) {
    
        g.drawImage(this.imgbackground, 0, 0, this.width, this.height, null);
        g.drawImage(this.imgtitle, GAME_WIDTH/3, 0, 300, 150, null);
        for (MenuButton mb : buttons)
			mb.draw(g);
	
    }
    @Override
    public void keyPressed(KeyEvent e) {
     // if (e.getKeyCode() == KeyEvent.VK_ENTER){
      //     GameState.currentState = GameState.PLAYING;
       //    System.out.println("Enter!");
     // }
    }

    @Override
    public void keyReleased(KeyEvent e) {
   
    }

    @Override
    public void keyTyped(KeyEvent e) {
        
    }

    public void mouseMoved(MouseEvent e){
        for (MenuButton mb : buttons)
        mb.setMouseOver(false);

    for (MenuButton mb : buttons)
        if (isIn(e, mb)) {
            mb.setMouseOver(true);
            break;

        }
    }


     public void mouseClicked(MouseEvent e) {
       
     }
     

    public void mouseDragged(MouseEvent e) {
          
        }    
        
     @Override
      public void mousePressed(MouseEvent e) {
        for (MenuButton mb : buttons) {
			if (isIn(e, mb)) {
				mb.setMousePressed(true);
			}
    
      }
    }
    
        @Override
        public void mouseReleased(MouseEvent e) {
            for (MenuButton mb : buttons) {
                if (isIn(e, mb)) {
                    if (mb.getMousePressed())
                        mb.applyGamestate();
                    break;
                }
            }
    
            resetButtons();
        
     }

     private void resetButtons() {
		for (MenuButton mb : buttons)
			mb.resetButtons();

	}

     @Override
      public void mouseExited(MouseEvent e) {
         
      }
    
        @Override
        public void mouseEntered(MouseEvent e) {
        
     }

    
}