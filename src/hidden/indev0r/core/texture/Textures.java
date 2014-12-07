package hidden.indev0r.core.texture;

import org.newdawn.slick.Image;
import org.newdawn.slick.SpriteSheet;

public class Textures {


	private static final String ENTITY_IMAGE_PATH        = "res/textures/sprites/";
	private static final String ENTITY_STATIC_IMAGE_PATH = ENTITY_IMAGE_PATH + "static/";
	private static final String ENTITY_ANIM_IMAGE_PATH   = ENTITY_IMAGE_PATH + "animated/";

	private static final String UI_GAME_ELEMENT_PATH = "res/textures/ui_elements/game/";
	private static final String CURSOR_IMAGE_PATH    = "res/textures/cursors/";

	public static void Init() {
		SpriteSheets.Init();
	}

	public static final class SpriteSheets {
		public static void Init() {
		}

		public static final SpriteSheet UI_FONT = ResourceManager.loadSpriteSheet("res/font/font.png", 16, 20);


		static {
			ResourceManager.loadSpriteSheet(ENTITY_ANIM_IMAGE_PATH + "mage0.png", 32, 32);
			ResourceManager.loadSpriteSheet(ENTITY_ANIM_IMAGE_PATH + "warrior0.png", 32, 32);
			ResourceManager.loadSpriteSheet(ENTITY_ANIM_IMAGE_PATH + "rogue0.png", 32, 32);
		}
	}


	public static final class Images {
		public static final Image EMPTY        = ResourceManager.loadImage("res/empty.png");
		public static final Image DUNGEON_MASK = ResourceManager.loadImage("res/overlay/mask.png");
	}


	public static final class UI {
		public static final Image MINIMAP_BUTTON         = ResourceManager.loadImage(UI_GAME_ELEMENT_PATH + "minimap/BUTTON.png");
		public static final Image MINIMAP_BUTTON_PRESSED = ResourceManager.loadImage(UI_GAME_ELEMENT_PATH + "minimap/BUTTON_PRESSED.png");

		public static final Image FRAME_TOP_RIGHT         = ResourceManager.loadImage(UI_GAME_ELEMENT_PATH + "frame/TOP_RIGHT.png");
		public static final Image FRAME_TOP_MIDDLE        = ResourceManager.loadImage(UI_GAME_ELEMENT_PATH + "frame/TOP_MIDDLE.png");
		public static final Image FRAME_TOP_LEFT          = ResourceManager.loadImage(UI_GAME_ELEMENT_PATH + "frame/TOP_LEFT.png");
		public static final Image FRAME_MIDDLE_RIGHT      = ResourceManager.loadImage(UI_GAME_ELEMENT_PATH + "frame/MIDDLE_RIGHT.png");
		public static final Image FRAME_MIDDLE            = ResourceManager.loadImage(UI_GAME_ELEMENT_PATH + "frame/MIDDLE.png");
		public static final Image FRAME_MIDDLE_LEFT       = ResourceManager.loadImage(UI_GAME_ELEMENT_PATH + "frame/MIDDLE_LEFT.png");
		public static final Image FRAME_BOTTOM_RIGHT      = ResourceManager.loadImage(UI_GAME_ELEMENT_PATH + "frame/BOTTOM_RIGHT.png");
		public static final Image FRAME_BOTTOM_MIDDLE     = ResourceManager.loadImage(UI_GAME_ELEMENT_PATH + "frame/BOTTOM_MIDDLE.png");
		public static final Image FRAME_BOTTOM_LEFT       = ResourceManager.loadImage(UI_GAME_ELEMENT_PATH + "frame/BOTTOM_LEFT.png");
		public static final Image FRAME_TOP_RIGHT_DIALOG  = ResourceManager.loadImage(UI_GAME_ELEMENT_PATH + "frame/TOP_RIGHT_DIALOG.png");
		public static final Image FRAME_TOP_MIDDLE_DIALOG = ResourceManager.loadImage(UI_GAME_ELEMENT_PATH + "frame/TOP_MIDDLE_DIALOG.png");
		public static final Image FRAME_TOP_LEFT_DIALOG   = ResourceManager.loadImage(UI_GAME_ELEMENT_PATH + "frame/TOP_LEFT_DIALOG.png");

