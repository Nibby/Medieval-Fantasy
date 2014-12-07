package hidden.indev0r.core.maps;

import hidden.indev0r.core.reference.References;
import hidden.indev0r.core.util.CipherEngine;
import hidden.indev0r.core.util.XMLParser;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import java.awt.*;
import java.io.DataInputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Created by MrDeathJockey on 14/12/3.
 * <p/>
 * This is the database class for tilesets. No need to keep the tileset instances as their only purpose is to supply their tiles with images.
 * <p/>
 * Originally I thought this was not required, but turns out tile frames & tile animation is dependant on them.
 */
public class TilesetDatabase {

	/**
	 * This method should only be invoked upon the init() method in the main game class.
	 * <p/>
	 * As the name implies, this will initiate the entie tileset loading sequence.
	 */
	public void loadTilesets() throws Exception {
		Path tilesetPath = References.TILESET_DEFINITION_PATH;
		Cipher cipher = CipherEngine.getCipher(Cipher.DECRYPT_MODE, References.CIPHER_KEY_1);

		//Read raw data & decrypt
		DataInputStream input = new DataInputStream(new CipherInputStream(Files.newInputStream(tilesetPath), cipher));
		byte[] bytes = new byte[input.readInt()];
		input.readFully(bytes);

		//Parse decrypted content and dissect XML string
		String data = new String(bytes, Charset.forName("UTF-8"));
		XMLParser parser = new XMLParser(data);

		Document doc = parser.getDocument();
		Element root = (Element) doc.getElementsByTagName("ttTileset").item(0);
		NodeList tilesets = root.getElementsByTagName("tileset");

		for (int i = 0; i < tilesets.getLength(); i++) {
			Element eTileset = (Element) tilesets.item(i);

			String tilesetName = eTileset.getAttribute("name");
			String tilesetTexturePath = eTileset.getAttribute("texture");

			Tileset tileset = new Tileset(References.DATA_PATH.resolve(tilesetTexturePath).toAbsolutePath().toString());

			//Load associated tiles
			NodeList tiles = eTileset.getElementsByTagName("tile");
			for (int j = 0; j < tiles.getLength(); j++) {
				Element eTile = (Element) tiles.item(j);

				String tileProperties = eTile.getAttribute("properties");
				String tileTexture = eTile.getAttribute("texture");
				int tileID = Integer.parseInt(eTile.getAttribute("id"));

				String[] raw = tileTexture.split(";");
				Point[] points = new Point[raw.length];
				for (int a = 0; a < raw.length; a++) {
					String[] pointInfo = raw[a].split(",");
					points[a] = new Point(Integer.parseInt(pointInfo[0]), Integer.parseInt(pointInfo[1]));
				}

				Tile tile = new Tile(tileID, tileset, points, tileProperties);
				Tile.registerTile(tile);
			}

			registerTileset(tileset);
		}
	}

	private void registerTileset(Tileset tileset) {
		//Stub
	}

	//Is made private for a Singleton class design.
	//Multiple instances of this class is NOT NECESSARY
	private TilesetDatabase() {
	}

	public static TilesetDatabase getDatabase() {
		return database;
	}

	private static final TilesetDatabase database = new TilesetDatabase();
}
