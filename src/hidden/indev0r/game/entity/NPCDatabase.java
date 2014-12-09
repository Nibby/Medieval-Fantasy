package hidden.indev0r.game.entity;

import hidden.indev0r.game.Colors;
import hidden.indev0r.game.entity.animation.ActionSet;
import hidden.indev0r.game.entity.animation.ActionSetDatabase;
import hidden.indev0r.game.entity.npc.script.Script;
import hidden.indev0r.game.entity.npc.script.ScriptParser;
import hidden.indev0r.game.map.MapDirection;
import hidden.indev0r.game.map.Tile;
import hidden.indev0r.game.map.TileMap;
import hidden.indev0r.game.map.TileMapDatabase;
import hidden.indev0r.game.reference.References;
import hidden.indev0r.game.texture.ResourceManager;
import hidden.indev0r.game.util.CipherEngine;
import hidden.indev0r.game.util.XMLParser;
import org.lwjgl.util.vector.Vector2f;
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
 * Created by MrDeathJockey on 14/12/8.
 *
 * This is the data center for all the NPCs that are loaded in the game.
 */
public class NPCDatabase {

    private static final Map<String, NPC> npcMap = new HashMap<>();

    /*
        This method should be invoked only once upon game initialization.
        It will proceed to scan the NPC directories for all NPCs and register them
        in the form of 'folder'.'id'

        For example, if npc with id 'merlin' is in the folder 'test', then the
        corresponding NPC key will be 'test.merlin'.

        If in multiple folders, such as 'merlin' being in 'folder1' that's in 'folder2',
        then the key will be 'test2.test1.merlin'
     */
    public void loadNPCs() throws Exception {
        Path npcFolder = References.NPC_PATH;
        loadDirectory(npcFolder);
    }

    private void loadDirectory(Path npcFolder) throws Exception {
        DirectoryStream<Path> npcFiles = Files.newDirectoryStream(npcFolder);
        for(Path path : npcFiles) {
            if(Files.isDirectory(path)) {
                loadDirectory(path);
                continue;
            }
            if(path.getFileName().toString().endsWith(".dat")) {
                loadNPC(path);
            }
        }
    }

    private void loadNPC(Path path) throws Exception {
        if(Files.isDirectory(path) || !path.toString().endsWith(".dat")) return;

        Cipher c = CipherEngine.getCipher(Cipher.DECRYPT_MODE, References.CIPHER_KEY_2);
        DataInputStream input = new DataInputStream(new CipherInputStream(Files.newInputStream(path), c));
        byte[] bytes = new byte[input.readInt()];
        input.readFully(bytes);
        input.close();

        String data = new String(bytes, Charset.forName("UTF-8"));
        XMLParser parser = new XMLParser(data);

        Document doc = parser.getDocument();
        Element root = (Element) doc.getElementsByTagName("npc").item(0);

        try {
            NPC.Faction npcFaction = NPC.Faction.valueOf(root.getAttribute("faction"));
            int npcWidth = Integer.parseInt(root.getAttribute("width"));
            int npcHeight = Integer.parseInt(root.getAttribute("height"));
            String npcName = root.getAttribute("name");
            String npcId = root.getAttribute("identifier");

            NPC npc = new NPC(npcId, npcFaction, npcName, new Vector2f(0, 0));
            npc.setSize(npcWidth * Tile.TILE_SIZE, npcHeight * Tile.TILE_SIZE);

            if(root.hasAttribute("resource") && root.hasAttribute("sprite")) {
                SpriteSheet resource = (SpriteSheet) ResourceManager.get("spritesheet:" + root.getAttribute("resource"));
                if(resource != null) {
                    String[] frameCoords = root.getAttribute("sprite").split(",");
                    int frameX = Integer.parseInt(frameCoords[0]);
                    int frameY = Integer.parseInt(frameCoords[1]);
                    Image image = resource.getSprite(frameX, frameY);
                    npc.setSprite(image);
                }
            } else if(root.hasAttribute("actionSet")) {
                int actionSetID = Integer.parseInt(root.getAttribute("actionSet"));
                ActionSet actionSet = ActionSetDatabase.get(actionSetID);
                if(actionSet == null) {
                    JOptionPane.showMessageDialog(null, "Specified action setStat '" + actionSetID + "' for NPC '" + path + "' is null!",
                            "Internal Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                npc.setActionSet(actionSet);
            }

            if(root.hasAttribute("nameColor")) {
                Colors color = Colors.valueOf(root.getAttribute("nameColor"));
                if(color != null)
                    npc.setNameColor(color.getColor());
            }

            if(root.hasAttribute("facing")) {
                MapDirection direction = MapDirection.valueOf(root.getAttribute("facing"));
                if(direction != null)
                    npc.setFacingDirection(direction);
            }

            //Loads location
            Element eLocation = (Element) root.getElementsByTagName("location").item(0);
            String npcAssignedMap = (eLocation.getElementsByTagName("map").item(0)).getTextContent();
            String[] npcCoords = eLocation.getElementsByTagName("pos").item(0).getTextContent().split(",");

            TileMap npcMap = TileMapDatabase.getTileMap(npcAssignedMap);
            if(npcMap != null) {
                int npcX = Integer.parseInt(npcCoords[0]);
                int npcY = Integer.parseInt(npcCoords[1]);

                npc.setPosition(npcX, npcY);
                npcMap.addEntity(npc);
            } else {
                JOptionPane.showMessageDialog(null, "The specified map idenfier '" + npcAssignedMap + "' for NPC '" + path + "' is invalid!",
                        "Internal Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            //Loads properties
            Element eStats = (Element) root.getElementsByTagName("stats").item(0);
            NodeList statList = eStats.getElementsByTagName("stat");
            for(int i = 0; i < statList.getLength(); i++) {
                Element eStat = (Element) statList.item(i);

                Actor.Stat stat = Actor.Stat.valueOf(eStat.getAttribute("type"));
                String value = eStat.getAttribute("value");

                npc.setStat(stat, Integer.parseInt(value));
            }

            Path relative = References.NPC_PATH.relativize(path);
            String key = "";
            for(int i = 0; i < relative.getNameCount() - 1; i++) {
                key += relative.getName(i) + ".";
            }
            key += relative.getName(relative.getNameCount() - 1).toString().replace(".dat", "");
            put(key, npc);

            //Load scripts
            NodeList scriptList = root.getElementsByTagName("script");
            for(int i = 0; i < scriptList.getLength(); i++) {
                Element eScript = (Element) scriptList.item(i);

                Script script = ScriptParser.parse(npc, eScript);
                if(script != null)
                    npc.addScript(script);
                else
                    JOptionPane.showMessageDialog(null, "Cannot create script '" + eScript.getAttribute("type") + "' for NPC '" + npcName + "'!",
                            "Internal Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch(Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "An error occurred while loading NPC '" + path + "'\n" + e,
                    "Internal Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
    }

    public static final NPC get(String key) {
        return npcMap.get(key);
    }

    private static final void put(String key, NPC npc) {
        if(npcMap.get(key) != null) {
            JOptionPane.showMessageDialog(null, "Unable to register NPC '" + key + "' because it was already taken!",
                    "Internal Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        npcMap.put(key, npc);
    }

    private static final NPCDatabase database = new NPCDatabase();

    private NPCDatabase() {}

    public static final NPCDatabase getDatabase() { return database; }
}
