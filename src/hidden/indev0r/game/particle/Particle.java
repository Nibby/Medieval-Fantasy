package hidden.indev0r.game.particle;

import hidden.indev0r.game.Camera;
import hidden.indev0r.game.MedievalLauncher;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;

/**
 * Created by MrDeathJockey on 14/12/18.
 */
public abstract class Particle {

    protected Vector2f position;
    protected float width, height;
    protected Color color;

    protected boolean luminescent = false;
    protected int lumPulse = 1, lumSize = 3;

    protected boolean decayed = false;
    protected long tickTime;
    protected boolean useCameraOffset = false;

    public Particle(Vector2f position, boolean useCameraOffset, Color color, int width, int height) {
        this.position = position;
        this.color = color;
        this.useCameraOffset = useCameraOffset;
        setSizes(width, height);
    }

    public void onSpawn() {
        tickTime = System.currentTimeMillis();

    }

    protected abstract void randomize();

    public void render(Graphics g) {
        g.setColor(color);
        if(useCameraOffset) {
            Camera camera = MedievalLauncher.getInstance().getGameState().getCamera();
            g.fillRect(position.x + camera.getOffsetX(), position.y + camera.getOffsetY(), width, height);
            if(luminescent) {
                for(int i = 0; i < lumSize; i++) {
                    float alpha = color.a - (i + 1) * 0.35f;
                    if(alpha < 0f) alpha = 0f;

                    g.setColor(new Color(color.r, color.g, color.b, alpha));
                    g.fillRect(position.x - i * lumPulse + camera.getOffsetX(),
                            position.y - i * lumPulse + camera.getOffsetY(),
                            width + i * lumPulse * 2, height + i * lumPulse * 2);
                }
            }
        } else {
            g.fillRect(position.x, position.y, width, height);
            if(luminescent) {
                for(int i = 0; i < lumSize; i++) {
                    float alpha = color.a - (i + 1) * 0.35f;
                    if(alpha < 0f) alpha = 0f;

                    g.setColor(new Color(color.r, color.g, color.b, alpha));
                    g.fillRect(position.x - i * lumPulse, position.y - i * lumPulse,
                            width + i * lumPulse * 2, height + i * lumPulse * 2);
                }
            }
        }


    }

    public abstract void tick(GameContainer gc);

    public boolean shouldUseCameraOffset() {
        return useCameraOffset;
    }

    public boolean hasDecayed() {
        return decayed;
    }

    public int getLuminescentPulse() {
        return lumPulse;
    }

    public void setLuminescentPulse(int lumPulse) {
        this.lumPulse = lumPulse;
    }

    public void setLuminscentSize(int lumSize) {
        this.lumSize = lumSize;
    }

    public int getLuminsescentSize() {
        return lumSize;
    }

    public void setSizes(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public void setPosition(Vector2f position) {
        this.position = position;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setLuminescent(boolean luminescent) {
        this.luminescent = luminescent;
    }

    public Vector2f getPosition() {
        return position;
    }

    public Color getColor() {
        return color;
    }

    public boolean isLuminescent() {
        return luminescent;
    }

    public static final Particle getCloned(Particle particle) {
        try {
            return (Particle) particle.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }
}
