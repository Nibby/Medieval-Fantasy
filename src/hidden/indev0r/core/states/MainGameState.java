package hidden.indev0r.core.states;


import hidden.indev0r.core.BitFont;
import hidden.indev0r.core.Camera;
import hidden.indev0r.core.entity.Player;
import hidden.indev0r.core.maps.TileMap;
import hidden.indev0r.core.maps.TileMapDatabase;
import hidden.indev0r.core.reference.References;
import hidden.indev0r.core.texture.Textures;
import org.newdawn.slick.*;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

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
        canvas = Textures.Images.EMPTY;

        camera = new Camera(0, 0);
        player = new Player(4, 3);
        camera.setTrackObject(player);

        map = TileMapDatabase.getTileMap("map00_test");
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


        BitFont.render(g, map.getIdentifierName() + " [" + map.getName() + "]" , 5, 5);
        BitFont.render(g, player.getX() + ", " + player.getY() + " / " + player.getCurrentX() + ", " + player.getCurrentY(), 5, 25);
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

