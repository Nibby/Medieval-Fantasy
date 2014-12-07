package hidden.indev0r.core.entity.animation;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by MrDeathJockey on 14/12/4.
 */
public class Action {

	Animation animation;
	private ActionType actionType;

	private int xShift = 0, yShift = 0;
	private java.util.List<Point> frameShifts = new ArrayList<>();

	public Action(ActionType id) {
		this(id, 0, 0);
	}

	public Action(ActionType id, int xShift, int yShift) {
		animation = new Animation(new Image[]{}, 0);
		this.actionType = id;
		this.xShift = xShift;
		this.yShift = yShift;
	}

	public Action addFrame(Image frame, int delay, int xShift, int yShift) {
		animation.addFrame(frame, delay);
		frameShifts.add(new Point(xShift, yShift));
		return Action.this;
	}

	public void render(Graphics g, float x, float y, boolean animate) {
		if (animate) {
			animation.start();
			int frame = animation.getFrame();
			Point shift = frameShifts.get(frame);

			drawOutline(animation.getCurrentFrame(), x, y, 1);
			g.drawAnimation(animation, x + xShift + shift.x, y + yShift + shift.y);
		} else {
			drawOutline(animation.getImage(0), x, y, 1);
			g.drawImage(animation.getImage(0), x + xShift, y + yShift);
		}
	}

	public void renderForced(Graphics g, float x, float y) {
		animation.setLooping(false);

		int frame = animation.getFrame();
		Point shift = frameShifts.get(frame);

		drawOutline(animation.getCurrentFrame(), x, y, 1);
		g.drawAnimation(animation, x + xShift + shift.x, y + yShift + shift.y);
	}

	private void drawOutline(Image img, float x, float y, int weight) {

		img.getScaledCopy(img.getWidth() + weight * 2, img.getHeight() + weight * 2).draw(x - weight, y - weight, org.newdawn.slick.Color.black);
	}

	public ActionType getActionType() {
		return actionType;
	}

	public boolean hasEnded() {
		return animation.isStopped();
	}

	public void restart() {
		animation.restart();
	}
}
