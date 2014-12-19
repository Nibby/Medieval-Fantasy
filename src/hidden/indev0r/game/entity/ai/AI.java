package hidden.indev0r.game.entity.ai;

import hidden.indev0r.game.entity.Actor;
import hidden.indev0r.game.entity.combat.DamageModel;
import hidden.indev0r.game.map.MapDirection;
import hidden.indev0r.game.map.TileMap;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by MrDeathJockey on 14/12/11.
 */
public abstract class AI {

    protected Actor actHost;

    public AI(Actor host) {
        this.actHost = host;
    }

    private static final Map<String, AI> aiDatabase = new HashMap<>();

    public abstract void make(Element aiElement);

    public abstract void tick(TileMap map);

    public abstract void onApproach(Actor actor);

    public abstract void onHurt(Actor initiator, DamageModel model);

    public static final AI getAI(Actor actor, String key) {
        return AIList.valueOf(key).getInstance(actor);
    }
}
