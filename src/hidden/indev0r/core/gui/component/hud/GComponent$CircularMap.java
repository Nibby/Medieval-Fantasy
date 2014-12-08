package hidden.indev0r.core.gui.component.hud;

import hidden.indev0r.core.entity.Entity;
import hidden.indev0r.core.gui.component.base.GComponent;
import hidden.indev0r.core.gui.component.interfaces.GMapSupplier;
import hidden.indev0r.core.reference.References;
import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

import java.util.List;

public class GComponent$CircularMap extends GComponent {


	private GMapSupplier mapSupplier;
	private List<Entity> entities;
	private Entity       centerEntity;


	public GComponent$CircularMap(Vector2f pos, GMapSupplier supplier) {
		super(pos);
		this.width = 100;
		this.height = 100;
		this.mapSupplier = supplier;
		entities = mapSupplier.getEntitiesOnMap();
		setCenterEntity(entities.get(0));
	}

	@Override
	public void render(Graphics g) {
		g.setColor(Color.darkGray);
		g.fillOval(position.x, position.y, width, height);
		g.setColor(Color.cyan);
		g.fillRect(position.x + (width / 2) - 4, position.y + (height / 2) - 4, 8, 8);

	}

	@Override
	public void tick(GameContainer gc) {
		entities = mapSupplier.getEntitiesOnMap();
	}

	private void setCenterEntity(Entity entity) {
		this.centerEntity = entity;
	}


}
