package hidden.indev0r.core.map;

import hidden.indev0r.core.entity.Entity;
import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by MrDeathJockey on 14/12/3.
 * <p/>
 * This is a single generic representation of a usual tile. Loaded from XML, it has a properties sheet that contains collision information and a few
 * methods concerning game logic.
 * <p/>
 * A collection of tiles belong in tilesets, but it is also universally registered on a database since tiles aren't strictly limited to being in a
 * tileset.
 */
public class Tile {

	public static final  int                TILE_SIZE = 32; //actual tile size
	//Tile database, contains all the registered, unique tiles
	private static final Map<Integer, Tile> tiles     = new HashMap<>();

	private Map<String, String> properties = new HashMap<>();

	private int       id;
	private Animation texture;

	//Tile animation timers
	private static final int frameTickDelay = 750; //ms

	/**
	 * This creates a generic tile instance.
	 *
	 * @param id         The ID of the tile, must be unique.
	 * @param tileFrames The animation frames of the tile described as x,y co-ordinates on the given spritesheet.
	 * @param properties A single condensed string of properties, will be split and placed inside 'properties'
	 */
	public Tile(int id, Tileset tileset, Point[] tileFrames, String properties) {
		this.id = id;
		Image[] frames = new Image[tileFrames.length];

		for (int i = 0; i < tileFrames.length; i++) {
			Point p = tileFrames[i];
			frames[i] = tileset.getTexture(p.x, p.y);

		}
		texture = new Animation(frames, frameTickDelay);

		//Decodes the properties string and registers properties into a map
		String[] propertySegments = properties.split(";");
		for (String segment : propertySegments) {
			String[] kvPair = segment.split("=");

			this.properties.put(kvPair[0], (kvPair.length > 1) ? kvPair[1] : "");
		}
	}

	public void tick(GameContainer gc) {
	}

	public void render(Graphics g, float x, float y) {
		g.drawAnimation(texture, x, y);
	}

	public void steppedOn(Entity entity) {
        entity.setSunken(propertyExists("liquid"));
	}

    public void steppedOut(Entity entity) {
    }

	public boolean propertyExists(String propertyKey) {
		return properties.get(propertyKey) != null;
	}

	public String getProperty(String propertyKey) {
		return properties.get(propertyKey);
	}

	public int getId() {
		return id;
	}

	public static void registerTile(Tile tile) {
		if (tiles.get(tile.getId()) != null) {
			JOptionPane.showMessageDialog(null, "Tile ID '" + tile.getId() + "' has already been taken!",
					"Internal Error", JOptionPane.ERROR_MESSAGE);
			return;
		}

		tiles.put(tile.getId(), tile);
	}

	public static Tile getTile(int id) {
		return tiles.get(id);
	}
}
