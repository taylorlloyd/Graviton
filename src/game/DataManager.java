package game;
import java.awt.Font;
import java.awt.Image;
import java.io.*;
import java.security.spec.AlgorithmParameterSpec;
import java.util.*;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.*;


public class DataManager {
	static String[] paths = new String[] {"Data/","Data/Images/", "Levels/Images/","Levels/"};
	static HashMap<String, Image> images;
	public static File findFile(String name) {
		for(String path : paths) {
			File f = new File(path+name);
			if(f.exists()) return f;
		}
		System.out.println("Couldn't find "+name);
		return null;
	}
	public static InputStream findInput(String name) {
		return findFileInput(name);
	}
	public static InputStream findFileInput(String name) {
		File f = findFile(name);
		if(f != null) {
			try {
				return new FileInputStream(f);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	public static String findAbsolute(String name) {
		File f = findFile(name);
		if(f != null)
			return f.getAbsolutePath();
		return "";
	}
	public static String readFile(String name) {
		String value="";
		try{
			BufferedReader br = new BufferedReader(new InputStreamReader(findInput(name)));
			String s;
			value=br.readLine();
			while((s=br.readLine()) != null) {
				value+="\n"+s;
			}
		} catch (IOException e) {}
		return value;
	}
	public static void writeFile(String file, String[] contents) {
		try{
			PrintWriter pw = new PrintWriter(new FileWriter(findFile(file)));
			for(String s : contents)
				pw.println(s);
			pw.flush();
			pw.close();
		} catch (IOException e) {}
	}
	public static String[] readFileAsArray(String name) {
		ArrayList<String> arr = new ArrayList<String>();
		try{
			BufferedReader br = new BufferedReader(new InputStreamReader(findInput(name)));
			String s;
			arr.add(br.readLine());
			while((s=br.readLine()) != null) {
				arr.add(s);
			}
		} catch (IOException e) {}
		return arr.toArray(new String[arr.size()]);
	}
	public static Font loadFont(String string) {
		Font f;
		try {
		      InputStream is = findInput("text.ttf");
		      f = Font.createFont(Font.TRUETYPE_FONT, is);
		      is.close();
		    } catch (Exception ex) {
		      ex.printStackTrace();
		      System.err.println("Font didn't load");
		      f = new Font("serif", Font.PLAIN, 1);
		    }
		return f;
	}
	public static Image loadImage(String string) {
		if(images == null)
			images = new HashMap<String, Image>();
		Image i = images.get(string);
		if(i != null) return i;
		i = new ImageIcon(findAbsolute(string)).getImage();
		images.put(string,i);
		if(i == null)
			System.out.println("Error loading image " + string);
		return i;
	}
	
}
