package gravitable;

import game.AnimTimer;
import game.CollisionEvent;
import game.CollisionListener;
import game.DataManager;
import game.ExplosionEffect;
import game.Force;
import game.GravityPane;
import game.Popper;
import game.Velocity;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.RenderingHints;


public class PlayerGravitable extends SimpleGravitable implements CollisionListener, Popper {
	GravityPane gp;
	Image rocket;
	public int state = NORMAL;
	public static final int NORMAL = 0;
	public static final int INVINCIBLE = 4;
	public static final int WARPING = 1;
	public static final int TELEPORTING = 3;
	public static final int EXPLODING = 2;
	long warptmr;
	long teletmr;
	long explodetmr;
	Point telOff;
	boolean in=true;
	public int getState() {
		return state;
	}
	public void applyForce(Force f) {
		if(state == NORMAL)
			super.applyForce(f);
	}
	public void explode() {

		explodetmr = AnimTimer.startTmr();
		state = EXPLODING;
	}
	public void warpOut() {
		in=true;
		warptmr = AnimTimer.startTmr();
		state = WARPING;
	}
	public void teleTo(double x, double y) {
		telOff = new Point((int)(x-xLoc), (int) (y-yLoc));
		teletmr = AnimTimer.startTmr();
		state = TELEPORTING;
	}
	public void setPane(GravityPane gp) {
		gp.addCollisionListener(this);
		this.gp = gp;
	}
	public PlayerGravitable(double x, double y) {
		super(10, x, y, new Velocity(0,0));
		rocket = DataManager.loadImage("player.png");
	}
	public void applyVelocity() {
		if (state == WARPING) {
			setVelocity(new Velocity(getVelocity().getMag()*9/10,getVelocity().getAngle()));
		}
		super.applyVelocity();
	}

	public void collisionOccurred(CollisionEvent ce) {
		if(ce.g1 instanceof Endpoint || ce.g2 instanceof Endpoint) {
			return;
		}
		if (state == NORMAL) {
		if(ce.g1.equals(this) || ce.g2.equals(this)) {
			if(ce.g1 instanceof CollectorItem) {
				return;
			}
			if(ce.g2 instanceof CollectorItem) {
				return;
			}
			setVelocity(new Velocity(0,getVelocity().getAngle()));
			setMove(false);
			explode();
		}
		}
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
				gp.game.resetPlayer();
			}
			g2.setColor(new Color(Color.orange.getRed(),Color.orange.getGreen(), Color.orange.getBlue(), 255-2*explodecount));
			g2.fillOval(explodecount/-2, explodecount/-2, explodecount, explodecount);
			return;
		}
		if(state == TELEPORTING) {
			int teleportcount = AnimTimer.countSince(teletmr, 2);
			Graphics2D g3 = (Graphics2D)g.create();
			g3.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
	                RenderingHints.VALUE_ANTIALIAS_ON);
			g3.translate((int)xLoc+telOff.x, (int)yLoc+telOff.y);
			g3.rotate(getVelocity().getAngle()+(Math.PI/2));
			g2.setColor(new Color(20,250,90));
			g3.setColor(new Color(20,250,90));
			if(teleportcount<=60) {
				g2.drawOval(teleportcount/-4, teleportcount/-4, teleportcount/2, teleportcount/2);
				g3.fillOval(teleportcount/-4, teleportcount/-4, teleportcount/2, teleportcount/2);
			} else if(teleportcount <=120){
				g3.drawOval((120-teleportcount)/-4, (120-teleportcount)/-4, (120-teleportcount)/2, (120-teleportcount)/2);
				g2.fillOval((120-teleportcount)/-4, (120-teleportcount)/-4, (120-teleportcount)/2, (120-teleportcount)/2);
				g2 = g3;
			} else {
				state = NORMAL;
				xLoc += telOff.x;
				yLoc += telOff.y;
				teleportcount = 0;
			}
		}
		if(in)g2.drawImage(rocket, -10, -13, null);
		if(state == WARPING) {
			int warpcount = AnimTimer.countSince(warptmr, 3);
			if(!in)
				warpcount = 120 - warpcount;
			g2.setColor(new Color(20,90,250));
			g2.drawOval(warpcount/-4, warpcount/-4, warpcount/2, warpcount/2);
			if(warpcount > 60)
				in = false;
			if(!in) {
				g2.fillOval(warpcount/-4, warpcount/-4, warpcount/2, warpcount/2);
				if(warpcount < -200) {
					gp.game.winLevel();
				}
				warpcount-=2;
			}
		}
	}

}
