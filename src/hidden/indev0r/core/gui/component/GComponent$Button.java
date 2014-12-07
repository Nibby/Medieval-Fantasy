package hidden.indev0r.core.gui.component;

import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;

public class GComponent$Button extends GComponent {

	private Image   button;
	private Image   buttonPressed;
	private Image   buttonHovered;

	public GComponent$Button(Vector2f pos, Image button) {
		this(pos, button, null);
	}

	public GComponent$Button(Vector2f pos, Image button, Image buttonPressed) {
		this(pos, button, buttonPressed, null);
	}

	public GComponent$Button(Vector2f pos, Image button, Image buttonPressed, Image buttonHovered) {
		super(pos);
		this.button = button;
		this.buttonPressed = buttonPressed;
		this.buttonHovered = buttonHovered;
		this.width = button.getWidth();
		this.height = button.getHeight();
		currentState = GStates.NORMAL;
	}

	@Override
	public void render(Graphics g) {
		switch (currentState) {
			case PRESSED:
				if (buttonPressed != null) buttonPressed.draw(position.x, position.y);
				else button.draw(position.x, position.y);
				break;
			case HOVERED:
				if (buttonHovered != null) buttonHovered.draw(position.x, position.y);
				else button.draw(position.x, position.y);
				break;
			case NORMAL:
			default:
				if (button != null) button.draw(position.x, position.y);
				break;
		}
	}

	@Override
	public void tick(GameContainer gc) {
		Input input = gc.getInput();
		Vector2f mouse = new Vector2f(input.getMouseX(), input.getMouseY());

		if (mouse.x > this.position.x && mouse.x < (this.position.x + this.width) && mouse.y > this.position.y && (mouse.y < this.position.y + this.height)) {

			if (!currentState.equals(GStates.DISABLED)) {
				if (!input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)) {
					currentState = GStates.HOVERED;
					if (!firedHoverEvent) {
						fireHoverEvent();
						firedHoverEvent = true;
					}
				} else {
					currentState = GStates.PRESSED;
					wasClicked = true;
				}

				//Mouse click
				if (wasClicked && !input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)) {
					firePressEvent();
					wasClicked = false;
				}
			}

		}//END OF MOUSE BOUNDS
		else {
			currentState = GStates.NORMAL;
			firedHoverEvent = false;
		}

	}//END OF TICK


}
