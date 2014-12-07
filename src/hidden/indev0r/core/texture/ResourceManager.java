package hidden.indev0r.core.texture;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

public class ResourceManager {

	private static final Map<String, Object> RESOURCES = new HashMap<>();

	public static Object get(String id) {
		return RESOURCES.get(id);
	}

	private static void put(String id, Object resource) {
		if (get(id) != null) {
			JOptionPane.showMessageDialog(null, "Duplicated texture id: '" + id + "'!", "Internal Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		RESOURCES.put(id, resource);
	}

	protected static Image loadImage(String location) {
		Image res = null;
		try {
			res = new Image(location);
			res.setFilter(Image.FILTER_NEAREST);
		} catch (SlickException e) {
			e.printStackTrace();
		}

		put("img:" + location, res);

		return res;
	}

	protected static SpriteSheet loadSpriteSheet(String location, int width, int height) {
		SpriteSheet res = null;
		try {
			res = new SpriteSheet(location, width, height);
			res.setFilter(Image.FILTER_NEAREST);
		} catch (SlickException e) {
			e.printStackTrace();
		}

		put("spritesheet:" + location, res);
		return res;
	}


}
