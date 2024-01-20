package Items;

import Main.Game;
import Entities.Player;

public class UpgradeGem {
    
    int damageBoost = 75;
    int firerateBoost = 10;
    int quantity = 0;

    private Player player;
    public UpgradeGem(Player player) {
        this.player = player;
        this.quantity = quantity;
        this.damageBoost = damageBoost;
        this.firerateBoost = firerateBoost;
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

    public int getDamageBoost() {
        return damageBoost;
    }

    public int getFirerateBoost() {
        return firerateBoost;
    }

}
