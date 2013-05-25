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

public class yLocEdit extends EditVal implements KeyListener{
	JTextField yLocField;
	JLabel label;
	public yLocEdit() {
		setLayout(new GridLayout(1,2));
		label = new JLabel("yLoc:");
		yLocField = new JTextField("0");
		yLocField.addKeyListener(this);
		add(label);
		add(yLocField);
	}
	
	public void load(EditorObject eo) {
		super.load(eo);
		yLocField.setText(""+eo.getY());
	}

	@Override
	public void keyPressed(KeyEvent k) {
		if(k.getKeyCode() == k.VK_ENTER) {
			try{model.setY(Double.parseDouble(yLocField.getText()));
			if(model instanceof EditorGravitable) {
				if(((EditorGravitable)model).model instanceof PathGravitable) {
					((PathGravitable)((EditorGravitable)model).model).start.y = (int) Double.parseDouble(yLocField.getText());
				}
			}
			}catch(Exception e) {
			yLocField.setText(""+model.getY());
			}
			EditorWindow.pane.drawUpdate();
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {}

	@Override
	public void keyTyped(KeyEvent arg0) {}
}
