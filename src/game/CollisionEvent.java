package game;

import gravitable.Gravitable;


public class CollisionEvent {
	public Gravitable g1;
	public Gravitable g2;
	public CollisionEvent(Gravitable g, Gravitable g2) {
		this.g1 = g;
		this.g2 = g2;
	}
}
