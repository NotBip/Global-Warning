package Objects;

import static Utilities.Atlas.CHEST_ATLAS;
import static Utilities.Atlas.SPIKE_ATLAS;
import static Utilities.Atlas.DOOR_ATLAS;
import static Utilities.Atlas.getSpriteAtlas;
import static Utilities.Constants.objectConstants.*;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import Entities.Planet1Enemies.*;
import GameStates.Playing;
import Levels.Level;

/**
***********************************************
* @Author : Bobby Walden
* @Originally made : 15 JAN, 2024
* @Last Modified: 21 JAN, 2024
* @Description: Manages the objects during the game session.
***********************************************
*/

public class ObjectManager{

    // Variables
    private Playing playing; 
    private BufferedImage spikeImg;
    private BufferedImage[][] chestImg, doorImg; 
    private int[][] lvlData; 
    
    // Initialize Object Manager
    public ObjectManager(Playing playing) { 
        this.playing = playing; 
        loadImage(); 

    }

    /**
	@Method Name: loadImage
	@Author: Bobby Walden
	@Creation Date: 15 JAN, 2024
	@Modified Date: 16 JAN, 2024
	@Description: Loads the images for each object including dimensions
	@Parameters: N/A
	@Returns: N/A
	@Dependencies: Atlas
	@Throws/Exceptions: N/A
	*/
    public void loadImage() { 
        spikeImg = getSpriteAtlas(SPIKE_ATLAS);
        
        BufferedImage ChestSprite = getSpriteAtlas(CHEST_ATLAS);
        chestImg = new BufferedImage[8][5];
		for (int i = 0; i < chestImg.length; i++)
            for (int j = 0; j < chestImg[i].length; j++)
            chestImg[i][j] = ChestSprite.getSubimage(48 * j, 32 * i, 48, 32);
        
        BufferedImage DoorSprite = getSpriteAtlas(DOOR_ATLAS);
        doorImg = new BufferedImage[1][10];
		for (int i = 0; i < doorImg.length; i++)
            for (int j = 0; j < doorImg[i].length; j++)
            doorImg[i][j] = DoorSprite.getSubimage(594 * j, 706 * i, 594, 706);
        }
    
    /**
	@Method Name: loadObjects
	@Author: Bobby Walden
	@Creation Date: 15 JAN, 2024
	@Modified Date: 15 JAN, 2024
	@Description: Imports the level data into Object Manager
	@Parameters: int[][] lvlData
	@Returns: N/A
	@Dependencies: Level
	@Throws/Exceptions: N/A
	*/    
    public void loadObjects(int[][] lvlData) { 
        this.lvlData = lvlData; 
    }

    /**
	@Method Name: update
	@Author: Bobby Walden
	@Creation Date: 15 JAN, 2024
	@Modified Date: 16 JAN, 2024
	@Description: Updates the checks on each of the objects every tick.
	@Parameters: N/A
	@Returns: N/A
	@Dependencies: N/A
	@Throws/Exceptions: N/A
	*/
    public void update() { 
        checkSpikeTouch(); 
        updateChests();
        updateDoors();  

    }
    
    /**
	@Method Name: draw
	@Author: Bobby Walden
	@Creation Date: 15 JAN, 2024
	@Modified Date: 16 JAN, 2024
	@Description: Initiates the drawing methods of all objects.
	@Parameters: Graphics g, int xOffset
	@Returns: N/A
	@Dependencies: N/A
	@Throws/Exceptions: N/A
	*/
    public void draw(Graphics g, int xOffset)  { 
        drawSpikes(g, xOffset);
        drawChests(g, xOffset);
        drawDoors(g, xOffset);
        //drawTutorialSigns(g, xOffset);
    }

    /**
	@Method Name: checkInteracts
	@Author: Bobby Walden
	@Creation Date: 15 JAN, 2024
	@Modified Date: 16 JAN, 2024
	@Description: Updates the checks from player interactions.
	@Parameters: N/A
	@Returns: N/A
	@Dependencies: N/A
	@Throws/Exceptions: N/A
	*/
    public void checkInteracts() { 
         checkChestInteract();
         checkDoorInteract();
    }

    /**
	@Method Name: checkSpikeTouch
	@Author: Ryder Hodgson
	@Creation Date: 
	@Modified Date: 
	@Description: Checks whether an enemy or player is hitting a spike.
	@Parameters: N/A
	@Returns: N/A
	@Dependencies: Spike, Planet1Enemies, Playing
	@Throws/Exceptions: N/A
	*/
    public void checkSpikeTouch() { 
        for(Spike s : playing.getLevelManager().getCurrentLevel().getSpike()) {
            for(Enemy1 e : playing.getLevelManager().getCurrentLevel().getFireBoi()) {
                if(s.getHitbox().intersects(e.getHitbox()))
                e.dead();
            }
            for(Enemy2 e : playing.getLevelManager().getCurrentLevel().getWaterBoi()) {
                if(s.getHitbox().intersects(e.getHitbox()))
                e.dead();
            }
            
            if(playing.getPlayer().getHitbox().intersects(s.getHitbox()) && !playing.getPlayer().isImmune()) {
                playing.getPlayer().changeHealth(-20);
                return;
            }  
        }  
    }
    
