package hidden.indev0r.game.gui.menu;

import hidden.indev0r.game.entity.Player;
import hidden.indev0r.game.gui.component.base.GComponent;
import hidden.indev0r.game.gui.component.base.GComponent$BarDialog;
import hidden.indev0r.game.gui.component.base.GComponent$Dialog;
import hidden.indev0r.game.gui.component.base.GComponent$Frame;
import hidden.indev0r.game.gui.component.interfaces.GComponentListener;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

import java.util.ArrayList;
import java.util.List;

public abstract class GMenu implements GComponentListener {

	protected List<GComponent>      components;

	public GMenu() {
		components = new ArrayList<>(0);
	}

	public void render(Graphics g) {
		//for (GComponent gc : components) gc.render(g);
		for (int i = 0; i < components.size(); i++) {
			components.get(i).render(g);
		}
	}

	public void tick(GameContainer gamec) {
		int removed = 0;
		//for (GComponent gc : components) gc.tick(gamec);
		for (int i = 0 - removed; i < components.size(); i++) {
			GComponent c = components.get(i);
			c.tick(gamec);

			if (c instanceof GComponent$Frame) {
				GComponent$Frame dialog = (GComponent$Frame) c;
				if (dialog.isDisposed()) {
					removeComponent(c);
					removed++;
				}
			}

            if (c instanceof GComponent$BarDialog) {
                GComponent$BarDialog bd = (GComponent$BarDialog) c;
                if(!bd.isVisible()){
                    removeComponent(bd);
                    removed++;
                }
            }

            if(c.isRemoved()) {
                removeComponent(c);
                removed++;
            }
		}
	}

	public void addComponent(GComponent c) {
		c.onAdd(this);
		if(components.size() == 0)
			components.add(components.size(), c);
		else
			components.add(components.size() - 1, c);
	}

	public void removeComponent(GComponent c) {
		c.onRemove();
		components.remove(c);
	}

	public void setComponentTopPriority(GComponent c){
		removeComponent(c);
		addComponent(c);
	}

	public void onAdd() {

	}

	public void onRemove() {

	}
}