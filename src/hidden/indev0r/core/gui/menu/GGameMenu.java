package hidden.indev0r.core.gui.menu;

import hidden.indev0r.core.gui.component.base.GComponent;
import hidden.indev0r.core.gui.component.GComponent$Gague;
import hidden.indev0r.core.gui.component.GComponent$Minimap;
import hidden.indev0r.core.reference.References;
import org.lwjgl.util.vector.Vector2f;

public class GGameMenu extends GMenu {

	private GComponent$Minimap minimap;
	private GComponent$Gague gague;

	public GGameMenu() {

		minimap = new GComponent$Minimap(new Vector2f(References.GAME_WIDTH - 138, 10), 5);
		minimap.addListener(this);

		gague = new GComponent$Gague(new Vector2f(10, 10));


		addComponent(minimap);
		addComponent(gague);
	}


	@Override
	public void componentClicked(GComponent c) {
		minimap.checkClicked(c);
	}

	@Override
	public void componentHovered(GComponent c) {
		System.out.println("Hovered " + c.getClass().toString());
	}
}
