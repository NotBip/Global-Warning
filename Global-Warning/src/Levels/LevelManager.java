package Levels;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class LevelManager {

	private BufferedImage[] levelSprite;
	private Levels levelOne;
    

	public LevelManager() {
		importOutsideSprites();
		levelOne = new Levels(Levels.GetLevelData());
	}

	private void importOutsideSprites() {
		BufferedImage img = Levels.GetSpriteAtlas(Levels.LEVEL_ATLAS);
		levelSprite = new BufferedImage[48];
		for (int j = 0; j < 4; j++)
			for (int i = 0; i < 12; i++) {
				int index = j * 12 + i;
				levelSprite[index] = img.getSubimage(i * 32, j * 32, 32, 32);
			}
	}

	public void draw(Graphics g) {
		for (int j = 0; j < Levels.levelheight; j++)
			for (int i = 0; i < Levels.levelwidth; i++) {
				int index = levelOne.getSpriteIndex(i, j);
				g.drawImage(levelSprite[index], Levels.tilesize * i, Levels.tilesize * j, Levels.tilesize, Levels.tilesize, null);
			}
	}

	public void update() {

	}

}
