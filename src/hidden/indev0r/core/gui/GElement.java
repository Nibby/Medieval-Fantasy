package hidden.indev0r.core.gui;

import org.lwjgl.util.vector.Vector2f;

public abstract class GElement {

	protected Vector2f location;

	public GElement(){
		this(new Vector2f(0, 0));
	}

	public GElement(int x, int y) {
		this(new Vector2f(x, y));
	}

	public GElement(Vector2f coords){
		this.location = coords;
	}


	public abstract void render();

}
