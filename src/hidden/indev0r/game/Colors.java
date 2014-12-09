package hidden.indev0r.game;

import org.newdawn.slick.Color;

/**
 * Created by MrDeathJockey on 14/12/9.
 *
 * Referenced from XML files
 */
public enum Colors {

    YELLOW(Color.yellow),
    RED(Color.red),
    GREEN(Color.green),
    MAGENTA(Color.magenta),
    WHITE(Color.white),
    BLUE(Color.blue),
    CYAN(Color.cyan),
    BLACK(Color.black),
    DARK_GRAY(Color.darkGray),
    LIGHT_GRAY(Color.lightGray)
    ;

    private Color color;
    private Colors(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }
}
