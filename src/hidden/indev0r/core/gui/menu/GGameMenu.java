package hidden.indev0r.core.gui.menu;

import hidden.indev0r.core.gui.component.base.GComponent;
import hidden.indev0r.core.gui.component.GComponent$Gague;
import hidden.indev0r.core.gui.component.GComponent$Minimap;
import hidden.indev0r.core.gui.component.base.GComponent$Dialog;
import hidden.indev0r.core.reference.References;
import org.lwjgl.util.vector.Vector2f;

public class GGameMenu extends GMenu {

	private GComponent$Dialog someDialog;

	private GComponent$Minimap minimap;
	private GComponent$Gague   gague;

	public GGameMenu() {

		minimap = new GComponent$Minimap(new Vector2f(References.GAME_WIDTH - 138, 10), 5);
		minimap.addListener(this);

		gague = new GComponent$Gague(new Vector2f(10, 10));

		someDialog = new GComponent$Dialog(new Vector2f(200, 200), 3, 3);
		someDialog.addListener(this);

		addComponent(minimap);
		addComponent(gague);
	}


	private void checkMinimapButtons(GComponent c) {
		if (c == minimap.zoomInButton) System.out.println("ZOOM IN");
		if (c == minimap.zoomOutButton) System.out.println("ZOOM OUT");
		if (c == minimap.bigMapButton) System.out.println("PRESENT BIG MAP");

		//First Buttom
		if (c == minimap.sideButtons.get(0)) addComponent(someDialog);
		if(c == someDialog.closeButton) removeComponent(someDialog);
	}

	@Override
	public void componentClicked(GComponent c) {
		System.out.println("CLICKED");
		checkMinimapButtons(c);
	}

	@Override
	public void componentHovered(GComponent c) {
		System.out.println("Hovered " + c.getClass().toString());
	}
}
