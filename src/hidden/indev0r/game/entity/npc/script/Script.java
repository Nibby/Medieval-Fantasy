package hidden.indev0r.game.entity.npc.script;

import hidden.indev0r.game.MedievalLauncher;
import hidden.indev0r.game.data.ScriptDataManager;
import hidden.indev0r.game.entity.Actor;
import hidden.indev0r.game.entity.NPC;
import hidden.indev0r.game.entity.NPCDatabase;
import hidden.indev0r.game.entity.player.Player;

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
public class Script implements CommandBlock {

    //The type of script, these are called at different times
    public enum Type {
        interact,
        approach,
        death,
        hurt,
        damage,
        summon
        ;

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

        //A specific, referred NPC
        $NPC_REF {
            @Override
            public Object getDefinition(Object... params) {
                String refID = (String) params[0];
                return NPCDatabase.get(refID);
            }
        },

        $PLAYER {
            @Override
            public Object getDefinition(Object... params) {
                return MedievalLauncher.getInstance().getGameState().getPlayer();
            }
        },

        $PLAYER_POSITION {
            @Override
            public Object getDefinition(Object... params) {
                Player player = MedievalLauncher.getInstance().getGameState().getPlayer();
                return (int) player.getX() + "," + (int) player.getY();
            }
        },


        //Data from ScriptDataManagr
        $SCRIPT_DATA {
            @Override
            public Object getDefinition(Object... params) {
                String refKey = (String) params[0];
                return ScriptDataManager.getManager().getValue(refKey);
            }
        },

        $RANDOM_NUMBER {
            @Override
            public Object getDefinition(Object... params) {
                if(params != null && params.length == 1) {
                    String[] bounds = ((String) params[0]).split("-");
                    int lower = Integer.parseInt(bounds[0]);
                    int upper = Integer.parseInt(bounds[1]);
                    return (int) (Math.random() * (upper - lower) + lower) + "";
                } else {
                    return "0";
                }
            }
        }

        ;
        public abstract Object getDefinition(Object ... params);

    }

    //The list of commands which will be executed in order
    private List<Command> commandList = new ArrayList<>();
    private CommandBlock parentBlock;

    /*
        Certain commands register store content, such as recording dialog choices etc.
     */
    private Map<String, Object> scriptStore;

    //Current command out of total that the script is up to
    //This is absolutely necessary because we don't want all scripts to be executed all at once
    private int step = 0;

    private boolean finished = true;

    public Script() {
        this(null);
    }

    public Script(CommandBlock parent) {
        setParentBlock(parent);
    }

    public CommandBlock getParentBlock() {
        return parentBlock;
    }

    public void setParentBlock(CommandBlock parentBlock) {
        this.parentBlock = parentBlock;
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

    @Override
    public void storeTemp(String key, Object obj) {
        scriptStore.put(key, obj);
    }

    @Override
    public Object getTemp(String key) {
        return scriptStore.get(key);
    }

    //Runs the script
    @Override
    public void execute(Actor actor) {
        if(!finished) return;
        System.out.println("SCRIPT STARTED [" + actor + "]");
        step = 0;
        finished = false;
        scriptStore = new HashMap<>();
        executeStep(actor, step);
    }

    @Override
    public void executeStep(Actor actor, int step) {
        if(step > commandList.size() - 1) {
            finish(actor);
            return;
        }
        Command cmd = commandList.get(step);
        System.out.println("    STEP: " + step + " - ");
        cmd.exec(actor, this);
    }

    @Override
    public void executeNext(Actor actor) {
        if(finished) return;
        step++;
        executeStep(actor, step);
    }

    @Override
    public void executePrev(Actor actor) {
        if(finished) return;
        step--;
        executeStep(actor, step);
    }

    @Override
    public void finish(Actor actor) {
        finished = true;
        step = 0;
        System.out.println("SCRIPT ENDED\n");
        if(parentBlock != null) {
            parentBlock.executeNext(actor);
//            setParentBlock(null);
        }
    }

    @Override
    public boolean isFinished() {
        return finished;
    }
}
