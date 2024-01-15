package Objects;

import Main.Game;

public class HealthPickup extends Object {
    int quantity = 0;
    int healAmount = 10;
    public HealthPickup(int x, int y, int object) { 
        super(x, y, object);    
        initHitbox(32, 32);
        xDrawOffset = 0;
        yDrawOffset = (int)(Game.SCALE * (32-this.hitbox.height));
        hitbox.y += yDrawOffset;
    }

    public void update() { 
        updateAnimationTick(); 
    }
}