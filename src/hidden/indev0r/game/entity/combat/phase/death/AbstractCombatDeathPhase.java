package hidden.indev0r.game.entity.combat.phase.death;

import hidden.indev0r.game.entity.Actor;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

/**
 * Created by MrDeathJockey on 14/12/19.
 */
public abstract class AbstractCombatDeathPhase implements CombatDeathPhase {

    protected Actor actInitiator;
    protected boolean started = false, expired = false;
    protected long startTime;

    public AbstractCombatDeathPhase(Actor actor) {
        this.actInitiator = actor;
    }

    @Override
    public void render(Graphics g) {
        renderInitiator(g, actInitiator);
    }

    @Override
    public void tick(GameContainer gc) {
        if(!started) {
            init();
            started = true;
            startTime = System.currentTimeMillis();
        }

        if(System.currentTimeMillis() - startTime > getDuration()) {
            expired = true;
            actInitiator.removeCombatPhase(this);
        }
    }

    protected abstract void init();

    @Override
    public boolean overrideInitiatorRender() {
        return true;
    }

    @Override
    public boolean isInitiator(Actor actor) {
        return actor.equals(actInitiator);
    }

    @Override
    public boolean isExpired() {
        return false;
    }
}
