package hidden.indev0r.core.gui.component;


import hidden.indev0r.core.BitFont;
import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;


public class GComponent$Label extends GComponent {

	private String text;
	private Color color;

	public GComponent$Label(String text, Vector2f pos){
		this(text, pos, Color.white);
	}

	public GComponent$Label(String text, Vector2f pos, Color color){
		super(pos);
		this.text = text;
		this.color = color;
	}

	@Override
	public void render(Graphics g) {
		BitFont.render(g, text, (int) position.x, (int) position.y, color);
	}

	@Override
	public void tick(GameContainer gc) {

	}
}
