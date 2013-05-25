package editor;

import game.CollisionEvent;
import game.GravityPane;
import game.Wall;
import gravitable.Gravitable;

import java.awt.Point;

public class EditorGravityPane extends GravityPane {
	
	public EditorGravityPane() {
		super(null);
	}
	public Gravitable gravAt(Point p) {
		for(Gravitable g : objects) {
			if(!(g instanceof EditorGravitable))
			{continue;}
			if(g.getLocation().distance(p) <= g.getRadius())
				return g;
		}
		return null;
	}
	public Wall wallAt(Point p) {
		for(Wall w : walls) {
			if(w.getRect().contains(p))
				return w;
		}
		return null;
	}
	public EditorObject objAt(Point p) {
		Gravitable g = gravAt(p);
		if(g!= null) return (EditorGravitable) g;
		return (EditorWall)wallAt(p);
	}
	public void onCollision(CollisionEvent e) {
		//Ignore Collisions
	}
	public void updatePaintArray() {
		paintable = objects.toArray(new Gravitable[0]);
		paintwalls = walls.toArray(new Wall[0]);
	}
	public void remove(EditorObject obj) {
		objects.remove(obj);
		walls.remove(obj);
	}
}
