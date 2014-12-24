package hidden.indev0r.game.gui.menu;

import hidden.indev0r.game.BitFont;
import hidden.indev0r.game.entity.Actor;
import hidden.indev0r.game.gui.component.*;
import hidden.indev0r.game.gui.component.base.GComponent;
import hidden.indev0r.game.gui.component.hud.*;
import hidden.indev0r.game.gui.component.interfaces.GMapSupplier;
import hidden.indev0r.game.gui.component.interfaces.GStatsSupplier;
import hidden.indev0r.game.reference.References;
import hidden.indev0r.game.sound.BGM;
import hidden.indev0r.game.state.MainGameState;

import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;

public class GGameOverlayMenu extends GMenu {

	private GComponent$Minimap           minimap;
	private GComponent$PlayerStatusGauge gauge;

	private MainGameState             state;
	private GComponent$AnimatedScroll scrollComponent;

    private boolean cinematicMode = false;

	public GGameOverlayMenu(MainGameState state, GStatsSupplier supplierStats, GMapSupplier supplierMap) {
		minimap = new GComponent$Minimap(new Vector2f(References.GAME_WIDTH - 138, 10), supplierMap, 5);
		gauge = new GComponent$PlayerStatusGauge(new Vector2f(10, 10), supplierStats);

		scrollComponent = new GComponent$AnimatedScroll("", 1);
		this.state = state;
		addComponent(minimap);
		addComponent(gauge);
	}

    public void render(Graphics g) {
        for(int i = 0; i < components.size(); i++) {
            GComponent c = components.get(i);
            if(cinematicMode) {
                if(c instanceof GComponent$Minimap
                        || c instanceof GComponent$PlayerStatusGauge)
                    continue;
            }

            c.render(g);
        }
    }

	public void tick(GameContainer gc) {
		super.tick(gc);

        if(!isCinematicMode()) {
            Input input = gc.getInput();
            if (input.isKeyPressed(Input.KEY_ESCAPE)) {
                state.getMenuManager().addMenu(new GGameOverlayMenu$OptionMenu());
            }
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

	public void showHintVerbose(String text, int duration, Color color, int type) {
		GComponent$HintVerbose hint = new GComponent$HintVerbose(text, color, duration, type);
		addComponent(hint);
	}

    public void showStatusVerbose(Actor actor, Color color, String text, int duration) {
        GComponent$StatusVerbose verbose = new GComponent$StatusVerbose(actor, color, text, duration);
        addComponent(verbose);
    }

    public void showBGMTrackInfo(BGM bgm) {
        GComponent$BGMTrackInfo trackInfo = new GComponent$BGMTrackInfo(bgm.getTitle(), bgm.getComposer());
        addComponent(trackInfo);
    }

    public void showActorHPGauge(Actor actor) {
        if(actor.showingCombatHPGauge) return;
        GComponent$ActorHealthGauge gauge = new GComponent$ActorHealthGauge(actor);
        addComponent(gauge);
    }

	@Override
	public void componentClicked(GComponent c) {

	}

	@Override
	public void componentHovered(GComponent c) {

	}

	public boolean isComponentEmpty() {
		for (GComponent c : components) {
			if(c.doesRequireFocus()) {

                return false;
            }
		}
		return true;
	}

    public boolean isCinematicMode() {
        return cinematicMode;
    }

    public void setCinematicMode(boolean cinematicMode) {
        this.cinematicMode = cinematicMode;
    }
}
