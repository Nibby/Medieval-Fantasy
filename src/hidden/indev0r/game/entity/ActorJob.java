package hidden.indev0r.game.entity;

import hidden.indev0r.game.entity.animation.ActionSet;
import hidden.indev0r.game.entity.animation.ActionSetDatabase;
import hidden.indev0r.game.entity.combat.AttackType;

/**
 * Created by MrDeathJockey on 14/12/18.
 */
public enum ActorJob {
    MAGE(0, "Mage") {
        @Override
        public int getMaxHPAtLevel(int level) {
            return 14 + level + (int) Math.pow(level, 2) / 10;
        }

        @Override
        public int getMaxMPAtLevel(int level) {
            return 17 + level + (int) (Math.pow(level, 2) / 2);
        }

        @Override
        public int getAttackRange() {
            return 5;
        }

        @Override
        public int getStrengthAtLevel(int level) {
            return 0;
        }

        @Override
        public int getDexterityAtLevel(int level) {
            return 0;
        }

        @Override
        public int getIntelligenceAtLevel(int level) {
            return 0;
        }

        @Override
        public int getSpeedAtLevel(int level) {
            return 0;
        }

        @Override
        public int getSpeedMax() {
            return 0;
        }

        @Override
        public int getStrengthMax() {
            return 0;
        }

        @Override
        public int getDexterityMax() {
            return 0;
        }

        @Override
        public int getIntelligenceMax() {
            return 0;
        }

        @Override
        public AttackType getDefaultAttackType() {
            return AttackType.normal_mage;
        }
    },

    WARRIOR(1, "Warrior") {
        @Override
        public int getMaxHPAtLevel(int level) {
            return 18 + level + ((int) Math.pow(level, 2) / 3);
        }

        @Override
        public int getMaxMPAtLevel(int level) {
            return 9 + level + (level + (int) Math.pow(level, 2) / 13);
        }

        @Override
        public int getAttackRange() {
            return 1;
        }

        @Override
        public int getStrengthAtLevel(int level) {
            return 5 + (level - 1) / 2;
        }

        @Override
        public int getDexterityAtLevel(int level) {
            return 3 + (level - 1) / 3;
        }

        @Override
        public int getIntelligenceAtLevel(int level) {
            return 1 + (level - 2) / 4;
        }

        @Override
        public int getSpeedAtLevel(int level) {
            return 15 + (level - 1) / 3;
        }

        @Override
        public int getSpeedMax() {
            return 25;
        }

        @Override
        public int getStrengthMax() {
            return 50;
        }

        @Override
        public int getDexterityMax() {
            return 30;
        }

        @Override
        public int getIntelligenceMax() {
            return 25;
        }

        @Override
        public AttackType getDefaultAttackType() {
            return AttackType.normal_warrior;
        }
    },

    ROGUE(2, "Rogue") {
        @Override
        public int getMaxHPAtLevel(int level) {
            return 17 + level + (int) Math.pow(level, 2) / 4;
        }

        @Override
        public int getMaxMPAtLevel(int level) {
            return 10 + level + (int) Math.pow(level, 2) / 9;
        }

        @Override
        public int getAttackRange() {
            return 1;
        }

        @Override
        public int getStrengthAtLevel(int level) {
            return 0;
        }

        @Override
        public int getDexterityAtLevel(int level) {
            return 0;
        }

        @Override
        public int getIntelligenceAtLevel(int level) {
            return 0;
        }

        @Override
        public int getSpeedAtLevel(int level) {
            return 0;
        }

        @Override
        public int getSpeedMax() {
            return 0;
        }

        @Override
        public int getStrengthMax() {
            return 0;
        }

        @Override
        public int getDexterityMax() {
            return 0;
        }

        @Override
        public int getIntelligenceMax() {
            return 0;
        }

        @Override
        public AttackType getDefaultAttackType() {
            return AttackType.normal_rogue;
        }
    };

    public ActionSet actionSet;
    public String    name;

    private ActorJob(int actionSetID, String name) {
        this.actionSet = ActionSetDatabase.get(actionSetID);
        this.name = name;
    }

    public abstract int getMaxHPAtLevel(int level);
    public abstract int getMaxMPAtLevel(int level);

    public abstract int getAttackRange();
    public abstract int getStrengthAtLevel(int level);
    public abstract int getDexterityAtLevel(int level);
    public abstract int getIntelligenceAtLevel(int level);
    public abstract int getSpeedAtLevel(int level);

    public abstract int getSpeedMax();
    public abstract int getStrengthMax();
    public abstract int getDexterityMax();
    public abstract int getIntelligenceMax();

    public abstract AttackType getDefaultAttackType();

    public static int getRequiredEXPAtLevel(int level) {
        switch(level) {
            case 1:     return 100;
            case 2:     return 150;
            case 3:     return 200;
            case 4:     return 350;
            case 5:     return 470;
            case 6:     return 600;
            case 7:     return 800;
            case 8:     return 1200;
            case 9:     return 1650;
            case 10:    return 2000;

            case 11:    return 2800;
            case 12:    return 3700;
            case 13:    return 4650;
            case 14:    return 5950;
            case 15:    return 6500;
            case 16:    return 8500;
            case 17:    return 10500;
            case 18:    return 12100;
            case 19:    return 14500;
            case 20:    return 17500;

            case 21:    return 23000;
            case 22:    return 28000;
            case 23:    return 35000;
            case 24:    return 47000;
            case 25:    return 60000;
            case 26:    return 75000;
            case 27:    return 88000;
            case 28:    return 100000;
            case 29:    return 150000;
            case 30:    return 210000;

            case 31:    return 300000;
            case 32:    return 400000;
            case 33:    return 520000;
            case 34:    return 650000;
            case 35:    return 800000;
            case 36:    return 1000000;
            case 37:    return 1500000;
            case 38:    return 2200000;
            case 39:    return 3500000;
            case 40:    return 5000000;

            case 41:    return 7500000;
            case 42:    return 12500000;
            case 43:    return 18500000;
            case 44:    return 26500000;
            case 45:    return 39500000;
            case 46:    return 50000000;
            case 47:    return 83000000;
            case 48:    return 100000000;
            case 49:    return 150000000;
            case 50:    return 250000000;

            case 51:    return 380000000;
            case 52:    return 490000000;
            case 53:    return 600000000;
            case 54:    return 800000000;
            default:    return 1000000000;
        }
    }

}
