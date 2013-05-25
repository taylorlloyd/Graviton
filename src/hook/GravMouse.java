package hook;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import gravitable.Gravitable;
import game.CollisionException;
import game.Force;
import game.GravityPane;
import game.Velocity;

public class GravMouse implements Gravitable, MouseListener, MouseMotionListener{
	double x;
	double y;
	boolean pressed = false;
	GravityPane pane;
	public GravMouse(GravityPane pane) {
		this.pane = pane;
		pane.addMouseListener(this);
		pane.addMouseMotionListener(this);
	}

	@Override
	public void applyForce(Force f) {}
	public void applyVelocity() {}
	public boolean canMove() {return false;}
	public Gravitable copy() {return this;}
	public double distanceFrom(Gravitable g) {
		return g.getLocation().distance(x, y);
	}
	public Force forceOn(Gravitable g) throws CollisionException {
		if(!pressed) return new Force(0,0);
		double d = distanceFrom(g);
		double mag = grav*g.getMass()*getMass()/(d*d);
		double angle = Math.atan2((y-g.getLocation().getY()),(x-g.getLocation().getX()));
		if(d<(getRadius()) + g.getRadius()) {//impact?
			return new Force(0,0);
		}
		return new Force(mag, angle);
	}
	public Color getColor() {return Color.white;}
	public Point getLocation() {return new Point((int)x,(int)y);}
	public int getMass() {
		if(!pressed) return 0;
		return 10;
	}
	public double getRadius() {return 0;}
	public Velocity getVelocity() {return new Velocity(0,0);}
	public double getX() {return x;}
	public double getY() {return y;}

	public void paint(Graphics g) {
		// TODO Auto-generated method stub
		
	}

	public void setColor(Color c) {}
	public void setLocation(double x, double y) {}
	public void setMass(int mass) {}
	public void setMove(boolean b) {}
	public void setPane(GravityPane gp) {}
	public void setVelocity(Velocity v) {}

	@Override
	public void mouseClicked(MouseEvent e) {}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {pressed = false;}

	@Override
	public void mousePressed(MouseEvent e) {
		if(!pane.objects.contains(this)) {pane.objects.add(this);System.out.println("Gravity Mouse Enabled");}
		pressed=true;
		x=e.getX();
		y=e.getY();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		pressed=false;
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		pressed=true;
		x=e.getX();
		y=e.getY();
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		pressed=false;
		x=e.getX();
		y=e.getY();
	}
}
