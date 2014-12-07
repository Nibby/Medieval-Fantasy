package hidden.indev0r.core.gui.component;

import hidden.indev0r.core.gui.component.base.GComponent;
import hidden.indev0r.core.gui.component.base.GComponent$Button;
import hidden.indev0r.core.gui.component.listener.GComponentListener;
import hidden.indev0r.core.texture.Textures;
import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

import java.util.ArrayList;

public class GComponent$Minimap extends GComponent {

	//private int sideButtons;
	public ArrayList<GComponent$SidebarButton> sideButtons;

	private Image             minimap;
	private Image             buttonAttach;
	public  GComponent$Button zoomInButton;
	public  GComponent$Button zoomOutButton;
	public  GComponent$Button bigMapButton;


	public GComponent$Minimap(Vector2f pos, int numOfButton) {
		super(pos);
		minimap = Textures.UI.MINIMAP_BASE;
		this.width = minimap.getWidth();
		this.height = minimap.getHeight();


		buttonAttach = Textures.UI.MINIMAP_BUTTON_ATTACH;

		zoomInButton = new GComponent$Button(new Vector2f(position.x + 10, position.y + 88),
				Textures.UI.BUTTON_ROUND_GREEN_NORMAL,
				Textures.UI.BUTTON_ROUND_GREEN_PRESSED,
				Textures.UI.BUTTON_ROUND_GREEN_HOVERED,
				Textures.Icons.PLUS
		);
		zoomOutButton = new GComponent$Button(new Vector2f(position.x + 26, position.y + 104),
				Textures.UI.BUTTON_ROUND_RED_NORMAL,
				Textures.UI.BUTTON_ROUND_RED_PRESSED,
				Textures.UI.BUTTON_ROUND_RED_HOVERED,
				Textures.Icons.MINUS
		);

		bigMapButton = new GComponent$Button(new Vector2f(position.x + 96, position.y + 18),
				Textures.UI.BUTTON_ROUND_BLUE_NORMAL,
				Textures.UI.BUTTON_ROUND_BLUE_PRESSED,
				Textures.UI.BUTTON_ROUND_BLUE_HOVERED,
				Textures.Icons.MAGNIFYING_GLASS
		);


		//SIDE BUTTONS
		this.sideButtons = new ArrayList<>(0);
		for (int i = 0; i < numOfButton; i++) {
			if (i == 0) {
				this.sideButtons.add(new GComponent$SidebarButton(
						new Vector2f(position.x + 84, position.y + 130),
						Textures.UI.MINIMAP_BUTTON_NORMAL,
						Textures.UI.MINIMAP_BUTTON_PRESSED));
				continue;
			}
			GComponent$SidebarButton sb = new GComponent$SidebarButton(
					new Vector2f((position.x + 84), (position.y + 130) + (i * Textures.UI.MINIMAP_BUTTON_NORMAL.getWidth()) + (4 * i)),
					Textures.UI.MINIMAP_BUTTON_NORMAL,
					Textures.UI.MINIMAP_BUTTON_PRESSED);
			sb.setConnectorImage(Textures.UI.MINIMAP_BUTTON_SEPERATOR);
			this.sideButtons.add(sb);
		}


	}


	@Override
	public void render(Graphics g) {
		minimap.draw(position.x, position.y);
		buttonAttach.draw(position.x + 97, position.y + 103);
		zoomInButton.render(g);
		zoomOutButton.render(g);
		bigMapButton.render(g);
		for (GComponent$SidebarButton s : sideButtons) s.render(g);
	}

	@Override
	public void tick(GameContainer gc) {
		zoomInButton.tick(gc);
		zoomOutButton.tick(gc);
		bigMapButton.tick(gc);
		for (GComponent$SidebarButton s : sideButtons) s.tick(gc);
	}

	@Override
	public GComponent addListener(GComponentListener l) {
		zoomInButton.addListener(l);
		zoomOutButton.addListener(l);
		bigMapButton.addListener(l);
		for (GComponent$SidebarButton s : sideButtons) s.addListener(l);
		return super.addListener(l);
	}


	private class GComponent$SidebarButton extends GComponent$Button {

		private Image connectorImage;

		public GComponent$SidebarButton(Vector2f pos, Image button, Image buttonPressed) {
			super(pos, button, buttonPressed);
			connectorImage = null;
		}


		@Override
		public void render(Graphics g) {
			super.render(g);
			if (connectorImage != null) connectorImage.draw(position.x + 12, position.y - connectorImage.getHeight());
		}

		public GComponent$SidebarButton setConnectorImage(Image connectorImage) {
			this.connectorImage = connectorImage;
			return this;
		}
	}


}
