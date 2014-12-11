package hidden.indev0r.game.entity.npc.script;

import hidden.indev0r.game.MedievalLauncher;
import hidden.indev0r.game.entity.Actor;
import hidden.indev0r.game.entity.NPC;
import hidden.indev0r.game.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by MrDeathJockey on 14/12/9.
 *
 * This is a single instance of 'actions' by NPCs and certain special monsters.
 * Different scripts are executed at different times, such as NPC interaction, monster death etc.
 *
 * A script is essentially a list of commands executed in order.
 */
public class Script {

    public boolean isFinished() {
        return finished;
    }

    //The type of script, these are called at different times
    public enum Type {
        interact,
        approach,
        death;

    }

    /*
        Sometimes it is not appropriate to specify the values directly in the XML file, rather
        you adopt something such as a 'variable reference'. Shown here, this enum will translate
        different $REFERENCES into proper values on parse.
     */
    public enum ReferredVariables {

        $NPC_NAME {
            @Override
            public Object getDefinition(Object...params) {
                if(params[0] instanceof NPC) return ((NPC) params[0]).getName();
                return "null";
            }
        },

        $PLAYER {
            @Override
            public Object getDefinition(Object... params) {
                return MedievalLauncher.getInstance().getGameState().getPlayer();
            }
        };

        public abstract Object getDefinition(Object ... params);

    }

    //The list of commands which will be executed in order
    private List<Command> commandList = new ArrayList<>();

    /*
        Certain commands register store content, such as recording dialog choices etc.
     */
    private Map<String, Object> scriptStore;

    //Current command out of total that the script is up to
    //This is absolutely necessary because we don't want all scripts to be executed all at once
    private int step = 0;
    private boolean finished = true;
    private Type type;

    //Associated actor this script is made for
    private Actor actor;

    public Script(Type type, Actor actor) {
        if(actor instanceof Player)
            throw new IllegalArgumentException("Player cannot be assigned scripts!");

        this.type = type;
        this.actor = actor;
    }

    //This will translate a $REFERENCE to actual values, by utilizing ConstantDictionary enum
    public static Object translate(String text, Object... args) {
        if(!text.startsWith("$")) return text;
        ReferredVariables key = null;

        try { key = ReferredVariables.valueOf(text); }
        catch(Exception e) { return text; }

        return key.getDefinition(args);
    }

    public List<Command> getCommandList() {
        return commandList;
    }

    public void setCommandList(List<Command> commandList) {
        this.commandList = commandList;
    }

    public void putStore(String key, Object obj) {
        scriptStore.put(key, obj);
    }

    public Object getStore(String key) {
        return scriptStore.get(key);
    }

    //Runs the script
    public void execute() {
        if(actor instanceof Player) return;
        if(!finished) return;
        step = 0;
        finished = false;
        scriptStore = new HashMap<>();
        executeStep(step);
    }

    private void executeStep(int step) {
        if(step > commandList.size() - 1) {
            finish();
            return;
        }
        Command cmd = commandList.get(step);
        cmd.exec(actor, this);
    }

    protected void executeNext() {
        if(finished) return;
        step++;
        executeStep(step);
    }

    protected void executePrevious() {
        if(finished) return;
        step--;
        executeStep(step);
    }

    public void finish() {
        finished = true;
    }

    public Type getType() {
        return type;
    }
}
