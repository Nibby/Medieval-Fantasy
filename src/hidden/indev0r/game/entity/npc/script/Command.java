package hidden.indev0r.game.entity.npc.script;

import hidden.indev0r.game.MedievalLauncher;
import hidden.indev0r.game.entity.Actor;
import hidden.indev0r.game.gui.component.base.GComponent$Dialog;
import hidden.indev0r.game.gui.component.base.GComponent$Frame;
import hidden.indev0r.game.gui.component.dialog.npc.GDialog$NPC$0;
import hidden.indev0r.game.gui.component.interfaces.GDialogListener;
import org.lwjgl.util.vector.Vector2f;
import org.w3c.dom.Element;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by MrDeathJockey on 14/12/9.
 *
 * The registry of all the available commands in the NPC scripting engine.
 * This cannot be put into an enum because enums cannot be cloned, and must be
 * singleton, whereas individual commands must be cloned for individual scripts.
 */
public class Command implements Cloneable {

    public static final void init() {

        new Command("dialog") {

            private String  dialogID   = "null";
            private int     dialogType = 0;

            private String  dialogTitle = "null";
            private String  dialogContent = "null";

            @Override
            public Command make(Script script, Actor actor, Element e) {
                if(!(e instanceof Element && e.getTagName().equals("dialog"))) return null;

                dialogID        = e.getAttribute("id");
                dialogType      = Integer.parseInt(e.getAttribute("type"));
                dialogContent   = e.getElementsByTagName("content").item(0).getTextContent();
                dialogTitle     = (String) Script.translate(e.getAttribute("title"), actor);

                //TODO assign this dialog instance information into script temp store

                return generateInstance(this);
            }

            @Override
            public void exec(Actor actor, final Script script) {
                GComponent$Dialog dialog = null;
                switch(dialogType) {
                    case 0:
                        dialog = new GDialog$NPC$0(dialogTitle, dialogContent, new Vector2f(200, 200));
                        break;
                }

                final GComponent$Dialog finalDialog = dialog;
                dialog.addDialogListener(new GDialogListener() {
                    @Override
                    public void titleBarClicked(GComponent$Frame c) {}

                    @Override
                    public void dialogClosed(GComponent$Frame c) {
                        if(c == finalDialog)
                            script.executeNext();
                    }
                });

                MedievalLauncher.getInstance().getGameState().getMenuOverlay().addComponent(dialog);
            }
        };
    }

    private static final Map<String, Command> commandDatabase = new HashMap<>();

    /**
     * Each command can (optionally) have its own command branches.
     * These are able to be selectively executed depending on a given choice
     * (typically used in multi-choice commands such as a dialog)
     *
     * TODO: Implement them
     */
    private Map<String, List<Command>> commandMap = new HashMap<>();

    private Command(String cmdKey) {
        if(commandDatabase.get(cmdKey) != null) {
            throw new IllegalArgumentException("Command key already registered: " + cmdKey);
        }
        commandDatabase.put(cmdKey, this);
    }

    public Command make(Script script, Actor actor, Element e) { return null; }
    public void exec(Actor actor, Script script) {}

    public Map<String, List<Command>> getCommandMap() {
        return commandMap;
    }

    public void setCommandMap(Map<String, List<Command>> commandMap) {
        this.commandMap = commandMap;
    }

    public static final Command getInstance(String key) {
        return commandDatabase.get(key);
    }

    public static final Command generateInstance(Command command) {
        try {
            return (Command) command.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }
}
