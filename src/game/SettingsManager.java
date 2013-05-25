package game;

import java.util.HashMap;

public class SettingsManager {
	private static HashMap<String, String> settings;
	private static final String[] disallowed = {"tracePlayer","followCam", "gravMouse", "alllevels", "debug","superplayer"};//development settings
	
	private static void loadSettings() {
		settings = new HashMap<String, String>();
		try {
			String[] setstr = DataManager.readFileAsArray("settings.dat");
			for(String s : setstr) {
				settings.put(s.substring(0, s.indexOf("=")), s.substring(s.indexOf("=")+1));
			}
			if(setstr.length == 0) throw new Exception("No Settings.dat");
		} catch(Exception e) {
			loadDefaults();
		}
	}
	private static boolean isAllowed(String key) {

		if("1337hack".equals(settings.get("hackFeatures"))) return true;
		for (String s : disallowed)
			if(s.equals(key)) return false;
		return true;
	}
	private static void loadDefaults() {
		settings.put("debug","false");
		settings.put("fullscreen", "false");
		settings.put("fps", "70");
		settings.put("beginner", "true");
		saveSettings();
	}
	private static void loadIfNeeded() {
		if(settings == null) loadSettings();
	}
	public static String get(String key) {
		loadIfNeeded();
		if(!isAllowed(key)) {
			return "";
		}
		if(settings.containsKey(key))
			return settings.get(key);
		return "";
	}
	public static void put(String key, String value) {
		settings.put(key, value);
		saveSettings();
	}
	public static void saveSettings() {
		String[] data = new String[settings.size()];
		String[] keys = settings.keySet().toArray(new String[0]);
		for(int i=0;i<data.length;i++) {
			data[i] = keys[i]+"="+settings.get(keys[i]);
		}
		DataManager.writeFile("settings.dat", data);
	}
}
