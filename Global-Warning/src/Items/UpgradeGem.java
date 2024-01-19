package Items;

import Main.Game;
import Entities.Player;

public class UpgradeGem {
    
    Player player;
    int damageBoost = 75;
    int firerateBoost = 10;
    int quantity = 0;

    public void addItem(int addQuantity) { 
        quantity += addQuantity;
    }

    public void useItem() {
        quantity --;
        player.updateUpgrade();
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int savedQuantity) {
        quantity = savedQuantity;
    }

    public int getDamageBoostAmount() {
        return damageBoost;
    }

    public int getFirerateBoostAmount() {
        return firerateBoost;
    }

}
