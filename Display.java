import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Display {

	public int rows, cols, width, height, scale;
	
	private JLabel[][] tiles;
	private ArrayList<BufferedImage> tileImages = new ArrayList<BufferedImage>();
	private BufferedImage[] tileMap;
	private ArrayList<BufferedImage> actorImages;
	private ArrayList<JLabel> actorLabels = new ArrayList<JLabel>();
	
	public BufferedImage playerImage;
	public BufferedImage enemyImage;
	
	public void addTileToMap(BufferedImage img) {
		System.out.println("added");
		tileImages.add(img);
	}
	
	public void scaleTiles() {
		tileMap = new BufferedImage[tileImages.size()];
		for (int i = 0; i < tileImages.size(); i++) {
			Image icon = new ImageIcon(tileImages.get(i).getScaledInstance(scale, scale, Image.SCALE_DEFAULT)).getImage();
			tileMap[i] = new BufferedImage(icon.getWidth(null), icon.getHeight(null), BufferedImage.TYPE_INT_ARGB);
			tileMap[i].getGraphics().drawImage(icon, 0, 0, null);
		}
	}
	
	public void setActorImages(BufferedImage player, BufferedImage enemy) {
		Image icon = new ImageIcon(player.getScaledInstance(scale, scale, Image.SCALE_DEFAULT)).getImage();
		playerImage = new BufferedImage(icon.getWidth(null), icon.getHeight(null), BufferedImage.TYPE_INT_ARGB);
		playerImage.getGraphics().drawImage(icon, 0, 0, null);
		
		icon = new ImageIcon(enemy.getScaledInstance(scale, scale, Image.SCALE_DEFAULT)).getImage();
		enemyImage = new BufferedImage(icon.getWidth(null), icon.getHeight(null), BufferedImage.TYPE_INT_ARGB);
		enemyImage.getGraphics().drawImage(icon, 0, 0, null);
	}
	
	/*
	 * constructor, accepts rows and cols for the map tiles, and a width and height to fit it in
	 */
	public Display(int r, int c, int w, int h, JPanel panel) {
		rows = r;
		cols = c;
		width = w;
		height = h;
		
		int labelWidth = width / cols;
		int labelHeight = height / rows;
		if (labelHeight < labelWidth) {
			scale = labelHeight;
		} else {
			scale = labelWidth;
		}
		
		tiles = new JLabel[rows][cols];
		
		for (int x = 0; x < rows; x++) {
			for (int y = 0; y < cols; y++) {
				tiles[x][y] = new JLabel();
				panel.add(tiles[x][y]);
				tiles[x][y].setBounds(scale * x, scale * y, scale, scale);
			}
		}
	}
	
	/*
	 * displays the current board based on the location of the current actors
	 */
	public void displayBoard(Board board, ArrayList<ActorController> actors) {
		int[][] lightMap = new int[0][0];
		if (board.darkened) {
			lightMap = calculateLightmap(board, actors);
		}
		for (int x = 0; x < board.getWidth(); x++) {
			for (int y = 0; y < board.getHeight(); y++) {
				int tileChoice = board.getTileAt(x, y);
				int lightMapValue = 4;
				if (board.darkened) {
					lightMapValue = lightMap[x][y];
					if (lightMapValue > 4) {
						lightMapValue = 4;
					}
				}
				
				tiles[x][y].setIcon(new ImageIcon(darkenTile(tileMap[tileChoice], lightMapValue/4.0)));
				//tiles[x][y].setIcon(new ImageIcon(tileMap[tileChoice]));
			}
		}
	}
	
	public void highlightActor(Board board, Actor act) {
		for (int x = 0; x < board.getWidth(); x++) {
			for (int y = 0; y < board.getHeight(); y++) {
				if (getDistance(x, y, act.x, act.y) <= act.distance) {
					tiles[x][y].setIcon(new ImageIcon(highlightTile(tileMap[board.getTileAt(x, y)], 1.2)));
				}
			}
		}
	}
	
	public int getDistance(int a1, int b1, int a2, int b2) {
		return Math.abs(a1 - a2) + Math.abs(b1 - b2);
	}
	
	/*
	 * copies the provided tile and darkens it if necessary
	 */
	public BufferedImage darkenTile(BufferedImage tile, double percent) {
		BufferedImage buffered = new BufferedImage(tile.getWidth(), tile.getHeight(), BufferedImage.TYPE_INT_ARGB);
		for (int i = 0; i < buffered.getWidth(); i++) {
            for (int j = 0; j < buffered.getHeight(); j++) {                    
            	int rgb = tile.getRGB(i, j);
            	//System.out.println(rgb);
            	Color c = new Color(rgb);
            	//System.out.println(c.getRed() + "|" + c.getGreen() + "|" + c.getBlue());
            	int red = (int)(c.getRed() * percent);
            	int green = (int)(c.getGreen() * percent);
            	int blue = (int)(c.getBlue() * percent);
            	c = new Color(red, green, blue);
            	//System.out.println(red + ":" + green + ":" + blue);
            	buffered.setRGB(i, j, c.getRGB());
            }
        }
		return buffered;
	}
	
	public BufferedImage highlightTile(BufferedImage tile, double percent) {
		BufferedImage buffered = new BufferedImage(tile.getWidth(), tile.getHeight(), BufferedImage.TYPE_INT_ARGB);
		for (int i = 0; i < buffered.getWidth(); i++) {
            for (int j = 0; j < buffered.getHeight(); j++) {                    
            	int rgb = tile.getRGB(i, j);
            	//System.out.println(rgb);
            	Color c = new Color(rgb);
            	//System.out.println(c.getRed() + "|" + c.getGreen() + "|" + c.getBlue());
            	int red = (int)(c.getRed() * (1/percent));
            	int green = (int)(c.getGreen() * (1/percent));
            	int blue = (int)(c.getBlue() * percent);
            	c = new Color(red, green, blue);
            	//System.out.println(red + ":" + green + ":" + blue);
            	buffered.setRGB(i, j, c.getRGB());
            }
        }
		return buffered;
	}
	
	//for debugging
	public void displayBlank() {
		for (int x = 0; x < rows; x++) {
			for (int y = 0; y < cols; y++) {
				tiles[x][y].setIcon(new ImageIcon(tileMap[0]));
			}
		}
	}
	
	public void displayActors(ArrayList<Actor> actors, JPanel panel) {
		for (Actor act: actors) {
			//place actor jlabel on the right square
			//if actor is a player
			JLabel newActor = new JLabel(new ImageIcon(playerImage));
			//else
			//JLabel newActor = new JLabel(new ImageIcon(enemyImage));
			actorLabels.add(newActor);
			panel.add(newActor);
			newActor.setBounds(500, 500, width/cols, height/rows);
		}
	}
	
	public void addPlayerLights(int[][] lightMap, ArrayList<ActorController> actors) {
		for (ActorController act: actors) {
			lightMap[act.actor.x][act.actor.y] = act.actor.light;
			//int[] pos = act.getRoundedPosition();
			//lightMap[pos[0]][pos[1]] = act.calcSightRadius();
		}
	}
	
	public int[][] calculateLightmap(Board board, ArrayList<ActorController> actors) {
		int[][] lightMap = new int[rows][cols];
		for (int x = 0; x < rows; x++) {
			for (int y = 0; y < cols; y++) {
				lightMap[x][y] = -1;
			}
		}
		addPlayerLights(lightMap, actors);
		boolean completed = false;
		while (!completed) {
			completed = true;
			//set up intermediate array
			int[][] temp = new int[rows][cols];
			for (int x = 0; x < rows; x++) {
				for (int y = 0; y < cols; y++) {
					if (lightMap[x][y] == -1) {
						completed = false;
						int avg = getNeighborAverage(x, y, lightMap, board);
						//drop 1 for light level
						if (avg != -1) {
							avg--;
							if (avg < 0) {
								avg = 0;
							}
							if (board.getWallAt(x, y)) {
								avg /= 2;
							}
						}
						temp[x][y] = avg;
					} else {
						temp[x][y] = -1;
					}
				}
			}
			//update lightmap based on temp
			for (int x = 0; x < rows; x++) {
				for (int y = 0; y < cols; y++) {
					//System.out.print(lightMap[x][y] + " ");
					if (temp[x][y] != -1) {
						
						lightMap[x][y] = temp[x][y];
						
					}
					
				}
				//System.out.println();
			}
		}
		return lightMap;
	}
	
	//get the average value of neighboring lights
	public int getNeighborAverage(int x, int y, int[][] lightMap, Board board) {
		int total = 0;
		int numNeighbors = 0;
		if (board.inBounds(x - 1, y) && lightMap[x - 1][y] != -1) {
			total += lightMap[x - 1][y];
			numNeighbors++;
		}
		if (board.inBounds(x + 1, y) && lightMap[x + 1][y] != -1) {
			total += lightMap[x + 1][y];
			numNeighbors++;
		}
		if (board.inBounds(x, y - 1) && lightMap[x][y - 1] != -1) {
			total += lightMap[x][y - 1];
			numNeighbors++;
		}
		if (board.inBounds(x, y + 1) && lightMap[x][y + 1] != -1) {
			total += lightMap[x][y + 1];
			numNeighbors++;
		}
		if (numNeighbors == 0) {
			return -1;
		}
		return total/numNeighbors;
	}
}
