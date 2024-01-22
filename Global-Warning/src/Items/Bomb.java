package Items;

import Main.Game;
import Entities.Player;

public class Bomb {
    
    int damageAmount = 100;
    int quantity =0;

    private Player player;
    public Bomb(Player player) {
        this.player = player;
    }


    public void addItem(int addQuantity) { 
        this.quantity += addQuantity;
    }

    public void useItem() {
        this.quantity --;
    }

    public int getQuantity() {
        return this.quantity;
    }

    public void setQuantity(int savedQuantity) {
        this.quantity = savedQuantity;
    }

    public int getDamageAmount() {
        return damageAmount;
    }

}
