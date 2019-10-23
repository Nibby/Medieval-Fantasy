package hidden.indev0r.game.map;

import java.awt.*;

/**
 * Created by MrDeathJockey on 14/12/3.
 */
public class WarpPoint {

	private String targetMap;
	private Point  origin, target;

	public WarpPoint(String targetMap, Point origin, Point target) {
		this.targetMap = targetMap;
		this.origin = origin;
		this.target = target;
	}

	public String getTargetMap() {
		return targetMap;
	}

	public Point getOrigin() {
		return origin;
	}

	public Point getTarget() {
		return target;
	}
}
