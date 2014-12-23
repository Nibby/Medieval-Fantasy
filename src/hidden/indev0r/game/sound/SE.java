package hidden.indev0r.game.sound;

import hidden.indev0r.game.MedievalLauncher;
import hidden.indev0r.game.entity.Actor;
import hidden.indev0r.game.reference.References;

import java.nio.file.Path;

/**
 * Created by MrDeathJockey on 14/12/19.
 */
public enum SE {

    CHANNEL_0(References.SE_PATH.resolve("channel").resolve("channel0.wav")),
    CHANNEL_1(References.SE_PATH.resolve("channel").resolve("channel1.wav")),
    CHANNEL_2(References.SE_PATH.resolve("channel").resolve("channel2.wav")),
    CHANNEL_3(References.SE_PATH.resolve("channel").resolve("channel3.wav")),
    CHANNEL_4(References.SE_PATH.resolve("channel").resolve("channel4.wav")),
    CHANNEL_5(References.SE_PATH.resolve("channel").resolve("channel5.wav")),
    CHANNEL_6(References.SE_PATH.resolve("channel").resolve("channel6.wav")),
    CHANNEL_7(References.SE_PATH.resolve("channel").resolve("channel7.wav")),
    CHANNEL_8(References.SE_PATH.resolve("channel").resolve("channel8.wav")),
    CHANNEL_9(References.SE_PATH.resolve("channel").resolve("channel9.wav")),

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
