import java.util.ArrayList;

public class Board {
	
	public boolean darkened = false;
	public boolean closeQuarters;
	
	private int width, height;
	private boolean[][] wallMap;
	private int[][] tileMap;
	private boolean[][] visibleMap;
	private boolean[][] highlightMap;
	
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
		highlightMap = new boolean[width][height];
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
	
	public void highlight(int x, int y, int distance) {
		
		
		for (int i = 0; i < distance; i++) {
			boolean[][] temp = new boolean[getWidth()][getHeight()];
			for (int a = 0; a < getWidth(); a++) {
				for (int b = 0; b < getHeight(); b++) {
					temp[a][b] = false;
				}
			}
			for (int a = 0; a < getWidth(); a++) {
				for (int b = 0; b < getHeight(); b++) {
					if (highlightMap[a][b] || (a == x && b == y)) {
						if (inBounds(a + 1, b) && !wallMap[a + 1][b]) {
							temp[a + 1][b] = true;
						}
						if (inBounds(a - 1, b) && !wallMap[a - 1][b]) {
							temp[a - 1][b] = true;
						}
						if (inBounds(a, b + 1) && !wallMap[a][b + 1]) {
							temp[a][b + 1] = true;
						}
						if (inBounds(a, b - 1) && !wallMap[a][b - 1]) {
							temp[a][b - 1] = true;
						}
					}
				}
			}
			for (int a = 0; a < getWidth(); a++) {
				for (int b = 0; b < getHeight(); b++) {
					highlightMap[a][b] = highlightMap[a][b] | temp[a][b];
				}
			}
		}
		
//		for (int a = 0; a < getWidth(); a++) {
//			for (int b = 0; b < getHeight(); b++) {
//				highlightMap[a][b] = getDistance(a, b, x, y) <= distance && !wallMap[a][b];
//			}
//		}
	}
	
	public void clearHighlighting() {
		for (int a = 0; a < getWidth(); a++) {
			for (int b = 0; b < getHeight(); b++) {
				highlightMap[a][b] = false;
			}
		}
	}
	
	public boolean getHighlight(int x, int y) {
		return highlightMap[x][y];
	}
	
	public int getDistance(int a1, int b1, int a2, int b2) {
		return Math.abs(a1 - a2) + Math.abs(b1 - b2);
	}
	
}
