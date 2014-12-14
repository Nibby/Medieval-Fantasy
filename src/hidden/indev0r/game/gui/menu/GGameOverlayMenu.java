package hidden.indev0r.game.gui.menu;

import hidden.indev0r.game.BitFont;
import hidden.indev0r.game.Camera;
import hidden.indev0r.game.MedievalLauncher;
import hidden.indev0r.game.entity.Actor;
import hidden.indev0r.game.entity.Player;
import hidden.indev0r.game.gui.component.base.GComponent$Frame;
import hidden.indev0r.game.gui.component.hud.*;
import hidden.indev0r.game.gui.component.base.GComponent;
import hidden.indev0r.game.gui.component.interfaces.GMapSupplier;
import hidden.indev0r.game.gui.component.interfaces.GStatsSupplier;
import hidden.indev0r.game.reference.References;
import hidden.indev0r.game.state.MainGameState;
import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.Color;
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
        int removed = 0;
        //for (GComponent gc : components) gc.tick(gamec);
        for (int i = 0; i < components.size() - removed; i++) {
            GComponent c = components.get(i);
            c.tick(gc);

            if (c instanceof GComponent$Frame) {
                GComponent$Frame dialog = (GComponent$Frame) c;
                if (dialog.isDisposed()) {
                    removeComponent(c);
                    removed++;
                }
            }

            if(c instanceof GComponent$SpeechBubble) {
                if(((GComponent$SpeechBubble) c).isExpired()) {
                    removeComponent(c);
                }
            }

            if(c instanceof GComponent$Hint) {
                if(((GComponent$Hint) c).isFinished()) {
                    removeComponent(c);
                }
            }
        }

        if(!scrollComponent.isActive()) {
            removeComponent(scrollComponent);
        }

		Input input = gc.getInput();
		if (input.isKeyPressed(Input.KEY_ESCAPE)) {
			state.getMenuManager().addMenu(new GGameOverlayMenu$OptionMenu());
		}
	}

    public void showAnimatedScroll(String text, int duration) {
        removeComponent(scrollComponent);
        scrollComponent = new GComponent$AnimatedScroll(text, duration);
        addComponent(scrollComponent);
    }

    public void showSpeechBubble(Actor actor, String text, int duration, Color textCol, boolean jitter) {
        GComponent$SpeechBubble bubble = new GComponent$SpeechBubble(
                text, new Vector2f(actor.getPosition().x + actor.getWidth() / 2 - BitFont.widthOf(text, 16) / 2,
                                   actor.getPosition().y - 48), duration, textCol, jitter);
        bubble.onAdd();
        addComponent(bubble);
    }

    public void showHint(String text, int duration, Color color) {
        GComponent$Hint hint = new GComponent$Hint(text, color, duration);
        addComponent(hint);

    }

	@Override
	public void componentClicked(GComponent c) {
	}

	@Override
	public void componentHovered(GComponent c) {
	}

    public boolean isComponentEmpty() {
        for(GComponent c : components) {
            if(!(c == minimap || c == gauge || c == scrollComponent || c instanceof GComponent$SpeechBubble
            || c instanceof GComponent$Hint))
                return false;
        }
        return true;
    }
}
