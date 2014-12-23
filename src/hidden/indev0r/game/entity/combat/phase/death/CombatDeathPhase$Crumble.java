package hidden.indev0r.game.entity.combat.phase.death;

import hidden.indev0r.game.entity.Actor;
import hidden.indev0r.game.entity.combat.DamageModel;
import hidden.indev0r.game.map.Tile;
import hidden.indev0r.game.particle.Particle$TexturePiece;
import hidden.indev0r.game.particle.ParticleManager;
import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

/**
 * Created by MrDeathJockey on 14/12/19.
 */
public class CombatDeathPhase$Crumble extends AbstractCombatDeathPhase{

    private Particle$TexturePiece[] texturePieces;
    private int partSize = 4;

    public CombatDeathPhase$Crumble(DamageModel model, Actor actor, Actor target) {
        super(model, actor, target);

        Image texture = actor.getCurrentImage();
        partSize = 4 * (actor.getWidth() / 32);
        int tw = texture.getWidth() / partSize;
        int th = texture.getHeight() / partSize;
        texturePieces = new Particle$TexturePiece[tw * th];

        for(int x = 0; x < tw; x++) {
            for(int y = 0; y < th; y++) {
                Particle$TexturePiece particle = new Particle$TexturePiece(
                        new Vector2f(actor.getPosition().x + x * partSize, actor.getPosition().y + y * partSize),
                        true, texture, x * 4, y * 4, partSize) {

                    float rotationDelta;
                    float crumbleX, crumbleY;
                    int baseline;
                    long tickTime;

                    @Override
                    public void randomize() {
                        super.randomize();

                        rotationDelta = 0;
                        baseline = (int) position.y + (int) (Math.random() * (Tile.TILE_SIZE / 3)) + Tile.TILE_SIZE / 2;
                        crumbleX = (float) (Math.random() * 0.06f) - 0.03f;
                        crumbleY = (float) (Math.random() * 0.75f) + 0.25f;
                        tickTime = System.currentTimeMillis();
                    }

                    @Override
                    public void tick(GameContainer gc) {
                        super.tick(gc);
                        if(System.currentTimeMillis() - tickTime > 5) {
                            if(position.y < baseline) {
                                position.x += crumbleX;
                                position.y += crumbleY;
                            } else {
                                setRenderType(TYPE_BACKGROUND);
                                if(System.currentTimeMillis() - tickTime > 10) {
                                    if(color.a > 0f) color.a -= 0.005f;
                                    else decayed = true;
                                }
                            }

                            tickTime = System.currentTimeMillis();
                        }
                    }

                };
                particle.randomize();
                ParticleManager.get().addParticle(particle);

                texturePieces[x + y * texture.getWidth() / partSize] = particle;
            }
        }
    }

    @Override
    public void tick(GameContainer gc) {
        super.tick(gc);
        if(System.currentTimeMillis() - startTime > 1500) expired = true;
    }

    @Override
    protected void init() {

    }

    @Override
    public void renderInitiator(Graphics g, Actor initiator) {

    }
}
