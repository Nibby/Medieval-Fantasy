package hidden.indev0r.game.entity;

import static hidden.indev0r.game.entity.Actor.Faction.*;
import static hidden.indev0r.game.entity.Actor.Faction;

/**
 * Created by MrDeathJockey on 14/12/8.
 */
public class FactionUtil {

    public static boolean isEnemy(Faction origin, Faction compare) {
        Faction[] enemies = getEnemies(origin);
        for(Faction f : enemies) {
            if(compare.equals(f)) return true;
        }
        return false;
    }

    public static boolean isAlly(Faction origin, Faction compare) {
        Faction[] allies = getAllies(origin);
        for(Faction f : allies) {
            if(compare.equals(f)) return true;
        }
        return false;
    }

    public static Faction[] getAllies(Faction faction) {
        switch(faction) {
            case GLYSIA:
                return array(GLYSIA, HUMAN);
            case NAYA:
                return array(NAYA, HUMAN);
            case UNDEAD:
                return array(UNDEAD, DEMON);
            case DEMON:
                return array(DEMON, UNDEAD);
            case HUMAN:
                return array(HUMAN, GLYSIA, NAYA);
            case NEUTRAL:
                return array(NEUTRAL);
            default:
                return array(faction);
        }
    }

    public static Faction[] getEnemies(Faction faction) {
        switch(faction) {
            case GLYSIA:
                return array(NAYA, UNDEAD, DEMON, NEUTRAL);

            case NAYA:
                return array(GLYSIA, UNDEAD, DEMON, NEUTRAL);

            case UNDEAD:
                return array(GLYSIA, NAYA, HUMAN, NEUTRAL);

            case DEMON:
                return array(GLYSIA, NAYA, HUMAN, NEUTRAL);

            case NEUTRAL:
                return array(GLYSIA, NAYA, HUMAN, DEMON, UNDEAD);

            case HUMAN:
                return array(UNDEAD, DEMON, NEUTRAL);

            default:
                return new Faction[] {};
        }
    }

    private static Faction[] array(Faction... factions) {
        return factions;
    }

}
