package hidden.indev0r.game.gui.menu;

import hidden.indev0r.game.gui.Cursor;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

import java.util.Stack;

public class GMenuManager {

	private Stack<GMenu> menus;
    private boolean displayTopOnly = true;
    private boolean tickTopOnly = true;

	public GMenuManager() {
		menus = new Stack<>();
	}

	public void addMenu(GMenu menu) {
		menus.push(menu).onAdd();
	}

	public void popMenu() {
		menus.pop().onRemove();
	}

	public void clearMenus() {
		menus.clear();
	}

	public void render(Graphics g) {
		if (!menus.isEmpty()) {
            if(displayTopOnly)
                menus.peek().render(g);
            else
                for(int i = 0; i < menus.size(); i++)
                    menus.get(i).render(g);
        }

        Cursor.render(g);
    }

	public void tick(GameContainer gc) {
		if (!menus.isEmpty()) {
            if (tickTopOnly) menus.peek().tick(gc);
            else
                for(int i = 0; i < menus.size(); i++)
                    menus.get(i).tick(gc);
        }
	}

    /*
        In situations like a game hud, we need to update and render all the hud
        such as a collection of ability tree window, inventory window and quest window
        etc. so that users can interact with any window they'd like.

        Since we don't want that in main menu state, here's a switch that toggles it.
     */
    public Stack<GMenu> getMenus() {
        return menus;
    }

    public void setMenus(Stack<GMenu> menus) {
        this.menus = menus;
    }

    public boolean isDisplayingTopMenuOnly() {
        return displayTopOnly;
    }

    public void setDisplayTopMenuOnly(boolean updateTopOnly) {
        this.displayTopOnly = updateTopOnly;
    }

    public boolean isTickingTopMenuOnly() {
        return tickTopOnly;
    }

    public void setTickTopMenuOnly(boolean tickTopOnly) {
        this.tickTopOnly = tickTopOnly;
    }

    public boolean hasMenus() {
        return !menus.isEmpty();
    }

    public GMenu peekMenu() {
        return menus.peek();
    }
}
