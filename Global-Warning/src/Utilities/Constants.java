package Utilities;

import Main.Game;

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

			public static final String IDLE = "IDLE";
			public static final String WALK = "WALK"; 
			public static final String RUN = "RUN"; 
			public static final String ATTACK = "ATTACK"; 

			public static final int Pirate = 0; 
			public static final int PIRATE_HEIGHT = 67*(int) Game.SCALE; 
			public static final int PIRATE_WIDTH = 90*(int) Game.SCALE; 
			public static final int pirateArrI = 9;
			public static final int pirateArrJ = 6; 
			public static final int pirateW = 64; 
			public static final int pirateH = 40;
			public static final int pirateIdle = 0; 
			public static final int pirateRunning = 1; 
			public static final int pirateAttack = 6; 
			public static final float pirateSpeed = 1.2f * Game.SCALE; 
			public static final int pirateSizeX = 100*(int) Game.SCALE; 
			public static final int pirateSizeY = 85*(int) Game.SCALE; 

			public static final int Fireboi = 1; 
			public static final int FIREBOI_HEIGHT = 93 *(int) Game.SCALE; 
			public static final int FIREBOI_WIDTH = 60 *(int) Game.SCALE; 
			public static final int fireboiArrI = 5; 
			public static final int fireboiArrJ = 8; 
			public static final int fireboiW = 159; 
			public static final int fireboiH = 163;
			public static final int fireIdle = 0; 
			public static final int fireWalk = 1; 
			public static final int fireRun = 2; 
			public static final float fireSpeed = 1.5f * Game.SCALE; 
			public static final int fireSizeX = 100 *(int) Game.SCALE; 
			public static final int fireSizeY = 110 *(int) Game.SCALE; 


			
			
	public static int GetSpriteAmount(int enemy_type, String enemy_state) {
		switch (enemy_state) {

		case "IDLE": 
			if(enemy_type == Pirate)
			return 4; 
			if(enemy_type == Fireboi) 
			return 5; 
		case "WALK":
			 if(enemy_type == Pirate)
			return 2; 
			 if(enemy_type == Fireboi)
			return 7;
		case "RUN": 
			 if(enemy_type == Pirate)
			return 2; 
			 if(enemy_type == Fireboi)
			return 2;
		case "ATTACK": 
			if(enemy_type == Pirate)
			return 3; 
			if(enemy_type == Fireboi)
			return 5; 
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

	public static int getEnemyDamage(int enemyType) { 
		switch (enemyType) {

			case Pirate:
				return 1; 		
			default:
				return 0; 
		}
	}

	public static int findState(int enemy_type, String enemy_state) { 
		switch(enemy_state) { 
			case "IDLE": 
				if(enemy_type == Pirate) 
				return pirateIdle; 
				if(enemy_type == Fireboi)
				return fireIdle; 

			case "WALK":
				if(enemy_type == Pirate)
				return pirateRunning; 
				if(enemy_type == Fireboi) 
				return fireWalk; 
			
			case "RUN": 
				if(enemy_type == Pirate)
				return pirateRunning; 
				if(enemy_type == Fireboi)
				return fireRun; 
			
			case "ATTACK": 
				if(enemy_type == Pirate) 
				return pirateAttack; 
				if(enemy_type == Fireboi)
				return fireIdle; 
			
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
		
