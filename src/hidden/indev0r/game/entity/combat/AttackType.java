package hidden.indev0r.game.entity.combat;

import hidden.indev0r.game.entity.Actor;
import hidden.indev0r.game.entity.combat.phase.channel.AbstractCombatChannelPhase;
import hidden.indev0r.game.entity.combat.phase.death.AbstractCombatDeathPhase;
import hidden.indev0r.game.entity.combat.phase.death.CombatDeathPhase$Crumble;
import hidden.indev0r.game.entity.combat.phase.hit.AbstractCombatHitPhase;
import hidden.indev0r.game.entity.combat.phase.channel.CombatChannelPhase$MeleeAttack;
import hidden.indev0r.game.entity.combat.phase.hit.CombatHitPhase$MeleeAttack;

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

        @Override
        public AbstractCombatDeathPhase getDeathPhase(Actor target) {
            return null;
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

        @Override
        public AbstractCombatDeathPhase getDeathPhase(Actor target) {
            return null;
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

        @Override
        public AbstractCombatDeathPhase getDeathPhase(Actor target) {
            return null;
        }
    },

    ;

    public abstract AbstractCombatChannelPhase getChannelPhase(Actor initiator);
    public abstract AbstractCombatHitPhase     getHitPhase(Actor initiator, Actor target);
    public abstract AbstractCombatDeathPhase   getDeathPhase(Actor target);

}
