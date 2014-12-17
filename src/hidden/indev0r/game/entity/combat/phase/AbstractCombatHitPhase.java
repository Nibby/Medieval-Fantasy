package hidden.indev0r.game.entity.combat.phase;

import hidden.indev0r.game.entity.Actor;
import hidden.indev0r.game.entity.combat.DamageModel;
import org.newdawn.slick.GameContainer;

/**
 * Created by MrDeathJockey on 14/12/18.
 */
public abstract class AbstractCombatHitPhase implements CombatHitPhase {

    protected Actor actInitiator;
    protected Actor actTarget;

    protected long startTime;
    protected boolean started = false, expired = false;
    private DamageModel damageModel;

    public AbstractCombatHitPhase(Actor initiator, Actor target) {
        actInitiator = initiator;
        actTarget = target;

        actTarget.addCombatPhase(this);
    }

    @Override
    public void tick(GameContainer gc, Actor initiator) {
        if(!started) {
            init();
            startTime = System.currentTimeMillis();
            started = true;
        }

        if(started && !expired && System.currentTimeMillis() - startTime > getDuration()) {
            expired = true;
        }

        if(expired) {
            initiator.combatEnd();
            initiator.removeCombatPhase(this);
            actTarget.combatEnd();
            actTarget.removeCombatPhase(this);
        }
    }

    protected abstract void init();

    @Override
    public boolean isTarget(Actor actor) {
        return actor.equals(actTarget);
    }

    @Override
    public boolean isInitiator(Actor actor) {
        return actor.equals(actInitiator);
    }

    @Override
    public boolean isExpired() {
        return expired;
    }

    public void setDamageModel(DamageModel damageModel) {
        this.damageModel = damageModel;
    }

    public DamageModel getDamageModel() {
        return damageModel;
    }
}
