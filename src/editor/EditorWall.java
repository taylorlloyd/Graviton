package editor;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

import game.CollisionException;
import game.Force;
import game.ForceWall;
import game.GravityPane;
import game.PopWall;
import game.Velocity;
import game.Wall;
import game.WarpWall;
import gravitable.Gravitable;

public class EditorWall extends EditorObject implements Wall {
	public Wall model;
	public String type="force";
	public EditorWall(Wall w) {
		behaveAs(w);
	}
	public void behaveAs(Wall w) {
		model = w;
		if(model instanceof PopWall) {
			type = "pop";
		} else if(model instanceof ForceWall)
			type = "force";
	}
	@Override
	public boolean contains(double x, double y) {
		return model.contains(x, y);
	}

	@Override
	public void impact(Gravitable g) {
		model.impact(g);
	}

	@Override
	public void paint(Graphics g) {
		g.setColor(Color.cyan);
		g.drawRect(model.getRect().x, model.getRect().y, model.getRect().width, model.getRect().height);
		if(model instanceof WarpWall) {
			g.setColor(Color.blue);
			g.drawLine((int)getX()+(getRect().width/2), (int)getY()+(getRect().height/2),((WarpWall) model).endPoint.x, ((WarpWall) model).endPoint.y);
			g.drawOval((int)(((WarpWall) model).endPoint.x-4), (int)(((WarpWall) model).endPoint.y-4), 8, 8);
		}
	}
	@Override
	public Rectangle getRect() {
		return model.getRect();
	}
	@Override
	public void setRect(Rectangle r) {
		model.setRect(r);
	}
	public String toString() {
		if(model instanceof WarpWall)
			return "warp("+model.getRect().x+","+model.getRect().y+","+model.getRect().width+","+model.getRect().height+","+((WarpWall)model).endPoint.x+","+((WarpWall)model).endPoint.y+")";
		return "wall("+type+","+model.getRect().x+","+model.getRect().y+","+(model.getRect().x+model.getRect().width)+","+(model.getRect().y + model.getRect().height)+")";
	}
	@Override
	public String[] getEditArray() {
		// TODO Auto-generated method stub
		if(model instanceof WarpWall)
			return new String[] {"xLoc", "yLoc", "xOut", "yOut"};
		return new String[] {"pop","xLoc","yLoc","width","height"};
	}
	@Override
	public double getX() {
		return model.getRect().x;
	}
	@Override
	public double getY() {
		return model.getRect().y;
	}
	@Override
	public void setX(double x) {
		model.getRect().x=(int)x;
	}
	@Override
	public void setY(double y) {
		model.getRect().y=(int)y;
	}


}
