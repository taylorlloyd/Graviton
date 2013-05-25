package hook;

import game.CollisionException;
import game.Force;
import game.GravityPane;
import game.Velocity;
import game.Window;
import gravitable.Gravitable;
import gravitable.PlayerGravitable;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class PlayerHack implements Gravitable, KeyListener {
	GravityPane gp;
	double xLoc = 0;
	double yLoc = 0;
	double changeX = 0;
	double changeY = 0;
	boolean allowPlayerCollide=false;
	boolean destroyPlayer=false;
	@Override
	public void applyForce(Force f) {
	}

	@Override
	public void applyVelocity() {
		for(Gravitable g : gp.objects) {
			if(g instanceof PlayerGravitable)
				hackPlayer((PlayerGravitable)g);
		}
	}
	public void hackPlayer(PlayerGravitable pg) {
		if(destroyPlayer){
			if(pg.getState() != pg.EXPLODING)
			pg.explode();
			return;
		}
		if(xLoc == 0 && yLoc == 0)
		{
			xLoc = pg.getX();
			yLoc = pg.getY();
		}
		xLoc += changeX;
		yLoc += changeY;
		pg.setLocation(xLoc, yLoc);
		if(allowPlayerCollide && pg.getState() == pg.INVINCIBLE)
			pg.state = pg.NORMAL;
		if(!allowPlayerCollide)
			pg.state = pg.INVINCIBLE;
	}
	@Override
	public boolean canMove() {
		return false;
	}

	@Override
	public Gravitable copy() {
		return this;
	}

	@Override
	public double distanceFrom(Gravitable g) {
		return 1000000;
	}

	@Override
	public Force forceOn(Gravitable g) throws CollisionException {
		return new Force(0.01,0.01);
	}

	@Override
	public Color getColor() {
		return Color.red;
	}

	@Override
	public Point getLocation() {
		return new Point(1,1);
	}

	@Override
	public int getMass() {
		return 0;
	}

	@Override
	public double getRadius() {
		return 0;
	}

	@Override
	public Velocity getVelocity() {
		return new Velocity(0,0);
	}

	@Override
	public double getX() {
		return 0;
	}

	@Override
	public double getY() {
		return 0;
	}

	@Override
	public void paint(Graphics g) {
	}

	@Override
	public void setColor(Color c) {
	}

	@Override
	public void setLocation(double x, double y) {
	}

	@Override
	public void setMass(int mass) {
	}

	@Override
	public void setMove(boolean b) {
	}

	@Override
	public void setPane(GravityPane gp) {
		this.gp = gp;
		Window.current.addKeyListener(this);
	}

	@Override
	public void setVelocity(Velocity v) {
	}

	@Override
	public void keyPressed(KeyEvent k) {
		if(k.getKeyCode() == k.VK_A)
			changeX = -0.5;
		else if(k.getKeyCode() == k.VK_D)
			changeX = 0.5;
		else if(k.getKeyCode() == k.VK_W)
			changeY = -0.5;
		else if(k.getKeyCode() == k.VK_S)
			changeY = 0.5;
		else if(k.getKeyCode() == k.VK_Q)
			allowPlayerCollide = true;
		else if(k.getKeyCode() == k.VK_E)
			destroyPlayer=true;
	}

	@Override
	public void keyReleased(KeyEvent k) {
		if(k.getKeyCode() == k.VK_A)
			changeX = 0;
		else if(k.getKeyCode() == k.VK_D)
			changeX = 0;
		else if(k.getKeyCode() == k.VK_W)
			changeY = 0;
		else if(k.getKeyCode() == k.VK_S)
			changeY = 0;
		else if(k.getKeyCode() == k.VK_Q)
			allowPlayerCollide = false;
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
