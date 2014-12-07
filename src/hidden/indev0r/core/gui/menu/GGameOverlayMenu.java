package hidden.indev0r.core.gui.menu;

import hidden.indev0r.core.MedievalLauncher;
import hidden.indev0r.core.gui.component.hud.GComponent$PlayerStatusGague;
import hidden.indev0r.core.gui.component.base.GComponent;
import hidden.indev0r.core.gui.component.hud.GComponent$Minimap;
import hidden.indev0r.core.gui.component.listener.GDialogListener;
import hidden.indev0r.core.reference.References;
import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;

public class GGameOverlayMenu extends GMenu {

	private GComponent$Minimap minimap;
	private GComponent$PlayerStatusGague gague;

	public GGameOverlayMenu() {
		minimap = new GComponent$Minimap(new Vector2f(References.GAME_WIDTH - 138, 10), 5);

		gague = new GComponent$PlayerStatusGague(new Vector2f(10, 10));

		addComponent(minimap);
		addComponent(gague);
	}

    public void tick(GameContainer gc) {
        super.tick(gc);

        Input input = gc.getInput();
        if(input.isKeyPressed(Input.KEY_ESCAPE)) {
            MedievalLauncher.getInstance().getGameState().getMenuManager().addMenu(new GGameOverlayMenu$OptionMenu());
        }
    }

	@Override
	public void componentClicked(GComponent c) { }

	@Override
	public void componentHovered(GComponent c) {
	}

}
