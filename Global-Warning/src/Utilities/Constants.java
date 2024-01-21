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
		public static final int SAVE_B_HEIGHT = 64;

		public static final int PAUSE_B_WIDTH = 56;
		public static final int PAUSE_B_HEIGHT = 56;

		public static final int INVENTORY_B_WIDTH = 100;
		public static final int INVENTORY_B_HEIGHT = 81;

		public static final int SOUND_B_WIDTH = 42;
		public static final int SOUND_B_HEIGHT = 42;

		public static final int RESET_B_WIDTH = 100;
		public static final int RESET_B_HEIGHT = 46;

		public static final int LOAD_WIDTH = 133;
		public static final int LOAD_HEIGHT = 91;
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

			public static final int Demonboi = 2; 
			public static final int DEMONBOI_HEIGHT = 247 * (int) Game.SCALE; 
			public static final int DEMONBOI_WIDTH = 250 *(int) Game.SCALE; 
			public static final int demonAttackSpeed = 10; 
			public static final int demonboiArrI = 5; 
			public static final int demonboiArrJ = 22; 
			public static final int demonboiW = 288; 
			public static final int demonboiH = 160;
			public static final int demonIdle = 0; 
			public static final int demonWalk = 1; 
			public static final int demonAttack = 2; 
			public static final int demonDead = 4; 
			public static final float demonSpeed = 1.5f * Game.SCALE; 
			public static final int demonSizeX = 290 *(int) Game.SCALE; 
			public static final int demonSizeY = 253 *(int) Game.SCALE; 



			
			
	public static int GetSpriteAmount(int enemy_type, String enemy_state) {
		switch (enemy_state) {

		case "IDLE": 
			if(enemy_type == Waterboi)
			return 1; 
			if(enemy_type == Fireboi) 
			return 1; 
			if(enemy_type == Demonboi)
			return 5; 
		case "WALK":
			 if(enemy_type == Waterboi)
			return 6; 
			 if(enemy_type == Fireboi)
			return 7;
			 if(enemy_type == Demonboi)
			return 10; 
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
			if(enemy_type == Demonboi)
			return 14;  
		case "DEAD": 
			if(enemy_type == Waterboi)
			return 6; 
			if(enemy_type == Fireboi)
			return 6; 
			if(enemy_type == Demonboi)
			return 21; 

		default: 
			return 0;

		}
	}

	public static int getMaxEnemyHealth(int enemyType) {
		switch (enemyType) {
			case Fireboi:
				return 100; 	
			case Waterboi: 
				return 150; 
			case Demonboi: 
				return 2000; 
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
			
			case Demonboi: 
				return 25; 

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
				if(enemy_type == Demonboi)
				return demonIdle; 

			case "WALK":
				if(enemy_type == Waterboi)
				return waterWalk; 
				if(enemy_type == Fireboi) 
				return fireWalk; 
				if(enemy_type == Demonboi)
				return demonWalk; 
			
			case "RUN": 
				if(enemy_type == Waterboi)
				return waterRunning; 
				if(enemy_type == Fireboi)
				return fireRun; 
				if(enemy_type == Demonboi)
				return demonWalk; 
			
			case "ATTACK": 
				if(enemy_type == Waterboi) 
				return waterAttack; 
				if(enemy_type == Fireboi)
				return fireAttack; 
				if(enemy_type == Demonboi)
				return demonAttack; 

			case "DEAD": 
				if(enemy_type == Waterboi)
				return 9; 
				if(enemy_type == Fireboi)
				return 9; 
				if(enemy_type == Demonboi)
				return demonDead; 

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
			case Demonboi: 
				return demonAttackSpeed; 
			default:
				return 0;
		}
	}

	public static int getFinalAttack(int enemy_type) { 
		switch (enemy_type) {

			case Waterboi: 
				return 3; 
			case Fireboi: 
				return 6; 
			case Demonboi: 
				return 9; 
			default: 
				return 0;  
		}
	}


}


	public static class PlayerConstants {
		public static final int IDLE = 0;
		public static final int RUNNING = 2;
		public static final int DEAD = 3; 

		public static int GetSpriteAmount(int player_action) {
			switch (player_action) {
			case IDLE:
				return 6;
            case RUNNING: 
                return 3;
			case DEAD: 
				return 7; 
			default:
				return 1;
			}
		}

		public static int getPlayerDamage(Playing playing) { 
			switch (playing.gunIndex) {
				case 1: 
					System.out.println(playing.gunIndex);
					System.out.println("I'm Returning 10 Damage!");
					return 10 + (playing.getPlayer().getUpgradeGem().getDamageBoost() * playing.getPlayer().getUpgradeGem().getNumUpgrades()); 
				case 2: 
				System.out.println(playing.gunIndex);
				System.out.println("I'm Returning 20 Damage!");
					return 20 + (playing.getPlayer().getUpgradeGem().getDamageBoost() * playing.getPlayer().getUpgradeGem().getNumUpgrades()); 
				default:
					return 0; 
			}
		}

		public static int getPlayerDamage(Playing playing, int gun) { 
			switch (gun) {
				case 1: 
					return 10 + (playing.getPlayer().getUpgradeGem().getDamageBoost() * playing.getPlayer().getUpgradeGem().getNumUpgrades()); 
				case 2: 
					return 20 + (playing.getPlayer().getUpgradeGem().getDamageBoost() * playing.getPlayer().getUpgradeGem().getNumUpgrades()); 
				default:
					return 0; 
			}
		}

		public static int getGunFirerate(Playing playing, int gun) { 
			switch (gun) {
				case 1: 
					return (int) Playing.fireRateWeapon1; 
				case 2: 
					return (int) Playing.fireRateWeapon2; 
				default:
					return 0; 
			}
		}
    }

	public static class objectConstants { 

		public static final int Spike = 0; 		
		public static final int Chest = 1;
		public static final int Door = 2;
		public static final int Explosion = 3;


		public static int GetSpriteAmount(int objectType) { 
			switch (objectType) { 
				case Spike: 
					return 0;  
				case Chest: 
					return 5; 
				case Door: 
					return 10; 
				default:
					return 0; 
			}
		}

		
	}

}
		