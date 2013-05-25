package editor.editval;

import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JLabel;
import javax.swing.JTextField;

import editor.EditVal;
import editor.EditorGravitable;
import editor.EditorObject;
import editor.EditorWindow;

public class massEdit extends EditVal implements KeyListener{
	JTextField xLocField;
	JLabel label;
	public massEdit() {
		setLayout(new GridLayout(1,2));
		label = new JLabel("mass:");
		xLocField = new JTextField("0");
		xLocField.addKeyListener(this);
		add(label);
		add(xLocField);
	}
	
	public void load(EditorObject eo) {
		super.load(eo);
		xLocField.setText(""+((EditorGravitable)model).getMass());
	}

	@Override
	public void keyPressed(KeyEvent k) {
		if(k.getKeyCode() == k.VK_ENTER) {
			try{((EditorGravitable)model).setMass(Integer.parseInt(xLocField.getText()));}catch(Exception e) {
				xLocField.setText(""+((EditorGravitable)model).getMass());
			}
			EditorWindow.pane.drawUpdate();
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {}

	@Override
	public void keyTyped(KeyEvent arg0) {}
}
