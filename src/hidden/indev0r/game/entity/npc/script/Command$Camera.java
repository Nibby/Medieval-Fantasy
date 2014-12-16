package hidden.indev0r.game.entity.npc.script;

import hidden.indev0r.game.Camera;
import hidden.indev0r.game.CameraListener;
import hidden.indev0r.game.MedievalLauncher;
import hidden.indev0r.game.entity.Actor;
import hidden.indev0r.game.map.Tile;
import hidden.indev0r.game.reference.References;
import org.w3c.dom.Element;

/**
 * Created by MrDeathJockey on 14/12/14.
 */
public class Command$Camera extends Command implements CameraListener {

    enum CameraAction {

        TRACK {
            @Override
            void exec(Actor actor, Element cmdElement, CommandBlock block, Camera camera) {
                Actor cameraTarget = (Actor) Script.translate(cmdElement.getAttribute("actor"), cmdElement.getAttribute("actorRef"));
                camera.setTrackObject(cameraTarget);
                block.executeNext(actor);
            }
        },

        PAN {
            /*
                Camera pan modes:
                   TOWARDS_ACTOR    - Pans towards a certain actor
                   CENTER_TILE      - Set a tile of given x,y co-ord as its center
             */

            @Override
            void exec(Actor executor, Element cmdElement, CommandBlock block, Camera camera) {
                String mode = cmdElement.getAttribute("mode");
                camera.setTrackObject(null);

                int cameraSpeed = 4;
                int cameraX, cameraY;

                if(cmdElement.hasAttribute("speed"))
                    cameraSpeed = Integer.parseInt(cmdElement.getAttribute("speed"));

                switch(mode) {
                    case "TOWARDS_ACTOR":
                        Actor actor = (Actor) Script.translate(cmdElement.getAttribute("actor"), cmdElement.getAttribute("actorRef"));

                        cameraX = -((int) actor.getPosition().x - References.GAME_WIDTH / 2 / References.DRAW_SCALE + actor.getWidth() / 2);
                        cameraY = -((int) actor.getPosition().y - References.GAME_HEIGHT / 2 / References.DRAW_SCALE + actor.getHeight() / 2);

                        camera.pan(cameraX, cameraY, cameraSpeed);
                        break;
                    case "CENTER_TILE":
                        String[] tileXY = cmdElement.getAttribute("tilePos").split(",");
                        int tileX = Integer.parseInt(tileXY[0]) * Tile.TILE_SIZE;
                        int tileY = Integer.parseInt(tileXY[1]) * Tile.TILE_SIZE;

                        cameraX = -(tileX - References.GAME_WIDTH / 2 / References.DRAW_SCALE + Tile.TILE_SIZE / 2);
                        cameraY = -(tileY - References.GAME_HEIGHT / 2 / References.DRAW_SCALE + Tile.TILE_SIZE / 2);

                        camera.pan(cameraX, cameraY, cameraSpeed);
                        break;
                }
            }
        },

        SHAKE {
            @Override
            void exec(Actor executor, Element cmdElement, CommandBlock block, Camera camera) {
                int shakeAmplitude = Integer.parseInt(cmdElement.getAttribute("amplitude"));
                int shakeDuration = Integer.parseInt(cmdElement.getAttribute("duration"));
                int shakeIntensity = Integer.parseInt(cmdElement.getAttribute("intensity"));
                camera.shake(shakeAmplitude, shakeIntensity, shakeDuration);
            }
        },

        SNAP_TO {
            @Override
            void exec(Actor executor, Element cmdElement, CommandBlock block, Camera camera) {
                String mode = cmdElement.getAttribute("mode");
                int cameraX, cameraY;
                switch(mode) {
                    case "ACTOR":
                        camera.setTrackObject(null);
                        Actor actor = (Actor) Script.translate(cmdElement.getAttribute("actor"), cmdElement.getAttribute("actorRef"));
                        cameraX = -((int) actor.getPosition().x - References.GAME_WIDTH / 2 / References.DRAW_SCALE + actor.getWidth() / 2);
                        cameraY = -((int) actor.getPosition().y - References.GAME_HEIGHT / 2 / References.DRAW_SCALE + actor.getHeight() / 2);
                        camera.setOffset(cameraX, cameraY);
                        break;
                    case "TILE":
                        camera.setTrackObject(null);
                        String[] tileXY = cmdElement.getAttribute("tilePos").split(",");
                        int tileX = Integer.parseInt(tileXY[0]) * Tile.TILE_SIZE;
                        int tileY = Integer.parseInt(tileXY[1]) * Tile.TILE_SIZE;

                        cameraX = -(tileX - References.GAME_WIDTH / 2 / References.DRAW_SCALE + Tile.TILE_SIZE / 2);
                        cameraY = -(tileY - References.GAME_HEIGHT / 2 / References.DRAW_SCALE + Tile.TILE_SIZE / 2);

                        camera.setOffset(cameraX, cameraY);
                        break;
                }
            }
        }

        ;

        abstract void exec(Actor executor, Element cmdElement, CommandBlock block, Camera camera);
    }

    private boolean isListening = false;
    private CommandBlock block;
    private CameraAction action;
    private Actor actor;
    private Camera camera;

    private boolean requireListener = false;

    @Override
    public void make(CommandBlock block, Actor actor, Element e) {
        super.make(block, actor, e);
        action = CameraAction.valueOf(e.getAttribute("action"));
        if(action.equals(CameraAction.SHAKE) || action.equals(CameraAction.PAN))
            requireListener = true;
    }

    @Override
    public void exec(Actor actor, final CommandBlock block) {
//        super.exec(actor, block);
        if(requireListener && !isListening) {
            camera = MedievalLauncher.getInstance().getGameState().getCamera();
            camera.addListener(this);
            this.block = block;
            isListening = true;
        }
        this.actor = actor;
        camera = MedievalLauncher.getInstance().getGameState().getCamera();
        action.exec(getActor(), cmdElement, block, camera);
        if(!requireListener)
            block.executeNext(actor);
    }

    @Override
    public void panFinished(Camera camera) {
        block.executeNext(actor);
    }

    @Override
    public void shakeFinished(Camera camera) {
        block.executeNext(actor);
    }

    public Actor getCommandActor() {
        return actor;
    }
}
