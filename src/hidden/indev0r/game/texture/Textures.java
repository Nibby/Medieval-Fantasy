package hidden.indev0r.game.texture;

import org.newdawn.slick.Image;
import org.newdawn.slick.SpriteSheet;

public class Textures {


    private static final String ENTITY_IMAGE_PATH = "res/textures/sprites/";
    private static final String ENTITY_STATIC_IMAGE_PATH = ENTITY_IMAGE_PATH + "static/";
    private static final String ENTITY_ANIM_IMAGE_PATH = ENTITY_IMAGE_PATH + "animated/";

    private static final String UI_GAME_ELEMENT_PATH = "res/textures/ui_elements/game/";
    private static final String CURSOR_IMAGE_PATH = "res/textures/cursors/";

    public static void Init() {
        SpriteSheets.Init();
    }

    public static final class SpriteSheets {
        public static void Init() {
        }

        public static final SpriteSheet UI_FONT = ResourceManager.loadSpriteSheet("res/font/font.png", 16, 20);
        public static final SpriteSheet HIT_EFFECT_MELEE = ResourceManager.loadSpriteSheet("res/textures/effects/hit_melee.png", 32, 32);
        public static final SpriteSheet HIT_EFFECT_CASTER = ResourceManager.loadSpriteSheet("res/textures/effects/hit_caster.png", 32, 32);
        public static final SpriteSheet HIT_EFFECT_RANGER = ResourceManager.loadSpriteSheet("res/textures/effects/hit_ranger.png", 32, 32);

        static {
            ResourceManager.loadSpriteSheet(ENTITY_STATIC_IMAGE_PATH + "mon00.png", 32, 32);
            ResourceManager.loadSpriteSheet(ENTITY_STATIC_IMAGE_PATH + "mon01.png", 32, 32);
            ResourceManager.loadSpriteSheet(ENTITY_STATIC_IMAGE_PATH + "mon02.png", 32, 32);

            ResourceManager.loadSpriteSheet(ENTITY_ANIM_IMAGE_PATH + "chars/mage0.png", 32, 32);
            ResourceManager.loadSpriteSheet(ENTITY_ANIM_IMAGE_PATH + "chars/mage1.png", 32, 32);
            ResourceManager.loadSpriteSheet(ENTITY_ANIM_IMAGE_PATH + "chars/mage2.png", 32, 32);
            ResourceManager.loadSpriteSheet(ENTITY_ANIM_IMAGE_PATH + "chars/mage3.png", 32, 32);
            ResourceManager.loadSpriteSheet(ENTITY_ANIM_IMAGE_PATH + "chars/mage4.png", 32, 32);

            ResourceManager.loadSpriteSheet(ENTITY_ANIM_IMAGE_PATH + "chars/warrior0.png", 32, 32);
            ResourceManager.loadSpriteSheet(ENTITY_ANIM_IMAGE_PATH + "chars/warrior1.png", 32, 32);
            ResourceManager.loadSpriteSheet(ENTITY_ANIM_IMAGE_PATH + "chars/warrior2.png", 32, 32);
            ResourceManager.loadSpriteSheet(ENTITY_ANIM_IMAGE_PATH + "chars/warrior3.png", 32, 32);
            ResourceManager.loadSpriteSheet(ENTITY_ANIM_IMAGE_PATH + "chars/bowman0.png", 32, 32);
            ResourceManager.loadSpriteSheet(ENTITY_ANIM_IMAGE_PATH + "chars/bowman1.png", 32, 32);
            ResourceManager.loadSpriteSheet(ENTITY_ANIM_IMAGE_PATH + "chars/rogue0.png", 32, 32);
            ResourceManager.loadSpriteSheet(ENTITY_ANIM_IMAGE_PATH + "chars/rogue1.png", 32, 32);
            ResourceManager.loadSpriteSheet(ENTITY_ANIM_IMAGE_PATH + "chars/monk0.png", 32, 32);

            //Monster
            ResourceManager.loadSpriteSheet(ENTITY_ANIM_IMAGE_PATH + "mon/blob/blob0.png", 32, 32);
            ResourceManager.loadSpriteSheet(ENTITY_ANIM_IMAGE_PATH + "mon/blob/blob1.png", 32, 32);
            ResourceManager.loadSpriteSheet(ENTITY_ANIM_IMAGE_PATH + "mon/blob/blob2.png", 32, 32);
            ResourceManager.loadSpriteSheet(ENTITY_ANIM_IMAGE_PATH + "mon/blob/blob3.png", 32, 32);
            ResourceManager.loadSpriteSheet(ENTITY_ANIM_IMAGE_PATH + "mon/blob/blob4.png", 32, 32);
            ResourceManager.loadSpriteSheet(ENTITY_ANIM_IMAGE_PATH + "mon/blob/blob5.png", 32, 32);
            ResourceManager.loadSpriteSheet(ENTITY_ANIM_IMAGE_PATH + "mon/blob/blob6.png", 32, 32);

        }
    }


