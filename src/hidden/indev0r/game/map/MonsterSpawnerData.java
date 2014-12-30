package hidden.indev0r.game.map;

import hidden.indev0r.game.entity.Entity;
import hidden.indev0r.game.entity.Monster;
import org.newdawn.slick.geom.Rectangle;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MrDeathJockey on 14/12/26.
 *
 * Contains information regarding a specific monster spawn
 */
public class MonsterSpawnerData {

    private List<MonsterSpawnerDataEntry> spawnEntries = new ArrayList<>();

    private Rectangle region;
    private int monsterLimit;

    protected MonsterSpawnerData(int x, int y, int w, int h, int limit) {
        region = new Rectangle(x, y, w, h);
        this.monsterLimit = limit;
    }

    public void addSpawnEntry(MonsterSpawnerDataEntry entry) {
        spawnEntries.add(entry);
    }

    public Rectangle getRegion() {
        return region;
    }

    public int getMonsterLimit() {
        return monsterLimit;
    }

    public void spawnMonsters(TileMap map) {
        List<Entity> regionEntities = map.getEntitiesInRegion((int) region.getX(),
                (int) region.getY(), (int) region.getWidth(), (int) region.getHeight());
        int monCount = 0;
        for(Entity e : regionEntities) {
            if(e instanceof Monster) monCount++;
        }

        if(monCount > monsterLimit) {
            for(MonsterSpawnerDataEntry e : spawnEntries) e.updateSpawnTimer();
            return;
        }

        for(MonsterSpawnerDataEntry e : spawnEntries)
            e.trySpawn(map);
    }
}
