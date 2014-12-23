package hidden.indev0r.game.entity.animation;

import hidden.indev0r.game.entity.Entity;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

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
        public void render(Graphics g, Entity e, Color col, float x, float y) {
            x += xShift;
            y += yShift;
            Image renderFrame = frame;
            if(e.isSunken()) {
                renderFrame = renderFrame.getSubImage(0, 0, renderFrame.getWidth(), renderFrame.getHeight() / 4 * 3);
            }
            g.drawImage(renderFrame, x, y, col);
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

    public void setActionSet(ActionSet set) {
        this.actionSet = set;
    }

	public Action addFrame(Image frame, int delay, int xShift, int yShift) {
        animation.add(new ActionFrame(frame, xShift, yShift));
        animationDelay.add(delay);

		return Action.this;
	}

	public void render(Graphics g, Entity e, float x, float y, Color col, boolean animate) {
        tick(false, animate);

		if (animate) {
            animationStopped = false;

            ActionFrame frame = animation.get(animationFrame);
			frame.render(g, e, col, x, y);
		} else {
			animation.get(0).render(g, e, col, x, y);
		}

        //Special effects
        if(actionSet != null && (actionSet.getID() == 10 || actionSet.getID() == 6)) {
            Image frame = getCurrentFrame();
            Color c = new Color(1f, 1f, 1f, actionSet.getAnimAlpha());
            frame = frame.getScaledCopy(1 + (float) actionSet.getAnimTick() / 15);
            frame.draw(x - actionSet.getAnimTick(), y - actionSet.getAnimTick(), c);

            if(System.currentTimeMillis() - actionSet.getAnimTickTime() > 25) {
                actionSet.setAnimTick(actionSet.getAnimTick() + 1);
                actionSet.setAnimAlpha(actionSet.getAnimAlpha() - 0.02f);
                if(actionSet.getAnimAlpha() <= 0f) {
                    actionSet.setAnimTick(0);
                    actionSet.setAnimAlpha(0.5f);
                    actionSet.setAnimTickTime(actionSet.getAnimTickTime() + 2000);
                    return;
                }
                actionSet.setAnimTickTime(System.currentTimeMillis());
            }
        }
	}

	public void renderForced(Graphics g, Entity e, Color col, float x, float y) {
        tick(true, true);

        ActionFrame frame = animation.get(animationFrame);
        frame.render(g, e, col, x, y);
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

    public Image getLastFrame() {
        return animation.get(animation.size() - 1).frame;
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
