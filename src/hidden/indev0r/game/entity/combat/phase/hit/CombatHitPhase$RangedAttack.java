package hidden.indev0r.game.entity.combat.phase.hit;

import hidden.indev0r.game.entity.Actor;
import hidden.indev0r.game.entity.combat.DamageModel;
import hidden.indev0r.game.particle.Particle;
import hidden.indev0r.game.particle.Particle$TexturePiece;
import hidden.indev0r.game.particle.ParticleManager;
import hidden.indev0r.game.particle.projectile.Projectile;
import hidden.indev0r.game.sound.SoundType;
import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

import java.util.Random;

/**
 * Created by MrDeathJockey on 14/12/22.
 */
public class CombatHitPhase$RangedAttack extends AbstractCombatHitPhase {

    private Color hurtTint = new Color(1f, 0f, 0f, 0.5f);
    private long textureTintTick;
    private Image texture;
    private Particle$TexturePiece[] particlePart;

    public CombatHitPhase$RangedAttack(DamageModel model, Actor initiator, Actor target, int hitIndex) {
        super(model, initiator, target, hitIndex);

    }

    private void parseFx() {
        texture = Projectile.Type.valueOf(damageModel.getFxParam(currentHit)).getTexture();

    }

    @Override
    protected void init() {
        parseFx();

        textureTintTick = System.currentTimeMillis();
        int dmg = damageModel.getDamage(currentHit);
        if(dmg <= 0)
            hurtTint = new Color(1f, 1f, 1f, 1f);

        int w = texture.getWidth();
        int h = texture.getHeight();

        boolean crit = damageModel.isCritical(currentHit);
        int size = (crit) ? 2 : 4;
        particlePart = new Particle$TexturePiece[(w * h) / size];

        for(int i = 0; i < w / size; i++) {
            for(int j = 0; j < h / size; j++) {
                Particle$TexturePiece p = new Particle$TexturePiece(
                        new Vector2f((int) actTarget.getPosition().x + actTarget.getWidth() / 2,
                                (int) getTarget().getPosition().y + actTarget.getHeight() / 2),
                        true, texture, i * size, j * size, 2) {

                    private long moveTick, decayTick;
                    private float vx, vy;

                    public void randomize() {
                        Random r = new Random();

                        vx = r.nextFloat() * 2f - 1f;
                        vy = r.nextFloat() * 2f - 1f;

                        int size = r.nextInt(2) + 2;
                        setSizes(size, size);
                        setLuminescent(true);
                        setLuminescentPulse(2);
                        setLuminscentSize(2);
                    }

                    public void onSpawn() {
                        moveTick = System.currentTimeMillis();
                        decayTick = System.currentTimeMillis();
                    }

                    @Override
                    public void tick(GameContainer gc) {
                        super.tick(gc);

                        if(System.currentTimeMillis() - moveTick > 20) {
                            position.x += vx;
                            position.y += vy;

                            moveTick += 20;
                        }

                        if(System.currentTimeMillis() - decayTick > 100) {
                            if(getWidth() > 0f) width -= 0.5f;
                            if(getHeight() > 0f) height -= 0.5f;

                            if(getWidth() == 0 && getHeight() == 0f)
                                decayed = true;

                            decayTick += 100;
                        }
                    }

                    @Override
                    public void render(Graphics g) {
                        super.render(g);
                    }
                };
                p.randomize();

                particlePart[i + j * w / size] = p;
                ParticleManager.get().addParticle(p);
            }
        }

        actTarget.playSound(Projectile.Type.valueOf(damageModel.getFxParam(currentHit)).getHurtSound());
        hurtTarget();
    }

    @Override
    public void tick(GameContainer gc) {
        super.tick(gc);

        if(System.currentTimeMillis() - textureTintTick > 2) {
            if(hurtTint.a < 1f) hurtTint.a += 0.05f;
            if(hurtTint.r < 1f) hurtTint.r += 0.05f;
            if(hurtTint.g < 1f) hurtTint.g += 0.05f;
            if(hurtTint.b < 1f) hurtTint.b += 0.05f;

            textureTintTick = System.currentTimeMillis();
        }

        boolean canExpire = true;
        for(Particle p : particlePart) {
            if(p != null)
                if(!p.hasDecayed()) canExpire = false;
        }
        if(canExpire) {
            expired = true;
        }
    }

    @Override
    public void renderHitEffects(Graphics g, Actor target) {

    }

    @Override
    public boolean overrideTargetRender() {
        return true;
    }

    @Override
    public void renderTarget(Graphics g, Actor target) {
        target.render(g, hurtTint);
    }

    @Override
    public boolean overrideInitiatorRender() {
        return false;
    }

    @Override
    public void renderInitiator(Graphics g, Actor initiator) {

    }
}
