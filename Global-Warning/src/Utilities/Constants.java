/**
***********************************************
* @Author : All
* @Originally made : 10 DEC, 2024
* @Last Modified: 22 JAN, 2024
* @Description: Library made for all constants
***********************************************
*/


package Utilities;

import GameStates.Playing;
import GameStates.SaveState;
import Main.Game;
import Objects.Saving.Checkpoint;
import UserInterface.SaveButton;

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

/**
***********************************************
* @Author : Nusayba
* @Originally made : 1 JAN, 2024
* @Last Modified: 22 JAN, 2024
* @Description: Library made for all button image constants
***********************************************
*/
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


		/**
	***********************************************
	* @Author : Hamad Mohammed
	* @Originally made : 15 JAN, 2024
	* @Last Modified: 18 JAN, 2024
	* @Description: Module to call enemy constants when needed.
	***********************************************
	*/
	public static class EnemyConstants {

			public static final String IDLE = "IDLE";
			public static final String WALK = "WALK"; 
			public static final String RUN = "RUN"; 
			public static final String ATTACK = "ATTACK"; 
			public static final String DEAD = "DEAD"; 
			public static final String MAGIC = "MAGIC"; 
			
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
			public static final int DEMONBOI_HEIGHT = 200 * (int) Game.SCALE; 
			public static final int DEMONBOI_WIDTH = 180 *(int) Game.SCALE; 
			public static final int demonAttackSpeed = 10; 
			public static final int demonboiArrI = 6; 
			public static final int demonboiArrJ = 22; 
			public static final int demonboiW = 288; 
			public static final int demonboiH = 161;
			public static final int demonIdle = 0; 
			public static final int demonWalk = 1; 
			public static final int demonAttack = 2; 
			public static final int demonDead = 4; 
			public static final float demonSpeed = 1f * Game.SCALE; 
			public static final int demonSizeX = 288 * 2 *(int) Game.SCALE; 
			public static final int demonSizeY = 161 * 2 *(int) Game.SCALE; 

			public static final int Shardboi = 3;
			public static final int SHARDBOI_HEIGHT = 90 * (int) Game.SCALE; 
			public static final int SHARDBOI_WIDTH = 90 *(int) Game.SCALE; 
			public static final int shardAttackSpeed = 15; 
			public static final int shardboiArrI = 5; 
			public static final int shardboiArrJ = 8; 
			public static final int shardboiW = 64; 
			public static final int shardboiH = 64;
			public static final int shardIdle = 0; 
			public static final int shardWalk = 1; 
			public static final int shardAttack = 2; 
			public static final int shardDead = 4; 
			public static final float shardSpeed = 1f * Game.SCALE; 
			public static final int shardSizeX = 140 *(int) Game.SCALE; 
			public static final int shardSizeY = 150 *(int) Game.SCALE;  

			public static final int Coolboi = 4;
			public static final int COOLBOI_HEIGHT = 130 * (int) Game.SCALE; 
			public static final int COOLBOI_WIDTH = 120 *(int) Game.SCALE; 
			public static final int coolAttackSpeed = 5; 
			public static final int coolboiArrI = 5; 
			public static final int coolboiArrJ = 23; 
			public static final int coolboiW = 80; 
			public static final int coolboiH = 80;
			public static final int coolIdle = 0; 
			public static final int coolWalk = 1; 
			public static final int coolAttack = 2; 
			public static final int coolDead = 4; 
			public static final float coolSpeed = 1.5f * Game.SCALE; 
			public static final int coolSizeX = 100 * 2 *(int) Game.SCALE; 
			public static final int coolSizeY = 100 * 2 *(int) Game.SCALE; 



			
		/**
		@Method Name: GetSpriteAmount
		@Author: hamad Mohammed
		@Creation Date: 15 JAN, 2024
		@Modified Date: 16 JAN, 2024
		@Description: Returns number of sprites per object
		@Parameters: int enemy_type, String enemy_state
		@Returns: int
		@Dependencies: N/A
		@Throws/Exceptions: N/A
		*/		
	public static int GetSpriteAmount(int enemy_type, String enemy_state) {
		switch (enemy_state) {

		case "IDLE": 
			if(enemy_type == Waterboi)
			return 1; 
			if(enemy_type == Fireboi) 
			return 1; 
			if(enemy_type == Demonboi)
			return 5; 
			if(enemy_type == Shardboi)
			return 7; 
			if(enemy_type == Coolboi)
			return 8; 

		case "WALK":
			if(enemy_type == Waterboi)
			return 6; 
			if(enemy_type == Fireboi)
			return 7;
			if(enemy_type == Demonboi)
			return 10; 
			if(enemy_type == Shardboi)
			return 7; 
			if(enemy_type == Coolboi)
			return 5; 

		case "RUN": 
			 if(enemy_type == Waterboi)
			return 5; 
			 if(enemy_type == Fireboi)
			return 2;
			if(enemy_type == Coolboi)
			return 5; 
			if(enemy_type == Demonboi)
			return 10; 

		case "ATTACK": 
			if(enemy_type == Waterboi)
			return 3; 
			if(enemy_type == Fireboi)
			return 6;
			if(enemy_type == Demonboi)
			return 14;  
			if(enemy_type == Shardboi)
			return 4; 
			if(enemy_type == Coolboi)
			return 11; 

		case "DEAD": 
			if(enemy_type == Waterboi)
			return 6; 
			if(enemy_type == Fireboi)
			return 6; 
			if(enemy_type == Demonboi)
			return 21; 
			if(enemy_type == Shardboi)
			return 5; 
			if(enemy_type == Coolboi)
			return 22; 

		case "MAGIC":
			if(enemy_type == Demonboi)
			return 5; 
			if(enemy_type == Fireboi)
			return 6; 
			if(enemy_type == Waterboi)
			return 3;
			if(enemy_type == Shardboi)
			return 4; 
			if(enemy_type == Coolboi)
			return 11; 

		default: 
			return 0;

		}
	}

		/**
		@Method Name: getMaxEnemyHealth
		@Author: hamad Mohammed
		@Creation Date: 15 JAN, 2024
		@Modified Date: 16 JAN, 2024
		@Description: Returns the max health for each enemy
		@Parameters: int enemyType
		@Returns: int
		@Dependencies: N/A
		@Throws/Exceptions: N/A
		*/		
	public static int getMaxEnemyHealth(int enemyType) {
		switch (enemyType) {
			case Fireboi:
				return 100; 	
			case Waterboi: 
				return 150; 
			case Demonboi: 
				return 2000; 
			case Shardboi: 
				return 200; 
			case Coolboi: 
				return 300; 
			default:
				return 0;
		}
	}


	
	/**
	@Method Name: getEnemyDamage
	@Author: hamad Mohammed
	@Creation Date: 15 JAN, 2024
	@Modified Date: 16 JAN, 2024
	@Description: Returns the max damage for each enemy
	@Parameters: int enemyType
	@Returns: int
	@Dependencies: N/A
	@Throws/Exceptions: N/A
	*/	
	public static int getEnemyDamage(int enemyType) { 
		switch (enemyType) {

			case Waterboi:
				return 10;
			case Fireboi: 
				return 10; 
			case Demonboi: 
				return 50; 
			case Shardboi: 
				return 20; 
			case Coolboi: 
				return 25; 
			default:
				return 0; 
		}
	}	

	
	/**
	@Method Name: findState
	@Author: hamad Mohammed
	@Creation Date: 15 JAN, 2024
	@Modified Date: 16 JAN, 2024
	@Description: Returns the the correct state for each enemy
	@Parameters: int enemy_type, String enemy_state
	@Returns: int
	@Dependencies: N/A
	@Throws/Exceptions: N/A
	*/
	public static int findState(int enemy_type, String enemy_state) { 
		switch(enemy_state) { 
			case "IDLE": 
				if(enemy_type == Waterboi) 
				return waterIdle; 
				if(enemy_type == Fireboi)
				return fireIdle; 
				if(enemy_type == Demonboi)
				return demonIdle; 
				if(enemy_type == Shardboi)
				return shardIdle; 
				if(enemy_type == Coolboi)
				return coolIdle; 

			case "WALK":
				if(enemy_type == Waterboi)
				return waterWalk; 
				if(enemy_type == Fireboi) 
				return fireWalk; 
				if(enemy_type == Demonboi)
				return demonWalk; 
				if(enemy_type == Shardboi)
				return shardWalk; 
				if(enemy_type == Coolboi)
				return coolWalk; 
			
			case "RUN": 
				if(enemy_type == Waterboi)
				return waterRunning; 
				if(enemy_type == Fireboi)
				return fireRun; 
				if(enemy_type == Demonboi)
				return demonWalk; 
				if(enemy_type == Shardboi)
				return shardWalk; 
				if(enemy_type == Coolboi)
				return coolWalk; 
			
			case "ATTACK": 
				if(enemy_type == Waterboi) 
				return waterAttack; 
				if(enemy_type == Fireboi)
				return fireAttack; 
				if(enemy_type == Demonboi)
				return demonAttack; 
				if(enemy_type == Shardboi)
				return shardAttack; 
				if(enemy_type == Coolboi)
				return coolAttack; 

			case "DEAD": 
				if(enemy_type == Waterboi)
				return 9; 
				if(enemy_type == Fireboi)
				return 9; 
				if(enemy_type == Demonboi)
				return demonDead; 
				if(enemy_type == Shardboi)
				return shardDead; 
				if(enemy_type == Coolboi)
				return coolDead; 

			case "MAGIC": 
				if(enemy_type == Waterboi)
				return waterAttack; 
				if(enemy_type == Fireboi)
				return fireAttack; 
				if(enemy_type == Demonboi)
				return 5; 
				if(enemy_type == Shardboi)
				return shardAttack; 
				if(enemy_type == Coolboi)
				return coolAttack; 

			default: 
				return 0; 
		}
	}


	/**
	@Method Name: getAttackSpeed
	@Author: hamad Mohammed
	@Creation Date: 15 JAN, 2024
	@Modified Date: 16 JAN, 2024
	@Description: Returns the the attack speed for each enemy
	@Parameters: int enemy_type
	@Returns: int
	@Dependencies: N/A
	@Throws/Exceptions: N/A
	*/
	public static int getAttackSpeed(int enemy_type) { 
		
		switch (enemy_type) {

			case Waterboi:
				return waterAttackSpeed; 
			case Fireboi: 
				return fireAttackSpeed;
			case Demonboi: 
				return demonAttackSpeed; 
			case Shardboi:
				return shardAttackSpeed; 
			case Coolboi: 
				return coolAttackSpeed; 	
			default:
				return 0;
		}
	}


	/**
	@Method Name: getFinalAttack
	@Author: hamad Mohammed
	@Creation Date: 15 JAN, 2024
	@Modified Date: 16 JAN, 2024
	@Description: Returns the last attack animation for each enemy
	@Parameters: int enemy_type
	@Returns: int
	@Dependencies: N/A
	@Throws/Exceptions: N/A
	*/
	public static int getFinalAttack(int enemy_type) { 
		switch (enemy_type) {

			case Waterboi: 
				return 3; 
			case Fireboi: 
				return 6; 
			case Demonboi: 
				return 9; 
			case Shardboi: 
				return 3; 
			case Coolboi:
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
					return playing.damageWeapon1; 
				case 2: 
				return playing.damageWeapon2; 
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

		public static int getGunDamage(Playing playing, int gun) {
			switch (gun) {
				case 1: 
					return (int) Playing.damageWeapon1; 
				case 2: 
					return (int) Playing.damageWeapon2; 
				default:
					return 0; 
			}
		}
    }

	/**
	***********************************************
	* @Author : Bobby Walden
	* @Originally made : 15 JAN, 2024
	* @Last Modified: 18 JAN, 2024
	* @Description: Manages the objects during the game session.
	***********************************************
	*/

	public static class objectConstants { 

		public static final int Spike = 0; 		
		public static final int Chest = 1;
		public static final int Door = 2;
		public static final int Explosion = 3;
		public static final int Sign = 4;
		public static final int SIGN_HEIGHT = 20;


		/**
		@Method Name: GetSpriteAmount
		@Author: Bobby Walden
		@Creation Date: 15 JAN, 2024
		@Modified Date: 16 JAN, 2024
		@Description: Returns number of sprites per object
		@Parameters: int objectType
		@Returns: int
		@Dependencies: N/A
		@Throws/Exceptions: N/A
		*/
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
		