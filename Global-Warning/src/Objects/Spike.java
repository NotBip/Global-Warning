/**
 * @author: Hamad Mohammed
 * @since: 10 Jan 2024
 * @Last Modified: 10 Jan 2024
 * @Description: Class used for generation spike hitboxes and initialzing its width and height. 
 */


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
