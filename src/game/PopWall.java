package game;

import gravitable.Gravitable;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

public class PopWall extends ForceWall{
	public PopWall(double x1, double y1, double x2, double y2) {
		super(x1, y1, x2, y2);
	}

	int count=0;
	int dir = 1;
	@Override
	public void impact(Gravitable g) {
	
		if (g instanceof Popper)
			r=new Rectangle();
		else 
			super.impact(g);
	}

	@Override
	public void paint(Graphics g) {
		g.setColor(new Color(0,240,255,count/2));
		g.fillRect(r.x, r.y, r.width, r.height);
		count += dir;
		if(count >= 300)
			dir = -1;
		if(count <= 0)
			dir = 1;
	}

}
