package hidden.indev0r.game.entity.combat.phase.death;

import hidden.indev0r.game.entity.Actor;
import hidden.indev0r.game.entity.combat.DamageModel;
import hidden.indev0r.game.sound.SoundType;

/**
 * Created by MrDeathJockey on 14/12/19.
 */
public enum DeathType {

    crumble {
        @Override
        public CombatDeathPhase newInstance(DamageModel model, Actor self, Actor target) {
            return new CombatDeathPhase$Crumble(model, self, target);
        }

        @Override
        public SoundType getSound() {
            return SoundType.death_crumble;
        }
    },

    action_set {
        @Override
        public CombatDeathPhase newInstance(DamageModel model, Actor self, Actor target) {
            return new CombatDeathPhase$ActionSet(model, self, target);
        }

        @Override
        public SoundType getSound() {
            return SoundType.death_crumble;
        }
    }
    ;

    public abstract CombatDeathPhase newInstance(DamageModel model, Actor self, Actor target);
    public abstract SoundType getSound();
}
