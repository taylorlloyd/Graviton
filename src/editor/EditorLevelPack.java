package editor;

import game.DataManager;
import game.GameProperties;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;

public class EditorLevelPack extends DefaultTableModel{
	private ArrayList<String> levels = new ArrayList<String>();
	public String location = null;
	public String getLevel(int i) {
		return levels.get(i);
	}
	public void setLevel(String s, int i) {
		levels.remove(i);
		levels.add(i, s);
		fireTableRowsUpdated(0, getRowCount());
	}
	public int addLevel(String s) {
		levels.add(s);
		fireTableRowsUpdated(0, getRowCount());
		return levels.indexOf(s);
	}
	public void movelevel(int i1, int i2) {
		String level = levels.get(i1);
		levels.remove(i1);
		levels.add(i2, level);
		fireTableRowsUpdated(0, getRowCount());
	}
	public EditorLevelPack(String file) {
		location = file;
		String[] levels = DataManager.readFileAsArray(file);
		for(String s : levels) {
			if(forLevel(s).name == null)
				continue;
			this.levels.add(s);
		}
		fireTableRowsUpdated(0, getRowCount());
	}
	public EditorLevelPack() {
		addLevel("name=default;background=default.png");
	}
	public GameProperties forLevel(int i) {
		return forLevel(levels.get(i));
	}
	public GameProperties forLevel(String level) {
		return GameProperties.parseMeta(level);
	}
	public void saveTo(String file) {
		try {
			PrintWriter pw = new PrintWriter(new FileWriter(new File("Levels/"+file)));
			for(String s : levels)
				pw.println(s);
			pw.flush();
			pw.close();
			location = file;
			System.out.println("File Saved.");
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Error during save.");
			location = null;
		}
		//AuthLevel.main(new String[0]);
		//System.out.println("Levels signed with alpha key.");
	}
	public void save() {
		if(location != null)
			saveTo(location);
		else {
			String newLoc = JOptionPane.showInputDialog(null, "Name of the Level Pack?", "Error - Not yet Saved", JOptionPane.ERROR_MESSAGE);
			saveTo(newLoc.toLowerCase()+".level");
		}
	}
	@Override
	public int getColumnCount() {
		return 2;
	}
	@Override
	public int getRowCount() {
		if(levels == null) return 0;
		return levels.size();
	}
	@Override
	public Object getValueAt(int row, int col) {
		if(col==1)
			return forLevel(row).name;
		if(col==0)
			return row+1;
		return null;
	}
}
