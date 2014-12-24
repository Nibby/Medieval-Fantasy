package hidden.indev0r.game.item;

import hidden.indev0r.game.texture.Textures;
import org.newdawn.slick.Image;

public class Item {

	private String itemName;
	private Image  itemImage;

	public Item() {
		this("NULL_ITEM", Textures.Images.EMPTY_8x8);
	}

	public Item(String name, Image image) {
		setItemName(name);
		setItemImage(image);
	}

	protected void setItemImage(Image itemImage) {
		this.itemImage = itemImage;
	}

	protected void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public Image getItemImage() {
		return itemImage;
	}

	public String getItemName() {
		return itemName;
	}
}
