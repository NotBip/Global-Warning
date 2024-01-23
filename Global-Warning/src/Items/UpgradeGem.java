package Items;

import Main.Game;
import Entities.Player;

    /**
	***********************************************
	* @Author : Bobby Walden
	* @Originally made : 18 JAN, 2024
	* @Last Modified: 22 JAN, 2024
	* @Description: Manages the information for the uppgrade item.
	***********************************************
	*/
public class UpgradeGem {
    
    // Variables
    int damageBoost = 10;
    int firerateBoost = -20;
    int quantity = 3;
    int numUpgrades = 0;
    private Player player;

    // Initialize Upgrade
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

}
