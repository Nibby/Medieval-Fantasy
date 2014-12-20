package hidden.indev0r.game.entity.ai;

import hidden.indev0r.game.entity.Actor;
import hidden.indev0r.game.entity.Entity;
import hidden.indev0r.game.entity.FactionUtil;
import hidden.indev0r.game.entity.combat.DamageModel;
import hidden.indev0r.game.entity.npc.script.Script;
import hidden.indev0r.game.map.MapDirection;
import hidden.indev0r.game.map.TileMap;
import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.util.pathfinding.AStarPathFinder;
import org.newdawn.slick.util.pathfinding.Path;
import org.w3c.dom.Element;

import java.util.List;

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

    private AStarPathFinder pathFinder;
    private int tx, ty;

    public AI$MON$MeleeBasic(Actor host) {
        super(host);
    }

    @Override
    public void make(Element aiElement) {
        this.aiElement = aiElement;
        wanderInterval = Integer.parseInt(Script.translate(aiElement.getAttribute("wanderInterval"), aiElement.getAttribute("randomParams")).toString());
    }

    /*
        Goal of this AI:

            Wander the area aimlessly if it does not have a target

            Upon having a target, find an adjacent square to it using A*,
            upon entering attack range, begin combat.

            If target dies, drop focus and begin wandering.
     */
    @Override
    public void tick(TileMap map) {
        this.map = map;
        if(pathFinder == null)
            pathFinder = new AStarPathFinder(map.getPathMap(), actHost.getApproachRange() * 2, false);

        if(target != null) {
            boolean inAttackRange = actHost.withinRange(target, actHost.getAttackRange());
            if(!actHost.isMoving()) {
                //If there is no traverse path while monster is not in attack range, then find a way to approach target
                if(movePath == null && !inAttackRange && System.currentTimeMillis() - aiTickTimer > 50) {
                    Vector2f adjacentTile = map.getVacantAdjacentTile(target, actHost, true);
                    //If adjacent tile is present, proceed to move
                    if(adjacentTile != null) {
                        map.getPathMap().setReferenceEntity(actHost);
                        movePath = pathFinder.findPath(actHost, (int) actHost.getX(), (int) actHost.getY(), (int) adjacentTile.x, (int) adjacentTile.y);
                        map.getPathMap().setReferenceEntity(null);
                    }
                    aiTickTimer = System.currentTimeMillis();
                }
            }

            //If the monster does have an actor to attack
            if(movePath != null) {
                if(movePath.getLength() > 0 && !actHost.isMoving()) {
                    Path.Step step = movePath.getStep(1);
                    actHost.setMoveDestination(step.getX(), step.getY());
                    movePath = null;
                }
            }

            //Check and cancel current target if it is already dead
            if(target.isDead()) {
                tx = -1;
                ty = -1;
                target = null;
                movePath = null;
                return;
            } else {
                //If target is not dead, and is in attack range, initiate attack
                if(inAttackRange) {
                    actHost.combatStart(target);
                }
            }
        } else {
            //If we don't have a target, begin wondering around and find one
            //In the mean time, search for nearby entities to attack
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

                List<Actor> nearbyActors = map.getActorsNear(actHost, actHost.getApproachRange());
                int lowDistance = 9999;
                Actor targetCandidate = null;
                for(Actor actor : nearbyActors) {
                    boolean enemy = FactionUtil.isEnemy(actHost.getFaction(), actor.getFaction());
                    if(enemy) {
                        int distance = map.getActorDistance(actHost, actor);
                        if(distance < lowDistance) {
                            lowDistance = distance;
                            targetCandidate = actor;
                        }
                    }
                }

                if(targetCandidate != null) {
                    target = targetCandidate;
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
