package hidden.indev0r.game.state;


import hidden.indev0r.game.Camera;
import hidden.indev0r.game.entity.Actor;
import hidden.indev0r.game.entity.Entity;
import hidden.indev0r.game.entity.Player;
import hidden.indev0r.game.gui.component.interfaces.GMapSupplier;
import hidden.indev0r.game.gui.menu.GGameOverlayMenu;
import hidden.indev0r.game.gui.menu.GMenuManager;
import hidden.indev0r.game.map.Tile;
import hidden.indev0r.game.map.TileMap;
import hidden.indev0r.game.map.TileMapDatabase;
import hidden.indev0r.game.map.WarpType;
import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import javax.swing.*;
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
		player = new Player(Actor.Faction.GLYSIA, Player.Job.MAGE, 7, 6);
        player.setLevel(1);
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

//		BitFont.render(g, map.getName(), 345, 25);
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

    public void warpPlayer(TileMap targetMap, int x, int y, WarpType type) {
        if(targetMap == null) {
            JOptionPane.showMessageDialog(null, "Specified warp map is null!", "Internal Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        this.map.removeEntity(player);
        this.map = targetMap;
        player.setPosition(x, y);
        targetMap.addEntity(player);

        //TEMPORARY
        getMenuOverlay().showAnimatedScroll(map.getName(), map.getName().split(" ").length * 750);
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
    public Actor getCenterEntity() {
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

    public Player getPlayer() {
        return player;
    }
}

