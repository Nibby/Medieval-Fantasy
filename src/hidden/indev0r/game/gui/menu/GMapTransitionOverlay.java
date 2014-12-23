package hidden.indev0r.game.gui.menu;

import hidden.indev0r.game.BitFont;
import hidden.indev0r.game.MedievalLauncher;
import hidden.indev0r.game.entity.Player;
import hidden.indev0r.game.gui.component.base.GComponent;
import hidden.indev0r.game.map.TileMap;
import hidden.indev0r.game.map.WarpType;
import hidden.indev0r.game.reference.References;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

/**
 * Created by MrDeathJockey on 14/12/11.
 */
public class GMapTransitionOverlay extends GMenu {

    private String mapName;
    private Color mapNameColor;
    private TileMap target;
    private int tx, ty;
    private WarpType warpType;

    private static final int PHASE_FADE_IN = 0, PHASE_TEXT_FADE_IN = 1, PHASE_TEXT_FADE_OUT = 2, PHASE_FADE_OUT = 3;
    private float menuAlpha = 0f, textAlpha = 0f;
    private long phaseTick;
    private int phase = 0;
    private static Color background = new Color(0f, 0f, 0f, 0f);

    private GMenuManager manager;

    public GMapTransitionOverlay(GMenuManager manager, TileMap target, WarpType warpType, int tx, int ty, String mapName, Color mapNameColor) {
        this.mapName = mapName;
        this.mapNameColor = mapNameColor;
        this.manager = manager;
        this.target = target;
        this.tx = tx;
        this.ty = ty;
        this.warpType = warpType;

        manager.setDisplayTopMenuOnly(true);
        manager.setTickTopMenuOnly(true);
    }

    public void onAdd() {
        super.onAdd();
        phaseTick = System.currentTimeMillis();
    }

    public void render(Graphics g) {
        background.a = menuAlpha;
        g.setColor(background);
        g.fillRect(0, 0, References.GAME_WIDTH, References.GAME_HEIGHT);

        BitFont.render(g, mapName, References.GAME_WIDTH / 2 - BitFont.widthOf(mapName, 16) / 2, References.GAME_HEIGHT / 2 - 16, mapNameColor, 16, textAlpha);
    }

    public void tick(GameContainer gc) {
        switch(phase) {
            case PHASE_FADE_IN:
                if(System.currentTimeMillis() - phaseTick > 10) {
                    if(menuAlpha < 1f) menuAlpha += 0.05f;
                    else phase++;

                    phaseTick = System.currentTimeMillis();
                }
                break;
            case PHASE_TEXT_FADE_IN:
                if(System.currentTimeMillis() - phaseTick > 10) {
                    if(textAlpha < 1f) textAlpha += 0.05f;
                    else {
                        phase++;
                        phaseTick = System.currentTimeMillis() + ((!mapName.isEmpty()) ? mapName.split(" ").length * 500 : 0);
                        //Do map transition
                        Player player = MedievalLauncher.getInstance().getGameState().getPlayer();
                        MedievalLauncher.getInstance().getGameState().forceWarpEntity(player, target, tx, ty, warpType);
                        //
                        return;
                    }

                    phaseTick = System.currentTimeMillis();
                }
                break;
            case PHASE_TEXT_FADE_OUT:
                if(System.currentTimeMillis() - phaseTick > 10) {
                    if(textAlpha > 0f) textAlpha -= 0.05f;
                    else {
                        phaseTick = System.currentTimeMillis() + 250;
                        phase++;
                        return;
                    }

                    phaseTick = System.currentTimeMillis();
                }
                break;
            case PHASE_FADE_OUT:
                if(System.currentTimeMillis() - phaseTick > 10) {
                    if(menuAlpha > 0f) menuAlpha -= 0.05f;
                    else {
                        manager.setDisplayTopMenuOnly(false);
                        manager.setTickTopMenuOnly(false);
                        manager.popMenu();
                    }

                    phaseTick = System.currentTimeMillis();
                }
                break;
        }
    }

    @Override
    public void componentClicked(GComponent c) {

    }

    @Override
    public void componentHovered(GComponent c) {

    }
}
