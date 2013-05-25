package game;

import gravitable.Gravitable;

import java.awt.Graphics;
import java.awt.Rectangle;

public interface Wall {
	public boolean contains(double x, double y);
	public void impact(Gravitable g);
	public void paint(Graphics g);
	public Rectangle getRect();
	public void setRect(Rectangle r);
}
