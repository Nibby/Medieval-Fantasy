package hidden.indev0r.game.sound;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by MrDeathJockey on 14/12/19.
 */
public enum SoundSet {

    player_warrior {
        @Override
        protected void initSoundBank() {
            addSound(SoundType.attack_normal_swing, SE.HIT_0);
            addSound(SoundType.attack_normal_swing, SE.HIT_1);

            addSound(SoundType.parry, SE.PARRY_0);

            addSound(SoundType.attack_normal_hurt, SE.HURT_1);
        }
    },


    mon_0 {
        @Override
        protected void initSoundBank() {
            addSound(SoundType.death_crumble, SE.CRUMBLE_0);
            addSound(SoundType.death_crumble, SE.CRUMBLE_1);

            addSound(SoundType.attack_normal_swing, SE.HIT_2);
            addSound(SoundType.attack_normal_hurt, SE.HURT_0);
        }
    }
    ;

    private Map<SoundType, List<SE>> soundBank = new HashMap<>();

    private SoundSet() {
        initSoundBank();
    }

    protected abstract void initSoundBank();

    protected void addSound(SoundType key, SE se) {
        List<SE> list = soundBank.get(key);
        if(list == null) list = new ArrayList<>();
        list.add(se);
        soundBank.put(key, list);
    }

    public SE getRandomSound(SoundType key) {
        List<SE> list = soundBank.get(key);
        if(list == null) return null;
        if(list.size() == 0) return null;

        SE randomSE = list.get((int) (Math.random() * list.size()));
        return randomSE;
    }

    public List<SE> getSounds(Object key) {
        return soundBank.get(key);
    }
}
