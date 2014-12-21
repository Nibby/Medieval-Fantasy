package hidden.indev0r.game.particle.projectile;

import hidden.indev0r.game.entity.Actor;
import hidden.indev0r.game.entity.combat.AttackType;
import hidden.indev0r.game.entity.combat.DamageModel;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

/**
 * Created by MrDeathJockey on 14/12/21.
 */
public class CasterAttackProjectile extends Projectile {

    public CasterAttackProjectile(Actor origin, Actor target, AttackType type, DamageModel model, Projectile.Type texture, int hitIndex) {
        super(origin, target, type, model, texture, 1200, hitIndex);


    }

    @Override
    public void render(Graphics g) {
        super.render(g);
    }

    @Override
    public void tick(GameContainer gc) {
        super.tick(gc);
    }
}
