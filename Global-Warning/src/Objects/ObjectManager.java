package Objects;

import static Utilities.Atlas.CHEST_ATLAS;
import static Utilities.Atlas.SPIKE_ATLAS;
import static Utilities.Atlas.DOOR_ATLAS;
import static Utilities.Atlas.getSpriteAtlas;
import static Utilities.Constants.objectConstants.*;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import Entities.Player;
import Entities.Planet1Enemies.*;
import GameStates.Playing;
import Levels.Level;

public class ObjectManager{

    private Playing playing; 
    private BufferedImage spikeImg;
    private BufferedImage[][] chestImg, doorImg; 
    private int lvlData[][];
    
    public ObjectManager(Playing playing) { 
        this.playing = playing; 
        lvlData = playing.getLevelManager().getCurrentLevel().getLevelData(); 
        loadImage(); 

    }

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

    public void loadObjects(int[][] lvlData) { 
        this.lvlData = lvlData; 
    }

    public void update() { 
        checkSpikeTouch(); 
        updateChests();
        updateDoors();  

    }
    
    public void draw(Graphics g, int xOffset)  { 
        drawSpikes(g, xOffset);
        drawChests(g, xOffset);
        drawDoors(g, xOffset);
    }

    public void checkInteracts() { 
         checkChestInteract();
         checkDoorInteract();
    }

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
    
    private void drawSpikes(Graphics g, int xOffset) { 
        for (Spike s : playing.getLevelManager().getCurrentLevel().getSpike()) { 
            g.drawImage(spikeImg, (int) s.getHitbox().x - xOffset, (int) s.getHitbox().y, (int) s.getHitbox().getWidth(), (int) s.getHitbox().getHeight(), null);
            }
    }

    private void drawChests(Graphics g, int xOffset) { 
        for (Chest c : playing.getLevelManager().getCurrentLevel().getChest()){
            if (!c.chestInteract && !c.chestOpen && !c.chestOpened){
            c.resetAnimation();//reset get item animation
            g.drawImage(chestImg[2][c.getAniIndex()], (int) c.getHitbox().x - xOffset , (int) c.getHitbox().y, (int) c.getHitbox().width, (int) c.getHitbox().height, null);
            }
            if (c.chestInteract && c.chestOpen && !c.chestOpened){
            g.drawImage(chestImg[3][c.getAniIndex()], (int) c.getHitbox().x - xOffset , (int) c.getHitbox().y, (int) c.getHitbox().width, (int) c.getHitbox().height, null);
          
            }
            if (c.chestInteract && !c.chestOpen && c.chestOpened){
            g.drawImage(chestImg[3][GetSpriteAmount(Chest)-1], (int) c.getHitbox().x - xOffset , (int) c.getHitbox().y, (int) c.getHitbox().width, (int) c.getHitbox().height, null);
            c.drawItem(g,(int)c.getHitbox().x - xOffset);//draws item final anim
        }
        }
    }

    private void drawDoors(Graphics g, int xOffset) { 
        for (BarrierDoor d : playing.getLevelManager().getCurrentLevel().getDoor()) { 
            if (!d.doorInteract && !d.doorOpen && !d.doorOpened)
            g.drawImage(doorImg[0][0], (int) d.getHitbox().x - xOffset, (int) d.getHitbox().y, (int) d.getHitbox().width, (int) d.getHitbox().height, null);
            if (d.doorInteract && d.doorOpen && !d.doorOpened)
            g.drawImage(doorImg[0][d.getAniIndex()], (int) d.getHitbox().x - xOffset, (int) d.getHitbox().y, (int) d.getHitbox().width, (int) d.getHitbox().height, null);
            if (d.doorInteract && !d.doorOpen && d.doorOpened)
            g.drawImage(doorImg[0][GetSpriteAmount(Door) - 1], (int) d.getHitbox().x - xOffset, (int) d.getHitbox().y, (int) d.getHitbox().width, (int) d.getHitbox().height, null);

        }
    }
    
    public void checkChestInteract() { 
        for (Chest c : playing.getLevelManager().getCurrentLevel().getChest()) { 
             if (!c.chestInteract) { 
                    if (c.getHitbox().intersects(playing.getPlayer().getHitbox())) { 
                        c.chestInteract = true;
                        return; 
                    }
             }
        }
    }

    public void checkDoorInteract() { 
        for (BarrierDoor d : playing.getLevelManager().getCurrentLevel().getDoor()) { 
            if (!d.doorInteract) { 
                if (d.getHitbox().intersects(playing.getPlayer().getHitbox())) { 
                    d.doorInteract = true; 
                    return; 
                }
            }
        }
    }

    private void updateChests() {
		for (Chest c : playing.getLevelManager().getCurrentLevel().getChest()) {
			if (c.chestInteract && !c.chestOpened)
				    c.chestOpen = true; 
			c.update();
	 	if (c.getAniIndex() == 4 && c.getAniTick() == 0 && c.chestOpen && !c.chestOpened){
			c.chestOpen = (false);
            c.chestOpened = true;
            c.giveItem();
        }   
		}
	}

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