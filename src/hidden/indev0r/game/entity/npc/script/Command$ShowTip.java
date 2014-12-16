package hidden.indev0r.game.entity.npc.script;

import hidden.indev0r.game.Colors;
import hidden.indev0r.game.MedievalLauncher;
import hidden.indev0r.game.entity.Actor;
import org.w3c.dom.Element;

/**
 * Created by MrDeathJockey on 14/12/16.
 */
public class Command$ShowTip extends Command {

    private Colors color = Colors.WHITE;
    private int type = 0, duration = 1500;
    private String text;

    @Override
    public void make(CommandBlock block, Actor actor, Element e) {
        super.make(block, actor, e);

        if(e.hasAttribute("color"))
            color = Colors.valueOf(e.getAttribute("color"));

        if(e.hasAttribute("type"))
            type = Integer.parseInt(e.getAttribute("type"));

        if(e.hasAttribute("duration"))
            duration = Integer.parseInt("duration");

        text = e.getTextContent().replace("\t", "");
    }

    @Override
    public void exec(Actor actor, CommandBlock block) {
        super.exec(actor, block);
        MedievalLauncher.getInstance().getGameState().getMenuOverlay().showHint(text, duration, color.getColor(), type);
        block.executeNext(actor);
    }
}
