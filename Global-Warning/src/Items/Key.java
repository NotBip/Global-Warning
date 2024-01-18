package Items;

import Main.Game;

public class Key {
    
    private int quantity;

    public Key(){
    }

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

}
