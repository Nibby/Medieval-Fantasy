package hidden.indev0r.core.states;

import hidden.indev0r.core.MedievalLauncher;
import hidden.indev0r.core.maps.TileMapDatabase;
import hidden.indev0r.core.maps.TilesetDatabase;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

/**
 * Created by MrDeathJockey on 14/12/3.
 *
 * The purpose of this gme state is to load all game assets and data,
 * prior to the transition to splash screen.
 */
public class InitializationState extends BasicGameState {
    @Override
    public int getID() {

        return GameStateID.INITIALIZATION_STATE.getID();
    }

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        try {
            //Load and register tilesets
            TilesetDatabase.getDatabase().loadTilesets();

            //Load and register maps
            TileMapDatabase.getDatabase().loadMaps();
        } catch (Exception e) {
            e.printStackTrace();
        }

        //When all is done, switch state
        MedievalLauncher.getInstance().enterState(GameStateID.MAIN_GAME_STATE.getID());
    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {

    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) throws SlickException {

    }
}