    public static final class Images {
        public static final Image EMPTY = ResourceManager.loadImage("res/empty.png");
        public static final Image EMPTY_8x8 = ResourceManager.loadImage("res/misc/empty_8x8.png");
    }


    public static final class UI {
        public static final Image MINIMAP_BASE = ResourceManager.loadImage(UI_GAME_ELEMENT_PATH + "minimap/MINIMAP.png");
        public static final Image MINIMAP_IMAGE = ResourceManager.loadImage(UI_GAME_ELEMENT_PATH + "minimap/MINIMAP_BLANK.png");
        public static final Image MINIMAP_BUTTON_NORMAL = ResourceManager.loadImage(UI_GAME_ELEMENT_PATH + "minimap/BUTTON.png");
        public static final Image MINIMAP_BUTTON_PRESSED = ResourceManager.loadImage(UI_GAME_ELEMENT_PATH + "minimap/BUTTON_PRESSED.png");
        public static final Image MINIMAP_BUTTON_ATTACH = ResourceManager.loadImage(UI_GAME_ELEMENT_PATH + "minimap/BUTTON_ATTACH.png");
        public static final Image MINIMAP_BUTTON_SEPERATOR = ResourceManager.loadImage(UI_GAME_ELEMENT_PATH + "minimap/BUTTON_SEPERATOR.png");

        public static final Image STATS_GAGUE_BASE = ResourceManager.loadImage(UI_GAME_ELEMENT_PATH + "gague/GAGUE.png");
        public static final Image STATS_GAGUE_BARS = ResourceManager.loadImage(UI_GAME_ELEMENT_PATH + "gague/GAGUE_BARS.png");
        public static final Image STATS_GAGUE_RED = STATS_GAGUE_BARS.getSubImage(0, 0, 80, 12);
        public static final Image STATS_GAGUE_BLUE = STATS_GAGUE_BARS.getSubImage(0, 12, 80, 12);
        public static final Image STATS_GAGUE_GREEN = STATS_GAGUE_BARS.getSubImage(0, 24, 80, 12);


        public static final Image FRAME_TOP_RIGHT = ResourceManager.loadImage(UI_GAME_ELEMENT_PATH + "frame/TOP_RIGHT.png");
        public static final Image FRAME_TOP_MIDDLE = ResourceManager.loadImage(UI_GAME_ELEMENT_PATH + "frame/TOP_MIDDLE.png");
        public static final Image FRAME_TOP_LEFT = ResourceManager.loadImage(UI_GAME_ELEMENT_PATH + "frame/TOP_LEFT.png");
        public static final Image FRAME_MIDDLE_RIGHT = ResourceManager.loadImage(UI_GAME_ELEMENT_PATH + "frame/MIDDLE_RIGHT.png");
        public static final Image FRAME_MIDDLE = ResourceManager.loadImage(UI_GAME_ELEMENT_PATH + "frame/MIDDLE.png");
        public static final Image FRAME_MIDDLE_LEFT = ResourceManager.loadImage(UI_GAME_ELEMENT_PATH + "frame/MIDDLE_LEFT.png");
        public static final Image FRAME_BOTTOM_RIGHT = ResourceManager.loadImage(UI_GAME_ELEMENT_PATH + "frame/BOTTOM_RIGHT.png");
        public static final Image FRAME_BOTTOM_MIDDLE = ResourceManager.loadImage(UI_GAME_ELEMENT_PATH + "frame/BOTTOM_MIDDLE.png");
        public static final Image FRAME_BOTTOM_LEFT = ResourceManager.loadImage(UI_GAME_ELEMENT_PATH + "frame/BOTTOM_LEFT.png");
        public static final Image FRAME_TOP_RIGHT_DIALOG = ResourceManager.loadImage(UI_GAME_ELEMENT_PATH + "frame/TOP_RIGHT_DIALOG.png");
        public static final Image FRAME_TOP_MIDDLE_DIALOG = ResourceManager.loadImage(UI_GAME_ELEMENT_PATH + "frame/TOP_MIDDLE_DIALOG.png");
        public static final Image FRAME_TOP_LEFT_DIALOG = ResourceManager.loadImage(UI_GAME_ELEMENT_PATH + "frame/TOP_LEFT_DIALOG.png");

