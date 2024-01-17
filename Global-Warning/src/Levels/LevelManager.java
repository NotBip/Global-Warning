package Levels;

import static Utilities.Atlas.MENUBACKGROUND_ATLAS;
import static Utilities.Atlas.MENUBACKGROUND_ATLAS_FIRE;
import static Utilities.Atlas.MENUBACKGROUND_ATLAS_ICE;
import static Utilities.Atlas.MENUBACKGROUND_ATLAS_STORM;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import GameStates.Playing;
import Objects.Spike;
import Utilities.Constants;
import Utilities.LoadSave;

public class LevelManager {

	private BufferedImage[] levelSprite;
	private BufferedImage[] levelSpriteDefault;
	private BufferedImage[] levelSpriteFire;
	private BufferedImage[] levelSpriteIce;
	
	private BufferedImage[] waterSprite;
	private ArrayList<Level> levels;
	public static int lvlIndex = 0;
	private int aniTick;
	private int aniIndex;
	private Playing playing;

	public LevelManager(Playing playing) {
		this.playing = playing;
		importOutsideSprites();
		createWater();
		levels = new ArrayList<>();
		buildAllLevels();
	}

	private void createWater() {
		waterSprite = new BufferedImage[5];
		BufferedImage img = LoadSave.GetSpriteAtlas(LoadSave.WATER_TOP);
		for (int i = 0; i < 4; i++)
			waterSprite[i] = img.getSubimage(i * 32, 0, 32, 32);
		waterSprite[4] = LoadSave.GetSpriteAtlas(LoadSave.WATER_BOTTOM);
	}

	public void loadNextLevel() {
		
		Level newLevel = levels.get(lvlIndex);
		//game.getPlaying().getEnemyManager().generateEnemies();
		if(levels.get(lvlIndex).getWindy()) { //|| levels.get(lvlIndex).getStormy()) {
			levelSprite = levelSpriteDefault;
			playing.setBackGround(LoadSave.GetSpriteAtlas(MENUBACKGROUND_ATLAS_STORM));
		} else if (levels.get(lvlIndex).getStormy()) {
			levelSprite = levelSpriteFire;
			playing.setBackGround(LoadSave.GetSpriteAtlas(MENUBACKGROUND_ATLAS_FIRE));
		} else {
			levelSprite = levelSpriteIce;
			playing.setBackGround(LoadSave.GetSpriteAtlas(MENUBACKGROUND_ATLAS_ICE));
		}
		playing.getPlayer().loadLevelData(newLevel.getLevelData());
		playing.getPlayer().setWindy(newLevel.getWindy());
		playing.setMaxLvlOffset(newLevel.getLvlOffset());
		playing.getObjectManager().loadObjects(newLevel.getLevelData()); 
	}


	private void buildAllLevels() {
		BufferedImage[] allLevels = LoadSave.GetAllLevels();
		for (BufferedImage img : allLevels)
			levels.add(new Level(img, playing));
	}

	private void importOutsideSprites() {
		BufferedImage img = LoadSave.GetSpriteAtlas(LoadSave.LEVEL_ATLAS);
		levelSpriteDefault = new BufferedImage[48];
		for (int j = 0; j < 4; j++)
			for (int i = 0; i < 12; i++) {
				int index = j * 12 + i;
				levelSpriteDefault[index] = img.getSubimage(i * 32, j * 32, 32, 32);
			}
		img = LoadSave.GetSpriteAtlas(LoadSave.LEVEL_ATLAS_FIRE);
		levelSpriteFire = new BufferedImage[48];
		for (int j = 0; j < 4; j++)
			for (int i = 0; i < 12; i++) {
				int index = j * 12 + i;
				levelSpriteFire[index] = img.getSubimage(i * 32, j * 32, 32, 32);
			}
		img = LoadSave.GetSpriteAtlas(LoadSave.LEVEL_ATLAS_ICE);
		levelSpriteIce = new BufferedImage[48];
		for (int j = 0; j < 4; j++)
			for (int i = 0; i < 12; i++) {
				int index = j * 12 + i;
				levelSpriteIce[index] = img.getSubimage(i * 32, j * 32, 32, 32);
			}
	}

	public void draw(Graphics g, int offset) throws IOException {
		for (int j = 0; j < levels.get(lvlIndex).getLevelData().length; j++)
			for (int i = 0; i < levels.get(lvlIndex).getLevelData()[0].length; i++) {
				int index = levels.get(lvlIndex).getSpriteIndex(i, j);
				int x = Constants.TILE_SIZE * i;
				int y = Constants.TILE_SIZE * j;
				if (index == 48)
					g.drawImage(waterSprite[aniIndex], x - offset, y, Constants.TILE_SIZE, Constants.TILE_SIZE, null);
				else if (index == 49)
					g.drawImage(waterSprite[4], x - offset, y, Constants.TILE_SIZE, Constants.TILE_SIZE, null);
				else 
					g.drawImage(levelSprite[index], x - offset, y, Constants.TILE_SIZE, Constants.TILE_SIZE, null);
			}
		if(levels.get(lvlIndex).getCheckpoint()!= null) {
			
				levels.get(lvlIndex).getCheckpoint().draw(g, offset);
			
		}
	}

	public void update() {
		updateWaterAnimation();
	}

	public void setLevelType() {
		
	}

	private void updateWaterAnimation() {
		aniTick++;
		if (aniTick >= 40) {
			aniTick = 0;
			aniIndex++;

			if (aniIndex >= 4)
				aniIndex = 0;
		}
	}

	public Level getCurrentLevel() {
		return levels.get(lvlIndex);
	}


	public int getAmountOfLevels() {
		return levels.size();
	}

	public int getLevelIndex() {
		return lvlIndex;
	}

	public void setLevelIndex(int lvlIndex) {
		LevelManager.lvlIndex = lvlIndex;
	}

} 