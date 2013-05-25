package game;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;


public class LevelLoader {
	HashMap<String, LevelPack> levelpacks;
	ArrayList<String> packnames;
	private static LevelLoader instance;
	public LevelLoader() {
		levelpacks = new HashMap<String, LevelPack>();
		packnames = new ArrayList<String>();
		System.out.println("Levelpack Version: "+ DataManager.readFile("version"));
		String[] levels = DataManager.readFile("levellist").split("\n");
		for (String levelname : levels) {
			System.out.println("Loading Levelpack "+levelname);
			try{
				levelpacks.put(levelname, new LevelPack(levelname));
				packnames.add(levelname);
			} catch (Exception e) {System.out.println("Levelpack "+ levelname + " failed to load");}
		}
		instance = this;
	}
	public static LevelLoader getInstance() {
		if(instance == null)
			return new LevelLoader();
		return instance;
	}

}
