package hidden.indev0r.game.entity.combat;

import hidden.indev0r.game.entity.Actor;
import hidden.indev0r.game.entity.combat.phase.AbstractCombatChannelPhase;
import hidden.indev0r.game.entity.combat.phase.AbstractCombatHitPhase;
import hidden.indev0r.game.entity.combat.phase.CombatChannelPhase$MeleeAttack;
import hidden.indev0r.game.entity.combat.phase.CombatHitPhase$MeleeAttack;

/**
 * Created by MrDeathJockey on 14/12/18.
 */
public enum AttackType {

    normal_warrior {
        @Override
        public AbstractCombatChannelPhase getChannelPhase(Actor initiator) {
            return new CombatChannelPhase$MeleeAttack(normal_warrior, initiator);
        }

        @Override
        public AbstractCombatHitPhase getHitPhase(Actor initiator, Actor target) {
            return new CombatHitPhase$MeleeAttack(initiator, target);
        }
    },
    normal_rogue {
        @Override
        public AbstractCombatChannelPhase getChannelPhase(Actor initiator) {
            return new CombatChannelPhase$MeleeAttack(normal_rogue, initiator);
        }

        @Override
        public AbstractCombatHitPhase getHitPhase(Actor initiator, Actor target) {
            return new CombatHitPhase$MeleeAttack(initiator, target);
        }
    },
    normal_mage {
        @Override
        public AbstractCombatChannelPhase getChannelPhase(Actor initiator) {
            return null;
        }

        @Override
        public AbstractCombatHitPhase getHitPhase(Actor initiator, Actor target) {
            return null;
        }
    },

    ;

    public abstract AbstractCombatChannelPhase getChannelPhase(Actor initiator);
    public abstract AbstractCombatHitPhase     getHitPhase(Actor initiator, Actor target);

}
