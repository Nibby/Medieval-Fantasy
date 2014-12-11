package hidden.indev0r.game.gui.component.base;

import hidden.indev0r.game.gui.Cursor;
import hidden.indev0r.game.gui.component.interfaces.GComponentListener;
import hidden.indev0r.game.gui.menu.GMenu;
import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Rectangle;

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
    protected Rectangle interactBounds;
	//Mouse Related
	protected boolean firedHoverEvent;

    protected boolean wasClicked;
    protected boolean wasInteractInstance = false;
    protected boolean visible = true;
    protected GStates currentState;

	//Listeners
	protected ArrayList<GComponentListener> componentListeners;

	public GComponent(Vector2f pos) {
		this.position = pos;
		firedHoverEvent = false;
		wasClicked = false;
		componentListeners = new ArrayList<>(0);
	}

	public void render(Graphics g) {
        if(!isVisible()) return;
    }

	public void tick(GameContainer gc) {
        if(!isVisible()) return;
        Input input = gc.getInput();
        int mx = input.getMouseX();
        int my = input.getMouseY();

        if(mx > position.x + interactBounds.getX() && mx < position.x + interactBounds.getWidth()
                && my > position.y + interactBounds.getY() && my < position.y + interactBounds.getHeight()) {
            if(Cursor.INTERACT_INSTANCE == null)  {
                Cursor.setInteractInstance(this);
                wasInteractInstance = true;
            }
        } else {
            if(wasInteractInstance) {
                Cursor.setInteractInstance(null);
                wasInteractInstance = false;
            }
        }
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public boolean isVisible() { return visible; }

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

    public void setSize(int width, int height) {
        this.width = width;
        this.height = height;
        setInteractBounds(0, 0, width, height);
    }

    public void setInteractBounds(int x, int y, int w, int h) {
        interactBounds = new Rectangle(x, y, w, h);
    }
}
























