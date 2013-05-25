package editor;

import javax.swing.JPanel;

public class EditVal extends JPanel {
	protected EditorObject model;
	public void load(EditorObject eo) {
		model = eo;
	}
}
