package hidden.indev0r.game.particle.projectile;

import hidden.indev0r.game.entity.Actor;
import hidden.indev0r.game.entity.combat.AttackType;
import hidden.indev0r.game.entity.combat.DamageModel;
import hidden.indev0r.game.particle.Particle;
import hidden.indev0r.game.sound.SE;
import hidden.indev0r.game.sound.SoundType;
import hidden.indev0r.game.texture.Textures;
import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

/**
 * Created by MrDeathJockey on 14/12/21.
 */
public abstract class Projectile extends Particle {

    protected Actor actorOrigin, actorTarget;
    protected AttackType attackType;
    protected DamageModel damageModel;
    protected Type type;
    protected int travelTime;
    protected int hitIndex;
    protected boolean didHitTarget;

    public Projectile(Actor origin, Actor target, AttackType type, DamageModel model, Type texture, double time, int hitIndex) {
        super(new Vector2f(origin.getPosition().x, origin.getPosition().y), false, new Color(0f, 0f, 0f, 0f), texture.getTexture().getWidth(), texture.getTexture().getHeight());

        this.actorOrigin = origin;
        this.actorTarget = target;
        this.attackType = type;
        this.damageModel = model;
        this.type = texture;
        this.travelTime = (int) time;
        this.hitIndex = hitIndex;
        didHitTarget = false;
    }

    @Override
    public void randomize() {

    }

    @Override
    public void tick(GameContainer gc) {
    }

    @Override
    public void render(Graphics g) {
    }

    protected void hitTarget() {
        didHitTarget = true;
        decayed = true;
    }

    public boolean didHitTarget() {
        return didHitTarget;
    }

    public enum Type {

        bolt_white_0(Textures.SpriteSheets.HIT_EFFECT_CASTER.getSprite(0, 0), SE.CHANNEL_3, SE.CAST_HURT_3, 45),
        bolt_attuned_star(Textures.SpriteSheets.HIT_EFFECT_CASTER.getSprite(0, 1), SE.CHANNEL_8, SE.CAST_HURT_1, 0),
        bolt_mero_mero_0(Textures.SpriteSheets.HIT_EFFECT_CASTER.getSprite(0, 2), SE.CHANNEL_9, SE.CAST_HURT_6, 45),

        bolt_red_0(Textures.SpriteSheets.HIT_EFFECT_CASTER.getSprite(1, 0), SE.CHANNEL_2, SE.CAST_HURT_6, 45),
        bolt_red_1(Textures.SpriteSheets.HIT_EFFECT_CASTER.getSprite(1, 1), SE.CHANNEL_4, SE.CAST_HURT_5, 45),
        bolt_red_2(Textures.SpriteSheets.HIT_EFFECT_CASTER.getSprite(1, 2), SE.CHANNEL_5, SE.CAST_HURT_5, 45),
        bolt_red_3(Textures.SpriteSheets.HIT_EFFECT_CASTER.getSprite(1, 3), SE.CHANNEL_1, SE.CAST_HURT_7, 45),
        bolt_red_4(Textures.SpriteSheets.HIT_EFFECT_CASTER.getSprite(1, 4), SE.CHANNEL_1, SE.CAST_HURT_8, 45),
        bolt_red_5(Textures.SpriteSheets.HIT_EFFECT_CASTER.getSprite(1, 5), SE.CHANNEL_1, SE.CAST_HURT_8, 0),

        bolt_blue_0(Textures.SpriteSheets.HIT_EFFECT_CASTER.getSprite(2, 0), SE.CHANNEL_2, SE.CAST_HURT_6,  45),
        bolt_blue_1(Textures.SpriteSheets.HIT_EFFECT_CASTER.getSprite(2, 1), SE.CHANNEL_4, SE.CAST_HURT_5, 45),
        bolt_blue_4(Textures.SpriteSheets.HIT_EFFECT_CASTER.getSprite(2, 4), SE.CHANNEL_1, SE.CAST_HURT_8, 45),
        bolt_blue_5(Textures.SpriteSheets.HIT_EFFECT_CASTER.getSprite(2, 5), SE.CHANNEL_1, SE.CAST_HURT_8, 0),

        bolt_green_0(Textures.SpriteSheets.HIT_EFFECT_CASTER.getSprite(3, 0), SE.CHANNEL_2, SE.CAST_HURT_2,  45),
        bolt_green_1(Textures.SpriteSheets.HIT_EFFECT_CASTER.getSprite(3, 1), SE.CHANNEL_4, SE.CAST_HURT_7, 45),
        bolt_green_4(Textures.SpriteSheets.HIT_EFFECT_CASTER.getSprite(3, 4), SE.CHANNEL_1, SE.CAST_HURT_8, 45),
        bolt_green_5(Textures.SpriteSheets.HIT_EFFECT_CASTER.getSprite(3, 5), SE.CHANNEL_1, SE.CAST_HURT_8, 0),

        bolt_pink_0(Textures.SpriteSheets.HIT_EFFECT_CASTER.getSprite(4, 0), SE.CHANNEL_2, SE.CAST_HURT_6,  45),
        bolt_pink_1(Textures.SpriteSheets.HIT_EFFECT_CASTER.getSprite(4, 1), SE.CHANNEL_4, SE.CAST_HURT_0, 45),
        bolt_pink_4(Textures.SpriteSheets.HIT_EFFECT_CASTER.getSprite(4, 4), SE.CHANNEL_1, SE.CAST_HURT_0, 45),
        bolt_pink_5(Textures.SpriteSheets.HIT_EFFECT_CASTER.getSprite(4, 5), SE.CHANNEL_1, SE.CAST_HURT_8, 0),

        bolt_shadow_0(Textures.SpriteSheets.HIT_EFFECT_CASTER.getSprite(5, 0), SE.CHANNEL_8, SE.CAST_HURT_2, 45),
        bolt_shadow_1(Textures.SpriteSheets.HIT_EFFECT_CASTER.getSprite(5, 1), SE.CHANNEL_7, SE.CAST_HURT_2, 45),

        bolt_angelic_0(Textures.SpriteSheets.HIT_EFFECT_CASTER.getSprite(5, 2), SE.CHANNEL_0,  SE.CAST_HURT_1, 45),
        bolt_angelic_1(Textures.SpriteSheets.HIT_EFFECT_CASTER.getSprite(6, 2), SE.CHANNEL_0, SE.CAST_HURT_1, 0),

        ;

        private Image texture;
        private int defaultAngle;
        private SE channel, hurt;

        Type(Image texture, SE channel, SE hurt, int defaultAngle) {
            this.texture = texture;
            this.defaultAngle = defaultAngle;
            this.channel = channel;
            this.hurt = hurt;
        }

        public Image getTexture() {
            return texture;
        }

        public int getDefaultAngle() {
            return defaultAngle;
        }

        public SE getChannelSound() {
            return channel;
        }

        public SE getHurtSound() {
            return hurt;
        }
    }
}
