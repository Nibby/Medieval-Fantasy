package hidden.indev0r.game.gui.component;

import hidden.indev0r.game.BitFont;
import hidden.indev0r.game.Camera;
import hidden.indev0r.game.MedievalLauncher;
import hidden.indev0r.game.entity.Actor;
import hidden.indev0r.game.gui.component.base.GComponent;
import hidden.indev0r.game.gui.menu.GMenu;
import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

/**
 * Created by MrDeathJockey on 14/12/23.
 */
public class GComponent$StatusVerbose extends GComponent {

    private long tickTime;
    private int duration;
    private String text;
    private Color color;
    private float alpha = 1.0f;
    private int ticks = 0;
    private Actor actor;

    public GComponent$StatusVerbose(Actor actor, Color color, String text, int duration) {
        super(new Vector2f(0, 0));

        int width = BitFont.widthOf(text, 16);
        setSize(width, 20);
        setPosition(new Vector2f(actor.getPosition().x + actor.getWidth() / 2 - width / 2,
                                 actor.getPosition().y - 24));
        setRequireFocus(false);
        this.color = color;
        this.text = text;
        this.actor = actor;
        this.duration = duration;
    }

    @Override
    public void onAdd(GMenu menu) {
        super.onAdd(menu);
        tickTime = System.currentTimeMillis();
    }

    @Override
    public void render(Graphics g) {
        super.render(g);

        BitFont.render(g, text, (int) actor.getRenderX() + actor.getWidth() / 2 - width / 2,
                (int) actor.getRenderY() - 22 - ticks, color, 16, alpha);
    }

    @Override
    public void tick(GameContainer gc) {
        super.tick(gc);

        if(System.currentTimeMillis() - tickTime > duration) {
            if (System.currentTimeMillis() - tickTime + duration + ticks * 100 > 100) {
                if (alpha > 0f) alpha -= 0.025f;
                else setRemoved(true);

                ticks++;
            }
        }
    }
}
