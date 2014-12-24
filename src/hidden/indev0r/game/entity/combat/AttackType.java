package hidden.indev0r.game.entity.combat;

import hidden.indev0r.game.entity.Actor;
import hidden.indev0r.game.entity.combat.phase.channel.AbstractCombatChannelPhase;
import hidden.indev0r.game.entity.combat.phase.channel.CombatChannelPhase$RangedBasic;
import hidden.indev0r.game.entity.combat.phase.death.AbstractCombatDeathPhase;
import hidden.indev0r.game.entity.combat.phase.hit.AbstractCombatHitPhase;
import hidden.indev0r.game.entity.combat.phase.channel.CombatChannelPhase$MeleeAttack;
import hidden.indev0r.game.entity.combat.phase.hit.CombatHitPhase$RangedAttack;
import hidden.indev0r.game.entity.combat.phase.hit.CombatHitPhase$MeleeAttack;

/**
 * Created by MrDeathJockey on 14/12/18.
 */
public enum AttackType {

    melee_0 {
        @Override
        public AbstractCombatChannelPhase getChannelPhase(DamageModel model, Actor initiator, Actor target) {
            return new CombatChannelPhase$MeleeAttack(model, melee_0, initiator, target);
        }

        @Override
        public AbstractCombatHitPhase getHitPhase(DamageModel model, Actor initiator, Actor target, int hitIndex) {
            return new CombatHitPhase$MeleeAttack(model, initiator, target, hitIndex);
        }

        @Override
        public AbstractCombatDeathPhase getDeathPhase(DamageModel model, Actor initiator, Actor target) {
            return null;
        }
    },

    melee_1 {
        @Override
        public AbstractCombatChannelPhase getChannelPhase(DamageModel model, Actor initiator, Actor target) {
            return new CombatChannelPhase$MeleeAttack(model, melee_1, initiator, target);
        }

        @Override
        public AbstractCombatHitPhase getHitPhase(DamageModel model, Actor initiator, Actor target, int hitIndex) {
            return new CombatHitPhase$MeleeAttack(model, initiator, target, hitIndex);
        }

        @Override
        public AbstractCombatDeathPhase getDeathPhase(DamageModel model, Actor initiator, Actor target) {
            return null;
        }
    },

    caster_0 {
        @Override
        public AbstractCombatChannelPhase getChannelPhase(DamageModel model, Actor initiator, Actor target) {
            return new CombatChannelPhase$RangedBasic(model, caster_0, initiator, target);
        }

        @Override
        public AbstractCombatHitPhase getHitPhase(DamageModel model, Actor initiator, Actor target, int hitIndex) {
            return new CombatHitPhase$RangedAttack(model, initiator, target, hitIndex);
        }

        @Override
        public AbstractCombatDeathPhase getDeathPhase(DamageModel model, Actor initiator, Actor target) {
            return null;
        }
    },

    ;

    public abstract AbstractCombatChannelPhase getChannelPhase(DamageModel model, Actor initiator, Actor target);
    public abstract AbstractCombatHitPhase     getHitPhase(DamageModel model, Actor initiator, Actor target, int hitIndex);
    public abstract AbstractCombatDeathPhase   getDeathPhase(DamageModel model, Actor initiator, Actor target);

}
