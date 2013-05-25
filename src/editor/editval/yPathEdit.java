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

public class yPathEdit extends EditVal implements KeyListener{
	JTextField yPathField;
	JLabel label;
	PathGravitable pg;
	public yPathEdit() {
		setLayout(new GridLayout(1,2));
		label = new JLabel("yPath:");
		yPathField = new JTextField("0");
		yPathField.addKeyListener(this);
		add(label);
		add(yPathField);
	}
	
	public void load(EditorObject eo) {
		super.load(eo);
		pg = (PathGravitable) ((EditorGravitable)eo).model;
		yPathField.setText(""+pg.end.y);
	}

	@Override
	public void keyPressed(KeyEvent k) {
		if(k.getKeyCode() == k.VK_ENTER) {
			try{pg.end.y=Integer.parseInt(yPathField.getText());}catch(Exception e) {
				yPathField.setText(""+pg.end.y);
			}
			EditorWindow.pane.drawUpdate();
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {}

	@Override
	public void keyTyped(KeyEvent arg0) {}
}
