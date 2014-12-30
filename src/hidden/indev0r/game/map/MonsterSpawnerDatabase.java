package hidden.indev0r.game.map;

import hidden.indev0r.game.entity.npc.script.Script;
import hidden.indev0r.game.reference.References;
import hidden.indev0r.game.util.CipherEngine;
import hidden.indev0r.game.util.XMLParser;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import java.io.DataInputStream;
import java.nio.charset.Charset;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by MrDeathJockey on 14/12/26.
 *
 * Holds all information regarding monster spawning
 */
public class MonsterSpawnerDatabase {

    private static final Map<TileMap, MonsterSpawner> spawnList = new HashMap<>();

    public void loadMonsterSpawners() throws Exception {
        Path spawnerPath = References.MONSTER_SPAWN_PATH;
        DirectoryStream<Path> spawnFileStream = Files.newDirectoryStream(spawnerPath);

        for(Path spawnFile : spawnFileStream) {
            if(Files.isDirectory(spawnFile)) continue;
            if(!spawnFile.getFileName().toString().endsWith(".dat")) continue;

            loadSpawnData(spawnFile);
        }
    }

    private static void loadSpawnData(Path spawnFile) throws Exception {
        if(Files.isDirectory(spawnFile)) return;
        if(!spawnFile.getFileName().toString().endsWith(".dat")) return;

        TileMap assocMap = TileMapDatabase.getTileMap(spawnFile.getFileName().toString().replace(".dat", ""));
        if(assocMap == null) {
            System.err.println("Unable to locate associated map for spawn file:\n" + spawnFile.getFileName().toString() + "!\n" +
                    "Entry is ignored.");
            return;
        }

        MonsterSpawner monSpawner = new MonsterSpawner(assocMap);

        Cipher c = CipherEngine.getCipher(Cipher.DECRYPT_MODE, References.CIPHER_KEY_2);
        DataInputStream input = new DataInputStream(new CipherInputStream(Files.newInputStream(spawnFile), c));
        byte[] bytes = new byte[input.readInt()];
        input.readFully(bytes);
        input.close();

        String data = new String(bytes, Charset.forName("UTF-8"));
        XMLParser xml = new XMLParser(data);
        Document doc = xml.getDocument();
        Element root = (Element) doc.getElementsByTagName("spawnList").item(0);

        NodeList spawnZoneList = root.getElementsByTagName("spawnZone");
        for(int i = 0; i < spawnZoneList.getLength(); i++) {
            Element e = (Element) spawnZoneList.item(i);

            String zid = e.getAttribute("id");
            TileMapZone zone = assocMap.getZone(zid);
            if(zone == null) {
                System.err.println("Unable to find specified zone: '" + zid + "' for spawn list " + spawnFile.getFileName().toString() +"\n" +
                        "This section is ignored!");
                continue;
            }
            int zlimit = Integer.parseInt(e.getAttribute("limit"));
            int zx = zone.getX();
            int zy = zone.getY();
            int zw = zone.getWidth();
            int zh = zone.getHeight();

            MonsterSpawnerData spawnerData = new MonsterSpawnerData(zx, zy, zw, zh, zlimit);

            NodeList spawnEntryList = e.getElementsByTagName("spawn");
            for(int j = 0; j < spawnEntryList.getLength(); j++) {
                Element ee = (Element) spawnEntryList.item(j);

                MonsterSpawnerDataEntry entry = loadSpawnDataEntry(spawnerData, ee);
                spawnerData.addSpawnEntry(entry);
            }
            monSpawner.addData(spawnerData);
        }

        spawnList.put(assocMap, monSpawner);
    }

    private static MonsterSpawnerDataEntry loadSpawnDataEntry(MonsterSpawnerData data, Element ee) {
        String monID = ee.getAttribute("mon");
        int spawnInterval = Integer.parseInt(Script.translate(ee.getAttribute("interval"), ee.getAttribute("randomParams")).toString());
        boolean spawnOffscreen = Boolean.parseBoolean(ee.getAttribute("offScreenOnly"));
        boolean spawnConfine = (ee.hasAttribute("confined") && Boolean.parseBoolean(ee.getAttribute("confined")));

        MonsterSpawnerDataEntry result = new MonsterSpawnerDataEntry(monID, data, spawnInterval, spawnOffscreen, spawnConfine);
        return result;
    }

    public static MonsterSpawner get(TileMap map) {
        return spawnList.get(map);
    }

    private static MonsterSpawnerDatabase database = new MonsterSpawnerDatabase();

    private MonsterSpawnerDatabase() { }

    public static MonsterSpawnerDatabase getDatabase() {
        return database;
    }
}
