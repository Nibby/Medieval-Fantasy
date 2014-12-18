package hidden.indev0r.game.entity.combat;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MrDeathJockey on 14/12/18.
 */
public class DamageModel {

    private List<DamageType> damageType = new ArrayList<>();
    private List<Integer> damageList = new ArrayList<>();
    private List<Boolean> damageCrit = new ArrayList<>();
    private List<String> damageFx = new ArrayList<>();

    public DamageModel() {

    }

    public void addHit(DamageType type, int damage, boolean critical, String fxParams) {
        damageType.add(type);
        damageList.add(damage);
        damageCrit.add(critical);
        damageFx.add(fxParams);
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

    public boolean isCritical(int hit) {
        return damageCrit.get(hit);
    }

    public String getFxParam(int hit) {
        return damageFx.get(hit);
    }
}
