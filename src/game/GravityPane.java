package game;

import gravitable.Gravitable;
import gravitable.Launcher;
import gravitable.PlayerGravitable;
import gravitable.SimpleGravitable;
import hook.GravMouse;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JPanel;


public class GravityPane extends JPanel implements Runnable{
	public boolean gravityOn=false;
	public Image image;
	public ArrayList<Gravitable> objects;
	public ArrayList<Wall> walls;
	ArrayList<CollisionListener> listeners;
	ArrayList<Point> trace;
	Point[] lastTrace;
	protected Gravitable[] paintable;
	protected Wall[] paintwalls;
	Gravitable add;
	public Gravitable remove;
	Thread gravThread;
	
	boolean win=false;
	long wincount;
	
	boolean traceplayer=false;
	boolean gravMouse = false;
	boolean followCam = false; int transX, transY = 0;
	
	int tracecount=50;
	long paintstart;
	int paintcount=0;
	int fps=0;
	long cyclestart;
	int cyclecount=0;
	int cps=0;
	public GamePane game;
	public void addCollisionListener(CollisionListener cl) {
		listeners.add(cl);
	}
	public void removeCollisionListener(CollisionListener cl) {
		listeners.remove(cl);
	}
	public void setBackground(Image i){
		image = i;
	}
	public void add(Gravitable g) {
		objects.add(g);paintable = objects.toArray(new Gravitable[0]);
		g.setPane(this);
	}
	public void add(Wall w) {
		walls.add(w);
	}
	public void fadeWin() {
		wincount = AnimTimer.startTmr();
		win = true;
	}
	public GravityPane(GamePane gp) {
		traceplayer = Boolean.parseBoolean(SettingsManager.get("tracePlayer"));
		gravMouse = Boolean.parseBoolean(SettingsManager.get("gravMouse"));
		followCam = Boolean.parseBoolean(SettingsManager.get("followCam"));
		trace = new ArrayList<Point>();
		game = gp;
		setBackground(Color.black);
		objects = new ArrayList<Gravitable>();
		walls = new ArrayList<Wall>();
		listeners = new ArrayList<CollisionListener>();
		this.setPreferredSize(new Dimension(800,600));
		this.setMinimumSize(new Dimension(800,600));
		this.setMaximumSize(new Dimension(800,600));
		paintable = objects.toArray(new Gravitable[0]);
		paintwalls = walls.toArray(new Wall[0]);
		if(gravMouse){
			new GravMouse(this);
		}
	}
	public void paint(Graphics g) {
		paintcount++;
		if(System.currentTimeMillis() - paintstart > 1000)
		{
			fps = paintcount;
			paintcount = 0;
			paintstart = System.currentTimeMillis();
		}
		if(paintable  == null)
			paintable = objects.toArray(new Gravitable[0]);
		if(followCam && transX != 0 && transY != 0) {
			g.translate(transX+400, transY+290);
		}
		if(image == null) {
			g.setColor(this.getBackground());
			g.fillRect(0, 0, getWidth(), getHeight());
		} else {
			g.drawImage(image,0,0,null);
		}
		try {
		for(Gravitable g2 : paintable) {
			g2.paint(g);
		}
		for(Wall w : paintwalls)
			w.paint(g);
		if(traceplayer) {
			g.setColor(Color.orange);
			if(lastTrace != null && lastTrace.length > 0) {
				Point last = lastTrace[0];
				for(Point p : lastTrace) {
					g.drawLine(last.x, last.y, p.x, p.y);
					last = p;
				}
			}
			g.setColor(Color.red);
			if(trace.size() > 0) {
				Point last = trace.get(0);
				for(int i=0;i<trace.size();i++) {
					Point p = trace.get(i);
					g.drawLine(last.x, last.y, p.x, p.y);
					last = p;
				}
			}
		}
		} catch(Exception e) {
			e.printStackTrace();
		}
		//System.out.println("Painting "+objects.size()+" Objects");
		if(SettingsManager.get("debug").equals("true")) {
			g.setColor(Color.green);
			g.drawString("FPS: "+fps, 10, 10);
			g.drawString("CPS: "+cps, 10, 40);
		}
		if(win) {
			int count = AnimTimer.countSince(wincount, 5);
			if(count>350){
				Window.current.toPane(new WinPane(game));
				gravityOn = false;
			}
			if(count>255) count = 255;
			g.setColor(new Color(0,0,0,count));
			g.fillRect(0, 0, getWidth(), getHeight());
		}
	}
	boolean startPaint = true;
	Runnable paintThread = new Runnable() {
		int fps=80;
		int sleepms = (int)(1000/(double)fps);
		public void run() {
			if(!startPaint) return;
			startPaint = false;
			while (gravityOn) {
				repaint();
				try {
					Thread.sleep(sleepms);
				} catch (InterruptedException e) {}
			}
			startPaint = true;
		}
	};
	public void run() {
		gravityOn = true;
		new Thread(paintThread).start();
		while (gravityOn) {
			cyclecount++;
			if(System.currentTimeMillis() - cyclestart > 1000)
			{
				cps = cyclecount;
				cyclecount = 0;
				cyclestart = System.currentTimeMillis();
			}
			try{
				if(remove != null) {
					objects.remove(remove);
					remove=null;
				}
				if(add != null) {
					objects.add(add);
					add=null;
				}
			} catch(Exception e) {}
			long startTime = System.currentTimeMillis();
			for(Gravitable g : objects.toArray(new Gravitable[0])) {
				if(!g.canMove()) continue;
				for(Gravitable g2 : objects.toArray(new Gravitable[0])) {
					if(g2!=g) {
						
						try {
							g.applyForce(g2.forceOn(g));
						} catch (CollisionException e) {
							if(!(e.g1 instanceof Launcher) && !(e.g2 instanceof Launcher))
							onCollision(e.createEvent());
						}
					}
				}
				if(g.getLocation().x<0) {
					g.setLocation(0, g.getY());
					g.setVelocity(Velocity.fromXY(g.getVelocity().getX()*-1, g.getVelocity().getY()));
					onCollision(new CollisionEvent(g,g));
				}
				if (g.getLocation().x > Math.max(getWidth(),760)) {
					g.setLocation(Math.max(getWidth(),760), g.getY());
					g.setVelocity(Velocity.fromXY(g.getVelocity().getX()*-1, g.getVelocity().getY()));
					onCollision(new CollisionEvent(g,g));
				}
				if(g.getLocation().y<0) {
					g.setLocation(g.getX(), 0);
					g.setVelocity(Velocity.fromXY(g.getVelocity().getX(), g.getVelocity().getY()*-1));
					onCollision(new CollisionEvent(g,g));
				}
				if (g.getLocation().y > Math.max(getHeight(),550)) {
					g.setLocation(g.getX(),Math.max(getHeight(),550));
					g.setVelocity(Velocity.fromXY(g.getVelocity().getX(), g.getVelocity().getY()*-1));
					onCollision(new CollisionEvent(g,g));
				}
			}
			for(Gravitable g : objects.toArray(new Gravitable[0])) {
				g.applyVelocity();
				for(Wall w : walls.toArray(new Wall[0])) {
					if(w.contains(g.getX(), g.getY())) {
						w.impact(g);
						onCollision(new CollisionEvent(g,g));
					}
				}
				if((g instanceof PlayerGravitable) && traceplayer) {
					if(tracecount > 10) {
						trace.add(g.getLocation());
						tracecount=0;
					} else {
						tracecount++;
					}
					
				} 
				if((g instanceof PlayerGravitable) && followCam) {
					transX = -1*(int)g.getX();
					transY = -1*(int)g.getY();
				}
					
			}
			paintable = objects.toArray(new Gravitable[0]);
			paintwalls = walls.toArray(new Wall[0]);
			try {
				int wait = (int) (System.currentTimeMillis()-startTime);
				if(wait<5)
					Thread.sleep(5-wait);
			} catch (InterruptedException e) {}
		}
	}
	public void onCollision(CollisionEvent e) {
		for (CollisionListener c : listeners.toArray(new CollisionListener[0]))
			c.collisionOccurred(e);
	}
	public void clear() {
		gravityOn = false;
		if(gravThread != null) gravThread.stop();
		objects = new ArrayList<Gravitable>();
		listeners = new ArrayList<CollisionListener>();
		walls = new ArrayList<Wall>();
	}
	public Image createGravImage() {
		BufferedImage i = new BufferedImage(800, 550, BufferedImage.TYPE_INT_RGB);
		Graphics g = i.getGraphics();
		Gravitable test = new SimpleGravitable(5, 0, 0, new Velocity(0,0));
		for(int j=0;j<i.getWidth();j++) {
			for(int k=0;k<i.getHeight();k++) {
				test.setLocation(j, k);
				Force net = new Force(0,0);
				for(Gravitable g2 : objects.toArray(new Gravitable[0])) {
					try {
						net = net.addForce(g2.forceOn(test));
					} catch (CollisionException e) {}
				}
				g.setColor(new Color((int) net.getMag(),0,0));
				g.drawRect(j, k, 1, 1);
			}
		}
		return i;
	}
}
