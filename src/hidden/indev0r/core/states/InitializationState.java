package hidden.indev0r.core.states;

import hidden.indev0r.core.MedievalLauncher;
import hidden.indev0r.core.entity.animation.ActionSetDatabase;
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

    private boolean loadedActionSets = false;

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
    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {

    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) throws SlickException {
        if(!loadedActionSets) {
            /*
                THIS MAY BE A TEMPORARY PLACE

                Since ActionSet relies on using Image objects, which are loaded during god konws when,
                 placing this function in the init() method will not work as all image related code
                 will return null (because images aren't loaded yet).

                 Game will only render and update when all images are loaded, so this is the only
                 etiquette place to put it for now. Forgive me, it's 4am in the morning and I just
                 wrote the tinyCipher engine plus the action set database ~

                 Then again, this is the initalization state and we can do things that are sort of
                 logical, as long as it makes the actual game work. Right?
             */
            try {
                ActionSetDatabase.getDatabase().loadActionSets();
                loadedActionSets = true;

                MedievalLauncher.getInstance().enterState(GameStateID.MAIN_GAME_STATE.getID());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
