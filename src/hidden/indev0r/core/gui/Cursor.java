package hidden.indev0r.core.gui;

import hidden.indev0r.core.texture.Textures;
import org.newdawn.slick.Image;

/**
 * Created by MrDeathJockey on 14/12/8.
 */
public enum Cursor {

    NORMAL(Textures.Cursors.POINTER_YELLOW, 0, 0),
    ENEMY_TARGET(Textures.Cursors.POINTER_RED, 0, 0),
    INTERACT(Textures.Cursors.FINGER, 0, 0),
    INTERACT_2(Textures.Cursors.FINGER_PRESSED, 0 ,0)

    ;

    private Image image;
    private int focusX, focusY;

    private Cursor(Image image, int focusX, int focusY) {
        this.image = image;
        this.focusX = focusX;
        this.focusY = focusY;
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
