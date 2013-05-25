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

public class xLocEdit extends EditVal implements KeyListener{
	JTextField xLocField;
	JLabel label;
	public xLocEdit() {
		setLayout(new GridLayout(1,2));
		label = new JLabel("xLoc:");
		xLocField = new JTextField("0");
		xLocField.addKeyListener(this);
		add(label);
		add(xLocField);
	}
	
	public void load(EditorObject eo) {
		super.load(eo);
		xLocField.setText(""+eo.getX());
	}

	@Override
	public void keyPressed(KeyEvent k) {
		if(k.getKeyCode() == k.VK_ENTER) {
			try{model.setX(Double.parseDouble(xLocField.getText()));
			if(model instanceof EditorGravitable) {
				if(((EditorGravitable)model).model instanceof PathGravitable) {
					((PathGravitable)((EditorGravitable)model).model).start.x = (int) Double.parseDouble(xLocField.getText());
				}
			}
			
			}catch(Exception e) {
				xLocField.setText(""+model.getX());
			}
			EditorWindow.pane.drawUpdate();
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {}

	@Override
	public void keyTyped(KeyEvent arg0) {}
}
