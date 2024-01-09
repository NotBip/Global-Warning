package GameStates;

import Main.Game;
import UserInterface.MenuButton;

import java.awt.Rectangle;
import java.awt.event.MouseEvent.*;
import java.awt.event.MouseEvent;
import UserInterface.*;

public class State {
    private Game game;

    public State(Game game) {
        this.game = game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

}