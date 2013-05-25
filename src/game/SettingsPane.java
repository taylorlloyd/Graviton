package game;

import java.awt.Color;
import java.awt.Font;
import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

import javax.swing.JLabel;

public class SettingsPane extends BGPane implements ActionListener{
	MetalButton soundToggle;
	MetalButton fsToggle;
	MetalButton beginToggle;
	JButton back;
	
	public SettingsPane() {
		setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		back = new JButton(new ImageIcon(DataManager.loadImage("back.png")));
		back.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
		back.setRolloverIcon(new ImageIcon(DataManager.loadImage("back_over.png")));
		back.addActionListener(this);
		back.setActionCommand("back");
		back.setBorderPainted(false);
		gbc.gridx=0;
		gbc.gridy=0;
		add(back,gbc);
		gbc.gridx++;
		gbc.gridwidth = 2;
		gbc.weighty = 1;
		add(transLabel("Settings",35),gbc);
		gbc.gridy++;
		gbc.gridwidth = 1;
		add(transLabel("Sound",20), gbc);
		soundToggle = new MetalButton(("true".equals(SettingsManager.get("sound")) ? "On" : "Off"));
		soundToggle.addActionListener(this);
		soundToggle.setEnabled(false);
		gbc.gridx++;
		add(soundToggle, gbc);
		gbc.gridx--;
		gbc.gridy++;
		add(transLabel("Fullscreen",20), gbc);
		fsToggle = new MetalButton(("true".equals(SettingsManager.get("fullscreen")) ? "On" : "Off"));
		fsToggle.addActionListener(this);
		gbc.gridx++;
		add(fsToggle, gbc);
		gbc.gridx--;
		gbc.gridy++;
		add(transLabel("Beginner",20),gbc);
		beginToggle = new MetalButton(("true".equals(SettingsManager.get("beginner")) ? "On" : "Off"));
		beginToggle.addActionListener(this);
		gbc.gridx++;
		add(beginToggle, gbc);
		gbc.gridx--;
	}
	
	public JLabel transLabel(String s, int size) {
		Font font=DataManager.loadFont("text.ttf");
		JLabel lab = new JLabel(s);
		lab.setFont(font.deriveFont((float)size));
		lab.setForeground(new Color(255,255,255,80));
		return lab;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(back)) {
			Window.current.toPane(Window.current.menuPane);
		} else if(e.getSource().equals(soundToggle)) {
			SettingsManager.put("sound", new Boolean(soundToggle.getText().equals("Off")).toString());
			soundToggle.setText(soundToggle.getText().equals("Off") ? "On" : "Off");
			//Sound Subsystem change-state work here?
		} else if(e.getSource().equals(fsToggle)) {
			SettingsManager.put("fullscreen", new Boolean(fsToggle.getText().equals("Off")).toString());
			fsToggle.setText(fsToggle.getText().equals("Off") ? "On" : "Off");
			Window.current.setFullscreen(SettingsManager.get("fullscreen").equals("true"));
		} else if(e.getSource().equals(beginToggle)) {
			SettingsManager.put("beginner", new Boolean(beginToggle.getText().equals("Off")).toString());
			beginToggle.setText(beginToggle.getText().equals("Off") ? "On" : "Off");
		}
	}
}
