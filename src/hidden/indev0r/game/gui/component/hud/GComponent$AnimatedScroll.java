package hidden.indev0r.game.gui.component.hud;

import hidden.indev0r.game.BitFont;
import hidden.indev0r.game.gui.component.base.GComponent;
import hidden.indev0r.game.gui.menu.GMenu;
import hidden.indev0r.game.texture.Textures;
import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

/**
 * Created by MrDeathJockey on 14/12/9.
 */
public class GComponent$AnimatedScroll extends GComponent {

    private static final int PHASE_INITIAL_FADE_IN = 0,
                             PHASE_SCROLL_EXPAND = 1,
                             PHASE_TEXT_FADE_IN = 2,
                             PHASE_TEXT_FADE_OUT = 3,
                             PHASE_SCROLL_COLLAPSE = 4,
                             PHASE_FINAL_FADE_OUT = 5;
    private long phaseTick;
    private int duration;
    private String text;

    private float scrollAlpha = 0f, textAlpha = 0f;
    private int expandWidth = 0, expandMaxWidth = 182;

    private int phase = 0;

    public GComponent$AnimatedScroll(String text, int duration) {
        super(new Vector2f(215, 15));
        this.text = text;
        this.duration = duration;
    }

    @Override
    public void onAdd(GMenu parent) {
        super.onAdd(parent);
        phase = 0;
        phaseTick = System.currentTimeMillis();
    }

    @Override
    public void render(Graphics g) {
        Textures.UI.SCROLL_LEFT.setAlpha(scrollAlpha);
        Image center = Textures.UI.SCROLL_CENTER.getScaledCopy(expandWidth, Textures.UI.SCROLL_CENTER.getHeight());
        center.setAlpha(scrollAlpha);
        Textures.UI.SCROLL_RIGHT.setAlpha(scrollAlpha);

        g.pushTransform();
        g.scale(1.5f, 1.5f);
        g.drawImage(Textures.UI.SCROLL_LEFT, position.x, position.y);
        g.drawImage(center, position.x + (Textures.UI.SCROLL_LEFT.getWidth()),  position.y);
        g.drawImage(Textures.UI.SCROLL_RIGHT, position.x + Textures.UI.SCROLL_LEFT.getWidth() + expandWidth, position.y);
        g.popTransform();

        BitFont.render(g, text,
                (int) (position.x * 1.5 + Textures.UI.SCROLL_LEFT.getWidth() * 1.5 + expandMaxWidth * 1.5 / 2 - BitFont.widthOf(text, 16) / 2),
                (int) (position.y * 1.5) + 30, Color.black, 16, textAlpha);
    }

    @Override
    public void tick(GameContainer gc) {
        switch(phase) {
            case PHASE_INITIAL_FADE_IN:
                if(System.currentTimeMillis() - phaseTick > 10) {
                    if(scrollAlpha < 1f) scrollAlpha += 0.05f;
                    else phase++;

                    phaseTick = System.currentTimeMillis();
                }
                break;

            case PHASE_SCROLL_EXPAND:
                if(System.currentTimeMillis() - phaseTick > 5) {
                    if(expandWidth < expandMaxWidth) expandWidth += 5;
                    else phase++;
                    phaseTick = System.currentTimeMillis();
                }
                break;

            case PHASE_TEXT_FADE_IN:
                if(System.currentTimeMillis() - phaseTick > 10) {
                    if(textAlpha < 1f) textAlpha += 0.05f;
                    else {
                        phase++;
                        phaseTick = System.currentTimeMillis() + duration;
                        return;
                    }

                    phaseTick = System.currentTimeMillis();
                }
                break;
            case PHASE_TEXT_FADE_OUT:
                if(System.currentTimeMillis() - phaseTick > 10) {
                    if(textAlpha > 0f) textAlpha -= 0.05f;
                    else {
                        phaseTick = System.currentTimeMillis() + 250;
                        phase++;
                        return;
                    }

                    phaseTick = System.currentTimeMillis();
                }
                break;

            case PHASE_SCROLL_COLLAPSE:
                if(System.currentTimeMillis() - phaseTick > 5) {
                    if(expandWidth > 5) expandWidth -= 5;
                    else {
                        phaseTick = System.currentTimeMillis() + 500;
                        phase++;
                    }
                    phaseTick = System.currentTimeMillis();
                }
                break;

            case PHASE_FINAL_FADE_OUT:
                if(System.currentTimeMillis() - phaseTick > 10) {
                    if(scrollAlpha > 0f) scrollAlpha -= 0.05f;
                    else setRemoved(true);

                    phaseTick = System.currentTimeMillis();
                }
                break;
        }
    }
}
