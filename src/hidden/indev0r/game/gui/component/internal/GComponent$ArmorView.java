package hidden.indev0r.game.gui.component.internal;

import hidden.indev0r.game.MedievalLauncher;
import hidden.indev0r.game.entity.ActorJob;
import hidden.indev0r.game.entity.player.Player;
import hidden.indev0r.game.gui.component.base.GComponent;
import hidden.indev0r.game.texture.Textures;
import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

/**
 * Created by James on 12/21/2014.
 */
public class GComponent$ArmorView extends GComponent {

    private Image slot;
    private Player player;

    private ActorJob playerClass;

    public GComponent$ArmorView(Vector2f position) {
        super(position);
        slot = Textures.UI.INVENTORY_SLOT;
        player = MedievalLauncher.getInstance().getGameState().getPlayer();
        //playerClass = player.getJob();
    }


    @Override
    public void render(Graphics g) {
        super.render(g);
        //Head
        slot.draw(position.x, position.y);
        g.drawImage(Textures.Icons.HEMLET, position.x, position.y);

        slot.draw(position.x + 40, position.y + 19);
        g.drawImage(Textures.Icons.TORCH, position.x + 40, position.y + 19);

        //Necklace
        slot.draw(position.x - 40, position.y + 19);
        g.drawImage(Textures.Icons.NECKLACE, position.x - 40, position.y + 19);

        //Weapon and Accessory
        slot.draw(position.x - 40, position.y + 60);
        g.drawImage(Textures.Icons.WEAPON_STAFF, position.x - 40, position.y + 60);

        slot.draw(position.x + 40, position.y + 60);
        g.drawImage(Textures.Icons.ACCESSORY_ORB, position.x + 40, position.y + 60);

        //Necklace Chest, Belt, Boots
        slot.draw(position.x, position.y + 40);
        g.drawImage(Textures.Icons.ARMOR_ROBE, position.x, position.y + 40);

        slot.draw(position.x, position.y + 80);
        g.drawImage(Textures.Icons.BELT, position.x, position.y + 80);

        slot.draw(position.x, position.y + 120);
        g.drawImage(Textures.Icons.BOOT, position.x, position.y + 120);

    }

    @Override
    public void tick(GameContainer gc) {
        super.tick(gc);
    }
}
