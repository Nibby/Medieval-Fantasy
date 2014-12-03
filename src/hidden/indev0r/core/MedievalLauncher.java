package hidden.indev0r.core;

import hidden.indev0r.core.reference.References;
import hidden.indev0r.core.states.GameStateID;
import hidden.indev0r.core.states.InitializationState;
import hidden.indev0r.core.states.MainGameState;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import java.nio.file.Paths;

public class MedievalLauncher extends StateBasedGame {

    private static MedievalLauncher instance;

    //Game state list
    private InitializationState stateInit;
    private MainGameState       stateGame;

    public MedievalLauncher(String title) {
        super(title);
    }

	@Override
	public void initStatesList(GameContainer gc) throws SlickException {
        this.addState((stateInit = new InitializationState()));
        this.addState((stateGame = new MainGameState()));

        enterState(GameStateID.INITIALIZATION_STATE.getID());
	}

    public InitializationState getInitializationState() {
        return stateInit;
    }

    public MainGameState getGameState() {
        return stateGame;
    }

    public static MedievalLauncher getInstance() {
        return instance;
    }

	public static void main(String[] args) {
        System.setProperty("org.lwjgl.librarypath", Paths.get("dep").resolve("natives").toAbsolutePath().toString());

        try {
            instance = new MedievalLauncher(References.GAME_TITLE);

            AppGameContainer app = new AppGameContainer(instance);
            app.setDisplayMode(References.GAME_WIDTH, References.GAME_HEIGHT, false);
            app.setAlwaysRender(false);
            app.setUpdateOnlyWhenVisible(true);
            app.setShowFPS(false);
            app.setTargetFrameRate(60);
            app.start();
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }

}
