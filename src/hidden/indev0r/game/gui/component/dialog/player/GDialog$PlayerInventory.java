package hidden.indev0r.game.gui.component.dialog.player;

import hidden.indev0r.game.MedievalLauncher;
import hidden.indev0r.game.entity.player.InventoryPlayer;
import hidden.indev0r.game.gui.component.base.GComponent$Dialog;
import hidden.indev0r.game.gui.component.internal.GComponent$ArmorView;
import hidden.indev0r.game.gui.component.internal.GComponent$InventoryView;
import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

/**
 * Created by MrDeathJockey on 14/12/8.
 */
public class GDialog$PlayerInventory extends GComponent$Dialog {

    private InventoryPlayer inventory;
    private GComponent$InventoryView inventoryView;
    private GComponent$ArmorView armorView;

    public GDialog$PlayerInventory(Vector2f pos) {
        super("Inventory", pos, 18, 12);
        inventoryView = new GComponent$InventoryView(new Vector2f(0, 3), 9, 9);
        armorView = new GComponent$ArmorView(new Vector2f(width -150,0));
        addComponent(inventoryView);
        addComponent(armorView);

        inventory = MedievalLauncher.getInstance().getGameState().getPlayer().getInventory();
    }

    @Override
    public void render(Graphics g) {
        super.render(g);
    }


    @Override
    public void tick(GameContainer gc) {
        super.tick(gc);
    }
}
