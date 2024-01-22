package Main;

import static Utilities.Atlas.CURSOR_ATLAS;
import static Utilities.Atlas.getSpriteAtlas;

import java.awt.Cursor;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

public class gameFrame extends JFrame{
    private JFrame frame;
    private BufferedImage cursorImg; 
    Cursor cursor;
    
    public gameFrame(gamePanel panel) {
        frame = new JFrame();
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.add(panel);
        frame.setResizable(false);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
       // getCursorImg(); 
       // cursor = Toolkit.getDefaultToolkit().createCustomCursor(cursorImg, new Point(0,0), "Curser"); 
        frame.getContentPane().setCursor(cursor);
        frame.addWindowFocusListener(new WindowFocusListener() {

            @Override
            public void windowGainedFocus(WindowEvent e) {
                panel.inFocus = true;
            }

            @Override
            public void windowLostFocus(WindowEvent e) {
                panel.inFocus = false;
            }
            
        });
    }

    public void getCursorImg() { 
        cursorImg = getSpriteAtlas(CURSOR_ATLAS); 
    }

}
