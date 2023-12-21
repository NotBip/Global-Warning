package Utilities;

public class Constants {

    // Game constants

    public static final int TILE_SIZE = 32;
    public static final int WIDTH_IN_TILES = 36;
    public static final int HEIGHT_IN_TILES = 22;
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
			public static final int ZOMBIE_HEIGHT = 67; 
			public static final int ZOMBIE_WIDTH = 60; 
			public static final int zombieArrI = 5; 
			public static final int zombieArrJ = 6; 
			public static final int zombieW = 64; 
			public static final int zombieH = 62; 
			public static final int zombieAttack = 2; 

			public static final int Pirate = 1; 
			public static final int PIRATE_HEIGHT = 67; 
			public static final int PIRATE_WIDTH = 90; 
			public static final int pirateArrI = 9;
			public static final int pirateArrJ = 6; 
			public static final int pirateW = 64; 
			public static final int pirateH = 40;
			public static final int IDLE = 0; 
			public static final int RUNNING = 1; 
			public static final int ATTACK = 6; 


			
			
	public static int GetSpriteAmount(int enemy_type, int enemy_state) {
		switch (enemy_state) {
			
		case ATTACK: 
			if (enemy_type == Pirate)
				return 2; 
		
		case zombieAttack: 
			if (enemy_type == Zombie)
				return 6; 
		case RUNNING:
			if (enemy_type == Zombie)
			return 5;

			else if(enemy_type == Pirate)
			return 2; 

		default: 
			return 0;

		}
	}

	public static int getMaxEnemyHealth(int enemyType) {
		switch (enemyType) {
			case 0:
				return 10; 		
			default:
				return 0;
		}
	}
}


	public static class PlayerConstants {
		public static final int IDLE = 0;
		public static final int RUNNING = 2;

		public static int GetSpriteAmount(int player_action) {
			switch (player_action) {
			case IDLE:
				return 4;
            case RUNNING: 
                return 4;
			default:
				return 1;
			}
		}
    }
}
		
