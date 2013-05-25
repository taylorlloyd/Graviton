package editor.editval;

import editor.EditorGravitable;
import editor.EditorObject;
import editor.EditorWall;
import game.WarpWall;
import gravitable.Bomb;
import gravitable.BombFragment;

public class xOutEdit extends textEdit {

	@Override
	public String getValue(EditorObject eo) {
		return ""+((WarpWall)((EditorWall)eo).model).endPoint.x;
	}

	@Override
	public String labelText() {
		return "xOut:";
	}

	@Override
	public void setValue(String s, EditorObject eo) throws Exception {
		((WarpWall)((EditorWall)eo).model).endPoint.x = Integer.parseInt(s);
	}

}