    /**
	@Method Name: checkSpikeTouch
	@Author: Ryder Hodgson
	@Creation Date: 
	@Modified Date: 
	@Description: Checks whether an enemy or player is hitting a spike.
	@Parameters: Graphics g, int xOffset
	@Returns: N/A
	@Dependencies: Spike, Playing
	@Throws/Exceptions: N/A
	*/
    private void drawSpikes(Graphics g, int xOffset) { 
        for (Spike s : playing.getLevelManager().getCurrentLevel().getSpike()) { 
            // g.drawRect((int) s.getHitbox().x - xOffset, (int) s.getHitbox().y, (int) s.getHitbox().width, (int) s.getHitbox().height);
            g.drawImage(spikeImg, (int) (s.getHitbox().x - xOffset)-10, (int) s.getHitbox().y, (int) s.spikeW, (int) s.spikeH, null);
            }
    }

    /**
	@Method Name: drawChests
	@Author: Bobby Walden
	@Creation Date: 15 JAN, 2024
	@Modified Date: 21 JAN, 2024
	@Description: Draws every chest and each of its animations.
	@Parameters: Graphics g, int xOffset
	@Returns: N/A
	@Dependencies: Chest, KeyChest, Playing
	@Throws/Exceptions: N/A
	*/
    private void drawChests(Graphics g, int xOffset) { 
        for (Chest c : playing.getLevelManager().getCurrentLevel().getChest()){
            if (!c.chestInteract && !c.chestOpen && !c.chestOpened)
            g.drawImage(chestImg[2][c.getAniIndex()], (int) c.getHitbox().x - xOffset , (int) c.getHitbox().y, (int) c.getHitbox().width, (int) c.getHitbox().height, null);
            if (c.chestInteract && c.chestOpen && !c.chestOpened)
            g.drawImage(chestImg[3][c.getAniIndex()], (int) c.getHitbox().x - xOffset , (int) c.getHitbox().y, (int) c.getHitbox().width, (int) c.getHitbox().height, null);
            if (c.chestInteract && !c.chestOpen && c.chestOpened)
            g.drawImage(chestImg[3][GetSpriteAmount(Chest)-1], (int) c.getHitbox().x - xOffset , (int) c.getHitbox().y, (int) c.getHitbox().width, (int) c.getHitbox().height, null);
        }
        for (KeyChest c : playing.getLevelManager().getCurrentLevel().getKeyChest()){
            if (!c.chestInteract && !c.chestOpen && !c.chestOpened)
            g.drawImage(chestImg[2][c.getAniIndex()], (int) c.getHitbox().x - xOffset , (int) c.getHitbox().y, (int) c.getHitbox().width, (int) c.getHitbox().height, null);
            if (c.chestInteract && c.chestOpen && !c.chestOpened)
            g.drawImage(chestImg[3][c.getAniIndex()], (int) c.getHitbox().x - xOffset , (int) c.getHitbox().y, (int) c.getHitbox().width, (int) c.getHitbox().height, null);
            if (c.chestInteract && !c.chestOpen && c.chestOpened)
            g.drawImage(chestImg[3][GetSpriteAmount(Chest)-1], (int) c.getHitbox().x - xOffset , (int) c.getHitbox().y, (int) c.getHitbox().width, (int) c.getHitbox().height, null);
        }
    }

    /**
	@Method Name: drawDoors
	@Author: Bobby Walden
	@Creation Date: 16 JAN, 2024
	@Modified Date: 16 JAN, 2024
	@Description: Draws every door and each of its animations.
	@Parameters: Graphics g, int xOffset
	@Returns: N/A
	@Dependencies: BarrierDoor, Playing
	@Throws/Exceptions: N/A
	*/
    private void drawDoors(Graphics g, int xOffset) { 
        for (BarrierDoor d : playing.getLevelManager().getCurrentLevel().getDoor()) { 
            d.drawHitbox(g, xOffset);
            if (!d.doorInteract && !d.doorOpen && !d.doorOpened)
            g.drawImage(doorImg[0][0], (int) d.getHitbox().x - xOffset, (int) d.getHitbox().y, (int) (130 - d.getHitbox().width), (int) d.getHitbox().height, null);
            if (d.doorInteract && d.doorOpen && !d.doorOpened)
            g.drawImage(doorImg[0][d.getAniIndex()], (int) d.getHitbox().x - xOffset, (int) d.getHitbox().y, (int) (130 - d.getHitbox().width), (int) d.getHitbox().height, null);
            if (d.doorInteract && !d.doorOpen && d.doorOpened)
            g.drawImage(doorImg[0][GetSpriteAmount(Door) - 1], (int) d.getHitbox().x - xOffset, (int) d.getHitbox().y, (int) (130 - d.getHitbox().width), (int) d.getHitbox().height, null);

        }
    }
    
