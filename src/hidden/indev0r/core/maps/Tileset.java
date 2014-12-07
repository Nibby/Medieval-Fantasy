package hidden.indev0r.core.maps;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

/**
 * Created by MrDeathJockey on 14/12/3.
 */
public class Tileset {

	private SpriteSheet textureMap;

	public Tileset(String imgPath) throws SlickException {
		setTextureMap(imgPath);
	}

	public Image getTexture(int x, int y) {
		return textureMap.getSprite(x, y);
	}

	private void setTextureMap(String imagePath) throws SlickException {
		textureMap = new SpriteSheet(imagePath, Tile.TILE_SIZE, Tile.TILE_SIZE);
	}
}
