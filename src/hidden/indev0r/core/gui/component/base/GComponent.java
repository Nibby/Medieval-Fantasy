package hidden.indev0r.core.gui.component.base;

import hidden.indev0r.core.gui.component.interfaces.GComponentListener;
import hidden.indev0r.core.gui.menu.GMenu;
import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

import java.util.ArrayList;

public abstract class GComponent {


	public enum GStates {
		DISABLED(-1),

		NORMAL(0),

		HOVERED(1),

		PRESSED(2);

		private int id;

		GStates(int id) {
			this.id = id;
		}

		public int getID() {
			return id;
		}
	}


	//Parent
	protected GMenu parent;

	//Location and Size
	protected Vector2f position;
	protected int      width;
	protected int      height;

	//Mouse Related
	protected boolean firedHoverEvent;
	protected boolean wasClicked;
	protected GStates currentState;

	//Listeners
	protected ArrayList<GComponentListener> componentListeners;


	public GComponent(Vector2f pos) {
		this.position = pos;
		firedHoverEvent = false;
		wasClicked = false;
		componentListeners = new ArrayList<>(0);
	}

	public abstract void render(Graphics g);

	public abstract void tick(GameContainer gc);

	protected void fireHoverEvent() {
		for (GComponentListener l : componentListeners) l.componentHovered(this);
	}

	protected void firePressEvent() {
		for (GComponentListener l : componentListeners) l.componentClicked(this);
	}


	//Add listener
	public GComponent addListener(GComponentListener l) {
		componentListeners.add(l);
		return this;
	}

	//Add and remove events
	public void onAdd(GMenu parent) {
		this.parent = parent;
	}

	public void onRemove() {}


	//Getters and Setters
	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public Vector2f getPosition() {
		return position;
	}

	public void setPosition(Vector2f position) {
		this.position = position;
	}

	public GMenu getParent() {
		return parent;
	}

}
























