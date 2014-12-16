package hidden.indev0r.game.entity.npc.script;

import hidden.indev0r.game.MedievalLauncher;
import hidden.indev0r.game.entity.Actor;
import hidden.indev0r.game.gui.component.base.GComponent;
import hidden.indev0r.game.gui.component.base.GComponent$Button;
import hidden.indev0r.game.gui.component.base.GComponent$Dialog;
import hidden.indev0r.game.gui.component.base.GComponent$Frame;
import hidden.indev0r.game.gui.component.dialog.npc.GDialog$NPC$Custom;
import hidden.indev0r.game.gui.component.dialog.npc.GDialog$NPC$Paged;
import hidden.indev0r.game.gui.component.dialog.npc.GDialog$NPC$Standard;
import hidden.indev0r.game.gui.component.interfaces.GComponentListener;
import hidden.indev0r.game.gui.component.interfaces.GDialogListener;
import hidden.indev0r.game.map.Tile;
import hidden.indev0r.game.texture.Textures;
import org.lwjgl.util.vector.Vector2f;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by MrDeathJockey on 14/12/14.
 */
public class Command$Dialog extends Command implements GComponentListener {

    private int     dialogType = 0;
    private int     dialogWidth = 13, dialogHeight = 6;

    private String  dialogTitle = "null";
    private String[]  dialogContent = new String[] { };

    private Map<GComponent$Button, NestedCommandBlock> dialogOptionMap = new HashMap<>();
    private List<GComponent$Button> dialogOptionButtonList = new ArrayList<>();
    private GComponent$Dialog dialog;
    private CommandBlock block;
    private boolean listenerAssigned = false;

    @Override
    public void make(final CommandBlock block, final Actor actor, Element e) {
        super.make(block, actor, e);
        dialogType      = Integer.parseInt(e.getAttribute("type"));
        dialogTitle     = (String) Script.translate(e.getAttribute("title"), actor);

        if(e.hasAttribute("width"))  dialogWidth = Integer.parseInt(e.getAttribute("width"));
        else                         dialogWidth = 13;

        if(e.hasAttribute("height")) dialogHeight = Integer.parseInt(e.getAttribute("height"));
        else                         dialogHeight = 6;

        NodeList contentList = e.getElementsByTagName("content");
        dialogContent = new String[contentList.getLength()];

        this.block = block;

        for(int i = 0; i < dialogContent.length; i++) {
            Element eContent = (Element) contentList.item(i);
            dialogContent[i] = eContent.getTextContent().replace("    ", "").replace("\t", "");
        }

        //Custom option dialog
        if(dialogType == 2) {
            NodeList optionList = e.getElementsByTagName("option");
            for(int i = 0; i < optionList.getLength(); i++) {
                Element eOption = (Element) optionList.item(i);

                final NestedCommandBlock optionCommandBlock = new NestedCommandBlock(block);
                List<Command> optionCommandList = ScriptParser.parse("option", optionCommandBlock, actor, eOption);
                optionCommandBlock.setCommandList(optionCommandList);

                int optionType = Integer.parseInt(eOption.getAttribute("type"));
                String optionText = eOption.getAttribute("text").toUpperCase();

                Vector2f buttonPosition = new Vector2f(0, 0);
                GComponent$Button button = null;
                switch(optionType) {
                    //Wood button
                    case 0:
                        button = new GComponent$Button(buttonPosition, Textures.UI.BUTTON_LONG_WOODEN.getSprite(0, 0),
                                                                       Textures.UI.BUTTON_LONG_WOODEN.getSprite(0, 1),
                                                                       Textures.UI.BUTTON_LONG_WOODEN.getSprite(0, 2));
                        break;
                    //Blue button
                    case 1:
                        button = new GComponent$Button(buttonPosition, Textures.UI.BUTTON_LONG_BLUE.getSprite(0, 0),
                                                                       Textures.UI.BUTTON_LONG_BLUE.getSprite(0, 1),
                                                                       Textures.UI.BUTTON_LONG_BLUE.getSprite(0, 2));
                        break;
                    //Green button
                    case 2:

                        break;
                    //Red button
                    case 3:

                        break;
                }
                button.setText(optionText);
                dialogOptionMap.put(button, optionCommandBlock);
                dialogOptionButtonList.add(button);
            }
        }
    }

    @Override
    public void exec(final Actor actor, final CommandBlock block) {
        super.exec(actor, block);
        Vector2f pos = GComponent$Frame.alignToCenter(dialogWidth * Tile.TILE_SIZE, dialogHeight * Tile.TILE_SIZE);

        dialog = null;
        switch(dialogType) {
            case 0:
                dialog = new GDialog$NPC$Standard(getActor(), dialogTitle, dialogContent[0], pos, dialogWidth, dialogHeight);
                break;
            case 1:
                dialog = new GDialog$NPC$Paged(getActor(), dialogTitle, dialogContent, pos, dialogWidth, dialogHeight);
                break;
            case 2:
                if(!listenerAssigned) {
                    for (GComponent$Button button : dialogOptionButtonList) {
                        button.addListener(this);
                    }
                    listenerAssigned = true;
                }
                dialog = new GDialog$NPC$Custom(getActor(), dialogTitle, dialogContent[0], pos, dialogWidth, dialogHeight, dialogOptionButtonList);
                dialog.setClosable(false);
                break;
        }

        dialog.addDialogListener(new GDialogListener() {
            @Override
            public void titleBarClicked(GComponent$Frame c) {
            }

            @Override
            public void dialogClosed(GComponent$Frame c) {
                if (c == dialog) {
                    if(dialogType != 2)
                        block.executeNext(actor);
                }
            }
        });

        MedievalLauncher.getInstance().getGameState().getMenuOverlay().addComponent(dialog);
    }


    @Override
    public void componentClicked(GComponent c) {
        for(GComponent$Button btn : dialogOptionMap.keySet()) {
            if(btn == c) {
                dialogOptionMap.get(btn).execute(actor);
                dialog.dispose();
            }
        }
    }

    @Override
    public void componentHovered(GComponent c) {

    }
}
