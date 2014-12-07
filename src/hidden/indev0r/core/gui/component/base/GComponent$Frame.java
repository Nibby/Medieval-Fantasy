package hidden.indev0r.core.gui.component.base;

import hidden.indev0r.core.texture.Textures;
import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;

import java.util.ArrayList;
import java.util.Arrays;

public class GComponent$Frame extends GComponent {

	//General Data
	protected int tileWidth;
	protected int tileHeight;

	//Frame Data
	protected Image[] frames;
	protected int     stdImageWidth;
	protected int     stdImageHeight;
	protected float   scale; //TODO: Scale immutable


	//Top row image data
	protected Image TOP_RIGHT_FRAME;
	protected Image TOP_MIDDLE_FRAME;
	protected Image TOP_LEFT_FRAME;
	protected int   topHeight;
	protected int   topRightHeight;

	//Components to render on frame;
	protected ArrayList<GComponent> internalComponents;

	public GComponent$Frame(Vector2f pos, int tileWidth, int tileHeight) {
		super(pos);

		//Render Data
		this.tileWidth = tileWidth;
		this.tileHeight = tileHeight;
		this.scale = 1f;
		this.frames = new Image[tileWidth * tileHeight];

		this.stdImageWidth = Textures.UI.FRAME_MIDDLE.getWidth();
		this.stdImageHeight = Textures.UI.FRAME_MIDDLE.getWidth();
		this.topHeight = Textures.UI.FRAME_TOP_RIGHT.getWidth();
		this.topRightHeight = Textures.UI.FRAME_TOP_RIGHT.getHeight();

		if (!this.equals(GComponent$Dialog.class)) {
			this.TOP_RIGHT_FRAME = Textures.UI.FRAME_TOP_RIGHT;
			this.TOP_MIDDLE_FRAME = Textures.UI.FRAME_TOP_MIDDLE;
			this.TOP_LEFT_FRAME = Textures.UI.FRAME_TOP_LEFT;
			fillFrames();
		}

		this.width = tileWidth * stdImageWidth;
		this.height = tileHeight * stdImageHeight;
		internalComponents = new ArrayList<>(0);
	}

	protected void fillFrames() {
		Arrays.fill(frames, Textures.UI.FRAME_MIDDLE);

		for (int x = 0; x < tileWidth; x++) {
			for (int y = 0; y < tileHeight; y++) {
				if (y == 0) frames[x + y * tileWidth] = this.TOP_MIDDLE_FRAME;//Textures.UI.FRAME_TOP_MIDDLE;
				if (y == (tileHeight - 1)) frames[x + y * tileWidth] = Textures.UI.FRAME_BOTTOM_MIDDLE;
				if (x == 0) frames[x + y * tileWidth] = Textures.UI.FRAME_MIDDLE_LEFT;
				if (x == (tileWidth - 1)) frames[x + y * tileWidth] = Textures.UI.FRAME_MIDDLE_RIGHT;

			}//END OF Y LOOP
		}//END OF X LOOP
		frames[0] = this.TOP_LEFT_FRAME;//Textures.UI.FRAME_TOP_LEFT;
		frames[tileWidth - 1] = this.TOP_RIGHT_FRAME;//Textures.UI.FRAME_TOP_RIGHT;
		frames[tileWidth * (tileHeight - 1)] = Textures.UI.FRAME_BOTTOM_LEFT;
		frames[frames.length - 1] = Textures.UI.FRAME_BOTTOM_RIGHT;
	}

	@Override
	public void render(Graphics g) {
		g.pushTransform();
		g.scale(scale, scale);
		for (int x = 0; x < tileWidth; x++)
			for (int y = 0; y < tileHeight; y++)
				if (frames[x + (y * tileWidth)] != null) {
					frames[x + (y * tileWidth)].draw(position.x + (x * stdImageWidth), position.y + (y * stdImageHeight));
				}

		for (GComponent gr : internalComponents) gr.render(g);
		g.popTransform();

	}

	@Override
	public void tick(GameContainer gc) {

		Input input = gc.getInput();
		Vector2f mouse = new Vector2f(input.getMouseX(), input.getMouseY());
		if ((mouse.x > position.x) &&
				(mouse.x < (position.x + (stdImageWidth * tileWidth * scale))) &&
				(mouse.y > position.y) &&
				(mouse.y < (position.y + (stdImageHeight * tileHeight * scale)))
				) {

			if (!firedHoverEvent) {
				fireHoverEvent();
				firedHoverEvent = true;
			}

		} else {
			firedHoverEvent = false;
		}

		for (GComponent gco : internalComponents) gco.tick(gc);
	}

	public void addComponent(GComponent gc) {
		Vector2f gcPos = gc.getPosition();
		gc.setPosition(new Vector2f(position.x + 7 + gcPos.x, position.y + 7 + gcPos.y));
		internalComponents.add(gc);
	}

}
