package game;

import gravitable.Gravitable;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.Random;

public class ForceWall implements Wall {
	Rectangle r;
	int fluxcount = new Random().nextInt(300);
	Point flux=new Point(-100,-100);
	Point impact;
	Color impactColor;
	int impactcount=100;
	Point center;
	public ForceWall(double x1, double y1, double x2, double y2) {
		r = new Rectangle((int)x1,(int)y1,(int)(x2-x1),(int)(y2-y1));
		center = new Point(r.x+(r.width/2), r.y+(r.height/2));
	}
	public boolean contains(double x, double y) {
		return r.contains(x, y);
	}

	public void impact(Gravitable g) {
		g.setVelocity(Velocity.fromXY(-1 * g.getVelocity().getX(), -1 * g.getVelocity().getY()));
		impact = g.getLocation();
		impactcount = 0;
		impactColor = g.getColor();
	}

	public void paint(Graphics g) {
		Graphics g2 = g.create();
		g2.clipRect(r.x, r.y, r.width, r.height);
		fluxcount+= 2;
		if(fluxcount > 300) {
			Random r = new Random();
			flux = new Point(r.nextInt(this.r.width)+this.r.x, r.nextInt(this.r.height)+this.r.y);
			fluxcount = 0;
		}
		if(fluxcount < 100) {
		g2.setColor(new Color(0,240,255,100-fluxcount));
		g2.fillOval(flux.x-fluxcount-fluxcount, flux.y-fluxcount-fluxcount, 4*fluxcount, 4*fluxcount);
		}
		if(impactcount < 100) {
			g2.setColor(new Color(impactColor.getRed(),impactColor.getGreen(),impactColor.getBlue(),100-impactcount));
			g2.fillOval(impact.x-impactcount-impactcount, impact.y-impactcount-impactcount, 4*impactcount, 4*impactcount);
			impactcount += 1;
			}
	}
	public Rectangle getRect() {
		return r;
	}
	@Override
	public void setRect(Rectangle r) {
		this.r = r;
	}

}
