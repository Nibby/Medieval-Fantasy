package hidden.indev0r.core.entity;

import hidden.indev0r.core.Camera;
import hidden.indev0r.core.MedievalLauncher;
import hidden.indev0r.core.entity.animation.Action;
import hidden.indev0r.core.entity.animation.ActionSet;
import hidden.indev0r.core.entity.animation.ActionType;
import hidden.indev0r.core.maps.MapDirection;
import hidden.indev0r.core.maps.Tile;
import hidden.indev0r.core.maps.TileMap;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public abstract class Entity {

	protected MapDirection currentDirection = MapDirection.RIGHT;

	/**
	 * Entity sprite is done in two ways: 1. Through a static sprite 2. Through use of a more animated series of sprites known as motions
	 * <p/>
	 * By default, the entity uses static sprites. This map is null (i.e. not used) if entity has no motion map.
	 */
	protected Map<ActionType, Action> actionMap       = null; //The map of all entity actions
	protected Stack<ActionType>       actionPlayStack = new Stack<>();        //A stack of animation which the entity is forced to act out
	protected ActionType              action          = null;
	protected float currentX, currentY;
	protected float moveX, moveY;
	protected boolean moving    = false;
	protected float   moveSpeed = 2f;

	protected TileMap map;

	protected Image sprite, spriteFlipped;
	protected int width, height; //In terms of pixels
	protected              boolean                 drawShadow  = true;
	protected static final org.newdawn.slick.Color shadowColor = new org.newdawn.slick.Color(0f, 0f, 0f, 0.6f);

	public Entity() {
		this(0, 0);
	}

	public Entity(float x, float y) {
		this.currentX = x * Tile.TILE_SIZE;
		this.currentY = y * Tile.TILE_SIZE;
		this.moveX = x * Tile.TILE_SIZE;
		this.moveY = y * Tile.TILE_SIZE;
	}

	public void render(Graphics g) {
		//If entity is not using a motion map
		if (actionMap == null) {
			if (sprite != null) {
				renderShadow(g);
				g.drawImage((currentDirection == MapDirection.RIGHT) ? sprite : spriteFlipped,
						getRenderX(), getRenderY());
			}
		}

		//Otherwise
		else {
			renderShadow(g);

			Action motion = null;
			//If entity is forced to act out an animation, do so
			if (!actionPlayStack.isEmpty()) {
				motion = actionMap.get(actionPlayStack.peek());
				if (motion != null) {
					if (!motion.hasEnded()) {
						motion.renderForced(g, getRenderX(), getRenderY());
					} else {
						actionPlayStack.pop();
					}
				} else {
					actionPlayStack.pop();
				}

			} else {
				motion = actionMap.get(action);
				if (motion == null) {
					actionMap.get(ActionType.STATIC_RIGHT).render(g, getRenderX(), getRenderY(), false);
				} else {
					motion.render(g, getRenderX(), getRenderY(), moving);
				}
			}
		}
	}

	private float getRenderY() {
		Camera camera = MedievalLauncher.getInstance().getGameState().getCamera();
		return currentY + camera.getOffsetY() - Tile.TILE_SIZE / 5;
	}

	private float getRenderX() {
		Camera camera = MedievalLauncher.getInstance().getGameState().getCamera();
		return currentX + camera.getOffsetX();
	}

	private void renderShadow(Graphics g) {

	}

	public void tick(GameContainer gc) {
		if (currentX != moveX) {
			if (currentX > moveX) {
				if (currentX - moveSpeed < moveX) {
					currentX = moveX;
				} else {
					currentX -= moveSpeed;
				}
				action = ActionType.WALK_LEFT;
			}
			if (currentX < moveX) {
				if (currentX + moveSpeed > moveX) {
					currentX = moveSpeed;
				} else {
					currentX += moveSpeed;
				}
				action = ActionType.WALK_RIGHT;
			}
		}

		if (currentY != moveY) {
			if (currentY > moveY) {
				if (currentY - moveSpeed < moveY) {
					currentY = moveY;
				} else {
					currentY -= moveSpeed;
				}
				action = ActionType.WALK_UP;
			}
			if (currentY < moveY) {
				if (currentY + moveSpeed > moveY) {
					currentY = moveY;
				} else {
					currentY += moveSpeed;
				}
				action = ActionType.WALK_DOWN;
			}
		}

		if (Math.abs(moveX - currentX) < moveSpeed) currentX = moveX;
		if (Math.abs(moveY - currentY) < moveSpeed) currentY = moveY;
		if (currentY == moveY && currentX == moveX) {
			moving = false;
		}
	}

	public void move(int x, int y) {
		int oldX = (int) (moveX / Tile.TILE_SIZE);
		int oldY = (int) (moveY / Tile.TILE_SIZE);

		moving = true;
		moveX = x * Tile.TILE_SIZE;
		moveY = y * Tile.TILE_SIZE;

		map.stepOn(this, x, y, oldX, oldY);
	}

	public void setMotion(ActionType action) {
		this.action = action;
	}

	public void setSize(int width, int height) {
		this.width = width;
		this.height = height;

		if (sprite != null) {
			sprite = sprite.getScaledCopy(width, height);
		}
	}

	public void forceActAction(ActionType id) {
		Action action = actionMap.get(id);
		if (action == null) return;
		action.restart();
		actionPlayStack.add(0, id);
	}

	/**
	 * EntityActionSets are pre-defined sets of animation for use on this entity.
	 * <p/>
	 * Once used, all properties from the action set will be cloned.
	 *
	 * @see hidden.indev0r.core.entity.animation.ActionSetDatabase
	 */
	public void setActionSet(ActionSet set) {
		actionMap = new HashMap<>();
		set.applyAll(actionMap);

		action = ActionType.STATIC_RIGHT;
	}

	public void setCurrentMap(TileMap map) {
		this.map = map;
	}

	public float getX() {
		return moveX / Tile.TILE_SIZE;
	}

	public float getY() {
		return moveY / Tile.TILE_SIZE;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public void setSprite(Image sprite) {
		this.sprite = sprite;
		spriteFlipped = sprite.getFlippedCopy(true, false);
	}

	public Image getSprite() {
		return sprite;
	}

	public float getCurrentY() {
		return currentY;
	}

	public float getCurrentX() {
		return currentX;
	}

	public void setDrawShadow(boolean shadow) {
		this.drawShadow = shadow;
	}
}
