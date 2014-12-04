package hidden.indev0r.core.texture;

import hidden.indev0r.core.reference.References;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class ResourceLoader {

	protected static Image loadImage(String location) {
		Image res = null;
		try {
			res = new Image(location);
			res.setFilter(Image.FILTER_NEAREST);
		} catch (SlickException e) {
			e.printStackTrace();
		}
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
		return res;
	}

}
