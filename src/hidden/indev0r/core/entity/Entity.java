package hidden.indev0r.core.entity;

import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.Graphics;

public abstract class Entity {

	protected Vector2f    position;

	public Entity(){
		this(new Vector2f(0, 0));
	}

	public Entity(Vector2f pos) {
		this.position = pos;
	}

	public abstract void draw(Graphics g);
	public abstract void tick(int delta);

}
