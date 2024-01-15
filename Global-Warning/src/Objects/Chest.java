package Objects;

import java.util.Random;

import Main.Game;

public class Chest extends Object {
    
    public Chest(int x, int y, int object) { 
        super(x, y, object);    
        initHitbox(68, 52);
        xDrawOffset = 0;
        yDrawOffset = (int)(Game.SCALE * (32-this.hitbox.height));
        hitbox.y += yDrawOffset;
    }

    public void update() { 
        updateAnimationTick(); 
    }

    public void giveItem() {
        Random r = new Random();
        int randnum = r.nextInt(2-1) + 1;
        int quantity = 0;
        String item;
        switch (randnum) {
            case 1:
            item = "Potion";
            break;
            case 2:
            item = "Bomb";
            break;
        }
        randnum = r.nextInt(5 - 1) + 1;
        switch (randnum) {
            case 1:
            quantity = 1;
            break;
            case 2:
            quantity = 3;
            break;
            case 3:
            quantity = 5;
            break;
            case 4:
            quantity = 7;
            break;
        }
        //inventory add "item", quantity
    }


}