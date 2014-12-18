package hidden.indev0r.game.entity.combat.phase.channel;

import hidden.indev0r.game.Camera;
import hidden.indev0r.game.MedievalLauncher;
import hidden.indev0r.game.entity.Actor;
import hidden.indev0r.game.entity.animation.Action;
import hidden.indev0r.game.entity.animation.ActionType;
import hidden.indev0r.game.entity.combat.AttackType;
import hidden.indev0r.game.entity.combat.DamageModel;
import hidden.indev0r.game.entity.combat.DamageType;
import org.newdawn.slick.Color;
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

    public CombatChannelPhase$MeleeAttack(AttackType type, Actor actor) {
        super(type, actor);

        Map<ActionType, Action> actorActionMap = actor.getActionMap();
        if(actorActionMap != null) {
            switch (actor.getCurrentDirection()) {
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
            Image sprite = actor.getCurrentImage();
            if(sprite != null) {
                switch(actor.getCurrentDirection()) {
                    case UP:
                        shiftY = -actor.getHeight() / 3;
                        break;
                    case DOWN:
                        shiftY = actor.getHeight() / 3;
                        break;
                    case LEFT:
                        shiftX = -actor.getWidth() / 3;
                        break;
                    case RIGHT:
                        shiftX = actor.getWidth() / 3;
                        break;
                }
            }

            mode = MODE_SPRITE;
        }
    }

    @Override
    public int getDuration() {
        if(mode == MODE_ACTION_SET)
            return actInitiator.getActionMap().get(actionToPlay).getPlayTime();
        else
            return 200;
    }

    private boolean playedSound = false;
    @Override
    public void tick(GameContainer gc) {
        super.tick(gc);

        if(mode == MODE_SPRITE) {
            if(System.currentTimeMillis() - hitTickTime > 200) {
                shiftX = 0;
                shiftY = 0;

            } else {
                if(!playedSound) {
                    actInitiator.playSound(DamageType.normal + "_hit");
                    playedSound = true;
                }
            }
        }

        if(mode == MODE_ACTION_SET &&
                System.currentTimeMillis() - startTime > getDuration() - 200) {
            if(!playedSound) {
                actInitiator.playSound(DamageType.normal + "_hit");
                playedSound = true;
            }
        }
    }

    @Override
    protected void init() {
        hitTickTime = System.currentTimeMillis();
        if(mode == MODE_ACTION_SET) {
            actInitiator.forceActAction(actionToPlay);
        }
    }

    @Override
    public boolean overrideInitiatorRender() {
        return mode == MODE_SPRITE;
    }

    @Override
    public void renderInitiator(Graphics g, Actor initiator) {
        Camera camera = MedievalLauncher.getInstance().getGameState().getCamera();
        initiator.render(g, initiator.getPosition().x + camera.getOffsetX() + shiftX,
                initiator.getPosition().y + camera.getOffsetY() + shiftY, new Color(1f, 1f, 1f, 1f));
    }
}
