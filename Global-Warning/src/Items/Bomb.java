package Items;

import Main.Game;

public class Bomb {
    
    int damageAmount = 100;
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

    public int getDamageAmount() {
        return damageAmount;
    }

}
