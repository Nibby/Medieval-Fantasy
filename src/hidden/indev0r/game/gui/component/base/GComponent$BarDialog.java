package hidden.indev0r.game.gui.component.base;

import hidden.indev0r.game.gui.component.interfaces.GComponentListener;
import hidden.indev0r.game.texture.Textures;

import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

public class GComponent$BarDialog extends GComponent$Bar implements GComponentListener {

	private GComponent$Button closeButton;

	public GComponent$BarDialog(Vector2f pos, int length) {
		super(pos, length);
		closeButton = new GComponent$Button(
				new Vector2f(position.x + (tileWidth * length) - 32, position.y),
				Textures.UI.BAR_BUTTON_NORMAL,
				Textures.UI.BAR_BUTTON_PUSHED,
				Textures.UI.BAR_BUTTON_HOVERED
		);
		closeButton.addListener(this);
		closeButton.setInteractBounds(21, 10, 42, 47);
		setVisible(true);
	}

	@Override
	public void render(Graphics g) {
		if (!isVisible()) return;
		super.render(g);
		closeButton.render(g);

	}

	@Override
	public void tick(GameContainer gc) {
		super.tick(gc);
		closeButton.tick(gc);
	}

	@Override
	public void componentClicked(GComponent c) {
		if (c.equals(closeButton)) {
			this.setVisible(false);
		}
	}

	@Override
	public void componentHovered(GComponent c) {

	}
}
