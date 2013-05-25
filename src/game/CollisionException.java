package game;

import gravitable.Gravitable;


public class CollisionException extends Exception {
	Gravitable g1;
	Gravitable g2;
	public CollisionException(Gravitable g1, Gravitable g2) {
		this.g1 = g1;
		this.g2 = g2;
	}
	public CollisionEvent createEvent() {
		return new CollisionEvent(g1, g2);
	}
}
