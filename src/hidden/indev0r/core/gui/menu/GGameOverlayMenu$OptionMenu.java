package hidden.indev0r.core.gui.menu;

import hidden.indev0r.core.BitFont;
import hidden.indev0r.core.MedievalLauncher;
import hidden.indev0r.core.gui.component.base.GComponent;
import hidden.indev0r.core.gui.component.base.GComponent$Button;
import hidden.indev0r.core.reference.References;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;

/**
 * Created by MrDeathJockey on 14/12/8.
 */
public class GGameOverlayMenu$OptionMenu extends GMenu {

    private static final Color BACKGROUND_COLOR = new Color(0f, 0f, 0f, 0.4f);

    private GComponent$Button[] buttons;

    public GGameOverlayMenu$OptionMenu() {

    }

    public void render(Graphics g) {
        g.setColor(BACKGROUND_COLOR);
        g.fillRect(0, 0, References.GAME_WIDTH, References.GAME_HEIGHT);

        super.render(g);

        BitFont.render(g, "Menu items go here...`Need to add long, text-holding buttons!``Press [ESC] to get outta here!",
                References.GAME_WIDTH / 2 - 220, References.GAME_HEIGHT / 2 - 75, Color.yellow);
    }

    public void tick(GameContainer gc) {
        super.tick(gc);

        Input input = gc.getInput();
        if(input.isKeyPressed(Input.KEY_ESCAPE)) {
            MedievalLauncher.getInstance().getGameState().getMenuManager().popMenu();
        }
    }

    public void onAdd() {
        super.onAdd();

        //Don't want the user to be playing around with other menus
        MedievalLauncher.getInstance().getGameState().getMenuManager().setTickTopMenuOnly(true);
    }

    public void onRemove() {
        super.onRemove();
        MedievalLauncher.getInstance().getGameState().getMenuManager().setTickTopMenuOnly(false);
    }

    @Override
    public void componentClicked(GComponent c) {

    }

    @Override
    public void componentHovered(GComponent c) {

    }
}
