package hidden.indev0r.game.sound;

import paulscode.sound.Library;
import paulscode.sound.SoundSystem;
import paulscode.sound.SoundSystemConfig;
import paulscode.sound.SoundSystemException;
import paulscode.sound.codecs.CodecJOrbis;
import paulscode.sound.codecs.CodecWav;
import paulscode.sound.libraries.LibraryJavaSound;

import java.net.MalformedURLException;

/**
 * Created by MrDeathJockey on 14/12/17.
 */
public class SoundPlayer {

    private final Class<? extends Library> libraryType;
    private SoundSystem sys;
    private boolean oggSupport = true;
    private boolean wavSupport = true;

    private static final String BACKGROUND_MUSIC = "BGM";

    private float volume = 1.0f;
    private boolean muted = false;

    public SoundPlayer() {
        libraryType = LibraryJavaSound.class;

        try {
            SoundSystemConfig.setCodec("ogg", CodecJOrbis.class);
        } catch (SoundSystemException e) {
            e.printStackTrace();
            oggSupport = false;
        }

        try {
            SoundSystemConfig.setCodec("wav", CodecWav.class);
        } catch (SoundSystemException e) {
            e.printStackTrace();
            wavSupport = false;
        }

        try {
            sys = new SoundSystem(libraryType);
        } catch (SoundSystemException e) {
            e.printStackTrace();
        }
    }

    public boolean isOggSupported() {
        return oggSupport && sys != null;
    }

    public boolean isWavSupported() {
        return wavSupport && sys != null;
    }

    public boolean isCurrentlyPlaying(String source) {
        if(isOggSupported()) {
            return sys.playing(source);
        }
        return false;
    }

    public void playBGM(BGM bgm, boolean loop) {
        if(!isCurrentlyPlaying(BACKGROUND_MUSIC) && !isMuted() && isOggSupported()) {
            try {
                sys.backgroundMusic(BACKGROUND_MUSIC, bgm.getResourceName().toUri().toURL(), bgm.getResourceName().toString(), loop);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
    }

    public float getVolume() {
        return volume;
    }

    public void setVolume(float volume) {
        this.volume = volume;
    }

    public boolean isMuted() {
        return muted;
    }

    public void setMuted(boolean muted) {
        this.muted = muted;
    }

    public void fadeOutBGM(BGM bgm, int duration) {
        if(isOggSupported()) {
            try {
                sys.fadeOut(BACKGROUND_MUSIC, bgm.getResourceName().toUri().toURL(), bgm.getResourceName().toString(), duration);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
    }

    public void stopBGM() {
        if(isOggSupported()) {
            sys.stop(BACKGROUND_MUSIC);
        }
    }
}
