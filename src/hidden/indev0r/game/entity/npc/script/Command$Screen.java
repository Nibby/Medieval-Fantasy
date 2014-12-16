package hidden.indev0r.game.entity.npc.script;

import hidden.indev0r.game.Colors;
import hidden.indev0r.game.MedievalLauncher;
import hidden.indev0r.game.entity.Actor;
import org.w3c.dom.Element;

/**
 * Created by MrDeathJockey on 14/12/16.
 */
public class Command$Screen extends Command {

    private String mode;
    private Colors color = Colors.BLACK;
    private int duration = 2500;

    @Override
    public void make(CommandBlock block, Actor actor, Element e) {
        super.make(block, actor, e);
        mode = e.getAttribute("mode");

        if(e.hasAttribute("duration"))
            duration = Integer.parseInt(e.getAttribute("duration"));

        if(e.hasAttribute("color"))
            color = Colors.valueOf(e.getAttribute("color"));
    }

    @Override
    public void exec(Actor actor, CommandBlock block) {
        super.exec(actor, block);

        switch(mode) {
            case "FADE_IN":
                MedievalLauncher.getInstance().getGameState().fadeIn(color.getColor(), duration);
                break;
            case "FADE_OUT":
                MedievalLauncher.getInstance().getGameState().fadeOut(duration);
                break;
            case "SET_HUE":
                MedievalLauncher.getInstance().getGameState().setFadeHue(color.getColor());
                break;
        }
        block.executeNext(actor);
    }
}
