package gravitable;

import game.CollisionException;
import game.Force;
import game.GravityPane;
import game.Velocity;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;


public interface Gravitable {
	public static double grav = 2;
	public int getMass();
	public void setMass(int mass);
	public Point getLocation();
	public double getX();
	public double getY();
	public void setVelocity(Velocity v);
	public void applyForce(Force f);
	public Velocity getVelocity();
	public void setLocation(double x, double y);
	public double distanceFrom(Gravitable g);
	public double getRadius();
	public Force forceOn(Gravitable g)throws CollisionException;
	public void applyVelocity();
	public void paint(Graphics g);
	public void setPane(GravityPane gp);
	public Gravitable copy();
	public Color getColor();
	public void setColor(Color c);
	public boolean canMove();
	public void setMove(boolean b);
}
