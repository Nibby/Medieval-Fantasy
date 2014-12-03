package hidden.indev0r.core.states;

/**
 * Created by James on 12/2/2014.
 */
public enum GameStateID {

    INITIALIZATION_STATE(0),
	SPLASH_SCREEN_STATE(1),
	MAIN_MENU_STATE(2),
	MAIN_GAME_STATE(3);


	private int id;

	GameStateID(int id) {
		this.id = id;
	}

	public int getID(){
		return id;
	}


}
