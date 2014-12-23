package hidden.indev0r.game.entity;

import hidden.indev0r.game.Colors;
import hidden.indev0r.game.entity.ai.AI;
import hidden.indev0r.game.entity.animation.ActionSet;
import hidden.indev0r.game.entity.animation.ActionSetDatabase;
import hidden.indev0r.game.entity.combat.AttackType;
import hidden.indev0r.game.entity.combat.phase.death.DeathType;
import hidden.indev0r.game.entity.npc.script.Script;
import hidden.indev0r.game.entity.npc.script.ScriptParser;
import hidden.indev0r.game.map.Tile;
import hidden.indev0r.game.reference.References;
import hidden.indev0r.game.sound.SoundSet;
import hidden.indev0r.game.texture.ResourceManager;
import hidden.indev0r.game.util.CipherEngine;
import hidden.indev0r.game.util.XMLParser;
import org.newdawn.slick.Color;
import org.newdawn.slick.Image;
import org.newdawn.slick.SpriteSheet;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.swing.*;
import java.io.DataInputStream;
import java.nio.charset.Charset;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by MrDeathJockey on 14/12/17.
 */
public class MonsterDatabase  {

    private static Map<String, Monster> monsterMap = new HashMap<>();

    public void loadMonsters() throws Exception {
        Path monFolder = References.MONSTER_PATH;
        loadDirectory(monFolder);
    }

    private void loadDirectory(Path monFolder) throws Exception {
        DirectoryStream<Path> monFiles = Files.newDirectoryStream(monFolder);
        for(Path path : monFiles) {
            if(Files.isDirectory(path)) {
                loadDirectory(path);
                continue;
            }
            if(path.getFileName().toString().endsWith(".dat")) {
                loadMon(path);
            }
        }
    }

    private void loadMon(Path path) throws Exception {
        if(Files.isDirectory(path) || !path.toString().endsWith(".dat")) return;

        Cipher c = CipherEngine.getCipher(Cipher.DECRYPT_MODE, References.CIPHER_KEY_2);
        DataInputStream input = new DataInputStream(new CipherInputStream(Files.newInputStream(path), c));
        byte[] bytes = new byte[input.readInt()];
        input.readFully(bytes);
        input.close();

        String data = new String(bytes, Charset.forName("UTF-8"));
        XMLParser parser = new XMLParser(data);

        Document doc = parser.getDocument();
        Element root = (Element) doc.getElementsByTagName("monster").item(0);

        //Load root tag info
        String monName = root.getAttribute("name");
        Actor.Faction monFaction = Actor.Faction.valueOf(root.getAttribute("faction"));

        //AI
        Element aiElement = (Element) root.getElementsByTagName("ai").item(0);

        Monster monster = new Monster(monFaction, monName, aiElement);
        AI ai = AI.getAI(monster, aiElement.getAttribute("type"));
        ai.make(aiElement);
        monster.setAI(ai);

        if(root.hasAttribute("resource") && root.hasAttribute("sprite")) {
            SpriteSheet resource = (SpriteSheet) ResourceManager.get("spritesheet:" + root.getAttribute("resource"));
            if(resource != null) {
                String[] frameCoords = root.getAttribute("sprite").split(",");
                int frameX = Integer.parseInt(frameCoords[0]);
                int frameY = Integer.parseInt(frameCoords[1]);
                Image image = resource.getSprite(frameX, frameY);
                monster.setSprite(image);
            }
        } else if(root.hasAttribute("actionSet")) {
            int actionSetID = Integer.parseInt(root.getAttribute("actionSet"));
            ActionSet actionSet = ActionSetDatabase.get(actionSetID);
            if(actionSet == null) {
                JOptionPane.showMessageDialog(null, "Specified action setStat '" + actionSetID + "' for MONSTER '" + path + "' is null!",
                        "Internal Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            monster.setActionSet(actionSet);
        }

        Color monNameColor = Color.red, monMinimapColor = Color.red;
        if(root.hasAttribute("nameColor")) {
            monNameColor = Colors.valueOf(root.getAttribute("nameColor")).getColor();
        }

        if(root.hasAttribute("minimapColor")) {
            monMinimapColor = Colors.valueOf(root.getAttribute("minimapColor")).getColor();
        }

        if(root.hasAttribute("deathType")) {
            monster.setDeathType(DeathType.valueOf(root.getAttribute("deathType")));
        }

        if(root.hasAttribute("approachRange")) {
            monster.setApproachRange(Integer.parseInt(root.getAttribute("approachRange")));
        }

        if(root.hasAttribute("soundSet")) {
            monster.setSoundSet(SoundSet.valueOf(root.getAttribute("soundSet")));
        }

        if(root.hasAttribute("attackType")) {
            monster.setAttackType(AttackType.valueOf(root.getAttribute("attackType")),
                                  root.getAttribute("attackTypeParam"));
        }

        monster.setSize(Integer.parseInt(root.getAttribute("width")) * Tile.TILE_SIZE,
                        Integer.parseInt(root.getAttribute("height")) * Tile.TILE_SIZE);

        monster.setMinimapColor(monMinimapColor);
        monster.setNameColor(monNameColor);

        //Loads properties
        Element eStats = (Element) root.getElementsByTagName("stats").item(0);
        NodeList statList = eStats.getElementsByTagName("stat");
        for(int i = 0; i < statList.getLength(); i++) {
            Element eStat = (Element) statList.item(i);

            Actor.Stat stat = Actor.Stat.valueOf(eStat.getAttribute("type"));
            String value = eStat.getAttribute("value");

            monster.setStat(stat, Integer.parseInt(value));
        }

        //Load scripts
        NodeList scriptList = root.getElementsByTagName("script");
        for(int i = 0; i < scriptList.getLength(); i++) {
            Element eScript = (Element) scriptList.item(i);

            Script script = ScriptParser.parse(monster, eScript);
            if(script == null)
                JOptionPane.showMessageDialog(null, "Cannot create script '" + eScript.getAttribute("type") + "' for MON '" + monName + "'!",
                        "Internal Error", JOptionPane.ERROR_MESSAGE);
        }

        Path relative = References.MONSTER_PATH.relativize(path);
        String key = "";
        for(int i = 0; i < relative.getNameCount() - 1; i++) {
            key += relative.getName(i) + ".";
        }
        key += relative.getName(relative.getNameCount() - 1).toString().replace(".dat", "");
        put(key, monster);
    }

    private void put(String key, Monster monster) {
        if(monsterMap.get(key) != null) {
            JOptionPane.showMessageDialog(null, "Unable to register MONSTER '" + key + "' because it was already taken!",
                    "Internal Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        monsterMap.put(key, monster);
    }

    private static final MonsterDatabase database = new MonsterDatabase();

    private MonsterDatabase() { }

    public static MonsterDatabase getDatabase() {
        return database;
    }

    public static final Monster get(String monsterKey) {
        Monster monster = new Monster(monsterMap.get(monsterKey));
        return monster;
    }
}
