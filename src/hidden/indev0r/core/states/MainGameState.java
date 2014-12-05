package hidden.indev0r.core.states;


import hidden.indev0r.core.Camera;
import hidden.indev0r.core.entity.Player;
import hidden.indev0r.core.gui.GHUD;
import hidden.indev0r.core.maps.TileMap;
import hidden.indev0r.core.maps.TileMapDatabase;
import org.newdawn.slick.*;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class MainGameState extends BasicGameState {

	//Game objects
	private Camera camera;

	//Currently focused map
	private TileMap map;

	//Globally constant entity
	private Player player;

	//GUI Elements
	private GHUD hud;

	@Override
	public int getID() {
		return GameStateID.MAIN_GAME_STATE.getID();
	}

	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {

		camera = new Camera(0, 0);
		player = new Player(11, 17);
		camera.setTrackObject(player);

		map = TileMapDatabase.getTileMap("map00_test");
		map.addEntity(player);

		hud = new GHUD();


	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		map.render(g, camera);
		hud.render();


		//BitFont.render(g, map.getIdentifierName() + " [" + map.getName() + "]", 5, 5);
		//BitFont.render(g, player.getX() + ", " + player.getY() + " / " + player.getCurrentX() + ", " + player.getCurrentY(), 5, 25);
	}

	@Override
	public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int delta) throws SlickException {
		camera.tick();
		hud.tick();
		map.tick(gameContainer);
	}


	public Camera getCamera() {
		return camera;
	}
}

