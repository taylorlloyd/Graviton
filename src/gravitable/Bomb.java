package gravitable;

import game.AnimTimer;
import game.ExplosionEffect;
import game.Force;
import game.GravityPane;
import game.Velocity;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.Random;

public abstract class Bomb extends SimpleGravitable{
	public BombFragment[] bf;
	public GravityPane gp;
	public int state = NORMAL;
	public static final int NORMAL = 0;
	public static final int EXPLODING = 1;
	public static final int GONE = 2;
	long explodetmr;
	boolean triggered = false;
	public void explode() {
		explodetmr = AnimTimer.startTmr();
		state = EXPLODING;
	}
	public Bomb(double x, double y, int particles) {
		super(0, x, y, new Velocity(0,0));
		setMove(false);
		bf = new BombFragment[particles];
		// TODO Auto-generated constructor stub
	}
	public void setPane(GravityPane gp) {
		this.gp = gp;
		for(int i=0;i<bf.length;i++) {
			bf[i]=new BombFragment(xLoc, yLoc, new Velocity(4,new Random().nextDouble()*6.28));
			gp.add(bf[i]);
		}
	}
	public abstract boolean triggers(Gravitable g);
	public Force forceOn(Gravitable g) {
		if(triggers(g) && !triggered) blowUp();
		return new Force(0,0);
	}
	private void blowUp() {
		triggered = true;
		for(BombFragment b : bf) {
			b.trigger();
		}
		explode();
	}
	public abstract void paintBomb(Graphics2D g);
	public void paint(Graphics g) {
		Graphics2D g2 = (Graphics2D) g.create();
		g2.translate(xLoc, yLoc);
		if(state == EXPLODING) {
			int explodecount = AnimTimer.countSince(explodetmr, 6);
			ExplosionEffect.paint(g2, 5, new Point(0,0), 40);
			g2.setColor(new Color(Color.orange.getRed(),Color.orange.getGreen(), Color.orange.getBlue(), 255-2*explodecount));
			g2.fillOval(explodecount*-1, explodecount*-1, 2*explodecount, 2*explodecount);
			if(explodecount > 100)
				state = GONE;
			return;
		}
		if(state == NORMAL) {
			paintBomb(g2);
		}
	}
}
