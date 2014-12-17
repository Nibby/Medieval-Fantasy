package hidden.indev0r.game.entity.combat;

import hidden.indev0r.game.entity.Actor;
import org.newdawn.slick.Color;

/**
 * Created by MrDeathJockey on 14/12/18.
 */
public enum DamageType {

    normal("Normal", Color.white) {
        @Override
        int processDamage(DamageModel model, Actor hurt, Actor initiator) {
            return model.getDamage(0);
        }
    },

    holy("Holy", Color.white) {
        @Override
        int processDamage(DamageModel model, Actor hurt, Actor initiator) {
            return model.getDamage(0);
        }
    },

    lightning("Static", Color.yellow) {
        @Override
        int processDamage(DamageModel model, Actor hurt, Actor initiator) {
            return model.getDamage(0);
        }
    },

    undead("Necrotic", Color.magenta) {
        @Override
        int processDamage(DamageModel model, Actor hurt, Actor initiator) {
            return model.getDamage(0);
        }
    },

    fire("Flame", Color.orange) {
        @Override
        int processDamage(DamageModel model, Actor hurt, Actor initiator) {
            return model.getDamage(0);
        }
    },

    poison("Poison", Color.cyan) {
        @Override
        int processDamage(DamageModel model, Actor hurt, Actor initiator) {
            return model.getDamage(0);
        }
    }
    ;

    private String name;
    private Color nameColor;

    DamageType(String typeName, Color nameColor) {
        this.name = typeName;
        this.nameColor = nameColor;
    }

    abstract int processDamage(DamageModel model, Actor hurt, Actor initiator);
}
