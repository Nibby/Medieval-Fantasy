package hidden.indev0r.game.entity.combat.phase.death;

import hidden.indev0r.game.entity.Actor;
import hidden.indev0r.game.entity.animation.Action;
import hidden.indev0r.game.entity.animation.ActionType;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

/**
 * Created by MrDeathJockey on 14/12/19.
 */
public class CombatDeathPhase$ActionSet extends AbstractCombatDeathPhase {

    private Action action;

    public CombatDeathPhase$ActionSet(Actor actor) {
        super(actor);

        action = actor.getActionMap().get(ActionType.DEATH);
    }

    @Override
    protected void init() {

    }

    @Override
    public int getDuration() {
        return action.getPlayTime();
    }

    @Override
    public void renderInitiator(Graphics g, Actor initiator) {
        action.render(g, initiator, initiator.getRenderX(), initiator.getRenderY(), new Color(1f, 1f, 1f, 1f), true);
    }
}
