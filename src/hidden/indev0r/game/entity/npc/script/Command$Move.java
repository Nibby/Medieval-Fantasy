package hidden.indev0r.game.entity.npc.script;

import hidden.indev0r.game.entity.Actor;
import hidden.indev0r.game.map.MapDirection;
import org.w3c.dom.Element;

/**
 * Created by MrDeathJockey on 14/12/14.
 */
public class Command$Move extends Command {

    private int dx, dy;
    private int rx, ry;

    private boolean absMove = false;

    @Override
    public void make(CommandBlock block, Actor actor, Element e) {
        super.make(block, actor, e);

        if(e.hasAttribute("direction")) {
            int steps = Integer.parseInt(e.getAttribute("steps"));
            MapDirection dir = MapDirection.valueOf(e.getAttribute("direction"));
            switch(dir) {
                case UP:
                    ry = -steps;
                    rx = 0;
                    break;
                case DOWN:
                    ry = steps;
                    rx = 0;
                    break;
                case LEFT:
                    rx = -steps;
                    ry = 0;
                    break;
                case RIGHT:
                    rx = steps;
                    ry = 0;
                    break;
            }
            absMove = false;
        } else {
            dx = Integer.parseInt(e.getAttribute("x"));
            dy = Integer.parseInt(e.getAttribute("y"));
            rx = 0;
            ry = 0;
            absMove = true;
        }
    }

    @Override
    public void exec(Actor actor, final CommandBlock block) {
        super.exec(actor, block);

        Actor act = getActor();
        if(absMove) {
            act.setMoveDestination(dx, dy);
        } else {
            act.setMoveDestination(act.getX() + rx, act.getY() + ry);
        }
        block.executeNext(actor);
    }

}
