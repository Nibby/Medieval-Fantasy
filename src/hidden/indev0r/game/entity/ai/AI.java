package hidden.indev0r.game.entity.ai;

import hidden.indev0r.game.entity.Actor;
import hidden.indev0r.game.map.MapDirection;
import hidden.indev0r.game.map.TileMap;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by MrDeathJockey on 14/12/11.
 */
public abstract class AI implements Cloneable {

    private static final Map<String, AI> aiDatabase = new HashMap<>();

    public abstract void make(Actor actor, Element aiElement);

    public abstract void tick(TileMap map, Actor actor);

    public static final AI getAI(String key) {
        return AIList.valueOf(key).getInstance();
    }

    public static AI getCloned(AI ai) {
        try {
            return (AI) ai.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }
}
