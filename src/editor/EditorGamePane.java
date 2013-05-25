package editor;

import java.awt.Image;
import java.awt.Point;

import game.GamePane;
import game.GameProperties;
import game.GravityPane;
import game.Velocity;
import game.Wall;
import gravitable.Endpoint;
import gravitable.Gravitable;
import gravitable.Launcher;
import gravitable.PlayerGravitable;

public class EditorGamePane {
	private String lastProps;
	private EditorGravityPane gp;
	public GameProperties props;
	public EditorGamePane() {
		gp = new EditorGravityPane();
	}
	public void loadProperties(String s) {
		lastProps = s;
		//Dynamically added data won't be present - (pack, index)
		props = GameProperties.fromString(s);
		for(Gravitable g : props.gravitables)
			try{gp.add(new EditorGravitable(g));}catch(Exception e) {}
		for(Gravitable g : props.items)
			try{gp.add(new EditorGravitable(g));}catch(Exception e) {}
		for(Wall w : props.walls)
			try{gp.add(new EditorWall(w));}catch(Exception e) {}
		if(props.background != null) {
			try{gp.image = props.background;}catch(Exception e) {}
		}
		try{gp.add(new EditorGravitable(new Endpoint(props.end.x, props.end.y, new Velocity(0,0))));}catch(Exception e) {}
		try{gp.add(new EditorGravitable(new Launcher(props.start.x, props.start.y)));}catch(Exception e) {}
		drawUpdate();
	}
	public void updateString(String level) {
		lastProps = level;
	}
	public void updateString(String levelStr, int level) {
		updateString(levelStr);
	}
	public EditorObject objAt(Point p) {
		return (EditorObject) gp.objAt(p);
	}
	
	public void clear() {
		gp.clear();
		gp.setBackground((Image)null);
		paneset=false;
	}
	public void reset() {
		clear();
		loadProperties(lastProps);
		for(Gravitable g : gp.objects)
			if(g instanceof PlayerGravitable) {
				gp.objects.remove(g);
			}
		drawUpdate();
	}
	public void drawUpdate() {
		gp.updatePaintArray();
		gp.repaint();
	}
	private boolean paneset=false;
	public void playGravity() {
		if(!paneset)
		for(Gravitable g : gp.objects.toArray(new Gravitable[0])) {
			((EditorGravitable)g).setPane(gp,true);
		}
		paneset=true;
		new Thread(gp).start();
	}
	public void pauseGravity() {
		gp.gravityOn = false;
		drawUpdate();
	}
	public GravityPane getPanel() {
		return gp;
	}
	public void updatePaintArray() {
		gp.updatePaintArray();
	}
	public String getLevelString() {
		return lastProps;
	}

}
