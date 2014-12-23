package hidden.indev0r.game.entity.combat.phase.channel;

import hidden.indev0r.game.entity.Actor;
import hidden.indev0r.game.entity.animation.Action;
import hidden.indev0r.game.entity.animation.ActionType;
import hidden.indev0r.game.entity.combat.AttackType;
import hidden.indev0r.game.entity.combat.DamageModel;
import hidden.indev0r.game.particle.ParticleManager;
import hidden.indev0r.game.particle.projectile.CasterAttackProjectile;
import hidden.indev0r.game.particle.projectile.Projectile;

import java.util.Map;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

/**
 * Created by MrDeathJockey on 14/12/21.
 *
 * A ranged mage's basic attack
 */
public class CombatChannelPhase$CasterBasic extends AbstractCombatChannelPhase {

    private static final int MODE_SPRITE = 0, MODE_ACTION_SET = 1;
    private int mode;
    private ActionType actionToPlay;

    private CasterAttackProjectile projectile;
    private Projectile.Type projectileType = Projectile.Type.bolt_red_0;

    private long channelTickTime;

    public CombatChannelPhase$CasterBasic(DamageModel model, AttackType type, Actor initiator, Actor target) {
        super(model, type, initiator, target);

        Map<ActionType, Action> actionMap = initiator.getActionMap();
        if (actionMap != null) {
            switch (initiator.getCurrentDirection()) {
                case UP:
                    actionToPlay = ActionType.ATTACK_UP;
                    break;
                case DOWN:
                    actionToPlay = ActionType.ATTACK_DOWN;
                    break;
                case LEFT:
                    actionToPlay = ActionType.ATTACK_LEFT;
                    break;
                case RIGHT:
                    actionToPlay = ActionType.ATTACK_RIGHT;
                    break;
            }
            mode = MODE_ACTION_SET;
        } else {

            mode = MODE_SPRITE;
        }
    }

    private boolean didHit = false;
    @Override
    public void tick(GameContainer gc) {
        super.tick(gc);

        if(mode == MODE_ACTION_SET) {
            if (System.currentTimeMillis() - channelTickTime >
                    getInitiator().getActionMap().get(actionToPlay).getPlayTime() + 50) {
                if(currentHit < damageModel.getHits() - 1 && !actTarget.isDead()) {
                    currentHit++;
                    doChannel();
                    didHit = false;
                } else {
                    expired = true;
                }
                channelTickTime = System.currentTimeMillis();
            }
        }

        if(projectile.hasDecayed() && !didHit) {
            actInitiator.combatChannelEnd(damageModel, attackType, currentHit);
            didHit = true;
        }
    }

    @Override
    protected void init() {
        channelTickTime = System.currentTimeMillis();
        doChannel();
    }

    private void doChannel() {
        if(mode == MODE_ACTION_SET) {
            actInitiator.forceActAction(actionToPlay);
        }
        //TODO projectile type adapts with initiator equipment
        projectileType = Projectile.Type.bolt_white_0;
        projectile = new CasterAttackProjectile(actInitiator, actTarget, attackType, damageModel, projectileType, currentHit, 0.4f);
        actInitiator.playSound(projectileType.getSound());
        ParticleManager.get().addParticle(projectile);
    }

    @Override
    public boolean overrideInitiatorRender() {
        return mode == MODE_SPRITE;
    }

    @Override
    public void renderInitiator(Graphics g, Actor initiator) {

    }
}
