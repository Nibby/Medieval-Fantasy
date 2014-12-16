package hidden.indev0r.game.entity.npc.script;

/**
 * Created by MrDeathJockey on 14/12/14.
 */
public enum CommandList {

    dialog {
        @Override
        public Command newInstance() {
            return new Command$Dialog();
        }
    },

    actAction {
        @Override
        public Command newInstance() {
            return new Command$ActAction();
        }
    },

    facing {
        @Override
        public Command newInstance() {
            return new Command$Facing();
        }
    },

    IF {
        @Override
        public Command newInstance() {
            return new Command$If();
        }
    },

    move {
        @Override
        public Command newInstance() {
            return new Command$Move();
        }
    },

    random {
        @Override
        public Command newInstance() {
            return new Command$Random();
        }
    },

    say {
        @Override
        public Command newInstance() {
            return new Command$Say();
        }
    },

    scriptData {
        @Override
        public Command newInstance() {
            return new Command$ScriptData();
        }
    },

    wait {
        @Override
        public Command newInstance() {
            return new Command$Wait();
        }
    },

    stats {
        @Override
        public Command newInstance() {
            return new Command$Stats();
        }
    },

    camera {
        @Override
        public Command newInstance() {
            return new Command$Camera();
        }
    },

    runScript {
        @Override
        public Command newInstance() {
            return new Command$RunScript();
        }
    },

    freezeControls {
        @Override
        public Command newInstance() {
            return new Command$FreezeControls();
        }
    },

    cinematicMode {
        @Override
        public Command newInstance() {
            return new Command$CinematicMode();
        }
    },

    showTip {
        @Override
        public Command newInstance() {
            return new Command$ShowTip();
        }
    },

    screen {
        @Override
        public Command newInstance() {
            return new Command$Screen();
        }
    },

    cinematicText {
        @Override
        public Command newInstance() {
            return new Command$CinematicText();
        }
    },

    bgm {
        @Override
        public Command newInstance() {
            return new Command$BGM();
        }
    }
    ;



    public abstract Command newInstance();
}
