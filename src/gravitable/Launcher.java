package gravitable;
import game.GravityPane;
import game.SettingsManager;
import game.Velocity;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RadialGradientPaint;
import java.awt.RenderingHints;
import java.awt.MultipleGradientPaint.CycleMethod;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Ellipse2D;
import java.util.Random;


public class Launcher extends SimpleGravitable implements MouseListener, MouseMotionListener{
	public PlayerGravitable pg;
	boolean launched = false;
	boolean pressed = false;
	GravityPane pane;
	
	public void setPane(GravityPane gp) {
		pane = gp;
		gp.addMouseListener(this);
		gp.addMouseMotionListener(this);
	}
	
	public Launcher(double x, double y) {
		super(0,x,y,new Velocity(0,0));
		canMove = false;
		pg = new PlayerGravitable(x,y);
	}
	public void paint(Graphics g) {
		if(launched) return;
		pg.paint(g);
        Graphics2D g2d = (Graphics2D)g.create();
        g2d.translate(xLoc, yLoc);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        // Center Point
        Point top = new Point(0, -23);
        Point bottom = new Point(top.x,top.y+56);
        Point player = pg.getLocation();
        if(launched)
        	player = getLocation();
        double distance = getLocation().distance(player);
        double angle = 0;
        try{
        	angle = Math.atan2((yLoc-player.y),(xLoc-player.x));
        	if(angle != 0)
        	g2d.rotate(angle);
        } catch(Exception e) {}
        g2d.setColor(new Color(255,0,0,100));
        if(SettingsManager.get("beginner").equals("true"))
        	g2d.drawLine((int) -distance, 0, 900, 0);
        g2d.setPaint(new GradientPaint((float) -distance,0,new Color(10,255,10,0),top.x,top.y,new Color(255,10,10)));
        g2d.setStroke(new BasicStroke(10,BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 0));
        g2d.drawLine(top.x, top.y, (int) (-distance), 0);
        g2d.setPaint(new GradientPaint((float) -distance,0,new Color(10,255,10,0),bottom.x,bottom.y,new Color(255,10,10)));
        g2d.drawLine(bottom.x, bottom.y, (int) (-distance), 0);
        Color orbColor = new Color(130, 130, 130);
        paintOrb(g2d, orbColor, top, 15);
        paintOrb(g2d, orbColor, bottom, 15);
    }

    private void paintOrb(Graphics2D g, Color orbColor, Point center, int radius) {
        Graphics2D g2d = (Graphics2D)g.create();
        float scale = radius / 8;
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

	public void mouseClicked(MouseEvent arg0) {}

	public void mouseEntered(MouseEvent arg0) {}

	public void mouseExited(MouseEvent arg0) {}

	public void mousePressed(MouseEvent arg0) {
		if(!launched) {pressed = true;mouseDragged(arg0);}
	}

	public void mouseReleased(MouseEvent arg0) {
		if(launched) return;
		pressed = false;
		launched = true;
		//pane.remove=this;
		//Calculate Velocity of Player
		double mag = getLocation().distance(pg.getLocation())/100;
		double ang = Math.atan2((yLoc-pg.getY()),(xLoc - pg.getX()));
		pg.setVelocity(new Velocity(mag,ang));
		pane.add(pg);
	}

	public void mouseDragged(MouseEvent me) {
		if(launched) return;
		double distance = getLocation().distance(me.getPoint());
		if(distance > 150){
			//normalize to nearest max point
			Point offset = new Point((int)((me.getPoint().x-xLoc)*150/distance), (int)((me.getPoint().y-yLoc)*150/distance));
			pg.setLocation(xLoc+offset.x, yLoc+offset.y);
		} else
		pg.setLocation(me.getPoint().x, me.getPoint().y);
		double ang = Math.atan2((yLoc-pg.getY()),(xLoc - pg.getX()));
		pg.setVelocity(new Velocity(0,ang));
	}

	public void mouseMoved(MouseEvent arg0) {}
	public void resetLaunch() {
		pg.setVelocity(new Velocity(0,0));
		launched = false;
	}
	public double getRadius() {
		if(launched)return 0;
		return 15;
	}
}
