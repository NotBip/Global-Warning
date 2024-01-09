package Main;

import static Utilities.Constants.GAME_HEIGHT;
import static Utilities.Constants.GAME_WIDTH;

import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

import UserInputs.KeyInputs;
import UserInputs.MouseInputs;

public class gamePanel extends JPanel{
    private Game game;

    public gamePanel(Game game) {
        this.game = game;
        setPreferredSize(new Dimension(GAME_WIDTH, GAME_HEIGHT));
        addMouseListener(new MouseInputs(this));
        addMouseMotionListener(new MouseInputs(this));
        addKeyListener(new KeyInputs(this));
        
    }

    public Game getGame() {
        return game;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        game.draw(g);
    }

}
