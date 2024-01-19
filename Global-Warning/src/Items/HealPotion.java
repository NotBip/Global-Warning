package Items;

import Main.Game;
import Entities.Player;

public class HealPotion {
    
    private Player player;
    public HealPotion(Player player) {
        this.player = player;
    }

    int healingAmount = 75;
    int quantity = 5;

    public void addItem(int addQuantity) { 
        quantity += addQuantity;
    }

    public void useItem() {
        quantity --;
        player.updatePotion();
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int savedQuantity) {
        quantity = savedQuantity;
    }

    public int getHealingAmount() {
        return healingAmount;
    }

}
