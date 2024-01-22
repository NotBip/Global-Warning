package Items;

import Main.Game;
import Entities.Player;

public class UpgradeGem {
    
    int damageBoost = 10;
    int firerateBoost = -20;
    int quantity = 0;
    int numUpgrades = 0;

    private Player player;
    public UpgradeGem(Player player) {
        this.player = player;
    
    }


    public void addItem(int addQuantity) { 
        this.quantity += addQuantity;
    }

    public void useItem() {
        this.quantity --;
        this.numUpgrades ++;
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

    public int getNumUpgrades() {
        return numUpgrades;
    }

    public void setNumUpgrades(int num) {
        numUpgrades = num;
    }

}
