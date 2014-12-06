package hidden.indev0r.core.texture;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

public class Textures {



	private static final String ENTITY_IMAGE_PATH = "res/textures/sprites/";
    private static final String ENTITY_ANIM_IMAGE_PATH = ENTITY_IMAGE_PATH  + "animated/";
    private static final String ENTITY_STATIC_IMAGE_PATH = ENTITY_IMAGE_PATH  + "static/";

	public static void Init() {}

	public static final class SpriteSheets {

		public static final SpriteSheet ANIM_MAGE_0    = ResourceManager.loadSpriteSheet(ENTITY_ANIM_IMAGE_PATH + "mage0.png", 32, 32);
		public static final SpriteSheet ANIM_WARRIOR_0 = ResourceManager.loadSpriteSheet(ENTITY_ANIM_IMAGE_PATH + "warrior0.png", 32, 32);
		public static final SpriteSheet ANIM_ROGUE_0   = ResourceManager.loadSpriteSheet(ENTITY_ANIM_IMAGE_PATH + "rogue0.png", 32, 32);

		public static final SpriteSheet UI_FONT = ResourceManager.loadSpriteSheet("res/font.png", 16, 20);
	}


	public static final class Images {
		//Map
		public static final Image EMPTY = ResourceManager.loadImage("res/empty.png");

		//View Elements
		public static final Image DUNGEON_MASK = ResourceManager.loadImage("res/mask.png");
	}


}
