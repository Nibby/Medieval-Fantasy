package hidden.indev0r.core.state;


import hidden.indev0r.core.Camera;
import hidden.indev0r.core.entity.Entity;
import hidden.indev0r.core.entity.Player;
import hidden.indev0r.core.gui.component.interfaces.GMapSupplier;
import hidden.indev0r.core.gui.menu.GGameOverlayMenu;
import hidden.indev0r.core.gui.menu.GMenuManager;
import hidden.indev0r.core.map.Tile;
import hidden.indev0r.core.map.TileMap;
import hidden.indev0r.core.map.TileMapDatabase;
import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import java.util.List;

public class MainGameState extends BasicGameState implements GMapSupplier {

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
        menuMgr.addMenu((menuOverlay = new GGameOverlayMenu(this, player, this)));
        menuMgr.setTickTopMenuOnly(false);
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

    @Override
    public List<Entity> getEntitiesOnMap() {
        if(map == null) return null;
        return map.getEntities();
    }

    @Override
    public Tile getTile(int layer, Vector2f position) {
        if(map == null) return null;
        return map.getTile(layer, position);
    }

    @Override
    public boolean blockedAt(Vector2f position) {
        if(map == null) return true;
        return map.tileBlocked((int) (position.x), (int) (position.y));
    }

    @Override
    public Entity getCenterEntity() {
        return player;
    }

    @Override
    public int[][][] getTiles() {
        return map.getTileData();
    }

    @Override
    public boolean isNullTile(Vector2f vector2f) {
        if(map == null) return true;
        return map.isNullTile((int) vector2f.x, (int) vector2f.y);
    }
}

