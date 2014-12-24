package hidden.indev0r.game.map;

import hidden.indev0r.game.MedievalLauncher;
import hidden.indev0r.game.entity.Entity;
import hidden.indev0r.game.entity.player.Player;
import org.newdawn.slick.geom.Rectangle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by MrDeathJockey on 14/12/3.
 */
public class TileMapZone {

	private Map<String, String> properties = new HashMap<>();

    private List<Entity> zoneEntities = new ArrayList<>();
	private TileMap map;
	private String  zoneID;
	private int     x, y, w, h;

	public TileMapZone(TileMap map, String zoneID, String property, int x, int y, int w, int h) {
		this.map = map;
		this.zoneID = zoneID;
		this.x = x;
		this.y = y;
		this.w = w - 1;
		this.h = h - 1;

        if(this.w < 0) this.w = 0;
        if(this.h < 0) this.h = 0;

		//Assigning properties
		String[] propertySegments = property.split(";");
		for (String segment : propertySegments) {
			String[] kvPair = segment.split("=");
			properties.put(kvPair[0], (kvPair.length > 1) ? kvPair[1] : "");
		}
	}

    public void enterZone(Entity e) {
        if(!isEntityInZone(e))
            zoneEntities.add(e);

        if(e instanceof Player) {
            if(propertyExists("showName"))
                MedievalLauncher.getInstance().getGameState().announceName(getProperty("showName"));
        }
    }

    public void leaveZone(Entity e) {
        if(isEntityInZone(e)) {
            zoneEntities.remove(e);
        }
    }

    public boolean inBounds(int cx, int cy) {
        return inBounds(cx, cy, 1, 1);
    }

    public boolean inBounds(int cx, int cy, int cw, int ch) {
        return new Rectangle(cx, cy, cw, ch).intersects(new Rectangle(x, y, w, h));
    }

    public boolean isEntityInZone(Entity e) {
        for(Entity entity : zoneEntities) {
            if(entity.equals(e)) return true;
        }
        return false;
    }

	public boolean propertyExists(String propertyKey) {
		return properties.get(propertyKey) != null;
	}

	public String getProperty(String propertyKey) {
		return properties.get(propertyKey);
	}

	public String getZoneID() {
		return zoneID;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getW() {
		return w;
	}

	public int getH() {
		return h;
	}
}
