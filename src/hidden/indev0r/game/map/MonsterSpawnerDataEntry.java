package hidden.indev0r.game.map;

import hidden.indev0r.game.entity.Monster;
import hidden.indev0r.game.entity.MonsterDatabase;
import org.newdawn.slick.geom.Rectangle;

/**
 * Created by MrDeathJockey on 14/12/26.
 */
public class MonsterSpawnerDataEntry {

    private String monsterID;
    private int interval;
    private boolean offScreenOnly;
    private boolean confineToRegion;
    private MonsterSpawnerData data;

    private long spawnTime = 0;

    public MonsterSpawnerDataEntry(String monsterID, MonsterSpawnerData spawnData, int interval, boolean offScreen, boolean confine) {
        this.monsterID = monsterID;
        this.interval = interval;
        this.offScreenOnly = offScreen;
        this.data = spawnData;
        this.confineToRegion = confine;
    }

    public String getMonsterID() {
        return monsterID;
    }

    public int getInterval() {
        return interval;
    }

    public boolean isOffScreenOnly() {
        return offScreenOnly;
    }

    public boolean isConfinedToRegion() {
        return confineToRegion;
    }

    public void updateSpawnTimer() {
        spawnTime = System.currentTimeMillis();
    }

    public void trySpawn(TileMap map) {
        if(System.currentTimeMillis() - spawnTime > interval) {
            Monster monster = MonsterDatabase.get(monsterID);
            if(monster == null) {
                System.err.println("Cannot spawn '"+monsterID+"' because it is an invalid monster ID!");
                return;
            }

            Rectangle region = data.getRegion();
            int rx = (int) region.getX();
            int ry = (int) region.getY();
            int rw = (int) region.getWidth();
            int rh = (int) region.getHeight();

            int mx, my;
            do {
                mx = (int) (Math.random() * rw) + rx;
                my = (int) (Math.random() * rh) + ry;
            } while(map.tileBlocked(mx, my));

            monster.setPosition(mx, my);

            map.addEntity(monster);
            updateSpawnTimer();
        }
    }
}
