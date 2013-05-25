package game;
import java.awt.Graphics;

import javax.swing.JPanel;


public abstract class AnimatablePane extends JPanel{
	public abstract void paintComponent(Graphics g, boolean paintBackground);
	public abstract void paint(Graphics g, boolean paintComponent);
}
