package hidden.indev0r.game.gui.component.dialog.player;

import hidden.indev0r.game.BitFont;
import hidden.indev0r.game.MedievalLauncher;
import hidden.indev0r.game.entity.Actor;
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

        //HP
        int playerHP = player.getHealth();
        int playerHPMax = player.getHealthMax();
        int playerHPBonus = player.getStat(Actor.Stat.HEALTH_MAX_BONUS);
        String playerHPText = playerHP + "/" + playerHPMax;
        String playerHPBonusText = " (+" + playerHPBonus + ")";
        int playerHPWidth = BitFont.widthOf(playerHPText, 16);
        BitFont.render(g, "HP:", (int) position.x + 20, (int) position.y + 80, Actor.Stat.HEALTH.getColor(), 16);
        BitFont.render(g, playerHPText, (int) position.x + 20 + BitFont.widthOf("HP: ", 16), (int) position.y + 80);
        BitFont.render(g, playerHPBonusText, (int) position.x + getWidth() - 20 - BitFont.widthOf(playerHPBonusText, 16), (int) position.y + 80, Color.green);


        //MP
        int playerMP = player.getMana();
        int playerMPMax = player.getManaMax();
        int playerMPBonus = player.getStat(Actor.Stat.MANA_MAX_BONUS);
        String playerMPText = playerMP + "/" + playerMPMax;
        String playerMPBonusText = " (+" + playerMPBonus + ")";
        int playerMPWidth = BitFont.widthOf(playerMPText, 16);
        BitFont.render(g, "MP:", (int) position.x + 20, (int) position.y + 100, Actor.Stat.MANA.getColor(), 16);
        BitFont.render(g, playerMPText, (int) position.x + 20 + BitFont.widthOf("MP: ", 16), (int) position.y + 100);
        BitFont.render(g, playerMPBonusText, (int) position.x + getWidth() - 20 - BitFont.widthOf(playerMPBonusText, 16), (int) position.y + 100, Color.green);

        //EXP
        int playerEXP = player.getExperience();
        int playerEXPMax = player.getExperienceMax();
        String playerEXPText = playerEXP + "";
        int playerEXPWidth = BitFont.widthOf(playerEXPText, 16);
        BitFont.render(g, "EXP:", (int) position.x + 20, (int) position.y + 120, Actor.Stat.EXPERIENCE.getColor(), 16);
        BitFont.render(g, playerEXPText, (int) position.x + 20 + BitFont.widthOf("EXP:", 16) + 3, (int) position.y + 120);
        BitFont.render(g, "NEXT LEVEL: " + (playerEXPMax - playerEXP), (int) position.x + 20 + BitFont.widthOf("EXP:", 16) + 3,
                (int) position.y + 136, Color.white, 8);

        //Stats section
//        g.setColor(TRANSPARENCY);
//        g.fillRoundRect(position.x + 10, position.y + 85, getWidth() - 20, 120, 10);
        //
    }
}
