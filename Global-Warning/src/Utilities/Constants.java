package Utilities;

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

    public static class Directions {
		public static final int LEFT = 0;
		public static final int UP = 1;
		public static final int RIGHT = 2;
		public static final int DOWN = 3;
	}

	    public static class EnemyConstants {
			public static final int Zombie = 0; 
	}

	public static int getMaxEnemyHealth(int enemyType) {
		switch (enemyType) {
			case 0:
				return 10; 		
			default:
				return 0;
		}
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
