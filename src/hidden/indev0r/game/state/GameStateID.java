package hidden.indev0r.game.state;

/**
 * Created by James on 12/2/2014.
 */
public enum GameStateID {
	MAIN_MENU_STATE(1),
	MAIN_GAME_STATE(2);


	private int id;

	GameStateID(int id) {
		this.id = id;
	}

	public int getID() {
		return id;
	}


}
