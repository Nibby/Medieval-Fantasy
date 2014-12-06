package hidden.indev0r.core.gui.component;

import hidden.indev0r.core.texture.Textures;
import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.Graphics;

public class GComponent$Dialog extends GComponent$Frame {

	public GComponent$Dialog(Vector2f pos, int width, int height) {
		super(pos, width, height);
		this.topHeight = Textures.UI.FRAME_TOP_MIDDLE_DIALOG.getHeight();
		this.topRightHeight = Textures.UI.FRAME_TOP_RIGHT_DIALOG.getHeight();
		this.TOP_RIGHT_FRAME = Textures.UI.FRAME_TOP_RIGHT_DIALOG;
		this.TOP_MIDDLE_FRAME = Textures.UI.FRAME_TOP_MIDDLE_DIALOG;
		this.TOP_LEFT_FRAME = Textures.UI.FRAME_TOP_LEFT_DIALOG;


		fillFrames();
	}

	@Override
	public void render(Graphics g) {
		g.pushTransform();
		g.scale(scale, scale);
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				if (frames[x + (y * width)] != null) {


					//Draw unique top right frame
					if ((x + (y * width)) == (width - 1)) {
						TOP_RIGHT_FRAME.draw(position.x + (x * stdImageWidth), position.y + (y * stdImageHeight) - 4);
						continue;
					}

					//Draw top row
					if (y == 1) {
						frames[x + (y * width)].draw(position.x + (x * stdImageWidth), position.y + (y * stdImageHeight));
						continue;
					}

					//Render the rest
					frames[x + (y * width)].draw(position.x + (x * stdImageWidth), position.y + (y * stdImageHeight));
				}
			}
		}
		g.popTransform();
	}
}
