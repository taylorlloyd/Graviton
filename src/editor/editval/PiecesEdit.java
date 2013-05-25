package editor.editval;

import editor.EditorGravitable;
import editor.EditorObject;
import gravitable.Bomb;
import gravitable.BombFragment;

public class PiecesEdit extends textEdit {

	@Override
	public String getValue(EditorObject eo) {
		return ""+((Bomb)((EditorGravitable)eo).model).bf.length;
	}

	@Override
	public String labelText() {
		return "Pieces:";
	}

	@Override
	public void setValue(String s, EditorObject eo) throws Exception {
		((Bomb)((EditorGravitable)eo).model).bf = new BombFragment[Integer.parseInt(s)];
	}

}
