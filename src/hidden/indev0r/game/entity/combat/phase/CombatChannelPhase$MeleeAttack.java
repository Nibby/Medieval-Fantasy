package hidden.indev0r.game.entity.combat.phase;

import hidden.indev0r.game.entity.Actor;
import hidden.indev0r.game.entity.animation.ActionType;
import hidden.indev0r.game.entity.combat.AttackType;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

/**
 * Created by MrDeathJockey on 14/12/18.
 */
public class CombatChannelPhase$MeleeAttack extends AbstractCombatChannelPhase {

    private boolean playedAction = false;
    private ActionType actionToPlay;

    public CombatChannelPhase$MeleeAttack(AttackType type, Actor actor) {
        super(type, actor);

        switch(actor.getCurrentDirection()) {
            case UP:
                actionToPlay = ActionType.ATTACK_UP;
                break;
            case DOWN:
                actionToPlay = ActionType.ATTACK_DOWN;
                break;
            case LEFT:
                actionToPlay = ActionType.ATTACK_LEFT;
                break;
            case RIGHT:
                actionToPlay = ActionType.ATTACK_RIGHT;
                break;
        }
    }

    @Override
    public int getDuration() {
        return actInitiator.getActionMap().get(actionToPlay).getPlayTime();
    }

    @Override
    public void tick(GameContainer gc, Actor initiator) {
        if(!playedAction) {
            initiator.forceActAction(actionToPlay);
            playedAction = true;
        }

        super.tick(gc, initiator);
    }

    @Override
    public boolean overrideInitiatorRender() {
        return false;
    }

    @Override
    public void renderInitiator(Graphics g, Actor initiator) {}
}
