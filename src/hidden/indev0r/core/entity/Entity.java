package hidden.indev0r.core.entity;

import hidden.indev0r.core.Camera;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.Graphics;

public abstract class Entity {

	protected Vector2f    position;
    protected int width, height; //In terms of pixels

	public Entity(){
		this(new Vector2f(0, 0));
	}

	public Entity(Vector2f pos) {
		this.position = pos;
	}

	public abstract void draw(Graphics g, Camera camera);
	public abstract void tick(int delta);

    public void setSize(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public Vector2f getPosition() {
        return position;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
