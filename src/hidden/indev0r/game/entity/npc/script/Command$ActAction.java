package hidden.indev0r.game.entity.npc.script;

import hidden.indev0r.game.entity.Actor;
import hidden.indev0r.game.entity.animation.Action;
import hidden.indev0r.game.entity.animation.ActionType;
import org.w3c.dom.Element;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

/**
 * Created by MrDeathJockey on 14/12/14.
 */
public class Command$ActAction extends Command {

    private ActionType type;
    private boolean wait = false;

    @Override
    public void make(CommandBlock block, Actor actor, Element e) {
        super.make(block, actor, e);
        type = ActionType.valueOf(e.getAttribute("type"));

        if(e.hasAttribute("wait")) wait = Boolean.parseBoolean(e.getAttribute("wait"));
    }

    @Override
    public void exec(final Actor actor, final CommandBlock block) {
        super.exec(actor, block);
        Map<ActionType, Action> actionMap = getActor().getActionMap();
        if(actionMap == null) return;

        Action action = getActor().getActionMap().get(type);
        if(action == null) return;

        getActor().forceActAction(type);
        if(wait) {
            final Timer timer = new Timer(action.getPlayTime(), null);
            timer.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    block.executeNext(actor);
                    timer.stop();
                }
            });
            timer.start();
        } else  block.executeNext(actor);
    }

}
