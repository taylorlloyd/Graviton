package game;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class LevelButton extends JPanel implements MouseListener, MouseMotionListener {
	GameProperties gp;
	LevelPack pack;
	static final Color NORMAL = new Color(180,180,180);
	static final Color MOUSEOVER = new Color(255,255,255);
	Color current = NORMAL;
	JLabel name;
	JLabel time;
	//Optimizations required - load font once, load icons once (Icons in Image cache?)
	public LevelButton(String level) {
		super();
		gp = GameProperties.parseMeta(level);
		setBackground(new Color(0,0,0,0));
		setLayout(new GridBagLayout());
		Font font = DataManager.loadFont("text.ttf").deriveFont((float)20);
		//Name, Time, Not Done/Done/Success/locked icon
		name = new JLabel(gp.name);
		name.setFont(font.deriveFont((float)20));
		name.setForeground(current);
		time = new JLabel("No Time");
		time.setForeground(current);
		time.setFont(font.deriveFont((float)20));
		if(GameState.isCompleted(gp.pack, gp.index))
			time.setText(timeFrom(GameState.getTime(gp.pack, gp.index)));
		 ImageIcon i=null;
		 if(!GameState.isPlayable(gp.pack, gp.index))
			 i = new ImageIcon(DataManager.loadImage("locked.png"));
		 else if(!GameState.isCompleted(gp.pack, gp.index))
			 i = new ImageIcon(DataManager.loadImage("none.png"));
		 else if(!GameState.isSuccess(gp.pack, gp.index))
			 i = new ImageIcon(DataManager.loadImage("time.png"));
		 else 
			 i = new ImageIcon(DataManager.loadImage("success.png"));
		 JLabel icon = new JLabel(i);
		 //They all need to be added to the pane here
		 GridBagConstraints gbc = new GridBagConstraints();
		 gbc.fill=gbc.HORIZONTAL;
		 gbc.gridx=0;
		 gbc.gridy=0;
		 gbc.weightx=1;
		 add(name, gbc);
		 gbc.gridx++;
		 add(time, gbc);
		 gbc.gridx++;
		 gbc.weightx=0;
		 gbc.fill=gbc.NONE;
		 add(icon, gbc);
		 addMouseListener(this);
		 addMouseMotionListener(this);
		
	}
	public String timeFrom(int seconds) {
		int hours = seconds/3600;
		int min = (seconds-(3600*hours))/60;
		int secs = seconds -((min*60) + (hours*3600));
		if(hours>0)return hours+":"+(min<10 ? "0" : "")+min+":"+(secs<10 ? "0" : "")+secs;
		return min+":"+(secs<10 ? "0" : "")+secs;
	}
	public void mouseClicked(MouseEvent arg0) {
		if(GameState.isPlayable(gp.pack, gp.index)) {
			Window.current.toPane(new BGPane());
			Window.current.toGame(gp.loadFully());
		}
		
	}
	public void mouseEntered(MouseEvent arg0) {
		if(GameState.isPlayable(gp.pack, gp.index))
			current = MOUSEOVER;
		name.setForeground(current);
		time.setForeground(current);
		repaint();
		
	}
	public void mouseExited(MouseEvent arg0) {
		current = NORMAL;
		name.setForeground(current);
		time.setForeground(current);
		repaint();
		
	}
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	public void mouseMoved(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
