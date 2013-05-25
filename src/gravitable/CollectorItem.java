package gravitable;

import game.AnimTimer;
import game.CollisionEvent;
import game.CollisionListener;
import game.DataManager;
import game.GravityPane;
import game.Velocity;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.util.Random;

import javax.swing.ImageIcon;


public class CollectorItem extends SimpleGravitable implements CollisionListener{
	static Image img;
	long moveTmr;
	long blowTmr;
	GravityPane gp;
	private Object Graphics2D;
	public CollectorItem(double x,double y) {
		super(0, x, y, new Velocity(0,0));
		img = DataManager.loadImage("item.png");
		setMove(false);
	}
	public double getRadius() {
		return 10;
	}
	public void setPane(GravityPane gp) {
		gp.addCollisionListener(this);
		this.gp = gp;
	}
	public void paint(Graphics g) {
		if(blowTmr == 0) {
			Graphics2D g2 = (Graphics2D) g.create();
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
	                RenderingHints.VALUE_ANTIALIAS_ON);
			g.drawImage(img, (int)xLoc-10, (int)yLoc - 10, null);
			g2.translate(xLoc, yLoc);
			g2.setColor(new Color(0,160,240));
			g2.setStroke(new BasicStroke(2));
			drawJagged(g2,0,0,14);
			drawJagged(g2,0,0,14);
			drawJagged(g2,0,0,14);
		}
		else {
			int count = AnimTimer.countSince(blowTmr, 4);
			if(count>120) {
				gp.remove=this;
				return;
			}
			g.setColor(new Color(0,160,250,255-count));
			g.fillOval(-count/2 + (int)xLoc,-count/2+(int)yLoc,count,count);
		}
		
	}
	public void drawJagged(Graphics2D g, int x, int y, int dist) {
		Random r = new Random();
		int x2 = x + r.nextInt(dist*2) - dist;
		int y2 = y + r.nextInt(dist*2) - dist;
		int midx1 =(x+x2)/3;
		int midx2 = (x+x2)*2/3;
		int midy1 = (y+y2)/3;
		int midy2 = (y+y2)*2/3;
		midx1 += r.nextInt(10)-5;
		midx2 += r.nextInt(10)-5;
		midy1 += r.nextInt(10)-5;
		midy2 += r.nextInt(10)-5;
		g.drawLine(x,y,midx1,midy1);
		g.drawLine(midx1, midy1, midx2, midy2);
		g.drawLine(midx2, midy2, x2, y2);
	}
	public void applyVelocity() {
		if(moveTmr != 0) {
			for(Gravitable g : gp.objects)
			{
				if (g instanceof Endpoint) {
					
					double xC = g.getX() - getX();
					double yC = g.getY() - getY();
					xLoc += xC/14;
					yLoc += yC/14;
					if(getLocation().distance(g.getLocation())<8) {
						//blowTmr = AnimTimer.startTmr();
						gp.remove = this;
						moveTmr = 0;
					}
				}
			}
		}
	}
	@Override
	public void collisionOccurred(CollisionEvent ce) {
		if((ce.g1 instanceof PlayerGravitable || ce.g2 instanceof PlayerGravitable) && (ce.g1.equals(this) || ce.g2.equals(this))) {
			if(moveTmr == 0 && blowTmr == 0) {
				moveTmr = AnimTimer.startTmr();
			}
		}
	}
}
