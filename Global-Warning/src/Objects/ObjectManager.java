package Objects;

import static Utilities.Atlas.CHEST_ATLAS;
import static Utilities.Atlas.SPIKE_ATLAS;
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
    private BufferedImage[][] chestImg; 
    private int lvlData[][];
    private boolean chestInteract = false, chestOpen = false; 

    
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

    }

    public void loadObjects(int[][] lvlData) { 
        this.lvlData = lvlData; 
    }

    public void update() { 
        checkSpikeTouch(); 
        for (Chest c : playing.getLevelManager().getCurrentLevel().getChest()) { 
            if(!chestOpen)
            c.updateAnimationTick(); 
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
    }
    
    private void drawSpikes(Graphics g, int xOffset) { 
        for (Spike s : playing.getLevelManager().getCurrentLevel().getSpike()) { 
            g.drawImage(spikeImg, (int) s.getHitbox().x - xOffset, (int) s.getHitbox().y, (int) s.getHitbox().getWidth(), (int) s.getHitbox().getHeight(), null);
            }
    }

    private void drawChests(Graphics g, int xOffset) { 
        for (Chest c : playing.getLevelManager().getCurrentLevel().getChest()) { 
            System.out.println(c.getState());
            int type = 2; 
            if (chestInteract)
                type = 3;
            if (!chestInteract)
            g.drawImage(chestImg[type][c.getAniIndex()], (int) c.getHitbox().x - xOffset, (int) c.getHitbox().y, (int) c.getHitbox().width, (int) c.getHitbox().height, null);
            else if (chestInteract && c.getAniIndex() <= GetSpriteAmount(Chest, c.getState())){
            g.drawImage(chestImg[3][GetSpriteAmount(Chest, c.getState())-1], (int) c.getHitbox().x - xOffset, (int) c.getHitbox().y, (int) c.getHitbox().width, (int) c.getHitbox().height, null); 
            chestOpen = true; 
            }
        }
    }

    public void setChestInteract() { 
        for (Chest c : playing.getLevelManager().getCurrentLevel().getChest()) { 
            if(c.getHitbox().intersects(playing.getPlayer().getHitbox())) { 
                chestInteract = true; 
                c.setState(INTERACT);
            }
            else if (!chestInteract) { 
                c.setState(IDLE);
            }
        }
    }
}