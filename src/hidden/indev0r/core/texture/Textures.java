package hidden.indev0r.core.texture;

import org.newdawn.slick.Image;
import org.newdawn.slick.SpriteSheet;

public class Textures {

	private static final String ENTITY_IMAGE_PATH = "res/textures/entity/";
	private static final String UI_GAME_ELEMENT_PATH = "res/textures/ui_elements/game/";

	public static final class Images {
		//View Elements
		public static final Image DUNGEON_MASK = ResourceLoader.loadImage("res/mask.png");

	}

	public static final class SpriteSheets {
		//Characters
		public static final SpriteSheet WIZARD = ResourceLoader.loadSpriteSheet(ENTITY_IMAGE_PATH + "player.png", 32, 32);

		//Font
        public static final SpriteSheet UI_FONT = ResourceLoader.loadSpriteSheet("res/font.png", 16, 20);
    }

	public static final class UI {
		public static final Image HOTBAR = ResourceLoader.loadImage(UI_GAME_ELEMENT_PATH + "UI_HOTBAR.png");
		public static final Image STATS = ResourceLoader.loadImage(UI_GAME_ELEMENT_PATH + "UI_STATS.png");
	}

}
