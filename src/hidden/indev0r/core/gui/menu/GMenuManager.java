package hidden.indev0r.core.gui.menu;

import java.util.Stack;

public class GMenuManager {
	private Stack<GMenu> menus;

	public GMenuManager(){
		menus = new Stack<>();
	}

	public void addMenu(GMenu menu){
		menus.push(menu);
	}

	public void popMenu(){
		menus.pop();
	}

	public void clearMenus(){
		menus.clear();
	}

	public void render(){
		if(!menus.isEmpty())menus.peek().render();
	}

	public void tick(){
		if(!menus.isEmpty())menus.peek().tick();

	}


}
