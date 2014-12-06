package hidden.indev0r.core.gui.component;

import hidden.indev0r.core.texture.Textures;
import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;

import java.util.Arrays;

public class GComponent$Frame extends GComponent {

	private Image[] frames;
	private int     imageWidth;
	private int     imageHeight;
	private float   scale;

	public GComponent$Frame(Vector2f pos, int width, int height) {
		super(pos);
		this.width = width;
		this.height = height;
		this.scale = 1f;
		frames = new Image[width * height];
		imageWidth = Textures.UI.FRAME_TOP_RIGHT.getWidth();
		imageHeight = Textures.UI.FRAME_TOP_RIGHT.getHeight();
		fillFrames();
	}

	@Override
	public void render(Graphics g) {
		g.pushTransform();
		g.scale(scale, scale);
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				frames[x + (y * width)].draw(position.x + (x * imageWidth), position.y + (y * imageHeight));
			}
		}
		g.popTransform();
	}

	@Override
	public void tick(GameContainer gc) {

		Input input = gc.getInput();
		Vector2f mouse = new Vector2f(input.getMouseX(), input.getMouseY());

		if ((mouse.x > position.x) && (mouse.x < (position.x + (imageWidth * width * scale))) && (mouse.y > position.y) && (mouse.y < (position.y + (imageHeight * height * scale)))) {
			if (!firedHoverEvent) {
				fireHoverEvent();
				firedHoverEvent = true;
			}
		} else {
			firedHoverEvent = false;
		}

	}

	private void fillFrames() {
		Arrays.fill(frames, Textures.UI.FRAME_MIDDLE);

		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				if (y == 0) frames[x + y * width] = Textures.UI.FRAME_TOP_MIDDLE;
				if (y == (height - 1)) frames[x + y * width] = Textures.UI.FRAME_BOTTOM_MIDDLE;
				if (x == 0) frames[x + y * width] = Textures.UI.FRAME_MIDDLE_LEFT;
				if (x == (width - 1)) frames[x + y * width] = Textures.UI.FRAME_MIDDLE_RIGHT;

			}//END OF Y LOOP
		}//END OF X LOOP
		frames[0] = Textures.UI.FRAME_TOP_LEFT;
		frames[width - 1] = Textures.UI.FRAME_TOP_RIGHT;
		frames[width * (height - 1)] = Textures.UI.FRAME_BOTTOM_LEFT;
		frames[frames.length - 1] = Textures.UI.FRAME_BOTTOM_RIGHT;
	}


//	public GComponent$Frame setScale(float scale) {
//		this.scale = scale;
//		return this;
//	}

}
