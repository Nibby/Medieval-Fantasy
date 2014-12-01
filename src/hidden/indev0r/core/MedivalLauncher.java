package hidden.indev0r.core;

import org.newdawn.slick.*;

import java.nio.file.Paths;

/**
 * Created by MrDeathJockey on 14/12/1.
 */
public class MedivalLauncher extends BasicGame {

    public MedivalLauncher(String title) {
        super(title);
    }

    public static void main(String[] args) {
        System.setProperty("org.lwjgl.librarypath", Paths.get("dep").resolve("natives").toAbsolutePath().toString());

        try {
            AppGameContainer app = new AppGameContainer(new MedivalLauncher("test"));
            app.setDisplayMode(800, 600, false);
            app.start();
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void init(GameContainer gameContainer) throws SlickException {

    }

    @Override
    public void update(GameContainer gameContainer, int i) throws SlickException {

    }

    @Override
    public void render(GameContainer gameContainer, Graphics graphics) throws SlickException {

    }
}
