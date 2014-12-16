package hidden.indev0r.game.sound;

import hidden.indev0r.game.reference.References;

import java.nio.file.Path;

/**
 * Created by MrDeathJockey on 14/12/17.
 */
public enum BGM {


    BGM_0(References.BGM_PATH.resolve("bgm0.ogg"), "Sarah", "DDRKirby(ISQ)"),

    ;

    private String clipTitle;
    private String clipComposer;
    private Path clipResource;

    private BGM(Path resource, String title, String composer) {
        this.clipResource = resource;
        this.clipTitle = title;
        this.clipComposer = composer;
    }

    public String getComposer() {
        return clipComposer;
    }

    public String getTitle() {
        return clipTitle;
    }

    public Path getResourceName() {
        return clipResource;
    }
}
