import java.util.ArrayList;

public class Board {
	
	public boolean darkened = false;
	public boolean closeQuarters;
	
	private int width, height;
	private boolean[][] wallMap;
	private int[][] tileMap;
	private boolean[][] visibleMap;
	
	public Board() {
	}
	
	/*
	 * assumes walls and tiles are the same dimensions
	 */
	public Board(int[][] tiles, boolean[][] walls) {
		tileMap = tiles;
		wallMap = walls;
		width = walls.length;
		height = walls[0].length;
		visibleMap = new boolean[width][height];
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public boolean getWallAt(int x, int y) {
		return wallMap[x][y];
	}
	
	public int getTileAt(int x, int y) {
		return tileMap[x][y];
	}
	
	public void calculateVisibility(ArrayList<Actor> actors) {
		for (Actor act: actors) {
			
		}
	}
	
	public boolean inBounds(int x, int y) {
		return x >= 0 && x < getWidth() && y >= 0 && y < getHeight();
	}
	
	public void inVisualRange() {
		
	}
	
}
