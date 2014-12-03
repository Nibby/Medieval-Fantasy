package hidden.indev0r.core.entity;

import org.lwjgl.util.vector.Vector2f;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SpriteSheet;

public class Entity {

	protected SpriteSheet sheet;
	protected Image       sprite;
	protected Vector2f    position;

	public Entity() {
		this(null);

	}
	//TODO: Make these methods less repetitive. (There must be a way)
	public Entity(Image sprite) {
		this.sprite = sprite;
		position = new Vector2f(50, 50);
	}

	public Entity(SpriteSheet sheet) {
		this.sheet = sheet;
		position = new Vector2f(0, 0);
	}

	public void draw(Graphics g) {
		if (sprite == null) return;
		g.drawImage(sprite, position.x, position.y);
	}

	public void tick(int delta) {

	}


}
