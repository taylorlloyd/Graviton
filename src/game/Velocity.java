package game;

public class Velocity {
	private double magnitude;
	private double angle;
	public Velocity(double magnitude, double angle) {
		this.magnitude = magnitude;
		this.angle = angle;
	}
	public static Velocity fromXY(double x, double y) {
		return new Velocity(Math.sqrt((x*x)+(y*y)),Math.atan2(y,x));
	}
	public Velocity addVelocity(Velocity v) {
		return fromXY(getX()+v.getX(), getY()+v.getY());
	}
	public Velocity subtractVelocity(Velocity subtractBy) {
		return fromXY(getX()-subtractBy.getX(), getY()+subtractBy.getY());
	}
	public double getX() {
		return Math.cos(angle)*magnitude;
	}
	public double getY() {
		return Math.sin(angle)*magnitude;
	}
	public double getMag() {
		return magnitude;
	}
	public double getAngle() {
		return angle;
	}
}
