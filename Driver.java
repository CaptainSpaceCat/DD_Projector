import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Driver implements ActionListener, MouseListener {

	public Board board;
	public Display driverDisplay, displayDisplay;
	public ArrayList<ActorController> actorControllers;

	public JFrame driverFrame, displayFrame;
	
	public BufferedImage playerImg = loadImage(new File("C:\\Users\\conno\\OneDrive\\Desktop\\Projector\\Sprites\\player.png"));
	public BufferedImage enemyImg = loadImage(new File("C:\\Users\\conno\\OneDrive\\Desktop\\Projector\\Sprites\\enemy.png"));

	public Driver() {
		//load in board and actors from file
		//or maybe for hackathon just do it manually
		int[] dims = getMapDims(mapPath + "cave.txt");
		int[][] map = new int[dims[0]][dims[1]];
		boolean[][] walls = new boolean[dims[0]][dims[1]];
		boolean dark = getMapDarkness(mapPath + "cave.txt");
		loadMap(map, walls, mapPath + "cave.txt");

		board = new Board(map, walls);
		board.darkened = dark;

		actorControllers = new ArrayList<ActorController>();
		actorControllers.add(new ActorController(0, 0, 4, "Suhas", playerImg, 5));
		actorControllers.add(new ActorController(7, 14, 8, "Elegant Wood", playerImg, 5));
		actorControllers.add(new ActorController(16, 10, 8, "Death Tyrant", enemyImg, 7));

		initDriverFrame();
		//initDisplayFrame();
		
	}

	public void initDriverFrame() {
		driverFrame = new JFrame();

		//initialize panel
		JPanel panel = new JPanel() {

			public void paintComponent(Graphics g) {
				super.paintComponent(g);
			}
		};
		panel.setLayout(null);

		driverFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		driverFrame.setTitle("D&D - Driver");
		Rectangle rec = applyFrameToScreen(driverFrame, 0);
		driverFrame.setSize(new Dimension(rec.width, rec.height));


		//engage panel
		driverFrame.add(panel);
		driverFrame.setVisible(true);

		//handle display
		driverDisplay = new Display(board.getWidth(), board.getHeight(), (int)(rec.width/1.0), (int)(rec.height/1.0), panel);
		File[] tiles = getFilesByExtention(new File(tilePath), "png");	
		for (File f: tiles) {
			driverDisplay.addTileToMap(loadImage(f));
		}
		driverDisplay.scaleTiles();
		driverDisplay.displayBoard(board, actorControllers);
		
		for (ActorController act: actorControllers) {
			//panel.add(act.actorIcon);
			//act.actorIcon.setBounds(700, 700, 64, 64);
		}

	}

	private void initDisplayFrame() {
		displayFrame = new JFrame();

		JPanel panel = new JPanel() {

			public void paintComponent(Graphics g) {
				super.paintComponent(g);
			}
		};
		panel.setLayout(null);
		displayFrame.addMouseListener(this);
		displayFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		displayFrame.setTitle("D&D - Display");
		Rectangle rec = applyFrameToScreen(displayFrame, 1);
		displayFrame.setSize(new Dimension(rec.width, rec.height));

		//engage panel
		displayFrame.add(panel);
		displayFrame.setVisible(true);

		//handle display
		displayDisplay = new Display(board.getWidth(), board.getHeight(), rec.width, rec.height, panel);
		File[] tiles = getFilesByExtention(new File(tilePath), "png");	
		for (File f: tiles) {
			displayDisplay.addTileToMap(loadImage(f));
		}
		displayDisplay.scaleTiles();
		displayDisplay.displayBoard(board, actorControllers);
	}

	private Rectangle applyFrameToScreen(JFrame frame, int screen) {
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice[] gs = ge.getScreenDevices();
		if (screen < gs.length) {
			gs[screen].setFullScreenWindow(frame);
			return gs[screen].getFullScreenWindow().getBounds();
		} else {
			gs[0].setFullScreenWindow(frame);
			return gs[0].getFullScreenWindow().getBounds();
		}

	}

	/*
	 * accepts a file, and returns that file's extension, or "" if none found
	 */
	private String getFileExtension(File f) {
		String fileName = f.getName();
		int dotIndex = fileName.lastIndexOf('.');
		return (dotIndex == -1) ? "" : fileName.substring(dotIndex + 1);
	}

	/*
	 * Load an image from a file into a BufferedImage
	 * used for tiles
	 */
	public BufferedImage loadImage(File f) {
		BufferedImage i = null;
		boolean success = true;
		System.out.println(f.getPath());
		try {
			i = ImageIO.read(f);
		} catch (IOException e) {
			System.err.println("Load failed.");
			success = false;
		}
		if (success) {
			System.out.println("Load successful.");
		}
		return i;
	}

	private File[] getFilesByExtention(File dir, String ext) {
		File[] flist = dir.listFiles();
		int num = 0;
		for (File f: flist) {
			if (f.isFile() && getFileExtension(f).equals(ext)) {
				num++;
			}
		}
		File[] result = new File[num];
		num = 0;
		for (File f: flist) {
			if (f.isFile() && getFileExtension(f).equals(ext)) {
				result[num] = f;
				num++;
			}
		}

		return result;
	}

	public boolean getMapDarkness(String path) {
		BufferedReader br;
		boolean result = false;
		try {
			br = new BufferedReader(new FileReader(path));
			String line = br.readLine();
			if (line.equals("darkened=TRUE")) {
				result = true;
			}
			br.close();
		} catch (Exception e) {
			System.err.println("Error: Failed to pull map dims from " + path);
			System.err.println(e);
		}
		return result;
	}

	public int[] getMapDims(String path) {
		int[] dims = new int[2];
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader(path));

			String line = br.readLine();
			line = br.readLine();
			dims[0] = Integer.parseInt(line.substring(0, line.indexOf(':')));
			dims[1] = Integer.parseInt(line.substring(line.indexOf(':')+1, line.length()));
			br.close();
		} catch (Exception e) {
			System.err.println("Error: Failed to pull map dims from " + path);
			System.err.println(e);
		}
		return dims;
	}

	public boolean loadMap(int[][] tiles, boolean[][] walls, String path) {
		BufferedReader br;
		int[] dims = new int[2];
		try {
			br = new BufferedReader(new FileReader(path));

			//get dims
			String line = br.readLine();
			line = br.readLine();
			dims[0] = Integer.parseInt(line.substring(0, line.indexOf(':')));
			dims[1] = Integer.parseInt(line.substring(line.indexOf(':')+1, line.length()));

			//tiles = new int[dims[0]][dims[1]];
			for (int h = 0; h < dims[1]; h++) {
				line = br.readLine();
				for (int w = 0; w < dims[0]; w++) {
					tiles[w][h] = Integer.parseInt("" +line.charAt(w));
				}
			}

			//walls = new boolean[dims[0]][dims[1]];
			for (int h = 0; h < dims[1]; h++) {
				line = br.readLine();
				for (int w = 0; w < dims[0]; w++) {
					walls[w][h] = (line.charAt(w) != '.');
				}
			}
			br.close();

		} catch (Exception e) {
			System.err.println("Error: Failed to pull map data from " + path);
			System.err.println(e);
			return false;
		}
		return true;
	}
	
	public int[] getMouseCoords(int x, int y) {
		int[] result = new int[2];
		result[0] = x/driverDisplay.scale;
		result[1] = y/driverDisplay.scale;
		return result;
	}
	
	public Actor getClickedActor(int x, int y) {
		for (ActorController act: actorControllers) {
			if (act.actor.x == x && act.actor.y == y) {
				return act.actor;
			}
		}
		return null;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		
	}

	private String tilePath = "C:\\Users\\conno\\OneDrive\\Desktop\\Projector\\Resources";
	private String mapPath = "C:\\Users\\conno\\OneDrive\\Desktop\\Projector\\Maps\\";

	//get it to run
	public static void main(String[] args) {
		new Driver();
	}

	private Actor selected = null;
	@Override
	public void mouseClicked(MouseEvent e) {
		System.out.println(e);
		int[] coords = getMouseCoords(e.getX(), e.getY());
		System.out.println(coords[0] + ":" + coords[1]);
		if (board.inBounds(coords[0],  coords[1])) {
			Actor act = getClickedActor(coords[0], coords[1]);
			if (selected != null) {
				if (act != null) {
					act.x = coords[0];
					act.y = coords[1];
				}
			} else {
				selected = act;
				driverDisplay.highlightActor(board, act);
				displayDisplay.highlightActor(board, act);
			}
		}
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
