package hidden.indev0r.core.texture;

import org.newdawn.slick.Image;
import org.newdawn.slick.SpriteSheet;

public class Textures {

	private static final String ENTITY_IMAGE_PATH = "res/textures/";

	public static final class SpriteSheets {
		public static final SpriteSheet WIZARD = ResourceLoader.loadSpriteSheet(ENTITY_IMAGE_PATH + "player/player.png", 32);

        public static final Image EMPTY = ResourceLoader.loadImage("res/empty.png");
        public static final Image DUNGEON_MASK = ResourceLoader.loadImage("res/mask.png");

	}
}
