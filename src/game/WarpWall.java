package game;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;

import gravitable.Gravitable;
import gravitable.PlayerGravitable;

public class WarpWall implements Wall {
	public Point endPoint;
	Image bhole;
	Rectangle r;
	public WarpWall(int x, int y, int width, int height, Point end) {
		bhole = DataManager.loadImage("bhole.png");
		endPoint = end;
		r = new Rectangle(x,y,width,height);
	}
	@Override
	public boolean contains(double x, double y) {
		return r.contains(x, y);
	}

	@Override
	public Rectangle getRect() {
		return r;
	}

	@Override
	public void impact(Gravitable g) {
		if(g instanceof PlayerGravitable)
		{
			if(((PlayerGravitable)g).getState() == PlayerGravitable.NORMAL)
			((PlayerGravitable) g).teleTo(endPoint.x, endPoint.y);
		}
	}

	@Override
	public void paint(Graphics g) {
		g.drawImage(bhole, r.x, r.y, null);
	}

	@Override
	public void setRect(Rectangle r) {
		this.r = r;
	}

}
