package hidden.indev0r.core.entity;

import hidden.indev0r.core.texture.Textures;
import hidden.indev0r.core.world.WorldDirection;
import javafx.animation.Animation;
import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

public class Player extends Entity {


	private Image walkingForwards;
	private Image walkingBackwards;
	private Image walkingLeft;
	private Image walkingRight;

	private float   movementSpeed;
	private boolean isMovingUp;
	private boolean isMovingDown;
	private boolean isMovingLeft;
	private boolean isMovingRight;

	private WorldDirection lastDirection;
	//TODO: Review code for redundancy
	public Player(Image sprite) {
		super(sprite);
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
	public void draw(Graphics g) {
		renderCharacter(g);
	}

	@Override
	public void tick(int delta) {
		isMovingUp = Keyboard.isKeyDown(Keyboard.KEY_W); // position.y -= .5;
		isMovingDown = Keyboard.isKeyDown(Keyboard.KEY_S); // position.y += .5;
		isMovingLeft = Keyboard.isKeyDown(Keyboard.KEY_A); // position.x -= .5;
		isMovingRight = Keyboard.isKeyDown(Keyboard.KEY_D); // position.x += .5;
		movementSpeed = .08f;
		moveCharacter(delta);
	}

	private void renderCharacter(Graphics g) {
		if (isMovingDown) {
			walkingBackwards.draw(position.x, position.y);
			lastDirection = WorldDirection.DOWN;
		} else if (isMovingUp) {
			walkingForwards.draw(position.x, position.y);
			lastDirection = WorldDirection.UP;
		} else if (isMovingRight) {
			walkingRight.draw(position.x, position.y);
			lastDirection = WorldDirection.RIGHT;
		} else if (isMovingLeft) {
			walkingLeft.draw(position.x, position.y);
			lastDirection = WorldDirection.LEFT;
		}

		if (!isMovingUp && !isMovingDown && !isMovingRight && !isMovingLeft) {
			switch (lastDirection) {
				case UP:
					walkingForwards.draw(position.x, position.y);
					break;

				case DOWN:
					walkingBackwards.draw(position.x, position.y);
					break;

				case LEFT:
					walkingLeft.draw(position.x, position.y);
					break;

				case RIGHT:
					walkingRight.draw(position.x, position.y);
					break;
				default:
					break;
			}
		}


	}

	private void moveCharacter(int delta) {
		if (isMovingUp) position.y -= movementSpeed;
		if (isMovingDown) position.y += movementSpeed;
		if (isMovingLeft) position.x -= movementSpeed;
		if (isMovingRight) position.x += movementSpeed;
	}


}




