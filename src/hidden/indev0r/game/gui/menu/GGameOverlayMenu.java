package hidden.indev0r.game.gui.menu;

import hidden.indev0r.game.MedievalLauncher;
import hidden.indev0r.game.gui.component.hud.GComponent$AnimatedScroll;
import hidden.indev0r.game.gui.component.hud.GComponent$Minimap;
import hidden.indev0r.game.gui.component.hud.GComponent$PlayerStatusGauge;
import hidden.indev0r.game.gui.component.base.GComponent;
import hidden.indev0r.game.gui.component.interfaces.GMapSupplier;
import hidden.indev0r.game.gui.component.interfaces.GStatsSupplier;
import hidden.indev0r.game.reference.References;
import hidden.indev0r.game.state.MainGameState;
import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;

public class GGameOverlayMenu extends GMenu {

	private GComponent$Minimap minimap;
	private GComponent$PlayerStatusGauge gauge;

    private MainGameState state;
    private GComponent$AnimatedScroll scrollComponent;

    public GGameOverlayMenu(MainGameState state, GStatsSupplier supplierStats, GMapSupplier supplierMap) {
		minimap = new GComponent$Minimap(new Vector2f(References.GAME_WIDTH - 138, 10), supplierMap, 5);
		gauge = new GComponent$PlayerStatusGauge(new Vector2f(10, 10), supplierStats);

        scrollComponent = new GComponent$AnimatedScroll("", 1);

        this.state = state;
		addComponent(minimap);
		addComponent(gauge);
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
        removeComponent(scrollComponent);
        scrollComponent = new GComponent$AnimatedScroll(text, duration);
        addComponent(scrollComponent);
    }

	@Override
	public void componentClicked(GComponent c) {
	}

	@Override
	public void componentHovered(GComponent c) {
	}

    public boolean isComponentEmpty() {
        for(GComponent c : components) {
            if(!(c == minimap || c == gauge || c == scrollComponent))
                return false;
        }
        return true;
    }
}
