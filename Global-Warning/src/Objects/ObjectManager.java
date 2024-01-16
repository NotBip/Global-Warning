package Objects;

import static Utilities.Atlas.CHEST_ATLAS;
import static Utilities.Atlas.SPIKE_ATLAS;
import static Utilities.Atlas.DOOR_ATLAS;
import static Utilities.Atlas.getSpriteAtlas;
import static Utilities.Constants.objectConstants.*;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
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
        for (Chest c : playing.getLevelManager().getCurrentLevel().getChest()) { 
            if(!c.chestOpen)
            c.updateAnimationTick(); 
        }
        for (BarrierDoor d : playing.getLevelManager().getCurrentLevel().getDoor()) { 
            if (d.doorOpen) { 
                if (d.getState() != INTERACT && d.getState() != DOORSTOP) { 
                    d.setState(INTERACT);
                    d.aniTick = 0; 
                    d.aniIndex = 0; 
                }
                else if (d.aniIndex == GetSpriteAmount(Door, INTERACT) - 1 && d.aniTick >= d.aniSpeed - 1) { 
                    d.doorInteract = true;
                    d.setState(DOORSTOP);
                } else { 
                    d.updateAnimationTick(); 
                }
                return; 
            }
        }

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
                return; // stop checking the other spikes around the player if one has already been checked (multiple spikes may be intersecting)
            }  
        }  
    }
    
    public void draw(Graphics g, int xOffset)  { 
        drawSpikes(g, xOffset);
        drawChests(g, xOffset);
        drawDoors(g, xOffset);
    }
    
    private void drawSpikes(Graphics g, int xOffset) { 
        for (Spike s : playing.getLevelManager().getCurrentLevel().getSpike()) { 
            g.drawImage(spikeImg, (int) s.getHitbox().x - xOffset, (int) s.getHitbox().y, (int) s.getHitbox().getWidth(), (int) s.getHitbox().getHeight(), null);
            }
    }

    private void drawChests(Graphics g, int xOffset) { 
        for (Chest c : playing.getLevelManager().getCurrentLevel().getChest()) { 
            int type = 2; 
            if (c.chestInteract)
                type = 3;
            if (!c.chestInteract)
            g.drawImage(chestImg[type][c.getAniIndex()], (int) c.getHitbox().x - xOffset, (int) c.getHitbox().y, (int) c.getHitbox().width, (int) c.getHitbox().height, null);
            else if (c.chestInteract && c.getAniIndex() <= GetSpriteAmount(Chest, c.getState())){
            g.drawImage(chestImg[3][GetSpriteAmount(Chest, c.getState())-1], (int) c.getHitbox().x - xOffset, (int) c.getHitbox().y, (int) c.getHitbox().width, (int) c.getHitbox().height, null); 
            c.chestOpen = true; 
            }
        }
    }

    private void drawDoors(Graphics g, int xOffset) { 
        for (BarrierDoor d : playing.getLevelManager().getCurrentLevel().getDoor()) { 
            if(d.getState() == IDLE)
            g.drawImage(doorImg[0][0], (int) d.getHitbox().x - xOffset, (int) d.getHitbox().y, (int) d.getHitbox().width, (int) d.getHitbox().height, null);
            if(d.doorInteract && d.getState() != IDLE)
            g.drawImage(doorImg[0][d.getAniIndex()], (int) d.getHitbox().x - xOffset, (int) d.getHitbox().y, (int) d.getHitbox().width, (int) d.getHitbox().height, null);
            if(d.doorInteract && d.getState() == DOORSTOP)
            g.drawImage(doorImg[0][9], (int) d.getHitbox().x - xOffset, (int) d.getHitbox().y, (int) d.getHitbox().width, (int) d.getHitbox().height, null);

        }
    }

    public void setChestInteract() { 
        for (Chest c : playing.getLevelManager().getCurrentLevel().getChest()) { 
            if(c.getHitbox().intersects(playing.getPlayer().getHitbox()) && c.getState() != INTERACT) { 
                c.setState(INTERACT);
                c.giveItem();
            }
            else {
                c.setState(IDLE);
            }
        }
    }

    public void setDoorInteract() { 
        for (BarrierDoor d : playing.getLevelManager().getCurrentLevel().getDoor()) { 
            if(d.getHitbox().intersects(playing.getPlayer().getHitbox()) && d.getState() != INTERACT && playing.getPlayer().getKey() == true) { 
                d.doorInteract = true; 
                d.setState(INTERACT);
                d.doorOpen = true; 
                System.out.println("You opened the door!");
            }
                else if (d.getHitbox().intersects(playing.getPlayer().getHitbox()) && playing.getPlayer().getKey() == false) {
                System.out.println("You don't have the BALLS");
            }
                else if (d.getState() != INTERACT) { 
                    d.setState(IDLE);
                    d.doorInteract = false; 
                    d.doorOpen = false; 
                }
        }
    }
}