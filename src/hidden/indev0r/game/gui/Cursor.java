package hidden.indev0r.game.gui;

import hidden.indev0r.game.BitFont;
import hidden.indev0r.game.Camera;
import hidden.indev0r.game.MedievalLauncher;
import hidden.indev0r.game.entity.Door;
import hidden.indev0r.game.entity.FactionUtil;
import hidden.indev0r.game.entity.NPC;
import hidden.indev0r.game.entity.Player;
import hidden.indev0r.game.entity.npc.script.Script;
import hidden.indev0r.game.gui.component.base.GComponent;
import hidden.indev0r.game.texture.Textures;
import org.newdawn.slick.*;

/**
 * Created by MrDeathJockey on 14/12/8.
 *
 * This class is a hybrid of an enumeration of the available cursors that game container can use.
 * Additionally, it handles mouse dragging events when dragging windows.
 *
 * There's a technical issue that when you draw and update multiple GComponent$Dialogs,
 * if they're too close together, dragging one dialog causes the other to getStat dragged too.
 * The cursor class holds some information to make sure that only one dialog is dragged.
 *
 * I could've made a separate mouse handler class but that.... doesn't seem really necessary.
 */
public class Cursor {

    public static final Cursor NORMAL = new Cursor(Textures.Cursors.POINTER_YELLOW, 0, 0);
    public static final Cursor ENEMY_TARGET = new Cursor(Textures.Cursors.POINTER_RED, 0, 0);
    public static final Cursor INTERACT = new Cursor(Textures.Cursors.FINGER, 0, 0);
    public static final Cursor INTERACT_2 = new Cursor(Textures.Cursors.FINGER_PRESSED, 0 ,0);

    private static final Animation INTERACT_TALK = new Animation(Textures.Cursors.INERACT_ANIMATION, 500);

    //The reason for an Object than anything GComponent related is so that in case we want
    //to make it so user can drag items in inventory screen, they may do so without confusing
    //the GComponents.
    public static Object DRAG_INSTANCE = null;
    public static Object INTERACT_INSTANCE = null;

    //
    private static final Color NAME_TAG_COLOR = new Color(0f, 0f, 0f, 0.6f);

    private Image image;
    private int focusX, focusY;

    private Cursor(Image image, int focusX, int focusY) {
        this.image = image;
        this.focusX = focusX;
        this.focusY = focusY;
    }

    public static void setInteractInstance(Object instance) {
        INTERACT_INSTANCE = instance;

        if(instance instanceof NPC || instance  instanceof GComponent) {
            MedievalLauncher.getInstance().setCursor(INTERACT_2);
        }

        if(instance instanceof Door) {
            MedievalLauncher.getInstance().setCursor(INTERACT_2);
        }

        if(instance == null) {
            MedievalLauncher.getInstance().setCursor(NORMAL);
        }

//        System.out.println(instance);
    }

    public static void render(Graphics g) {

        if(INTERACT_INSTANCE instanceof NPC && !((NPC) INTERACT_INSTANCE).isPostInteraction()) {
            NPC npc = (NPC) INTERACT_INSTANCE;
            Player player = MedievalLauncher.getInstance().getGameState().getPlayer();
            boolean isEnemy = FactionUtil.isEnemy(player.getFaction(), npc.getFaction());

            g.setColor(NAME_TAG_COLOR);
            Camera camera = MedievalLauncher.getInstance().getGameState().getCamera();

            if(!isEnemy && npc.hasScript(Script.Type.interact)) {
                g.drawAnimation(INTERACT_TALK, npc.getPosition().x + camera.getOffsetX() + npc.getWidth() / 2 - 16,
                        npc.getPosition().y + camera.getOffsetY() - 32);
            }

            if(isEnemy) MedievalLauncher.getInstance().setCursor(Cursor.ENEMY_TARGET);


            GameContainer gc = MedievalLauncher.getInstance().getGameContainer();
            Input input = gc.getInput();
            int mx = input.getMouseX();
            int my = input.getMouseY();

            String renderText = (isEnemy || npc.isHostile()) ? npc.getName() + " [Hostile]" : npc.getName();
            g.fillRoundRect(mx + 16 - BitFont.widthOf(renderText, 16) / 2,  my + 38, (BitFont.widthOf(renderText, 16)) + 16, 24, 5);

            BitFont.render(g, renderText, (mx + 24 - BitFont.widthOf(renderText, 16) / 2), my + 42, (!isEnemy) ? npc.getNameColor() : Color.red);
        }
    }

    public static void releaseInteractInstance(Object obj) {
        if(INTERACT_INSTANCE == obj) {
            setInteractInstance(null);
        }
    }

    public Image getImage() {
        return image;
    }

    public int getFocusX() {
        return focusX;
    }

    public int getFocusY() {
        return focusY;
    }
}
