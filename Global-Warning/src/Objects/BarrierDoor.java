package Objects;

import java.awt.geom.Rectangle2D;

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
    public boolean doorInteract = false, doorOpen = false, doorOpened = false;
    public Rectangle2D.Float interactHitbox;

    public BarrierDoor(int x, int y, int object) { 
        super(x, y, object); 
        this.aniSpeed = 10;
        initHitbox(40, 135);
        this.interactHitbox = new Rectangle2D.Float(this.hitbox.x-10, this.hitbox.y, this.hitbox.width+20, this.hitbox.height);
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
        if(doorOpen)
        updateAnimationTick(); 
    }

     /**
	 * @Method Name: resetDoor
	 * @author Nusayba Hamou
	 * @since 22 JAN 2024
	 * @Description: resets doors every time a save is loaded
	 * @Parameters: N/A
	 * @returns:N/A
	 * @Dependencies: N/A
	 * @Throws/Exceptions: N/A
	 **/

    public void resetDoor () {
        doorInteract = false;
         doorOpen = false;
        doorOpened = false;   
    }
}