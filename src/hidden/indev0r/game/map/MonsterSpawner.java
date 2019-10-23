package hidden.indev0r.game.map;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MrDeathJockey on 14/12/26.
 *
 * A single instance of monster spawner
 */
public class MonsterSpawner {

    private List<MonsterSpawnerData> spawnerData = new ArrayList<>();

    private TileMap map;

    public MonsterSpawner(TileMap map) {
        this.map = map;
        map.setMonsterSpawner(this);
    }

    public void addData(MonsterSpawnerData data) {
        spawnerData.add(data);
    }

    public void spawnMonsters() {
        for(MonsterSpawnerData sd : spawnerData) {
            sd.spawnMonsters(map);
        }
    }
}
