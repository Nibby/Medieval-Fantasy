package hidden.indev0r.core.entity.animation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by MrDeathJockey on 14/12/6.
 */
public class ActionSet {

	List<Action> motionList = new ArrayList<>();

	private int id;

	protected ActionSet(int id) {
		this.id = id;
	}

	protected void add(Action motion) {
		motionList.add(motion);
	}

	public void applyAll(Map<ActionType, Action> motionMap) {
		for (Action motion : motionList) {
			motionMap.put(motion.getActionType(), motion);
		}
	}

	public int getID() {
		return id;
	}
}
