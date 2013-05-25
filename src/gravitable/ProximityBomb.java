package gravitable;

import game.AnimTimer;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RadialGradientPaint;
import java.awt.RenderingHints;
import java.awt.MultipleGradientPaint.CycleMethod;
import java.awt.geom.Ellipse2D;

public class ProximityBomb extends Bomb {
	public double triggerDist;
	private long pingtmr;
	public ProximityBomb(double x, double y, double triggerDist, int particles) {
		super(x, y, particles);
		this.triggerDist = triggerDist;
		pingtmr = AnimTimer.startTmr();
	}

	@Override
	public void paintBomb(Graphics2D g) {
		int count = AnimTimer.countSince(pingtmr, 15);
		if(count>triggerDist){
			pingtmr = AnimTimer.startTmr();
			count = (int)triggerDist;
		}
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
		Graphics2D pg = (Graphics2D) g.create();
		pg.setStroke(new BasicStroke(6));
		pg.setColor(new Color(255,0,0,(int)(255-240*(count/triggerDist))));
		pg.drawOval(-count, -count, 2*count, 2*count);
        paintOrb(g, Color.gray, new Point(0, 0), 20);
        if(count<20)
        	paintDot(g, Color.red, new Point(0, 0), 5);
        else
        	paintDot(g, Color.red.darker().darker(), new Point(0, 0), 5);
	}

	@Override
	public boolean triggers(Gravitable g) {
		if(triggered) return false;
		if(g instanceof PlayerGravitable) {
			if(g.distanceFrom(this) <= triggerDist)return true;
		}
		if(g instanceof BombFragment) {
			if(((BombFragment)g).triggered && g.distanceFrom(this) <= triggerDist)
			return true;
		}
		return false;
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
	private void paintDot(Graphics2D g, Color orbColor, Point center, double radius) {
        Graphics2D g2d = (Graphics2D)g.create();
        double scale = radius / 8;
        g2d.scale(scale, scale);
        g2d.translate(center.x / scale - 8, center.y / scale - 8);
        Ellipse2D orb = new Ellipse2D.Float(0, 0, 16, 16);
        g2d.setPaint(new GradientPaint(0, 0, orbColor, 5, 12,
                orbColor.darker().darker(), false));
        g2d.fill(orb);
    }
}
