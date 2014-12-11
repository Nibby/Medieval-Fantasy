package hidden.indev0r.game.gui.component.base;

import hidden.indev0r.game.BitFont;
import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

public class GComponent$Button extends GComponent$AbstractButton {

	private Image button;
	private Image buttonPressed;
	private Image buttonHovered;
	private Image icon;

    private String text;
    private int textSize = 8;

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

    public Image getIcon() {
        return icon;
    }

    public void setIcon(Image icon) {
        this.icon = icon;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getTextSize() {
        return textSize;
    }

    public void setTextSize(int textSize) {
        this.textSize = textSize;
    }

    @Override
	public void render(Graphics g) {
        super.render(g);
		switch (currentState) {
			case PRESSED:
				if (buttonPressed != null) {
					buttonPressed.draw(position.x, position.y);
				} else {
					button.draw(position.x, position.y);
				}
				if (icon != null) icon.draw(position.x + width / 2 - 3, position.y + height / 2 - 3);
				break;
			case HOVERED:
				if (buttonHovered != null) {
					buttonHovered.draw(position.x, position.y);
				} else {
					button.draw(position.x, position.y);
				}

				if (icon != null) icon.draw(position.x + width / 2 - 4, position.y + height / 2 - 4);
				break;
			case NORMAL:
			default:
				if (button != null) button.draw(position.x, position.y);
				if (icon != null) icon.draw(position.x + width / 2 - 4, position.y + width / 2 - 4);
				break;
		}

        if(text != null && !text.isEmpty()) {
            BitFont.render(g, text, (int) position.x + width / 2 - BitFont.widthOf(text, 8) / 2 + ((currentState == GStates.PRESSED) ? 1 : 0),
                    (int) position.y + height / 2 - 4 + ((currentState == GStates.PRESSED) ? 1 : 0), Color.white, textSize);
        }
    }
}
