package hidden.indev0r.game.entity.combat.phase;

import hidden.indev0r.game.entity.Actor;
import hidden.indev0r.game.entity.combat.AttackType;
import org.newdawn.slick.GameContainer;

/**
 * Created by MrDeathJockey on 14/12/18.
 */
public abstract class AbstractCombatChannelPhase implements CombatChannelPhase {

    protected Actor actInitiator;

    protected long startTime;
    protected boolean started = false, expired = false;

    protected AttackType attackType;

    public AbstractCombatChannelPhase(AttackType type, Actor actor) {
        this.actInitiator = actor;
        this.attackType = type;
    }

    @Override
    public void tick(GameContainer gc, Actor initiator) {
        if(!started) {
            startTime = System.currentTimeMillis();
            started = true;
        }

        if(started && !expired && System.currentTimeMillis() - startTime > getDuration()) {
            expired = true;
            actInitiator.combatChannelEnd(attackType);
        }
    }

    @Override
    public boolean isInitiator(Actor actor) {
        return actor.equals(actInitiator);
    }

    @Override
    public boolean isExpired() {
        return expired;
    }
}
