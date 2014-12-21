package hidden.indev0r.game.map;

import hidden.indev0r.game.entity.Entity;
import hidden.indev0r.game.entity.Player;

/**
 * Created by James on 12/4/2014.
 */
public enum MapDirection {
	UP,
	DOWN,
	LEFT,
	RIGHT;

    public static MapDirection getOppositeOf(MapDirection dir) {
        switch(dir) {
            case UP:
                return DOWN;
            case DOWN:
                return UP;
            case LEFT:
                return RIGHT;
            case RIGHT:
                return LEFT;
            default:
                return RIGHT;
        }
    }

    public static MapDirection turnToFace(Entity self, Entity facing) {
        return turnToFace(self, (int) facing.getX(), (int) facing.getY());
    }

    public static MapDirection turnToFace(Entity self, int fx, int fy) {
        int sx = (int) self.getX();
        int sy = (int) self.getY();

        if(sx > fx) return LEFT;
        if(sx < fx) return RIGHT;
        if(sy > fy) return UP;
        if(sy < fy) return DOWN;

        return self.getCurrentDirection();
    }
}
