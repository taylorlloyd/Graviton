package gravitable;

import game.AnimTimer;
import game.DataManager;
import game.GravityPane;
import game.Velocity;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;

public class SuperTurret extends SimpleGravitable {
	Image turretImg;
	long fireTmr;
	long firingTmr;
	boolean playerFound;
	boolean disable=false;
	PlayerGravitable player;
	private GravityPane gp;
	public SuperTurret(double x, double y) {
		super(0, x, y, new Velocity(0,0));
		turretImg = DataManager.loadImage("superturret.png");
	}
	public void setPane(GravityPane gp) {
		this.gp = gp;
	}
	public void applyVelocity() {
		if(!playerFound) {
			for(Gravitable g : gp.objects.toArray(new Gravitable[0]))
				if(g instanceof PlayerGravitable) {
					player = (PlayerGravitable)g;
					playerFound = true;
					fireTmr = AnimTimer.startTmr();
					if(player.getState() == PlayerGravitable.EXPLODING) playerFound = false;
				}
		} else {
			if(player.getState()==PlayerGravitable.EXPLODING)playerFound = false;
			if(AnimTimer.countSince(fireTmr, 10)>100) {
				gp.add(new SuperMissile(getLocation().x,getLocation().y,player));
				fireTmr = AnimTimer.startTmr();
			}
		}
	}
	public void paint(Graphics g) {
		Graphics2D g2 = (Graphics2D) g.create();
		g2.translate(xLoc, yLoc);
		if(playerFound) {
			g2.rotate(angleTo(player)-Math.PI/2);
		}
		g2.drawImage(turretImg, -15, -15, null);
	}
	public double angleTo(Gravitable g) {
		return Math.atan2((yLoc-player.getY()),(xLoc-player.getX()));
	}
	public double getRadius() {
		return 0;
	}
	

}
