package game;
import java.awt.Color;
import java.awt.DisplayMode;
import java.awt.Graphics;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

import javax.swing.*;


public class Window extends JFrame{
	public static Window current;
	public JPanel levelPane;
	public JPanel menuPane;
	public JPanel lastPane;
	private JPanel next;
	private DisplayMode originalDisplay;
	int opacity=0;
	public Window() {
		super("Graviton");
		originalDisplay = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDisplayMode();
		menuPane=new MenuPane();
		levelPane=new LevelPane();
		current=this;
		GameState.loadData();
		setContentPane(menuPane);
		setSize(800,600);
		setResizable(false);
		this.setDefaultCloseOperation(this.EXIT_ON_CLOSE);
		setFullscreen(SettingsManager.get("fullscreen").equals("true"));
		setVisible(true);
	}
	/*public Window(String leveltxt) {
		super("Graviton");
		//levelPane=new LevelPane();
		current=this;
		setContentPane(new GamePane(GameProperties.fromString(readFile(new File(leveltxt)))));
		setSize(800,600);
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}*/
	public void toPane(JPanel content) {
		lastPane = (JPanel) getContentPane();
		setContentPane(content);
		validate();
	}
	public void toGame(GameProperties level) {
		toPane(new GamePane(level));
	}
	public String readFile(File f) {
		String value="";
		try{
			BufferedReader br = new BufferedReader(new FileReader(f));
			String s;
			value=br.readLine();
			while((s=br.readLine()) != null) {
				value+="\n"+s;
			}
		} catch (IOException e) {}
		return value;
	}
	public void toLastPane() {
		toPane(lastPane);
	}
	public void setFullscreen(boolean fullscreen) {
		if(fullscreen) {
			setVisible(false);
			dispose();
			setUndecorated(true);
			setVisible(true);
			setLocation(0, 0);
			GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
			gd.setFullScreenWindow(this);
			DisplayMode mode = new DisplayMode(800, 600, 32, DisplayMode.REFRESH_RATE_UNKNOWN);
			try{gd.setDisplayMode(mode);}catch(Exception e) {}
			validate();
			repaint();
		} else {
			setVisible(false);
			dispose();
			setUndecorated(false);
			setVisible(true);
			setLocation(0, 0);
			GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
			gd.setFullScreenWindow(null);
			try{gd.setDisplayMode(originalDisplay);}catch(Exception e) {}
			validate();
			repaint();
			repaint();
			repaint();
		}
	}
}
