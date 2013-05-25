package game;

import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;

import javax.swing.*;
public class LevelPane extends AnimatablePane implements ActionListener{
	static Image background;
	JButton back;
	public LevelLoader levels;
	public LevelPane() {
		super();
		levels = new LevelLoader();
		if(background == null)
			background = DataManager.loadImage("horizon.png");
		if(TopBar.background == null)
			TopBar.background = DataManager.loadImage("gamebar.png");
		setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx=0;
		back = new JButton(new ImageIcon(DataManager.loadImage("back.png")));
		back.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
		back.setRolloverIcon(new ImageIcon(DataManager.loadImage("back_over.png")));
		back.addActionListener(this);
		back.setActionCommand("back");
		back.setBorderPainted(false);
		add(back, gbc);
		gbc.gridx=1;
		gbc.gridy=0;
		add(new JLabel(new ImageIcon(DataManager.loadImage("title.png"))),gbc);
		gbc.gridy++;
		for(String l : levels.packnames) {
			if(levels.levelpacks.get(l).playable()) {
				add(new LevelPackButton(levels.levelpacks.get(l)),gbc);
			} else {
				MetalButton b= new LevelPackButton(levels.levelpacks.get(l));
				b.setEnabled(false);
				add(b, gbc);
			}
			gbc.gridy++;
		}
	}
	public void paintComponent(Graphics g) {
		paintComponent(g,true);
	}
	@Override
	public void paint(Graphics g, boolean paintComponent) {
		paint(g);
	}
	@Override
	public void paintComponent(Graphics g, boolean paintBackground) {
		if(paintBackground)
			g.drawImage(background, 0, 0, 800, 600, null);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		Window.current.toPane(Window.current.menuPane);
	}
}
