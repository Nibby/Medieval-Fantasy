package hidden.indev0r.game.gui.component;

import hidden.indev0r.game.BitFont;
import hidden.indev0r.game.gui.component.base.GComponent;
import hidden.indev0r.game.gui.menu.GMenu;
import hidden.indev0r.game.reference.References;
import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

/**
 * Created by MrDeathJockey on 14/12/12.
 */
public class GComponent$HintVerbose extends GComponent {


    private long tickTime;
    private int duration;
    private String text;
    private Color color;
    private float alpha = 1.0f;
    private int type = 0;
    private int ticks = 0;

    public GComponent$HintVerbose(String text, Color color, int duration, int type) {
        super(new Vector2f(References.GAME_WIDTH / 2 - BitFont.widthOf(text, 16) / 2, References.GAME_HEIGHT / 3 - 10));
        setSize(BitFont.widthOf(text, 16), 20);
        this.duration = duration;
        this.text = text;
        this.color = color;
        this.type = type;

        setRequireFocus(false);
    }

    @Override
    public void onAdd(GMenu menu) {
        super.onAdd(menu);
        tickTime = System.currentTimeMillis();
    }

    public void render(Graphics g) {
        super.render(g);

        BitFont.render(g, text, (int) position.x, (int) position.y - ticks * 2, color, 16, alpha, (type == 1), (type == 1) ? 1 : 0);
    }

    public void tick(GameContainer gc) {
        super.tick(gc);

        if(System.currentTimeMillis() - tickTime > duration) {
            if (System.currentTimeMillis() - tickTime + duration + ticks * 10 > 10) {
                if (alpha > 0f) alpha -= 0.05f;
                else setRemoved(true);

                ticks++;
            }
        }
    }
}
