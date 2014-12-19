package hidden.indev0r.game.entity.combat.phase.hit;

import hidden.indev0r.game.entity.Actor;
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
    private DamageModel damageModel;

    protected boolean combatHurt = false;
    protected long combatHurtTick;
    protected Color combatHPColor = new Color(1f, 0f, 0f, 1f);
    protected Color combatHPLaceColor = new Color(1f, 0.5f, 0f, 1f);
    protected int combatHPLaceLength, combatHPBarLength;
    protected int currentHit = 0;

    public AbstractCombatHitPhase(Actor initiator, Actor target) {
        target.addCombatPhase(this);
        initiator.addCombatPhase(this);

        actInitiator = initiator;
        actTarget = target;
        actTarget.addCombatPhase(this);
    }

    @Override
    public void render(Graphics g) {
        if(overrideInitiatorRender())
            renderInitiator(g, actInitiator);

        if(overrideTargetRender())
            renderTarget(g, actTarget);
        renderHitEffects(g, actTarget);

        if(combatHurt && !(actTarget instanceof Player)) {
            int barLength = actTarget.getWidth();
            int barHeight = 4;

            g.setColor(Color.black);
            g.fillRect(actTarget.getRenderX() - 2, actTarget.getRenderY() - 14, barLength + 4, barHeight + 4);

            g.setColor(combatHPLaceColor);
            g.fillRect(actTarget.getRenderX(), actTarget.getRenderY() - 12, combatHPLaceLength, barHeight);

            g.setColor(combatHPColor);
            g.fillRect(actTarget.getRenderX(), actTarget.getRenderY() - 12, combatHPBarLength, barHeight);
        }
    }

    @Override
    public void tick(GameContainer gc) {
        if(!started) {
            init();
            startTime = System.currentTimeMillis();
            started = true;
        }

        if(combatHurt && !(actTarget instanceof Player)) {
            if(System.currentTimeMillis() - combatHurtTick > 50) {
                if(combatHPLaceLength > combatHPBarLength) {
                    combatHPLaceLength -= 2;
                    combatHurtTick = System.currentTimeMillis();
                }
                else {
                    if(System.currentTimeMillis() - combatHurtTick > 1500) combatHurt = false;
                }
            }
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
        if(!(actTarget instanceof Player)) {

            combatHurt = true;
            combatHurtTick = System.currentTimeMillis();

            int totalLength = actTarget.getWidth();
            float percentageBefore = (float) actTarget.getHealth() / (float) actTarget.getHealthMax();
            combatHPLaceLength = (int) ((float) totalLength * percentageBefore);
            if(combatHPLaceLength < 0) combatHPLaceLength = 0;

            float percentageAfter = (float) (actTarget.getHealth() - actualDamage) / (float) actTarget.getHealthMax();
            combatHPBarLength = (int) ((float) totalLength * percentageAfter);
            if(combatHPBarLength < 0) combatHPBarLength = 0;
        }

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
