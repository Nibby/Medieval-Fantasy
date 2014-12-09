package hidden.indev0r.game.map;

import hidden.indev0r.game.entity.Entity;

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
        int fx = (int) facing.getX();
        int sx = (int) self.getX();

        if(sx > fx) return LEFT;
        if(sx < fx) return RIGHT;
        else return self.getCurrentDirection();
    }
}
