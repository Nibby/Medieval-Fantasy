package hidden.indev0r.core;

import hidden.indev0r.core.entity.Entity;
import hidden.indev0r.core.reference.References;

import java.awt.*;

/**
 * Created by MrDeathJockey on 14/12/3.
 */
public class Camera {

	private float offsetX = 0, offsetY = 0;
	private Entity trackingEntity;

	/*
	 * When panning, it will disable any attempts to set
	 * offset of an entity
	 */
	private boolean forcePan     = false;
	private long    forcePanTime = System.currentTimeMillis();
	private int   forcePanInterval;
	private Point forcePanPoint;
	private static int   panSpeed = References.DRAW_SCALE;
	private        float rotation = 0f;

	public Camera(float initialOffsetX, float initialOffsetY) {
		this.offsetX = initialOffsetX;
		this.offsetY = initialOffsetY;
	}

	public void setOffset(float x, float y) {
		offsetX = x;
		offsetY = y;
	}

	public float getOffsetX() {
		return offsetX;
	}

	public float getOffsetY() {
		return offsetY;
	}

	public void tick() {
		//Updates track
		if (trackingEntity != null) {
			if (!forcePan) {
				setOffset(-(trackingEntity.getCurrentX() - (References.GAME_WIDTH / 2 / References.DRAW_SCALE) + trackingEntity.getWidth() / 2),
						-(trackingEntity.getCurrentY() - (References.GAME_HEIGHT / 2 / References.DRAW_SCALE) + trackingEntity.getHeight() / 2));

			} else {
				doForcePan();
			}
		} else {
			doForcePan();
		}
	}

	private void doForcePan() {
		if (!forcePan) return;
		if (System.currentTimeMillis() - forcePanTime > forcePanInterval) {
			float cx = getOffsetX();
			float cy = getOffsetY();
			float dx = forcePanPoint.x;
			float dy = forcePanPoint.y;
			if (cx != dx || cy != dy) {
				if (cx > dx) {
					setOffset(cx - panSpeed, cy);
				} else if (cx < dx) {
					setOffset(cx + panSpeed, cy);
				}

				if (cy > dy) {
					setOffset(cy, cy - panSpeed);
				} else if (cy < dy) {
					setOffset(cx, cy + panSpeed);
				}
			} else {
				forcePan = false;
			}
			forcePanTime += forcePanInterval;
		}
	}

	public void setTrackObject(Entity entity) {
		trackingEntity = entity;
	}

	/**
	 * In contrast to setOffset method, pan brings a much slower and smoother camera
	 * <p/>
	 * Doing so will disable entity tracking
	 *
	 * @param x   - X co-ordinate to pan to
	 * @param y   - Y co-ordinate to pan to
	 * @param spd - Speed of pan
	 */
	public void pan(int x, int y, int spd) {
		forcePanPoint = new Point(x - x % References.DRAW_SCALE, y - y % References.DRAW_SCALE);
		forcePanInterval = spd;
		forcePanTime = System.currentTimeMillis();
		forcePan = true;

		trackingEntity = null;
	}
}
