package gravitable;

import game.CollisionEvent;
import game.CollisionListener;
import game.GravityPane;
import game.Velocity;

import java.awt.Color;


public class ResetGravitable extends SimpleGravitable implements CollisionListener{
	double xOrig;
	double yOrig;
	Velocity vOrig;
	GravityPane pane;
	
	public void setPane(GravityPane gp) {
		pane = gp;
		pane.addCollisionListener(this);
	}
	
	public ResetGravitable(int mass, double x, double y, Velocity v) {
		super(mass, x, y, v);
		xOrig=x;
		yOrig=y;
		vOrig=v;
	}

	public void collisionOccurred(CollisionEvent ce) {
		if(ce.g1.equals(this) || ce.g2.equals(this)) {
			xLoc=xOrig;
			yLoc=yOrig;
			setVelocity(vOrig);
		}
	}
	
}
