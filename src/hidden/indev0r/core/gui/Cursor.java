package hidden.indev0r.core.gui;

import hidden.indev0r.core.MedievalLauncher;
import hidden.indev0r.core.entity.NPC;
import hidden.indev0r.core.texture.Textures;
import org.newdawn.slick.Image;

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

    //The reason for an Object than anything GComponent related is so that in case we want
    //to make it so user can drag items in inventory screen, they may do so without confusing
    //the GComponents.
    public static Object DRAG_INSTANCE = null;
    public static Object INTERACT_INSTANCE = null;

    private Image image;
    private int focusX, focusY;

    private Cursor(Image image, int focusX, int focusY) {
        this.image = image;
        this.focusX = focusX;
        this.focusY = focusY;
    }

    public static void setInteractInstance(Object instance) {
        INTERACT_INSTANCE = instance;

        if(instance instanceof NPC) {
            MedievalLauncher.getInstance().setCursor(INTERACT);
        }

        if(instance == null) {
            MedievalLauncher.getInstance().setCursor(NORMAL);
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
