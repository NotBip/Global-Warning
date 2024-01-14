package Objects;

import java.awt.Graphics;
import GameStates.Playing;
import Utilities.Constants.ObjectConstants;

import static Utilities.Constants.ObjectConstants.*;
import static Utilities.Atlas.CHEST_ATLAS;

public class Chest extends Object{



    public Chest(float x, float y) {
        super(x, y, CHEST_WIDTH, CHEST_HEIGHT, Chest, chestArrI, chestW, chestH, CHEST_ATLAS, CHEST_WIDTH, -1, chestSizeX, chestSizeY);
        super.inAir = true;

}
}