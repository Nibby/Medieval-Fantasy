package hidden.indev0r.game.data;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by MrDeathJockey on 14/12/14.
 *
 * This class is the storage class for in-game variables related to scripts.
 * Mostly used as the 'toggle switch' to ensure that NPCs are saying the right
 * things at the right time.
 *
 * For example, after completing a quest a switch (or other means of data) will
 * be changed in this class. Upon NPC interaction again, if the checked switch
 * is turned on, then NPC will not give quest.
 *
 * A list will be made in the 'docs' section to keep track of all the unique
 * game switches.
 */
public class ScriptDataManager {

    private Map<String, String> scriptData = new HashMap<>();

    public void setValue(String dataKey, String dataValue) {
        scriptData.put(dataKey, dataValue);
    }

    public String getValue(String dataKey) {
        return scriptData.get(dataKey);
    }

    private ScriptDataManager() {
        //Apply default set on init
        for(ScriptDataDefaults def : ScriptDataDefaults.values()) {
            setValue(def.key, def.value);
        }
    }

    private static final ScriptDataManager manager = new ScriptDataManager();

    public static ScriptDataManager getManager() {
        return manager;
    }

    private enum ScriptDataDefaults {
        //The default switch values

        TEMP$GUARD_LEFT_0("guard_left_0.spoken", "0")
        ;

        String key, value;

        private ScriptDataDefaults(String key, String defaultValue) {
            this.key = key;
            this.value = defaultValue;
        }
    }
}
