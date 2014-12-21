package hidden.indev0r.game.entity.combat.phase;

import hidden.indev0r.game.entity.Actor;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

/**
 * Created by MrDeathJockey on 14/12/18.
 *
 * A phase during combat
 */
public interface CombatPhase {

    public void render(Graphics g);
    public void tick(GameContainer gc);

    //Whether or not to replace default rendering model and use this one temporarily for special effects
    public boolean overrideInitiatorRender();

    //Will only be invoked if above method returns true.
    public void renderInitiator(Graphics g, Actor initiator);

    public boolean isInitiator(Actor actor);

    public boolean isExpired();

    Actor getInitiator();
    Actor getTarget();
}
