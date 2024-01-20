package Items;

import Main.Game;
import Entities.Player;

public class HealPotion {
    
    int healingAmount = 75;
    int quantity = 5;

    private Player player;
    public HealPotion(Player player) {
        this.player = player;
        this.quantity = quantity;
        this.healingAmount = healingAmount;
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

    public int getHealingAmount() {
        return healingAmount;
    }

}
