package Levels;

import java.awt.Color;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import Utilities.Constants;

//import Entities. (etc.)
import Main.Game;
//import Objects. (etc.)

import static Utilities.Constants.*;

public class Level {

	private BufferedImage img;
	public int[][] lvlData;

	//private ArrayList<EntityType> entityname = new ArrayList<>();

	private int lvlTilesWide;
	private int maxTilesOffset;
	private int maxLvlOffsetX;
	private Point playerSpawn;
	private Point leftSpawn;
	private Point rightSpawn;
	private Point leftTransition;
	private Point rightTransition;

	public Level(BufferedImage img) {
		this.img = img;
		lvlData = new int[img.getHeight()][img.getWidth()];
		loadLevel();
		calcLvlOffsets();
	}

	private void loadLevel() {

		// Looping through the image colors just once. Instead of one per
		// object/enemy/etc..
		// Removed many methods in HelpMethods class.

		for (int y = 0; y < img.getHeight(); y++)
			for (int x = 0; x < img.getWidth(); x++) {
				Color c = new Color(img.getRGB(x, y));
				int red = c.getRed();
				int green = c.getGreen();
				int blue = c.getBlue();

				loadLevelData(red, x, y);
				loadEntities(green, x, y);
				loadObjects(blue, x, y);
			}
	}

	private void loadLevelData(int redValue, int x, int y) {
		if (redValue >= 50)
			lvlData[y][x] = 0;
		else
			lvlData[y][x] = redValue;
		switch (redValue) {
		//case 0, 1, 2, 3, 30, 31, 33, 34, 35, 36, 37, 38, 39 -> 
		//grass.add(new Grass((int) (x * Game.TILES_SIZE), (int) (y * Game.TILES_SIZE) - Game.TILES_SIZE, getRndGrassType(x)));
		}
	}

	private int getRndGrassType(int xPos) {
		return xPos % 2;
	}

	private void loadEntities(int greenValue, int x, int y) {
		switch (greenValue) {
		//case EntityName -> EntityName.add(new EntityName(x * Game.TILES_SIZE, y * Game.TILES_SIZE));
		case 98: playerSpawn = new Point(x * Constants.TILE_SIZE, y * Constants.TILE_SIZE); break;
		case 99: leftSpawn = new Point(x * Constants.TILE_SIZE, y * Constants.TILE_SIZE); break;
		case 100: rightSpawn = new Point(x * Constants.TILE_SIZE, y * Constants.TILE_SIZE); break;
		case 101: leftTransition = new Point(x * Constants.TILE_SIZE, y * Constants.TILE_SIZE); break;
		case 102: rightTransition = new Point((x+1) * Constants.TILE_SIZE - 1, y * Constants.TILE_SIZE); break;
		}
	}

	private void loadObjects(int blueValue, int x, int y) {
		switch (blueValue) {
		//case ObjectName (, + any other objects of same type) -> ObjectType.add(new ObjectType(x * Game.TILES_SIZE, y * Game.TILES_SIZE, blueValue));
		}
	}

	private void calcLvlOffsets() {
		lvlTilesWide = img.getWidth();
		maxTilesOffset = lvlTilesWide - Constants.WIDTH_IN_TILES;
		maxLvlOffsetX = Constants.TILE_SIZE * maxTilesOffset;
	}

	public int getSpriteIndex(int x, int y) {
		return lvlData[y][x];
	}

	public int[][] getLevelData() {
		return lvlData;
	}

	public int getLvlOffset() {
		return maxLvlOffsetX;
	}

	public Point getPlayerSpawn() {
		return playerSpawn;
	}

	public Point getLeftSpawn() {
		return leftSpawn;
	}

	public Point getRightSpawn() {
		return rightSpawn;
	}

	public Point getLeftTransition() {
		return leftTransition;
	}

	public Point getRightTransition() {
		return rightTransition;
	}

	/*public ArrayList<Entity> getEntityName() {
		return EntityName;
	}*/

}