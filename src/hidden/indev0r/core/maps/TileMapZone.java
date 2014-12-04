package hidden.indev0r.core.maps;

import hidden.indev0r.core.entity.Entity;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by MrDeathJockey on 14/12/3.
 */
public class TileMapZone {

    private Map<String, String> properties = new HashMap<>();

    private TileMap map;
    private String zoneID;
    private int x, y, w, h;

    public TileMapZone(TileMap map, String zoneID, String property, int x, int y, int w, int h) {
        this.map = map;
        this.zoneID = zoneID;
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;

        //Assigning properties
        String[] propertySegments = property.split(";");
        for(String segment : propertySegments) {
            String[] kvPair = segment.split("=");

            properties.put(kvPair[0], (kvPair.length > 1) ? kvPair[1] : "");
        }
    }

    public void steppedInto(Entity entity) {

    }

    public void steppedOut(Entity entity) {

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
