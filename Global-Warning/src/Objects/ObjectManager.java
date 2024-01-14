package Objects;

import static Utilities.Atlas.SPIKE_ATLAS;
import static Utilities.Atlas.getSpriteAtlas;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import Entities.Enemy;
import Entities.Player;
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

    public void update(Player player, Enemy enemy, Level level) { 
        checkSpikeTouch(player, enemy, level); 
    }
    
    public void checkSpikeTouch(Player player, Enemy enemy, Level level) { 
        for (Spike s : level.getSpike()) { 
            if(s.getHitbox().intersects(player.getHitbox()))
                player.dead();
            if(s.getHitbox().intersects(enemy.getHitbox()))
                enemy.dead(); 
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