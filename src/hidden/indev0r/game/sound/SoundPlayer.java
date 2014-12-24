package hidden.indev0r.game.sound;

import hidden.indev0r.game.entity.Actor;
import org.lwjgl.util.vector.Vector2f;
import paulscode.sound.Library;
import paulscode.sound.SoundSystem;
import paulscode.sound.SoundSystemConfig;
import paulscode.sound.SoundSystemException;
import paulscode.sound.codecs.CodecJOrbis;
import paulscode.sound.codecs.CodecWav;
import paulscode.sound.libraries.LibraryJavaSound;
import paulscode.sound.libraries.LibraryLWJGLOpenAL;

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
        Class<? extends Library> compatibleLibrary = null;
        if(SoundSystem.libraryCompatible(LibraryLWJGLOpenAL.class))
            compatibleLibrary = LibraryLWJGLOpenAL.class;
        else if(SoundSystem.libraryCompatible(LibraryJavaSound.class))
            compatibleLibrary = LibraryJavaSound.class;
        else
            compatibleLibrary = Library.class;

        libraryType = compatibleLibrary;

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
            SoundSystemConfig.addLibrary(LibraryLWJGLOpenAL.class);
            SoundSystemConfig.addLibrary(LibraryJavaSound.class);

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
            sys.fadeOut(BACKGROUND_MUSIC, null, "", duration);
        }
    }

    public void fadeOutInBGM(BGM newBGM, int durationEnd, int durationStart) {
        if(isOggSupported()) {
            try {
                sys.fadeOutIn(BACKGROUND_MUSIC, newBGM.getResourceName().toUri().toURL(), newBGM.toString(), durationEnd, durationStart);
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

    Vector2f listenerPosition = new Vector2f(0, 0);
    public void setListenerPosition(float x, float y) {
        sys.setListenerPosition(x, y, 0);
        listenerPosition.x = x;
        listenerPosition.y = y;
    }

    public void setListenerAngle(float angle) {
        sys.setListenerAngle(angle);
    }

    public void playSound(SE sound) {
        playSound(sound, listenerPosition.x, listenerPosition.y);
    }

    public void playSound(SE sound, Actor source) {
        playSound(sound, source.getX(), source.getY());
    }

    public void playSound(SE sound, float sx, float sy) {
        try {
            sys.quickPlay(false,
                    sound.getResource().toUri().toURL(),
                    sound.getResource().toString(),
                    false, sx, sy, 0,
                    SoundSystemConfig.ATTENUATION_ROLLOFF,
                    SoundSystemConfig.getDefaultRolloff());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
}
