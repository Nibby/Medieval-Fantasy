package hidden.indev0r.game.entity.combat.phase;

import hidden.indev0r.game.BitFont;
import hidden.indev0r.game.Camera;
import hidden.indev0r.game.MedievalLauncher;
import hidden.indev0r.game.entity.Actor;
import hidden.indev0r.game.texture.Textures;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

/**
 * Created by MrDeathJockey on 14/12/18.
 */
public class CombatHitPhase$Melee extends AbstractCombatHitPhase {

    private Color hurtTint = new Color(1f, 0f, 0f, 0.5f);
    private long textureTintTick = 0;

    private long fxTick = 0;
    private float fx = 16, fy = -16;
    private float fxSpeed = 0f;
    private float fxTicks = 0;
    private Color fxColor = new Color(1f, 1f, 1f, 0f);

    private Color textColor = new Color(1f, 0f, 0f, 1f);

    private static final int HIT_DELAY = 300;
    private int currentHit = 1;
    private long hitTick;

    public CombatHitPhase$Melee(Actor initiator, Actor target) {
        super(initiator, target);
    }

    @Override
    public void tick(GameContainer gc, Actor initiator) {
        super.tick(gc, initiator);

        if(System.currentTimeMillis() - textureTintTick > 10) {
            if(hurtTint.a < 1f) hurtTint.a += 0.05f;
            if(hurtTint.r < 1f) hurtTint.r += 0.05f;
            if(hurtTint.g < 1f) hurtTint.g += 0.05f;
            if(hurtTint.b < 1f) hurtTint.b += 0.05f;
        }

        if(System.currentTimeMillis() - fxTick > 5) {
            fx -= 0.4f * fxTicks;
            fy += 0.4f * fxTicks;
            textColor.a -= 0.035f;
            fxTicks++;
            if(fxTicks < 10) fxColor.a += 0.05f;
            else {
                fxColor.a -= 0.05f;
            }
            fxTick = System.currentTimeMillis();
        }

        if(System.currentTimeMillis() - hitTick > HIT_DELAY) {
            if(currentHit < getDamageModel().getHits()) {
                currentHit++;

                hurtTarget();
                textColor = new Color(1f, 0f, 0f, 1f);

                hurtTint = new Color(1f, 0f, 0f, 0.5f);
                fxColor = new Color(1f, 1f, 1f, 0f);
                fxTicks = 0;
                fx = 16;
                fy = -16;
            } else {
                expired = true;
            }

            hitTick = System.currentTimeMillis();
        }
    }

    private void hurtTarget() {
        actTarget.combatHurt(actInitiator, currentHit - 1, getDamageModel());
    }

    @Override
    protected void init() {
        textureTintTick = System.currentTimeMillis();
        fxTick = System.currentTimeMillis();
        hitTick = System.currentTimeMillis();

        hurtTarget();
    }

    @Override
    public void renderHitEffects(Graphics g, Actor target) {
        Camera camera = MedievalLauncher.getInstance().getGameState().getCamera();
        g.drawImage(Textures.SpriteSheets.EFFECTS_WEAPON.getSprite(0, 0),
                target.getPosition().x + target.getWidth() / 2 - 16 + camera.getOffsetX() + fx,
                target.getPosition().y + target.getHeight() / 2 - 16 + camera.getOffsetY() + fy, fxColor);

        String text = "-" + getDamageModel().getDamage(currentHit - 1);
        BitFont.render(g, text, (int) (target.getPosition().x + target.getWidth() / 2 - BitFont.widthOf(text, 16) / 2 + camera.getOffsetX()),
                (int) (target.getPosition().y + camera.getOffsetY() - 16 - fxTicks), textColor, 16);
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
    public int getDuration() {
        return (200 + HIT_DELAY) * getDamageModel().getHits();
    }

    @Override
    public boolean overrideInitiatorRender() {
        return false;
    }

    @Override
    public void renderInitiator(Graphics g, Actor initiator) {

    }
}
