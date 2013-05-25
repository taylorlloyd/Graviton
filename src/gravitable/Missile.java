package gravitable;

import game.AnimTimer;
import game.CollisionEvent;
import game.CollisionListener;
import game.DataManager;
import game.ExplosionEffect;
import game.GravityPane;
import game.Velocity;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.RenderingHints;

public class Missile extends SimpleGravitable implements CollisionListener{
	GravityPane gp;
	Image missile;
	public final int NORMAL=0;
	public final int EXPLODING = 1;
	int state=NORMAL;
	long explodetmr;
	public Missile(double x, double y, Velocity v) {
		super(5, x, y, v);
		missile = DataManager.loadImage("missile.png");
	}
	public void applyVelocity() {
		if(state == EXPLODING) return;
		setVelocity(new Velocity(getVelocity().getMag()+0.05, getVelocity().getAngle()));
		super.applyVelocity();
	}
	public void paint(Graphics g) {
		Graphics2D g2 = (Graphics2D)g.create();
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
		g2.translate((int)xLoc, (int)yLoc);
		g2.rotate(getVelocity().getAngle()+(Math.PI/2));
		if(state == EXPLODING) {
			int explodecount = AnimTimer.countSince(explodetmr, 6);
			ExplosionEffect.paint(g2, 2, new Point(0,0), 25);
			if(explodecount > 100){
				explodetmr = AnimTimer.startTmr();
				gp.objects.remove(this);
			}
			g2.setColor(new Color(Color.orange.getRed(),Color.orange.getGreen(), Color.orange.getBlue(), 255-2*explodecount));
			g2.fillOval(explodecount/-2, explodecount/-2, explodecount, explodecount);
			return;
		}
		g2.drawImage(missile, -5, -13, null);
	}
	public void explode() {
		if(state == NORMAL)
		explodetmr = AnimTimer.startTmr();
		state = EXPLODING;
	}
	@Override
	public void collisionOccurred(CollisionEvent ce) {
		if((ce.g1.equals(this) || ce.g2.equals(this)) &&(!(ce.g1 instanceof Turret || ce.g2 instanceof Turret)))
			{explode();}
	}
	public void setPane(GravityPane gp) {
		this.gp = gp;
		gp.addCollisionListener(this);
	}
}
