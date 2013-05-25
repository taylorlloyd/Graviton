package gravitable;

import game.AnimTimer;
import game.CollisionException;
import game.ExplosionEffect;
import game.Force;
import game.Velocity;

import java.awt.Color;
import java.awt.Graphics;

public class BombFragment extends SimpleGravitable {
	boolean triggered = false;
	long tmr;
	public BombFragment(double x, double y, Velocity v) {
		super(2, x, y, v);
		setMove(false);
		setColor(Color.lightGray);
	}
	public void trigger() {
		tmr = AnimTimer.startTmr();
		triggered = true;
		setMove(true);
	}
	public Force forceOn(Gravitable g) throws CollisionException {
		if(!triggered) return new Force(0,0);
		return super.forceOn(g);
	}
	public void paint(Graphics g) {
		if(!triggered) return;
		super.paint(g);
		int count = AnimTimer.countSince(tmr, 20);
		if(count < 256)
			ExplosionEffect.paint(g, 2, getLocation(), 8, 255-count);
	}

}
