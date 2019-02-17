import javax.swing.JButton;
import javax.swing.JLabel;

public class ActorEntry {
	public JLabel label;
	public JButton damageButton;
	
	public ActorEntry() {
		label = new JLabel();
		damageButton = new JButton("Apply Damage");
	}
	
	public void setText(String text) {
		label.setText(text);
	}
}
