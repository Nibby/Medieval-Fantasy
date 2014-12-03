package hidden.indev0r.core.entity;

import hidden.indev0r.core.Camera;
import hidden.indev0r.core.reference.References;
import hidden.indev0r.core.texture.Textures;
import hidden.indev0r.core.world.WorldDirection;
import org.lwjgl.input.Keyboard;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Vector2f;

/*
    I hope you don't mind me testing some of my own code.
    Although we might be branching soon by the looks of the progress ^_^
 */
public class Player extends Entity {

    private static final int WALK_FORWARD = 0, WALK_BACKWARD = 1, WALK_LEFT = 2, WALK_RIGHT = 3;
    private int walkDirection = WALK_BACKWARD;

    private Image[] sprites;

//	private Image walkingForwards;
//	private Image walkingBackwards;
//	private Image walkingLeft;
//	private Image walkingRight;

	private float          movementSpeed;
	private boolean        isMovingUp;
	private boolean        isMovingDown;
	private boolean        isMovingLeft;
	private boolean        isMovingRight;
//	private WorldDirection lastDirection;


	public Player() {
		this(new Vector2f(0, 0));
	}

	public Player(Vector2f pos) {
		super(pos);

//		walkingForwards = Textures.SpriteSheets.PLAYER.getSprite(0, 1);
//		walkingBackwards = Textures.SpriteSheets.PLAYER.getSprite(1, 0);
//		walkingLeft = Textures.SpriteSheets.PLAYER.getSprite(1, 1);
//		walkingRight = Textures.SpriteSheets.PLAYER.getSprite(0, 0);

        sprites = new Image[] {
                Textures.SpriteSheets.PLAYER.getSprite(0, 1),
                Textures.SpriteSheets.PLAYER.getSprite(1, 0),
                Textures.SpriteSheets.PLAYER.getSprite(1, 1),
                Textures.SpriteSheets.PLAYER.getSprite(0, 0),
        };

		isMovingUp = false;
		isMovingDown = false;
		isMovingRight = false;
		isMovingLeft = false;

//		lastDirection = WorldDirection.LEFT;
	}

	@Override
	public void draw(Graphics g, Camera camera) {
		renderCharacter(g, camera);
	}

	@Override
	public void tick(int delta) {
		isMovingUp = Keyboard.isKeyDown(Keyboard.KEY_W);
		isMovingDown = Keyboard.isKeyDown(Keyboard.KEY_S);
		isMovingLeft = Keyboard.isKeyDown(Keyboard.KEY_A);
		isMovingRight = Keyboard.isKeyDown(Keyboard.KEY_D);
        movementSpeed = .6f * References.DRAW_SCALE;

        if(isMovingUp) {
            walkDirection = WALK_FORWARD;
        } else if(isMovingDown) {
            walkDirection = WALK_BACKWARD;
        } else if(isMovingLeft) {
            walkDirection = WALK_LEFT;
        } else if(isMovingRight) {
            walkDirection = WALK_RIGHT;
        }

		moveCharacter(delta);
	}

	private void renderCharacter(Graphics g, Camera camera) {
        camera.tick();
        sprites[walkDirection].draw(position.x + camera.getOffsetX(), position.y + camera.getOffsetY());

//        walkingForwards.draw(position.x + camera.getOffsetX(), position.y + camera.getOffsetY());
//
//		if (isMovingDown) {
//			walkingBackwards.draw(position.x, position.y);
//			lastDirection = WorldDirection.DOWN;
//		} else if (isMovingUp) {
//			walkingForwards.draw(position.x, position.y);
//			lastDirection = WorldDirection.UP;
//		} else if (isMovingRight) {
//			walkingRight.draw(position.x, position.y);
//			lastDirection = WorldDirection.RIGHT;
//		} else if (isMovingLeft) {
//			walkingLeft.draw(position.x, position.y);
//			lastDirection = WorldDirection.LEFT;
//		}
//
//		if (!isMovingUp && !isMovingDown && !isMovingRight && !isMovingLeft) {
//			switch (lastDirection) {
//				case UP:
//					walkingForwards.draw(position.x, position.y);
//					break;
//
//				case DOWN:
//					walkingBackwards.draw(position.x, position.y);
//					break;
//
//				case LEFT:
//					walkingLeft.draw(position.x, position.y);
//					break;
//
//				case RIGHT:
//					walkingRight.draw(position.x, position.y);
//					break;
//				default:
//					break;
//			}
//		}

	}

	private void moveCharacter(int delta) {
		if (isMovingUp) position.y -= movementSpeed;
		if (isMovingDown) position.y += movementSpeed;
		if (isMovingLeft) position.x -= movementSpeed;
		if (isMovingRight) position.x += movementSpeed;
	}


}




