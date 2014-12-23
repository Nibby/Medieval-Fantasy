package hidden.indev0r.game.particle.projectile;

import hidden.indev0r.game.Camera;
import hidden.indev0r.game.MedievalLauncher;
import hidden.indev0r.game.entity.Actor;
import hidden.indev0r.game.entity.combat.AttackType;
import hidden.indev0r.game.entity.combat.DamageModel;
import hidden.indev0r.game.map.MapDirection;
import hidden.indev0r.game.particle.Particle;
import hidden.indev0r.game.particle.ParticleManager;
import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Rectangle;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by MrDeathJockey on 14/12/21.
 */
public class LinearRangedProjectile extends Projectile {

    private static final int MOVE_TICK = 20;

    private double textureAngle;
    private Image texture;
    private double duration;
    private long moveTime;
    private double velocityX, velocityY;

    //Projectile trail
    private static final int PROJECTILE_TRAIL_INTERVAL = 5;
    private long trailTick;
    private List<Vector2f> trailPosition = new ArrayList<>();

    public LinearRangedProjectile(Actor origin, Actor target, AttackType type, DamageModel model, Projectile.Type texture, int hitIndex, float speed) {
        super(origin, target, type, model, texture, speed, hitIndex);

        //Origin position and target position
        Vector2f op = origin.getPosition();
        Vector2f tp = target.getPosition();

        double adjacent = -Math.abs(op.x + origin.getWidth() / 2 - tp.x - target.getWidth() / 2);
        double opposite = -Math.abs(op.y + origin.getHeight() / 2 - tp.y - target.getHeight() / 2);
        double hypotenuse = Math.sqrt(Math.pow(adjacent, 2)) + Math.sqrt(Math.pow(opposite, 2));
        //v = d/t -> t = d/v
        duration = hypotenuse / speed;

        double ab = 0d;

        int ox = (int) origin.getX();
        int oy = (int) origin.getY();
        int tx = (int) target.getX();
        int ty = (int) target.getY();

        if((tx < ox || tx == ox) && ty < oy) ab = 270d;
        if(tx > ox && (ty < oy || ty == oy)) ab = 0d;
        if(tx > ox && (ty > oy || ty == oy)) ab = 90d;
        if((tx < ox || tx == ox) && ty > oy) ab = 180d;

        //tan theta = opposite / adjacent
        // -> tan theta * adjacent = opposite
        // -> theta = opposite / tan adjacent

        if(opposite == 0) {
            if(actorOrigin.getCurrentDirection().equals(MapDirection.LEFT))
                textureAngle = ab - texture.getDefaultAngle();
            else
                textureAngle = ab + texture.getDefaultAngle();
        }
        else textureAngle = Math.toDegrees(Math.atan(opposite / adjacent))
                + texture.getDefaultAngle() + ab;
        
        this.texture = texture.getTexture();
        this.texture.setCenterOfRotation(this.texture.getWidth() / 2, this.texture.getHeight() / 2);
        this.texture.setRotation((float) textureAngle);

        //Velocity (v = distance / time)
        if(ox - tx == 0) velocityX = 0;
        else             velocityX = -(op.x + origin.getWidth() / 2 - tp.x - target.getWidth() / 2) / (double) (MOVE_TICK);

        if(oy - ty == 0) velocityY = 0;
        else             velocityY = -(op.y + origin.getHeight() / 2 - tp.y - target.getHeight() / 2) / (double) (MOVE_TICK);
    }

    @Override
    public void onSpawn() {
        super.onSpawn();
        moveTime = System.currentTimeMillis();
        trailTick = System.currentTimeMillis();
    }

    @Override
    public void render(Graphics g) {
        if(type.equals(Type.bolt_attuned_star)) {
        	texture.rotate(10f);
        }
        Camera camera = MedievalLauncher.getInstance().getGameState().getCamera();
        g.drawImage(texture, position.x + camera.getOffsetX(), position.y + camera.getOffsetY());
    }

    @Override
    public void tick(GameContainer gc) {
        super.tick(gc);

        if(!decayed) {
            Rectangle bounds = new Rectangle(position.x, position.y, texture.getWidth(), texture.getHeight());
            if(bounds.intersects(new Rectangle(actorTarget.getPosition().x, actorTarget.getPosition().y, actorTarget.getWidth(), actorTarget.getHeight()))) {
                hitTarget();
            }

            if(System.currentTimeMillis() - trailTick > PROJECTILE_TRAIL_INTERVAL) {
                final Random r = new Random();
                if(trailPosition.size() > 0) {
                    Vector2f trail = trailPosition.get(r.nextInt(trailPosition.size()));
                    final int size = r.nextInt(4) + 4;

                    Particle trailParticle = new Particle(new Vector2f(trail.x + texture.getWidth() / 2, trail.y + texture.getHeight() / 2),
                            true, Color.white, size, size) {

                        long shrinkTick;
                        int shrinkInterval;

                        int sustainTime;
                        float delta;

                        @Override
                        public void onSpawn() {
                            super.onSpawn();
                            shrinkTick = System.currentTimeMillis();
                        }

                        @Override
                        public void randomize() {
                            shrinkInterval = r.nextInt(100) + 100;
                            sustainTime = size * shrinkInterval;
                            delta = (r.nextFloat() * 0.5f);
                            luminescent = true;
                            lumPulse = 3;
                            lumSize = 3;

                            int ww = texture.getWidth();
                            int hh = texture.getHeight();
                            color = texture.getColor(r.nextInt(ww), r.nextInt(hh));
                        }

                        @Override
                        public void tick(GameContainer gc) {
                            position.x += velocityX * delta;
                            position.y += velocityY * delta;
                            if ((width > 0.1f || height > 0.1f) && System.currentTimeMillis() - shrinkTick > shrinkInterval) {
                                float shrink = r.nextFloat() * 0.45f + 0.1f;
                                width -= shrink;
                                height -= shrink;

                                if (width <= 0f) width = 0.1f;
                                if (height <= 0f) height = 0.1f;
                                shrinkTick = System.currentTimeMillis();
                            }

                            if (System.currentTimeMillis() - tickTime > sustainTime) {
                                decayed = true;
                            }
                        }
                    };
                    trailParticle.randomize();
                    ParticleManager.get().addParticle(trailParticle);
                }
            }
        }

        if(System.currentTimeMillis() - moveTime > duration / MOVE_TICK) {
            trailPosition.add(0, new Vector2f(position.x, position.y));
            position.x += velocityX;
            position.y += velocityY;
            moveTime += duration  / MOVE_TICK;

            if(trailPosition.size() > 25) trailPosition.remove(trailPosition.size() - 1);
        }

        if(System.currentTimeMillis() - tickTime > duration) {
            decayed = true;
        }
    }
}
