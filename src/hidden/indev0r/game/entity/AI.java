package hidden.indev0r.game.entity;

import hidden.indev0r.game.map.MapDirection;
import hidden.indev0r.game.map.TileMap;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by MrDeathJockey on 14/12/11.
 */
public class AI implements Cloneable {

    public static final void init() {
        new AI("NONE") {
            public AI make(Actor actor, Element aiElement) {
                return generateInstance(this);
            }
        };

        new AI("LOOK_RANDOM") {

            private MapDirection[] directions;
            private int interval = 0;
            private long lastTick = 0;

            public AI make(Actor actor, Element aiElement) {
                interval = Integer.parseInt(aiElement.getAttribute("interval"));

                NodeList params = aiElement.getElementsByTagName("param");
                directions = new MapDirection[params.getLength()];
                for(int i = 0; i < params.getLength(); i++) {
                    Element param = (Element) params.item(i);

                    MapDirection direction = MapDirection.valueOf(param.getAttribute("direction"));
                    directions[i] = direction;
                }

                return generateInstance(this);
            }

            public void tick(TileMap map, Actor actor) {
                if(System.currentTimeMillis() - lastTick > interval) {
                    MapDirection direction = directions[(int) (Math.random() * directions.length)];
                    actor.setFacingDirection(direction);
                    lastTick = System.currentTimeMillis();
                }
            }
        };

        new AI("LOOK_ORDERED") {

            private MapDirection[] directions;
            private int interval = 0;
            private long lastTick = 0;
            private int currentStep = 0;

            public AI make(Actor actor, Element aiElement) {
                interval = Integer.parseInt(aiElement.getAttribute("interval"));

                NodeList params = aiElement.getElementsByTagName("param");
                directions = new MapDirection[params.getLength()];
                for(int i = 0; i < params.getLength(); i++) {
                    Element param = (Element) params.item(i);

                    MapDirection direction = MapDirection.valueOf(param.getAttribute("direction"));
                    directions[i] = direction;
                }

                return generateInstance(this);
            }

            public void tick(TileMap map, Actor actor) {
                if(System.currentTimeMillis() - lastTick > interval) {
                    if(currentStep < directions.length - 1) currentStep++;
                    else currentStep = 0;

                    MapDirection direction = directions[currentStep];
                    actor.setFacingDirection(direction);

                    lastTick = System.currentTimeMillis();
                }
            }
        };

        new AI("MOVE_RANDOM") {

            private boolean setInterval = false;
            private int intervalMin, intervalMax, interval;
            private int thisInterval = 0;
            private long lastTick = 0;

            public AI make(Actor actor, Element aiElement) {

                setInterval = aiElement.hasAttribute("interval");
                if(!setInterval) {
                    intervalMin = Integer.parseInt(aiElement.getAttribute("intervalMin"));
                    intervalMax = Integer.parseInt(aiElement.getAttribute("intervalMax"));
                    thisInterval = (int) (Math.random() * (intervalMax - intervalMin) + intervalMin);
                } else {
                    interval = Integer.parseInt(aiElement.getAttribute("interval"));
                }

                return generateInstance(this);
            }

            public void tick(TileMap map, Actor actor) {
                if(System.currentTimeMillis() - lastTick > thisInterval) {
                    if(setInterval) thisInterval = interval;
                    else thisInterval = (int) (Math.random() * (intervalMax - intervalMin) + intervalMin);

                    MapDirection[] directions = MapDirection.values();
                    MapDirection random = directions[(int) (Math.random() * directions.length)];
                    int x = (int) actor.getX(), y = (int) actor.getY();
                    switch(random) {
                        case UP:
                            actor.setMoveDestination(x, y - 1);
                            break;
                        case DOWN:
                            actor.setMoveDestination(x, y + 1);
                            break;
                        case LEFT:
                            actor.setMoveDestination(x - 1, y);
                            break;
                        case RIGHT:
                            actor.setMoveDestination(x + 1, y);
                            break;
                    }

                    lastTick = System.currentTimeMillis();
                }
            }
        };
    }

    private static final Map<String, AI> aiDatabase = new HashMap<>();

    private AI(String id) {
        if(aiDatabase.get(id) != null) throw new IllegalArgumentException("AI already exists: " + id);
        aiDatabase.put(id, this);
    }

    public AI make(Actor actor, Element aiElement) {
        return null;
    }

    public void tick(TileMap map, Actor actor) {

    }

    public static final AI getAI(String key) {
        return aiDatabase.get(key);
    }

    private static final AI generateInstance(AI ai) {
        try {
            return (AI) ai.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }
}
