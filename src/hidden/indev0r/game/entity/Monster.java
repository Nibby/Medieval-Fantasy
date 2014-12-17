package hidden.indev0r.game.entity;

import hidden.indev0r.game.Camera;
import hidden.indev0r.game.MedievalLauncher;
import hidden.indev0r.game.gui.Cursor;
import hidden.indev0r.game.map.MapDirection;
import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;

/**
 * Created by MrDeathJockey on 14/12/17.
 */
public class Monster extends Actor implements Cloneable {

    private String name;
    private boolean wasMouseFocused = false;
    private boolean postInteraction = false;

    public Monster(Faction faction, String name) {
        super(faction, new Vector2f(0, 0));
        this.name = name;
        setAI(ai);
        setSolid(true);
        setFacingDirection(MapDirection.RIGHT);
        setMinimapColor(Color.red);
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
            if(player.isControllable()) {
                if (input.isMousePressed(Input.MOUSE_RIGHT_BUTTON)) {
                    //To interact, player must be 2 tiles or less away from the NPC
                    if (withinRange(player, player.getAttackRange())) {
                        interact(MedievalLauncher.getInstance().getGameState().getPlayer());
                        postInteraction = true;
                    }
                }

                if (withinRange(player, interactRange)) {
                    if (Cursor.INTERACT_INSTANCE == null) {
                        Cursor.setInteractInstance(this);
                        wasMouseFocused = true;
                    }
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

    //Combat...
    private void interact(Player player) {
        player.setFacingDirection(MapDirection.turnToFace(player, this));
        player.combatStart(this);
    }

    public static Monster generateInstance(Monster monster) {
        try {
            return (Monster) monster.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getName() {
        return name;
    }
}
