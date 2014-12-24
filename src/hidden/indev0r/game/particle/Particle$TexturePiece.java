package hidden.indev0r.game.particle;

import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

/**
 * Created by MrDeathJockey on 14/12/19.
 */
public class Particle$TexturePiece extends Particle {

    public Particle$TexturePiece(Vector2f position, boolean useCameraOffset, Image texture, int pieceX, int pieceY, int pieceSize) {
        super(position, useCameraOffset, Color.white, pieceSize, pieceSize);
        setColor(texture.getColor(pieceX, pieceY));
    }

    @Override
    public void randomize() {

    }

    @Override
    public void render(Graphics g) {
        super.render(g);

    }

    @Override
    public void tick(GameContainer gc) {}
}
