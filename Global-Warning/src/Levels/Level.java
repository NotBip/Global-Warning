package Levels;

import java.awt.Color;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import Entities.Planet1Enemies.Enemy1;
import Entities.Planet1Enemies.Enemy2;
import Utilities.Constants;

public class Level {

	private BufferedImage img;
	public int[][] lvlData;


	//private ArrayList<EntityType> entityname = new ArrayList<>();

	private int lvlTilesWide;
	private int maxTilesOffset;
	private int maxLvlOffsetX;
	private Point playerSpawn; // Default spawn point in a room
	private Point leftSpawn; // Spawn point from the left door in a room
	private Point rightSpawn; // Spawn point from the right left in a room
	private Point leftTransition; // Transition point into the left door in a room
	private Point rightTransition; // Transition point into the right door in a room
	private boolean isWindy = false;
	ArrayList<Enemy2> Waterboi = new ArrayList<Enemy2>(); 
    ArrayList<Enemy1> Fireboi = new ArrayList<Enemy1>(); 

	public Level(BufferedImage img) {
		this.img = img;
		lvlData = new int[img.getHeight()][img.getWidth()];
		loadLevel();
		calcLvlOffsets();
	}

	private void loadLevel() {

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
		}
	}

	private void loadEntities(int greenValue, int x, int y) {
		switch (greenValue) {
		//case EntityName -> EntityName.add(new EntityName(x * Game.TILES_SIZE, y * Game.TILES_SIZE));
		case 1: Waterboi.add(new Enemy2(x * Constants.TILE_SIZE, y * Constants.TILE_SIZE, lvlData)); break; 
		case 98: playerSpawn = new Point(x * Constants.TILE_SIZE, y * Constants.TILE_SIZE); break;
		case 99: leftSpawn = new Point(x * Constants.TILE_SIZE, y * Constants.TILE_SIZE); break;
		case 100: rightSpawn = new Point(x * Constants.TILE_SIZE, y * Constants.TILE_SIZE); break;
		case 101: leftTransition = new Point(x * Constants.TILE_SIZE, y * Constants.TILE_SIZE); break;
		case 102: rightTransition = new Point((x+1) * Constants.TILE_SIZE - 1, y * Constants.TILE_SIZE); break;
		case 103: isWindy = true;
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

	public boolean getWindy() {
		return isWindy;
	}

	public ArrayList<Enemy2> getWaterBoi() { 
		return Waterboi; 
	}

	/*public ArrayList<Entity> getEntityName() {
		return EntityName;
	}*/
}
