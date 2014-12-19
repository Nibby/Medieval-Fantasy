package hidden.indev0r.game.entity.ai;

import hidden.indev0r.game.entity.Actor;
import hidden.indev0r.game.entity.combat.DamageModel;
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

    public AI$NPC$MoveRandom(Actor host) {
        super(host);
    }

    @Override
    public void make(Element aiElement) {
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
    public void tick(TileMap map) {
        if(System.currentTimeMillis() - lastTick > thisInterval) {
            if(setInterval) thisInterval = interval;
            else thisInterval = (int) (Math.random() * (intervalMax - intervalMin) + intervalMin);

            MapDirection[] directions = MapDirection.values();
            MapDirection random = directions[(int) (Math.random() * directions.length)];
            int x = (int) actHost.getX(), y = (int) actHost.getY();
            switch(random) {
                case UP:
                    actHost.setMoveDestination(x, y - 1);
                    break;
                case DOWN:
                    actHost.setMoveDestination(x, y + 1);
                    break;
                case LEFT:
                    actHost.setMoveDestination(x - 1, y);
                    break;
                case RIGHT:
                    actHost.setMoveDestination(x + 1, y);
                    break;
            }

            lastTick = System.currentTimeMillis();
        }
    }

    @Override
    public void onApproach(Actor actor) {

    }

    @Override
    public void onHurt(Actor initiator, DamageModel model) {

    }
}
