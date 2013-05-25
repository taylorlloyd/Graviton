package game;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;


public class LevelPackButton extends MetalButton implements ActionListener {
	LevelPack level;
	public LevelPackButton(LevelPack level) {
		super(level.name);
		this.level = level;
		addActionListener(this);
		setBackground(new Color(0,0,0,0));
	}


	public void actionPerformed(ActionEvent e) {
		Window.current.toPane(new LevelPackPane(level));
	}
}
