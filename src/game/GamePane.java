package game;

import gravitable.Endpoint;
import gravitable.Gravitable;
import gravitable.Launcher;
import gravitable.PlayerGravitable;
import hook.PlayerHack;

import java.awt.*;

import javax.swing.*;


public class GamePane extends JPanel{
	GravityPane grav;
	GameProperties level;
	TopBar topBar;
	String curLevel;
	long starttime;
	boolean win=false;
	int tries = 1;
	public GamePane(GameProperties gp) {
		curLevel = gp.original;
		this.level = gp;
		this.setLayout(new GridBagLayout());
		grav = new GravityPane(this);
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx=0;
		gbc.gridy=0;
		gbc.weightx=1;
		gbc.weighty=0;
		gbc.fill=gbc.HORIZONTAL;
		topBar = new TopBar(gp.name,this);
		add(topBar,gbc);
		gbc.weightx=1;
		gbc.weighty=1;
		gbc.gridy=1;
		gbc.fill=gbc.BOTH;
		this.add(grav,gbc);
		loadProperties(grav, gp);
		validate();
		starttime = System.currentTimeMillis();
	}
	private void loadProperties(GravityPane grav, GameProperties level) {
		//set background image
		for(Gravitable g : level.gravitables)
			grav.add(g);
		for(Gravitable g : level.items)
			grav.add(g);
		for(Wall w : level.walls)
			grav.add(w);
		if(level.background != null) {
			grav.image = level.background;
		}
		grav.add(new Endpoint(level.end.x, level.end.y, new Velocity(0,0)));
		grav.add(new Launcher(level.start.x, level.start.y));
		if(SettingsManager.get("superplayer").equals("true"))
			{grav.add(new PlayerHack());System.out.println("Player Hack injected");}
		grav.gravThread = new Thread(grav);
		grav.gravThread.start();
	}
	public void reloadLevel() {
		grav.clear();
		loadProperties(grav, GameProperties.fromString(curLevel));
		starttime = System.currentTimeMillis();
		topBar.reset();
		resetPlayer();
		tries=1;
		topBar.updateTries();
	}
	public void resetPlayer() {
		Launcher launch=null;
		for(Gravitable g : grav.objects)
			if(g instanceof Launcher) {
				((Launcher) g).resetLaunch();
				launch = (Launcher)g;
				launch.pg = new PlayerGravitable(launch.getX(),launch.getY());
			}
		for(Gravitable g : grav.objects) {
			if(g instanceof PlayerGravitable) {
				grav.remove=g;
				
			}
		}
		if(grav.traceplayer) {
			grav.lastTrace = grav.trace.toArray(new Point[0]);
			grav.trace.clear();
		}
		tries++;
		topBar.updateTries();
	}
	public void winLevel() {
		if(win)
			return;
		win=true;
		int timetaken = (int) ((System.currentTimeMillis()-starttime)/1000);
		GameState.setCompleted(level.pack, level.index, true);
		if((!level.timed) || timetaken<level.timetobeat)
			GameState.setSuccess(level.pack, level.index, true);
		//Only store this if it beats the previous score
		if(timetaken<GameState.getTime(level.pack, level.index) || GameState.getTime(level.pack, level.index)==0)
			GameState.setTime(level.pack, level.index, timetaken);
		//draw win over the screen and repaint()
		repaint();
		topBar.tt.run = false;
		grav.fadeWin();
		//Window.current.toPane(Window.current.levelPane);
	}
	public void paint(Graphics g) {
		super.paint(g);
		if(win) {
			//Draw win code here
		}
	}
	//Only a button can actually unload the GamePane and return to the level screen
}

