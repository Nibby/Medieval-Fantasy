package hidden.indev0r.game.inventory;

import hidden.indev0r.game.item.Item;

public interface IInventory {

	int GetInventorySize();
	Item GetItemInSlot(int slot);

}
