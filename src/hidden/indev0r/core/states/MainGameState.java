package hidden.indev0r.core.states;


import hidden.indev0r.core.Camera;
import hidden.indev0r.core.entity.Player;
import hidden.indev0r.core.entity.animation.ActionID;
import hidden.indev0r.core.maps.TileMap;
import hidden.indev0r.core.maps.TileMapDatabase;
import hidden.indev0r.core.reference.References;
import hidden.indev0r.core.texture.Textures;
import org.newdawn.slick.*;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import java.sql.Ref;

public class MainGameState extends BasicGameState {

    //Game objects
    private Camera camera;
    private Image canvas;

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
        canvas = Textures.SpriteSheets.empty;

        camera = new Camera(0, 0);
        player = new Player(8, 8);
        camera.setTrackObject(player);

        map = TileMapDatabase.getTileMap("_test");
        map.addEntity(player);
    }

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
        Graphics g2 = canvas.getGraphics();
        g2.setColor(Color.black);
        g2.fillRect(0, 0, References.GAME_WIDTH, References.GAME_HEIGHT);

        map.render(g2, camera);


        g.pushTransform();
        g.scale(References.DRAW_SCALE, References.DRAW_SCALE);
        g.drawImage(canvas, 0, 0);
        g.popTransform();
	}

	@Override
	public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int delta) throws SlickException {
        camera.tick();
        map.tick(gameContainer);
	}


    public Camera getCamera() {
        return camera;
    }
}

