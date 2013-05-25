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

public class xPathEdit extends EditVal implements KeyListener{
	JTextField xPathField;
	JLabel label;
	PathGravitable pg;
	public xPathEdit() {
		setLayout(new GridLayout(1,2));
		label = new JLabel("xPath:");
		xPathField = new JTextField("0");
		xPathField.addKeyListener(this);
		add(label);
		add(xPathField);
	}
	
	public void load(EditorObject eo) {
		super.load(eo);
		pg = (PathGravitable) ((EditorGravitable)eo).model;
		xPathField.setText(""+pg.end.x);
	}

	@Override
	public void keyPressed(KeyEvent k) {
		if(k.getKeyCode() == k.VK_ENTER) {
			try{pg.end.x=Integer.parseInt(xPathField.getText());}catch(Exception e) {
				xPathField.setText(""+pg.end.x);
			}
			EditorWindow.pane.drawUpdate();
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {}

	@Override
	public void keyTyped(KeyEvent arg0) {}
}
