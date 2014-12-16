package hidden.indev0r.game.entity.npc.script;

import hidden.indev0r.game.entity.Actor;
import org.w3c.dom.Element;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by MrDeathJockey on 14/12/14.
 */
public class Command$Wait extends Command {

    private Timer delayTimer = null;
    private int delay;

    @Override
    public void make(CommandBlock block, Actor actor, Element e) {
        defineActor(actor, e);

        delay = Integer.parseInt(e.getAttribute("for"));
    }

    @Override
    public void exec(final Actor actor, final CommandBlock block) {
        super.exec(actor, block);
        delayTimer = new Timer(delay, null);
        delayTimer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                block.executeNext(actor);
                delayTimer.stop();
            }
        });
        delayTimer.start();
    }

}
