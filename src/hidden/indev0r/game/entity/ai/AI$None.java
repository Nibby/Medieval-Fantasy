package hidden.indev0r.game.entity.ai;

import hidden.indev0r.game.entity.Actor;
import hidden.indev0r.game.entity.combat.DamageModel;
import hidden.indev0r.game.map.TileMap;
import org.w3c.dom.Element;

/**
 * Created by MrDeathJockey on 14/12/17.
 */
public class AI$None extends AI {

    public AI$None(Actor host) {
        super(host);
    }

    @Override
    public void make(Element aiElement) {

    }

    @Override
    public void tick(TileMap map) {

    }

    @Override
    public void onApproach(Actor actor) {

    }

    @Override
    public void onHurt(Actor initiator, DamageModel model) {

    }
}
