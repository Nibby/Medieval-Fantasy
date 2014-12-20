package hidden.indev0r.game.sound;

import hidden.indev0r.game.MedievalLauncher;
import hidden.indev0r.game.entity.Actor;
import hidden.indev0r.game.reference.References;

import java.nio.file.Path;

/**
 * Created by MrDeathJockey on 14/12/19.
 */
public enum SE {

    HIT_0(References.SE_PATH.resolve("hit").resolve("hit0.wav")),
    HIT_1(References.SE_PATH.resolve("hit").resolve("hit1.wav")),
    HIT_2(References.SE_PATH.resolve("hit").resolve("hit2.wav")),

    CRUMBLE_0(References.SE_PATH.resolve("crumble").resolve("crumble0.wav")),
    CRUMBLE_1(References.SE_PATH.resolve("crumble").resolve("crumble1.wav")),

    HURT_0(References.SE_PATH.resolve("hurt").resolve("hurt0.wav")),
    HURT_1(References.SE_PATH.resolve("hurt").resolve("hurt1.wav")),

    PARRY_0(References.SE_PATH.resolve("parry").resolve("parry0.wav"))
    ;

    private Path resource;

    private SE(Path resource) {
        this.resource = resource;
    }

    public Path getResource() {
        return resource;
    }

    public void play(Actor actor) {
        play(actor.getX(), actor.getY());
    }

    public void play(float x, float y) {
        MedievalLauncher.getInstance().getGameState().getSoundPlayer().playSound(this, x, y);
    }
}