        public static final Image SCROLL_LEFT = ResourceManager.loadImage(UI_GAME_ELEMENT_PATH + "banner/SCROLL_LEFT.png");
        public static final Image SCROLL_CENTER = ResourceManager.loadImage(UI_GAME_ELEMENT_PATH + "banner/SCROLL_CENTER.png");
        public static final Image SCROLL_RIGHT = ResourceManager.loadImage(UI_GAME_ELEMENT_PATH + "banner/SCROLL_RIGHT.png");

        public static final Image BUTTON_ROUND_GREEN_NORMAL = ResourceManager.loadImage(UI_GAME_ELEMENT_PATH + "buttons/GREEN_ROUND_NORMAL.png");
        public static final Image BUTTON_ROUND_GREEN_HOVERED = ResourceManager.loadImage(UI_GAME_ELEMENT_PATH + "buttons/GREEN_ROUND_HOVERED.png");
        public static final Image BUTTON_ROUND_GREEN_PRESSED = ResourceManager.loadImage(UI_GAME_ELEMENT_PATH + "buttons/GREEN_ROUND_PRESSED.png");
        public static final Image BUTTON_ROUND_RED_NORMAL = ResourceManager.loadImage(UI_GAME_ELEMENT_PATH + "buttons/RED_ROUND_NORMAL.png");
        public static final Image BUTTON_ROUND_RED_HOVERED = ResourceManager.loadImage(UI_GAME_ELEMENT_PATH + "buttons/RED_ROUND_HOVERED.png");
        public static final Image BUTTON_ROUND_RED_PRESSED = ResourceManager.loadImage(UI_GAME_ELEMENT_PATH + "buttons/RED_ROUND_PRESSED.png");
        public static final Image BUTTON_ROUND_BLUE_NORMAL = ResourceManager.loadImage(UI_GAME_ELEMENT_PATH + "buttons/BLUE_ROUND_NORMAL.png");
        public static final Image BUTTON_ROUND_BLUE_HOVERED = ResourceManager.loadImage(UI_GAME_ELEMENT_PATH + "buttons/BLUE_ROUND_HOVERED.png");
        public static final Image BUTTON_ROUND_BLUE_PRESSED = ResourceManager.loadImage(UI_GAME_ELEMENT_PATH + "buttons/BLUE_ROUND_PRESSED.png");
        public static final Image BUTTON_ROUND_GREY_NORMAL = ResourceManager.loadImage(UI_GAME_ELEMENT_PATH + "buttons/GREY_ROUND_NORMAL.png");
        public static final Image BUTTON_ROUND_GREY_HOVERED = ResourceManager.loadImage(UI_GAME_ELEMENT_PATH + "buttons/GREY_ROUND_HOVERED.png");
        public static final Image BUTTON_ROUND_GREY_PRESSED = ResourceManager.loadImage(UI_GAME_ELEMENT_PATH + "buttons/GREY_ROUND_PRESSED.png");

        public static final SpriteSheet SPEECH_BUBBLE = ResourceManager.loadSpriteSheet(UI_GAME_ELEMENT_PATH + "SPEECH_BUBBLE.png", 16, 32);

        public static final SpriteSheet BUTTON_LONG_WOODEN = ResourceManager.loadSpriteSheet(UI_GAME_ELEMENT_PATH + "buttons/LONG_ROUNDED_WOODEN.png", 96, 24);
        public static final SpriteSheet BUTTON_LONG_BLUE = ResourceManager.loadSpriteSheet(UI_GAME_ELEMENT_PATH + "buttons/LONG_ROUNDED_BLUE.png", 96, 24);
        public static final SpriteSheet BUTTON_ROUND_SLOTTED = ResourceManager.loadSpriteSheet(UI_GAME_ELEMENT_PATH + "buttons/ROUND_SLOTTED.png", 24, 24);

