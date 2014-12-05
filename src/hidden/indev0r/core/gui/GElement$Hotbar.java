package hidden.indev0r.core.gui;

import hidden.indev0r.core.reference.References;
import hidden.indev0r.core.texture.Textures;

public class GElement$Hotbar extends GElement {

	@Override
	public void render() {
		Textures.UI.HOTBAR.draw(
				(References.GAME_WIDTH / 2) - (Textures.UI.HOTBAR.getWidth() / 2),
				(References.GAME_HEIGHT - Textures.UI.HOTBAR.getHeight()) - 10
		);
	}

	@Override
	public void tick() {

	}

}
