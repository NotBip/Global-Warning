package Entities.Planet1Enemies;

import Entities.Enemy;
import GameStates.Playing;

import static Utilities.Atlas.ZOMBIE_ATLAS;
import static Utilities.Constants.EnemyConstants.*;

public class Enemy1 extends Enemy {

    public Enemy1(float x, float y) {
        super(x, y, ZOMBIE_WIDTH, ZOMBIE_HEIGHT, Zombie, zombieArrI, zombieArrJ, zombieW, zombieH, ZOMBIE_ATLAS, 0, 1, 1.2f);
    }


}