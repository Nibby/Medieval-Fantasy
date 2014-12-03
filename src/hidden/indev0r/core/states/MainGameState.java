package hidden.indev0r.core.states;


import hidden.indev0r.core.character.CharacterNode;
import hidden.indev0r.core.texture.Textures;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.GameState;
import org.newdawn.slick.state.StateBasedGame;

public class MainGameState implements GameState {

	CharacterNode characterBody;
	CharacterNode characterArms;
	CharacterNode characterFeet;

	@Override
	public int getID() {
		return GameStateID.MAIN_GAME_STATE.getID();
	}

	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		characterBody = new CharacterNode(Textures.PLAYER_BODY);
		characterArms = new CharacterNode(characterBody, Textures.PLAYER_ARMS);
		characterFeet = new CharacterNode(characterBody, Textures.PLAYER_FEET);
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		characterBody.draw(g, 0,0);
	}

	@Override
	public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int delta) throws SlickException {

	}

	@Override
	public void enter(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {

	}

	@Override
	public void leave(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {

	}

	@Override
	public void controllerLeftPressed(int i) {

	}

	@Override
	public void controllerLeftReleased(int i) {

	}

	@Override
	public void controllerRightPressed(int i) {

	}

	@Override
	public void controllerRightReleased(int i) {

	}

	@Override
	public void controllerUpPressed(int i) {

	}

	@Override
	public void controllerUpReleased(int i) {

	}

	@Override
	public void controllerDownPressed(int i) {

	}

	@Override
	public void controllerDownReleased(int i) {

	}

	@Override
	public void controllerButtonPressed(int i, int i2) {

	}

	@Override
	public void controllerButtonReleased(int i, int i2) {

	}

	@Override
	public void keyPressed(int i, char c) {

	}

	@Override
	public void keyReleased(int i, char c) {

	}

	@Override
	public void mouseWheelMoved(int i) {

	}

	@Override
	public void mouseClicked(int i, int i2, int i3, int i4) {

	}

	@Override
	public void mousePressed(int i, int i2, int i3) {

	}

	@Override
	public void mouseReleased(int i, int i2, int i3) {

	}

	@Override
	public void mouseMoved(int i, int i2, int i3, int i4) {

	}

	@Override
	public void mouseDragged(int i, int i2, int i3, int i4) {

	}

	@Override
	public void setInput(Input input) {

	}

	@Override
	public boolean isAcceptingInput() {
		return false;
	}

	@Override
	public void inputEnded() {

	}

	@Override
	public void inputStarted() {

	}
}

