package hidden.indev0r.core.entity;

import hidden.indev0r.core.Camera;
import hidden.indev0r.core.entity.animation.ActionID;
import hidden.indev0r.core.entity.animation.ActionMotion;
import hidden.indev0r.core.entity.animation.EntityActionSet;
import hidden.indev0r.core.maps.Tile;
import hidden.indev0r.core.maps.TileMap;
import hidden.indev0r.core.texture.Textures;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.Graphics;

import java.util.HashMap;
import java.util.Map;

public abstract class Entity {

    public static final int FACING_LEFT = 0, FACING_RIGHT = 1;
    protected int facing = FACING_RIGHT;

    /**
     * Entity sprite is done in two ways:
     * 1. Through a static sprite
     * 2. Through use of a more animated series of sprites known as motions
     *
     * By default, the entity uses static sprites.
     * This map is null (i.e. not used) if entity has no motion map.
     */
    private Map<ActionID, ActionMotion> motionMap = null;
    protected ActionID action = null;
    protected boolean forcedMotion = false;

	protected float currentX, currentY;
    protected float moveX, moveY;
    protected boolean moving = false;
    protected float moveSpeed = 2f;

    protected TileMap map;

    protected Image sprite, spriteFlipped;
    protected int width, height; //In terms of pixels
    protected boolean drawShadow = true;
    protected static final org.newdawn.slick.Color shadowColor = new org.newdawn.slick.Color(0f, 0f, 0f, 0.6f);

	public Entity(){
		this(0, 0);
	}

	public Entity(float x, float y) {
		this.currentX = x * Tile.TILE_SIZE;
        this.currentY = y * Tile.TILE_SIZE;
        this.moveX = x * Tile.TILE_SIZE;
        this.moveY = y * Tile.TILE_SIZE;
	}

	public void render(Graphics g, Camera camera) {
        //If entity is not using a motion map
        if(motionMap == null) {
            if(sprite != null) {
                renderShadow(g, camera);
                g.drawImage((facing == FACING_RIGHT) ? sprite : spriteFlipped,
                        getRenderX(camera), getRenderY(camera));
            }
        }

        //Otherwise
        else {
            renderShadow(g, camera);
            if(forcedMotion) {
                ActionMotion motion = motionMap.get(action);
                if(motion != null) {
                    if(!motion.hasEnded())
                        motion.renderForced(g, getRenderX(camera), getRenderY(camera));
                    } else {
                    forcedMotion = false;
                }
            } else {
                ActionMotion motion = motionMap.get(action);
                if(motion == null)
                    motionMap.get(ActionID.STATIC).render(g, getRenderX(camera), getRenderY(camera), false);
                else {
                    motion.render(g, getRenderX(camera), getRenderY(camera), moving);
                }
            }
        }
    }

    private float getRenderY(Camera camera) {
        return currentY + camera.getOffsetY() - Tile.TILE_SIZE / 5;
    }

    private float getRenderX(Camera camera) {
        return currentX + camera.getOffsetX();
    }

    private void renderShadow(Graphics g, Camera camera) {

    }

    public void tick(GameContainer gc) {
        if(currentX != moveX) {
           if(currentX > moveX) {
               if(currentX - moveSpeed < moveX)
                   currentX = moveX;
               else
                   currentX -= moveSpeed;
               action = ActionID.WALK_LEFT;
           }
            if(currentX < moveX) {
                if(currentX + moveSpeed > moveX)
                    currentX = moveSpeed;
                else
                    currentX += moveSpeed;
                action = ActionID.WALK_RIGHT;
            }
        }

        if(currentY != moveY) {
            if(currentY > moveY) {
                if(currentY - moveSpeed < moveY)
                    currentY = moveY;
                else
                    currentY -= moveSpeed;
                action = ActionID.WALK_UP;
            }
            if(currentY < moveY) {
                if(currentY + moveSpeed > moveY)
                    currentY = moveY;
                else
                    currentY += moveSpeed;
                action = ActionID.WALK_DOWN;
            }
        }

        if(Math.abs(moveX - currentX) < moveSpeed) currentX = moveX;
        if(Math.abs(moveY - currentY) < moveSpeed) currentY = moveY;
        if(currentY == moveY && currentX == moveX) {
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

    public void setMotion(ActionID action) {
        if(!forcedMotion)
            this.action = action;
    }

    public void setSize(int width, int height) {
        this.width = width;
        this.height = height;

        if(sprite != null)
            sprite = sprite.getScaledCopy(width, height);
    }

    public void forceActMotion(ActionID id) {
        action = id;
        forcedMotion = true;
    }

    /**
     * EntityActionSets are pre-defined sets of animation for
     * use on this entity.
     *
     * Once used, all properties from the action set will be
     * cloned.
     *
     * @see hidden.indev0r.core.entity.animation.EntityActionSet
     */
    public void setActionSet(EntityActionSet set) {
        motionMap = new HashMap<>();
        set.applyAll(motionMap);

        action = ActionID.STATIC;
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

    public Image getSprite() { return sprite; }

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