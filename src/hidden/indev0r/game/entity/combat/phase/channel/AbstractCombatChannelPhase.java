package hidden.indev0r.game.entity.combat.phase.channel;

import hidden.indev0r.game.entity.Actor;
import hidden.indev0r.game.entity.combat.AttackType;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

/**
 * Created by MrDeathJockey on 14/12/18.
 */
public abstract class AbstractCombatChannelPhase implements CombatChannelPhase {

    protected Actor actInitiator;

    protected long startTime;
    protected boolean started = false, expired = false;

    protected AttackType attackType;

    public AbstractCombatChannelPhase(AttackType type, Actor actor) {
        actor.addCombatPhase(this);

        this.actInitiator = actor;
        this.attackType = type;
    }

    @Override
    public void render(Graphics g) {
        if(overrideInitiatorRender())
            renderInitiator(g, actInitiator);
    }

    @Override
    public void tick(GameContainer gc) {
        if(!started) {
            startTime = System.currentTimeMillis();
            init();
            started = true;
        }

        if(started && !expired && System.currentTimeMillis() - startTime > getDuration()) {
            expired = true;
            actInitiator.removeCombatPhase(this);
            actInitiator.combatChannelEnd(attackType);
        }
    }

    protected abstract void init();

    @Override
    public boolean isInitiator(Actor actor) {
        return actor.equals(actInitiator);
    }

    @Override
    public boolean isExpired() {
        return expired;
    }
}
