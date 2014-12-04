package hidden.indev0r.core.entity.animation;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class EntityActionSet {

    //Keeping a record of all registered sets with unique IDs
    //In case if we want XML genereated NPC's to inherit some of these sets
    private static final Map<Integer, EntityActionSet> setMap = new HashMap<>();

    List<ActionMotion> motionList = new ArrayList<>();
    public EntityActionSet(int id) {

        initMotionList();

        if(setMap.get(id) != null) {
            JOptionPane.showMessageDialog(null,
                    "EntityActionSet '" + id + "' already registered!",
                    "Internal Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        setMap.put(id, this);
    }

    protected abstract void initMotionList();

    protected void add(ActionMotion motion) {
        motionList.add(motion);
    }

    public void applyAll(Map<ActionID, ActionMotion> motionMap) {
        for(ActionMotion motion : motionList) {
            motionMap.put(motion.getActionID(), motion);
        }
    }
}
