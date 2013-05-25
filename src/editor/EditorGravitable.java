package editor;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

import game.CollisionException;
import game.Force;
import game.GravityPane;
import game.Velocity;
import gravitable.CollectorItem;
import gravitable.Endpoint;
import gravitable.Gravitable;
import gravitable.Launcher;
import gravitable.PathGravitable;
import gravitable.ProximityBomb;
import gravitable.ResetGravitable;
import gravitable.SuperTurret;
import gravitable.Turret;

public class EditorGravitable extends EditorObject implements Gravitable {
	public Gravitable model;
	private boolean corrupted = false;
	public EditorGravitable(Gravitable g) {
		behaveAs(g);
	}
	public void behaveAs(Gravitable g) {
		model = g;
	}
	public String getGeneratorString() {
		String type = getTypeStr();
		if(type.equals("object") || type.equals("reset"))
			return type+"("+model.getMass()+","+(int)model.getX()+","+(int)model.getY()+","+Boolean.toString(model.canMove())+","+getColorStr(model.getColor())+","+getVelocityStr(model.getVelocity())+")";
		if(type.equals("item"))
			return "item("+(int)model.getX()+","+(int)model.getY()+")";
		if(type.equals("start") || type.equals("end"))
			return type+"=["+(int)model.getX()+","+(int)model.getY()+"]";
		if(type.equals("path"))
			return type+"("+model.getMass()+","+((PathGravitable)model).vel+","+((PathGravitable)model).start.x+","+((PathGravitable)model).start.y+","+((PathGravitable)model).end.x+","+((PathGravitable)model).end.y+","+getColorStr(model.getColor())+")";
		if(type.equals("proximity"))
			return "bomb(proximity,"+model.getX()+","+model.getY()+","+((ProximityBomb)model).triggerDist+","+((ProximityBomb)model).bf.length+")";
		if(type.contains("turret"))
			return type+"("+model.getX()+","+model.getY()+")";
			return "";
	}
	String getTypeStr() {
		String type = "object";
		if(model instanceof CollectorItem)
			type = "item";
		if(model instanceof ResetGravitable)
			type = "reset";
		if(model instanceof Launcher)
			type = "start";
		if(model instanceof Endpoint)
			type = "end";
		if(model instanceof PathGravitable)
			type = "path";
		if(model instanceof ProximityBomb)
			type = "proximity";
		if(model instanceof Turret)
			type = "turret";
		if(model instanceof SuperTurret)
			type = "superturret";
		
		return type;
	}
	private static String getColorStr(Color c) {
		return "color["+c.getRed()+","+c.getGreen()+","+c.getBlue()+"]";
	}
	private static String getVelocityStr(Velocity v) {
		return "velocity["+v.getX()+","+v.getY()+"]";
	}
	public void applyForce(Force f) {
		model.applyForce(f);
	}

	public void applyVelocity() {
		model.applyVelocity();
	}

	public boolean canMove() {
		return model.canMove();
	}

	public double distanceFrom(Gravitable g) {
		return model.distanceFrom(g);
	}

	public Force forceOn(Gravitable g) throws CollisionException {
		return model.forceOn(g);
	}

	public Point getLocation() {
		return model.getLocation();
	}

	public int getMass() {
		return model.getMass();
	}

	public double getRadius() {
		return model.getRadius();
	}

	public Velocity getVelocity() {
		return model.getVelocity();
	}

	public double getX() {
		return model.getX();
	}

	public double getY() {
		return model.getY();
	}

	public void paint(Graphics g) {
		if(model instanceof PathGravitable) {
			g.setColor(Color.green);
			g.drawLine(((PathGravitable)model).start.x, ((PathGravitable)model).start.y, ((PathGravitable)model).end.x, ((PathGravitable)model).end.y);
		}
		model.paint(g);
		if(model instanceof ProximityBomb) {
			g.setColor(Color.red);
			g.drawOval((int)(model.getX()-((ProximityBomb)model).triggerDist), (int)(model.getY()-((ProximityBomb)model).triggerDist), 2*(int)((ProximityBomb)model).triggerDist, 2*(int)((ProximityBomb)model).triggerDist);
		}
		g.setColor(Color.ORANGE);
		g.drawOval(model.getLocation().x-(int)model.getRadius(), model.getLocation().y-(int)model.getRadius(), 2*(int)model.getRadius(), 2*(int)model.getRadius());
	}

	public void setLocation(double x, double y) {
		model.setLocation(x,y);
	}
	public void setMass(int mass) {
		model.setMass(mass);
	}

	public void setPane(GravityPane gp) {
		setPane(gp,false);
	}
	public void setPane(GravityPane gp, boolean pass) {
		if(pass)
			model.setPane(gp);
	}
	public void setVelocity(Velocity v) {
		model.setVelocity(v);
	}
	public Gravitable copy() {
		return this;
	}
	
	public Color getColor() {
		return model.getColor();
	}
	
	public void setColor(Color c) {
		model.setColor(c);
		
	}
	@Override
	public void setMove(boolean b) {
		model.setMove(b);
	}
	@Override
	public String[] getEditArray() {
		if(model instanceof ProximityBomb)
			return new String[] {"xLoc","yLoc","pieces","proximity"};
		if(model instanceof PathGravitable)
			return new String[] {"mass","xLoc","yLoc","color","speed","xPath","yPath"};
		else if(model instanceof Launcher || model instanceof Endpoint || model instanceof CollectorItem)
			return new String[] {"xLoc", "yLoc"};
		return new String[] {"mass","xLoc","yLoc","color","movable","xVel","yVel"};
	}
	@Override
	public void setX(double x) {
		model.setLocation(x, model.getY());
	}
	@Override
	public void setY(double y) {
		model.setLocation(model.getX(),y);
	}

}
