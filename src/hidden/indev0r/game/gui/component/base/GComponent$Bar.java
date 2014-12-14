package hidden.indev0r.game.gui.component.base;

import hidden.indev0r.game.texture.Textures;
import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

import java.util.ArrayList;
import java.util.Arrays;

public class GComponent$Bar extends GComponent {


	private   Image   BAR_LEFT;
	private   Image   BAR_CENTER;
	private   Image   BAR_RIGHT;
	private   Image   SPACER_LEFT;
	private   Image   SPACER_RIGHT;
	private   Image[] barImage;
	protected int     tileWidth;
	private   int     tileHeight;
	private   int     length;

	private ArrayList<GComponent$Frame> subFrames;

	public GComponent$Bar(Vector2f pos, int length) {
		super(pos);
		BAR_LEFT = Textures.UI.BAR_LEFT;
		BAR_CENTER = Textures.UI.BAR_CENTER;
		BAR_RIGHT = Textures.UI.BAR_RIGHT;
		SPACER_LEFT = Textures.UI.BAR_SPACER_LEFT;
		SPACER_RIGHT = Textures.UI.BAR_SPACER_RIGHT;
		tileWidth = BAR_CENTER.getWidth();
		tileHeight = BAR_RIGHT.getHeight();

		width = tileWidth * length;
		height = tileHeight;
		setSize(width, height);

		barImage = new Image[length];
		Arrays.fill(barImage, BAR_CENTER);
		barImage[0] = BAR_LEFT;
		barImage[length - 1] = BAR_RIGHT;

		this.length = length;

		subFrames = new ArrayList<>();
	}

	public void addSubFrame(GComponent$Frame frame) {
		frame.position = new Vector2f((position.x + 30), (position.y + this.height + 3));
		frame.tileWidth = length + 1;
		frame.width = frame.tileWidth * frame.stdImageWidth;
		frame.setSize(frame.width, frame.height);
		frame.updateComponentPositions();
		frame.fillFrames();
		subFrames.add(frame);
	}

	@Override
	public void render(Graphics g) {
		super.render(g);

		for (int i = 0; i < barImage.length; i++) barImage[i].draw(position.x + (tileWidth * i), position.y);
		if (subFrames.size() > 0) {
			SPACER_LEFT.draw(position.x + 55, position.y + this.height - 1);
			SPACER_RIGHT.draw(position.x + this.width - 64, position.y + this.height - 1);
		}

		for (GComponent$Frame f : subFrames) f.render(g);

	}

	@Override
	public void tick(GameContainer gc) {
		super.tick(gc);
		for (int i = 0; i < subFrames.size(); i++) {
			subFrames.get(i).tick(gc);
			//System.out.println(subFrames.get(i));
		}
	}
}
