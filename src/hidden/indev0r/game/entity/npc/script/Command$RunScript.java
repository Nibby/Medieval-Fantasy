package hidden.indev0r.game.entity.npc.script;

import hidden.indev0r.game.entity.Actor;

import org.w3c.dom.Element;

/**
 * Created by MrDeathJockey on 14/12/15.
 */
public class Command$RunScript extends Command {

    private String scriptID;

    @Override
    public void make(CommandBlock block, Actor actor, Element e) {
        super.make(block, actor, e);
        scriptID = e.getAttribute("script");
    }

    @Override
    public void exec(Actor actor, final CommandBlock block) {
        super.exec(actor, block);
        Script script = ScriptDatabase.get(scriptID);
        script.setParentBlock(block);
        script.execute(getActor());
    }

}
