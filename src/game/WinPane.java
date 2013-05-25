package game;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class WinPane extends JPanel implements ActionListener{
	MetalButton tryAgain;
	MetalButton nextLevel;
	MetalButton levelList;
	MetalButton mainMenu;
	GamePane game;
	public WinPane(GamePane game) {
		this.game = game;
		setBackground(Color.black);
		setLayout(new GridBagLayout());
		tryAgain = new MetalButton("Try Again");
		tryAgain.addActionListener(this);
		nextLevel = new MetalButton("Next Level");
		nextLevel.addActionListener(this);
		levelList = new MetalButton("Level List");
		levelList.addActionListener(this);
		mainMenu = new MetalButton("Main Menu");
		mainMenu.addActionListener(this);
		add(transLabel("Win! - "+game.level.pack+" "+(game.level.index+1)+"/"+LevelLoader.getInstance().levelpacks.get(game.level.pack).getLevelCount(),30), gbc(0,0,2,1,gbc.NONE, 0, 1));
		int ycount=1;
		add(tryAgain, gbc(0,ycount,1,1,gbc.NONE, 1, 0.2));
		ycount++;
		if(game.level.index+1 < LevelLoader.getInstance().levelpacks.get(game.level.pack).getLevelCount()) {
		add(nextLevel, gbc(0,ycount,1,1,gbc.NONE, 1, 0.2));
		ycount++;
		}
		add(levelList, gbc(0,ycount,1,1,gbc.NONE, 1, 0.2));
		ycount++;
		add(mainMenu, gbc(0,ycount,1,1,gbc.NONE, 1, 0.2));
		add(transLabel("Time: "+game.topBar.tt.timer.getText(),20), gbc(1,1,1,1,gbc.NONE,1,0));
		add(transLabel("Attempts: "+game.tries, 20),  gbc(1,2,1,1,gbc.NONE,1,0));
		
	}
	GridBagConstraints gbc;
	public GridBagConstraints gbc(int gridx, int gridy, int gridwidth, int gridheight, int fill, double weightx, double weighty) {
		if(gbc == null)
			gbc = new GridBagConstraints();
		gbc.gridx=gridx;
		gbc.gridy=gridy;
		gbc.gridwidth=gridwidth;
		gbc.gridheight=gridheight;
		gbc.fill=fill;
		gbc.weightx=weightx;
		gbc.weighty=weighty;
		return gbc;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(tryAgain)) {
			Window.current.toPane(new BGPane());
			Window.current.toGame(GameProperties.fromString(game.level.original));
		} else if(e.getSource().equals(nextLevel)) {
			Window.current.toPane(new BGPane());
			Window.current.toGame(GameProperties.fromString(LevelLoader.getInstance().levelpacks.get(game.level.pack).getLevel(game.level.index+1)));
		} else if(e.getSource().equals(levelList)) {
			Window.current.toPane(new LevelPackPane(LevelLoader.getInstance().levelpacks.get(game.level.pack)));
		} else if(e.getSource().equals(mainMenu)) {
			Window.current.toPane(Window.current.levelPane);
		}
	}
	public JLabel transLabel(String s, int size) {
		Font font=DataManager.loadFont("text.ttf");
		JLabel lab = new JLabel(s);
		lab.setFont(font.deriveFont((float)size));
		lab.setForeground(new Color(255,255,255,80));
		return lab;
	}
}
