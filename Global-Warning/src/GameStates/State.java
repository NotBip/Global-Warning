package GameStates;

import Main.Game;

public class State {
    public Game game;

    public State(Game game) {
        this.game = game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

}