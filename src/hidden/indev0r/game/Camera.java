package hidden.indev0r.game;

import hidden.indev0r.game.entity.Entity;
import hidden.indev0r.game.reference.References;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by MrDeathJockey on 14/12/3.
 */
public class Camera {

	private float offsetX = 0, offsetY = 0;
	private Entity trackingEntity;

    private List<CameraListener> cameraListeners = new ArrayList<>();

	/*
	 * When panning, it will disable any attempts to setStat
	 * offset of an entity
	 */
	private boolean forcePan     = false;
	private long    forcePanTime = System.currentTimeMillis();
	private int   forcePanInterval;
	private Point forcePanPoint;
	private static int   panSpeed = References.DRAW_SCALE;

    private boolean shaking = false;
    private int shakeTime, shakeAmplitude, shakeInterval;
    private float shakeX = 0, originalX = 0, originalY = 0;
    private long shakeTimer, shakeStartTime;


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
		if(shaking) {
            if(System.currentTimeMillis() - shakeStartTime > shakeTime) {
                shaking = false;
                setOffset(originalX, originalY);
                fireShakeFinishEvent();
            }
            if(System.currentTimeMillis() - shakeTimer > shakeInterval) {
                if(getOffsetX() > originalX) shakeX = -shakeAmplitude;
                if(getOffsetX() < originalX) shakeX = shakeAmplitude;
                setOffset(getOffsetX() + shakeX, getOffsetY());
                shakeTimer = System.currentTimeMillis();
            }
            return;
        } else {
            shakeX = 0;
        }

		if (trackingEntity != null) {
			if (!forcePan) {
				setOffset(-(trackingEntity.getPosition().x - (References.GAME_WIDTH / 2 / References.DRAW_SCALE) + trackingEntity.getWidth() / 2),
						-(trackingEntity.getPosition().y - (References.GAME_HEIGHT / 2 / References.DRAW_SCALE) + trackingEntity.getHeight() / 2));

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
                firePanFinishEvent();
			}
			forcePanTime += forcePanInterval;
		}
	}

	public void setTrackObject(Entity entity) {
		trackingEntity = entity;
	}

    public void addListener(CameraListener listener) {
        cameraListeners.add(listener);
    }

    private void firePanFinishEvent() {
        for(int i = 0; i < cameraListeners.size(); i++) {
            cameraListeners.get(i).panFinished(this);
        }
    }

    private void fireShakeFinishEvent() {
        for(int i = 0; i < cameraListeners.size(); i++) {
            cameraListeners.get(i).shakeFinished(this);
        }
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
        panSpeed = spd;
        forcePanPoint = new Point(x - x % panSpeed, y - y % panSpeed);
        forcePanInterval = 10;
		forcePanTime = System.currentTimeMillis();
		forcePan = true;

		trackingEntity = null;
	}

    public void shake(int shakeAmplitude, int shakeInterval, int shakeLength) {
        shaking = true;
        originalX = getOffsetX();
        originalY = getOffsetY();
        shakeX = shakeAmplitude;
        this.shakeInterval = shakeInterval;
        shakeTimer = System.currentTimeMillis();
        shakeStartTime = System.currentTimeMillis();
        shakeTime = shakeLength;
        this.shakeAmplitude = shakeAmplitude;
    }

    public Entity getTrackingEntity() {
        return trackingEntity;
    }
}
