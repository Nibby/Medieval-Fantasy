package hidden.indev0r.core.map;

import hidden.indev0r.core.reference.References;
import hidden.indev0r.core.util.CipherEngine;
import hidden.indev0r.core.util.XMLParser;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.swing.*;
import java.awt.*;
import java.io.DataInputStream;
import java.nio.charset.Charset;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by MrDeathJockey on 14/12/3.
 * <p/>
 * This is the database class that loads and registers all the available tile map in the 'data/map' directory.
 * <p/>
 * Each map is registered based on their given identifier key (or codename)
 */
public class TileMapDatabase {


	private static final Map<String, TileMap> tileMaps = new HashMap<>();

	/**
	 * This method should only be invoked upon the init() method in the main game class.
	 * <p/>
	 * As the name implies, this will initiate the entire map loading sequence.
	 */
	public void loadMaps() throws Exception {
		DirectoryStream<Path> entries = Files.newDirectoryStream(References.MAP_PATH.toAbsolutePath());

		for (Path entry : entries) {
			if (entry.getFileName().toString().endsWith(".tm")) {
				TileMap map = loadTileMap(entry);

				if (map != null) {
					registerTileMap(map);
				}
			}
		}
	}

	/**
	 * Loads a tile map from given file
	 *
	 * @param entry The given map file
	 * @return
	 */
	private TileMap loadTileMap(Path entry) throws Exception {
		if (!entry.getFileName().toString().endsWith(".tm")) return null;

		Cipher cipher = CipherEngine.getCipher(Cipher.DECRYPT_MODE, "TTMSECKY");
		DataInputStream input = new DataInputStream(new CipherInputStream(Files.newInputStream(entry), cipher));
		byte[] bytes = new byte[input.readInt()];
		input.readFully(bytes);
		input.close();

		String data = new String(bytes, Charset.forName("UTF-8"));

		XMLParser file = new XMLParser(data);
		Document doc = file.getDocument();

		Element root = (Element) doc.getElementsByTagName("ttmMap").item(0);
		String mapName = root.getAttribute("name");
		String mapCodename = root.getAttribute("code");
		int mapWidth = Integer.parseInt(root.getAttribute("width"));
		int mapHeight = Integer.parseInt(root.getAttribute("height"));
		int mapLayer = Integer.parseInt(root.getAttribute("layers"));
		String mapProperties = root.getAttribute("properties");

		//Read tiles
		int[][][] mapTiles = new int[mapLayer][mapWidth][mapHeight];

		Element tileData = (Element) root.getElementsByTagName("tileData").item(0);
		NodeList tileLayers = tileData.getElementsByTagName("tileLayer");
		for (int i = 0; i < tileLayers.getLength(); i++) {
			Element tileLayer = (Element) tileLayers.item(i);
			int layerNum = Integer.parseInt(tileLayer.getAttribute("layer"));

			String layerData = tileLayer.getTextContent();
			String[] layerRaw = layerData.split(",");
			int x = 0, y = 0;
			for (int j = 0; j < layerRaw.length; j++) {
				if (y > mapHeight - 1) {
					y = 0;
					x++;
				}
				mapTiles[layerNum][x][y] = Integer.parseInt(layerRaw[j]);
				y++;
			}
		}
		TileMap map = new TileMap(mapName, mapCodename, mapTiles, mapProperties);

		//Zone data
		Element zoneData = (Element) root.getElementsByTagName("zoneData").item(0);
		NodeList zoneList = zoneData.getElementsByTagName("zone");
		for (int i = 0; i < zoneList.getLength(); i++) {
			Element eZone = (Element) zoneList.item(i);

			int zx = Integer.parseInt(eZone.getAttribute("x"));
			int zy = Integer.parseInt(eZone.getAttribute("y"));
			int zw = Integer.parseInt(eZone.getAttribute("width"));
			int zh = Integer.parseInt(eZone.getAttribute("height"));
			String zID = eZone.getAttribute("identifier");

			StringBuffer zProperty = new StringBuffer();
			NodeList zonePropList = eZone.getElementsByTagName("zoneProperty");
			for (int j = 0; j < zonePropList.getLength(); j++) {
				Element eZoneProp = (Element) zonePropList.item(j);

				String property = eZoneProp.getTextContent();
				zProperty.append(property);
				if (j != zonePropList.getLength() - 1) {
					zProperty.append(";");
				}
			}
			TileMapZone zone = new TileMapZone(map, zID, zProperty.toString(), zx, zy, zw, zh);

			map.addZone(zone);
		}

		//Warp points
		Element eWarpPoints = (Element) root.getElementsByTagName("warpPoints").item(0);
		NodeList listWarpPoints = eWarpPoints.getElementsByTagName("warp");
		for (int i = 0; i < listWarpPoints.getLength(); i++) {
			Element eWarp = (Element) listWarpPoints.item(i);

			String target = eWarp.getAttribute("targetMapID");
			int tx = Integer.parseInt(eWarp.getAttribute("targetX"));
			int ty = Integer.parseInt(eWarp.getAttribute("targetY"));
			int ox = Integer.parseInt(eWarp.getAttribute("fromX"));
			int oy = Integer.parseInt(eWarp.getAttribute("fromY"));

			MapWarpPoint warp = new MapWarpPoint(target, new Point(ox, oy), new Point(tx, ty));
			map.addWarpPoint(warp);
		}

		return map;
	}

	//Is made private for a Singleton class design.
	//Multiple instances of this class is NOT NECESSARY
	private TileMapDatabase() {
	}

	public static TileMapDatabase getDatabase() {
		return database;
	}

	private static final TileMapDatabase database = new TileMapDatabase();

	private static final void registerTileMap(TileMap map) {
		if (tileMaps.get(map.getIdentifierName()) != null) {
			JOptionPane.showMessageDialog(null,
					"Another map with identifier '" + map.getIdentifierName() + "' is already registered!",
					"Internal Error", JOptionPane.ERROR_MESSAGE);
			return;
		}

		tileMaps.put(map.getIdentifierName(), map);
	}

	public static TileMap getTileMap(String identifier) {
		return tileMaps.get(identifier);
	}
}
