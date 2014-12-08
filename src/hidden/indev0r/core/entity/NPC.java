package hidden.indev0r.core.entity;

import hidden.indev0r.core.BitFont;
import hidden.indev0r.core.Camera;
import hidden.indev0r.core.MedievalLauncher;
import hidden.indev0r.core.gui.Cursor;
import hidden.indev0r.core.map.Tile;
import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;

/**
 * Created by MrDeathJockey on 14/12/8.
 */
public class NPC extends Actor {

    private String identifier;
    private String name;
    private Faction faction;
    private boolean hostile;

    private boolean wasMouseFocused = false;

    public NPC(String identifier, Faction faction, String name, Vector2f position) {
        super(faction, position);
        this.identifier = identifier;
        this.name = name;
        this.faction = faction;
    }

    public void render(Graphics g) {
        super.render(g);

        if(wasMouseFocused) {
            g.setColor(Color.black);
            Camera camera = MedievalLauncher.getInstance().getGameState().getCamera();
            g.fillRoundRect(position.x + camera.getOffsetX() + width / 2 - BitFont.widthOf(name, 16) / 2 - 8, position.y - 40 + camera.getOffsetY(),
                    BitFont.widthOf(name, 16) + 16, 24, 5);

            BitFont.render(g, name, (int) (position.x + camera.getOffsetX() + width / 2 - BitFont.widthOf(name, 16) / 2), (int) (position.y - 34 + camera.getOffsetY()));
        }
    }

    public void tick(GameContainer gc) {
        super.tick(gc);

        Input input = gc.getInput();
        int mx = input.getMouseX();
        int my = input.getMouseY();
        Camera camera = MedievalLauncher.getInstance().getGameState().getCamera();
        if(mx > position.x + camera.getOffsetX() && my > position.y + camera.getOffsetY() &&
                mx < position.x + width + camera.getOffsetX() && my < position.y + height + camera.getOffsetY()) {
            Cursor.setInteractInstance(this);
            wasMouseFocused = true;
        } else {
            if(wasMouseFocused) {
                Cursor.setInteractInstance(null);
                wasMouseFocused = false;
            }
        }
    }

    public String getIdentifier() {
        return identifier;
    }

    public String getName() {
        return name;
    }


}
