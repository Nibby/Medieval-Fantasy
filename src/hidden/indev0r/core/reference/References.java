package hidden.indev0r.core.reference;

import java.nio.file.Path;
import java.nio.file.Paths;

public final class References {

	public static final String   GAME_TITLE   = "Medieval Fantasy";
	public static final String   GAME_VERSION = "0.0.1";
	public static final String[] GAME_AUTHORS = {"James Roberts", "Kevin Yang"};

    public static final int GAME_WIDTH = 800;
    public static final int GAME_HEIGHT = 600;
    public static final int DRAW_SCALE = 1;

    public static final Path ROOT_PATH = Paths.get(System.getProperty("user.dir"));
    public static final Path DATA_PATH = ROOT_PATH.resolve("data");

    public static final Path DATA_RESOURCE_PATH = DATA_PATH.resolve("resources");
    public static final Path MAP_PATH = DATA_PATH.resolve("maps");
    public static final Path TILESET_DEFINITION_PATH = MAP_PATH.resolve("tileset.ts");

}
