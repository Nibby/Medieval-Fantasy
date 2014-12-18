package hidden.indev0r.game.entity.combat.phase;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MrDeathJockey on 14/12/18.
 */
public class CombatPhaseManager {

    private List<CombatPhase> combatPhaseList = new ArrayList<>();

    public void render(Graphics g) {
        for(CombatPhase phase : combatPhaseList) {
            phase.render(g);
        }
    }

    public void tick(GameContainer gc) {
        for(int i = 0; i < combatPhaseList.size(); i++) {
            CombatPhase p = combatPhaseList.get(i);
            p.tick(gc);
            
            if(p.isExpired())
                removeCombatPhase(p);
        }
    }

    private void removeCombatPhase(CombatPhase p) {
        combatPhaseList.remove(p);
    }

    public void addCombatPhase(CombatPhase phase) {
        combatPhaseList.add(phase);
    }

    public static final CombatPhaseManager manager = new CombatPhaseManager();

    private CombatPhaseManager() {}

    public static final CombatPhaseManager get() { return manager; }
}
