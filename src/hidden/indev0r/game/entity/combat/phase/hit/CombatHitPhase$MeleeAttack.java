package hidden.indev0r.game.entity.combat.phase.hit;

import hidden.indev0r.game.BitFont;
import hidden.indev0r.game.Camera;
import hidden.indev0r.game.MedievalLauncher;
import hidden.indev0r.game.entity.Actor;
import hidden.indev0r.game.entity.combat.DamageModel;
import hidden.indev0r.game.particle.Particle$CombatHitDebris;
import hidden.indev0r.game.particle.ParticleManager;
import hidden.indev0r.game.sound.SoundType;
import hidden.indev0r.game.texture.Textures;
import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

/**
 * Created by MrDeathJockey on 14/12/18.
 */
public class CombatHitPhase$MeleeAttack extends AbstractCombatHitPhase {

    private Color hurtTint = new Color(1f, 0f, 0f, 0.5f);
    private long textureTintTick = 0;
    private long fxTick = 0;
    private float fx = 16, fy = -16, fw = 32, fh = 32;
    private float fxTicks = 0;
    private Color fxColor = new Color(1f, 1f, 1f, 0f);
    private Color textColor = new Color(1f, 0f, 0f, 1f);
    private int currentHit = 0;
    private long hitTick;
    private Image critBulge;
    private long bulgeTickTime;
    private float bulgeAlpha;
    private int bulgeTick;
    private Image fxTexture;

    public CombatHitPhase$MeleeAttack(DamageModel model, Actor initiator, Actor target, int hitIndex) {
        super(model, initiator, target, hitIndex);

    }

    @Override
    public void tick(GameContainer gc) {
        super.tick(gc);

        if(hurtTint.a >= 1f && hurtTint.r >= 1f && hurtTint.g >= 1f && hurtTint.b >= 1f
                && textColor.a <= 0f || actTarget.isDead()) {
            expired = true;
        }

        if(System.currentTimeMillis() - textureTintTick > 2) {
            if(hurtTint.a < 1f) hurtTint.a += 0.05f;
            if(hurtTint.r < 1f) hurtTint.r += 0.05f;
            if(hurtTint.g < 1f) hurtTint.g += 0.05f;
            if(hurtTint.b < 1f) hurtTint.b += 0.05f;

            textureTintTick = System.currentTimeMillis();
        }

        if(System.currentTimeMillis() - fxTick > 2) {
            fx -= 0.6f * fxTicks;
            fy += 0.6f * fxTicks;
            textColor.a -= 0.035f;
            fxTicks++;
            float tickX = fxTicks / 15f;
            fxColor.a = -tickX * (tickX - 1);
            fxTick = System.currentTimeMillis();
        }
    }

    private void updateHitInfo() {
        textColor = new Color(1f, 0f, 0f, 1f);
        if(getDamageModel().getDamage(currentHit) == 0)
            textColor = new Color(1f, 1f, 1f, 1f);
        if(getDamageModel().isCritical(currentHit))
            textColor = new Color(1f, 0.5f, 0f, 1f);

        if(getDamageModel().isCritical(currentHit)) {
            critBulge = actTarget.getCurrentImage();
            bulgeAlpha = 0.5f;
            bulgeTick = 0;
        }

        if(actualDamage > 0) {
            actTarget.playSound(getDamageModel().getDamageType(currentHit).getHurtSound());

            Camera camera = MedievalLauncher.getInstance().getGameState().getCamera();
            for (int i = 0; i < 10; i++) {

                Particle$CombatHitDebris p = new Particle$CombatHitDebris(
                        new Vector2f(actTarget.getPosition().x + actTarget.getWidth() / 2 + camera.getOffsetX() - 4,
                                actTarget.getPosition().y + actTarget.getHeight() / 2 + camera.getOffsetY()),
                        actTarget.getCurrentImage(),
                        actTarget.getWidth() / 32 * 4 + (int) (Math.random() * 2)
                );
                ParticleManager.get().addParticle(p);
            }
        } else
            actTarget.playSound(SoundType.parry);
    }

    private void parseFx() {
        int textureID = Integer.parseInt(getDamageModel().getFxParam(currentHit));
        fxTexture = Textures.SpriteSheets.HIT_EFFECT_MELEE.getSprite(textureID % 8, textureID / 8);
    }

    @Override
    protected void init() {
        textureTintTick = System.currentTimeMillis();
        fxTick = System.currentTimeMillis();
        hitTick = System.currentTimeMillis();
        bulgeTickTime = System.currentTimeMillis();

        hurtTarget();
        parseFx();
        updateHitInfo();

        if(actualDamage <= 0) {
            hurtTint = new Color(1f, 1f, 1f, 1f);
            textColor = new Color(1f, 1f, 1f, 1f);
        } else {
            Color c = getDamageModel().getDamageType(currentHit).getColor();
            hurtTint = new Color(c.r, c.g, c.b, 0.5f);
        }
        fxColor = new Color(1f, 1f, 1f, 0f);
        fxTicks = 0;
        fx = 16;
        fy = -16;
    }

    @Override
    public void renderHitEffects(Graphics g, Actor target) {
        Camera camera = MedievalLauncher.getInstance().getGameState().getCamera();

        if(getDamageModel().isCritical(currentHit)) {
            Image frame = critBulge;
            Color c = new Color(1f, 1f, 1f, bulgeAlpha);
            if(frame == null) return;
            frame = frame.getScaledCopy(1 + (float) bulgeTick / 15);
            frame.getScaledCopy((int) fw, (int) fh)
                    .draw(target.getPosition().x + camera.getOffsetX() - bulgeTick,
                            target.getPosition().y + camera.getOffsetY() - bulgeTick, c);

            if (System.currentTimeMillis() - bulgeTickTime > 25) {
                bulgeTick++;
                fw += 1.2f;
                fh += 1.2f;
                bulgeAlpha -= 0.05f;
                if (bulgeAlpha <= 0f) {
                    bulgeTick = 0;
                    bulgeTickTime += 1000;
                    return;
                }
                bulgeTickTime = System.currentTimeMillis();
            }
        }

        String text = (actualDamage > 0) ? "-" + actualDamage : "PARRY";
        BitFont.render(g, text, (int) (target.getPosition().x + target.getWidth() / 2 - BitFont.widthOf(text, 16) / 2 + camera.getOffsetX()),
                (int) (target.getPosition().y + camera.getOffsetY() - 28 - fxTicks), textColor, 16);
    }

    @Override
    public boolean overrideTargetRender() {
        return true;
    }

    @Override
    public void renderTarget(Graphics g, Actor target) {
        target.render(g, hurtTint);

        if(fxTexture != null && !target.isDead()) {
            Camera camera = MedievalLauncher.getInstance().getGameState().getCamera();
            g.drawImage(fxTexture,
                    target.getPosition().x + target.getWidth() / 2 - 16 + camera.getOffsetX() + fx,
                    target.getPosition().y + target.getHeight() / 2 - 16 + camera.getOffsetY() + fy,
                    fxColor);
        }
    }

    @Override
    public boolean overrideInitiatorRender() {
        return false;
    }

    @Override
    public void renderInitiator(Graphics g, Actor initiator) {

    }
}
