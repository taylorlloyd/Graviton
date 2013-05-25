package editor.editval;

import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JLabel;
import javax.swing.JTextField;

import editor.EditVal;
import editor.EditorObject;
import editor.EditorWall;
import editor.EditorWindow;

public class HeightEdit extends EditVal implements KeyListener{
	JTextField heightField;
	JLabel label;
	public HeightEdit() {
		setLayout(new GridLayout(1,2));
		label = new JLabel("height:");
		heightField = new JTextField("0");
		heightField.addKeyListener(this);
		add(label);
		add(heightField);
	}
	
	public void load(EditorObject eo) {
		super.load(eo);
		heightField.setText(""+((EditorWall)eo).getRect().height);
	}

	@Override
	public void keyPressed(KeyEvent k) {
		if(k.getKeyCode() == k.VK_ENTER) {
			try{((EditorWall)model).getRect().height = (Integer.parseInt(heightField.getText()));}catch(Exception e) {
				heightField.setText(""+((EditorWall)model).getRect().height);
			}
			EditorWindow.pane.drawUpdate();
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {}

	@Override
	public void keyTyped(KeyEvent arg0) {}
}
