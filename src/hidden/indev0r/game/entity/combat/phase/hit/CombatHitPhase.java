package hidden.indev0r.game.entity.combat.phase.hit;

import hidden.indev0r.game.entity.Actor;
import hidden.indev0r.game.entity.combat.phase.CombatPhase;
import org.newdawn.slick.Graphics;

/**
 * Created by MrDeathJockey on 14/12/18.
 */
public interface CombatHitPhase extends CombatPhase {

    public void renderHitEffects(Graphics g, Actor target);

    public boolean overrideTargetRender();
    public void renderTarget(Graphics g, Actor target);

    public boolean isTarget(Actor actor);
}
