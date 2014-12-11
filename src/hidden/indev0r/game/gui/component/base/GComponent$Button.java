package hidden.indev0r.game.gui.component.base;

import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

public class GComponent$Button extends GComponent$AbstractButton {

	private Image button;
	private Image buttonPressed;
	private Image buttonHovered;
	private Image icon;

    private String text;

    public GComponent$Button(Vector2f pos, Image button) {
		this(pos, button, null);
	}

	public GComponent$Button(Vector2f pos, Image button, Image buttonPressed) {
		this(pos, button, buttonPressed, null);
	}

	public GComponent$Button(Vector2f pos, Image button, Image buttonPressed, Image buttonHovered) {
		this(pos, button, buttonPressed, buttonHovered, null);
	}

	public GComponent$Button(Vector2f pos, Image button, Image buttonPressed, Image buttonHovered, Image icon) {
		super(pos);
		this.button = button;
		this.buttonPressed = buttonPressed;
		this.buttonHovered = buttonHovered;
		this.icon = icon;

        setSize(button.getWidth(), button.getHeight());

		currentState = GStates.NORMAL;
	}

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
	public void render(Graphics g) {
		switch (currentState) {
			case PRESSED:
				if (buttonPressed != null) {
					buttonPressed.draw(position.x, position.y);
				} else {
					button.draw(position.x, position.y);
				}
				if (icon != null) icon.draw(position.x + 3, position.y + 3);
				break;
			case HOVERED:
				if (buttonHovered != null) {
					buttonHovered.draw(position.x, position.y);
				} else {
					button.draw(position.x, position.y);
				}

				if (icon != null) icon.draw(position.x + 2, position.y + 2);
				break;
			case NORMAL:
			default:
				if (button != null) button.draw(position.x, position.y);
				if (icon != null) icon.draw(position.x + 2, position.y + 2);
				break;
		}

        if(text != null && !text.isEmpty()) {

        }
    }
}
