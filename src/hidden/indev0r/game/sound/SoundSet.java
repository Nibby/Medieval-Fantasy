package hidden.indev0r.game.sound;

import hidden.indev0r.game.entity.combat.DamageType;
import hidden.indev0r.game.entity.combat.phase.death.DeathType;

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
            addSound(DamageType.normal + "_hit", SE.HIT_0);
            addSound(DamageType.normal + "_hit", SE.HIT_1);
        }
    },


    mon_0 {
        @Override
        protected void initSoundBank() {
            addSound(DeathType.crumble, SE.CRUMBLE_0);
            addSound(DeathType.crumble, SE.CRUMBLE_1);

            addSound(DamageType.normal + "_hurt", SE.HURT_0);
        }
    }
    ;

    private Map<Object, List<SE>> soundBank = new HashMap<>();

    private SoundSet() {
        initSoundBank();
    }

    protected abstract void initSoundBank();

    protected void addSound(Object key, SE se) {
        List<SE> list = soundBank.get(key);
        if(list == null) list = new ArrayList<>();
        list.add(se);
        soundBank.put(key, list);
    }

    public SE getRandomSound(Object key) {
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
