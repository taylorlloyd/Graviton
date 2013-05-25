package editor;

import editor.editval.ColorEdit;
import editor.editval.HeightEdit;
import editor.editval.MovableEdit;
import editor.editval.PiecesEdit;
import editor.editval.PopableEdit;
import editor.editval.ProximityEdit;
import editor.editval.WidthEdit;
import editor.editval.massEdit;
import editor.editval.speedEdit;
import editor.editval.xLocEdit;
import editor.editval.xOutEdit;
import editor.editval.xPathEdit;
import editor.editval.xVelEdit;
import editor.editval.yLocEdit;
import editor.editval.yOutEdit;
import editor.editval.yPathEdit;
import editor.editval.yVelEdit;
import game.ForceWall;
import game.GravityPane;
import game.Velocity;
import game.Wall;
import game.WarpWall;
import gravitable.CollectorItem;
import gravitable.Endpoint;
import gravitable.Gravitable;
import gravitable.Launcher;
import gravitable.PathGravitable;
import gravitable.ProximityBomb;
import gravitable.ResetGravitable;
import gravitable.SimpleGravitable;
import gravitable.Turret;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JToggleButton;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class EditorWindow extends JFrame implements ActionListener, KeyListener, MouseListener, ListSelectionListener{
JPanel rightBarPnl;
JPanel leftBarPnl;
JButton saveBtn;
JButton loadBtn;
JTextArea nameTxt;
JButton newLvlBtn;
JButton delLvlBtn;
JButton dependsBtn;
JLabel dependsLbl;
JPanel playbackPnl;
JButton playBtn;
JButton pauseBtn;
JButton resetBtn;
JToggleButton editBtn;
JToggleButton objectBtn;
JToggleButton itemBtn;
JToggleButton resetObjectBtn;
JToggleButton startBtn;
JToggleButton endBtn;
JToggleButton pathBtn;
JToggleButton wallBtn;
JToggleButton proxBombBtn;
JToggleButton warpWallBtn;
JToggleButton turretBtn;
JButton backgroundBtn;
JLabel timeLbl1;
JCheckBox timeChk;
JLabel timeLbl2;
JTextArea timeTxt;
JPanel contextPnl;
JLabel typeLbl;
JComboBox typeCmbBox;
JLabel xLocLbl;
JTextArea xLocTxt;
JLabel yLocLbl;
JTextArea yLocTxt;
JLabel massLbl;
JTextArea massTxt;
JLabel colorLbl;
JLabel colorEgLbl;
JButton colorChooseBtn;
JLabel movableLbl;
JCheckBox movableChk;
JLabel xVelLbl;
JTextArea xVelTxt;
JLabel yVelLbl;
JTextArea yVelTxt;
JButton deleteGravBtn;
JTable levelTbl;
public static EditorGamePane pane;
EditorLevelPack levels;
HashMap<String, EditVal> editvals;
boolean levelChanged = false;
int mode=0;
int selectedLevel=0;
String backgroundName="default.png";
Color selectedColor=Color.black;
public final int EDIT_MODE=0;
public final int OBJECT_MODE=1;
public final int RESET_MODE=2;
public final int START_MODE=3;
public final int END_MODE=4;
public final int ITEM_MODE=5;
public final int WALL_MODE=10;
public final int PATH_MODE=11;
public final int PROX_MODE=12;
public final int WARP_WALL_MODE=13;
public final int TURRET_MODE=14;

public EditorWindow() {
	super("Editor");
	levels = new EditorLevelPack();
	setLayout(new GridBagLayout());
	rightBarPnl = new JPanel();
	rightBarPnl.setLayout(new GridBagLayout());
	add(rightBarPnl, gbc(0,0,1,1,gbc.VERTICAL,0.3,1));
	leftBarPnl = new JPanel();
	//add(new JPanel(/*Spacer*/),gbc(1,0,1,1,gbc.BOTH,2,1));
	leftBarPnl.setLayout(new GridBagLayout());
	add(leftBarPnl, gbc(2,0,1,1,gbc.VERTICAL,0.3,1));
	pane = new EditorGamePane();
	add(pane.getPanel(), gbc(1,0,1,1,gbc.BOTH,1,1));
	saveBtn = new JButton("Save");
	saveBtn.addActionListener(this);
	rightBarPnl.add(saveBtn, gbc(0,0,1,1,gbc.HORIZONTAL,1,1));
	loadBtn = new JButton("Load");
	loadBtn.addActionListener(this);
	rightBarPnl.add(loadBtn, gbc(1,0,1,1,gbc.HORIZONTAL,1,1));
	nameTxt = new JTextArea("LevelName");
	nameTxt.addKeyListener(this);
	rightBarPnl.add(nameTxt, gbc(0,1,2,1,gbc.HORIZONTAL,1,1));
	levelTbl = new JTable(levels);
	levelTbl.setRowSelectionInterval(0,0);
	levelTbl.getSelectionModel().addListSelectionListener(this);
	rightBarPnl.add(levelTbl, gbc(0,2,2,10,gbc.BOTH,4,4));
	newLvlBtn = new JButton("New");
	newLvlBtn.addActionListener(this);
	rightBarPnl.add(newLvlBtn, gbc(0,12,1,1,gbc.HORIZONTAL,1,1));
	delLvlBtn = new JButton("Delete");
	delLvlBtn.addActionListener(this);
	rightBarPnl.add(delLvlBtn, gbc(1,12,1,1,gbc.HORIZONTAL,1,1));
	dependsBtn = new JButton("Depends:");
	dependsBtn.addActionListener(this);
	rightBarPnl.add(dependsBtn, gbc(0,13,2,1,gbc.HORIZONTAL,1,1));
	dependsLbl = new JLabel("None");
	rightBarPnl.add(dependsLbl, gbc(0,14,2,1,gbc.HORIZONTAL,1,1));
	playbackPnl = new JPanel();
	playbackPnl.setLayout(new GridLayout(1,3));
	playBtn = new JButton(/*Icon*/"Play");
	playBtn.addActionListener(this);
	playbackPnl.add(playBtn);
	pauseBtn = new JButton(/*Icon*/"Pause");
	pauseBtn.addActionListener(this);
	playbackPnl.add(pauseBtn);
	resetBtn = new JButton(/*Icon*/"Reset");
	resetBtn.addActionListener(this);
	playbackPnl.add(resetBtn);
	rightBarPnl.add(playbackPnl, gbc(0,15,2,2,gbc.HORIZONTAL,1,1));
	editBtn = new JToggleButton("Edit");
	editBtn.addActionListener(this);
	editBtn.setSelected(true);
	leftBarPnl.add(editBtn, gbc(0,0,1,1,gbc.HORIZONTAL,1,1));
	objectBtn = new JToggleButton("Object");
	objectBtn.addActionListener(this);
	leftBarPnl.add(objectBtn, gbc(1,0,1,1,gbc.HORIZONTAL,1,1));
	itemBtn = new JToggleButton("Item");
	itemBtn.addActionListener(this);
	leftBarPnl.add(itemBtn, gbc(0,1,1,1,gbc.HORIZONTAL,1,1));
	resetObjectBtn = new JToggleButton("Reset");
	resetObjectBtn.addActionListener(this);
	leftBarPnl.add(resetObjectBtn, gbc(1,1,1,1,gbc.HORIZONTAL,1,1));
	startBtn = new JToggleButton("Start");
	startBtn.addActionListener(this);
	leftBarPnl.add(startBtn, gbc(0,2,1,1,gbc.HORIZONTAL,1,1));
	endBtn = new JToggleButton("End");
	endBtn.addActionListener(this);
	leftBarPnl.add(endBtn, gbc(1,2,1,1,gbc.HORIZONTAL,1,1));
	wallBtn = new JToggleButton("Wall");
	wallBtn.addActionListener(this);
	leftBarPnl.add(wallBtn, gbc(0,3,1,1,gbc.HORIZONTAL,1,1));
	pathBtn = new JToggleButton("Path");
	pathBtn.addActionListener(this);
	leftBarPnl.add(pathBtn, gbc(1,3,1,1,gbc.HORIZONTAL,1,1));
	proxBombBtn = new JToggleButton("Proximity");
	proxBombBtn.addActionListener(this);
	leftBarPnl.add(proxBombBtn, gbc(0,4,1,1,gbc.HORIZONTAL,1,1));
	warpWallBtn = new JToggleButton("Warper");
	warpWallBtn.addActionListener(this);
	leftBarPnl.add(warpWallBtn, gbc(1,4,1,1,gbc.HORIZONTAL,1,1));
	turretBtn = new JToggleButton("Turret");
	turretBtn.addActionListener(this);
	leftBarPnl.add(turretBtn, gbc(0,5,1,1,gbc.HORIZONTAL,1,1));
	backgroundBtn = new JButton("Background");
	backgroundBtn.addActionListener(this);
	leftBarPnl.add(backgroundBtn, gbc(0,8,2,1,gbc.HORIZONTAL,1,1));
	timeLbl1 = new JLabel("Timed:");
	leftBarPnl.add(timeLbl1, gbc(0,9,1,1,gbc.HORIZONTAL,1,1));
	timeChk = new JCheckBox();
	timeChk.setSelected(true);
	leftBarPnl.add(timeChk, gbc(1,9,1,1,gbc.HORIZONTAL,1,1));
	timeChk.addActionListener(this);
	timeLbl2 = new JLabel("Time:");
	leftBarPnl.add(timeLbl2, gbc(0,10,1,1,gbc.HORIZONTAL,1,1));
	timeTxt = new JTextArea("1:00");
	timeTxt.addKeyListener(this);
	leftBarPnl.add(timeTxt, gbc(1,10,1,1,gbc.HORIZONTAL,1,1));
	contextPnl = new JPanel();
	contextPnl.setLayout(new GridBagLayout());
	leftBarPnl.add(contextPnl, gbc(0,11,2,1,gbc.BOTH,1,1));
	typeLbl = new JLabel("Type:");
	contextPnl.add(typeLbl, gbc(0,0,1,1,gbc.HORIZONTAL,1,1));
	typeCmbBox = new JComboBox(new String[]{"object","reset","item","start","end","path","wall"});
	typeCmbBox.setEditable(false);
	contextPnl.add(typeCmbBox, gbc(1,0,1,1,gbc.HORIZONTAL,1,1));
	deleteGravBtn = new JButton("Delete");
	deleteGravBtn.addActionListener(this);
	//contextPnl.add(deleteGravBtn, gbc(0,8,2,1,gbc.HORIZONTAL,1,1));
	editvals = new HashMap<String, EditVal> ();
	editvals.put("xLoc", new xLocEdit());
	editvals.put("yLoc", new yLocEdit());
	xVelEdit xv = new xVelEdit();
	yVelEdit yv = new yVelEdit();
	editvals.put("xVel", xv);
	editvals.put("yVel", yv);
	editvals.put("height", new HeightEdit());
	editvals.put("width", new WidthEdit());
	editvals.put("movable", new MovableEdit(xv, yv));
	editvals.put("color", new ColorEdit());
	editvals.put("xPath", new xPathEdit());
	editvals.put("yPath", new yPathEdit());
	editvals.put("speed", new speedEdit());
	editvals.put("mass", new massEdit());
	editvals.put("pop", new PopableEdit());
	editvals.put("proximity", new ProximityEdit());
	editvals.put("pieces", new PiecesEdit());
	editvals.put("xOut", new xOutEdit());
	editvals.put("yOut", new yOutEdit());
	pane.getPanel().addMouseListener(this);
	pack();
	setVisible(true);
}
boolean contextload = false;
@Override
public void actionPerformed(ActionEvent e) {
	if(e.getSource().equals(playBtn))
		{pane.updateString(generateString());pane.reset();pane.playGravity();setMode(6);enableModes(false);}
	else if(e.getSource().equals(pauseBtn))
		pane.pauseGravity();
	else if(e.getSource().equals(resetBtn))
		{pane.reset();enableModes(true);}
	else if(e.getSource().equals(editBtn))
		{setMode(EDIT_MODE);levelChanged = true;}
	else if(e.getSource().equals(objectBtn))
		{setMode(OBJECT_MODE);levelChanged = true;}
	else if(e.getSource().equals(itemBtn))
		{setMode(ITEM_MODE);levelChanged = true;}
	else if(e.getSource().equals(startBtn))
		{setMode(START_MODE);levelChanged = true;}
	else if(e.getSource().equals(endBtn))
		{setMode(END_MODE);levelChanged = true;}
	else if(e.getSource().equals(resetObjectBtn))
		{setMode(RESET_MODE);levelChanged = true;}
	else if(e.getSource().equals(pathBtn))
	{setMode(PATH_MODE);levelChanged = true;}
	else if(e.getSource().equals(wallBtn))
	{setMode(WALL_MODE);levelChanged = true;}
	else if(e.getSource().equals(proxBombBtn))
	{setMode(PROX_MODE);levelChanged = true;}
	else if(e.getSource().equals(warpWallBtn))
	{setMode(WARP_WALL_MODE);levelChanged = true;}
	else if(e.getSource().equals(turretBtn))
	{setMode(TURRET_MODE);levelChanged = true;}
	else if(e.getSource().equals(timeChk))
		{setTimed(timeChk.isSelected(),0);levelChanged = true;}
	else if(e.getSource().equals(deleteGravBtn)) {
		pane.getPanel().objects.remove(curSelected);
		pane.getPanel().walls.remove(curSelected);
		loadContext(null);
		pane.updatePaintArray();
		pane.drawUpdate();
	} else if(e.getSource().equals(saveBtn)) {
		if(levelChanged) {
			unloadLevel();
			loadLevel(selectedLevel);
		}
		levels.save();
	} else if(e.getSource().equals(loadBtn)) {
		if(!unloadLevel())
			return;
		File f = new File("Levels/"+JOptionPane.showInputDialog(this, "Pack to load:", "Load which pack?", JOptionPane.QUESTION_MESSAGE)+".level");
		if(!f.exists())
			return;
		levels = new EditorLevelPack(f.getName());
		levelTbl.setModel(levels);
		levelTbl.setRowSelectionInterval(0,0);
	} else if(e.getSource().equals(newLvlBtn)) {
		levels.addLevel("default");
	} else if(e.getSource().equals(deleteGravBtn)) {
		((EditorGravityPane)pane.getPanel()).remove(curSelected);
		pane.drawUpdate();
	}
	
	
}
public void setMode(int mode) {
	this.mode = mode;
	loadContext(null);
	editBtn.setSelected(false);
	objectBtn.setSelected(false);
	itemBtn.setSelected(false);
	startBtn.setSelected(false);
	endBtn.setSelected(false);
	resetObjectBtn.setSelected(false);
	wallBtn.setSelected(false);
	pathBtn.setSelected(false);
	proxBombBtn.setSelected(false);
	warpWallBtn.setSelected(false);
	turretBtn.setSelected(false);
	if(mode == EDIT_MODE)
		editBtn.setSelected(true);
	else if(mode == OBJECT_MODE)
		objectBtn.setSelected(true);
	else if(mode == ITEM_MODE)
		itemBtn.setSelected(true);
	else if(mode == START_MODE)
		startBtn.setSelected(true);
	else if(mode == END_MODE)
		endBtn.setSelected(true);
	else if(mode == RESET_MODE)
		resetObjectBtn.setSelected(true);
	else if(mode == WALL_MODE)
		wallBtn.setSelected(true);
	else if(mode == PATH_MODE)
		pathBtn.setSelected(true);
	else if(mode == PROX_MODE)
		proxBombBtn.setSelected(true);
	else if(mode == WARP_WALL_MODE)
		warpWallBtn.setSelected(true);
	else if(mode == TURRET_MODE)
		turretBtn.setSelected(true);
}
public void enableModes(boolean b) {
	editBtn.setEnabled(b);
	objectBtn.setEnabled(b);
	itemBtn.setEnabled(b);
	startBtn.setEnabled(b);
	endBtn.setEnabled(b);
	resetObjectBtn.setEnabled(b);
	wallBtn.setEnabled(b);
	pathBtn.setEnabled(b);
	proxBombBtn.setEnabled(b);
	warpWallBtn.setEnabled(b);
	turretBtn.setEnabled(b);
}
GridBagConstraints gbc;
public GridBagConstraints gbc(int gridx, int gridy, int gridwidth, int gridheight, int fill, double weightx, double weighty) {
	if(gbc == null)
		gbc = new GridBagConstraints();
	gbc.gridx=gridx;
	gbc.gridy=gridy;
	gbc.gridwidth=gridwidth;
	gbc.gridheight=gridheight;
	gbc.fill=fill;
	gbc.weightx=weightx;
	gbc.weighty=weighty;
	return gbc;
}
public static void main(String[] args) {
	new EditorWindow();
}

@Override
public void keyPressed(KeyEvent e) {
	if(e.getKeyCode() == KeyEvent.VK_ENTER) {
		e.consume();
		try{
			if(e.getSource().equals(nameTxt))
				levelChanged = true;
			else {
			}
		} catch (Exception e2) {
			loadContext(curSelected);
		}
		pane.drawUpdate();
	}
	
}

@Override
public void keyReleased(KeyEvent e) {
	// TODO Auto-generated method stub
	
}

@Override
public void keyTyped(KeyEvent e) {
	
}

public EditorObject curSelected = null;
public void mouseClicked(MouseEvent me) {
	if(mode == EDIT_MODE) {
		curSelected = pane.objAt(me.getPoint());
		loadContext(curSelected);

	} else if(mode == OBJECT_MODE) {
		pane.getPanel().objects.add(new EditorGravitable(new SimpleGravitable(5,me.getPoint().x,me.getPoint().y,new Velocity(0,0))));
		pane.drawUpdate();
	} else if(mode == RESET_MODE) {
		pane.getPanel().objects.add(new EditorGravitable(new ResetGravitable(5,me.getPoint().x,me.getPoint().y,new Velocity(0,0))));
		pane.drawUpdate();
	} else if(mode == START_MODE) {
		pane.getPanel().objects.add(new EditorGravitable(new Launcher(me.getPoint().x,me.getPoint().y)));
		pane.drawUpdate();
	} else if(mode == END_MODE) {
		pane.getPanel().objects.add(new EditorGravitable(new Endpoint(me.getPoint().x,me.getPoint().y,new Velocity(0,0))));
		pane.drawUpdate();
	} else if(mode == ITEM_MODE) {
		pane.getPanel().objects.add(new EditorGravitable(new CollectorItem(me.getPoint().x,me.getPoint().y)));
		pane.drawUpdate();
	} else if(mode == WALL_MODE) {
		pane.getPanel().walls.add(new EditorWall(new ForceWall(me.getPoint().x, me.getPoint().y, me.getPoint().x+100, me.getPoint().y+100)));
		pane.drawUpdate();
	} else if(mode == PATH_MODE) {
		pane.getPanel().objects.add(new EditorGravitable(new PathGravitable(me.getPoint(), new Point(me.getPoint().x, me.getPoint().y+100),10,1.0)));
		pane.drawUpdate();
	} else if(mode == PROX_MODE) {
		pane.getPanel().objects.add(new EditorGravitable(new ProximityBomb(me.getPoint().x, me.getPoint().y,50,8)));
		pane.drawUpdate();
	} else if(mode == WARP_WALL_MODE) {
		pane.getPanel().walls.add(new EditorWall(new WarpWall(me.getPoint().x, me.getPoint().y, 50, 50, new Point(me.getPoint().x+150, me.getPoint().y))));
		pane.drawUpdate();
	} else if(mode == TURRET_MODE) {
		pane.getPanel().objects.add(new EditorGravitable(new Turret(me.getPoint().x, me.getPoint().y)));
		pane.drawUpdate();
	}
}

private void loadContext(EditorObject eo) {
	contextload=true;
	contextPnl.removeAll();
	contextPnl.add(typeLbl, gbc(0,0,1,1,gbc.HORIZONTAL,1,1));
	contextPnl.add(typeCmbBox,gbc(1,0,1,1,gbc.HORIZONTAL,1,1));
	typeCmbBox.setEnabled(eo != null);
	if(eo != null) {
		int i=1;
		for(String s:eo.getEditArray()) {
			EditVal e = editvals.get(s);
			if(e!= null) {
				e.load(eo);
				contextPnl.add(e,gbc(0,i,2,1,gbc.HORIZONTAL,1,1));
				i++;
			}
		}
		contextPnl.add(deleteGravBtn,gbc(0,i,2,1,gbc.HORIZONTAL,1,1));
	}
	contextPnl.validate();
	rightBarPnl.validate();
	this.validate();
}

private void setMovable(boolean b) {
	xVelTxt.setEnabled(b);
	yVelTxt.setEnabled(b);
}

@Override
public void mouseEntered(MouseEvent arg0) {
	// TODO Auto-generated method stub
	
}

@Override
public void mouseExited(MouseEvent arg0) {
	// TODO Auto-generated method stub
	
}

@Override
public void mousePressed(MouseEvent arg0) {
	// TODO Auto-generated method stub
	
}

@Override
public void mouseReleased(MouseEvent arg0) {
	// TODO Auto-generated method stub
	
}
public void loadLevel(String sel) {
	pane.loadProperties(sel);
	nameTxt.setText(pane.props.name);
	backgroundName = pane.props.imagename;
	setTimed(pane.props.timed,pane.props.timetobeat);
	
}
public void loadLevel(int sel) {
	levelChanged = false;
	selectedLevel = sel;
	loadLevel(levels.getLevel(sel));
}
public void setTimed(boolean timed, int time) {
	timeTxt.setEnabled(timed);
	timeChk.setSelected(timed);
	if(timed) timeTxt.setText(time+"");
}
public String generateString() {
	String lvl = "name="+nameTxt.getText()+";";
	if(timeChk.isSelected())
		try{
		lvl+="time="+Integer.parseInt(timeTxt.getText())+";";
		}catch(Exception e){System.out.println("Time must be an Integer.");}
		for(Gravitable eg : pane.getPanel().objects) {
			if(eg instanceof EditorGravitable)
				lvl+=((EditorGravitable)eg).getGeneratorString()+";";
		}
		for(Wall w : pane.getPanel().walls) {
			lvl+=((EditorWall)w).toString()+";";
		}
		try {
		lvl+="background="+pane.props.imagename+";";
		} catch(Exception e) {
			System.out.println("Level has no background");
		}
	return lvl;
	
}
public ImageIcon iconColor(Color c) {
	Image i = new BufferedImage(20, 20, BufferedImage.TYPE_INT_RGB);
	Graphics g = i.getGraphics();
	g.setColor(c);
	g.fillRect(0, 0, 20, 20);
	return new ImageIcon(i);
}
@Override
public void valueChanged(ListSelectionEvent e) {
	if(e.getValueIsAdjusting())
		return;
	int sel = levelTbl.getSelectedRow();
	if(sel == -1) return;
	if(unloadLevel())
		loadLevel(sel);
}

public boolean unloadLevel() {
	if(pane.getPanel().gravityOn)
	{
		JOptionPane.showMessageDialog(this, "Please reset the currently playing pane", "Error: Play mode active", JOptionPane.ERROR_MESSAGE);
		return false;
	}
	boolean save = false;
	if(levelChanged)
	save = JOptionPane.showConfirmDialog(null, "Save changes to level?","Save level?", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION;
	setMode(6);
	enableModes(true);
	if(save) {
		pane.updateString(generateString());
		levels.setLevel(pane.getLevelString(), selectedLevel);
	}
	pane.clear();
	pane.clear();
	return true;
	
}
}

