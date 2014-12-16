package hidden.indev0r.game.entity;

import hidden.indev0r.game.Camera;
import hidden.indev0r.game.MedievalLauncher;
import hidden.indev0r.game.gui.Cursor;
import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;

/**
 * Created by MrDeathJockey on 14/12/15.
 *
 * An ordinary door. For locked/special doors, create an NPC instead.
 */
public class Door extends Actor {

    private boolean closed;
    private Animation openAnim;
    private Animation closeAnim;

    private boolean postInteraction = false;
    private boolean wasMouseFocused = false;

    public Door(Vector2f position, Animation openAnim, Animation closeAnim, boolean closed) {
        super(Faction.NONE, position);
        this.closed = closed;
        this.openAnim = openAnim;
        this.closeAnim = closeAnim;
        interactRange = 1;
        setSize(32, 32);
        updateDoorProperties();
    }

    private void updateDoorProperties() {
        if(closed) setSprite(closeAnim.getCurrentFrame());
        if(!closed) setSprite(openAnim.getCurrentFrame());

        setSolid(closed);
    }

    public void tick(GameContainer gc) {
        if(!MedievalLauncher.getInstance().getGameState().getMenuOverlay().isComponentEmpty()
                || !closed) {
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

    public void interact(Actor actor) {
        if(actor instanceof Player) {
            closed = false;
        }
        updateDoorProperties();
    }
}
