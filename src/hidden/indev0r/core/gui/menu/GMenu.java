package hidden.indev0r.core.gui.menu;

import hidden.indev0r.core.gui.component.base.GComponent;
import hidden.indev0r.core.gui.component.base.GComponent$Frame;
import hidden.indev0r.core.gui.component.listener.GComponentListener;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

import java.util.ArrayList;
import java.util.List;

public abstract class GMenu implements GComponentListener {

	protected List<GComponent> components;

	public GMenu() {
		components = new ArrayList<>(0);
	}

	public void render(Graphics g) {
		//for (GComponent gc : components) gc.render(g);
		for(int i = 0; i < components.size(); i++) components.get(i).render(g);
	}

	public void tick(GameContainer gamec) {
        int removed = 0;
		//for (GComponent gc : components) gc.tick(gamec);
		for(int i = 0; i < components.size() - removed; i++) {
            GComponent c = components.get(i);
            c.tick(gamec);

            if(c instanceof GComponent$Frame) {
                GComponent$Frame dialog = (GComponent$Frame) c;
                if(dialog.isDisposed()) {
                    removeComponent(c);
                    removed++;
                }
            }
        }
	}

	public void addComponent(GComponent c) {
        c.onAdd();
        components.add(c);
	}

	public void removeComponent(GComponent c){
        c.onRemove();
        components.remove(c);
	}

    public void onAdd() {

    }

    public void onRemove() {

    }
}
