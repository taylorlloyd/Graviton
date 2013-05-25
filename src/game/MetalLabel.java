package game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class MetalLabel extends JPanel {
	Image bg;
	public MetalLabel(JLabel timer) {
		timer.setFont(DataManager.loadFont("text.ttf").deriveFont(25f));
		timer.setForeground(new Color(15,120,0));
		bg=DataManager.loadImage("blank.png");
		setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx=0;
		gbc.gridy=0;
		gbc.fill=gbc.BOTH;
		gbc.anchor = gbc.CENTER;
		gbc.insets = new Insets(6,0,0,0);
		add(timer,gbc);
	}
	public void paintComponent(Graphics g) {
		g.drawImage(bg, 0, 0, null);
	}
	public Dimension getSize() {
		return new Dimension(174,34);
	}
	public Dimension getMinimumSize() {
		return new Dimension(174,34);
	}
	public Dimension getMaximumSize() {
		return new Dimension(174,34);
	}
	public Dimension getPreferredSize() {
		return new Dimension(174,34);
	}
}
