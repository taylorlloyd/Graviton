package game;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import editor.EditorWindow;


public class MenuPane extends BGPane implements ActionListener{
	MetalButton play;
	MetalButton highScores;
	MetalButton settings;
	MetalButton editor;
	MetalButton quit;
	JLabel imageLabel;
	public MenuPane() {
		play = new MetalButton("Play");
		highScores = new MetalButton("High Scores");
		settings = new MetalButton("Settings");
		editor = new MetalButton("Editor");
		quit = new MetalButton("Quit");
		play.addActionListener(this);
		highScores.addActionListener(this);
		highScores.setEnabled(false);//Until High Scores are implemented
		settings.addActionListener(this);
		editor.addActionListener(this);
		quit.addActionListener(this);
		setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = gbc.HORIZONTAL;
		gbc.gridwidth = 2;
		gbc.gridy++;
		gbc.weighty = 1;
		gbc.weightx = 1;
		add(new JLabel(new ImageIcon(DataManager.loadImage("title.png"))),gbc);
		gbc.weighty = 0.33;
		gbc.gridwidth = 1;
		gbc.fill = gbc.NONE;
		gbc.gridy++;
		add(play, gbc);
		gbc.gridy++;
		add(highScores, gbc);
		gbc.gridy++;
		add(settings, gbc);
		gbc.gridy++;
		add(editor, gbc);
		gbc.gridy++;
		add(quit, gbc);
		imageLabel = new JLabel(new ImageIcon(DataManager.loadImage("play1.png")));
		gbc.gridx=1;
		gbc.gridy = 1;
		gbc.gridheight = 5;
		add(imageLabel, gbc);
	}
	@Override
	public void actionPerformed(ActionEvent ae) {
		if(ae.getSource().equals(play)) {
			Window.current.toPane(Window.current.levelPane);
		} else if(ae.getSource().equals(settings)) {
			Window.current.toPane(new SettingsPane());
		} else if(ae.getSource().equals(quit)) {
			System.exit(0);
		} else if(ae.getSource().equals(editor)) {
			new EditorWindow();
			Window.current.setVisible(false);
		}
		
	}
}
