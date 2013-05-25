package game;

import java.awt.Color;
import java.awt.Image;
import java.awt.Point;
import java.util.ArrayList;

import javax.swing.ImageIcon;

import gravitable.Bomb;
import gravitable.CollectorItem;
import gravitable.Gravitable;
import gravitable.PathGravitable;
import gravitable.ProximityBomb;
import gravitable.ResetGravitable;
import gravitable.SimpleGravitable;
import gravitable.SuperTurret;
import gravitable.Turret;


public class GameProperties {
	private GameProperties() {}
	public String name;
	public int index; //MUST CORRESPOND TO LINE!!!
	public Gravitable[] gravitables;
	public CollectorItem[] items;
	public Wall[] walls;
	public Point start;
	public Point end;
	public Image background;
	public String imagename="default.png";
	public String original;
	public boolean timed=false;
	public int timetobeat=0;
	public String pack;
	public static GameProperties fromString(String s) {
		GameProperties gp = new GameProperties();
		gp.original = s;
		String[] commands = s.split(";");
		ArrayList<Gravitable> g = new ArrayList<Gravitable>();
		ArrayList<CollectorItem> i = new ArrayList<CollectorItem>();
		ArrayList<Wall> w = new ArrayList<Wall>();
		for(String command : commands) {
			try {
			if(command.startsWith("name=")) gp.name = command.substring(5);
			else if(command.startsWith("index=")) {
				gp.index = Integer.parseInt(command.substring(6));
			}
			else if(command.startsWith("pack=")) {
				gp.pack = command.substring(5);
			}
			else if(command.startsWith("time=")) {
				gp.timetobeat = Integer.parseInt(command.substring(5));
				gp.timed=true;
			}
			else if(command.startsWith("start=")) {
				String[] point = command.substring(7, command.indexOf("]")).split(",");
				gp.start = new Point(Integer.parseInt(point[0]),Integer.parseInt(point[1]));
			} else if(command.startsWith("end=")) {
				String[] point = command.substring(5, command.indexOf("]")).split(",");
				gp.end = new Point(Integer.parseInt(point[0]),Integer.parseInt(point[1]));
			} else if(command.startsWith("turret(")) {
				String[] point = command.substring(7, command.indexOf(")")).split(",");
				g.add(new Turret(Double.parseDouble(point[0]),Double.parseDouble(point[1])));
			} else if(command.startsWith("superturret(")) {
				String[] point = command.substring(12, command.indexOf(")")).split(",");
				g.add(new SuperTurret(Double.parseDouble(point[0]),Double.parseDouble(point[1])));
			} else if(command.startsWith("item")) {
				String[] point = command.substring(5, command.indexOf(")")).split(",");
				i.add(new CollectorItem(Double.parseDouble(point[0]),Double.parseDouble(point[1])));
			} else if(command.startsWith("background=")) {
				gp.imagename = command.substring(11);
				gp.background = DataManager.loadImage(command.substring(11));
			} else if(command.startsWith("object(")) g.add(gravFromString(command.substring(6),false));
			  else if(command.startsWith("reset(")) g.add(gravFromString(command.substring(5),true));
			  else if(command.startsWith("path(")) g.add(pathFromString(command.substring(4)));
			  else if(command.startsWith("bomb(")) g.add(bombFromString(command.substring(4)));
			  else if(command.startsWith("wall(")) w.add(wallFromString(command.substring(4)));
			  else if(command.startsWith("warp(")) w.add(warpFromString(command.substring(4)));
			} catch (Exception e) {
				System.out.println("Error parsing command: "+command);
				e.printStackTrace();
			}
		}
		gp.gravitables = g.toArray(new Gravitable[0]);
		gp.items = i.toArray(new CollectorItem[0]);
		gp.walls = w.toArray(new Wall[0]);
		System.out.println(gp.gravitables.length + "Objects loaded");
		return gp;
	}
	public static GameProperties parseMeta(String s) {
		GameProperties gp = new GameProperties();
		gp.original = s;
		String[] commands = s.split(";");
		for(String command : commands) {
			try {
				if(command.startsWith("name=")) gp.name = command.substring(5);
				else if(command.startsWith("index=")) {
					gp.index = Integer.parseInt(command.substring(6));
				}
				else if(command.startsWith("pack=")) {
					gp.pack = command.substring(5);
				}
				else if(command.startsWith("time=")) {
					gp.timetobeat = Integer.parseInt(command.substring(5));
					gp.timed=true;
				} 
			} catch(Exception e) {}
		}
		return gp;
	}
	public GameProperties loadFully() {
		return fromString(original);
	}
	private static Gravitable gravFromString(String objstring, boolean reset) {
		//mass,x,y,movable,color[r,g,b],velocity[x,y]
		objstring = objstring.replace("(", "").replace(")", "");
		int mass = Integer.parseInt(objstring.substring(0,objstring.indexOf(",")));
		objstring = objstring.substring(objstring.indexOf(",")+1);
		double x = Double.parseDouble(objstring.substring(0,objstring.indexOf(",")));
		objstring = objstring.substring(objstring.indexOf(",")+1);
		double y = Double.parseDouble(objstring.substring(0,objstring.indexOf(",")));
		objstring = objstring.substring(objstring.indexOf(",")+1);
		boolean movable = Boolean.parseBoolean(objstring.substring(0,objstring.indexOf(",")));
		objstring = objstring.substring(objstring.indexOf(",")+1);
		Color color = colorFromString(objstring.substring(0,objstring.indexOf(",", objstring.indexOf("]"))));
		objstring = objstring.substring(objstring.indexOf(",", objstring.indexOf("]"))+1);
		Velocity v = velocityFromString(objstring);
		
		SimpleGravitable g;
		if(reset)
			g = new ResetGravitable(mass,x,y,v);
		else
			g = new SimpleGravitable(mass,x,y,v);
		g.setMove(movable);
		g.setColor(color);
		return g;
	}
	private static Gravitable pathFromString(String objstring) {
		//mass,x,y,movable,color[r,g,b],velocity[x,y]
		objstring = objstring.replace("(", "").replace(")", "");
		int mass = Integer.parseInt(objstring.substring(0,objstring.indexOf(",")));
		objstring = objstring.substring(objstring.indexOf(",")+1);
		double vel = Double.parseDouble(objstring.substring(0,objstring.indexOf(",")));
		objstring = objstring.substring(objstring.indexOf(",")+1);
		double x1 = Double.parseDouble(objstring.substring(0,objstring.indexOf(",")));
		objstring = objstring.substring(objstring.indexOf(",")+1);
		double y1 = Double.parseDouble(objstring.substring(0,objstring.indexOf(",")));
		objstring = objstring.substring(objstring.indexOf(",")+1);
		double x2 = Double.parseDouble(objstring.substring(0,objstring.indexOf(",")));
		objstring = objstring.substring(objstring.indexOf(",")+1);
		double y2 = Double.parseDouble(objstring.substring(0,objstring.indexOf(",")));
		objstring = objstring.substring(objstring.indexOf(",")+1);
		Color color = colorFromString(objstring.substring(0,objstring.indexOf("]")));
		PathGravitable g;
		g=new PathGravitable(new Point((int)x1, (int)y1), new Point((int)x2, (int)y2), mass, vel);
		g.setColor(color);
		return g;
	}
	private static Wall wallFromString(String objstring) {
		//mass,x,y,movable,color[r,g,b],velocity[x,y]
		Wall w;
		objstring = objstring.replace("(", "").replace(")", "");
		String type = objstring.substring(0,objstring.indexOf(","));
		objstring = objstring.substring(objstring.indexOf(",")+1);
		double x1 = Double.parseDouble(objstring.substring(0,objstring.indexOf(",")));
		objstring = objstring.substring(objstring.indexOf(",")+1);
		double y1 = Double.parseDouble(objstring.substring(0,objstring.indexOf(",")));
		objstring = objstring.substring(objstring.indexOf(",")+1);
		double x2 = Double.parseDouble(objstring.substring(0,objstring.indexOf(",")));
		objstring = objstring.substring(objstring.indexOf(",")+1);
		double y2 = Double.parseDouble(objstring);
		if(type.contains("force"))
			w = new ForceWall(x1, y1, x2, y2);
		else if (type.contains("pop"))
			w = new PopWall(x1, y1, x2, y2);
		else
			w = new ForceWall(x1, y1, x2, y2);
		return w;
	}
	private static Wall warpFromString(String objstring) {
		//mass,x,y,movable,color[r,g,b],velocity[x,y]
		Wall w;
		objstring = objstring.replace("(", "").replace(")", "");
		int x1 = Integer.parseInt(objstring.substring(0,objstring.indexOf(",")));
		objstring = objstring.substring(objstring.indexOf(",")+1);
		int y1 = Integer.parseInt(objstring.substring(0,objstring.indexOf(",")));
		objstring = objstring.substring(objstring.indexOf(",")+1);
		int height = Integer.parseInt(objstring.substring(0,objstring.indexOf(",")));
		objstring = objstring.substring(objstring.indexOf(",")+1);
		int width = Integer.parseInt(objstring.substring(0,objstring.indexOf(",")));
		objstring = objstring.substring(objstring.indexOf(",")+1);
		int x2 = Integer.parseInt(objstring.substring(0,objstring.indexOf(",")));
		objstring = objstring.substring(objstring.indexOf(",")+1);
		int y2 = Integer.parseInt(objstring);
		w = new WarpWall(x1, y1, height, width, new Point(x2, y2));
		return w;
	}
	private static Bomb bombFromString(String objstring) {
		//mass,x,y,movable,color[r,g,b],velocity[x,y]
		Bomb b;
		objstring = objstring.replace("(", "").replace(")", "");
		String type = objstring.substring(0,objstring.indexOf(","));
		objstring = objstring.substring(objstring.indexOf(",")+1);
		double x = Double.parseDouble(objstring.substring(0,objstring.indexOf(",")));
		objstring = objstring.substring(objstring.indexOf(",")+1);
		double y = Double.parseDouble(objstring.substring(0,objstring.indexOf(",")));
		objstring = objstring.substring(objstring.indexOf(",")+1);
		double special = Double.parseDouble(objstring.substring(0,objstring.indexOf(",")));
		objstring = objstring.substring(objstring.indexOf(",")+1);
		int particles = Integer.parseInt(objstring);
		if(type.contains("proximity"))
			b = new ProximityBomb(x,y,special,particles);
		//else if (type.contains("time"))
		else
			b = new ProximityBomb(x,y,special,particles);
		return b;
	}
	private static Velocity velocityFromString(String str) {
		str = str.replace("[", "").replace("]", "").replace("velocity", "");
		String[] vel = str.split(",");
		return Velocity.fromXY(Double.parseDouble(vel[0]), Double.parseDouble(vel[1]));
	}
	private static Color colorFromString(String str) {
		str = str.replace("[", "").replace("]", "").replace("color", "");
		String[] rgb = str.split(",");
		return new Color(Integer.parseInt(rgb[0]),Integer.parseInt(rgb[1]),Integer.parseInt(rgb[2]));
	}
}
