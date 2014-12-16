package hidden.indev0r.game.entity.npc.script;

import hidden.indev0r.game.entity.Actor;
import org.w3c.dom.Element;

/**
 * Created by MrDeathJockey on 14/12/14.
 */
public class Command$Stats extends Command {

    private String statAction;
    private Actor.Stat stat;
    private int statValue;

    @Override
    public void make(CommandBlock block, Actor actor, Element e) {
        super.make(block, actor, e);

        statAction = e.getAttribute("action");
        stat = Actor.Stat.valueOf(e.getAttribute("stat"));
        statValue = Integer.parseInt((String) Script.translate(e.getAttribute("value"), e.getAttribute("randomParams")));
    }

    @Override
    public void exec(Actor actor, final CommandBlock block) {
        super.exec(actor, block);
        statValue = Integer.parseInt((String) Script.translate(cmdElement.getAttribute("value"), cmdElement.getAttribute("randomParams")));

        switch(statAction) {
            case "SET":
                getActor().setStat(stat, statValue);
                break;
            case "ADD":
                getActor().setStat(stat, getActor().getStat(stat) + statValue);
                break;
            case "DEDUCT":
                getActor().setStat(stat, getActor().getStat(stat) - statValue);
                break;
        }

        block.executeNext(actor);
    }

}
