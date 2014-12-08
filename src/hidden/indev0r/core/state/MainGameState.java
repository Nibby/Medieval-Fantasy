package hidden.indev0r.core.state;


import hidden.indev0r.core.Camera;
import hidden.indev0r.core.entity.Player;
import hidden.indev0r.core.gui.menu.GGameOverlayMenu;
import hidden.indev0r.core.gui.menu.GMenuManager;
import hidden.indev0r.core.map.TileMap;
import hidden.indev0r.core.map.TileMapDatabase;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class MainGameState extends BasicGameState {

	//Game objects
	private Camera camera;

	//Currently focused map
	private TileMap map;

	//Globally constant player
	private Player player;

	//2D Elements
	private GMenuManager menuMgr;
    private GGameOverlayMenu menuOverlay;

	@Override
	public int getID() {
		return GameStateID.MAIN_GAME_STATE.getID();
	}

	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {

		camera = new Camera(0, 0);
		player = new Player(4, 3);
		camera.setTrackObject(player);

		map = TileMapDatabase.getTileMap("map00_test");
		map.addEntity(player);

		menuMgr = new GMenuManager();
        menuMgr.setDisplayTopMenuOnly(false);
        menuMgr.setTickTopMenuOnly(false);
        menuMgr.addMenu((menuOverlay = new GGameOverlayMenu(map)));
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		map.render(g, camera);
		menuMgr.render(g);

//		BitFont.render(g, map.getIdentifierName() + " [" + map.getName() + "]", 5, 5);
//		BitFont.render(g, player.getX() + ", " + player.getY() + " / " + player.getCurrentX() + ", " + player.getCurrentY(), 5, 25);
	}


	@Override
	public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int delta) throws SlickException {
        menuMgr.tick(gameContainer);

        //This means menu wants to pull the focus, no need to do these... (except for debug menu)
        if(!menuMgr.isTickingTopMenuOnly() || !menuMgr.hasMenus()) {
            camera.tick();
            map.tick(gameContainer);
        }

	}


	public Camera getCamera() {
		return camera;
	}

    public GGameOverlayMenu getMenuOverlay() {
        return menuOverlay;
    }

    public GMenuManager getMenuManager() {
        return menuMgr;
    }
}

