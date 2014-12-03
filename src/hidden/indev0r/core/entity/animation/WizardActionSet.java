package hidden.indev0r.core.entity.animation;

import hidden.indev0r.core.texture.Textures;

/**
 * Created by MrDeathJockey on 14/12/4.
 *
 * For wizards.
 */
public class WizardActionSet extends EntityActionSet {

    public WizardActionSet() {
        super(0);
    }

    @Override
    protected void initMotionList() {
        add(new ActionMotion(ActionID.STATIC)
                .addFrame(Textures.SpriteSheets.PLAYER.getSprite(0, 0), 1000));

        add(new ActionMotion(ActionID.WALK_RIGHT)
                .addFrame(Textures.SpriteSheets.PLAYER.getSprite(0, 0), 350)
                .addFrame(Textures.SpriteSheets.PLAYER.getSprite(1, 0), 350));

        add(new ActionMotion(ActionID.WALK_LEFT)
                .addFrame(Textures.SpriteSheets.PLAYER.getSprite(0, 3), 350)
                .addFrame(Textures.SpriteSheets.PLAYER.getSprite(1, 3), 350));

        add(new ActionMotion(ActionID.WALK_DOWN)
                .addFrame(Textures.SpriteSheets.PLAYER.getSprite(1, 1), 350)
                .addFrame(Textures.SpriteSheets.PLAYER.getSprite(2, 1), 350));

        add(new ActionMotion(ActionID.WALK_UP)
                .addFrame(Textures.SpriteSheets.PLAYER.getSprite(1, 2), 350)
                .addFrame(Textures.SpriteSheets.PLAYER.getSprite(2, 2), 350));

        add(new ActionMotion(ActionID.ATTACK_RIGHT)
                .addFrame(Textures.SpriteSheets.PLAYER.getSubImage(2 * 8, 0, 16, 8), 350)
                .addFrame(Textures.SpriteSheets.PLAYER.getSprite(0, 0), 1550));

        add(new ActionMotion(ActionID.ATTACK_LEFT)
                .addFrame(Textures.SpriteSheets.PLAYER.getSubImage(2 * 8, 0, 16, 8).getFlippedCopy(true, false), 250, -8, 0)
                .addFrame(Textures.SpriteSheets.PLAYER.getSprite(0, 0).getFlippedCopy(true, false), 1550));

        add(new ActionMotion(ActionID.ATTACK_UP)
                .addFrame(Textures.SpriteSheets.PLAYER.getSprite(4, 2), 350)
                .addFrame(Textures.SpriteSheets.PLAYER.getSprite(0, 2), 1550));

        add(new ActionMotion(ActionID.ATTACK_DOWN)
                .addFrame(Textures.SpriteSheets.PLAYER.getSubImage(3 * 8, 1 * 8, 8, 16), 350)
                .addFrame(Textures.SpriteSheets.PLAYER.getSprite(0, 1), 1550));

        add(new ActionMotion(ActionID.USE_SPECIAL)
                .addFrame(Textures.SpriteSheets.PLAYER.getSprite(4, 0), 1000));

        add(new ActionMotion(ActionID.DEATH)
                .addFrame(Textures.SpriteSheets.PLAYER.getSprite(4, 1), 1000));


    }
}
