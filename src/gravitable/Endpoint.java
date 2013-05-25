package gravitable;

import game.AnimTimer;
import game.CollisionEvent;
import game.DataManager;
import game.Velocity;

import java.awt.*;
import java.awt.MultipleGradientPaint.CycleMethod;
import java.awt.geom.Ellipse2D;
import java.util.Random;


public class Endpoint extends ResetGravitable {
	static Image closed;
	static Image open;
	float color = 0;
	long rotTmr;
	long spinUpTmr;
	public Endpoint(double x, double y, Velocity v) {
		super(20, x, y, v);
		if( closed == null) {
			closed = DataManager.loadImage("end_closed.png");
	    }
		if( open == null) {
			open = DataManager.loadImage("end_open.png");
	    }
		//Endpoints don't move by default, but can
		setMove(false);
	}
	public void collisionOccurred(CollisionEvent ce) {
		//if we hit a player, end the level
		if((ce.g1 instanceof PlayerGravitable || ce.g2 instanceof PlayerGravitable) && (ce.g1 instanceof Endpoint || ce.g2 instanceof Endpoint)){
			if(itemsRemaining())
				return;
			PlayerGravitable pg;
			if(ce.g1 instanceof PlayerGravitable) pg = (PlayerGravitable) ce.g1;
			else pg = (PlayerGravitable) ce.g2;
			if(pg.getState() == pg.NORMAL) {
				pg.warpOut();
			}
		}
	}
	public boolean itemsRemaining() {
		boolean items = false;
		try{
		for(Gravitable g : pane.objects)
			if(g instanceof CollectorItem)
				return true;
		} catch(Exception e) {}
		return false;
	}
	public void paint(Graphics g) {
		Graphics2D g2 = (Graphics2D) g.create();
		g2.translate(getX()-28, getY()-28);
		if(itemsRemaining())
			g2.drawImage(closed, 0, 0, null);
		else {
			g2.rotate((double)(AnimTimer.countSince(rotTmr, 4))/(100), 28, 28);
			g2.drawImage(open, 0, 0, null);
			Graphics2D g3 = (Graphics2D) g2.create();
			g3.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
	                RenderingHints.VALUE_ANTIALIAS_ON);
			g3.translate(27,27);
			g3.setColor(new Color(0,160,240));
			g3.setStroke(new BasicStroke(2));
			drawJagged(g3,-17,0,0,0);
			drawJagged(g3,17,0,0,0);
			drawJagged(g3,0,-17,0,0);
			drawJagged(g3,0,17,0,0);
			
		    float[] dist = {0.1f, 1.0f};
		    Color[] colors ={new Color(0,160,240,150), new Color(255, 255, 255, 0)};
		    RadialGradientPaint gp = new RadialGradientPaint(28, 28, 18, dist, colors,
		          CycleMethod.NO_CYCLE);
		    g2.setPaint(gp);
		    g2.fillOval(0, 0, 56, 56);
		    color += 0.002;
		}
	}
	public void drawJagged(Graphics2D g, int x1, int y1, int x2, int y2) {
		Random r = new Random();
		int midx1 =(x1+x2)/3;
		int midx2 = (x1+x2)*2/3;
		int midy1 = (y1+y2)/3;
		int midy2 = (y1+y2)*2/3;
		midx1 += r.nextInt(10)-5;
		midx2 += r.nextInt(10)-5;
		midy1 += r.nextInt(10)-5;
		midy2 += r.nextInt(10)-5;
		g.drawLine(x1,y1,midx1,midy1);
		g.drawLine(midx1, midy1, midx2, midy2);
		g.drawLine(midx2, midy2, x2, y2);
	}
	

}
