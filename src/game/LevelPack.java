package game;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


public class LevelPack {
	private String[] levels;
	String required;
	String name;
	private LevelPack() {}
	public LevelPack(String name) throws IOException{
		this.name = name;
		String[] pack = DataManager.readFileAsArray(name+".level");
		ArrayList<String> lvls = new ArrayList<String>();
		int i=0;
		for(String s : pack) {
			if(s.startsWith("depends:")||s.startsWith("#"))
				continue;
			lvls.add(s+("index="+i+";pack="+name+";"));
			i++;
		}
		levels = lvls.toArray(new String[lvls.size()]);
		if(levels.length == 0)
			throw new IOException("Levels failed to load");
		
	}
	public String getLevel(int index) {
		return levels[index];
	}
	public int getLevelCount() {
		return levels.length;
	}
	public boolean playable(int level) {
		return true;
		//return ((GameState.completed(name)) || ((GameState.current.equals(name)) && GameState.maxlevel>=level)) && playable();
	}
	public boolean playable() {
		return true;
		//return required==null || GameState.completed(required);
	}
}
