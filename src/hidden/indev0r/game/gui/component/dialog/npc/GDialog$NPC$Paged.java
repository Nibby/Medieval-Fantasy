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
 * Created by MrDeathJockey on 14/12/11.
 */
public class GDialog$NPC$Paged extends GComponent$Dialog {

    private Actor actor;
    private String title;
    private String[][] contents;

    private int currentPage = 0;

    private GComponent$Button buttonPgLeft, buttonPgRight;
    private GComponent$Button buttonOkay;

    private boolean showTip = false;
    private long showTime = 0;

    public GDialog$NPC$Paged(Actor actor, String title, String[] contents, Vector2f pos, int width, int height) {
        super(pos, width, height);
        this.actor = actor;
        this.title = title;

        this.contents = new String[contents.length][];
        for(int i = 0; i < contents.length; i++) {
            this.contents[i] = contents[i].split(";");
        }

        buttonOkay = new GComponent$Button(new Vector2f(width * Tile.TILE_SIZE / 6 * 5 - 56, height * Tile.TILE_SIZE - 42),
                Textures.UI.BUTTON_LONG_BLUE.getSprite(0, 0),
                Textures.UI.BUTTON_LONG_BLUE.getSprite(0, 1),
                Textures.UI.BUTTON_LONG_BLUE.getSprite(0, 2));
        buttonOkay.addListener(this);
        buttonOkay.setText("OKAY");
        addComponent(buttonOkay);

        buttonPgLeft = new GComponent$Button(new Vector2f(width * Tile.TILE_SIZE / 6 - 48, height * Tile.TILE_SIZE - 42),
                Textures.UI.BUTTON_ROUND_SLOTTED.getSprite(0, 0),
                Textures.UI.BUTTON_ROUND_SLOTTED.getSprite(2, 0),
                Textures.UI.BUTTON_ROUND_SLOTTED.getSprite(1, 0));
        buttonPgLeft.addListener(this);
        buttonPgLeft.setIcon(Textures.Icons.ARROW_LEFT);
        addComponent(buttonPgLeft);

        buttonPgRight = new GComponent$Button(new Vector2f(width * Tile.TILE_SIZE / 6 - 18, height * Tile.TILE_SIZE - 42),
                Textures.UI.BUTTON_ROUND_SLOTTED.getSprite(0, 0),
                Textures.UI.BUTTON_ROUND_SLOTTED.getSprite(2, 0),
                Textures.UI.BUTTON_ROUND_SLOTTED.getSprite(1, 0));
        buttonPgRight.addListener(this);
        buttonPgRight.setIcon(Textures.Icons.ARROW_RIGHT);
        addComponent(buttonPgRight);
    }

    public void render(Graphics g) {
        super.render(g);

        for(int i = 0; i < contents[currentPage].length; i++) {
            String text = contents[currentPage][i];
            BitFont.render(g, text, (int) position.x + 10, (int) position.y + 42 + i * 22);
        }

        BitFont.render(g, this.title, (int) position.x + actor.getWidth() + 10, (int) position.y + 3, Color.white);
        actor.render(g, position.x + 5, position.y - 12);

        if(showTip) {
            g.setColor(Color.black);
            String pageHint = "Page "  + (currentPage + 1) + " of " + contents.length;
            g.fillRoundRect(position.x + tileWidth * stdImageWidth / 6 + 24, position.y + height - 12, BitFont.widthOf(pageHint, 16), 24, 5);
            BitFont.render(g, pageHint, (int) (position.x + tileWidth * stdImageWidth / 6 + 28), (int) (position.y + tileHeight * stdImageHeight - 8));
        }
    }

    public void tick(GameContainer gc) {
        super.tick(gc);

        buttonPgLeft.setVisible(currentPage != 0 || contents.length == 0);
        buttonPgRight.setVisible(currentPage < contents.length - 1 || contents.length == 0);

        if(showTip && System.currentTimeMillis() - showTime > 1500) {
            showTip = false;
            showTime = 0;
        }
    }

    public void nextPage() {
        if(currentPage < contents.length - 1) currentPage++;
        buttonPgRight.setVisible(currentPage < contents.length - 1 || contents.length == 0);
    }

    public void prevPage() {
        if(currentPage > 0) currentPage --;
        buttonPgLeft.setVisible(currentPage != 0 || contents.length == 0);
    }

    @Override
    public void componentHovered(GComponent c) {
        super.componentHovered(c);

        showTip = c == buttonPgRight || c == buttonPgLeft;
        if(showTip) showTime = System.currentTimeMillis();
    }

    @Override
    public void componentClicked(GComponent c) {
        super.componentClicked(c);

        if(c == buttonOkay) dispose();
        if(c == buttonPgLeft) prevPage();
        if(c == buttonPgRight) nextPage();
    }
}
