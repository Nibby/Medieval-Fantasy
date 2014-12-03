package hidden.indev0r.core;

import hidden.indev0r.core.reference.References;
import hidden.indev0r.core.states.MainGameState;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import java.nio.file.Paths;

public class MedievalLauncher extends StateBasedGame{

    public MedievalLauncher(String title) {
        super(title);
    }

	@Override
	public void initStatesList(GameContainer gc) throws SlickException {
		this.addState(new MainGameState());
	}

	public static void main(String[] args) {
        System.setProperty("org.lwjgl.librarypath", Paths.get("dep").resolve("natives").toAbsolutePath().toString());

        try {
            AppGameContainer app = new AppGameContainer(new MedievalLauncher(References.GAME_TITLE));
            app.setDisplayMode(800, 600, false);
            app.start();
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }

}
