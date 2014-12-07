package hidden.indev0r.core.entity;

import hidden.indev0r.core.entity.animation.ActionSetDatabase;
import hidden.indev0r.core.entity.animation.ActionType;
import hidden.indev0r.core.map.MapDirection;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;

import javax.swing.*;

/*
    I hope you don't mind me testing some of my own code.
    Although we might be branching soon by the looks of the progress ^_^
 */
public class Player extends Entity {


	public Player(int x, int y) {
		super(x, y);

        setActionSet(ActionSetDatabase.get(11));
		setSize(32, 32);
	}

	@Override
	public void render(Graphics g) {
		super.render(g);
	}

	@Override
	public void tick(GameContainer gc) {
		super.tick(gc);

		Input input = gc.getInput();
		if (!moving) {
			int x = (int) getX();
			int y = (int) getY();

			if (input.isKeyDown(Input.KEY_W)) {
                currentDirection = MapDirection.UP;
				setMotion(ActionType.WALK_UP);
				moving = true;
				if (!map.isBlocked(x, y - 1)) {
					move(x, y - 1);
				}
			}
			if (input.isKeyDown(Input.KEY_A)) {
				currentDirection = MapDirection.LEFT;
				setMotion(ActionType.WALK_LEFT);
				moving = true;
				if (!map.isBlocked(x - 1, y)) {
					move(x - 1, y);
				}
			}
			if (input.isKeyDown(Input.KEY_S)) {
                currentDirection = MapDirection.DOWN;
				setMotion(ActionType.WALK_DOWN);
				moving = true;
				if (!map.isBlocked(x, y + 1)) {
					move(x, y + 1);
				}
			}
			if (input.isKeyDown(Input.KEY_D)) {
				currentDirection = MapDirection.RIGHT;
				setMotion(ActionType.WALK_RIGHT);
				moving = true;
				if (!map.isBlocked(x + 1, y)) {
					move(x + 1, y);
				}
			}

            //Debug
			if (input.isKeyPressed(Input.KEY_F1)) {
				forceActAction(ActionType.ATTACK_RIGHT);
			}
            if (input.isKeyPressed(Input.KEY_F2)) {
                forceActAction(ActionType.ATTACK_LEFT);
            }
            if (input.isKeyPressed(Input.KEY_F3)) {
                forceActAction(ActionType.ATTACK_UP);
            }
            if (input.isKeyPressed(Input.KEY_F4)) {
                forceActAction(ActionType.ATTACK_DOWN);
            }

            if (input.isKeyPressed(Input.KEY_F5)) {
                forceActAction(ActionType.CAST_RIGHT);
            }
            if (input.isKeyPressed(Input.KEY_F6)) {
                forceActAction(ActionType.CAST_LEFT);
            }
            if (input.isKeyPressed(Input.KEY_F7)) {
                forceActAction(ActionType.CAST_UP);
            }
            if (input.isKeyPressed(Input.KEY_F8)) {
                forceActAction(ActionType.CAST_DOWN);
            }

            if (input.isKeyPressed(Input.KEY_F9)) {
                forceActAction(ActionType.USE_SPECIAL);
            }
            if (input.isKeyPressed(Input.KEY_F10)) {
                forceActAction(ActionType.DEATH);
            }

            if (input.isKeyPressed(Input.KEY_F12)) {
                String option = JOptionPane.showInputDialog(null, "Enter action set id: ");
                if(option != null && !option.isEmpty() && !option.contains(" ")) {
                    setActionSet(ActionSetDatabase.get(Integer.parseInt(option)));
                }
            }
		}
	}

	@Override
	public void move(int x, int y) {
		super.move(x, y);
	}

}




