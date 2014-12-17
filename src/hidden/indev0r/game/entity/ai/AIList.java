package hidden.indev0r.game.entity.ai;

/**
 * Created by MrDeathJockey on 14/12/17.
 */
public enum AIList {

    NONE {
        @Override
        public AI getInstance() {
            return new AI$None();
        }
    },

    NPC_LOOK_RANDOM {
        @Override
        public AI getInstance() {
            return new AI$NPC$LookRandom();
        }
    },

    NPC_LOOK_ORDERED {
        @Override
        public AI getInstance() {
            return new AI$NPC$LookOrdered();
        }
    },

    NPC_MOVE_RANDOM {
        @Override
        public AI getInstance() {
            return new AI$NPC$MoveRandom();
        }
    }

    ;

    public abstract AI getInstance();
}