    /**
	@Method Name: checkChestInteract
	@Author: Bobby Walden
	@Creation Date: 15 JAN, 2024
	@Modified Date: 21 JAN, 2024
	@Description: Checks for chest interaction, and initiates action.
	@Parameters: N/A
	@Returns: N/A
	@Dependencies: Chest, KeyChest, Playing
	@Throws/Exceptions: N/A
	*/
    public void checkChestInteract() { 
        for (Chest c : playing.getLevelManager().getCurrentLevel().getChest()) { 
             if (!c.chestInteract) { 
                    if (c.getHitbox().intersects(playing.getPlayer().getHitbox())) { 
                        c.chestInteract = true;
                        c.giveItem(playing.player);
                        return; 
                    }
             }
        }
        for (KeyChest c : playing.getLevelManager().getCurrentLevel().getKeyChest()) { 
            if (!c.chestInteract) { 
                   if (c.getHitbox().intersects(playing.getPlayer().getHitbox())) { 
                       c.chestInteract = true;
                       c.giveItem(playing.player);
                       return; 
                   }
            }
       }
    }

    /**
	@Method Name: checkDoorInteract
	@Author: Bobby Walden
	@Creation Date: 16 JAN, 2024
	@Modified Date: 16 JAN, 2024
	@Description: Checks for door interaction, checks for key, and initiates action.
	@Parameters: N/A
	@Returns: N/A
	@Dependencies: BarrierDoor, Playing
	@Throws/Exceptions: N/A
	*/
    public void checkDoorInteract() { 
        for (BarrierDoor d : playing.getLevelManager().getCurrentLevel().getDoor()) { 
            if (!d.doorInteract) { 
                if (d.getHitbox().intersects(playing.getPlayer().getHitbox()) && playing.player.getItemQuantity(3) > 0) { 
                    d.doorInteract = true;
                    playing.player.useItem(3, playing);
                    return; 
                }
                else if (d.getHitbox().intersects(playing.getPlayer().getHitbox()) && playing.player.getItemQuantity(3) == 0) {
                    System.out.println("YOU DON'T HAVE THE BALLS");
                }
            }
        }
    }

    /**
	@Method Name: updateChests
	@Author: Bobby Walden
	@Creation Date: 16 JAN, 2024
	@Modified Date: 21 JAN, 2024
	@Description: Updates the chest animations and states.
	@Parameters: N/A
	@Returns: N/A
	@Dependencies: Chest, KeyChest, Playing
	@Throws/Exceptions: N/A
	*/
    private void updateChests() {
		for (Chest c : playing.getLevelManager().getCurrentLevel().getChest()) {
			if (c.chestInteract && !c.chestOpened)
				    c.chestOpen = true; 
			c.update();
	 	if (c.getAniIndex() == 4 && c.getAniTick() == 0 && c.chestOpen && !c.chestOpened){
			c.chestOpen = (false);
            c.chestOpened = true;
        }   
		}
        for (KeyChest c : playing.getLevelManager().getCurrentLevel().getKeyChest()) {
			if (c.chestInteract && !c.chestOpened)
				    c.chestOpen = true; 
			c.update();
	 	if (c.getAniIndex() == 4 && c.getAniTick() == 0 && c.chestOpen && !c.chestOpened){
			c.chestOpen = (false);
            c.chestOpened = true;
        }   
		}
	}

    /**
	@Method Name: updateDoors
	@Author: Bobby Walden
	@Creation Date: 16 JAN, 2024
	@Modified Date: 16 JAN, 2024
	@Description: Updates the door animations and states
	@Parameters: N/A
	@Returns: N/A
	@Dependencies: BarrierDoor, Playing
	@Throws/Exceptions: N/A
	*/
    private void updateDoors() { 
        for (BarrierDoor d : playing.getLevelManager().getCurrentLevel().getDoor()) { 
            if (d.doorInteract && !d.doorOpened) 
                d.doorOpen = true; 
            d.update();
        if (d.getAniIndex() == 9 && d.getAniTick() == 0 && d.doorOpen && !d.doorOpened) { 
            d.doorOpen = false; 
            d.doorOpened = true; 
        }
        }
    }


}