package game;

public class Force {
	private double magnitude;
	private double angle;
	public Force(double magnitude, double angle) {
		this.magnitude = magnitude;
		this.angle = angle;
	}
	public static Force fromXY(double x, double y) {
		return new Force(Math.sqrt((x*x)+(y*y)),Math.atan2(y, x));
	}
	public Force addForce(Force f) {
		return fromXY(getX()+f.getX(), getY()+f.getY());
	}
	public Force subtractForce(Force subtractBy) {
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
