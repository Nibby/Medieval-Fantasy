package hidden.indev0r.game.gui.component.dialog.npc;

import hidden.indev0r.game.BitFont;
import hidden.indev0r.game.entity.Actor;
import hidden.indev0r.game.gui.component.base.GComponent$Button;
import hidden.indev0r.game.gui.component.base.GComponent$Dialog;
import hidden.indev0r.game.map.Tile;
import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

import java.util.List;

/**
 * Created by MrDeathJockey on 14/12/15.
 */
public class GDialog$NPC$Custom extends GComponent$Dialog {

    private String title;
    private String[] contents;
    private Actor actor;

    public GDialog$NPC$Custom(Actor actor, String title, String contents,
                              Vector2f pos, int width, int height, List<GComponent$Button> buttons) {
        super(pos, width, height);

        this.actor = actor;
        this.title = title;
        this.contents = contents.split(";");

        for(int i = 0; i < buttons.size(); i++) {
            GComponent$Button button = buttons.get(i);
            button.setPosition(new Vector2f(width * Tile.TILE_SIZE / 6 * 5 - ((i == 0) ? 48 : 48 + (i) * 96), height * Tile.TILE_SIZE - 42));
            addComponent(button);
        }
    }

    public void render(Graphics g) {
        super.render(g);

        for(int i = 0; i < contents.length; i++) {
            String text = contents[i];
            BitFont.render(g, text, (int) position.x + 10, (int) position.y + 42 + i * 22);
        }

        BitFont.render(g, this.title, (int) position.x + actor.getWidth() + 10, (int) position.y + 3, Color.white);
        actor.render(g, position.x + 5, position.y - 12);
    }

    public void tick(GameContainer gc) {
        super.tick(gc);
    }
}
