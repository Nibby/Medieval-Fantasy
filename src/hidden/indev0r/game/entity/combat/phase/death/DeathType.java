package hidden.indev0r.game.entity.combat.phase.death;

import hidden.indev0r.game.entity.Actor;
import hidden.indev0r.game.sound.SoundType;

/**
 * Created by MrDeathJockey on 14/12/19.
 */
public enum DeathType {

    crumble {
        @Override
        public CombatDeathPhase newInstance(Actor actor) {
            return new CombatDeathPhase$Crumble(actor);
        }

        @Override
        public SoundType getSound() {
            return SoundType.death_crumble;
        }
    },

    action_set {
        @Override
        public CombatDeathPhase newInstance(Actor actor) {
            return new CombatDeathPhase$ActionSet(actor);
        }

        @Override
        public SoundType getSound() {
            return SoundType.death_crumble;
        }
    }
    ;

    public abstract CombatDeathPhase newInstance(Actor actor);
    public abstract SoundType getSound();
}
