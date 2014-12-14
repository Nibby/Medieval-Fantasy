package hidden.indev0r.game.entity;

import hidden.indev0r.game.Camera;
import hidden.indev0r.game.MedievalLauncher;
import hidden.indev0r.game.entity.npc.script.Script;
import hidden.indev0r.game.gui.Cursor;
import hidden.indev0r.game.map.MapDirection;
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
    private Color nameColor, minimapColor;

    private boolean wasMouseFocused = false;
    private boolean postInteraction = false;

    public NPC(Faction faction, String name, Vector2f position) {
        super(faction, position);
        this.name = name;
        this.faction = faction;
        nameColor = Color.white;
    }

    public void render(Graphics g) {
        super.render(g);
    }

    public void tick(GameContainer gc) {
        super.tick(gc);

        checkInteraction(gc);
    }

    private void checkInteraction(GameContainer gc) {
        if(!MedievalLauncher.getInstance().getGameState().getMenuOverlay().isComponentEmpty()) {
            Cursor.releaseInteractInstance(this);
            return;
        }

        Input input = gc.getInput();
        int mx = input.getMouseX();
        int my = input.getMouseY();
        Camera camera = MedievalLauncher.getInstance().getGameState().getCamera();
        if(mx > position.x + camera.getOffsetX() && my > position.y + camera.getOffsetY() &&
                mx < position.x + width + camera.getOffsetX() && my < position.y + height + camera.getOffsetY()) {

            Player player = MedievalLauncher.getInstance().getGameState().getPlayer();
            if(input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
                //To interact, player must be 2 tiles or less away from the NPC
                if(withinInteractRange(player)) {
                    interact(MedievalLauncher.getInstance().getGameState().getPlayer());
                    postInteraction = true;
                }
            }

            if(withinInteractRange(player)) {
                if(Cursor.INTERACT_INSTANCE == null) {
                    Cursor.setInteractInstance(this);
                    wasMouseFocused = true;
                }
            }

        } else {
            if(wasMouseFocused) {
                Cursor.setInteractInstance(null);
                wasMouseFocused = false;

                if(postInteraction) postInteraction = false;
            }
        }
    }

    private void interact(Player player) {
        executeScript(Script.Type.interact);
    }

    public Color getMinimapColor() {
        return minimapColor;
    }

    public void setMinimapColor(Color minimapColor) {
        this.minimapColor = minimapColor;
    }

    public void setNameColor(Color color) {
        this.nameColor = color;
    }

    public Color getNameColor() {
        return nameColor;
    }

    public String getIdentifier() {
        return identifier;
    }

    public String getName() {
        return name;
    }

    public boolean isPostInteraction() {
        return postInteraction;
    }

    public boolean isHostile() {
        return hostile;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }
}
