package hidden.indev0r.game.entity.combat.phase.channel;

import hidden.indev0r.game.entity.Actor;
import hidden.indev0r.game.entity.combat.AttackType;
import hidden.indev0r.game.entity.combat.DamageModel;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

/**
 * Created by MrDeathJockey on 14/12/18.
 */
public abstract class AbstractCombatChannelPhase implements CombatChannelPhase {

    protected Actor actInitiator;
    protected Actor actTarget;

    protected int currentHit = 0;
    protected long startTime;
    protected boolean started = false, expired = false;

    protected AttackType attackType;
    protected DamageModel damageModel;

    public AbstractCombatChannelPhase(DamageModel model, AttackType type, Actor initiator, Actor target) {
        initiator.addCombatPhase(this);
        this.damageModel = model;
        this.actInitiator = initiator;
        this.actTarget = target;
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

    @Override
    public Actor getInitiator() {
        return actInitiator;
    }

    @Override
    public Actor getTarget() {
        return actTarget;
    }
}
