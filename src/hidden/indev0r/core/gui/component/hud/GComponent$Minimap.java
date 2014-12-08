package hidden.indev0r.core.gui.component.hud;

import hidden.indev0r.core.MedievalLauncher;
import hidden.indev0r.core.gui.component.base.GComponent;
import hidden.indev0r.core.gui.component.base.GComponent$Button;
import hidden.indev0r.core.gui.component.dialog.GComponent$InventoryDialog;
import hidden.indev0r.core.gui.component.dialog.GComponent$JournalDialog;
import hidden.indev0r.core.gui.component.dialog.GComponent$SkillDialog;
import hidden.indev0r.core.gui.component.dialog.GComponent$StatusDialog;
import hidden.indev0r.core.gui.component.interfaces.GComponentListener;
import hidden.indev0r.core.gui.component.interfaces.GDialogListener;
import hidden.indev0r.core.gui.component.interfaces.GMapSupplier;
import hidden.indev0r.core.gui.menu.GGameOverlayMenu$OptionMenu;
import hidden.indev0r.core.texture.Textures;
import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

import java.util.ArrayList;

public class GComponent$Minimap extends GComponent implements GComponentListener, GDialogListener {

	public ArrayList<GComponent$SidebarButton> sideButtons;

	private Image             minimap;
	private Image             buttonAttach;
	public  GComponent$Button zoomInButton;
	public  GComponent$Button zoomOutButton;
	public  GComponent$Button bigMapButton;

	private GComponent$CircularMap     mapView;
	private GComponent$StatusDialog    dialogStatus;
	private GComponent$InventoryDialog dialogInventory;
	private GComponent$SkillDialog     dialogSkill;
	private GComponent$JournalDialog   dialogJournal;

	public GComponent$Minimap(Vector2f pos, GMapSupplier supplier, int numOfButton) {
		super(pos);
		minimap = Textures.UI.MINIMAP_BASE;
		this.width = minimap.getWidth();
		this.height = minimap.getHeight();

		buttonAttach = Textures.UI.MINIMAP_BUTTON_ATTACH;

		zoomInButton = new GComponent$Button(new Vector2f(position.x + 10, position.y + 88),
				Textures.UI.BUTTON_ROUND_GREEN_NORMAL,
				Textures.UI.BUTTON_ROUND_GREEN_PRESSED,
				Textures.UI.BUTTON_ROUND_GREEN_HOVERED,
				Textures.Icons.PLUS
		);
		zoomInButton.addListener(this);

		zoomOutButton = new GComponent$Button(new Vector2f(position.x + 26, position.y + 104),
				Textures.UI.BUTTON_ROUND_RED_NORMAL,
				Textures.UI.BUTTON_ROUND_RED_PRESSED,
				Textures.UI.BUTTON_ROUND_RED_HOVERED,
				Textures.Icons.MINUS
		);
		zoomOutButton.addListener(this);

		bigMapButton = new GComponent$Button(new Vector2f(position.x + 96, position.y + 18),
				Textures.UI.BUTTON_ROUND_BLUE_NORMAL,
				Textures.UI.BUTTON_ROUND_BLUE_PRESSED,
				Textures.UI.BUTTON_ROUND_BLUE_HOVERED,
				Textures.Icons.MAGNIFYING_GLASS
		);
		bigMapButton.addListener(this);


		//SIDE BUTTONS
		this.sideButtons = new ArrayList<>(0);
		for (int i = 0; i < numOfButton; i++) {
			if (i == 0) {
				GComponent$SidebarButton sb = new GComponent$SidebarButton(
						new Vector2f(position.x + 84, position.y + 130),
						Textures.UI.MINIMAP_BUTTON_NORMAL,
						Textures.UI.MINIMAP_BUTTON_PRESSED);
				sb.addListener(this);
				this.sideButtons.add(sb);
				continue;
			}

			GComponent$SidebarButton sb = new GComponent$SidebarButton(
					new Vector2f((position.x + 84), (position.y + 130) + (i * Textures.UI.MINIMAP_BUTTON_NORMAL.getWidth()) + (4 * i)),
					Textures.UI.MINIMAP_BUTTON_NORMAL,
					Textures.UI.MINIMAP_BUTTON_PRESSED);
			sb.setConnectorImage(Textures.UI.MINIMAP_BUTTON_SEPERATOR);
			sb.addListener(this);
			this.sideButtons.add(sb);
		}

		//I know parameters are duplicated, second one is for the icon when button is pressed. For now I think this way looks best (in game, of course)
		this.sideButtons.get(0).setIcon(Textures.Icons.CHARACTER_BIG, Textures.Icons.CHARACTER_BIG);
		this.sideButtons.get(1).setIcon(Textures.Icons.INVENTORY_BIG, Textures.Icons.INVENTORY_BIG);
		this.sideButtons.get(2).setIcon(Textures.Icons.SCROLL_BIG, Textures.Icons.SCROLL_BIG);
		this.sideButtons.get(3).setIcon(Textures.Icons.BOOK_BIG, Textures.Icons.BOOK_BIG);
		this.sideButtons.get(4).setIcon(Textures.Icons.MENU_BIG, Textures.Icons.MENU_BIG);



		Vector2f dialogPos = new Vector2f(200, 200);
		dialogStatus = new GComponent$StatusDialog(dialogPos);
		dialogInventory = new GComponent$InventoryDialog(dialogPos);
		dialogSkill = new GComponent$SkillDialog(dialogPos);
		dialogJournal = new GComponent$JournalDialog(dialogPos);

		dialogStatus.addDialogListener(this);
		dialogInventory.addDialogListener(this);
		dialogSkill.addDialogListener(this);
		dialogJournal.addDialogListener(this);

		mapView = new GComponent$CircularMap(new Vector2f(position.x + 14, position.y + 14), supplier);
	}

