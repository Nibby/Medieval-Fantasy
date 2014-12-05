package hidden.indev0r.core.gui.component;

import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

public abstract class GComponent {


	public enum GStates {
		DISABLED(-1),

		NORMAL(0),

		HOVERED(1),

		CLICKED(2);

		private int id;

		GStates(int id) {
			this.id = id;
		}

		public int getID() {
			return id;
		}

	}

	//Location and Size
	protected Vector2f position;
	protected int      width;
	protected int      height;

	//Mouse Related
	protected boolean firedHoverEvent;
	protected boolean wasClicked;

	public GComponent(Vector2f pos) {
		this.position = pos;
		firedHoverEvent = false;
		wasClicked = false;
	}

	public abstract void render(Graphics g);

	public abstract void tick(GameContainer gc);

	protected void fireHoverEvent(){
		System.out.println("DIBUJA");
	}

	protected void firePressEvent(){
		System.out.println("NOPE");
	}

}
