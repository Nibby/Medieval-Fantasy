package hidden.indev0r.core.entity.animation;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by MrDeathJockey on 14/12/4.
 */
public class ActionMotion {

    Animation animation;
    private ActionID actionID;

    private int xShift = 0, yShift = 0;
    private java.util.List<Point> frameShifts = new ArrayList<>();

    public ActionMotion(ActionID id) {
        this(id, 0, 0);
    }

    public ActionMotion(ActionID id, int xShift, int yShift) {
        animation = new Animation(new Image[] {}, 0);
        this.actionID = id;
        this.xShift = xShift;
        this.yShift = yShift;
    }

    public ActionMotion addFrame(Image frame, int delay) {
        return addFrame(frame, delay, 0, 0);
    }

    public ActionMotion addFrame(Image frame, int delay, int xShift, int yShift) {
        animation.addFrame(frame, delay);
        frameShifts.add(new Point(xShift, yShift));
        return ActionMotion.this;
    }

    public void render(Graphics g, float x, float y, boolean animate) {
        if(animate) {
            animation.start();
            int frame = animation.getFrame();
            Point shift = frameShifts.get(frame);
            g.drawAnimation(animation, x + xShift + shift.x, y + yShift + shift.y);
        }

        else
            g.drawImage(animation.getImage(0), x + xShift, y + yShift);
    }

    public void renderForced(Graphics g, float x, float y) {
        animation.stopAt(animation.getFrameCount() - 1);
        int frame = animation.getFrame();
        Point shift = frameShifts.get(frame);
        g.drawAnimation(animation, x + xShift + shift.x, y + yShift + shift.y);
    }

    public ActionID getActionID() {
        return actionID;
    }

    public boolean hasEnded() {
        return animation.isStopped();
    }
}
