package hidden.indev0r.core.gui.component;

import hidden.indev0r.core.BitFont;
import hidden.indev0r.core.gui.component.listener.GComponentListener;
import hidden.indev0r.core.texture.Textures;
import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

public class GComponent$Dialog extends GComponent$Frame {

	private GComponent$Button closeButton;
	private String            title;

	public GComponent$Dialog(Vector2f pos, int width, int height) {
		this(null, pos, width, height);
	}

	public GComponent$Dialog(String title, Vector2f pos, int width, int height) {
		super(pos, width, height);
		this.topHeight = Textures.UI.FRAME_TOP_MIDDLE_DIALOG.getHeight();
		this.topRightHeight = Textures.UI.FRAME_TOP_RIGHT_DIALOG.getHeight();
		this.TOP_RIGHT_FRAME = Textures.UI.FRAME_TOP_RIGHT_DIALOG;
		this.TOP_MIDDLE_FRAME = Textures.UI.FRAME_TOP_MIDDLE_DIALOG;
		this.TOP_LEFT_FRAME = Textures.UI.FRAME_TOP_LEFT_DIALOG;

		closeButton = new GComponent$Button(
				new Vector2f(position.x + (stdImageWidth * width) - 25, position.y + 1),
				Textures.UI.BUTTON_ROUND_RED_NORMAL,
				Textures.UI.BUTTON_ROUND_RED_PRESSED,
				Textures.UI.BUTTON_ROUND_RED_HOVERED);
		this.title = (title != null ? title : "");

		fillFrames();
	}

	@Override
	public void render(Graphics g) {
		g.pushTransform();
		g.scale(scale, scale);
		for (int x = 0; x < tileWidth; x++) {
			for (int y = 0; y < tileHeight; y++) {
				if (frames[x + (y * tileWidth)] != null) {
					//Draw unique top right frame
					if ((x + (y * tileWidth)) == (tileWidth - 1)) {
						TOP_RIGHT_FRAME.draw(position.x + (x * stdImageWidth), position.y + (y * stdImageHeight) - 4);
						continue;
					}
					//Draw top row
					if (y == 1) {
						frames[x + (y * tileWidth)].draw(position.x + (x * stdImageWidth), position.y + (y * stdImageHeight));
						continue;
					}
					//Render the rest
					frames[x + (y * tileWidth)].draw(position.x + (x * stdImageWidth), position.y + (y * stdImageHeight));
				}
			}
		}
		//Render close button
		closeButton.render(g);
		BitFont.render(g, title, (int) position.x + 5, (int) position.y + 3, Color.white);
		for (GComponent gc : internalComponents) gc.render(g);
		g.popTransform();
	}

	@Override
	public void tick(GameContainer gc) {
		super.tick(gc);
		closeButton.tick(gc);
		for (GComponent gco : internalComponents) gco.tick(gc);
	}

	@Override
	public GComponent addListener(GComponentListener l) {
		closeButton.addListener(l);
		for (GComponent gc : internalComponents) gc.addListener(l);
		return super.addListener(l);
	}


	@Override
	public void addComponent(GComponent gc) {
		Vector2f gcPos = gc.getPosition();
		gc.setPosition(new Vector2f(position.x + 7 + gcPos.x, position.y + 29 + gcPos.y));
		internalComponents.add(gc);
	}
}
