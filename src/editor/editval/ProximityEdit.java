package editor.editval;

import editor.EditorGravitable;
import editor.EditorObject;
import gravitable.Bomb;
import gravitable.BombFragment;
import gravitable.ProximityBomb;

public class ProximityEdit extends textEdit {

	@Override
	public String getValue(EditorObject eo) {
		return ""+((ProximityBomb)((EditorGravitable)eo).model).triggerDist;
	}

	@Override
	public String labelText() {
		return "Proximity:";
	}

	@Override
	public void setValue(String s, EditorObject eo) throws Exception {
		((ProximityBomb)((EditorGravitable)eo).model).triggerDist = Double.parseDouble(s);
	}

}
