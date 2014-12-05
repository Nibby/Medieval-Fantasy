package hidden.indev0r.core.gui.component;

import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;

public class GComponent$Button extends GComponent {

	private Image   button;
	private int     width;
	private int     height;
	private GStates currentState;

	public GComponent$Button(Vector2f pos, Image button) {
		super(pos);
		this.button = button;
		width = button.getWidth();
		height = button.getHeight();
		currentState = GStates.NORMAL;
	}

	@Override
	public void render(Graphics g) {
		button.draw(position.x, position.y);
	}

	@Override
	public void tick(GameContainer gc) {
		Input input = gc.getInput();
		Vector2f mouse = new Vector2f(input.getMouseX(), input.getMouseY());

		if (mouse.x > position.x && mouse.x < (position.x + width) && mouse.y > position.y && (mouse.y < position.x + height)) {

			if (!currentState.equals(GStates.DISABLED)) {
				if (!input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)) {
					currentState = GStates.HOVERED;
					if (!firedHoverEvent) {
						fireHoverEvent();
						firedHoverEvent = true;
					}
				} else {
					currentState = GStates.CLICKED;
					wasClicked = true;
				}

				//Mouse click
				if (wasClicked && !input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)) {
					firePressEvent();
					wasClicked = false;
				}
			}

		}//END OF MOUSE BOUNDS
		else
		{
			currentState = GStates.NORMAL;
			firedHoverEvent = false;
		}

	}//END OF TICK


}
