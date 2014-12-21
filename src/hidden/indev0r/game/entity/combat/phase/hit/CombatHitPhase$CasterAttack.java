package hidden.indev0r.game.entity.combat.phase.hit;

import hidden.indev0r.game.entity.Actor;
import hidden.indev0r.game.entity.combat.DamageModel;
import hidden.indev0r.game.particle.projectile.CasterAttackProjectile;
import hidden.indev0r.game.particle.projectile.Projectile;
import org.newdawn.slick.Graphics;

/**
 * Created by MrDeathJockey on 14/12/22.
 */
public class CombatHitPhase$CasterAttack extends AbstractCombatHitPhase {

    private CasterAttackProjectile projectile;
    private Projectile.Type projectileType;

    public CombatHitPhase$CasterAttack(DamageModel model, Actor initiator, Actor target, int hitIndex) {
        super(model, initiator, target, hitIndex);

    }

    private void parseFx() {

    }

    @Override
    protected void init() {

    }

    @Override
    public void renderHitEffects(Graphics g, Actor target) {

    }

    @Override
    public boolean overrideTargetRender() {
        return false;
    }

    @Override
    public void renderTarget(Graphics g, Actor target) {

    }

    @Override
    public boolean overrideInitiatorRender() {
        return false;
    }

    @Override
    public void renderInitiator(Graphics g, Actor initiator) {

    }
}
