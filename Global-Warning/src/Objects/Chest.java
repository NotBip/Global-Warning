package Objects;

import Main.Game;

public class Chest extends Object {
    
    public Chest(int x, int y, int object) { 
        super(x, y, object);    
        initHitbox(42, 42);
        xDrawOffset = 0;
        yDrawOffset = (int)(Game.SCALE * (32-this.hitbox.height));
        hitbox.y += yDrawOffset;
    }


}