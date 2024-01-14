package Objects;

import Main.Game;

public class Spike extends Object {
    
    public Spike(int x, int y, int object) { 
        super(x, y, object); 
        initHitbox(32, 24);
        xDrawOffset = 0;
		yDrawOffset = (int)(Game.SCALE * 6);
        hitbox.y += yDrawOffset;
    }
}
