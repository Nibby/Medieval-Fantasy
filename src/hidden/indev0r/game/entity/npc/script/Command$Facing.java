package hidden.indev0r.game.entity.npc.script;

import hidden.indev0r.game.entity.Actor;
import hidden.indev0r.game.map.MapDirection;
import org.w3c.dom.Element;

/**
 * Created by MrDeathJockey on 14/12/14.
 */
public class Command$Facing extends Command {

    private String faceTarget;
    private boolean hasTarget;
    private MapDirection direction;

    @Override
    public void make(CommandBlock block, Actor actor, Element e) {
        super.make(block, actor, e);
        hasTarget = e.hasAttribute("object");

        if(e.hasAttribute("object")) {
            faceTarget = e.getAttribute("object");
        }

        else if(e.hasAttribute("direction")) {
            direction = MapDirection.valueOf(e.getAttribute("direction"));
        }
    }

    @Override
    public void exec(Actor actor, final CommandBlock block) {
        super.exec(actor, block);
        if(hasTarget) {
            Actor facing = (Actor) Script.translate(faceTarget);
            if(facing != null) {
                direction = MapDirection.turnToFace(getActor(), facing);
            }
        }
        if(direction != null)
            getActor().setFacingDirection(direction);

        block.executeNext(actor);
    }
}
