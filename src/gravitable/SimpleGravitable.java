package gravitable;

import game.Force;
import game.Velocity;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RadialGradientPaint;
import java.awt.RenderingHints;
import java.awt.MultipleGradientPaint.CycleMethod;
import java.awt.geom.Ellipse2D;


public class SimpleGravitable extends AbstractGravitable{
	Force last = new Force(0,0);
	boolean canMove=true;
	public void applyVelocity() {
		super.applyVelocity();
	}
	public boolean canMove() {
		return canMove;
	}
	public void setMove(boolean b) {
		canMove=b;
	}
	public SimpleGravitable(int mass, double x, double y, Velocity v) {
		setMass(mass);
		xLoc = x;
		yLoc = y;
		velocity = v;
	}
	public void applyForce(Force f) {
		super.applyForce(f);
		last=f;
	}
	public void paint(Graphics g) {
		Graphics2D g2d = (Graphics2D)g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
		Point center = getLocation();
        paintOrb(g2d, color, center, getRadius());
	}
	private void paintOrb(Graphics2D g, Color orbColor, Point center, double radius) {
        Graphics2D g2d = (Graphics2D)g.create();
        double scale = radius / 8;
        g2d.scale(scale, scale);
        g2d.translate(center.x / scale - 8, center.y / scale - 8);
        Ellipse2D orb = new Ellipse2D.Float(0, 0, 16, 16);
        g2d.setPaint(new GradientPaint(0, 0, orbColor, 5, 12,
                orbColor.darker().darker().darker().darker(), false));
        g2d.fill(orb);
        float[] dist = {0.0f, 0.9f};
        Color[] colors ={new Color(255, 255, 255, 150), new Color(255, 255, 255, 0)};
        RadialGradientPaint gp = new RadialGradientPaint(8, 4, 8, dist, colors,
          CycleMethod.NO_CYCLE);
        g2d.setPaint(gp);
        g2d.fill(orb);
    }
}
