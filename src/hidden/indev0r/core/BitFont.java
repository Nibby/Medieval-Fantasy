package hidden.indev0r.core;

import hidden.indev0r.core.texture.Textures;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SpriteSheet;

/**
 * Created by MrDeathJockey on 14/12/4.
 */
public class BitFont {

    public static final String letters =
            "ABCDEFGHIJKLMNOPQRSTUVWXYZ" +
            "abcdefghijklmnopqrstuvwxyz" +
            "0123456789;'\"             " +
            "!@#$%^&*()-+_=~.,<>?/\\[]|:";

    private static final FontSpacing[] GLYPH_SPACING = {
            new FontSpacing("ABCDEFGHJKLMNOPQRSTUVWXYZabcdeghkmnopquvwyz023456789@#$%&_~?", 14),
            new FontSpacing("Ifrstx=\"", 12),
            new FontSpacing("j1^*-+<>/\\", 10),
            new FontSpacing("il()[],;'", 8),
            new FontSpacing("!.|:", 6),
    };

    public static void render(Graphics g, String text, int x, int y) {
        render(g, text, x, y, Color.white);
    }

    public static void render(Graphics g, String text, int x, int y, Color color) {
        render(g, text, x, y, color, 0);
    }

    public static void render(Graphics g, String text, int x, int y, Color color, int size) {
        render(g, text, x, y, color, size, 1.0f);
    }

    public static void render(Graphics g, String text, int x, int y, Color color, int size, float alpha) {
        if(size > 2) size = 2;
        if(size < 0) size = 0;
        int drawX = x, drawY = y;
        SpriteSheet fontSheet = Textures.SpriteSheets.UI_FONT;

        int fontWidth = 16 - 2, fontHeight = 20;
        for(int i = 0; i < text.length(); i++) {
            int index = letters.indexOf(text.charAt(i));
            if(text.charAt(i) == ' ') {
                drawX += fontWidth;
                continue;
            }
            if(text.charAt(i) == '`') {
                drawY += (fontHeight + 2);
                drawX = x;
                continue;
            }
            for(FontSpacing spacing : GLYPH_SPACING) {
                if(spacing.affectedGlyphs.contains("" + text.charAt(i))) {
                    fontWidth = spacing.glyphWidth;
                }
            }
            if(index < 0) continue;
            Image glyph = fontSheet.getSprite(index % 26, index / 26);
            glyph.setAlpha(alpha);
            if(color == Color.white)
                g.drawImage(glyph, drawX + size + 1, drawY + size + 1, Color.black);
            g.drawImage(glyph, drawX, drawY, color);
            drawX += fontWidth;
        }
    }

    public static int widthOf(String s, int size) {
        int width = 0;
        outer:
        for(int i = 0; i < s.length(); i++) {
            for(FontSpacing spacing : GLYPH_SPACING) {
                if(spacing.affectedGlyphs.contains("" + s.charAt(i))) {
                    width += spacing.glyphWidth;
                    continue outer;
                }
            }
            width += 16;
        }
        return width;
    }

    private static class FontSpacing {

        private String affectedGlyphs;
        private int glyphWidth;

        public FontSpacing(String affectedGlyphs, int newWidth) {
            this.affectedGlyphs = affectedGlyphs;
            this.glyphWidth = newWidth;
        }

    }

}
