package hidden.indev0r.game.gui.component.base;

import hidden.indev0r.game.gui.Cursor;
import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;

/**
 * Created by MrDeathJockey on 14/12/11.
 */
public abstract class GComponent$AbstractButton extends GComponent {

	public GComponent$AbstractButton(Vector2f pos) {
		super(pos);
	}

	public void tick(GameContainer gc) {
		Input input = gc.getInput();
		Vector2f mouse = new Vector2f(input.getMouseX(), input.getMouseY());

		if (
		mouse.x > this.position.x + interactBounds.getX() &&
		mouse.x < (this.position.x + interactBounds.getWidth()) &&
		mouse.y > this.position.y + interactBounds.getY() &&
		(mouse.y < this.position.y + interactBounds.getHeight()))
		{
			if (currentState.equals(GStates.DISABLED)) return;

			if (Cursor.INTERACT_INSTANCE != null && !(Cursor.INTERACT_INSTANCE instanceof GComponent$Frame)) {
				if (!Cursor.INTERACT_INSTANCE.equals(this)) return;
			}

			if (!input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)) {
				currentState = GStates.HOVERED;
				if (!firedHoverEvent) {
					fireHoverEvent();
					firedHoverEvent = true;

					if (Cursor.INTERACT_INSTANCE == null && !(Cursor.INTERACT_INSTANCE instanceof GComponent$Frame)) {
						Cursor.setInteractInstance(this);
					}
				}
			} else {
				currentState = GStates.PRESSED;
				wasClicked = true;
			}

			//Mouse click
			if (wasClicked && !input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)) {
				firePressEvent();
				wasClicked = false;

				if (Cursor.INTERACT_INSTANCE != null && !(Cursor.INTERACT_INSTANCE instanceof GComponent$Frame)) {
					Cursor.releaseInteractInstance(this);
				}
			}


		}//END OF MOUSE BOUNDS
		else {
			currentState = GStates.NORMAL;
			firedHoverEvent = false;
			wasClicked = false;
			Cursor.releaseInteractInstance(this);
		}
	}
}
