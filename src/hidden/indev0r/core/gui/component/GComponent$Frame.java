package hidden.indev0r.core.gui.component;

import hidden.indev0r.core.texture.Textures;
import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;

import java.util.Arrays;

public class GComponent$Frame extends GComponent {

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


	public GComponent$Frame(Vector2f pos, int width, int height) {
		super(pos);

		//Render Data
		this.width = width;
		this.height = height;
		this.scale = 1f;
		this.frames = new Image[width * height];

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

	}

	@Override
	public void render(Graphics g) {
		g.pushTransform();
		g.scale(scale, scale);
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				if (frames[x + (y * width)] != null) {
					frames[x + (y * width)].draw(position.x + (x * stdImageWidth), position.y + (y * stdImageHeight));
				}
			}
		}
		g.popTransform();
	}

	@Override
	public void tick(GameContainer gc) {

		Input input = gc.getInput();
		Vector2f mouse = new Vector2f(input.getMouseX(), input.getMouseY());

		if ((mouse.x > position.x) && (mouse.x < (position.x + (stdImageWidth * width * scale))) && (mouse.y > position.y) && (mouse.y < (position.y + (stdImageHeight * height * scale)))) {
			if (!firedHoverEvent) {
				fireHoverEvent();
				firedHoverEvent = true;
			}
		} else {
			firedHoverEvent = false;
		}

	}

	protected void fillFrames() {
		Arrays.fill(frames, Textures.UI.FRAME_MIDDLE);

		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				if (y == 0) frames[x + y * width] = this.TOP_MIDDLE_FRAME;//Textures.UI.FRAME_TOP_MIDDLE;
				if (y == (height - 1)) frames[x + y * width] = Textures.UI.FRAME_BOTTOM_MIDDLE;
				if (x == 0) frames[x + y * width] = Textures.UI.FRAME_MIDDLE_LEFT;
				if (x == (width - 1)) frames[x + y * width] = Textures.UI.FRAME_MIDDLE_RIGHT;

			}//END OF Y LOOP
		}//END OF X LOOP
		frames[0] = this.TOP_LEFT_FRAME;//Textures.UI.FRAME_TOP_LEFT;
		frames[width - 1] = this.TOP_RIGHT_FRAME;//Textures.UI.FRAME_TOP_RIGHT;
		frames[width * (height - 1)] = Textures.UI.FRAME_BOTTOM_LEFT;
		frames[frames.length - 1] = Textures.UI.FRAME_BOTTOM_RIGHT;
	}

}
