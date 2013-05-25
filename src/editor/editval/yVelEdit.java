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

public class yVelEdit extends EditVal implements KeyListener{
	JTextField yVelField;
	JLabel label;
	public yVelEdit() {
		setLayout(new GridLayout(1,2));
		label = new JLabel("yVel:");
		yVelField = new JTextField("0");
		yVelField.addKeyListener(this);
		add(label);
		add(yVelField);
	}
	
	public void load(EditorObject eo) {
		super.load(eo);
		yVelField.setText(""+((EditorGravitable)eo).getVelocity().getY());
	}

	@Override
	public void keyPressed(KeyEvent k) {
		if(k.getKeyCode() == k.VK_ENTER) {
			try{((EditorGravitable)model).setVelocity(Velocity.fromXY(((EditorGravitable)model).getVelocity().getX(),Double.parseDouble(yVelField.getText())));}catch(Exception e) {
				yVelField.setText(""+((EditorGravitable)model).getVelocity().getY());
			}
			EditorWindow.pane.drawUpdate();
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {}

	@Override
	public void keyTyped(KeyEvent arg0) {}
}
