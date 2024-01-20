package Objects;

import Main.Game;

public class Spike extends Object {

    public float spikeW = 32; 
    public float spikeH = 24; 
    
    public Spike(int x, int y, int object) { 
        super(x, y, object); 
        initHitbox(x+16, 10, 16);
        xDrawOffset = 0;
		yDrawOffset = (int)(Game.SCALE * 8);
        hitbox.y += yDrawOffset;
    }
}
