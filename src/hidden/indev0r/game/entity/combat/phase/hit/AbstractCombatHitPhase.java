package hidden.indev0r.game.entity.combat.phase.hit;

import hidden.indev0r.game.entity.Actor;
import hidden.indev0r.game.entity.NPC;
import hidden.indev0r.game.entity.Player;
import hidden.indev0r.game.entity.combat.DamageModel;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

/**
 * Created by MrDeathJockey on 14/12/18.
 */
public abstract class AbstractCombatHitPhase implements CombatHitPhase {

    protected Actor actInitiator;
    protected Actor actTarget;

    protected long startTime;
    protected boolean started = false, expired = false;
    protected DamageModel damageModel;
    protected int currentHit = 0;


    public AbstractCombatHitPhase(Actor initiator, Actor target) {
        if(target == null) return;
        if(initiator == null) return;
        target.addCombatPhase(this);
        initiator.addCombatPhase(this);

        actInitiator = initiator;
        actTarget = target;
        actTarget.addCombatPhase(this);
    }

    @Override
    public void render(Graphics g) {
        if (overrideInitiatorRender())
            renderInitiator(g, actInitiator);

        if (overrideTargetRender())
            renderTarget(g, actTarget);
        renderHitEffects(g, actTarget);
    }

    @Override
    public void tick(GameContainer gc) {
        if(actTarget == null) {
            expired = true;
            return;
        }
        if(actInitiator == null) {
            expired = true;
            return;
        }

        if(!started) {
            init();
            startTime = System.currentTimeMillis();
            started = true;
        }

        if(started && !expired && System.currentTimeMillis() - startTime > getDuration()) {
            expired = true;
        }

        if(expired) {
            actInitiator.combatEnd();
            actInitiator.removeCombatPhase(this);
            actTarget.combatEnd();
            actTarget.removeCombatPhase(this);
        }
    }

    protected void hurtTarget() {
        int actualDamage = getDamageModel().getDamageType(currentHit).processDamage(getDamageModel(), actTarget, actInitiator);

        actTarget.combatHurt(actInitiator, currentHit, getDamageModel(), actualDamage);
        if(actTarget.isDead()) {
            expired = true;
            actInitiator.combatEnd();
            actInitiator.removeCombatPhase(this);
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
