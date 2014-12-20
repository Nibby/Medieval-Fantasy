package hidden.indev0r.game.map;

import hidden.indev0r.game.entity.Entity;
import org.newdawn.slick.util.pathfinding.PathFindingContext;
import org.newdawn.slick.util.pathfinding.TileBasedMap;

/**
 * Created by MrDeathJockey on 14/12/20.
 */
public class PathFindingMap implements TileBasedMap {

    private TileMap map;
    private Entity referenceEntity;

    public PathFindingMap(TileMap map) {
        this.map = map;
    }

    @Override
    public int getWidthInTiles() {
        return map.getWidth();
    }

    @Override
    public int getHeightInTiles() {
        return map.getHeight();
    }

    @Override
    public void pathFinderVisited(int x, int y) {

    }

    @Override
    public boolean blocked(PathFindingContext context, int x, int y) {

        return map.tileBlocked(x, y) || map.entityBlocked(referenceEntity, x, y);
    }

    @Override
    public float getCost(PathFindingContext context, int x, int y) {
        return 0;
    }

    public Entity getReferenceEntity() {
        return referenceEntity;
    }

    public void setReferenceEntity(Entity referenceEntity) {
        this.referenceEntity = referenceEntity;
    }
}
