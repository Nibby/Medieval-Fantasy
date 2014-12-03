package hidden.indev0r.core.texture;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class Textures {

	private static final String IMAGES_LOCATION = "/data/images/";


	public static final class SpriteSheets {
		public static final SpriteSheet PLAYER = loadSpriteSheet(IMAGES_LOCATION + "player.png");
	}

	private static Image loadImage(String location) {
		Image res = null;
		try {
			res = new Image(location);
			res.setFilter(Image.FILTER_NEAREST);
		} catch (SlickException e) {
			e.printStackTrace();
		}
		return res;
	}

	private static SpriteSheet loadSpriteSheet(String location) {
		SpriteSheet res = null;
		try {
			res = new SpriteSheet(location, 8, 8);
			res.setFilter(Image.FILTER_NEAREST);
		} catch (SlickException e) {
			e.printStackTrace();
		}
		return res;
	}

}
