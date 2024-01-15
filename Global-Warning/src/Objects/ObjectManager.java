package Objects;

import static Utilities.Atlas.CHEST_ATLAS;
import static Utilities.Atlas.SPIKE_ATLAS;
import static Utilities.Atlas.getSpriteAtlas;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

import Entities.Player;
import Entities.Planet1Enemies.*;
import GameStates.Playing;
import Levels.Level;

public class ObjectManager implements KeyListener{

    private Playing playing; 
    private BufferedImage spikeImg, chestImg;
    private int lvlData[][];

    
    public ObjectManager(Playing playing) { 
        this.playing = playing; 
        lvlData = playing.getLevelManager().getCurrentLevel().getLevelData(); 
        loadImage(); 

    }

    public void loadImage() { 
        spikeImg = getSpriteAtlas(SPIKE_ATLAS);
        chestImg = getSpriteAtlas(CHEST_ATLAS);  
    }

    public void loadObjects(int[][] lvlData) { 
        this.lvlData = lvlData; 
    }

    public void update() { 
        checkSpikeTouch(); 
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

    public void chestInteract() { 
        for (Chest c : playing.getLevelManager().getCurrentLevel().getChest()) { 
                if (playing.getPlayer().getHitbox().intersects(c.getHitbox())) 
                    System.out.println("Open Chest!");            
                else 
                    System.out.println("No Chest Found!");
            }
        }

    
    
    public void draw(Graphics g, int xOffset, Level level)  { 
        drawSpikes(g, xOffset, level);
        drawChests(g, xOffset, level);
    }
    
    public void drawSpikes(Graphics g, int xOffset, Level level) { 
        for (Spike s : level.getSpike()) { 
            g.drawImage(spikeImg, (int) s.getHitbox().x - xOffset, (int) s.getHitbox().y, (int) s.getHitbox().getWidth(), (int) s.getHitbox().getHeight(), null);
        }
    }

    public void drawChests(Graphics g, int xOffset, Level level) { 
        for (Chest c : level.getChest()) { 
            g.drawImage(chestImg, (int) c.getHitbox().x - xOffset, (int) c.getHitbox().y, (int) c.getHitbox().width, (int) c.getHitbox().height, null); 
        }


    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }


    
}