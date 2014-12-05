package hidden.indev0r.core.gui.menu;

import hidden.indev0r.core.gui.component.GComponentListener;
import hidden.indev0r.core.gui.component.GComponent;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

import java.util.ArrayList;
import java.util.List;

public abstract class GMenu implements GComponentListener {

	protected List<GComponent> componenets;
	protected List<GMenu>      menus;

	public GMenu() {
		componenets = new ArrayList<>(0);
		menus = new ArrayList<>(0);
	}

	public void render(Graphics g) {
		for (GComponent gc : componenets) gc.render(g);
	}

	public void tick(GameContainer gamec) {
		for (GComponent gc : componenets) gc.tick(gamec);
	}

	public void addComponent(GComponent c) {
		componenets.add(c);
	}


}
