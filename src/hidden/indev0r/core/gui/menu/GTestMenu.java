package hidden.indev0r.core.gui.menu;

import hidden.indev0r.core.gui.component.GComponent;
import hidden.indev0r.core.gui.component.GComponent$Button;
import hidden.indev0r.core.gui.component.GComponent$Frame;
import hidden.indev0r.core.gui.component.GComponent$Label;
import hidden.indev0r.core.texture.Textures;
import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

public class GTestMenu extends GMenu {

	private GComponent$Label label;
	private GComponent$Button button;
	private GComponent$Frame frame;

	//TODO: GDialogue box system. How are you going to get these to close?
	//TODO: Cursors
	public GTestMenu() {
		super();
		label = new GComponent$Label("Hello", new Vector2f(1, 1));

		button = new GComponent$Button(new Vector2f(20, 20), Textures.UI.BUTTON, Textures.UI.BUTTON_PRESSED);
		button.addListener(this);

		frame = new GComponent$Frame(new Vector2f(80, 10), 8, 2);
		frame.addListener(this);

		addComponent(label);
		addComponent(button);
		addComponent(frame);

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

	@Override
	public void componentHovered(GComponent c) {

	}


}
