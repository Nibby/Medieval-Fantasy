package hidden.indev0r.core.gui.menu;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

import java.util.Stack;

public class GMenuManager {
	private Stack<GMenu> menus;

	public GMenuManager() {
		menus = new Stack<>();
	}

	public void addMenu(GMenu menu) {
		menus.push(menu);
	}

	public void popMenu() {
		menus.pop();
	}

	public void clearMenus() {
		menus.clear();
	}

	public void render(Graphics g) {
		if (!menus.isEmpty()) menus.peek().render(g);
	}

	public void tick(GameContainer gc) {
		if (!menus.isEmpty()) menus.peek().tick(gc);
	}


}
