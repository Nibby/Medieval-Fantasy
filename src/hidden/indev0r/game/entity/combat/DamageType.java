package hidden.indev0r.game.entity.combat;

import hidden.indev0r.game.entity.Actor;
import hidden.indev0r.game.sound.SoundType;
import org.newdawn.slick.Color;

/**
 * Created by MrDeathJockey on 14/12/18.
 */
public enum DamageType {

    normal("Normal", Color.white) {
        @Override
        public int processDamage(DamageModel model, Actor hurt, Actor initiator) {
            return model.getDamage(0);
        }

        @Override
        public SoundType getSwingSound() {
            return SoundType.attack_normal_swing;
        }

        @Override
        public SoundType getHurtSound() {
            return SoundType.attack_normal_hurt;
        }
    },

    holy("Holy", Color.white) {
        @Override
        public int processDamage(DamageModel model, Actor hurt, Actor initiator) {
            return model.getDamage(0);
        }

        @Override
        public SoundType getSwingSound() {
            return null;
        }

        @Override
        public SoundType getHurtSound() {
            return null;
        }
    },

    lightning("Static", Color.yellow) {
        @Override
        public int processDamage(DamageModel model, Actor hurt, Actor initiator) {
            return model.getDamage(0);
        }

        @Override
        public SoundType getSwingSound() {
            return null;
        }

        @Override
        public SoundType getHurtSound() {
            return null;
        }
    },

    undead("Necrotic", Color.magenta) {
        @Override
        public int processDamage(DamageModel model, Actor hurt, Actor initiator) {
            return model.getDamage(0);
        }

        @Override
        public SoundType getSwingSound() {
            return null;
        }

        @Override
        public SoundType getHurtSound() {
            return null;
        }
    },

    fire("Flame", Color.orange) {
        @Override
        public int processDamage(DamageModel model, Actor hurt, Actor initiator) {
            return model.getDamage(0);
        }

        @Override
        public SoundType getSwingSound() {
            return null;
        }

        @Override
        public SoundType getHurtSound() {
            return null;
        }
    },

    poison("Poison", Color.cyan) {
        @Override
        public int processDamage(DamageModel model, Actor hurt, Actor initiator) {
            return model.getDamage(0);
        }

        @Override
        public SoundType getSwingSound() {
            return null;
        }

        @Override
        public SoundType getHurtSound() {
            return null;
        }
    }
    ;

    private String name;
    private Color nameColor;

    DamageType(String typeName, Color nameColor) {
        this.name = typeName;
        this.nameColor = nameColor;
    }

    public abstract int processDamage(DamageModel model, Actor hurt, Actor initiator);

    public abstract SoundType getSwingSound();
    public abstract SoundType getHurtSound();
}
