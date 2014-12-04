package hidden.indev0r.core.entity.animation;

/**
 * Created by MrDeathJockey on 14/12/4.
 */
public enum ActionID {

    STATIC("anim_static"),

    WALK_LEFT("anim_walk_left"),
    WALK_RIGHT("anim_walk_right"),
    WALK_UP("anim_walk_up"),
    WALK_DOWN("anim_walk_down"),

    ATTACK_LEFT("anim_attack_left"),
    ATTACK_RIGHT("anim_attack_right"),
    ATTACK_UP("anim_attack_up"),
    ATTACK_DOWN("anim_attack_down"),

    CAST_LEFT("anim_cast_left"),
    CAST_RIGHT("anim_cast_right"),
    CAST_UP("anim_cast_up"),
    CAST_DOWN("anim_cast-down"),

    USE_SPECIAL("anim_use_special"),
    DEATH("anim_death")
    ;

    String id;
    private ActionID(String id) {
        this.id = id;
    }
}