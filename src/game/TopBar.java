package game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.MediaTracker;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class TopBar extends JPanel implements ActionListener{
		public static Image background;
		private GamePane pane;
		private JLabel timer;
		TimerThread tt;
		boolean countdown=false;
		long timetobeat=0;
		private JLabel tries;
		public TopBar(String level, GamePane gp) {
			pane=gp;
			setOpaque(true);
			setLayout(new GridBagLayout());
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.gridx=4;
			gbc.gridy=0;
			gbc.anchor = GridBagConstraints.EAST;
			gbc.fill=GridBagConstraints.NONE;
			gbc.insets = new Insets(0,0,0,25);
			MediaTracker mt = new MediaTracker(this);
			JButton menu = new JButton(new ImageIcon(DataManager.loadImage("menu.png")));
			menu.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
			menu.setRolloverIcon(new ImageIcon(DataManager.loadImage("menu_over.png")));
			menu.addActionListener(this);
			menu.setActionCommand("menu");
			menu.setBorderPainted(false);
			mt.addImage(((ImageIcon) menu.getIcon()).getImage(), 0);
			mt.addImage(((ImageIcon) menu.getRolloverIcon()).getImage(), 1);
			add(menu,gbc);
			menu.setBackground(new Color(0,0,0,0));
			gbc.gridx--;
			JButton reset = new JButton(new ImageIcon(DataManager.loadImage("reset.png")));
			reset.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
			reset.setRolloverIcon(new ImageIcon(DataManager.loadImage("reset_over.png")));
			reset.addActionListener(this);
			reset.setActionCommand("reset");
			reset.setBorderPainted(false);
			mt.addImage(((ImageIcon) reset.getIcon()).getImage(), 2);
			mt.addImage(((ImageIcon) reset.getRolloverIcon()).getImage(), 3);
			add(reset,gbc);
			reset.setBackground(new Color(0,0,0,0));
			if(background == null)
				background = DataManager.loadImage("topbar.png");
			mt.addImage(background, 4);
			//Color - If lowerOf(best time/time to beat) is past, then red. else, green.
			//update every second.
			timer = new JLabel();
			tries = new JLabel();
			tries.setText("Attempt: "+pane.tries);
			if(gp.level.timed) {
					timetobeat = gp.starttime;
			}
			tt=new TimerThread(timer,countdown,timetobeat,"Failed");
			gbc.gridx--;
			add(new MetalLabel(timer),gbc);
			gbc.gridx--;
			add(new MetalLabel(tries),gbc);
			try {
				mt.waitForAll();
			} catch (InterruptedException e) {}
			validate();
			setSize(800,40);
			this.setPreferredSize(new Dimension(800,46));
			this.setMinimumSize(new Dimension(800,46));
			this.setMaximumSize(new Dimension(800,46));
		}
		public void paintComponent(Graphics g) {
			g.drawImage(background, 0, 0, null);
		}
		public void actionPerformed(ActionEvent e) {
			if(e.getActionCommand().equals("menu"))
				Window.current.toPane(Window.current.levelPane);
			if(e.getActionCommand().equals("reset"))
				pane.reloadLevel();
		}
		public void reset() {
			tt.stop();
			tt=new TimerThread(timer, countdown, timetobeat, "Failed");
			
		}
		public void updateTries() {
			tries.setText("Attempt: "+pane.tries);
		}
	}
class TimerThread extends Thread {
	JLabel timer;
	boolean run=true;
	boolean countdown;
	long timetobeat;
	long starttime;
	String endString = "Fail";
	public TimerThread(JLabel timer, boolean countdown, long timetobeat, String endString) {
		this.timer = timer;
		this.countdown = countdown;
		this.timetobeat = timetobeat;
		this.starttime = System.currentTimeMillis();
		this.endString = endString;
		start();
	}
	public void run() {
		while(run) {
		long time = System.currentTimeMillis()-starttime;
		if(countdown)
			timer.setText(timeFrom((int) (timetobeat-(time/1000))));
		else
			timer.setText(timeFrom((int) (time/1000)));
		try {
			timer.repaint();
			sleep(1000);
		} catch (InterruptedException e) {}
		}
	}
	public String timeFrom(int seconds) {
		if(seconds<0) return endString;
		int hours = seconds/3600;
		int min = (seconds-(3600*hours))/60;
		int secs = seconds -((min*60) + (hours*3600));
		if(hours>0)return hours+":"+(min<10 ? "0" : "")+min+":"+(secs<10 ? "0" : "")+secs;
		return min+":"+(secs<10 ? "0" : "")+secs;
	}
}
