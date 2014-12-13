package hidden.indev0r.game.entity.animation;

import hidden.indev0r.game.map.MapDirection;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by MrDeathJockey on 14/12/6.
 */
public class ActionSet {

	List<Action> motionList = new ArrayList<>();

    private long animTickTime = 0;
    private float animAlpha = 0.5f;
    private int animTick = 0;

    private int animTrail = 0;
    private List<MapDirection> animFrameFacing = new ArrayList<>();

	private int id;

	protected ActionSet(int id) {
		this.id = id;
	}

	protected void add(Action motion) {
        motion.setActionSet(this);
		motionList.add(motion);
	}

	public void applyAll(Map<ActionType, Action> motionMap) {
		for (Action motion : motionList) {
			motionMap.put(motion.getActionType(), motion);
		}
	}

    protected long getAnimTickTime() { return animTickTime; }

    protected void setAnimTickTime(long time) { this.animTickTime = time; }

    protected float getAnimAlpha() {
        return animAlpha;
    }

    protected void setAnimAlpha(float animAlpha) {
        this.animAlpha = animAlpha;
    }

    protected int getAnimTick() {
        return animTick;
    }

    protected void setAnimTick(int animTick) {
        this.animTick = animTick;
    }

    public int getID() {
		return id;
	}

    public int getAnimTrail() {
        return animTrail;
    }

    public void setAnimTrail(int animTrail) {
        this.animTrail = animTrail;
    }

    public List<MapDirection> getAnimFrameFacing() {
        return animFrameFacing;
    }

    public void setAnimFrameFacing(List<MapDirection> animFrameFacing) {
        this.animFrameFacing = animFrameFacing;
    }
}
