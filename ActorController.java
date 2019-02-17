import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class ActorController {
		public Actor actor;
		public JLabel actorIcon;
		public JLabel actorEntry;
		
		public ActorController(int x, int y, int l, String n, BufferedImage img, int d) {
			actor = new Actor(x, y, l, n, d);
			actorIcon = new JLabel(new ImageIcon(img));
			actorEntry = new JLabel(n);
		}
	}