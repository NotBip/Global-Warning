package Objects;

import Main.Game;

/**
***********************************************
* @Author : Bobby Walden
* @Originally made : 16 JAN, 2024
* @Last Modified: 16 JAN, 2024
* @Description: Creates and calls the animation for doors.
***********************************************
*/

public class BarrierDoor extends Object {
    protected boolean doorInteract = false, doorOpen = false, doorOpened = false;
    
    // Initializes Doors
    public BarrierDoor(int x, int y, int object) { 
        super(x, y, object); 
        initHitbox(130, 135);
        xDrawOffset = 0;
        yDrawOffset = (int)(Game.SCALE * (32-this.hitbox.height));
        hitbox.y += yDrawOffset;
    }

    /**
	@Method Name: update
	@Author: Bobby Walden
	@Creation Date: 16 JAN, 2024
	@Modified Date: 16 JAN, 2024
	@Description: Updates the animations for the door.
	@Parameters: N/A
	@Returns: N/A
	@Dependencies: Object
	@Throws/Exceptions: N/A
	*/
    public void update() { 
        updateAnimationTick(); 
    }
}