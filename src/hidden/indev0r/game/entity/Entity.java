package hidden.indev0r.game.entity;

import hidden.indev0r.game.Camera;
import hidden.indev0r.game.MedievalLauncher;
import hidden.indev0r.game.entity.animation.Action;
import hidden.indev0r.game.entity.animation.ActionSet;
import hidden.indev0r.game.entity.animation.ActionSetDatabase;
import hidden.indev0r.game.entity.animation.ActionType;
import hidden.indev0r.game.map.MapDirection;
import hidden.indev0r.game.map.Tile;
import hidden.indev0r.game.map.TileMap;
import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.Color;
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
	protected Vector2f position;
	protected float    moveX, moveY;
	protected boolean moving    = false;
	protected float targetMoveSpeed = 2f, actualMoveSpeed = targetMoveSpeed;
    protected boolean solid     = true;

	protected TileMap map;

	protected Image sprite, spriteFlipped;
	protected int width, height; //In terms of pixels
	protected              boolean drawShadow  = true;
	protected static final Color   shadowColor = new Color(0f, 0f, 0f, 0.6f);
    private boolean sunken;

    public Entity() {
		this(0, 0);
	}

	public Entity(Vector2f position){
		this(position.x, position.y);
	}

	public Entity(float x, float y) {
		setPosition((int) x, (int) y);
        currentDirection = MapDirection.RIGHT;
	}

    public void setPosition(int x, int y) {
        this.moveX = x * Tile.TILE_SIZE;
        this.moveY = y * Tile.TILE_SIZE;
        this.position = new Vector2f(moveX, moveY);
    }

    public void render(Graphics g) {
		//If entity is not using a motion map
		if (actionMap == null) {
			if (sprite != null) {
				renderShadow(g);
				g.drawImage((currentDirection == MapDirection.RIGHT) ? sprite.getSubImage(0, 0, width, (sunken) ? height / 4 * 3 : height)
                                                                     : spriteFlipped.getSubImage(0, 0, width, (sunken) ? height / 4 * 3 : height),
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
						motion.renderForced(g, this, getRenderX(), getRenderY());
					} else {
						actionPlayStack.pop();
					}
				} else {
					actionPlayStack.pop();
				}

			}

			if (actionPlayStack.isEmpty()) {
				motion = actionMap.get(action);
				if (motion != null) {
					motion.render(g, this, getRenderX(), getRenderY(), moving);
				}
			}
		}
	}

	private float getRenderY() {
		Camera camera = MedievalLauncher.getInstance().getGameState().getCamera();
        float y = position.y + camera.getOffsetY() - Tile.TILE_SIZE / 5;
        if(sunken) y += height / 4;
		return y;
	}

	private float getRenderX() {
		Camera camera = MedievalLauncher.getInstance().getGameState().getCamera();
        float x = position.x + camera.getOffsetX();
		return x;
	}

	private void renderShadow(Graphics g) {

	}

	public void tick(GameContainer gc) {
        if(sunken) {
            actualMoveSpeed = 0.7f * targetMoveSpeed;
        }
        else actualMoveSpeed = targetMoveSpeed;

		if (position.x != moveX) {
			if (position.x > moveX) {
				if (position.x - actualMoveSpeed < moveX) {
					position.x = moveX;
				} else {
					position.x -= actualMoveSpeed;
				}
				action = ActionType.WALK_LEFT;
			}
			if (position.x < moveX) {
				if (position.x + actualMoveSpeed > moveX) {
					position.x = actualMoveSpeed;
				} else {
					position.x += actualMoveSpeed;
				}
				action = ActionType.WALK_RIGHT;
			}
		}

		if (position.y != moveY) {
			if (position.y > moveY) {
				if (position.y - actualMoveSpeed < moveY) {
					position.y = moveY;
				} else {
					position.y -= actualMoveSpeed;
				}
				action = ActionType.WALK_UP;
			}
			if (position.y < moveY) {
				if (position.y + actualMoveSpeed > moveY) {
					position.y = moveY;
				} else {
					position.y += actualMoveSpeed;
				}
				action = ActionType.WALK_DOWN;
			}
		}

		if (Math.abs(moveX - position.x) < targetMoveSpeed) position.x = moveX;
		if (Math.abs(moveY - position.y) < targetMoveSpeed) position.y = moveY;
		if (position.y == moveY && position.x == moveX) {
			moving = false;

            if(actionMap != null) {
                if (action.equals(ActionType.WALK_LEFT)) action = ActionType.STATIC_LEFT;
                if (action.equals(ActionType.WALK_RIGHT)) action = ActionType.STATIC_RIGHT;
                if (action.equals(ActionType.WALK_DOWN)) action = ActionType.STATIC_DOWN;
                if (action.equals(ActionType.WALK_UP)) action = ActionType.STATIC_UP;
            }
		}
	}

	public void move(int x, int y) {
        int oldX = (int) (moveX / Tile.TILE_SIZE);
		int oldY = (int) (moveY / Tile.TILE_SIZE);

        //Get direction
        if(oldX == x && y < oldY)   currentDirection = MapDirection.UP;
        if(oldX == x && y > oldY)   currentDirection = MapDirection.DOWN;
        if(oldX > x && y == oldY)   currentDirection = MapDirection.LEFT;
        if(oldX < x && y == oldY)   currentDirection = MapDirection.RIGHT;

		moving = true;
		moveX = x * Tile.TILE_SIZE;
		moveY = y * Tile.TILE_SIZE;

		map.stepOn(this, oldX, oldY, x, y);
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
	 * Once used, all properties from the action setStat will be cloned.
	 *
	 * @see hidden.indev0r.game.entity.animation.ActionSetDatabase
	 */
	public void setActionSet(ActionSet set) {
		if (set == null) set = ActionSetDatabase.get(0);
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
        currentDirection = MapDirection.RIGHT;
	}

	public Image getSprite() {
		return sprite;
	}

	public Vector2f getPosition() {
		return position;
	}

	public void setDrawShadow(boolean shadow) {
		this.drawShadow = shadow;
	}

    public boolean isSolid() {
        return solid;
    }

    public void setSolid(boolean solid) {
        this.solid = solid;
    }

    public void setSunken(boolean sunken) {
        this.sunken = sunken;
    }

    public boolean isSunken() {
        return sunken;
    }

    public void setFacingDirection(MapDirection direction) {
        currentDirection = direction;
    }

    public MapDirection getCurrentDirection() {
        return currentDirection;
    }
}
