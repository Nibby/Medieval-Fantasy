package hidden.indev0r.game.entity.npc.script;

import hidden.indev0r.game.MedievalLauncher;
import hidden.indev0r.game.entity.Actor;
import hidden.indev0r.game.gui.component.base.GComponent$Dialog;
import hidden.indev0r.game.gui.component.base.GComponent$Frame;
import hidden.indev0r.game.gui.component.dialog.npc.GDialog$NPC$Paged;
import hidden.indev0r.game.gui.component.dialog.npc.GDialog$NPC$Standard;
import hidden.indev0r.game.gui.component.interfaces.GDialogListener;
import hidden.indev0r.game.map.Tile;
import org.lwjgl.util.vector.Vector2f;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * Created by MrDeathJockey on 14/12/14.
 */
public class Command$Dialog extends Command {

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
}
