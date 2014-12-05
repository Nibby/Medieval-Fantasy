package hidden.indev0r.core.texture;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

public class Textures {

    private static final Map<String, Object> RESOURCES = new HashMap<>();

	private static final String ENTITY_IMAGE_PATH = "res/textures/sprites/";
    private static final String ENTITY_ANIM_IMAGE_PATH = ENTITY_IMAGE_PATH  + "animated/";
    private static final String ENTITY_STATIC_IMAGE_PATH = ENTITY_IMAGE_PATH  + "static/";

	public static final class SpriteSheets {

        public static final SpriteSheet ANIM_MAGE_0 = loadSpriteSheet(ENTITY_ANIM_IMAGE_PATH + "mage0.png", 32, 32);
        public static final SpriteSheet ANIM_WARRIOR_0 = loadSpriteSheet(ENTITY_ANIM_IMAGE_PATH + "warrior0.png", 32, 32);
        public static final SpriteSheet ANIM_ROGUE_0 = loadSpriteSheet(ENTITY_ANIM_IMAGE_PATH + "rogue0.png", 32, 32);

        public static final SpriteSheet UI_FONT = loadSpriteSheet("res/font.png", 16, 20);
    }

    public static final class Images {
        //Map
        public static final Image EMPTY = loadImage("res/empty.png");

        //View Elements
        public static final Image DUNGEON_MASK = loadImage("res/mask.png");
    }

    public static Object get(String id) {
        return RESOURCES.get(id);
    }

    private static void put(String id, Object resource) {
        if(get(id) != null) {
            JOptionPane.showMessageDialog(null, "Duplicated texture id: '" + id + "'!", "Internal Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        System.out.println(id);
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
