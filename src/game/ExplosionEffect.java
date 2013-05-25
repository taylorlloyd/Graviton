package game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.Random;

public class ExplosionEffect {
	public static Color[] colors = new Color[] {Color.red, Color.yellow, Color.orange};
	public static void paint(Graphics g, int circles, Point center, int radius) {
		Random r = new Random();
		for(int i=0;i<circles;i++) {
			int x = r.nextInt(radius+radius)+center.x-radius;
			int y = r.nextInt(radius+radius)+center.y-radius;
			int size = r.nextInt(radius-5)+5;
			g.setColor(colors[r.nextInt(colors.length)]);
			g.fillOval(x-(size/2), y-(size/2), size, size);
		}
	}
	public static void paint(Graphics g, int circles, Point center, int radius,int alpha) {
		Random r = new Random();
		for(int i=0;i<circles;i++) {
			int x = r.nextInt(radius+radius)+center.x-radius;
			int y = r.nextInt(radius+radius)+center.y-radius;
			int size = r.nextInt(radius-5)+5;
			g.setColor(translucentColor(colors[r.nextInt(colors.length)], alpha));
			g.fillOval(x-(size/2), y-(size/2), size, size);
		}
	}
	private static Color translucentColor(Color c, int alpha) {
		return new Color(c.getRed(), c.getGreen(), c.getBlue(), alpha);
	}
}