        public static final Image BAR_LEFT = ResourceManager.loadImage(UI_GAME_ELEMENT_PATH + "bar/BAR_LEFT.png");
        public static final Image BAR_CENTER = ResourceManager.loadImage(UI_GAME_ELEMENT_PATH + "bar/BAR_CENTER.png");
        public static final Image BAR_RIGHT = ResourceManager.loadImage(UI_GAME_ELEMENT_PATH + "bar/BAR_RIGHT.png");
        public static final Image BAR_SPACER_LEFT = ResourceManager.loadImage(UI_GAME_ELEMENT_PATH + "bar/BAR_SPACER_LEFT.png");
        public static final Image BAR_SPACER_RIGHT = ResourceManager.loadImage(UI_GAME_ELEMENT_PATH + "bar/BAR_SPACER_RIGHT.png");
        public static final Image BAR_BUTTON_NORMAL = ResourceManager.loadImage(UI_GAME_ELEMENT_PATH + "bar/BAR_BUTTON_NORMAL.png");
        public static final Image BAR_BUTTON_HOVERED = ResourceManager.loadImage(UI_GAME_ELEMENT_PATH + "bar/BAR_BUTTON_HOVERED.png");
        public static final Image BAR_BUTTON_PUSHED = ResourceManager.loadImage(UI_GAME_ELEMENT_PATH + "bar/BAR_BUTTON_PUSHED.png");
        public static final Image BAR_BUTTON_DISABLED = ResourceManager.loadImage(UI_GAME_ELEMENT_PATH + "bar/BAR_BUTTON_DISABLED.png");

        public static final Image INVENTROY_TOP_RIGHT = ResourceManager.loadImage(UI_GAME_ELEMENT_PATH + "inventory/INV_TOP_RIGHT.png");
        public static final Image INVENTROY_TOP_MIDDLE = ResourceManager.loadImage(UI_GAME_ELEMENT_PATH + "inventory/INV_TOP_MIDDLE.png");
        public static final Image INVENTROY_TOP_LEFT = ResourceManager.loadImage(UI_GAME_ELEMENT_PATH + "inventory/INV_TOP_LEFT.png");
        public static final Image INVENTROY_MIDDLE_RIGHT = ResourceManager.loadImage(UI_GAME_ELEMENT_PATH + "inventory/INV_MIDDLE_RIGHT.png");
        public static final Image INVENTROY_MIDDLE = ResourceManager.loadImage(UI_GAME_ELEMENT_PATH + "inventory/INV_MIDDLE.png");
        public static final Image INVENTROY_MIDDLE_LEFT = ResourceManager.loadImage(UI_GAME_ELEMENT_PATH + "inventory/INV_MIDDLE_LEFT.png");
        public static final Image INVENTROY_BOTTOM_RIGHT = ResourceManager.loadImage(UI_GAME_ELEMENT_PATH + "inventory/INV_BOTTOM_RIGHT.png");
        public static final Image INVENTROY_BOTTOM_MIDDLE = ResourceManager.loadImage(UI_GAME_ELEMENT_PATH + "inventory/INV_BOTTOM_MIDDLE.png");
        public static final Image INVENTROY_BOTTOM_LEFT = ResourceManager.loadImage(UI_GAME_ELEMENT_PATH + "inventory/INV_BOTTOM_LEFT.png");
        public static final Image INVENTORY_SLOT = ResourceManager.loadImage(UI_GAME_ELEMENT_PATH + "inventory/SLOT.png");
    }


    public static final class Icons {
        public static final Image PLUS = ResourceManager.loadImage(UI_GAME_ELEMENT_PATH + "buttons/icons/PLUS.png");
        public static final Image MINUS = ResourceManager.loadImage(UI_GAME_ELEMENT_PATH + "buttons/icons/MINUS.png");
        public static final Image MAGNIFYING_GLASS = ResourceManager.loadImage(UI_GAME_ELEMENT_PATH + "buttons/icons/MAGNIFYING_GLASS.png");
        public static final Image EXIT = ResourceManager.loadImage(UI_GAME_ELEMENT_PATH + "buttons/icons/EXIT.png");
        public static final Image ARROW_LEFT = ResourceManager.loadImage(UI_GAME_ELEMENT_PATH + "buttons/icons/LEFT.png");
        public static final Image ARROW_RIGHT = ResourceManager.loadImage(UI_GAME_ELEMENT_PATH + "buttons/icons/RIGHT.png");
        public static final Image ARROW_UP = ResourceManager.loadImage(UI_GAME_ELEMENT_PATH + "buttons/icons/UP.png");
        public static final Image ARROW_DOWN = ResourceManager.loadImage(UI_GAME_ELEMENT_PATH + "buttons/icons/DOWN.png");

