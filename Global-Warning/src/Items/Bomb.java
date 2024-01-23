package Items;

import Main.Game;
import Entities.Player;

    /**
	***********************************************
	* @Author : Bobby Walden
	* @Originally made : 18 JAN, 2024
	* @Last Modified: 22 JAN, 2024
	* @Description: Manages the information for the bomb item.
	***********************************************
	*/

public class Bomb {
    
    // Variables
    int damageAmount = 100;
    int quantity = 5;
    private Player player;

    // Initialize Bomb Item
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
