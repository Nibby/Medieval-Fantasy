package hidden.indev0r.core.states;


import hidden.indev0r.core.Camera;
import hidden.indev0r.core.entity.Player;
import hidden.indev0r.core.maps.TileMap;
import hidden.indev0r.core.maps.TileMapDatabase;
import hidden.indev0r.core.reference.References;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.GameState;
import org.newdawn.slick.state.StateBasedGame;

public class MainGameState extends BasicGameState {

    //Game objects
    private Camera camera;

    //Currently focused map
    private TileMap map;

    //Globally constant player
	private Player player;

	@Override
	public int getID() {
		return GameStateID.MAIN_GAME_STATE.getID();
	}

	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
        camera = new Camera(0, 0);
        player = new Player(new Vector2f(50, 50));
        camera.setTrackObject(player);

        map = TileMapDatabase.getTileMap("_test");
    }

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		g.pushTransform();
		g.scale(References.DRAW_SCALE, References.DRAW_SCALE);

        map.render(g, camera);
		player.draw(g, camera);

        g.popTransform();
	}

	@Override
	public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int delta) throws SlickException {
        camera.tick();
        player.tick(delta);
        map.tick(gameContainer);
	}


    public Camera getCamera() {
        return camera;
    }
}

