package hidden.indev0r.core.gui.menu;

import hidden.indev0r.core.BitFont;
import hidden.indev0r.core.gui.component.*;
import hidden.indev0r.core.texture.Textures;
import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

public class GTestMenu extends GMenu {

	private GComponent$Label  label;
	private GComponent$Button button;
	private GComponent$Frame  dialog;
	private GComponent$Frame  frame;

	//TODO: GDialogue box system. How are you going to get these to close?
	//TODO: Cursors
	public GTestMenu() {
		super();
		label = new GComponent$Label("Hello", new Vector2f(1, 1));

		button = new GComponent$Button(new Vector2f(20, 20), Textures.UI.BUTTON, Textures.UI.BUTTON_PRESSED);
		button.addListener(this);

		dialog = new GComponent$Dialog("Title", new Vector2f(80, 10), 8, 5);
		dialog.addComponent(new GComponent$Button(new Vector2f(0, 0), Textures.UI.BUTTON, Textures.UI.BUTTON_PRESSED));
		dialog.addListener(this);

		frame = new GComponent$Frame(new Vector2f(340, 10), 10, 5);
		frame.addComponent(new GComponent$Label("Player Stats", new Vector2f((frame.getWidth() / 2) - (BitFont.widthOf("Player Stats", 0) / 2), 0)));
		frame.addListener(this);

		addComponent(label);
		addComponent(button);
		addComponent(dialog);
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
		System.out.printf("Component %s clicked\n", c.getClass().toString());
	}

	@Override
	public void componentHovered(GComponent c) {
		System.out.println("Component hovered");
	}


}