	@Override
	public void render(Graphics g) {
		mapView.render(g);
		minimap.draw(position.x, position.y);
		buttonAttach.draw(position.x + 97, position.y + 103);
		zoomInButton.render(g);
		zoomOutButton.render(g);
		bigMapButton.render(g);

		for (GComponent$SidebarButton s : sideButtons) s.render(g);
	}

	@Override
	public void tick(GameContainer gc) {
		zoomInButton.tick(gc);
		zoomOutButton.tick(gc);
		bigMapButton.tick(gc);
		mapView.tick(gc);
		for (GComponent$SidebarButton s : sideButtons) s.tick(gc);
	}

	private void checkSidebarButtons(GComponent c) {
		//Character
		if (c == sideButtons.get(0)) {
			if (!dialogStatus.isVisible()) {
				dialogStatus = new GComponent$StatusDialog(new Vector2f(dialogStatus.getPosition()));
				MedievalLauncher.getInstance().getGameState().getMenuOverlay().addComponent(dialogStatus);
			} else {
				dialogStatus.dispose();
			}
		}

		//Inventory
		if (c == sideButtons.get(1)) {
			if (!dialogInventory.isVisible()) {
				dialogInventory = new GComponent$InventoryDialog(new Vector2f(dialogInventory.getPosition()));
				MedievalLauncher.getInstance().getGameState().getMenuOverlay().addComponent(dialogInventory);
			} else {
				dialogInventory.dispose();
			}
		}

		//Skill
		if (c == sideButtons.get(2)) {
			if (!dialogSkill.isVisible()) {
				dialogSkill = new GComponent$SkillDialog(new Vector2f(dialogSkill.getPosition()));
				MedievalLauncher.getInstance().getGameState().getMenuOverlay().addComponent(dialogSkill);
			} else {
				dialogSkill.dispose();
			}
		}

		//Journal
		if (c == sideButtons.get(3)) {
			if (!dialogJournal.isVisible()) {
				dialogJournal = new GComponent$JournalDialog(new Vector2f(dialogJournal.getPosition()));
				MedievalLauncher.getInstance().getGameState().getMenuOverlay().addComponent(dialogJournal);
			} else {
				dialogJournal.dispose();
			}
		}

		//Game settings/menu
		if (c == sideButtons.get(4)) {
			GGameOverlayMenu$OptionMenu menu = new GGameOverlayMenu$OptionMenu();
			MedievalLauncher.getInstance().getGameState().getMenuManager().addMenu(menu);
		}
	}

	@Override
	public void componentClicked(GComponent c) {
		checkSidebarButtons(c);

		if (c.equals(zoomInButton)) {

		}

		if (c.equals(zoomOutButton)) {

		}

		if (c.equals(bigMapButton)) {

		}


	}

	@Override
	public void componentHovered(GComponent c) {
	}

	@Override
	public void titleBarClicked(GComponent c) {
		c.getParent().setComponentTopPriority(c);
	}

	private class GComponent$SidebarButton extends GComponent$Button {

		private Image connectorImage;

		private Image iconActive;
		private Image iconPressed;

		public GComponent$SidebarButton(Vector2f pos, Image button, Image buttonPressed) {
			super(pos, button, buttonPressed);
			connectorImage = null;
		}


		@Override
		public void render(Graphics g) {
			super.render(g);
			if (connectorImage != null) connectorImage.draw(position.x + 12, position.y - connectorImage.getHeight());

			switch (currentState) {
				case PRESSED:
					if (iconActive != null) iconPressed.draw(position.x + 7, position.y + 7);
					break;
				default:
					if (iconPressed != null) iconActive.draw(position.x + 5, position.y + 5);
					break;
			}
		}

		public GComponent$SidebarButton setConnectorImage(Image connectorImage) {
			this.connectorImage = connectorImage;
			return this;
		}

		public void setIcon(Image active, Image pressed) {
			this.iconActive = active;
			this.iconPressed = pressed;
		}
	}


}
