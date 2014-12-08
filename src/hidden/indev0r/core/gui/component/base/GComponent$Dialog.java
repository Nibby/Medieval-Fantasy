package hidden.indev0r.core.gui.component.base;

import hidden.indev0r.core.BitFont;
import hidden.indev0r.core.MedievalLauncher;
import hidden.indev0r.core.gui.Cursor;
import hidden.indev0r.core.gui.component.interfaces.GComponentListener;
import hidden.indev0r.core.gui.component.interfaces.GDialogListener;
import hidden.indev0r.core.texture.Textures;
import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.*;
import org.newdawn.slick.util.InputAdapter;

import java.util.ArrayList;

public class GComponent$Dialog extends GComponent$Frame implements GComponentListener {

	//This doesn't seem right...
	protected static ArrayList<GDialogListener> dialogListeners = new ArrayList<>(0);

	public  GComponent$Button closeButton;
	private String            title;

	private boolean draggable = true;
	private boolean closed    = false;

	private DialogInputHandler inputHandler;

	public GComponent$Dialog(Vector2f pos, int width, int height) {
		this(null, pos, width, height);
	}

	public GComponent$Dialog(String title, Vector2f pos, int width, int height) {
		super(pos, width, height);
		this.topHeight = Textures.UI.FRAME_TOP_MIDDLE_DIALOG.getHeight();
		this.topRightHeight = Textures.UI.FRAME_TOP_RIGHT_DIALOG.getHeight();
		this.TOP_RIGHT_FRAME = Textures.UI.FRAME_TOP_RIGHT_DIALOG;
		this.TOP_MIDDLE_FRAME = Textures.UI.FRAME_TOP_MIDDLE_DIALOG;
		this.TOP_LEFT_FRAME = Textures.UI.FRAME_TOP_LEFT_DIALOG;

		closeButton = new GComponent$Button(
				new Vector2f((stdImageWidth * width) - 32, -28),
				Textures.UI.BUTTON_ROUND_RED_NORMAL,
				Textures.UI.BUTTON_ROUND_RED_PRESSED,
				Textures.UI.BUTTON_ROUND_RED_HOVERED);
		closeButton.addListener(this);
		addComponent(closeButton);
		this.title = (title != null ? title : "");

		MedievalLauncher.getInstance().getGameContainer().getInput().addListener((inputHandler = new DialogInputHandler()));
		fillFrames();
	}

	@Override
	public void render(Graphics g) {
		if (!isVisible()) return;
		g.pushTransform();
		g.scale(scale, scale);
		for (int x = 0; x < tileWidth; x++) {
			for (int y = 0; y < tileHeight; y++) {
				if (frames[x + (y * tileWidth)] != null) {
					//Draw unique top right frame
					if ((x + (y * tileWidth)) == (tileWidth - 1)) {
						TOP_RIGHT_FRAME.draw(position.x + (x * stdImageWidth), position.y + (y * stdImageHeight) - 4);
						continue;
					}
					//Draw top row
					if (y == 1) {
						frames[x + (y * tileWidth)].draw(position.x + (x * stdImageWidth), position.y + (y * stdImageHeight));
						continue;
					}
					//Render the rest
					frames[x + (y * tileWidth)].draw(position.x + (x * stdImageWidth), position.y + (y * stdImageHeight));
				}
			}
		}
		//Render close button
		BitFont.render(g, title, (int) position.x + 5, (int) position.y + 3, Color.white);
		for (GComponent gc : internalComponents) gc.render(g);
		g.popTransform();
	}

	@Override
	public void tick(GameContainer gc) {
		super.tick(gc);
		closeButton.tick(gc);
		for (GComponent gco : internalComponents) gco.tick(gc);

		Input input = gc.getInput();
		Vector2f mouse = new Vector2f(input.getMouseX(), input.getMouseY());

        if (mouse.x > this.position.x && mouse.x < (this.position.x + this.width) && mouse.y > this.position.y && (mouse.y < this.position.y + 32)) {
            if (currentState.equals(GStates.DISABLED)) return;
            //Mouse click
            if (input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
                fireTitleBarClickedEvent();
            }
        }//END OF MOUSE BOUNDS
		else {
			currentState = GStates.NORMAL;
		}


	}

	public void addDialogListener(GDialogListener l) {
		dialogListeners.add(l);
	}

	@Override
	public GComponent addListener(GComponentListener l) {
		closeButton.addListener(l);
		for (GComponent gc : internalComponents) gc.addListener(l);
		return super.addListener(l);
	}

	@Override
	public void addComponent(GComponent gc) {
		Vector2f gcPos = gc.getPosition();
		gc.setPosition(new Vector2f(position.x + 7 + gcPos.x, position.y + 29 + gcPos.y));
		internalComponents.add(gc);
	}

	public boolean canDrag() {
		return draggable;
	}

	public void setDraggable(boolean mouseMovable) {
		this.draggable = mouseMovable;
	}

	@Override
	public void componentClicked(GComponent c) {
		if (c.equals(closeButton)) {
			dispose();
		}
	}

	@Override
	public void componentHovered(GComponent c) {
	}

	@Override
	public void dispose() {
		super.dispose();
		MedievalLauncher.getInstance().getGameContainer().getInput().removeListener(inputHandler);
	}

	private void fireTitleBarClickedEvent() {
		//if (this instanceof GComponent$Dialog) {
		for (GDialogListener g : dialogListeners) g.titleBarClicked(this);
		//}
	}


	class DialogInputHandler extends InputAdapter {
		boolean wasDragging = false;

		private boolean isDialogDragInstance() {
			if (Cursor.DRAG_INSTANCE != null) {
				Object dragObj = Cursor.DRAG_INSTANCE;
				if (!(dragObj instanceof GComponent$Dialog)) {
					return false;
				} else {
					//Check if the dragging dialog is this dialog
					GComponent$Dialog dialog = (GComponent$Dialog) dragObj;
					return dialog.equals(GComponent$Dialog.this);
				}
			} else {
				return true;
			}
		}

		//Dragging frames = moving
		@Override
		public void mouseDragged(int oldx, int oldy, int newx, int newy) {
			//Check cursor's current drag instance to prevent multi-dragging
			if (wasDragging || newx > position.x && newx < position.x + width && newy > position.y && newy < position.y + 32) {
				if (!isDialogDragInstance()) return;

				int relativeX = (int) (oldx - position.x);
				int relativeY = (int) (oldy - position.y);

				position.x = newx - relativeX;
				position.y = newy - relativeY;

				int diffX = newx - oldx;
				int diffY = newy - oldy;

				for (GComponent gc : internalComponents) {
					Vector2f oldComponentPosition = gc.getPosition();
					Vector2f newComponentPosition = new Vector2f(oldComponentPosition.x + diffX, oldComponentPosition.y + diffY);

					gc.setPosition(newComponentPosition);
				}

				wasDragging = true;
				Cursor.DRAG_INSTANCE = GComponent$Dialog.this;
			}
		}

		@Override
		public void mouseReleased(int button, int x, int y) {
			super.mouseReleased(button, x, y);

			//Release all drag focus
			if (button == Input.MOUSE_LEFT_BUTTON) {
				wasDragging = false;

				if (isDialogDragInstance()) {
					//Release
					Cursor.DRAG_INSTANCE = null;
				}
			}
		}
	}
}
