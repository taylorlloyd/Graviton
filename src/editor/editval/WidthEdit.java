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

public class WidthEdit extends EditVal implements KeyListener{
	JTextField widthField;
	JLabel label;
	public WidthEdit() {
		setLayout(new GridLayout(1,2));
		label = new JLabel("width:");
		widthField = new JTextField("0");
		widthField.addKeyListener(this);
		add(label);
		add(widthField);
	}
	
	public void load(EditorObject eo) {
		super.load(eo);
		widthField.setText(""+((EditorWall)eo).getRect().width);
	}

	@Override
	public void keyPressed(KeyEvent k) {
		if(k.getKeyCode() == k.VK_ENTER) {
			try{((EditorWall)model).getRect().width = (Integer.parseInt(widthField.getText()));}catch(Exception e) {
				widthField.setText(""+((EditorWall)model).getRect().width);
			}
			EditorWindow.pane.drawUpdate();
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {}

	@Override
	public void keyTyped(KeyEvent arg0) {}
}
