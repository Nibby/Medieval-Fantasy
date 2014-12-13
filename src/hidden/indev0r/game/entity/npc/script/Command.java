package hidden.indev0r.game.entity.npc.script;

import hidden.indev0r.game.Colors;
import hidden.indev0r.game.MedievalLauncher;
import hidden.indev0r.game.data.ScriptDataManager;
import hidden.indev0r.game.entity.Actor;
import hidden.indev0r.game.entity.animation.Action;
import hidden.indev0r.game.entity.animation.ActionType;
import hidden.indev0r.game.gui.component.base.GComponent$Dialog;
import hidden.indev0r.game.gui.component.base.GComponent$Frame;
import hidden.indev0r.game.gui.component.dialog.npc.GDialog$NPC$Paged;
import hidden.indev0r.game.gui.component.dialog.npc.GDialog$NPC$Standard;
import hidden.indev0r.game.gui.component.interfaces.GDialogListener;
import hidden.indev0r.game.map.MapDirection;
import hidden.indev0r.game.map.Tile;
import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.Color;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by MrDeathJockey on 14/12/9.
 *
 * The registry of all the available commands in the NPC blocking engine.
 * This cannot be put into an enum because enums cannot be cloned, and must be
 * singleton, whereas individual commands must be cloned for individual blocks.
 */
public class Command implements Cloneable {

