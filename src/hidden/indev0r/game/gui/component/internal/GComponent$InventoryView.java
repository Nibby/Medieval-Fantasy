package hidden.indev0r.game.gui.component.internal;

import hidden.indev0r.game.gui.component.base.GComponent$Frame;
import hidden.indev0r.game.texture.Textures;
import org.lwjgl.util.vector.Vector2f;

import java.util.Arrays;

public class GComponent$InventoryView extends GComponent$Frame {

	public GComponent$InventoryView(Vector2f pos, int tileWidth, int tileHeight) {
		super(pos, tileWidth, tileHeight);
		this.MIDDLE_FRAME = Textures.UI.INVENTORY_SLOT;
		stdImageHeight = Textures.UI.INVENTORY_SLOT.getHeight();
		stdImageWidth = Textures.UI.INVENTORY_SLOT.getWidth();
		Arrays.fill(this.frames, Textures.UI.INVENTORY_SLOT);
	}


}
