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


	private Image walkingForwards;
	private Image walkingBackwards;
	private Image walkingLeft;
	private Image walkingRight;


	private float          movementSpeed;
	private boolean        isMovingUp;
	private boolean        isMovingDown;
	private boolean        isMovingLeft;
	private boolean        isMovingRight;
	private WorldDirection lastDirection;


	public Player() {
		this(new Vector2f(0, 0));
	}

	public Player(Vector2f pos) {
		super(pos);

		walkingForwards = Textures.SpriteSheets.PLAYER.getSprite(0, 1);
		walkingBackwards = Textures.SpriteSheets.PLAYER.getSprite(1, 0);
		walkingLeft = Textures.SpriteSheets.PLAYER.getSprite(1, 1);
		walkingRight = Textures.SpriteSheets.PLAYER.getSprite(0, 0);

		isMovingUp = false;
		isMovingDown = false;
		isMovingRight = false;
		isMovingLeft = false;

		lastDirection = WorldDirection.LEFT;
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

		moveCharacter(delta);
	}

	private void renderCharacter(Graphics g, Camera camera) {
        camera.tick();

		if (isMovingDown) {
			walkingBackwards.draw(position.x + camera.getOffsetX(), position.y + camera.getOffsetY());

		} else if (isMovingUp) {
			walkingForwards.draw(position.x + camera.getOffsetX(), position.y + camera.getOffsetY());

		} else if (isMovingRight) {
			walkingRight.draw(position.x + camera.getOffsetX(), position.y + camera.getOffsetY());

		} else if (isMovingLeft) {
			walkingLeft.draw(position.x + camera.getOffsetX(), position.y + camera.getOffsetY());

		}

		if (!isMovingUp && !isMovingDown && !isMovingRight && !isMovingLeft) {
			switch (lastDirection) {
				case UP:
					walkingForwards.draw(position.x + camera.getOffsetX(), position.y + camera.getOffsetY());
					break;

				case DOWN:
					walkingBackwards.draw(position.x + camera.getOffsetX(), position.y + camera.getOffsetY());
					break;

				case LEFT:
					walkingLeft.draw(position.x + camera.getOffsetX(), position.y + camera.getOffsetY());
					break;

				case RIGHT:
					walkingRight.draw(position.x + camera.getOffsetX(), position.y + camera.getOffsetY());
					break;
				default:
					break;
			}
		}

	}

	private void moveCharacter(int delta) {
		if (isMovingUp) {
			lastDirection = WorldDirection.UP;
			position.y -= movementSpeed;
		}
		if (isMovingDown){
			lastDirection = WorldDirection.DOWN;
			position.y += movementSpeed;
		}
		if (isMovingLeft) {
			lastDirection = WorldDirection.LEFT;
			position.x -= movementSpeed;
		}
		if (isMovingRight) {
			lastDirection = WorldDirection.RIGHT;
			position.x += movementSpeed;
		}
	}


}




