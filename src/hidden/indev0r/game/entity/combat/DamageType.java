package hidden.indev0r.game.entity.combat;

import hidden.indev0r.game.entity.Actor;
import hidden.indev0r.game.sound.SoundType;
import org.newdawn.slick.Color;

/**
 * Created by MrDeathJockey on 14/12/18.
 */
public enum DamageType {

    normal("Normal", Color.red) {
        @Override
        public int processDamage(int damage, Actor hurt, Actor initiator) {
            return damage;
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

    magic("Magic", Color.red) {
        @Override
        public int processDamage(int damage, Actor hurt, Actor initiator) {
            return damage;
        }

        @Override
        public SoundType getSwingSound() {
            return SoundType.attack_magic_swing;
        }

        @Override
        public SoundType getHurtSound() {
            return SoundType.attack_magic_hurt;
        }
    },

    holy("Holy", Color.white) {
        @Override
        public int processDamage(int damage, Actor hurt, Actor initiator) {
            return damage;
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
        public int processDamage(int damage, Actor hurt, Actor initiator) {
            return damage;
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
        public int processDamage(int damage, Actor hurt, Actor initiator) {
            return damage;
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
        public int processDamage(int damage, Actor hurt, Actor initiator) {
            return damage;
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
        public int processDamage(int damage, Actor hurt, Actor initiator) {
            return damage;
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

    public abstract int processDamage(int damage, Actor hurt, Actor initiator);
    public Color getColor() { return nameColor; }

    public abstract SoundType getSwingSound();
    public abstract SoundType getHurtSound();
}
