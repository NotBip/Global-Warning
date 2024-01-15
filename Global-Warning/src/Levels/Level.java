package Levels;

import static Utilities.Constants.objectConstants.Spike;
import static Utilities.Constants.objectConstants.Chest;
import static Utilities.Constants.TILE_SIZE;
import java.awt.Color;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import Entities.Planet1Enemies.Enemy1;
import Entities.Planet1Enemies.Enemy2;

import Objects.Chest;
import Objects.Spike;

import Objects.Saving.Checkpoint;
import Utilities.Constants;

public class Level {

	private BufferedImage img;
	public int[][] lvlData;


	//private ArrayList<EntityType> entityname = new ArrayList<>();

	private int lvlTilesWide;
	private int maxTilesOffset;
	private int maxLvlOffsetX;
	private Point windSpawn; 
	private Point playerSpawn; // Default spawn point in a room
	private Point leftSpawn; // Spawn point from the left door in a room
	private Point rightSpawn; // Spawn point from the right left in a room
	private Point leftTransition; // Transition point into the left door in a room
	private Point rightTransition; // Transition point into the right door in a room
	public boolean isWindy = false;
	private ArrayList<Spike> spike = new ArrayList<Spike>(); 
	private ArrayList<Chest> chest = new ArrayList<Chest>(); 
	private Checkpoint checkpoint;
	private boolean isStormy = false;
	private ArrayList<Enemy2> Waterboi = new ArrayList<Enemy2>(); 
    private ArrayList<Enemy1> Fireboi = new ArrayList<Enemy1>(); 

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
			lvlData[y][x] = 11;
		else	
			lvlData[y][x] = redValue;
		switch (redValue) {
		}
	}

	private void loadEntities(int greenValue, int x, int y) {
		switch (greenValue) {
		//case EntityName -> EntityName.add(new EntityName(x * Game.TILES_SIZE, y * Game.TILES_SIZE));
		case 1: Waterboi.add(new Enemy2(x * Constants.TILE_SIZE, y * Constants.TILE_SIZE, lvlData)); break; 

		case 2: Fireboi.add(new Enemy1(x * Constants.TILE_SIZE, y * Constants.TILE_SIZE, lvlData)); break; 
		case 98: playerSpawn = new Point(x * Constants.TILE_SIZE, y * Constants.TILE_SIZE); break;
		case 99: leftSpawn = new Point(x * Constants.TILE_SIZE, y * Constants.TILE_SIZE); break;
		case 100: rightSpawn = new Point(x * Constants.TILE_SIZE, y * Constants.TILE_SIZE); break;
		case 101: leftTransition = new Point(x * Constants.TILE_SIZE, y * Constants.TILE_SIZE); break;
		case 102: rightTransition = new Point((x+1) * Constants.TILE_SIZE - 1, y * Constants.TILE_SIZE); break;
		case 103: isWindy = true; windSpawn = new Point(x * Constants.TILE_SIZE, y * Constants.TILE_SIZE); break; 
		case 104: checkpoint = new Checkpoint(x * Constants.TILE_SIZE, y * Constants.TILE_SIZE - TILE_SIZE, 45, 63);
				  playerSpawn = new Point(x * Constants.TILE_SIZE, y * Constants.TILE_SIZE); break;
		case 105: isStormy = true; break;
		
		}
	}

	public void resetEnemies() {
			Waterboi.clear();
			Fireboi.clear();
	}

	private void loadObjects(int blueValue, int x, int y) {
		switch (blueValue) {
			case 250: spike.add(new Spike(x * Constants.TILE_SIZE, y * Constants.TILE_SIZE, Spike)); break; 
			case 100: chest.add(new Chest(x * Constants.TILE_SIZE, y * Constants.TILE_SIZE, Chest)); 
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

	public boolean getStormy() {
		return isStormy;
	}

	public ArrayList<Enemy2> getWaterBoi() { 
		return Waterboi; 
	}

	public ArrayList<Enemy1> getFireBoi() { 
		return Fireboi; 
	}

	public ArrayList<Spike> getSpike() { 
		return spike; 
	}

	public ArrayList<Chest> getChest() { 
		return chest; 
	}

	public Checkpoint getCheckpoint() {
		return checkpoint;
	}
}