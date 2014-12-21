package hidden.indev0r.game.particle;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MrDeathJockey on 14/12/18.
 */
public class ParticleManager {

    private List<Particle> particles = new ArrayList<>();

    public void render(Graphics g) {
        for(int i = 0; i < particles.size(); i++) {
            Particle particle = particles.get(i);
            particle.render(g);
        }
    }

    public void tick(GameContainer gc) {
        for(int i = 0; i < particles.size(); i++) {
            Particle particle = particles.get(i);
            particle.tick(gc);

            if(particle.hasDecayed()) {
                removeParticle(particle);
            }
        }
    }

    public void addParticle(Particle particle) {
        particle.onSpawn();
        particles.add(particle);
    }

    private void removeParticle(Particle particle) {
        particles.remove(particle);
    }

    private ParticleManager() {}

    public static final ParticleManager get() { return particleManager; }

    private static final ParticleManager particleManager = new ParticleManager();
}
