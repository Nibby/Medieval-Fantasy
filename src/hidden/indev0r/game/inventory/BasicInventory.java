package hidden.indev0r.game.inventory;

import hidden.indev0r.game.init.Items;
import hidden.indev0r.game.item.Item;

public class BasicInventory implements IInventory {

	private Item[] inventory;

	public BasicInventory(int size) {
		inventory = new Item[size];
		for (Item i : inventory)
			i = Items.testItem;
	}


	@Override
	public int GetInventorySize() {
		return inventory.length;
	}

	@Override
	public Item GetItemInSlot(int slot) {
		return inventory[slot];
	}
}
