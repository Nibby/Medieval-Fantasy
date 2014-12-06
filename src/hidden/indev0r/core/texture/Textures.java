package hidden.indev0r.core.texture;

import org.newdawn.slick.Image;
import org.newdawn.slick.SpriteSheet;

public class Textures {

	private static final String ENTITY_IMAGE_PATH    = "res/textures/entity/";
	private static final String UI_GAME_ELEMENT_PATH = "res/textures/ui_elements/game/";
	private static final String CURSOR_IMAGE_PATH    = "res/textures/cursors/";


	public static final class Images {
		public static final Image DUNGEON_MASK = ResourceLoader.loadImage("res/mask.png");
	}


	public static final class SpriteSheets {
		public static final SpriteSheet WIZARD  = ResourceLoader.loadSpriteSheet(ENTITY_IMAGE_PATH + "player.png", 32, 32);
		public static final SpriteSheet UI_FONT = ResourceLoader.loadSpriteSheet("res/font.png", 16, 20);
	}


	public static final class UI {
		public static final Image BUTTON         = ResourceLoader.loadImage(UI_GAME_ELEMENT_PATH + "minimap/BUTTON.png");
		public static final Image BUTTON_PRESSED = ResourceLoader.loadImage(UI_GAME_ELEMENT_PATH + "minimap/BUTTON_PRESSED.png");

		public static final Image FRAME_TOP_RIGHT     = ResourceLoader.loadImage(UI_GAME_ELEMENT_PATH + "frame/TOP_RIGHT.png");
		public static final Image FRAME_TOP_MIDDLE    = ResourceLoader.loadImage(UI_GAME_ELEMENT_PATH + "frame/TOP_MIDDLE.png");
		public static final Image FRAME_TOP_LEFT      = ResourceLoader.loadImage(UI_GAME_ELEMENT_PATH + "frame/TOP_LEFT.png");
		public static final Image FRAME_MIDDLE_RIGHT  = ResourceLoader.loadImage(UI_GAME_ELEMENT_PATH + "frame/MIDDLE_RIGHT.png");
		public static final Image FRAME_MIDDLE        = ResourceLoader.loadImage(UI_GAME_ELEMENT_PATH + "frame/MIDDLE.png");
		public static final Image FRAME_MIDDLE_LEFT   = ResourceLoader.loadImage(UI_GAME_ELEMENT_PATH + "frame/MIDDLE_LEFT.png");
		public static final Image FRAME_BOTTOM_RIGHT  = ResourceLoader.loadImage(UI_GAME_ELEMENT_PATH + "frame/BOTTOM_RIGHT.png");
		public static final Image FRAME_BOTTOM_MIDDLE = ResourceLoader.loadImage(UI_GAME_ELEMENT_PATH + "frame/BOTTOM_MIDDLE.png");
		public static final Image FRAME_BOTTOM_LEFT   = ResourceLoader.loadImage(UI_GAME_ELEMENT_PATH + "frame/BOTTOM_LEFT.png");

		public static final Image FRAME_TOP_RIGHT_DIALOG  = ResourceLoader.loadImage(UI_GAME_ELEMENT_PATH + "frame/TOP_RIGHT_DIALOG.png");
		public static final Image FRAME_TOP_MIDDLE_DIALOG = ResourceLoader.loadImage(UI_GAME_ELEMENT_PATH + "frame/TOP_MIDDLE_DIALOG.png");
		public static final Image FRAME_TOP_LEFT_DIALOG   = ResourceLoader.loadImage(UI_GAME_ELEMENT_PATH + "frame/TOP_LEFT_DIALOG.png");

	}


	public static final class Cursors {
		public static final Image FINGER              = ResourceLoader.loadImage(CURSOR_IMAGE_PATH + "FINGER_NORMAL.png");
		public static final Image FINGER_PRESSED      = ResourceLoader.loadImage(CURSOR_IMAGE_PATH + "FINGER_PRESSED.png");
		public static final Image POINTER_RED         = ResourceLoader.loadImage(CURSOR_IMAGE_PATH + "POINTER_RED.png");
		public static final Image POINTER_BLACK       = ResourceLoader.loadImage(CURSOR_IMAGE_PATH + "POINTER_BLACK.png");
		public static final Image POINTER_WHITE       = ResourceLoader.loadImage(CURSOR_IMAGE_PATH + "POINTER_WHITE.png");
		public static final Image POINTER_YELLOW      = ResourceLoader.loadImage(CURSOR_IMAGE_PATH + "POINTER_YELLOW.png");
		public static final Image POINTER_DARK_YELLOW = ResourceLoader.loadImage(CURSOR_IMAGE_PATH + "POINTER_DARK_YELLOW.png");

	}

}
