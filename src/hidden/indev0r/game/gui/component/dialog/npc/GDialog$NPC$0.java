package hidden.indev0r.game.gui.component.dialog.npc;

import hidden.indev0r.game.BitFont;
import hidden.indev0r.game.entity.Actor;
import hidden.indev0r.game.gui.component.base.GComponent;
import hidden.indev0r.game.gui.component.base.GComponent$Dialog;
import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

/**
 * Created by MrDeathJockey on 14/12/9.
 */
public class GDialog$NPC$0 extends GComponent$Dialog {

    private String[] textContent;
    private Actor actor;

    public GDialog$NPC$0(Actor actor, String title, String content, Vector2f pos) {
        super(title, pos, 13, 6);
        this.actor = actor;
        textContent = content.split(";");
    }

    public void render(Graphics g) {
        if (!isVisible()) return;
        g.pushTransform();
        g.scale(scale, scale);
        for (int x = 0; x < tileWidth; x++) {
            for (int y = 0; y < tileHeight; y++) {
                if (frames[x + (y * tileWidth)] != null) {
                    //Draw unique top right frame
                    if ((x + (y * tileWidth)) == (tileWidth - 1)) {
                        TOP_RIGHT_FRAME.draw(position.x + (x * stdImageWidth), position.y + (y * stdImageHeight) - 4);
                        continue;
                    }
                    //Draw top row
                    if (y == 1) {
                        frames[x + (y * tileWidth)].draw(position.x + (x * stdImageWidth), position.y + (y * stdImageHeight));
                        continue;
                    }
                    //Render the rest
                    frames[x + (y * tileWidth)].draw(position.x + (x * stdImageWidth), position.y + (y * stdImageHeight));
                }
            }
        }
        //Render close button
        BitFont.render(g, getTitle(), (int) position.x + actor.getWidth() + 10, (int) position.y + 3, Color.white);
        actor.render(g, position.x + 5, position.y - 12);
        for (GComponent gc : internalComponents) gc.render(g);
        g.popTransform();

        for(int i = 0; i < textContent.length; i++) {
            BitFont.render(g, textContent[i], (int) position.x + 10, (int) position.y + 42 + i * 22);
        }
    }

}
