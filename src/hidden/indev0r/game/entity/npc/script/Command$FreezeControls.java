package hidden.indev0r.game.entity.npc.script;

import hidden.indev0r.game.MedievalLauncher;
import hidden.indev0r.game.entity.Actor;
import org.w3c.dom.Element;

/**
 * Created by MrDeathJockey on 14/12/16.
 */
public class Command$FreezeControls extends Command {

    private boolean flag;

    @Override
    public void make(CommandBlock block, Actor actor, Element e) {
        super.make(block, actor, e);

        flag = Boolean.parseBoolean(e.getAttribute("flag"));
    }

    @Override
    public void exec(Actor actor, CommandBlock block) {
        super.exec(actor, block);

        MedievalLauncher.getInstance().getGameState().getPlayer().setControllable(!flag);
        block.executeNext(actor);
    }
}
