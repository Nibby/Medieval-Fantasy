package hidden.indev0r.game.entity.ai;

import hidden.indev0r.game.entity.Actor;
import hidden.indev0r.game.map.MapDirection;
import hidden.indev0r.game.map.TileMap;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * Created by MrDeathJockey on 14/12/17.
 */
public class AI$NPC$LookRandom extends AI {

    private MapDirection[] directions;
    private int interval = 0;
    private long lastTick = 0;

    @Override
    public void make(Actor actor, Element aiElement) {
        interval = Integer.parseInt(aiElement.getAttribute("interval"));

        NodeList params = aiElement.getElementsByTagName("param");
        directions = new MapDirection[params.getLength()];
        for(int i = 0; i < params.getLength(); i++) {
            Element param = (Element) params.item(i);

            MapDirection direction = MapDirection.valueOf(param.getAttribute("direction"));
            directions[i] = direction;
        }
    }

    @Override
    public void tick(TileMap map, Actor actor) {
        if(System.currentTimeMillis() - lastTick > interval) {
            MapDirection direction = directions[(int) (Math.random() * directions.length)];
            actor.setFacingDirection(direction);
            lastTick = System.currentTimeMillis();
        }
    }
}
