package Entities;

import static Utilities.Constants.getMaxEnemyHealth;
import static Utilities.Constants.Directions.LEFT;
import static Utilities.Constants.Directions.RIGHT;

public abstract class Enemy extends Entity{

    protected int enemyType; 
    protected int direction = LEFT; 

    public Enemy(float x, float y, int width, int height, int EnemyType) {
        super(x, y, width, height);
        this.enemyType = EnemyType; 

        maxHealth = getMaxEnemyHealth(EnemyType);
        currentHealth = maxHealth; 
        // x.speed ryrder bad lol push pl
    }

    protected void turnTowardsPlayer(Player player) {
		if (player.hitbox.x > hitbox.x)
			walkDir = RIGHT;
		else
			walkDir = LEFT;
	}





}