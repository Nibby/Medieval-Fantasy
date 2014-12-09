package hidden.indev0r.game.gui.component.dialog.npc;

import hidden.indev0r.game.BitFont;
import hidden.indev0r.game.gui.component.base.GComponent$Dialog;
import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.Graphics;

/**
 * Created by MrDeathJockey on 14/12/9.
 */
public class GDialog$NPC$0 extends GComponent$Dialog {

    private String[] textContent;

    public GDialog$NPC$0(String title, String content, Vector2f pos) {
        super(title, pos, 12, 6);

        textContent = content.split(";");
    }

    public void render(Graphics g) {
        super.render(g);

        for(int i = 0; i < textContent.length; i++) {
            BitFont.render(g, textContent[i], (int) position.x + 10, (int) position.y + 42 + i * 22);
        }
    }

}
