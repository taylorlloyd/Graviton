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
import gravitable.PathGravitable;

public abstract class textEdit extends EditVal implements KeyListener{
	JTextField inputField;
	JLabel label;
	public textEdit() {
		setLayout(new GridLayout(1,2));
		label = new JLabel(labelText());
		inputField = new JTextField("0");
		inputField.addKeyListener(this);
		add(label);
		add(inputField);
	}
	
	public void load(EditorObject eo) {
		super.load(eo);
		inputField.setText(getValue(eo));
	}

	@Override
	public void keyPressed(KeyEvent k) {
		if(k.getKeyCode() == k.VK_ENTER) {
			try{
				setValue(inputField.getText(),model);
			}catch(Exception e) {
				inputField.setText(getValue(model));
			}
			EditorWindow.pane.drawUpdate();
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {}

	@Override
	public void keyTyped(KeyEvent arg0) {}
	public abstract String labelText();
	public abstract String getValue(EditorObject eo);
	public abstract void setValue(String s, EditorObject eo) throws Exception;
	
}
