package hidden.indev0r.game.entity.npc.script;

import hidden.indev0r.game.entity.Actor;

import java.util.List;

/**
 * Created by MrDeathJockey on 14/12/14.
 */
public class NestedCommandBlock implements CommandBlock {

    private List<Command> commandList; //Each command has its own nested commands
    private int cmdStep = 0;
    private boolean finished = true;
    private CommandBlock parent;

    protected NestedCommandBlock(CommandBlock parent) {
        this.parent = parent;
    }

    public void setCommandList(List<Command> commandList) {
        this.commandList = commandList;
    }

    @Override
    public void execute(Actor actor) {
        if(!finished) {
            return;
        }
        System.out.println("        BLOCK STARTED");
        finished = false;
        cmdStep = 0;
        executeStep(actor, cmdStep);
    }

    @Override
    public void executeNext(Actor actor) {
        if(finished) return;
        cmdStep++;
        executeStep(actor, cmdStep);
    }

    @Override
    public void executeStep(Actor actor, int step) {
        if (commandList == null) {
            return;
        }
        if (commandList.isEmpty()) {
            System.out.println("empty");
            return;
        }
        if (cmdStep > commandList.size() - 1) {
            finish(actor);
            return;
        }
        Command cmd = commandList.get(cmdStep);
        System.out.println("            BLOCK STEP: " + step);
        cmd.exec(actor, this);
    }

    @Override
    public void executePrev(Actor actor) {
        if(finished) return;
        cmdStep--;
        if(cmdStep < 0) cmdStep = 0;
        executeStep(actor, cmdStep);
    }

    @Override
    public void storeTemp(String key, Object object) {
        throw new UnsupportedOperationException("Not Implemented");
    }

    @Override
    public Object getTemp(String key) {
        throw new UnsupportedOperationException("Not Implemented");
    }

    @Override
    public void finish(Actor actor) {
        finished = true;
        System.out.println("        BLOCK ENDED");
        parent.executeNext(actor);
    }

    @Override
    public boolean isFinished() {
        return finished;
    }

}
