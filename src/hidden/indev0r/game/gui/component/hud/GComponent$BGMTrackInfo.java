package hidden.indev0r.game.gui.component.hud;

import hidden.indev0r.game.BitFont;
import hidden.indev0r.game.gui.component.base.GComponent;
import hidden.indev0r.game.gui.menu.GMenu;
import hidden.indev0r.game.reference.References;
import hidden.indev0r.game.texture.Textures;
import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

/**
 * Created by MrDeathJockey on 14/12/17.
 */
public class GComponent$BGMTrackInfo extends GComponent {

    private String title;
    private String author;

    private static final int PHASE_FADE_IN = 0, PHASE_SUSTAIN = 1, PHASE_FADE_OUT = 2;
    private Color color = new Color(0f, 0f, 0f, 1f);
    private Color drawColor = new Color(1f, 1f, 1f, 1f);
    private long animTick;
    private int phase = 0;
    private float animAlpha = 0f;
    private int ticks = 20;

    public GComponent$BGMTrackInfo(String title, String author) {
        super(new Vector2f(References.GAME_WIDTH / 2 - BitFont.widthOf((title.length() > author.length()) ? title : author, 16) / 2 - 32,
                References.GAME_HEIGHT - 32 - BitFont.heightOf(new String[]{title, author})));
        this.title = title;
        this.author = author;

        setSize(BitFont.widthOf((title.length() > author.length()) ? title : author, 16) + 56, BitFont.heightOf(new String[] { title, author }) + 12);
    }

    public void onAdd(GMenu menu) {
        super.onAdd(menu);

        animTick = System.currentTimeMillis();
    }

    public void render(Graphics g) {

        float drawY = position.y + ticks * 2;
        color.a = animAlpha;
        drawColor.a = animAlpha;

        g.setColor(color);
        g.fillRoundRect(position.x, drawY, width, height, 5);

        Textures.Icons.BGM_TRACK.draw(position.x + 8, drawY + height / 2 - Textures.Icons.BGM_TRACK.getHeight() / 2, drawColor);
        BitFont.render(g, title, (int) position.x + 44, (int) drawY + 8, Color.yellow, 16, drawColor.a);
        BitFont.render(g, author, (int) position.x + 44, (int) drawY + 28, Color.white, 16, drawColor.a);
    }

    public void tick(GameContainer gc) {
        switch(phase) {
            case PHASE_FADE_IN:
                if(System.currentTimeMillis() - animTick > 10) {
                    if(animAlpha < 1f) {
                        animAlpha += 0.05f;
                        ticks--;
                    }
                    else {
                        animTick = System.currentTimeMillis() + 2000 + title.split(" ").length * 500;
                        phase++;
                        break;
                    }

                    animTick = System.currentTimeMillis();
                }
                break;
            case PHASE_SUSTAIN:
                if(System.currentTimeMillis() - animTick > 0) {
                    phase++;
                }
                break;
            case PHASE_FADE_OUT:
                if(System.currentTimeMillis() - animTick > 10) {
                    if(animAlpha > 0f) {
                        animAlpha -= 0.05f;
                        ticks++;
                    }
                    else {
                        setRemoved(true);
                        break;
                    }

                    animTick = System.currentTimeMillis();
                }
                break;
        }
    }
}
