package hidden.indev0r.game.entity.combat;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MrDeathJockey on 14/12/18.
 */
public class DamageModel {

    private List<DamageType> damageType = new ArrayList<>();
    private List<Integer> damageList = new ArrayList<>();

    public DamageModel() {

    }

    public void addHit(DamageType type, int damage) {
        damageType.add(type);
        damageList.add(damage);
    }

    public int getDamage(int hit) {
        return damageList.get(hit);
    }

    public DamageType getDamageType(int hit) {
        return damageType.get(hit);
    }

    public int getHits() {
        return damageList.size();
    }

}
