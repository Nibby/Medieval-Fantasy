package hidden.indev0r.game.entity.npc.script;

import hidden.indev0r.game.entity.Actor;
import org.w3c.dom.Element;

import java.util.List;

/**
 * Created by MrDeathJockey on 14/12/14.
 */
public class Command$Random extends Command {

    private List<Command> randomCommands;
    private long randomTick = 0;
    private int interval = 0;

    @Override
    public void make(CommandBlock block, Actor actor, Element e) {
        super.make(block, actor, e);

        randomCommands = ScriptParser.parse(e.getTagName(), block, actor, e);

        if(e.hasAttribute("interval")) {
            interval = Integer.parseInt((String) Script.translate(e.getAttribute("interval"), e.getAttribute("randomParams")));
        } else interval = 0;
    }

    @Override
    public void exec(Actor actor, final CommandBlock block) {
        super.exec(actor, block);
        
        if(System.currentTimeMillis() - randomTick > interval) {
            int size = randomCommands.size();
            if(size <= 0) {
                block.executeNext(actor);
                return;
            }
            int random = (int) (Math.random() * size);
            randomCommands.get(random).exec(actor, block);
            randomTick = System.currentTimeMillis();

            if(cmdElement.hasAttribute("interval"))
                interval = Integer.parseInt((String) Script.translate(cmdElement.getAttribute("interval"), cmdElement.getAttribute("randomParams")));
        }
        block.executeNext(actor);
    }

}
