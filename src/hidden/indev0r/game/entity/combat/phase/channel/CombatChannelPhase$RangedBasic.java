package hidden.indev0r.game.entity.combat.phase.channel;

import hidden.indev0r.game.entity.Actor;
import hidden.indev0r.game.entity.animation.Action;
import hidden.indev0r.game.entity.animation.ActionType;
import hidden.indev0r.game.entity.combat.AttackType;
import hidden.indev0r.game.entity.combat.DamageModel;
import hidden.indev0r.game.particle.ParticleManager;
import hidden.indev0r.game.particle.projectile.LinearRangedProjectile;
import hidden.indev0r.game.particle.projectile.Projectile;

import java.util.Map;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

/**
 * Created by MrDeathJockey on 14/12/21.
 *
 * A ranged mage's basic attack
 */
public class CombatChannelPhase$RangedBasic extends AbstractCombatChannelPhase {

    private static final int MODE_SPRITE = 0, MODE_ACTION_SET = 1;
    private int mode;
    private ActionType actionToPlay;

    private Color tintColor = new Color(150, 217, 242, 220);

    private LinearRangedProjectile projectile;
    private Projectile.Type projectileType = Projectile.Type.bolt_red_0;

    private long channelTickTime;

    public CombatChannelPhase$RangedBasic(DamageModel model, AttackType type, Actor initiator, Actor target) {
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

        if(projectile.hasDecayed()) {
            didHit = projectile.didHitTarget();

            if(didHit) {
                actInitiator.combatChannelEnd(damageModel, attackType, currentHit);
            }

            if(currentHit < damageModel.getHits() - 1 && !actTarget.isDead()) {
                currentHit++;
                doChannel();
                didHit = false;
            } else {
                expired = true;
            }
        }

        if(mode == MODE_SPRITE) {
            if(System.currentTimeMillis() - channelTickTime > 50) {
                if(tintColor.a < 1f) tintColor.a += 0.05f;
                if(tintColor.r < 1f) tintColor.r += 0.05f;
                if(tintColor.g < 1f) tintColor.g += 0.05f;
                if(tintColor.b < 1f) tintColor.b += 0.05f;

                channelTickTime += 50;
            }
        }

        if(actInitiator.isDead()) expired = true;
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
        projectileType = Projectile.Type.valueOf(damageModel.getFxParam(currentHit));
        projectile = new LinearRangedProjectile(actInitiator, actTarget, attackType, damageModel, projectileType, currentHit, 0.4f);
        actInitiator.playSound(projectileType.getChannelSound());
        ParticleManager.get().addParticle(projectile);
    }

    @Override
    public boolean overrideInitiatorRender() {
        return mode == MODE_SPRITE;
    }

    @Override
    public void renderInitiator(Graphics g, Actor initiator) {
        initiator.render(g, tintColor);
    }
}
