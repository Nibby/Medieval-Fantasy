package hidden.indev0r.game;

import hidden.indev0r.game.texture.Textures;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SpriteSheet;

/**
 * Created by MrDeathJockey on 14/12/4.
 */
public class BitFont {

	public static final  int FONT_GLYPH_SIZE      = 16;
	private static final int FONT_GLYPH_ROW_WIDTH = 26;

	public static final String letters =
			"ABCDEFGHIJKLMNOPQRSTUVWXYZ" +
					"abcdefghijklmnopqrstuvwxyz" +
					"0123456789;'\"             " +
					"!@#$%^&*()-+_=~.,<>?/\\[]|:";

	private static final FontSpacing[] GLYPH_SPACING = {
			new FontSpacing("@#$%&_~?", 14),
			new FontSpacing("ABCDEFGHJKLMNOPQRSTUVWXYZfexabcdghkmnopquvwyz023456789=\"", 12),
			new FontSpacing("Ijrst^*-+<>/\\", 10),
			new FontSpacing("!.|:il1()[],;'", 9),
	};

	public static void render(Graphics g, String text, int x, int y) {
		render(g, text, x, y, Color.white);
	}

	public static void render(Graphics g, String text, int x, int y, Color color) {
		render(g, text, x, y, color, 16);
	}

	public static void render(Graphics g, String text, int x, int y, Color color, int size) {
		render(g, text, x, y, color, size, 1.0f);
	}

	public static void render(Graphics g, String text, int x, int y, Color color, int size, float alpha) {
		render(g, text, x, y, color, size, alpha, false, 0);
	}

    public static void render(Graphics g, String text, int x, int y, Color color, int size, float alpha, boolean jitter, int jitterAmount) {
        if (size < 1) size = 1;
        int drawX = x, drawY = y;
        SpriteSheet fontSheet = Textures.SpriteSheets.UI_FONT;

        float fontWidth = ((FONT_GLYPH_SIZE) * ((float) size / FONT_GLYPH_SIZE)),
                fontHeight = (20 * ((float) size / FONT_GLYPH_SIZE));

        for (int i = 0; i < text.length(); i++) {
            int index = letters.indexOf(text.charAt(i));
            if (text.charAt(i) == ' ') {
                drawX += fontWidth;
                continue;
            }
            if (text.charAt(i) == '`') {
                drawY += (fontHeight + 2);
                drawX = x;
                continue;
            }
            for (FontSpacing spacing : GLYPH_SPACING) {
                if (spacing.affectedGlyphs.contains("" + text.charAt(i))) {
                    fontWidth = (spacing.glyphWidth * ((float) size / FONT_GLYPH_SIZE));
                }
            }
            if (index < 0) continue;

            int jitterY = (i % 2 == ((jitterFlag) ? 0 : 1)) ? jitterAmount : -jitterAmount;
            if(jitter) {

                if(System.currentTimeMillis() - jitterTick > 75) {
                    jitterTick = System.currentTimeMillis();
                    jitterFlag = !jitterFlag;
                }
            }

            Image glyph = fontSheet.getSprite(index % FONT_GLYPH_ROW_WIDTH, index / FONT_GLYPH_ROW_WIDTH);
            glyph.setAlpha(alpha);
            if (color == Color.white) {
                g.drawImage((size != FONT_GLYPH_SIZE) ? glyph.getScaledCopy((int) fontWidth + 2, (int) fontHeight) : glyph, drawX + 1, drawY + ((jitter) ? jitterY : 0) + 1, Color.black);
            }
            g.drawImage((size != FONT_GLYPH_SIZE) ? glyph.getScaledCopy((int) fontWidth + 2, (int) fontHeight) : glyph, drawX, drawY + ((jitter) ? jitterY : 0), color);
            drawX += fontWidth;
        }
    }

    static long jitterTick = System.currentTimeMillis();
    static boolean jitterFlag = false;

	public static int widthOf(String s, int size) {
		int width = 0;
		outer:
		for (int i = 0; i < s.length(); i++) {
			for (FontSpacing spacing : GLYPH_SPACING) {
				if (spacing.affectedGlyphs.contains("" + s.charAt(i))) {
					width += (int) (spacing.glyphWidth * ((float) size / (float) FONT_GLYPH_SIZE));
					continue outer;
				}
			}
			width += (int) (size * ((float) size / (float) FONT_GLYPH_SIZE));
		}
		return width;

	}

	private static class FontSpacing {

		private String affectedGlyphs;
		private int    glyphWidth;

		public FontSpacing(String affectedGlyphs, int newWidth) {
			this.affectedGlyphs = affectedGlyphs;
			this.glyphWidth = newWidth;
		}

	}

}
