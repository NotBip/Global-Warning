package Objects;

import static Utilities.Atlas.SPIKE_ATLAS;
import static Utilities.Atlas.getSpriteAtlas;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import Entities.Planet1Enemies.*;
import GameStates.Playing;
import Levels.Level;

public class ObjectManager{

    private Playing playing; 
    private BufferedImage spike; 
    private int lvlData[][];

    
    public ObjectManager(Playing playing) { 
        this.playing = playing; 
        lvlData = playing.getLevelManager().getCurrentLevel().getLevelData(); 
        loadImage(); 

    }

    public void loadImage() { 
        spike = getSpriteAtlas(SPIKE_ATLAS); 
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
    
    public void draw(Graphics g, int xOffset, Level level)  { 
        drawSpikes(g, xOffset, level);
    }
    
    public void drawSpikes(Graphics g, int xOffset, Level level) { 
        for (Spike s : level.getSpike()) { 
            g.drawImage(spike, (int) s.getHitbox().x - xOffset, (int) s.getHitbox().y, (int) s.getHitbox().getWidth(), (int) s.getHitbox().getHeight(), null);
            g.drawRect((int) s.getHitbox().x - xOffset, (int) s.getHitbox().y, (int) s.getHitbox().getWidth(), (int) s.getHitbox().getHeight());
        }
    }


    
}