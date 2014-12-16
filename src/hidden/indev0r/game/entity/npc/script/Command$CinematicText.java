package hidden.indev0r.game.entity.npc.script;

import hidden.indev0r.game.MedievalLauncher;
import hidden.indev0r.game.entity.Actor;
import hidden.indev0r.game.gui.component.hud.GComponent$CinematicText;
import org.w3c.dom.Element;

/**
 * Created by MrDeathJockey on 14/12/16.
 */
public class Command$CinematicText extends Command {

    private int type;
    private String[] text;
    private int duration;

    @Override
    public void make(CommandBlock block, Actor actor, Element e) {
        super.make(block, actor, e);

        type = Integer.parseInt(e.getAttribute("type"));
        text = e.getTextContent().replace("    ", "").replace("\t", "").split(";");
        duration = Integer.parseInt(e.getAttribute("duration"));
    }

    @Override
    public void exec(Actor actor, CommandBlock block) {
        super.exec(actor, block);

        GComponent$CinematicText cinematicText = new GComponent$CinematicText(type, duration, text);
        MedievalLauncher.getInstance().getGameState().getMenuOverlay().addComponent(cinematicText);

        block.executeNext(actor);
    }
}
