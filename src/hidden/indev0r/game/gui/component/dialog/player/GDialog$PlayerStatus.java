package hidden.indev0r.game.gui.component.dialog.player;

import hidden.indev0r.game.BitFont;
import hidden.indev0r.game.MedievalLauncher;
import hidden.indev0r.game.entity.ActorJob;
import hidden.indev0r.game.entity.player.Player;
import hidden.indev0r.game.entity.animation.ActionType;
import hidden.indev0r.game.gui.component.base.GComponent$Dialog;
import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

/**
 * Created by MrDeathJockey on 14/12/8.
 */
public class GDialog$PlayerStatus extends GComponent$Dialog {

    private static final Color TRANSPARENCY = new Color(0f, 0f, 0f, 0.5f);

    public GDialog$PlayerStatus(Vector2f pos) {
        super("Status", pos, 8, 12);
    }

    @Override
    public void tick(GameContainer gc) {
        super.tick(gc);
    }

    @Override
    public void render(Graphics g) {
        super.render(g);
        Player player = MedievalLauncher.getInstance().getGameState().getPlayer();
        if(player == null) return;

        //Top section
        g.setColor(TRANSPARENCY);
        g.fillRoundRect(position.x + 10, position.y + 35, getWidth() - 20, 40, 10);

        String playerName = "PLAYER";
        int nameWidth = BitFont.widthOf(playerName, 16);
        BitFont.render(g, playerName, (int) position.x + getWidth() - 20 - nameWidth, (int) position.y + 42);

        String byline = "LEVEL " + player.getLevel() + " " + player.getJob().name.toUpperCase();
        int jobNameWidth = BitFont.widthOf(byline, 8);
        BitFont.render(g, byline, (int) position.x + getWidth() - 20 - jobNameWidth, (int) (position.y + 60), Color.white, 8);

        Image playerImg = player.getActionMap().get(ActionType.ATTACK_RIGHT).getFrame((player.getJob().equals(ActorJob.MAGE) ? 0 : 1));
        playerImg.draw(position.x + 20, position.y + 40);
        //-----

        //Stats section
//        g.setColor(TRANSPARENCY);
//        g.fillRoundRect(position.x + 10, position.y + 85, getWidth() - 20, 120, 10);
        //
    }
}
