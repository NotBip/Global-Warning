package Objects;

import java.awt.geom.Rectangle2D;

import Main.Game;

public class BarrierDoor extends Object {
    public boolean doorInteract = false, doorOpen = false, doorOpened = false;
    public Rectangle2D.Float interactHitbox;

    public BarrierDoor(int x, int y, int object) { 
        super(x, y, object); 
        this.aniSpeed = 10;
        initHitbox(40, 135);
        this.interactHitbox = new Rectangle2D.Float(this.hitbox.x-10, this.hitbox.y, this.hitbox.width+20, this.hitbox.height);
        xDrawOffset = 0;
        yDrawOffset = (int)(Game.SCALE * (32-this.hitbox.height));
        hitbox.y += yDrawOffset;
    }

    public void update() { 
        if(doorOpen)
        updateAnimationTick(); 
    }
}