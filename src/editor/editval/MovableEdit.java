package editor.editval;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBox;
import javax.swing.JLabel;

import editor.EditVal;
import editor.EditorGravitable;
import editor.EditorObject;

public class MovableEdit extends EditVal implements ActionListener{
	xVelEdit x;
	yVelEdit y;
	JCheckBox movableCheck;
	JLabel label;
	EditorGravitable model;
	public MovableEdit(xVelEdit x, yVelEdit y) {
		this.x=x;
		this.y=y;
		label = new JLabel("movable:");
		movableCheck = new JCheckBox();
		movableCheck.addActionListener(this);
		setLayout(new GridLayout(1,2));
		add(label);
		add(movableCheck);
	}
	public void load(EditorObject eo) {
		model = (EditorGravitable)eo;
		movableCheck.setSelected(model.canMove());
		x.xVelField.setEnabled(model.canMove());
		y.yVelField.setEnabled(model.canMove());
	}
	@Override
	public void actionPerformed(ActionEvent ae) {
		model.setMove(movableCheck.isSelected());
		x.xVelField.setEnabled(model.canMove());
		y.yVelField.setEnabled(model.canMove());
	}
	
}
