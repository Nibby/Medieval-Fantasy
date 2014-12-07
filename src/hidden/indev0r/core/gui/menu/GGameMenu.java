package hidden.indev0r.core.gui.menu;

import hidden.indev0r.core.gui.component.GComponent;
import hidden.indev0r.core.gui.component.GComponent$Minimap;
import hidden.indev0r.core.reference.References;
import org.lwjgl.util.vector.Vector2f;

public class GGameMenu extends GMenu {

	private GComponent$Minimap minimap;

	public GGameMenu() {

		minimap = new GComponent$Minimap(new Vector2f(References.GAME_WIDTH - 138, 10), 5);
		minimap.addListener(this);


		addComponent(minimap);
	}


	@Override
	public void componentClicked(GComponent c) {
		System.out.println("Clicked " + c.getClass().toString());
	}

	@Override
	public void componentHovered(GComponent c) {
		System.out.println("Hovered " + c.getClass().toString());
	}
}
