package hidden.indev0r.core.gui.menu;

import hidden.indev0r.core.gui.component.GComponent;
import hidden.indev0r.core.gui.component.GComponent$Button;
import hidden.indev0r.core.gui.component.GComponent$Label;
import hidden.indev0r.core.texture.Textures;
import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

public class GTestMenu extends GMenu {

	public GTestMenu() {
		super();
		addComponent(new GComponent$Label("Hello", new Vector2f(40, 40)));
		addComponent(new GComponent$Button(new Vector2f(50,50), Textures.UI.BUTTON));
	}


	@Override
	public void render(Graphics g) {
		super.render(g);
	}

	@Override
	public void tick(GameContainer gc) {
		super.tick(gc);
	}

	@Override
	public void componentClicked(GComponent c) {

	}


}
