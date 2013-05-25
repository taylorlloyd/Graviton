package game;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;


public class BGPane extends JPanel {
	Image background;
	public BGPane() {
		background = DataManager.loadImage("horizon.png");
	}
	public void paintComponent(Graphics g) {
		g.drawImage(background, 0, 0, null);
	}
}
