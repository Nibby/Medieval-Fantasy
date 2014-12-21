package hidden.indev0r.game.particle.projectile;

import hidden.indev0r.game.Camera;
import hidden.indev0r.game.MedievalLauncher;
import hidden.indev0r.game.entity.Actor;
import hidden.indev0r.game.entity.combat.AttackType;
import hidden.indev0r.game.entity.combat.DamageModel;
import hidden.indev0r.game.map.Tile;
import hidden.indev0r.game.particle.Particle;
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

    private Image image;
    private Actor target;
    private Actor origin;
    private int interval, duration;
    private double angle, oldAngle, defaultAngle;
    private double vx, vy;
    private DamageModel damageModel;
    private AttackType attacKType;
    private long moveTime = System.currentTimeMillis();

    private boolean destinationReached = false;
    private long destinationTick;
    public Projectile(Actor origin, Actor target, AttackType type, DamageModel model, Type texture, double time, int hitIndex) {
        super(origin.getPosition(), false, new Color(0f, 0f, 0f, 0f), texture.getTexture().getWidth(), texture.getTexture().getHeight());
        if(origin == null || target == null) {
            decayed = true;
            return;
        }
        this.image = texture.getTexture();
        this.target = target;
        this.origin = origin;
        this.damageModel = model;
        this.attacKType = type;
        this.interval = (int) time / 50;
        this.duration = (int) time;
        this.defaultAngle = texture.defaultAngle;
        if(interval <= 0) decayed = true;

        setPosition(new Vector2f(this.origin.getRenderX() + this.origin.getWidth() / 2 - image.getWidth() / 2,
                this.origin.getRenderY() + this.origin.getHeight() / 2 - image.getHeight() / 2));
    }

    @Override
    protected void randomize() {

    }

    @Override
    public void tick(GameContainer gc) {
        if(origin == null || target == null) {
            decayed = true;
            return;
        }
        if(System.currentTimeMillis() - moveTime > 6) {

            double xDiff = Math.abs(origin.getX() * Tile.TILE_SIZE - target.getX() * Tile.TILE_SIZE);
            double yDiff = Math.abs(origin.getY() * Tile.TILE_SIZE - target.getY() * Tile.TILE_SIZE);

            double angularBonus = 0d;
            double tx = target.getPosition().x;
            double ty = target.getPosition().y;
            double ox = origin.getPosition().x;
            double oy = origin.getPosition().y;

            if (interval <= 0) interval = 1;

            //Projectile travel velocities
            if (ox - tx == 0) vx = 0;
            else vx = -(ox - tx) / (interval);

            if (oy - ty == 0) vy = 0;
            else vy = -(oy - ty) / (interval);

            //Projectile texture rotation angle
            if ((tx > ox && ty < oy) || (tx == ox && ty < oy)) angularBonus = 0d;
            if ((tx < ox && ty > oy) || (tx == ox && ty > oy)) angularBonus = 180d;
            if ((tx < ox && ty < oy) || (tx < ox && ty == oy)) angularBonus = 270d;
            if ((tx > ox && ty > oy) || (tx > ox && ty == oy)) angularBonus = 90d;

            if(yDiff == 0 || xDiff == 0) angle = angularBonus;
            else angle = Math.toDegrees(Math.tan(xDiff / yDiff)) + angularBonus;
            oldAngle = 0d;

            position.x += vx;
            position.y += vy;

            Camera camera = MedievalLauncher.getInstance().getGameState().getCamera();

            int xx = (int) (position.x - camera.getOffsetX()) / Tile.TILE_SIZE;
            int yy = (int) (position.y - camera.getOffsetY()) / Tile.TILE_SIZE;

            if(!destinationReached &&
                    xx >= (int) target.getX() - 1 && yy >= (int) target.getY() - 1 &&
                    xx <= (int) target.getX() + 1 && yy <= (int) target.getY() + 1) {
                destinationReached = true;
                destinationTick = System.currentTimeMillis();
            }

            if(destinationReached) {
                if(System.currentTimeMillis() - destinationTick > 100)
                    decayed = true;
            }

            if(System.currentTimeMillis() - tickTime > duration) decayed = true;

            moveTime = System.currentTimeMillis();
        }
    }

    @Override
    public void render(Graphics g) {
        if(oldAngle != angle) {
            image.setCenterOfRotation(image.getWidth() / 2, image.getHeight() / 2);
            image.setRotation((float) ((float) angle + defaultAngle));
            oldAngle = angle;
        }

        g.drawImage(image, origin.getRenderX() + (position.x - origin.getRenderX()),
                origin.getRenderY() + (position.y - origin.getRenderY()));
    }

    public enum Type {

        none(Textures.SpriteSheets.HIT_EFFECT_CASTER.getSprite(0, 0), 0),
        bolt_blue_0(Textures.SpriteSheets.HIT_EFFECT_CASTER.getSprite(2, 0), 45),
        bolt_blue_1(Textures.SpriteSheets.HIT_EFFECT_CASTER.getSprite(3, 0), 0),
        bolt_green_0(Textures.SpriteSheets.HIT_EFFECT_CASTER.getSprite(4, 0), 45),
        bolt_green_1(Textures.SpriteSheets.HIT_EFFECT_CASTER.getSprite(5, 0), 0),
        bolt_pink_0(Textures.SpriteSheets.HIT_EFFECT_CASTER.getSprite(6, 0), 45),
        bolt_pnk_0(Textures.SpriteSheets.HIT_EFFECT_CASTER.getSprite(7, 0), 0),
        bolt_orange_0(Textures.SpriteSheets.HIT_EFFECT_CASTER.getSprite(0, 1), 45),
        bolt_orange_1(Textures.SpriteSheets.HIT_EFFECT_CASTER.getSprite(1, 1), 0),
        bolt_angelic_0(Textures.SpriteSheets.HIT_EFFECT_CASTER.getSprite(2, 1), 45),
        bolt_angelic_1(Textures.SpriteSheets.HIT_EFFECT_CASTER.getSprite(3, 1), 0),
        bolt_red_0(Textures.SpriteSheets.HIT_EFFECT_CASTER.getSprite(4, 1), 45),
        bolt_red_1(Textures.SpriteSheets.HIT_EFFECT_CASTER.getSprite(5, 1), 0),
        ;

        private Image texture;
        private int defaultAngle;
        Type(Image texture, int defaultAngle) {
            this.texture = texture;
            this.defaultAngle = defaultAngle;
        }

        public Image getTexture() {
            return texture;
        }
    }
}
