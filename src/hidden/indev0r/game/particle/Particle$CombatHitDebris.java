package hidden.indev0r.game.particle;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Vector2f;

/**
 * Created by MrDeathJockey on 14/12/18.
 */
public class Particle$CombatHitDebris extends Particle {

    private Image sprite;
    private float dx, dy;
    private int ticks = 0;

    public Particle$CombatHitDebris(Vector2f position, Image sprite, int size) {
        super(position, false, Color.white, size, size);
        this.sprite = sprite;
        randomize();
    }

    @Override
    protected void randomize() {
        int randomX = (int) (Math.random() * sprite.getWidth());
        int randomY = (int) (Math.random() * sprite.getHeight());
        Color color = sprite.getColor(randomX, randomY);
        setColor(color);

        dx = (float) (Math.random() * 1f) - .5f;
        dy = 1.1f;
    }

    @Override
    public void tick(GameContainer gc) {
        if(System.currentTimeMillis() - tickTime > 20) {
            if(color.a > 0.5f) {
                color.a -= 0.05f;
                position.x += dx;
                position.y += dy;
            }
            else {
                decayed = true;
            }

            tickTime = System.currentTimeMillis();
        }
    }
}
