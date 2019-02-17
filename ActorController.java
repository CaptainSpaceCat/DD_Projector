import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;

public class ActorController {
		public Actor actor;
		public ActorEntry entry;
		
		public ActorController(int x, int y, boolean l, String n, int d, Actor.actorType t) {
			actor = new Actor(1, x, y, t);
			actor.hasLight = l;
			entry = new ActorEntry();
			actor.name = n;
		}
	}