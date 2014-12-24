package hidden.indev0r.game.entity.player;

import hidden.indev0r.game.inventory.IInventory;
import hidden.indev0r.game.item.Item;

public class InventoryPlayer implements IInventory {

	public Item[] mainInventory = new Item[15];


	@Override
	public int GetInventorySize() {
		return 0;
	}

	@Override
	public Item GetItemInSlot(int slot) {
		return null;
	}
}
