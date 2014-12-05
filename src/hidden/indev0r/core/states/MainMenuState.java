package hidden.indev0r.core.states;

import hidden.indev0r.core.gui.component.GComponent$Label;
import hidden.indev0r.core.gui.menu.GMenuManager;
import hidden.indev0r.core.gui.menu.GTestMenu;
import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class MainMenuState extends BasicGameState {

	/*
		GMenu - JPanel
		GComponent - JComponent
	 */

	private GMenuManager menuMgr;

	@Override
	public int getID() {
		return GameStateID.MAIN_MENU_STATE.getID();
	}

	@Override
	public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
		menuMgr = new GMenuManager();
		GTestMenu menu = new GTestMenu();
		menuMgr.addMenu(menu);


	}

	@Override
	public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
		menuMgr.render(graphics);
	}

	@Override
	public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) throws SlickException {
		menuMgr.tick(gameContainer);
	}
}
