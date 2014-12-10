package hidden.indev0r.game.entity.animation;

import hidden.indev0r.game.entity.Entity;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

import java.util.ArrayList;

/**
 * Created by MrDeathJockey on 14/12/4.
 */
public class Action {

    private class ActionFrame {

        private Image frame;

        private int xShift;
        private int yShift;
        private ActionFrame(Image frame, int xShift, int yShift) {
            this.frame = frame;
            this.xShift = xShift;
            this.yShift = yShift;
        }

        public void render(Graphics g, Entity e, float x, float y) {
            x += xShift;
            y += yShift;
            Image renderFrame = frame;
            if(e.isSunken()) {
                renderFrame = renderFrame.getSubImage(0, 0, renderFrame.getWidth(), renderFrame.getHeight() / 4 * 3);
            }

            g.drawImage(renderFrame, x, y);
        }

    }
    private ActionSet actionSet;

	private java.util.List<ActionFrame> animation = new ArrayList<>();

    private java.util.List<Integer> animationDelay = new ArrayList<>();
    private long animationTick;

    private int animationFrame = 0;
    private boolean animationStopped = false;
	private ActionType actionType;

    private int xShift = 0, yShift = 0;
	public Action(ActionType id) {
		this(id, 0, 0);
	}

	public Action(ActionType id, int xShift, int yShift) {
		this.actionType = id;
		this.xShift = xShift;
		this.yShift = yShift;

        animationTick = System.currentTimeMillis();
	}

	public Action addFrame(Image frame, int delay, int xShift, int yShift) {
        animation.add(new ActionFrame(frame, xShift, yShift));
        animationDelay.add(delay);

		return Action.this;
	}

	public void render(Graphics g, Entity e, float x, float y, boolean animate) {
        tick(false, animate);

		if (animate) {
            animationStopped = false;

            ActionFrame frame = animation.get(animationFrame);
			frame.render(g, e, x, y);
		} else {
			animation.get(0).render(g, e, x, y);
		}
	}

	public void renderForced(Graphics g, Entity e, float x, float y) {
        tick(true, true);

        ActionFrame frame = animation.get(animationFrame);
        frame.render(g, e, x, y);
	}

    private void tick(boolean forced,  boolean animated) {
        if(!animated) return;
        if(System.currentTimeMillis() - animationTick > 2 * animationDelay.get(animationFrame)) {
            animationTick = System.currentTimeMillis();
            return;
        }

        if(System.currentTimeMillis() - animationTick > animationDelay.get(animationFrame)) {
            if(animationFrame < animation.size() - 1) animationFrame++;
            else {
                if(forced) animationStopped = true;
                else animationFrame = 0;
            }

            animationTick = System.currentTimeMillis();
        }
    }

    //Returns the collective time that is required to play this animation
    public int getPlayTime() {
        int time = 0;
        for(int delay : animationDelay) {
            time += delay;
        }
        return time;
    }

    public Image getCurrentFrame() {
        ActionFrame frame = animation.get(animationFrame);
        return frame.frame;
    }

    public ActionType getActionType() {
		return actionType;
	}

	public boolean hasEnded() {
		return animationStopped;
	}

	public void restart() {
		animationStopped = false;
        animationFrame = 0;
	}

    public Image getFrame(int frame) {
        return animation.get(frame).frame;
    }
}
