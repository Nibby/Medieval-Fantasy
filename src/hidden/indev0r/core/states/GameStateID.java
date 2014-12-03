package hidden.indev0r.core.states;

/**
 * Created by James on 12/2/2014.
 */
public enum GameStateID {
	SPLASH_SCREEN_STATE(0),
	MAIN_MENU_STATE(1),
	MAIN_GAME_STATE(2);


	private int id;

	GameStateID(int id) {
		this.id = id;
	}

	public int getID(){
		return id;
	}


}
