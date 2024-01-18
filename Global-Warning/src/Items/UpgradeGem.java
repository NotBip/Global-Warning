package Items;

import Main.Game;

public class UpgradeGem {
    
    int damageBoost = 75;
    int firerateBoost = 10;
    int quantity = 0;

    public void addItem(int addQuantity) { 
        quantity += addQuantity;
    }

    public void useItem() {
        quantity --;
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
