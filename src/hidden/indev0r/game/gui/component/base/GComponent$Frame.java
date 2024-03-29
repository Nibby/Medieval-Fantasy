package hidden.indev0r.game.gui.component.base;

import hidden.indev0r.game.gui.component.interfaces.GDialogListener;
import hidden.indev0r.game.gui.menu.GMenu;
import hidden.indev0r.game.reference.References;
import hidden.indev0r.game.texture.Textures;
import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;

import java.util.ArrayList;
import java.util.Arrays;


public class GComponent$Frame extends GComponent {

    //General Data
    protected int tileWidth;
    protected int tileHeight;

    //Frame Data
    protected Image[] frames;
    protected int stdImageWidth;
    protected int stdImageHeight;
    protected float scale; //TODO: Scale immutable


    //Top row image data
    protected Image TOP_RIGHT_FRAME;
    protected Image TOP_MIDDLE_FRAME;
    protected Image TOP_LEFT_FRAME;

    protected int topHeight;
    protected int topRightHeight;

    //Middle row image data
    protected Image MIDDLE_RIGHT_FRAME;
    protected Image MIDDLE_FRAME;
    protected Image MIDDLE_LEFT_FRAME;

    //Bottom row image data
    protected Image BOTTOM_RIGHT_FRAME;
    protected Image BOTTOM_MIDDLE_FRAME;
    protected Image BOTTOM_LEFT_FRAME;


    //Components to render on frame;
    protected ArrayList<GComponent> internalComponents;
    protected ArrayList<GDialogListener> dialogListeners = new ArrayList<>(0);

    //Frame properties
    protected boolean disposed = false;

    public GComponent$Frame(Vector2f pos, int tileWidth, int tileHeight) {
        super(pos);

        //Render Data
        this.tileWidth = tileWidth;
        this.tileHeight = tileHeight;
        this.scale = 1f;

        this.stdImageWidth = Textures.UI.FRAME_MIDDLE.getWidth();
        this.stdImageHeight = Textures.UI.FRAME_MIDDLE.getWidth();
        this.topHeight = Textures.UI.FRAME_TOP_RIGHT.getWidth();
        this.topRightHeight = Textures.UI.FRAME_TOP_RIGHT.getHeight();

        if (!this.equals(GComponent$Dialog.class)) {
            this.TOP_RIGHT_FRAME = Textures.UI.FRAME_TOP_RIGHT;
            this.TOP_MIDDLE_FRAME = Textures.UI.FRAME_TOP_MIDDLE;
            this.TOP_LEFT_FRAME = Textures.UI.FRAME_TOP_LEFT;
            this.MIDDLE_RIGHT_FRAME = Textures.UI.FRAME_MIDDLE_RIGHT;
            this.MIDDLE_FRAME = Textures.UI.FRAME_MIDDLE;
            this.MIDDLE_LEFT_FRAME = Textures.UI.FRAME_MIDDLE_LEFT;
            this.BOTTOM_RIGHT_FRAME = Textures.UI.FRAME_BOTTOM_RIGHT;
            this.BOTTOM_MIDDLE_FRAME = Textures.UI.FRAME_BOTTOM_MIDDLE;
            this.BOTTOM_LEFT_FRAME = Textures.UI.FRAME_BOTTOM_LEFT;
            fillFrames();
        }

        this.width = tileWidth * stdImageWidth;
        this.height = tileHeight * stdImageHeight;
        setSize(width, height);
        internalComponents = new ArrayList<>(0);
    }

    protected void fillFrames() {

        this.frames = new Image[tileWidth * tileHeight];
        Arrays.fill(frames, this.MIDDLE_FRAME);
        for (int x = 0; x < tileWidth; x++) {
            for (int y = 0; y < tileHeight; y++) {
                if (y == 0) frames[x + y * tileWidth] = this.TOP_MIDDLE_FRAME;//Textures.UI.FRAME_TOP_MIDDLE;
                if (y == (tileHeight - 1))
                    frames[x + y * tileWidth] = this.BOTTOM_MIDDLE_FRAME;//Textures.UI.FRAME_BOTTOM_MIDDLE;
                if (x == 0) frames[x + y * tileWidth] = this.MIDDLE_LEFT_FRAME;//Textures.UI.FRAME_MIDDLE_LEFT;
                if (x == (tileWidth - 1))
                    frames[x + y * tileWidth] = this.MIDDLE_RIGHT_FRAME;//Textures.UI.FRAME_MIDDLE_RIGHT;

            }//END OF Y LOOP
        }//END OF X LOOP
        frames[0] = this.TOP_LEFT_FRAME;//Textures.UI.FRAME_TOP_LEFT;
        frames[tileWidth - 1] = this.TOP_RIGHT_FRAME;//Textures.UI.FRAME_TOP_RIGHT;
        frames[tileWidth * (tileHeight - 1)] = this.BOTTOM_LEFT_FRAME;//Textures.UI.FRAME_BOTTOM_LEFT;
        frames[frames.length - 1] = this.BOTTOM_RIGHT_FRAME;//Textures.UI.FRAME_BOTTOM_RIGHT;
    }

    @Override
    public void render(Graphics g) {
        super.render(g);
        g.pushTransform();
        g.scale(scale, scale);
        for (int x = 0; x < tileWidth; x++)
            for (int y = 0; y < tileHeight; y++)
                if (frames[x + (y * tileWidth)] != null) {
                    frames[x + (y * tileWidth)].draw(position.x + (x * stdImageWidth), position.y + (y * stdImageHeight));
                }

        for (GComponent gr : internalComponents) gr.render(g);
        g.popTransform();

    }

    @Override
    public void tick(GameContainer gc) {
        super.tick(gc);
        Input input = gc.getInput();
        Vector2f mouse = new Vector2f(input.getMouseX(), input.getMouseY());
        if ((mouse.x > position.x) &&
                (mouse.x < (position.x + (width * scale))) &&
                (mouse.y > position.y) &&
                (mouse.y < (position.y + (height * scale)))
                ) {

            if (!firedHoverEvent) {
                fireHoverEvent();
                firedHoverEvent = true;
            }

        } else {
            firedHoverEvent = false;
        }

        for (GComponent gco : internalComponents) gco.tick(gc);
    }

    public void addComponent(GComponent gc) {
        Vector2f gcPos = gc.getPosition();
        gc.setPosition(new Vector2f(position.x + 7 + gcPos.x, position.y + 7 + gcPos.y));
        internalComponents.add(gc);
    }

    public void dispose() {
        disposed = true;
        visible = false;

        fireDialogCloseEvent();
    }

    public void updateComponentPositions() {
        for (GComponent c : internalComponents)
            c.setPosition(new Vector2f(position.x + c.getPosition().x, position.y + c.getPosition().y));

    }

    protected void fireTitleBarClickedEvent() {
        for (GDialogListener g : dialogListeners) g.titleBarClicked(this);
    }

    protected void fireDialogCloseEvent() {
        for (GDialogListener g : dialogListeners) g.dialogClosed(this);
    }

    public boolean isDisposed() {
        return disposed;
    }

    public void onAdd(GMenu menu) {
        super.onAdd(menu);
        visible = true;
    }

    public void onRemove() {
        super.onRemove();
        visible = false;
    }

    public static Vector2f alignToCenter(int frameWidth, int frameHeight) {
        return new Vector2f(References.GAME_WIDTH / 2 - frameWidth / 2, References.GAME_HEIGHT / 2 - frameHeight / 2);
    }
}
