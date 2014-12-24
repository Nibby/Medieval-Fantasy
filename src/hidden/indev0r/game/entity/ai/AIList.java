package hidden.indev0r.game.entity.ai;

import hidden.indev0r.game.entity.Actor;

/**
 * Created by MrDeathJockey on 14/12/17.
 */
public enum AIList {

    NONE {
        @Override
        public AI getInstance(Actor actor) {
            return new AI$None(actor);
        }
    },

    //NPC
    NPC_LOOK_RANDOM {
        @Override
        public AI getInstance(Actor actor) {
            return new AI$NPC$LookRandom(actor);
        }
    },

    NPC_LOOK_ORDERED {
        @Override
        public AI getInstance(Actor actor) {
            return new AI$NPC$LookOrdered(actor);
        }
    },

    NPC_MOVE_RANDOM {
        @Override
        public AI getInstance(Actor actor) {
            return new AI$NPC$MoveRandom(actor);
        }
    },

    //Monster
    MONSTER_MELEE_BASIC {
        @Override
        public AI getInstance(Actor actor) {
            return new AI$MON$MeleeBasic(actor);
        }
    },

    MONSTER_RANGED_BASIC {
        @Override
        public AI getInstance(Actor actor) {
            return new AI$MON$RangedBasic(actor);
        }
    }

    ;

    public abstract AI getInstance(Actor actor);
}
