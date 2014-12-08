package hidden.indev0r.core;

import hidden.indev0r.core.entity.NPCDatabase;
import hidden.indev0r.core.entity.animation.ActionSetDatabase;
import hidden.indev0r.core.gui.Cursor;
import hidden.indev0r.core.map.TileMapDatabase;
import hidden.indev0r.core.map.TilesetDatabase;
import hidden.indev0r.core.reference.References;
import hidden.indev0r.core.state.GameStateID;
import hidden.indev0r.core.state.MainGameState;
import hidden.indev0r.core.state.MainMenuState;
import hidden.indev0r.core.texture.Textures;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import java.nio.file.Paths;

public class MedievalLauncher extends StateBasedGame {

	private static MedievalLauncher instance;

	//Game state list
	private MainGameState stateGame;
	private MainMenuState stateMainMenu;

    //Game container
    private GameContainer gameContainer;

    private Cursor currentCursor;

	public MedievalLauncher(String title) {
		super(title);
	}

	@Override
	public void initStatesList(GameContainer gc) throws SlickException {
        this.gameContainer = gc;

		try {
			//Initializing game data
			Textures.Init();

            TilesetDatabase.getDatabase().loadTilesets();
			TileMapDatabase.getDatabase().loadMaps();
			ActionSetDatabase.getDatabase().loadActionSets();
            NPCDatabase.getDatabase().loadNPCs();

		} catch (Exception e) {
            e.printStackTrace();
        }

        currentCursor = Cursor.NORMAL;
        setCursor(currentCursor);

		this.addState((stateGame = new MainGameState()));
		this.addState((stateMainMenu = new MainMenuState()));

		enterState(GameStateID.MAIN_GAME_STATE.getID());
	}

    public void setCursor(Cursor cursor) {
        try {
            if(currentCursor.equals(cursor)) return;

            gameContainer.setMouseCursor(cursor.getImage(), cursor.getFocusX(), cursor.getFocusY());
            currentCursor = cursor;
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }

	public MainGameState getGameState() {
		return stateGame;
	}

	public MainMenuState getStateMainMenu() {
		return stateMainMenu;
	}

    public GameContainer getGameContainer() {
        return gameContainer;
    }

    public static MedievalLauncher getInstance() {
		return instance;
	}

	public static void main(String[] args) {
		System.setProperty("org.lwjgl.librarypath", Paths.get("dep").resolve("natives").toAbsolutePath().toString());
		System.out.printf("Game \"%s\" %s, Written by: %s\n", References.GAME_TITLE, References.GAME_VERSION, References.GAME_AUTHORS[0] + " and " + References.GAME_AUTHORS[1]);

		try {
			instance = new MedievalLauncher(References.GAME_TITLE);

			AppGameContainer app = new AppGameContainer(instance);
			app.setDisplayMode(References.GAME_WIDTH, References.GAME_HEIGHT, false);
			app.setAlwaysRender(false);
			app.setUpdateOnlyWhenVisible(true);
			app.setShowFPS(false);
			app.setTargetFrameRate(60);
            app.setVerbose(false);
			app.start();
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
}
