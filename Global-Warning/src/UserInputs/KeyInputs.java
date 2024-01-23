/**
***********************************************
* @Author : All
* @Originally made : 1 JAN 2024
* @Last Modified: 22 JAN 2024
* @Description: Key inputs for key listeners
***********************************************
*/

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

    /**
	@Method Name: keyPressed
	@Modified Date: 22 JAN, 2024
	@Description: gets keys pressed for state
	@Parameters: Key Event E
	@Dependencies: Panel, Gamestate
	*/

    @Override
    public void keyPressed(KeyEvent e) {
       switch(GameState.currentState) {
        case PLAYING:
            panel.getGame().getPlaying().keyPressed(e);
        case MENU:
            panel.getGame().getMenu().keyPressed(e);
        default:
            break;
       }
    }

     /**
	@Method Name: keyReleased
	@Modified Date: 22 JAN, 2024
	@Description: get keys released for state
	@Parameters: Key Event E
	@Dependencies: Panel, Gamestate
	*/

    @Override
    public void keyReleased(KeyEvent e) {
       switch(GameState.currentState) {
        case PLAYING:
            panel.getGame().getPlaying().keyReleased(e);
        case MENU:
            panel.getGame().getMenu().keyReleased(e);
        default:
            break;
       }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        
    }
    
}
