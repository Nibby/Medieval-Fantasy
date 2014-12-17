package hidden.indev0r.game.gui.component.hud;

import hidden.indev0r.game.BitFont;
import hidden.indev0r.game.gui.component.base.GComponent;
import hidden.indev0r.game.gui.component.interfaces.GStatsSupplier;
import hidden.indev0r.game.texture.Textures;
import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

import java.text.DecimalFormat;


public class GComponent$PlayerStatusGauge extends GComponent {

	private Image gague;
	private Image redHealthBar;
	private Image blueManaBar;
	private Image greenExpBar;

	private GStatsSupplier statsSupplier;
    private static final DecimalFormat EXP_FORMAT = new DecimalFormat("#0.00");

	public GComponent$PlayerStatusGauge(Vector2f pos, GStatsSupplier statsSupplier) {
		super(pos);
		this.statsSupplier = statsSupplier;
		gague = Textures.UI.STATS_GAGUE_BASE;
		redHealthBar = Textures.UI.STATS_GAGUE_RED;
		blueManaBar = Textures.UI.STATS_GAGUE_BLUE;
		greenExpBar = Textures.UI.STATS_GAGUE_GREEN;
	}

	@Override
	public void render(Graphics g) {
        float eHealth =  statsSupplier.getHealth();
        float eHealthMax =  statsSupplier.getHealthMax();
        float hpPercent = eHealth / eHealthMax;

        float eMana =  statsSupplier.getMana();
        float eManaMax =  statsSupplier.getManaMax();
        float mpPercent = eMana / eManaMax;

        float eEXP =  statsSupplier.getExperience();
        float eEXPMax =  statsSupplier.getExperienceMax();
        float expPercent = eEXP / eEXPMax;

        int eLevel = statsSupplier.getLevel();

		g.pushTransform();
		g.scale(1.5f, 1.5f);
		gague.draw(position.x, position.y);
        redHealthBar.getSubImage(0, 0, (int) (80 * hpPercent), 12).draw(position.x + 84, position.y + 6);
        blueManaBar.getSubImage(0, 0, (int) (80 * mpPercent), 12).draw(position.x + 84, position.y + 27);
        greenExpBar.getSubImage(0, 0, (int) (80 * expPercent), 12).draw(position.x + 84, position.y + 46);
        g.popTransform();
        statsSupplier.getPreviewImage().draw(position.x + 35, position.y + 25);

        String hpString = Math.round(eHealth) + "/" + Math.round(eHealthMax);
        BitFont.render(g, hpString, (int) position.x + 150 + redHealthBar.getWidth() / 2 - BitFont.widthOf(hpString, 16) / 2, 25, Color.white, 16);

        String mpString = Math.round(eMana) + "/" + Math.round(eManaMax);
        BitFont.render(g, mpString, (int) position.x + 150 + blueManaBar.getWidth() / 2 - BitFont.widthOf(mpString, 16) / 2, 55, Color.white, 16);

        String expString = EXP_FORMAT.format(expPercent * 100) + "%";
        BitFont.render(g, expString, (int) position.x + 150 + greenExpBar.getWidth() / 2 - BitFont.widthOf(expString, 16) / 2, 85, Color.white, 16);

        String levelString =  eLevel + "";
        BitFont.render(g, "LEVEL", (int) position.x + 35, (int) position.y + 60, Color.white, 10);
        BitFont.render(g, levelString, (int) position.x + 52 - BitFont.widthOf(levelString, 16) / 2, (int) position.y + 70, (eLevel < 35) ? Color.yellow : Color.cyan, 16);
    }

	@Override
	public void tick(GameContainer gc) {

	}
}
