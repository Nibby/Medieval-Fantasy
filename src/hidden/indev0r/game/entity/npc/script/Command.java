package hidden.indev0r.game.entity.npc.script;

import hidden.indev0r.game.Colors;
import hidden.indev0r.game.MedievalLauncher;
import hidden.indev0r.game.entity.Actor;
import hidden.indev0r.game.entity.animation.Action;
import hidden.indev0r.game.entity.animation.ActionType;
import hidden.indev0r.game.gui.component.base.GComponent$Dialog;
import hidden.indev0r.game.gui.component.base.GComponent$Frame;
import hidden.indev0r.game.gui.component.dialog.npc.GDialog$NPC$0;
import hidden.indev0r.game.gui.component.interfaces.GDialogListener;
import hidden.indev0r.game.map.MapDirection;
import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.Color;
import org.w3c.dom.Element;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

                dialogID        = e.getAttribute("id");
                dialogType      = Integer.parseInt(e.getAttribute("type"));
                dialogContent   = e.getElementsByTagName("content").item(0).getTextContent().replace("  ", "");
                dialogTitle     = (String) Script.translate(e.getAttribute("title"), actor);

                //TODO assign this dialog instance information into script temp store

                return generateCommand(this);
            }

            @Override
            public void exec(Actor actor, final Script script) {
                super.exec(actor, script);
                GComponent$Dialog dialog = null;
                switch(dialogType) {
                    case 0:
                        dialog = new GDialog$NPC$0(actor, dialogTitle, dialogContent, new Vector2f(200, 200));
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

        new Command("wait") {

            private Timer delayTimer = null;

            @Override
            public Command make(Script script, Actor actor, Element e) {
                

                int delay = Integer.parseInt(e.getAttribute("for"));
                delayTimer = new Timer(delay, null);

                return generateCommand(this);
            }

            @Override
            public void exec(Actor actor, final Script script) {
                super.exec(actor, script);
                delayTimer.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        script.executeNext();
                        delayTimer.stop();
                    }
                });
                delayTimer.start();
            }
        };

        new Command("actAction") {

            private ActionType type;

            @Override
            public Command make(Script script, Actor actor, Element e) {
                

                type = ActionType.valueOf(e.getAttribute("type"));

                return generateCommand(this);
            }

            @Override
            public void exec(Actor actor, final Script script) {
                super.exec(actor, script);
                Map<ActionType, Action> actionMap = actor.getActionMap();
                if(actionMap == null) return;

                Action action = actor.getActionMap().get(type);
                if(action == null) return;

                actor.forceActAction(type);
                final Timer timer = new Timer(action.getPlayTime(), null);
                timer.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        script.executeNext();
                        timer.stop();
                    }
                });
                timer.start();

            }
        };

        new Command("say") {

            private Color color = Color.black;
            private String text = "";
            private int duration = 0;
            private int interval = 0;

            private long lastTime;

            @Override
            public Command make(Script script, Actor actor, Element e) {
                
                if(e.hasAttribute("color"))
                    color = Colors.valueOf(e.getAttribute("color")).getColor();
                text = e.getTextContent();
                duration = Integer.parseInt(e.getAttribute("duration"));
                interval = Integer.parseInt(e.getAttribute("interval"));

                lastTime = 0;

                return generateCommand(this);
            }

            @Override
            public void exec(Actor actor, final Script script) {
                super.exec(actor, script);
                if(System.currentTimeMillis() - lastTime > interval) {
                    MedievalLauncher.getInstance().getGameState().getMenuOverlay().showSpeechBubble(actor, text, duration, color);
                    lastTime = System.currentTimeMillis();
                }

                script.executeNext();
            }
        };

        new Command("facing") {

            private String faceTarget;
            private boolean hasTarget;
            private MapDirection direction;

            @Override
            public Command make(Script script, Actor actor, Element e) {
                
                hasTarget = e.hasAttribute("target");

                if(e.hasAttribute("target")) {
                    faceTarget = e.getAttribute("target");
                }

                else if(e.hasAttribute("direction")) {
                    direction = MapDirection.valueOf(e.getAttribute("direction"));
                }

                return generateCommand(this);
            }

            @Override
            public void exec(Actor actor, final Script script) {
                super.exec(actor, script);
                if(hasTarget) {
                    Actor facing = (Actor) Script.translate(faceTarget);
                    if(facing != null) {
                        direction = MapDirection.turnToFace(actor, facing);
                    }
                }
                if(direction != null)
                    actor.setFacingDirection(direction);

                script.executeNext();
            }
        };

        new Command("random") {

            private List<Command> randomCommands;
            private long randomTick = 0;
            private int interval = 0;

            @Override
            public Command make(Script script, Actor actor, Element e) {
                randomCommands = ScriptParser.parse(e.getTagName(), script, actor, e);

                if(e.hasAttribute("interval")) {
                    interval = Integer.parseInt(e.getAttribute("interval"));
                }

                return generateCommand(this);
            }

            @Override
            public void exec(Actor actor, final Script script) {
                super.exec(actor, script);
                if(System.currentTimeMillis() - randomTick > interval) {
                    int size = randomCommands.size();
                    if(size <= 0) return;
                    int random = (int) (Math.random() * size);
                    randomCommands.get(random).exec(actor, script);

                    randomTick = System.currentTimeMillis();
                }
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
    public void exec(Actor actor, Script script) {
        if(script.isFinished()) return;
    }

    public Map<String, List<Command>> getCommandMap() {
        return commandMap;
    }

    public void setCommandMap(Map<String, List<Command>> commandMap) {
        this.commandMap = commandMap;
    }

    public static final Command getCommand(String key) {
        return commandDatabase.get(key);
    }

    public static final Command generateCommand(Command command) {
        try {
            return (Command) command.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }
}
