package hidden.indev0r.game.gui.component;

import hidden.indev0r.game.entity.Actor;
import hidden.indev0r.game.gui.component.base.GComponent;
import hidden.indev0r.game.gui.menu.GMenu;
import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

/**
 * Created by MrDeathJockey on 14/12/24.
 */
public class GComponent$ActorHealthGauge extends GComponent {

    private final Color BASE_COLOR = new Color(0f, 0f, 0f, 1f);

    private long fadeTime;
    private float fadeAlpha = 1.0f;
    private boolean fading = false;

    private long tickTime;
    private Actor actor;

    public GComponent$ActorHealthGauge(Actor actor) {
        super(new Vector2f(0, 0));
        this.actor = actor;
        actor.showingCombatHPGauge = true;

        setRequireFocus(false);
    }

    @Override
    public void render(Graphics g) {
        super.render(g);

        int barLength = actor.getWidth();
        int barHeight = 4;

        BASE_COLOR.a = fadeAlpha;

        g.setColor(BASE_COLOR);
        g.fillRect(actor.getRenderX() - 2, actor.getRenderY() - 14, barLength + 4, barHeight + 4);

        Color lace = actor.combatHPLaceColor;
        g.setColor(new Color(lace.r, lace.g, lace.b, fadeAlpha));
        g.fillRect(actor.getRenderX(), actor.getRenderY() - 12, actor.combatHPLaceLength, barHeight);

        Color current = actor.combatHPColor;
        g.setColor(new Color(current.r, current.g, current.b, fadeAlpha));
        g.fillRect(actor.getRenderX(), actor.getRenderY() - 12, actor.combatHPBarLength, barHeight);
    }

    @Override
    public void tick(GameContainer gc) {
        super.tick(gc);

        if(actor.isDead()) setRemoved(true);

        if(!fading && System.currentTimeMillis() - tickTime > 10000) {
            fading = true;
            fadeTime = System.currentTimeMillis();
        }

        if(fading && System.currentTimeMillis() - fadeTime > 50) {
            if(fadeAlpha > 0f) fadeAlpha -= 0.05f;
            else {
                actor.showingCombatHPGauge = false;
                this.setRemoved(true);
            }
            fadeTime += 50;
        }
    }

    @Override
    public void onAdd(GMenu parent) {
        super.onAdd(parent);
        tickTime = System.currentTimeMillis();
    }
}
