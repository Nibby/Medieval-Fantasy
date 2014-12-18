package hidden.indev0r.game.entity.combat.phase.death;

import hidden.indev0r.game.entity.Actor;

/**
 * Created by MrDeathJockey on 14/12/19.
 */
public enum DeathType {

    crumble {
        @Override
        public CombatDeathPhase newInstance(Actor actor) {
            return new CombatDeathPhase$Crumble(actor);
        }
    },

    action_set {
        @Override
        public CombatDeathPhase newInstance(Actor actor) {
            return new CombatDeathPhase$ActionSet(actor);
        }
    }
    ;

    public abstract CombatDeathPhase newInstance(Actor actor);
}
