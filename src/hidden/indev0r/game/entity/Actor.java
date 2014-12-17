package hidden.indev0r.game.entity;

import hidden.indev0r.game.entity.ai.AI;
import hidden.indev0r.game.entity.animation.ActionType;
import hidden.indev0r.game.entity.combat.AttackType;
import hidden.indev0r.game.entity.combat.DamageModel;
import hidden.indev0r.game.entity.combat.DamageType;
import hidden.indev0r.game.entity.combat.phase.*;
import hidden.indev0r.game.entity.npc.script.Script;
import hidden.indev0r.game.gui.Cursor;
import hidden.indev0r.game.map.Tile;
import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.util.pathfinding.AStarPathFinder;
import org.newdawn.slick.util.pathfinding.Path;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Actor extends Entity {

    protected int interactRange = 2, approachRange = 2;

    /*
        Actor scripts are kept here rather than solely stored in NPC class
        because certain special monsters will have scripts also.
     */
    protected Map<Script.Type, Script> scripts = new HashMap<>();
    protected Faction faction;
    // Actor Attributes
	protected Map<Stat, Integer> propertyMap;
    protected AI ai;

    protected Color nameColor, minimapColor;
    protected boolean isAlive;

    /* MAYBE A PLACEHOLDER */
    protected AttackType attackType = AttackType.normal_warrior;
    protected Actor combatTarget;

    protected List<CombatPhase> combatPhaseList = new ArrayList<>();

    public Actor(Faction faction, Vector2f position) {
		super(position);
		propertyMap = new HashMap<>(0);
		for (Stat v : Stat.values()) propertyMap.put(v, v.getDefaultValue());
        this.faction = faction;
		isAlive = true;
        nameColor = Color.white;
        minimapColor = Color.blue;
	}

    public void render(Graphics g) {
        super.render(g);
        boolean shouldRender = true;
        if(!combatPhaseList.isEmpty()) {
            for(int i = 0; i < combatPhaseList.size(); i++) {
                CombatPhase phase = combatPhaseList.get(i);

                if(phase.isInitiator(this) && phase.overrideInitiatorRender()) {
                    phase.renderInitiator(g, this);
                    shouldRender = false;
                }

                if(phase instanceof CombatHitPhase && ((CombatHitPhase) phase).isTarget(this)) {
                    if(((CombatHitPhase) phase).overrideTargetRender())
                        ((CombatHitPhase) phase).renderTarget(g, this);
                    ((CombatHitPhase) phase).renderHitEffects(g, this);
                    shouldRender = false;
                }
            }
        }

        if(shouldRender) {
            super.render(g);
            for(int i = 0; i < combatPhaseList.size(); i++) {
                CombatPhase phase = combatPhaseList.get(i);

                if(phase instanceof CombatHitPhase && ((CombatHitPhase) phase).isTarget(this)) {
                    ((CombatHitPhase) phase).renderHitEffects(g, this);
                }
            }
        }
    }

	@Override
	public void tick(GameContainer gc) {
		super.tick(gc);

        if(getX() != moveDestination.x || getY() != moveDestination.y) {
            AStarPathFinder aStar = new AStarPathFinder(map, 16, false);
            Path path = aStar.findPath(null, (int) getX(), (int) getY(), (int) moveDestination.x, (int) moveDestination.y);
            if(path != null && path.getLength() > 0) {
                Path.Step step = path.getStep(1);
                if(!moving) move(step.getX(), step.getY());
            } else {
                setMoveDestination(getX(), getY());
            }
        }

        calculateStats();

        if(!combatPhaseList.isEmpty()) {
            for(int i = 0; i < combatPhaseList.size(); i++) {
                CombatPhase phase = combatPhaseList.get(i);

                phase.tick(gc, this);

                if(phase.isExpired()) {
                    removeCombatPhase(phase);
                }
            }
        }

        if(isAlive){
            if(!(this instanceof Player))
                ai.tick(map, this);
        } else {
            if(actionMap != null && action != ActionType.DEATH) {
                forceActAction(ActionType.DEATH);
            }
        }

	}

    public Actor getCombatTarget() {
        return combatTarget;
    }

    public void setCombatTarget(Actor combatTarget) {
        this.combatTarget = combatTarget;
    }

    public void combatStart(Actor target) {
        this.combatTarget = target;
        combatChannelStart(attackType);

    }

    public void combatChannelStart(AttackType type) {
        AbstractCombatChannelPhase channelPhase = type.getChannelPhase(this);
        addCombatPhase(channelPhase);
    }

    public void combatChannelEnd(AttackType type) {
        //计算战斗信息，包括玩家双手 VS 单手武器等。。。

        DamageModel model = new DamageModel();
        //2 is how many attacks actor will deal
        for(int i = 0; i < 2; i++) {
            int damage = getStat(Stat.ATTACK_DAMAGE) + getStat(Stat.ATTACK_DAMAGE_BONUS) / 2
                    + (int) (Math.random() * (getStat(Stat.ATTACK_DAMAGE_BONUS) / 2))
                    + getStat(Stat.STRENGTH_BONUS)
                    + (int) (Math.random() * (getStat(Stat.STRENGTH) / 2) + getStat(Stat.STRENGTH) / 2);
            model.addHit(DamageType.normal, damage);
        }

        AbstractCombatHitPhase hitPhase = type.getHitPhase(this, combatTarget);
        hitPhase.setDamageModel(model);
        addCombatPhase(hitPhase);
    }

    public void combatHurt(Actor dmgDealer, int currentHit, DamageModel model) {
        deductStat(Stat.HEALTH, model.getDamage(currentHit));
        System.out.println(getStat(Stat.HEALTH));
    }

    public void combatEnd() {
    }

    protected void calculateStats() {
        targetMoveSpeed = (getStat(Stat.SPEED) + getStat(Stat.SPEED_BONUS)) / 7;
    }

    public void addCombatPhase(CombatPhase phase) {
        combatPhaseList.add(phase);
    }

    public void removeCombatPhase(CombatPhase phase) {
        combatPhaseList.remove(phase);
    }

    public boolean withinRange(Actor actor, int range) {
        int boundWidth = getWidth() / Tile.TILE_SIZE - 1 + range;
        if(boundWidth < 1) boundWidth = 1;

        int boundHeight = getHeight() / Tile.TILE_SIZE - 1 + range;
        if(boundHeight < 1) boundHeight = 1;

        int otherWidth = actor.getWidth() / Tile.TILE_SIZE - 1;
        if(otherWidth < 1) otherWidth = 1;

        int otherHeight = actor.getHeight() / Tile.TILE_SIZE  - 1;
        if(otherHeight < 1) otherHeight = 1;

        Rectangle bounds = new Rectangle(getX() - range + 1, getY() - range + 1, boundWidth, boundHeight);
        Rectangle other  = new Rectangle(actor.getX(), actor.getY(), otherWidth, otherHeight);

        return other.intersects(bounds);
    }

    public int getInteractRange() {
        return interactRange;
    }

    public void setInteractRange(int interactRange) {
        this.interactRange = interactRange;
    }

    public int getApproachRange() {
        return approachRange;
    }

    public void setApproachRange(int approachRange) {
        this.approachRange = approachRange;
    }

    public int getAttackRange() {
        return getStat(Stat.ATTACK_RANGE) + getStat(Stat.ATTACK_RANGE_BONUS);
    }

    @Override
    public void setPosition(int x, int y) {
        super.setPosition(x, y);
    }

    public void setAI(AI ai) {
        this.ai = ai;
    }

    public void addScript(Script.Type type, Script script) {
        if(scripts.get(type) != null) {
            JOptionPane.showMessageDialog(null, "'" + type + "' script already exists for actor!",
                    "Internal Error", JOptionPane.ERROR_MESSAGE);
            throw new IllegalArgumentException("Script already exists!");
        }

        scripts.put(type, script);
    }

    public void executeScript(Script.Type scriptType) {
        Cursor.INTERACT_INSTANCE = null;
        Script script = scripts.get(scriptType);
        if(script != null && script.isFinished())
            script.execute(this);
    }

    public void executeScript(Script script) {
        Cursor.INTERACT_INSTANCE = null;
        if(script != null)
            script.execute(this);
    }

    public void die() {
        setStat(Stat.HEALTH, 0);
        isAlive = false;
    }

    public boolean isDead() {
        return !isAlive;
    }

    public Color getMinimapColor() {
        return minimapColor;
    }

    public void setMinimapColor(Color minimapColor) {
        this.minimapColor = minimapColor;
    }

    public Color getNameColor() {
        return nameColor;
    }

    public void setNameColor(Color nameColor) {
        this.nameColor = nameColor;
    }

    public boolean hasScript(Script.Type type) {
        return scripts.get(type) != null;
    }

	public Integer getProperty(Stat property) {
		return propertyMap.get(property);
	}

	public int getHealth() {
		return getStat(Stat.HEALTH);
	}

	public int getHealthMax() {
		return getStat(Stat.HEALTH_MAX) + getStat(Stat.HEALTH_MAX_BONUS);
	}

	public int getMana() {
		return getStat(Stat.MANA);
	}

	public int getManaMax() {
		return getStat(Stat.MANA_MAX);
	}

	public int getExperience() {
		return getStat(Stat.EXPERIENCE);
	}

	public int getExperienceMax() {
		return getStat(Stat.EXPERIENCE_MAX);
	}

    public int getLevel() { return getStat(Stat.LEVEL); }

	public void setStat(Stat stat, int value) {
        propertyMap.put(stat, value);
    }

    public void addStat(Stat stat, int value) {
        setStat(stat, getStat(stat) + value);
    }

    public void deductStat(Stat stat, int value) {
        setStat(stat, getStat(stat) - value);
    }

    public Integer getStat(Stat stat) {

        return propertyMap.get(stat);
    }

    public Faction getFaction() {
        return faction;
    }

    public void setFaction(Faction faction) {
        this.faction = faction;
    }

    public enum Stat {
		HEALTH(1),
        HEALTH_MAX(1),
        HEALTH_MAX_BONUS(0),

        MANA(0),
        MANA_MAX(0),
        MANA_MAX_BONUS(0),

        EXPERIENCE(0),
        EXPERIENCE_MAX(1),

        LEVEL(1),
        GOLD(0),

        //Affects damage dealt and taken
        ATTACK_DAMAGE(0),
        ATTACK_DAMAGE_BONUS(0),
        DEFENSE(0),
        DEFENSE_BONUS(0),

        //Affects movement speed
        SPEED(10),
        SPEED_BONUS(0),

        //Affects rate of attack
        DEXTERITY(0),
        DEXTERITY_BONUS(0),

        //Affects ability scaling (wizards)
        INTELLIGENCE(0),
        INTELLIGENCE_BONUS(0),

        //Affects attack damage
        STRENGTH(0),
        STRENGTH_BONUS(0),

        //Misc.
        LUCK(0),
        LUCK_BONUS(0),

        ACCURACY(0),
        ACCURACY_BONUS(0),

        EVASION(0),
        EVASION_BONUS(0),

        ATTACK_RANGE(1),
        ATTACK_RANGE_BONUS(0)
        ;

		private int defaultValue;


		Stat(int standard) {
			this.defaultValue = standard;
		}

		public int getDefaultValue() {
			return defaultValue;
		}

    }

    public enum Faction {

        NEUTRAL,
        GLYSIA,
        NAYA,
        UNDEAD,
        DEMON,
        HUMAN,

        //Special entities like signs and event points
        NONE
        ;

    }
}
