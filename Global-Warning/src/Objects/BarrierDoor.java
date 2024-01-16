package Objects;

import Main.Game;

public class BarrierDoor extends Object {
    protected boolean doorInteract = false, doorOpen = false;
    

    public BarrierDoor(int x, int y, int object) { 
        super(x, y, object); 
        initHitbox(130, 135);
        xDrawOffset = 0;
        yDrawOffset = (int)(Game.SCALE * (32-this.hitbox.height));
        hitbox.y += yDrawOffset;
    }

    public void update() { 
        updateAnimationTick(); 
    }
}