		public static final Image BUTTON_ROUND_GREEN_NORMAL  = ResourceManager.loadImage(UI_GAME_ELEMENT_PATH + "buttons/GREEN_ROUND_NORMAL.png");
		public static final Image BUTTON_ROUND_GREEN_HOVERED = ResourceManager.loadImage(UI_GAME_ELEMENT_PATH + "buttons/GREEN_ROUND_HOVERED.png");
		public static final Image BUTTON_ROUND_GREEN_PRESSED = ResourceManager.loadImage(UI_GAME_ELEMENT_PATH + "buttons/GREEN_ROUND_PRESSED.png");
		public static final Image BUTTON_ROUND_RED_NORMAL    = ResourceManager.loadImage(UI_GAME_ELEMENT_PATH + "buttons/RED_ROUND_NORMAL.png");
		public static final Image BUTTON_ROUND_RED_HOVERED   = ResourceManager.loadImage(UI_GAME_ELEMENT_PATH + "buttons/RED_ROUND_HOVERED.png");
		public static final Image BUTTON_ROUND_RED_PRESSED   = ResourceManager.loadImage(UI_GAME_ELEMENT_PATH + "buttons/RED_ROUND_PRESSED.png");
		public static final Image BUTTON_ROUND_BLUE_NORMAL   = ResourceManager.loadImage(UI_GAME_ELEMENT_PATH + "buttons/BLUE_ROUND_NORMAL.png");
		public static final Image BUTTON_ROUND_BLUE_HOVERED  = ResourceManager.loadImage(UI_GAME_ELEMENT_PATH + "buttons/BLUE_ROUND_HOVERED.png");
		public static final Image BUTTON_ROUND_BLUE_PRESSED  = ResourceManager.loadImage(UI_GAME_ELEMENT_PATH + "buttons/BLUE_ROUND_PRESSED.png");
		public static final Image BUTTON_ROUND_GREY_NORMAL   = ResourceManager.loadImage(UI_GAME_ELEMENT_PATH + "buttons/GREY_ROUND_NORMAL.png");
		public static final Image BUTTON_ROUND_GREY_HOVERED  = ResourceManager.loadImage(UI_GAME_ELEMENT_PATH + "buttons/GREY_ROUND_HOVERED.png");
		public static final Image BUTTON_ROUND_GREY_PRESSED  = ResourceManager.loadImage(UI_GAME_ELEMENT_PATH + "buttons/GREY_ROUND_PRESSED.png");
	}


	public static final class Cursors {
		public static final Image FINGER              = ResourceManager.loadImage(CURSOR_IMAGE_PATH + "FINGER_NORMAL.png");
		public static final Image FINGER_PRESSED      = ResourceManager.loadImage(CURSOR_IMAGE_PATH + "FINGER_PRESSED.png");
		public static final Image POINTER_RED         = ResourceManager.loadImage(CURSOR_IMAGE_PATH + "POINTER_RED.png");
		public static final Image POINTER_BLACK       = ResourceManager.loadImage(CURSOR_IMAGE_PATH + "POINTER_BLACK.png");
		public static final Image POINTER_WHITE       = ResourceManager.loadImage(CURSOR_IMAGE_PATH + "POINTER_WHITE.png");
		public static final Image POINTER_YELLOW      = ResourceManager.loadImage(CURSOR_IMAGE_PATH + "POINTER_YELLOW.png");
		public static final Image POINTER_DARK_YELLOW = ResourceManager.loadImage(CURSOR_IMAGE_PATH + "POINTER_DARK_YELLOW.png");
	}

}
