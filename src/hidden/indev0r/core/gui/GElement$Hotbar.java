package hidden.indev0r.core.gui;

import hidden.indev0r.core.texture.Textures;

public class GElement$Hotbar extends BaseGElement {


	public GElement$Hotbar() {
		super(5, 5);
	}

	@Override
	public void render() {
		Textures.GAME_UI.HOTBAR_LEFT.draw(location.x, location.y);
	}

}
