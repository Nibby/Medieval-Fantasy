package hidden.indev0r.game.gui.component.hud;

import hidden.indev0r.game.BitFont;
import hidden.indev0r.game.Camera;
import hidden.indev0r.game.MedievalLauncher;
import hidden.indev0r.game.entity.Entity;
import hidden.indev0r.game.gui.component.base.GComponent;
import hidden.indev0r.game.gui.component.base.GComponent$Label;
import hidden.indev0r.game.texture.Textures;
import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

/**
 * Created by MrDeathJockey on 14/12/10.
 */
public class GComponent$SpeechBubble extends GComponent {

    private long tickTime;
    private int duration;
    private boolean expired = false;

    private String text;
    private Color color;

    public GComponent$SpeechBubble(String text, Vector2f pos, int duration, Color color) {
        super(pos);

        this.duration = duration;
        this.text = text;
        this.color = color;
        setSize(BitFont.widthOf(text, 16), 28);
    }

    public void onAdd() {
        tickTime = System.currentTimeMillis();
    }

    public void render(Graphics g) {
        Image bLeft = Textures.UI.SPEECH_BUBBLE.getSprite(0, 0);
        Image center = Textures.UI.SPEECH_BUBBLE.getSprite(1, 0);
        Image center2 = Textures.UI.SPEECH_BUBBLE.getSprite(3, 0);
        Image bRight = Textures.UI.SPEECH_BUBBLE.getSprite(2, 0);

        int fWidth = BitFont.widthOf(text, 16);
        Camera camera = MedievalLauncher.getInstance().getGameState().getCamera();

        g.drawImage(bLeft, position.x - 8 + camera.getOffsetX(), position.y - 4 + camera.getOffsetY());
        g.drawImage(center.getScaledCopy(fWidth - bLeft.getWidth() - bRight.getWidth() + 24, center.getHeight()),
                position.x + bLeft.getWidth() - 8 + camera.getOffsetX(), position.y - 4 + camera.getOffsetY());
        g.drawImage(bRight, position.x + fWidth + camera.getOffsetX(), position.y - 4 + camera.getOffsetY());
        g.drawImage(center2, position.x + fWidth / 2 - center2.getWidth() / 2 + camera.getOffsetX(), position.y - 4 + camera.getOffsetY());

        BitFont.render(g, text, (int) (position.x + camera.getOffsetX()), (int) (position.y + camera.getOffsetY()), color);
    }

    public void tick(GameContainer gc) {
        super.tick(gc);
        if(System.currentTimeMillis() - tickTime > duration) {
            expired = true;
        }
    }

    public boolean isExpired() {
        return expired;
    }
}
