package hidden.indev0r.game.entity.npc.script;

import hidden.indev0r.game.data.ScriptDataManager;
import hidden.indev0r.game.entity.Actor;
import org.w3c.dom.Element;

/**
 * Created by MrDeathJockey on 14/12/14.
 */
public class Command$ScriptData extends Command {

    private String setKey;
    private String setValue;

    @Override
    public void make(CommandBlock block, Actor actor, Element e) {
        super.make(block, actor, e);
        setKey = e.getAttribute("key");
        setValue = (String) Script.translate(e.getAttribute("value"));
    }

    @Override
    public void exec(Actor actor, final CommandBlock block) {
        super.exec(actor, block);

        ScriptDataManager.getManager().setValue(setKey, setValue);
        block.executeNext(actor);
    }

}