    public static final void init() {

        new Command("dialog") {

            private int     dialogType = 0;
            private int     dialogWidth = 13, dialogHeight = 6;

            private String  dialogTitle = "null";
            private String[]  dialogContent = new String[] { };

            @Override
            public Command make(CommandBlock block, Actor actor, Element e) {
                onMake(block, actor, e);
                dialogType      = Integer.parseInt(e.getAttribute("type"));
                dialogTitle     = (String) Script.translate(e.getAttribute("title"), actor);

                if(e.hasAttribute("width"))  dialogWidth = Integer.parseInt(e.getAttribute("width"));
                else                         dialogWidth = 13;

                if(e.hasAttribute("height")) dialogHeight = Integer.parseInt(e.getAttribute("height"));
                else                         dialogHeight = 6;

                NodeList contentList = e.getElementsByTagName("content");
                dialogContent = new String[contentList.getLength()];

                for(int i = 0; i < dialogContent.length; i++) {
                    Element eContent = (Element) contentList.item(i);
                    dialogContent[i] = eContent.getTextContent().replace("    ", "");
                }

                //TODO assign this dialog instance information into block temp store

                return generateCommand(this);
            }

            @Override
            public void exec(final Actor actor, final CommandBlock block) {
                super.exec(actor, block);
                Vector2f pos = GComponent$Frame.alignToCenter(dialogWidth * Tile.TILE_SIZE, dialogHeight * Tile.TILE_SIZE);
                GComponent$Dialog dialog = null;
                switch(dialogType) {
                    case 0:
                        dialog = new GDialog$NPC$Standard(getActor(), dialogTitle, dialogContent[0], pos, dialogWidth, dialogHeight);
                        break;
                    case 1:
                        dialog = new GDialog$NPC$Paged(getActor(), dialogTitle, dialogContent, pos, dialogWidth, dialogHeight);
                        break;
                }

                final GComponent$Dialog finalDialog = dialog;
                assert dialog != null;
                dialog.addDialogListener(new GDialogListener() {
                    @Override
                    public void titleBarClicked(GComponent$Frame c) {}

                    @Override
                    public void dialogClosed(GComponent$Frame c) {
                        if(c == finalDialog)
                            block.executeNext(actor);
                    }
                });

                MedievalLauncher.getInstance().getGameState().getMenuOverlay().addComponent(dialog);
            }
        };

        new Command("wait") {

            private Timer delayTimer = null;
            private int delay;

            @Override
            public Command make(CommandBlock block, Actor actor, Element e) {
                defineActor(actor, e);

                delay = Integer.parseInt(e.getAttribute("for"));

                return generateCommand(this);
            }

            @Override
            public void exec(final Actor actor, final CommandBlock block) {
                super.exec(actor, block);
                delayTimer = new Timer(delay, null);
                delayTimer.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        block.executeNext(actor);
                        delayTimer.stop();
                    }
                });
                delayTimer.start();
            }
        };

        new Command("actAction") {
            private ActionType type;
            private boolean wait = false;

            @Override
            public Command make(CommandBlock block, Actor actor, Element e) {
                onMake(block, actor, e);
                type = ActionType.valueOf(e.getAttribute("type"));

                if(e.hasAttribute("wait")) wait = Boolean.parseBoolean(e.getAttribute("wait"));

                return generateCommand(this);
            }

            @Override
            public void exec(final Actor actor, final CommandBlock block) {
                super.exec(actor, block);
                Map<ActionType, Action> actionMap = getActor().getActionMap();
                if(actionMap == null) return;

                Action action = getActor().getActionMap().get(type);
                if(action == null) return;

                getActor().forceActAction(type);
                if(wait) {
                    final Timer timer = new Timer(action.getPlayTime(), null);
                    timer.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            block.executeNext(actor);
                            timer.stop();
                        }
                    });
                    timer.start();
                } else  block.executeNext(actor);
            }
        };

        new Command("say") {

            private Color color = Color.black;
            private String text = "";
            private int duration = 0;
            private int interval = 0;

            private long lastTime;

            @Override
            public Command make(CommandBlock block, Actor actor, Element e) {
                onMake(block, actor, e);
                if(e.hasAttribute("color"))
                    color = Colors.valueOf(e.getAttribute("color")).getColor();
                text = e.getTextContent();

                if(e.hasAttribute("duration"))
                    duration = Integer.parseInt(e.getAttribute("duration"));
                else duration = 1000;

                if(e.hasAttribute("interval"))
                    interval = Integer.parseInt(e.getAttribute("interval"));
                else interval = 0;

                lastTime = 0;

                return generateCommand(this);
            }

            @Override
            public void exec(Actor actor, final CommandBlock block) {
                super.exec(actor, block);
                if(System.currentTimeMillis() - lastTime > interval) {
                    MedievalLauncher.getInstance().getGameState().getMenuOverlay().showSpeechBubble(getActor(), text, duration, color);
                    lastTime = System.currentTimeMillis();

                }
                block.executeNext(actor);
            }
        };

        new Command("facing") {

            private String faceTarget;
            private boolean hasTarget;
            private MapDirection direction;

            @Override
            public Command make(CommandBlock block, Actor actor, Element e) {
                onMake(block, actor, e);
                hasTarget = e.hasAttribute("object");

                if(e.hasAttribute("object")) {
                    faceTarget = e.getAttribute("object");
                }

                else if(e.hasAttribute("direction")) {
                    direction = MapDirection.valueOf(e.getAttribute("direction"));
                }

                return generateCommand(this);
            }

            @Override
            public void exec(Actor actor, final CommandBlock block) {
                super.exec(actor, block);
                if(hasTarget) {
                    Actor facing = (Actor) Script.translate(faceTarget);
                    if(facing != null) {
                        direction = MapDirection.turnToFace(getActor(), facing);
                    }
                }
                if(direction != null)
                    getActor().setFacingDirection(direction);

                block.executeNext(actor);
            }
        };

        new Command("random") {

            private List<Command> randomCommands;
            private long randomTick = 0;
            private int interval = 0;

            @Override
            public Command make(CommandBlock block, Actor actor, Element e) {
                onMake(block, actor, e);

                randomCommands = ScriptParser.parse(e.getTagName(), block, actor, e);

                if(e.hasAttribute("interval")) {
                    interval = Integer.parseInt(e.getAttribute("interval"));
                } else interval = 0;

                return generateCommand(this);
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
                }
                block.executeNext(actor);
            }
        };

        new Command("move") {

            private int dx, dy;
            private int rx, ry;

            private boolean absMove = false;

            @Override
            public Command make(CommandBlock block, Actor actor, Element e) {
                onMake(block, actor, e);

                if(e.hasAttribute("direction")) {
                    int steps = Integer.parseInt(e.getAttribute("steps"));
                    MapDirection dir = MapDirection.valueOf(e.getAttribute("direction"));
                    switch(dir) {
                        case UP:
                            ry = -steps;
                            rx = 0;
                            break;
                        case DOWN:
                            ry = steps;
                            rx = 0;
                            break;
                        case LEFT:
                            rx = -steps;
                            ry = 0;
                            break;
                        case RIGHT:
                            rx = steps;
                            ry = 0;
                            break;
                    }
                    absMove = false;
                } else {
                    dx = Integer.parseInt(e.getAttribute("x"));
                    dy = Integer.parseInt(e.getAttribute("y"));
                    rx = 0;
                    ry = 0;
                    absMove = true;
                }

                return generateCommand(this);
            }

            @Override
            public void exec(Actor actor, final CommandBlock block) {
                super.exec(actor, block);
                Actor act = getActor();
                if(absMove) {
                    act.setMoveDestination(dx, dy);
                } else {
                    act.setMoveDestination(act.getX() + rx, act.getY() + ry);
                }
                block.executeNext(actor);
            }

        };

        new Command("scriptData") {

            private String setKey;
            private String setValue;

            @Override
            public Command make(CommandBlock block, Actor actor, Element e) {
                onMake(block, actor, e);
                setKey = e.getAttribute("key");
                setValue = (String) Script.translate(e.getAttribute("value"));
                return generateCommand(this);
            }

            @Override
            public void exec(Actor actor, final CommandBlock block) {
                super.exec(actor, block);

                ScriptDataManager.getManager().setValue(setKey, setValue);
                block.executeNext(actor);
            }
        };

        new Command("if") {

            private NestedCommandBlock nestedBlock;

            private Element ifElement;
            private String dataBeingChecked;
            private String checkOperation;
            private String dataBeingCompared;

            @Override
            public Command make(CommandBlock block, Actor actor, Element e) {
                onMake(block, actor, e);

                nestedBlock = new NestedCommandBlock(block);
                List<Command> childCommands = ScriptParser.parse("if", nestedBlock, actor, e);
                nestedBlock.setCommandList(childCommands);
                
                ifElement = e;

                return generateCommand(this);
            }

            @Override
            public void exec(Actor actor, final CommandBlock block) {
                super.exec(actor, block);

                //Checking script data
                if(ifElement.hasAttribute("scriptDataParam")) dataBeingChecked = (String) Script.translate(ifElement.getAttribute("check"), ifElement.getAttribute("scriptDataParam"));
                checkOperation = ifElement.getAttribute("operation");
                dataBeingCompared = ifElement.getAttribute("compare");

                int checkVar, compareVar;

                switch(checkOperation) {
                    //Int comparison
                    case "==":
                        checkVar = Integer.parseInt(dataBeingChecked);
                        compareVar = Integer.parseInt(dataBeingCompared);

                        if(checkVar == compareVar) {
                            nestedBlock.execute(actor);
                        } else block.executeNext(actor);
                        break;
                    case ">=":
                        checkVar = Integer.parseInt(dataBeingChecked);
                        compareVar = Integer.parseInt(dataBeingCompared);

                        if(checkVar >= compareVar) {
                            nestedBlock.execute(actor);
                        } else block.executeNext(actor);
                        break;
                    case "<=":
                        checkVar = Integer.parseInt(dataBeingChecked);
                        compareVar = Integer.parseInt(dataBeingCompared);

                        if(checkVar <= compareVar) {
                            nestedBlock.execute(actor);
                        } else block.executeNext(actor);
                        break;
                    //String comparison
                    case "equals":
                        if(dataBeingCompared.equals(dataBeingChecked)) {
                            nestedBlock.execute(actor);
                        } else block.executeNext(actor);
                        break;
                    default:
                        break;
                }
            }
        };
    }

    private static final Map<String, Command> commandDatabase = new HashMap<>();

    private CommandBlock parentBlock;
    private org.w3c.dom.Element cmdElement;
    private Actor actor;
    private String cmdKey;

    private Command(String cmdKey) {
        if(commandDatabase.get(cmdKey) != null) {
            throw new IllegalArgumentException("Command key already registered: " + cmdKey);
        }
        this.cmdKey = cmdKey;
        commandDatabase.put(cmdKey, this);
    }

    public Command make(CommandBlock block, Actor actor, Element e) {
        return null;
    }

    protected void onMake(CommandBlock block, Actor actor, Element e) {
        this.cmdElement = e;
    }

    public void exec(Actor actor, CommandBlock block) {
        defineActor(actor, cmdElement);
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

    public String getKey() {
        return cmdKey;
    }

    class NestedCommandBlock implements CommandBlock {

        private List<Command> commandList; //Each command has its own nested commands
        private int cmdStep = 0;
        private boolean finished = true;
        private CommandBlock parent;

        private NestedCommandBlock(CommandBlock parent) {
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
                return;
            }
            if (cmdStep > commandList.size() - 1) {
                finish(actor);
                return;
            }
            Command cmd = commandList.get(cmdStep);
            System.out.println("            BLOCK STEP: " + step + " - " + cmd.getKey());
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
}
