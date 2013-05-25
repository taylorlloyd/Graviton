package editor.editval;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBox;
import javax.swing.JLabel;

import editor.EditVal;
import editor.EditorGravitable;
import editor.EditorObject;
import editor.EditorWall;

public class PopableEdit extends EditVal implements ActionListener{

	JCheckBox movableCheck;
	JLabel label;
	EditorWall model;
	public PopableEdit() {
		label = new JLabel("popable:");
		movableCheck = new JCheckBox();
		movableCheck.addActionListener(this);
		setLayout(new GridLayout(1,2));
		add(label);
		add(movableCheck);
	}
	public void load(EditorObject eo) {
		model = (EditorWall)eo;
		movableCheck.setSelected(model.type.contains("pop"));
	}
	@Override
	public void actionPerformed(ActionEvent ae) {
		model.type = movableCheck.isSelected() ? "pop" : "force";
	}
	
}
