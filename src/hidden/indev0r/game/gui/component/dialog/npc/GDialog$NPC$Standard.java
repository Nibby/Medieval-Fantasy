package hidden.indev0r.game.gui.component.dialog.npc;

import hidden.indev0r.game.BitFont;
import hidden.indev0r.game.entity.Actor;
import hidden.indev0r.game.gui.component.base.GComponent;
import hidden.indev0r.game.gui.component.base.GComponent$Button;
import hidden.indev0r.game.gui.component.base.GComponent$Dialog;
import hidden.indev0r.game.map.Tile;
import hidden.indev0r.game.texture.Textures;
import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

/**
 * Created by MrDeathJockey on 14/12/9.
 */
public class GDialog$NPC$Standard extends GComponent$Dialog {

    private String[] textContent;
    private Actor actor;
    private String title;

    private GComponent$Button buttonOkay;

    public GDialog$NPC$Standard(Actor actor, String title, String content, Vector2f pos, int width, int height) {
        super(pos, width, height);
        this.actor = actor;
        textContent = content.split(";");
        this.title = title;
        buttonOkay = new GComponent$Button(new Vector2f(width * Tile.TILE_SIZE / 2 - 52, height * Tile.TILE_SIZE - 42),
                Textures.UI.BUTTON_LONG_BLUE.getSprite(0, 0),
                Textures.UI.BUTTON_LONG_BLUE.getSprite(0, 1),
                Textures.UI.BUTTON_LONG_BLUE.getSprite(0, 2));
        addComponent(buttonOkay);

        buttonOkay.setText("OKAY");
        buttonOkay.addListener(this);
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
        BitFont.render(g, title, (int) position.x + actor.getWidth() + 10, (int) position.y + 3, Color.white);
        actor.render(g, position.x + 5, position.y - 12, actor.getTextureColor());
        for (GComponent gc : internalComponents) gc.render(g);
        g.popTransform();

        for(int i = 0; i < textContent.length; i++) {
            BitFont.render(g, textContent[i], (int) position.x + 10, (int) position.y + 42 + i * 22);
        }
    }

    public void tick(GameContainer gc) {
        super.tick(gc);
    }

    @Override
    public void componentClicked(GComponent c) {
        super.componentClicked(c);

        if(c == buttonOkay) dispose();
    }
}
