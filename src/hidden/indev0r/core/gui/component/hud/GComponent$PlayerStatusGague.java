package hidden.indev0r.core.gui.component.hud;

import hidden.indev0r.core.gui.component.base.GComponent;
import hidden.indev0r.core.gui.component.interfaces.GStatsSupplier;
import hidden.indev0r.core.texture.Textures;
import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;


public class GComponent$PlayerStatusGague extends GComponent {

	private Image gague;
	private Image redHealthBar;
	private Image blueManaBar;
	private Image greenExpBar;

	private GStatsSupplier statsSupplier;

	public GComponent$PlayerStatusGague(Vector2f pos, GStatsSupplier statsSupplier) {
		super(pos);
		this.statsSupplier = statsSupplier;
		gague = Textures.UI.STATS_GAGUE_BASE;
		redHealthBar = Textures.UI.STATS_GAGUE_RED;
		blueManaBar = Textures.UI.STATS_GAGUE_BLUE;
		greenExpBar = Textures.UI.STATS_GAGUE_GREEN;
	}

	@Override
	public void render(Graphics g) {
		g.pushTransform();
		g.scale(1.5f, 1.5f);
		gague.draw(position.x, position.y);
		redHealthBar.getSubImage(0, 0, (int) (80 * ((statsSupplier.getHealth() / statsSupplier.getHealthMax()))), 12).draw(position.x + 84, position.y + 6);
		blueManaBar.getSubImage(0, 0, (int) (80 * ((statsSupplier.getMana() / statsSupplier.getManaMax()))), 12).draw(position.x + 84, position.y + 27);
		greenExpBar.getSubImage(0, 0, (int) (80 * ((statsSupplier.getExperience() / statsSupplier.getExperienceMax()))), 12).draw(position.x + 84, position.y + 46);
		g.popTransform();
	}

	@Override
	public void tick(GameContainer gc) {

	}
}
