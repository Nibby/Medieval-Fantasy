package hidden.indev0r.core.entity;

import hidden.indev0r.core.Camera;
import hidden.indev0r.core.entity.animation.ActionID;
import hidden.indev0r.core.entity.animation.WizardActionSet;
import hidden.indev0r.core.maps.TileMap;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;

/*
    I hope you don't mind me testing some of my own code.
    Although we might be branching soon by the looks of the progress ^_^
 */
public class Player extends Entity {


    public Player(int x, int y) {
        super(x, y);

        //Placeholder
        setActionSet(new WizardActionSet());
        setSize(32, 32);
    }

    @Override
	public void render(Graphics g, Camera camera) {
        super.render(g, camera);
	}

	@Override
	public void tick(GameContainer gc) {
        super.tick(gc);

        Input input = gc.getInput();
        if(!moving) {
            int x = (int) getX();
            int y = (int) getY();
            
            if(input.isKeyDown(Input.KEY_W)) {
                setMotion(ActionID.WALK_UP);
                moving = true;
                if(!map.isBlocked(x, y - 1)) {
                    move(x, y - 1);
                }
            }
            if(input.isKeyDown(Input.KEY_A)) {
                facing = FACING_LEFT;
                setMotion(ActionID.WALK_LEFT);
                moving = true;
                if(!map.isBlocked(x - 1, y)) {
                    move(x - 1, y);
                }
            }
            if(input.isKeyDown(Input.KEY_S)) {
                setMotion(ActionID.WALK_DOWN);
                moving = true;
                if(!map.isBlocked(x, y + 1)) {
                    move(x, y + 1);
                }
            }
            if(input.isKeyDown(Input.KEY_D)) {
                facing = FACING_RIGHT;
                setMotion(ActionID.WALK_RIGHT);
                moving = true;
                if(!map.isBlocked(x + 1, y)) {
                    move(x + 1, y);
                }
            }

            if(input.isKeyPressed(Input.KEY_F)) {
                action = ActionID.ATTACK_LEFT;
            }
        }
	}

    @Override
    public void move(int x, int y) {
        super.move(x, y);
    }

}




