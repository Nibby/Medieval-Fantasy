package hidden.indev0r.core.gui.menu;

import hidden.indev0r.core.gui.component.base.GComponent;
import hidden.indev0r.core.gui.component.listener.GComponentListener;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

import java.util.ArrayList;
import java.util.List;

public abstract class GMenu implements GComponentListener {

	protected List<GComponent> componenets;

	public GMenu() {
		componenets = new ArrayList<>(0);
	}

	public void render(Graphics g) {
		//for (GComponent gc : componenets) gc.render(g);
		for(int i = 0; i < componenets.size(); i++)componenets.get(i).render(g);
	}

	public void tick(GameContainer gamec) {
		//for (GComponent gc : componenets) gc.tick(gamec);
		for(int i = 0; i < componenets.size(); i++)componenets.get(i).tick(gamec);
	}

	public void addComponent(GComponent c) {
		componenets.add(c);
	}

	public void removeComponent(GComponent c){
		componenets.remove(c);
	}


}
