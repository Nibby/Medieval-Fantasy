package hidden.indev0r.core.maps;

import java.awt.*;

/**
 * Created by MrDeathJockey on 14/12/3.
 */
public class MapWarpPoint {

	private String targetMap;
	private Point  origin, target;

	public MapWarpPoint(String targetMap, Point origin, Point target) {
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
