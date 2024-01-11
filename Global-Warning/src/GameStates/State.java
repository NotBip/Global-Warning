package GameStates;

import Main.Game;

public class State {
    private Game game;

    public State(Game game) {
        this.game = game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

}