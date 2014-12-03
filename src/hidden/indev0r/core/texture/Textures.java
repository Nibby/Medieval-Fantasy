package hidden.indev0r.core.texture;

import org.newdawn.slick.Image;
import org.newdawn.slick.SpriteSheet;

public class Textures {

	private static final String ENTITY_IMAGE_PATH = "res/textures/";

	public static final class SpriteSheets {
		public static final SpriteSheet PLAYER = ResourceLoader.loadSpriteSheet(ENTITY_IMAGE_PATH + "player/player.png");
        public static final Image empty = ResourceLoader.loadImage("res/empty.png");

	}
}
