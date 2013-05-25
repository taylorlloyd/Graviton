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
import game.Velocity;

public class xVelEdit extends EditVal implements KeyListener{
	JTextField xVelField;
	JLabel label;
	public xVelEdit() {
		setLayout(new GridLayout(1,2));
		label = new JLabel("xVel:");
		xVelField = new JTextField("0");
		xVelField.addKeyListener(this);
		add(label);
		add(xVelField);
	}
	
	public void load(EditorObject eo) {
		super.load(eo);
		xVelField.setText(""+((EditorGravitable)eo).getVelocity().getX());
	}

	@Override
	public void keyPressed(KeyEvent k) {
		if(k.getKeyCode() == k.VK_ENTER) {
			try{((EditorGravitable)model).setVelocity(Velocity.fromXY(Double.parseDouble(xVelField.getText()),((EditorGravitable)model).getVelocity().getY()));}catch(Exception e) {
				xVelField.setText(""+((EditorGravitable)model).getVelocity().getX());
			}
			EditorWindow.pane.drawUpdate();
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {}

	@Override
	public void keyTyped(KeyEvent arg0) {}
}
