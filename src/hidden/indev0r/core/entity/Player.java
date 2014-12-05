package hidden.indev0r.core.entity;

import hidden.indev0r.core.Camera;
import hidden.indev0r.core.entity.animation.ActionSetDatabase;
import hidden.indev0r.core.entity.animation.ActionType;
import hidden.indev0r.core.maps.MapDirection;
import hidden.indev0r.core.texture.Textures;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SpriteSheet;

/*
    I hope you don't mind me testing some of my own code.
    Although we might be branching soon by the looks of the progress ^_^
 */
public class Player extends Entity {


    public Player(int x, int y) {
        super(x, y);

        SpriteSheet spriteSheet = Textures.SpriteSheets.ANIM_MAGE_0;

        setSize(32, 32);
    }

    @Override
	public void render(Graphics g, Camera camera) {
        super.render(g, camera);


        if(motionMap == null) {
            //Placeholder
            setActionSet(ActionSetDatabase.get(0));
        }
	}

	@Override
	public void tick(GameContainer gc) {
        super.tick(gc);

        Input input = gc.getInput();
        if(!moving) {
            int x = (int) getX();
            int y = (int) getY();
            
            if(input.isKeyDown(Input.KEY_W)) {
                setMotion(ActionType.WALK_UP);
                moving = true;
                if(!map.isBlocked(x, y - 1)) {
                    move(x, y - 1);
                }
            }
            if(input.isKeyDown(Input.KEY_A)) {
                currentDirection = MapDirection.LEFT;
                setMotion(ActionType.WALK_LEFT);
                moving = true;
                if(!map.isBlocked(x - 1, y)) {
                    move(x - 1, y);
                }
            }
            if(input.isKeyDown(Input.KEY_S)) {
                setMotion(ActionType.WALK_DOWN);
                moving = true;
                if(!map.isBlocked(x, y + 1)) {
                    move(x, y + 1);
                }
            }
            if(input.isKeyDown(Input.KEY_D)) {
	            currentDirection = MapDirection.RIGHT;
	            setMotion(ActionType.WALK_RIGHT);
                moving = true;
                if(!map.isBlocked(x + 1, y)) {
                    move(x + 1, y);
                }
            }

            if(input.isKeyPressed(Input.KEY_F)) {
                action = ActionType.ATTACK_LEFT;
            }
        }
	}

    @Override
    public void move(int x, int y) {
        super.move(x, y);
    }

}




