package hidden.indev0r.core.gui.menu;

import hidden.indev0r.core.MedievalLauncher;
import hidden.indev0r.core.gui.component.hud.GComponent$AnimatedScroll;
import hidden.indev0r.core.gui.component.hud.GComponent$PlayerStatusGauge;
import hidden.indev0r.core.gui.component.base.GComponent;
import hidden.indev0r.core.gui.component.hud.GComponent$Minimap;
import hidden.indev0r.core.gui.component.interfaces.GMapSupplier;
import hidden.indev0r.core.gui.component.interfaces.GStatsSupplier;
import hidden.indev0r.core.reference.References;
import hidden.indev0r.core.state.MainGameState;
import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;

public class GGameOverlayMenu extends GMenu {

	private GComponent$Minimap           minimap;
	private GComponent$PlayerStatusGauge gague;

    private MainGameState state;
    private GComponent$AnimatedScroll scrollComponent;

	public GGameOverlayMenu(MainGameState state, GStatsSupplier supplierStats, GMapSupplier supplierMap) {
		minimap = new GComponent$Minimap(new Vector2f(References.GAME_WIDTH - 138, 10), supplierMap, 5);
		gague = new GComponent$PlayerStatusGauge(new Vector2f(10, 10), supplierStats);

        scrollComponent = new GComponent$AnimatedScroll("", 1);

        this.state = state;
		addComponent(minimap);
		addComponent(gague);
	}

	public void tick(GameContainer gc) {
		super.tick(gc);

        if(!scrollComponent.isActive()) {
            removeComponent(scrollComponent);
        }

		Input input = gc.getInput();
		if (input.isKeyPressed(Input.KEY_ESCAPE)) {
			MedievalLauncher.getInstance().getGameState().getMenuManager().addMenu(new GGameOverlayMenu$OptionMenu());
		}
	}

    public void showAnimatedScroll(String text, int duration) {
        scrollComponent = new GComponent$AnimatedScroll(text, duration);
        addComponent(scrollComponent);
    }

	@Override
	public void componentClicked(GComponent c) {
	}

	@Override
	public void componentHovered(GComponent c) {
	}

}
