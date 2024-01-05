package Utilities;

import Main.Game;

public class Constants {

    // Game constants

    public static final int TILE_SIZE = 32;
    public static final int WIDTH_IN_TILES = 26;
    public static final int HEIGHT_IN_TILES = 14;
    public static final int GAME_WIDTH = WIDTH_IN_TILES * TILE_SIZE;
    public static final int GAME_HEIGHT = HEIGHT_IN_TILES * TILE_SIZE;
    public static final int animationSpeed = 15; 

    // States
    public static final int MENU = 0;
    public static final int PLAYING = 1;

	public static class Buttons {
			public static final int B_WIDTH= 140;
			public static final int B_HEIGHT = 56;

			public static final int SAVE_B_WIDTH= 200;
			public static final int SAVE_B_HEIGHT = 66;

			public static final int PAUSE_B_WIDTH= 56;
			public static final int PAUSE_B_HEIGHT = 56;


		}

    public static class Directions {
		public static final int LEFT = 0;
		public static final int UP = 1;
		public static final int RIGHT = 2;
		public static final int DOWN = 3;
	}

	public static class PlayerConstants {
		public static final int IDLELEFT = 1;
        public static final int IDLERIGHT = 0;
		public static final int RUNNINGRIGHT = 2;
        public static final int RUNNINGLEFT = 3; 

		public static int GetSpriteAmount(int player_action) {
			switch (player_action) {
			case RUNNINGRIGHT:
				return 4;
			case IDLELEFT:
				return 4;
            case IDLERIGHT:
				return 4;
            case RUNNINGLEFT: 
                return 4;
			default:
				return 1;
			}
		}
    }
}
