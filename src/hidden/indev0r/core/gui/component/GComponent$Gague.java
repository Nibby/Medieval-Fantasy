package hidden.indev0r.core.gui.component;

import hidden.indev0r.core.texture.Textures;
import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;


public class GComponent$Gague extends GComponent {

	private Image gague;
	private Image redBar;
	private Image blueBar;
	private Image greenBar;

	public GComponent$Gague(Vector2f pos) {
		super(pos);
		gague = Textures.UI.STATS_GAGUE_BASE;
		redBar = Textures.UI.STATS_GAGUE_RED;
		blueBar = Textures.UI.STATS_GAGUE_BLUE;
		greenBar = Textures.UI.STATS_GAGUE_GREEN;
	}

	@Override
	public void render(Graphics g) {
		g.pushTransform();
		g.scale(1.5f, 1.5f);
		gague.draw(position.x, position.y);
		redBar.draw(position.x + 84, position.y + 6);
		blueBar.draw(position.x + 84, position.y + 27);
		greenBar.draw(position.x + 84, position.y + 46);
		g.popTransform();
	}

	@Override
	public void tick(GameContainer gc) {

	}
}
