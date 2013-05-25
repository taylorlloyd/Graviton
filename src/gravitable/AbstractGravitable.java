package gravitable;

import game.CollisionException;
import game.Force;
import game.GravityPane;
import game.Velocity;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;


public abstract class AbstractGravitable implements Gravitable{
	Velocity velocity;
	int mass;
	double xLoc;
	double yLoc;
	Color color = new Color(240,10,10);
	public void applyVelocity() {
		if(!canMove())return;
		xLoc += velocity.getX();
		yLoc += velocity.getY();
	}
	public double getX() {
		return xLoc;
	}
	public double getY() {
		return yLoc;
	}
	public double getRadius() {
		return Math.sqrt(3.14*(double)getMass())+4;
	}
	
	public void applyForce(Force f) {
		if(mass==0)return;
		if(f.getMag() == 0)return;
		velocity =velocity.addVelocity(new Velocity(f.getMag()/mass, f.getAngle()));
	}

	public boolean canMove() {
		return true;
	}

	public double distanceFrom(Gravitable g) {
		return g.getLocation().distance(xLoc, yLoc);
	}

	public Force forceOn(Gravitable g) throws CollisionException {
		double d = distanceFrom(g);
		double mag = grav*g.getMass()*getMass()/(d*d);
		double angle = Math.atan2((yLoc-g.getLocation().getY()),(xLoc-g.getLocation().getX()));
		if(d<(getRadius()) + g.getRadius()) {//impact?
			throw new CollisionException(this,g);
		}
		return new Force(mag, angle);
	}

	public Point getLocation() {
		return new Point((int)xLoc,(int)yLoc);
	}

	public int getMass() {
		return mass;
	}
	public void setMass(int mass) {
		this.mass = mass;
	}

	public Velocity getVelocity() {
		return velocity;
	}

	public void setLocation(double x, double y) {	
		this.xLoc = x;
		this.yLoc = y;
	}

	public void setVelocity(Velocity v) {
		velocity = v;
	}

	public void paint(Graphics g) {
		g.setColor(color);
		g.fillOval((int)xLoc-5, (int)yLoc-5, 10, 10);
	}

	public void setPane(GravityPane gp) {
		//Don't care
	}
	public Gravitable copy() {
		try {
			AbstractGravitable g=this.getClass().newInstance();
			g.velocity = velocity;
			g.color = color;
			g.mass = mass;
			g.xLoc = xLoc;
			g.yLoc = yLoc;
			return g;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return this; //It's more important to get an instance than to be an independant one.
	}
	@Override
	public Color getColor() {
		return color;
	}
	@Override
	public void setColor(Color c) {
		color = c;
		
	}
	

}
