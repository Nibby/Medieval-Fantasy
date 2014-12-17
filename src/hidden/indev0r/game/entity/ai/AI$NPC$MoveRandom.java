package hidden.indev0r.game.entity.ai;

import hidden.indev0r.game.entity.Actor;
import hidden.indev0r.game.map.MapDirection;
import hidden.indev0r.game.map.TileMap;
import org.w3c.dom.Element;

/**
 * Created by MrDeathJockey on 14/12/17.
 */
public class AI$NPC$MoveRandom extends AI {

    private boolean setInterval = false;
    private int intervalMin, intervalMax, interval;
    private int thisInterval = 0;
    private long lastTick = 0;

    @Override
    public void make(Actor actor, Element aiElement) {
        setInterval = aiElement.hasAttribute("interval");
        if(!setInterval) {
            intervalMin = Integer.parseInt(aiElement.getAttribute("intervalMin"));
            intervalMax = Integer.parseInt(aiElement.getAttribute("intervalMax"));
            thisInterval = (int) (Math.random() * (intervalMax - intervalMin) + intervalMin);
        } else {
            interval = Integer.parseInt(aiElement.getAttribute("interval"));
        }
    }

    @Override
    public void tick(TileMap map, Actor actor) {
        if(System.currentTimeMillis() - lastTick > thisInterval) {
            if(setInterval) thisInterval = interval;
            else thisInterval = (int) (Math.random() * (intervalMax - intervalMin) + intervalMin);

            MapDirection[] directions = MapDirection.values();
            MapDirection random = directions[(int) (Math.random() * directions.length)];
            int x = (int) actor.getX(), y = (int) actor.getY();
            switch(random) {
                case UP:
                    actor.setMoveDestination(x, y - 1);
                    break;
                case DOWN:
                    actor.setMoveDestination(x, y + 1);
                    break;
                case LEFT:
                    actor.setMoveDestination(x - 1, y);
                    break;
                case RIGHT:
                    actor.setMoveDestination(x + 1, y);
                    break;
            }

            lastTick = System.currentTimeMillis();
        }
    }
}
