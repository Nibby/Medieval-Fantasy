package hidden.indev0r.game.entity.ai;

import hidden.indev0r.game.entity.Actor;
import hidden.indev0r.game.entity.FactionUtil;
import hidden.indev0r.game.entity.combat.DamageModel;
import hidden.indev0r.game.entity.npc.script.Script;
import hidden.indev0r.game.map.MapDirection;
import hidden.indev0r.game.map.TileMap;
import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.util.pathfinding.AStarPathFinder;
import org.newdawn.slick.util.pathfinding.Path;
import org.w3c.dom.Element;

/**
 * Created by MrDeathJockey on 14/12/19.
 *
 * Wonders freely in a given area (unless confined) until
 * a target is found. A target is defined as any other actor
 * not in the same faction as itself.
 *
 * Once target is found, it will use A* algorithm to approach
 * and engage in combat.
 */
public class AI$MON$MeleeBasic extends AI {

    private long aiTickTimer = 0;

    private Element aiElement;
    private int wanderInterval;

    private TileMap map;
    private Actor target;
    private Path movePath;

    public AI$MON$MeleeBasic(Actor host) {
        super(host);
    }

    @Override
    public void make(Element aiElement) {
        this.aiElement = aiElement;
        wanderInterval = Integer.parseInt(Script.translate(aiElement.getAttribute("wanderInterval"), aiElement.getAttribute("randomParams")).toString());
    }

    @Override
    public void tick(TileMap map) {
        this.map = map;
        if(!actHost.isMoving() && target != null) {
            boolean inTargetAttackRange = actHost.withinRange(target, actHost.getStat(Actor.Stat.ATTACK_RANGE) + actHost.getStat(Actor.Stat.ATTACK_RANGE_BONUS));

            if(movePath == null) {
                if(!inTargetAttackRange && System.currentTimeMillis() - aiTickTimer > 50) {
                    Vector2f adjacent = map.getVacantAdjacentTile(target, actHost, true);
                    if(adjacent != null) {
                        movePath = new AStarPathFinder(map, 24, false)
                                .findPath(null, (int) actHost.getX(), (int) actHost.getY(), (int) adjacent.x, (int) adjacent.y);
                    }
                    aiTickTimer = System.currentTimeMillis();
                }
            } else {
                if(target.isDead()) {
                    target = null;
                    return;
                }
                if(movePath.getLength() > 0) {
                    Path.Step step = movePath.getStep(1);
                    actHost.setMoveDestination(step.getX(), step.getY());
                    movePath = null;
                }
            }

            if (inTargetAttackRange) {
                actHost.combatStart(target);
                movePath = null;
            }
        } else {
            //If no predefined target, go to wander mode
            if(System.currentTimeMillis() - aiTickTimer > wanderInterval) {
                MapDirection direction = MapDirection.values()[(int) (Math.random() * MapDirection.values().length)];
                switch(direction) {
                    case UP:
                        actHost.setMoveDestination(actHost.getX(), actHost.getY() - 1);
                        break;
                    case DOWN:
                        actHost.setMoveDestination(actHost.getX(), actHost.getY() + 1);
                        break;
                    case LEFT:
                        actHost.setMoveDestination(actHost.getX() - 1, actHost.getY());
                        break;
                    case RIGHT:
                        actHost.setMoveDestination(actHost.getX() + 1, actHost.getY());
                        break;
                }
                aiTickTimer = System.currentTimeMillis();
            }
        }
    }

    @Override
    public void onApproach(Actor actor) {
        if(FactionUtil.isEnemy(actHost.getFaction(), actor.getFaction())) {
            if(this.target == null)
                this.target = actor;
        }
    }

    @Override
    public void onHurt(Actor initiator, DamageModel model) {
        if(FactionUtil.isEnemy(actHost.getFaction(), initiator.getFaction()))
            this.target = initiator;
    }
}
