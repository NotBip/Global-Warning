package Utilities;

import GameStates.Playing;
import Main.Game;

public class Constants {

    // Game constants

    public static final int TILE_SIZE = 32;
    public static final int WIDTH_IN_TILES = 39;
    public static final int HEIGHT_IN_TILES = 21;
    public static final int GAME_WIDTH = WIDTH_IN_TILES * TILE_SIZE;
    public static final int GAME_HEIGHT = HEIGHT_IN_TILES * TILE_SIZE;
    public static final int animationSpeed = 15; 


    // States
    public static final int MENU = 0;
    public static final int PLAYING = 1;

	//weapon constants
	public static final int WEAPON_WIDTH = 50;
	public static final int WEAPON_HEIGHT = 50;


	//hover images
	public static final int HOVER_WIDTH = 100;
	public static final int HOVER_HEIGHT = 68;

    public static class Directions {
		public static final int LEFT = 0;
		public static final int UP = 1;
		public static final int RIGHT = 2;
		public static final int DOWN = 3;
  }


	public static class Buttons {
		public static final int B_WIDTH = 140;
		public static final int B_HEIGHT = 56;

		public static final int SAVE_B_WIDTH = 200;
		public static final int SAVE_B_HEIGHT = 66;

		public static final int PAUSE_B_WIDTH = 56;
		public static final int PAUSE_B_HEIGHT = 56;

		public static final int INVENTORY_B_WIDTH = 100;
		public static final int INVENTORY_B_HEIGHT = 81;

		public static final int SOUND_B_WIDTH = 42;
		public static final int SOUND_B_HEIGHT = 42;
	}

	public static class EnemyConstants {

			public static final String IDLE = "IDLE";
			public static final String WALK = "WALK"; 
			public static final String RUN = "RUN"; 
			public static final String ATTACK = "ATTACK"; 
			public static final String DEAD = "DEAD"; 

			public static final int Waterboi = 0; 
			public static final int WATERBOI_HEIGHT = 93 *(int) Game.SCALE; 
			public static final int WATERBOI_WIDTH = 60 *(int) Game.SCALE; 
			public static final int waterAttackSpeed = 20; 
			public static final int waterArrI = 10;
			public static final int waterArrJ = 8; 
			public static final int waterW = 144; 
			public static final int waterH = 165;
			public static final int waterIdle = 0; 
			public static final int waterWalk = 1; 
			public static final int waterRunning = 2; 
			public static final int waterAttack = 6; 
			public static final int waterDead = 9; 
			public static final float waterSpeed = 1.0f * Game.SCALE; 
			public static final int waterSizeX = 100*(int) Game.SCALE; 
			public static final int waterSizeY = 110*(int) Game.SCALE; 



			public static final int Fireboi = 1; 
			public static final int FIREBOI_HEIGHT = 93 *(int) Game.SCALE; 
			public static final int FIREBOI_WIDTH = 60 *(int) Game.SCALE; 
			public static final int fireAttackSpeed = 15; 
			public static final int fireboiArrI = 10; 
			public static final int fireboiArrJ = 8; 
			public static final int fireboiW = 144; 
			public static final int fireboiH = 165;
			public static final int fireIdle = 0; 
			public static final int fireWalk = 1; 
			public static final int fireRun = 2; 
			public static final int fireAttack = 7; 
			public static final int fireDead = 9; 
			public static final float fireSpeed = 1.5f * Game.SCALE; 
			public static final int fireSizeX = 100 *(int) Game.SCALE; 
			public static final int fireSizeY = 110 *(int) Game.SCALE; 


			
			
	public static int GetSpriteAmount(int enemy_type, String enemy_state) {
		switch (enemy_state) {

		case "IDLE": 
			if(enemy_type == Waterboi)
			return 1; 
			if(enemy_type == Fireboi) 
			return 1; 
		case "WALK":
			 if(enemy_type == Waterboi)
			return 6; 
			 if(enemy_type == Fireboi)
			return 7;
		case "RUN": 
			 if(enemy_type == Waterboi)
			return 5; 
			 if(enemy_type == Fireboi)
			return 2;
		case "ATTACK": 
			if(enemy_type == Waterboi)
			return 3; 
			if(enemy_type == Fireboi)
			return 6; 
		case "DEAD": 
			if(enemy_type == Waterboi)
			return 6; 
			if(enemy_type == Fireboi)
			return 6; 

		default: 
			return 0;

		}
	}

	public static int getMaxEnemyHealth(int enemyType) {
		switch (enemyType) {
			case Fireboi:
				return 100; 	
			case Waterboi: 
				return 100; 

			default:
				return 0;
		}
	}

	public static int getEnemyDamage(int enemyType) { 
		switch (enemyType) {

			case Waterboi:
				return 10; 
			
			case Fireboi: 
				return 10; 
				
			default:
				return 0; 
		}
	}

	public static int findState(int enemy_type, String enemy_state) { 
		switch(enemy_state) { 
			case "IDLE": 
				if(enemy_type == Waterboi) 
				return waterIdle; 
				if(enemy_type == Fireboi)
				return fireIdle; 

			case "WALK":
				if(enemy_type == Waterboi)
				return waterWalk; 
				if(enemy_type == Fireboi) 
				return fireWalk; 
			
			case "RUN": 
				if(enemy_type == Waterboi)
				return waterRunning; 
				if(enemy_type == Fireboi)
				return fireRun; 
			
			case "ATTACK": 
				if(enemy_type == Waterboi) 
				return waterAttack; 
				if(enemy_type == Fireboi)
				return fireAttack; 

			case "DEAD": 
				if(enemy_type == Waterboi)
				return 9; 
				if(enemy_type == Fireboi)
				return 9; 

			default: 
				return 0; 
		}
	}

	public static int getAttackSpeed(int enemy_type) { 
		
		switch (enemy_type) {

			case Waterboi:
				return waterAttackSpeed; 
			case Fireboi: 
				return fireAttackSpeed;
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
				return 6;
            case RUNNING: 
                return 3;
			default:
				return 1;
			}
		}

		public static int getPlayerDamage(Playing playing) { 
			switch (playing.gunIndex) {
				case 1: 
					return 10; 
				case 2: 
					return 20; 
				default:
					return 0; 
			}
		}
    }

	public static class objectConstants { 

		public static final int Spike = 0; 		
		public static final int Chest = 1;
		public static final int HealthPick = 2;

		public static final String IDLE = "IDLE"; 
		public static final String INTERACT = "INTERACT"; 

		public static int GetSpriteAmount(int objectType, String objectState) { 
			switch (objectState) { 
				case IDLE: 
					if (objectType == Spike)
					return 1; 
					if (objectType == Chest) 
					return 5;  
					if (objectType == HealthPick){
						return 1;
					}
				case INTERACT: 
					if (objectType == Spike) 
					return 1; 
					if (objectType == Chest) 
					return 5; 

				default:
					return 0; 
			}
		}

		
	}
}
		