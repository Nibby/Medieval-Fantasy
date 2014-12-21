package hidden.indev0r.game.entity.combat.phase.channel;

import hidden.indev0r.game.entity.Actor;
import hidden.indev0r.game.entity.animation.Action;
import hidden.indev0r.game.entity.animation.ActionType;
import hidden.indev0r.game.entity.combat.AttackType;
import hidden.indev0r.game.entity.combat.DamageModel;
import hidden.indev0r.game.entity.combat.DamageType;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

import java.util.Map;

/**
 * Created by MrDeathJockey on 14/12/18.
 */
public class CombatChannelPhase$MeleeAttack extends AbstractCombatChannelPhase {

    private static final int MODE_SPRITE = 0, MODE_ACTION_SET = 1;
    private int mode;
    private ActionType actionToPlay;

    private int shiftX = 0, shiftY = 0;
    private long hitTickTime;

    public CombatChannelPhase$MeleeAttack(DamageModel model, AttackType type, Actor initiator, Actor target) {
        super(model, type, initiator, target);

        Map<ActionType, Action> actorActionMap = initiator.getActionMap();
        if(actorActionMap != null) {
            switch (initiator.getCurrentDirection()) {
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

            mode = MODE_ACTION_SET;
        } else {
            Image sprite = initiator.getCurrentImage();
            if(sprite != null) {
                switch(initiator.getCurrentDirection()) {
                    case UP:
                        shiftY = -initiator.getHeight() / 3;
                        break;
                    case DOWN:
                        shiftY = initiator.getHeight() / 3;
                        break;
                    case LEFT:
                        shiftX = -initiator.getWidth() / 3;
                        break;
                    case RIGHT:
                        shiftX = initiator.getWidth() / 3;
                        break;
                }
            }
            actInitiator.setRenderShift(shiftX, shiftY);
            mode = MODE_SPRITE;
        }
    }

    private boolean didSwing = false;
    @Override
    public void tick(GameContainer gc) {
        super.tick(gc);

        if(mode == MODE_SPRITE) {
            if(System.currentTimeMillis() - hitTickTime > 200) {
                shiftX = 0;
                shiftY = 0;
                actInitiator.setRenderShift(0, 0);
                if(System.currentTimeMillis() - hitTickTime > 400) {
                    didSwing = false;
                    Image sprite = actInitiator.getCurrentImage();
                    if(sprite != null) {
                        switch(actInitiator.getCurrentDirection()) {
                            case UP:
                                shiftY = -actInitiator.getHeight() / 3;
                                break;
                            case DOWN:
                                shiftY = actInitiator.getHeight() / 3;
                                break;
                            case LEFT:
                                shiftX = -actInitiator.getWidth() / 3;
                                break;
                            case RIGHT:
                                shiftX = actInitiator.getWidth() / 3;
                                break;
                        }
                    }
                    actInitiator.setRenderShift(shiftX, shiftY);
                    currentHit++;
                    if(currentHit > damageModel.getHits() - 1) {
                        actInitiator.setRenderShift(0, 0);
                        expired = true;
                    }

                    hitTickTime = System.currentTimeMillis();
                }
            } else {
                if(!didSwing) {
                    actInitiator.playSound(DamageType.normal.getSwingSound());
                    actInitiator.combatChannelEnd(damageModel, attackType, currentHit);
                    didSwing = true;
                }
            }
        }

        if(mode == MODE_ACTION_SET && System.currentTimeMillis() - hitTickTime > 200) {
            if(!didSwing) {
                actInitiator.playSound(DamageType.normal.getSwingSound());
                actInitiator.combatChannelEnd(damageModel, attackType, currentHit);
                didSwing = true;
            }

            if(System.currentTimeMillis() - hitTickTime > 450) {
                didSwing = false;
                currentHit++;
                if(currentHit > damageModel.getHits() - 1)
                    expired = true;
                else
                    actInitiator.forceActAction(actionToPlay);
                hitTickTime = System.currentTimeMillis();
            }
        }
    }

    @Override
    protected void init() {
        hitTickTime = System.currentTimeMillis();
        if(mode == MODE_ACTION_SET)
            actInitiator.forceActAction(actionToPlay);
    }

    @Override
    public boolean overrideInitiatorRender() {
        return false;
    }

    @Override
    public void renderInitiator(Graphics g, Actor initiator) {
    }
}
