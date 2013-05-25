package game;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Set;

public class GameState {
	private static final int COMPLETE = 1;
	private static final int BEGINNER = 2;
	private static final int WITHTIME = 4;
	private static boolean autosave=true;
	private static boolean loaded=false;
	private static HashMap<String, HashMap<Integer, int[]>> savedata;
	public static void loadData() {
		loaded=true;
		savedata = new HashMap<String, HashMap<Integer, int []>>();
		try {
			FileInputStream fis = new FileInputStream(DataManager.findAbsolute("save.dat"));
			while(fis.available()>0) {
			int len = fis.read();
			byte[] str = new byte[len];
			fis.read(str);
			String name = new String(str);
			System.out.println("Loading save for levelpack "+name);
			int levels = fis.read();
			HashMap<Integer, int[]> leveldata = new HashMap<Integer, int[]>();
			for(int i=0;i<levels;i++) {
				byte[] time = new byte[4];
				fis.read(time);
				int seconds = byteArrayToInt(time);
				int flags = fis.read();
				leveldata.put(i, new int[]{seconds, flags});
			}
			savedata.put(name,leveldata);
			}
			fis.close();
		} catch (Exception e) {
			System.out.println("Savedata did not load, blank data loaded.");
			
		}
	}
	public static void saveData() {
		if(!loaded) loadData();
		try {
			FileOutputStream fos = new FileOutputStream("Data/save.dat");
			for(String name : savedata.keySet()) {
				fos.write(name.length());
				fos.write(name.getBytes());
				fos.write(savedata.get(name).entrySet().size());//number of levels in pack
				for(int i=0;i<savedata.get(name).entrySet().size();i++) {
					fos.write(intToByteArray(savedata.get(name).get(i)[0]));
					fos.write(savedata.get(name).get(i)[1]);
				}
			}
			fos.flush();
			fos.close();
			System.out.println("Data Saved");
		} catch (Exception e) {
			//Warn user about lack of save data...
			System.out.println("Save failed.");
		}
		
		
	}
	protected static final byte[] intToByteArray(int value) {
        return new byte[] {
        		(byte)(value >>> 24),
                (byte)(value >>> 16),
                (byte)(value >>> 8),
                (byte)value};
	}
	protected static final int byteArrayToInt(byte [] b) {
        return (b[0] << 24)
                + ((b[1] & 0xFF) << 16)
                + ((b[2] & 0xFF) << 8)
                + (b[3] & 0xFF);
	}
	public static int highestCompleteLevel(String pack) {
		int i=0;
		while(isSuccess(pack,i))
			i++;
		return i--;
	}
	public static boolean isCompleted(String pack, int level) {
		if(!loaded) loadData();
		return (level(pack,level)[1] & COMPLETE) != 0;
	}
	public static boolean isPlayable(String pack, int level) {
		return (level <= highestCompleteLevel(pack)) || ("true".equals(SettingsManager.get("alllevels")));
	}
	public static boolean isSuccess(String pack, int level) {
		if(!loaded) loadData();
		return (level(pack,level)[1] & WITHTIME) != 0;
	}
	public static boolean isBeginner(String pack, int level) {
		if(!loaded) loadData();
		return (level(pack,level)[1] & BEGINNER) != 0;
	}
	public static void setCompleted(String pack, int level, boolean newval) {
		if(isCompleted(pack,level) && !newval)
			level(pack,level)[1] -= COMPLETE;
		else if(!isCompleted(pack,level) && newval)
			level(pack,level)[1] += COMPLETE;
		autosave();
	}
	public static void setBeginner(String pack, int level, boolean newval) {
		if(isBeginner(pack,level) && !newval)
			level(pack,level)[1] -= BEGINNER;
		else if(!isBeginner(pack,level) && newval)
			level(pack,level)[1] += BEGINNER;
		autosave();
	}
	public static void setSuccess(String pack, int level, boolean newval) {
		if(isSuccess(pack,level) && !newval)
			level(pack,level)[1] -= WITHTIME;
		else if(!isSuccess(pack,level) && newval)
			level(pack,level)[1] += WITHTIME;
		autosave();
	}
	public static int getTime(String pack, int level) {
		return level(pack, level)[0];
	}
	public static void setTime(String pack, int level, int time) {
		level(pack, level)[0]=time;
		autosave();
	}
	private static int[] level(HashMap<Integer, int[]> pack, int level) {
		if(!loaded) loadData();
		//If there are insufficient entries to read that level, put them in.
		if(pack.size()<=level) {
			for(int i=pack.size();i<=level;i++) {
				int[] blank = new int[] {0,0};
				pack.put(i, blank);
			}
			autosave();
		}
		return pack.get(level);
		
	}
	private static int[] level(String pack, int level) {
		if(!loaded) loadData();
		return level(levelPack(pack),level);
	}
	private static HashMap<Integer, int[]> levelPack(String name) {
		if(!loaded) loadData();
		HashMap<Integer, int[]> pack = savedata.get(name);
		if(pack != null) return pack;
		pack = new HashMap<Integer,int[]>();
		savedata.put(name, pack);
		autosave();
		return pack;
	}
	private static void autosave() {
		if(autosave) saveData();
	}
	public static void setAutosave(boolean save) {
		autosave = save;
	}
	public static boolean getAutosave() {
		return autosave == true;
	}
}