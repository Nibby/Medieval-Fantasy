package hidden.indev0r.game.entity.npc.script;

import hidden.indev0r.game.entity.Actor;

import org.w3c.dom.Element;

/**
 * Created by MrDeathJockey on 14/12/9.
 *
 * The registry of all the available commands in the NPC blocking engine.
 * This cannot be put into an enum because enums cannot be cloned, and must be
 * singleton, whereas individual commands must be cloned for individual blocks.
 */
public abstract class Command {

    protected CommandBlock parentBlock;
    protected org.w3c.dom.Element cmdElement;
    protected Actor actor;

    public Command() {}

    public void make(CommandBlock block, Actor actor, Element e) {
        this.cmdElement = e;
    }

    public void exec(Actor actor, CommandBlock block) {
        defineActor(actor, cmdElement);
    }

    public void setParentBlock(CommandBlock parentBlock) {
        this.parentBlock = parentBlock;
    }

    public CommandBlock getParentBlock() {
        return parentBlock;
    }

    public void defineActor(Actor original, Element cmdElement) {
        if(cmdElement == null) {
            actor = original;
            return;
        }
        if(cmdElement.hasAttribute("actor")) {
            actor = (Actor) Script.translate(cmdElement.getAttribute("actor"), cmdElement.getAttribute("actorRef"));
        }
        else actor = original;
    }

    public Actor getActor() { return actor; }

    public static Command getCommand(String cmdName) {
        return CommandList.valueOf(cmdName).newInstance();
    }
}
