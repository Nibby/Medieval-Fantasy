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
import hidden.indev0r.game.reference.References;
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
	protected Map<ActionType, Action> actionMap = null; //The map of all entity actions
	protected ActionType forceActAction = null;
	protected ActionType action = null;
	protected Vector2f position;
	protected float moveX, moveY;
	protected boolean stopMoving = false, moving = false;
	protected float targetMoveSpeed = 2f, actualMoveSpeed = targetMoveSpeed;
    protected boolean solid = true;

	protected TileMap map;

	protected Image sprite, spriteFlipped;
	protected int width, height; //In terms of pixels
	protected boolean drawShadow  = true;
	protected static final Color shadowColor = new Color(0f, 0f, 0f, 0.6f);
    protected Color textureColor = new Color(1f, 1f, 1f, 1f);
    private boolean sunken;

    protected Vector2f moveDestination = new Vector2f(0, 0);

    public Entity(Entity e) {
        this.actionMap = e.actionMap;
        this.forceActAction = e.forceActAction;
        this.action = e.action;
        this.position = new Vector2f(e.position);
        this.moveX = e.moveX;
        this.moveY = e.moveY;
        this.stopMoving = e.stopMoving;
        this.moving = e.moving;
        this.targetMoveSpeed = e.targetMoveSpeed;
        this.actualMoveSpeed = e.actualMoveSpeed;
        this.solid = e.solid;
        this.currentDirection = e.currentDirection;

        this.map = e.map;
        this.sprite = e.sprite;
        this.spriteFlipped = e.spriteFlipped;
        this.width = e.width;
        this.height = e.height;
        this.drawShadow = e.drawShadow;
        this.textureColor = new Color(e.textureColor);
        this.sunken = e.sunken;
        this.moveDestination = new Vector2f(e.moveDestination);
    }

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
        setMoveDestination(x, y);
    }

    public void render(Graphics g) {
		render(g, getRenderX(), getRenderY(), textureColor);
	}

    public void render(Graphics g, Color tint) {
        render(g, getRenderX(), getRenderY(), tint);
    }

    public void render(Graphics g, float x, float y, Color tint) {
        //If entity is not using a motion map
        if (actionMap == null) {
            if (sprite != null) {
                renderShadow(g);
                Image renderImg = (currentDirection == MapDirection.RIGHT) ? sprite : spriteFlipped;
                renderImg = renderImg.getScaledCopy(width, height);
                if(sunken) renderImg = renderImg.getSubImage(0, 0, renderImg.getWidth(), renderImg.getHeight() / 4 * 3);
                g.drawImage(renderImg, x, y, tint);
            }
        }

        //Otherwise
        else {
            renderShadow(g);
            //If entity is forced to act out an animation, do so
            if(forceActAction != null) {
                Action action = actionMap.get(forceActAction);
                if(action != null) {
                    if(!action.hasEnded())
                        action.renderForced(g, this, tint, x, y);
                    else
                        forceActAction = null;
                }
            }

            if(forceActAction == null) {
                Action act = actionMap.get(action);
                if (act != null) {
                    act.render(g, this, x, y, tint, true);
                }
            }
        }
    }

	public float getRenderY() {
		Camera camera = MedievalLauncher.getInstance().getGameState().getCamera();
        float y = position.y + camera.getOffsetY();
        if(sunken) y += height / 4;
		return y;
	}

	public float getRenderX() {
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

        Camera camera = MedievalLauncher.getInstance().getGameState().getCamera();
        Entity e = camera.getTrackingEntity();
        if (position.x != moveX) {

            if (position.x > moveX) {
                position.x -= actualMoveSpeed;
                setFacingDirection(MapDirection.LEFT);
                action = ActionType.WALK_LEFT;
            }
            if (position.x < moveX) {
                position.x += actualMoveSpeed;
                setFacingDirection(MapDirection.RIGHT);
                action = ActionType.WALK_RIGHT;
            }

            if(e != null && e.equals(this))
                camera.tick();
		}
		if (position.y != moveY) {


            if (position.y > moveY) {
                position.y -= actualMoveSpeed;
                setFacingDirection(MapDirection.UP);
                action = ActionType.WALK_UP;
            }
            if (position.y < moveY) {
                position.y += actualMoveSpeed;
                setFacingDirection(MapDirection.DOWN);
                action = ActionType.WALK_DOWN;
            }

            if(e != null && e.equals(this))
                camera.tick();
		}


            if (Math.abs(moveX - position.x) < targetMoveSpeed) position.x = moveX;
		if (Math.abs(moveY - position.y) < targetMoveSpeed) position.y = moveY;
		if (position.y == moveY && position.x == moveX) {
            if(!moving && actionMap != null && getX() == moveDestination.x && getY() == moveDestination.y) {
                if (currentDirection.equals(MapDirection.LEFT)) action = ActionType.STATIC_LEFT;
                if (currentDirection.equals(MapDirection.RIGHT)) action = ActionType.STATIC_RIGHT;
                if (currentDirection.equals(MapDirection.UP)) action = ActionType.STATIC_UP;
                if (currentDirection.equals(MapDirection.DOWN)) action = ActionType.STATIC_DOWN;
            }
            moving = false;
        }
	}

	public void move(int x, int y) {
        if(map == null) return;
        if(map.isBlocked(this, x, y)) return;

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

	public void setSize(int width, int height) {
		this.width = width;
		this.height = height;

		if (sprite != null) {
			sprite = sprite.getScaledCopy(width, height);
            spriteFlipped = sprite.getFlippedCopy(true, false);
		}
	}

	public void forceActAction(ActionType id) {
		Action action = actionMap.get(id);
		if (action == null) return;
		action.restart();
		this.forceActAction = id;
	}

    public boolean isVisibleOnScreen() {
        Camera camera = MedievalLauncher.getInstance().getGameState().getCamera();
        return (getPosition().x + width + camera.getOffsetX() + 2 * Tile.TILE_SIZE > 0
                && getPosition().y + camera.getOffsetY() + height  + 2 * Tile.TILE_SIZE > 0 &&
                getPosition().x + width + camera.getOffsetX() - 2 * Tile.TILE_SIZE < References.GAME_WIDTH
                && getPosition().y + height + camera.getOffsetY() - 2 * Tile.TILE_SIZE < References.GAME_HEIGHT);
    }

    public Vector2f getMoveDestination() {
        return moveDestination;
    }


    public void setMoveDestination(float x, float y) {
        if(moveDestination == null) {
            moveDestination = new Vector2f(x, y);
            return;
        }
        if(map != null && map.isBlocked(this, (int) x, (int) y)) return;
        moveDestination.x = x;
        moveDestination.y = y;
    }

    public Color getTextureColor() {
        return textureColor;
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
        if(forceActAction == null) {
            if(!moving) {
                switch(direction) {
                    case UP:
                        action = ActionType.STATIC_UP;
                        break;
                    case DOWN:
                        action = ActionType.STATIC_DOWN;
                        break;
                    case LEFT:
                        action = ActionType.STATIC_LEFT;
                        break;
                    case RIGHT:
                        action = ActionType.STATIC_RIGHT;
                        break;
                }
            }
        }
    }

    public MapDirection getCurrentDirection() {
        return currentDirection;
    }

    public Vector2f[] getBlockedTiles() {

        Vector2f[] result = new Vector2f[(width * height) / Tile.TILE_SIZE];

        int index = 0;
        for(int x = (int) getX(); x <= (int) getX() + width / Tile.TILE_SIZE - 1; x++) {
            for(int y = (int) getY(); y <= (int) getY() + height / Tile.TILE_SIZE - 1; y++) {
                result[index] = new Vector2f(x, y);

                index++;
            }
        }
        return result;
    }

    public Map<ActionType, Action> getActionMap() {
        return actionMap;
    }

    public TileMap getMap() {
        return map;
    }

    public boolean isMoving() {
        return moving;
    }
}