        public static final Image BOOK_BIG = ResourceManager.loadImage(UI_GAME_ELEMENT_PATH + "buttons/icons/BOOK.png");
        public static final Image CHARACTER_BIG = ResourceManager.loadImage(UI_GAME_ELEMENT_PATH + "buttons/icons/CHARACTER.png");
        public static final Image INVENTORY_BIG = ResourceManager.loadImage(UI_GAME_ELEMENT_PATH + "buttons/icons/INVENTORY.png");
        public static final Image SCROLL_BIG = ResourceManager.loadImage(UI_GAME_ELEMENT_PATH + "buttons/icons/SCROLL.png");
        public static final Image MENU_BIG = ResourceManager.loadImage(UI_GAME_ELEMENT_PATH + "buttons/icons/MENU.png");

        public static final Image BGM_TRACK = ResourceManager.loadImage(UI_GAME_ELEMENT_PATH + "icons/music.png");

        public static final Image ACCESSORY_ORB = ResourceManager.loadImage(UI_GAME_ELEMENT_PATH + "inventory/icons/accessory_orb.png");
        public static final Image ACCESSORY_SHIELD = ResourceManager.loadImage(UI_GAME_ELEMENT_PATH + "inventory/icons/accessory_shield.png");
        public static final Image ARMOR_HEAVY = ResourceManager.loadImage(UI_GAME_ELEMENT_PATH + "inventory/icons/armor_heavy.png");
        public static final Image ARMOR_LEATHER = ResourceManager.loadImage(UI_GAME_ELEMENT_PATH + "inventory/icons/armor_leather.png");
        public static final Image ARMOR_ROBE = ResourceManager.loadImage(UI_GAME_ELEMENT_PATH + "inventory/icons/armor_robe.png");
        public static final Image BELT = ResourceManager.loadImage(UI_GAME_ELEMENT_PATH + "inventory/icons/belt.png");
        public static final Image BOOT = ResourceManager.loadImage(UI_GAME_ELEMENT_PATH + "inventory/icons/boot.png");
        public static final Image GLOVE = ResourceManager.loadImage(UI_GAME_ELEMENT_PATH + "inventory/icons/glove.png");
        public static final Image HEMLET = ResourceManager.loadImage(UI_GAME_ELEMENT_PATH + "inventory/icons/helm.png");
        public static final Image NECKLACE = ResourceManager.loadImage(UI_GAME_ELEMENT_PATH + "inventory/icons/necklace.png");
        public static final Image RING = ResourceManager.loadImage(UI_GAME_ELEMENT_PATH + "inventory/icons/ring.png");
        public static final Image TORCH = ResourceManager.loadImage(UI_GAME_ELEMENT_PATH + "inventory/icons/torch.png");
        public static final Image WEAPON_DAGGER = ResourceManager.loadImage(UI_GAME_ELEMENT_PATH + "inventory/icons/weapon_dagger.png");
        public static final Image WEAPON_SWORD = ResourceManager.loadImage(UI_GAME_ELEMENT_PATH + "inventory/icons/weapon_sword.png");
        public static final Image WEAPON_STAFF = ResourceManager.loadImage(UI_GAME_ELEMENT_PATH + "inventory/icons/weapon_staff.png");

    }


    public static final class Cursors {
        public static final Image FINGER = ResourceManager.loadImage(CURSOR_IMAGE_PATH + "FINGER_NORMAL.png");
        public static final Image FINGER_PRESSED = ResourceManager.loadImage(CURSOR_IMAGE_PATH + "FINGER_PRESSED.png");
        public static final Image POINTER_RED = ResourceManager.loadImage(CURSOR_IMAGE_PATH + "POINTER_RED.png");
        public static final Image POINTER_BLACK = ResourceManager.loadImage(CURSOR_IMAGE_PATH + "POINTER_BLACK.png");
        public static final Image POINTER_WHITE = ResourceManager.loadImage(CURSOR_IMAGE_PATH + "POINTER_WHITE.png");
        public static final Image POINTER_YELLOW = ResourceManager.loadImage(CURSOR_IMAGE_PATH + "POINTER_YELLOW.png");
        public static final Image EMPTY = ResourceManager.loadImage(CURSOR_IMAGE_PATH + "empty.png");
        public static final Image POINTER_DARK_YELLOW = ResourceManager.loadImage(CURSOR_IMAGE_PATH + "POINTER_DARK_YELLOW.png");

        public static final SpriteSheet INERACT_ANIMATION = ResourceManager.loadSpriteSheet(CURSOR_IMAGE_PATH + "INTERACT_TALK.png", 32, 32);
    }

}
