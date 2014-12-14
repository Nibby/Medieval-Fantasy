package hidden.indev0r.game.entity.npc.script;

import hidden.indev0r.game.Colors;
import hidden.indev0r.game.MedievalLauncher;
import hidden.indev0r.game.entity.Actor;
import org.newdawn.slick.Color;
import org.w3c.dom.Element;

/**
 * Created by MrDeathJockey on 14/12/14.
 */
public class Command$Say extends Command {

    private Color color = Color.black;
    private String text = "";
    private int duration = 0;
    private int interval = 0;
    private boolean jitter = false;

    private long lastTime;

    @Override
    public Command make(CommandBlock block, Actor actor, Element e) {
        onMake(block, actor, e);
        if(e.hasAttribute("color"))
            color = Colors.valueOf(e.getAttribute("color")).getColor();
        text = e.getTextContent();

        if(e.hasAttribute("duration"))
            duration = Integer.parseInt(e.getAttribute("duration"));
        else duration = 1000;

        if(e.hasAttribute("interval"))
            interval = Integer.parseInt(e.getAttribute("interval"));
        else interval = 0;

        if(e.hasAttribute("jitter"))
            jitter = Boolean.parseBoolean(e.getAttribute("jitter"));
        else
            jitter = false;

        lastTime = 0;

        return generateCommand(this);
    }

    @Override
    public void exec(Actor actor, final CommandBlock block) {
        super.exec(actor, block);
        if(System.currentTimeMillis() - lastTime > interval) {
            MedievalLauncher.getInstance().getGameState().getMenuOverlay().showSpeechBubble(getActor(), text, duration, color, jitter);
            lastTime = System.currentTimeMillis();

        }
        block.executeNext(actor);
    }

}
