package gravitable;

import game.Force;
import game.Popper;
import game.Velocity;

import java.awt.Point;

public class PathGravitable extends SimpleGravitable implements Popper {
	public Point start;
	public Point end;
	public double vel;
	boolean forward = true;
	public PathGravitable(Point p1, Point p2, int mass, double vel) {
		super(mass, p1.x, p1.y, new Velocity(0,0));
		setMove(false);
		start = p1;
		end = p2;
		this.vel = vel;
	}
	public void applyForce(Force f) {
		return;
	}
	public void applyVelocity() {
		Point target;
		if(forward)
			target = end;
		else
			target = start;
		double xChange = target.x - xLoc;
		double yChange = target.y - yLoc;
		double dist = Math.sqrt((xChange*xChange)+(yChange*yChange));
		double reg = vel/dist;
		xLoc += xChange*reg;
		yLoc += yChange*reg;
		if(dist < 5)
			forward = !forward;
	}
}
