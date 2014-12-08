package hidden.indev0r.core.gui.component.interfaces;

import hidden.indev0r.core.entity.Actor;
import hidden.indev0r.core.entity.Entity;
import hidden.indev0r.core.map.Tile;
import org.lwjgl.util.vector.Vector2f;

import java.util.List;

public interface GMapSupplier {

	List<Entity> getEntitiesOnMap();

	Tile getTile(int layer, Vector2f position);

    boolean blockedAt(Vector2f position);

    Actor getCenterEntity();

    int[][][] getTiles();

    boolean isNullTile(Vector2f vector2f);
}

