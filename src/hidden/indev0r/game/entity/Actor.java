package hidden.indev0r.game.entity;

import hidden.indev0r.game.MedievalLauncher;
import hidden.indev0r.game.entity.ai.AI;
import hidden.indev0r.game.entity.ai.AIList;
import hidden.indev0r.game.entity.animation.ActionType;
import hidden.indev0r.game.entity.combat.AttackType;
import hidden.indev0r.game.entity.combat.DamageModel;
import hidden.indev0r.game.entity.combat.DamageType;
import hidden.indev0r.game.entity.combat.phase.CombatPhase;
import hidden.indev0r.game.entity.combat.phase.CombatPhaseManager;
import hidden.indev0r.game.entity.combat.phase.channel.AbstractCombatChannelPhase;
import hidden.indev0r.game.entity.combat.phase.death.CombatDeathPhase;
import hidden.indev0r.game.entity.combat.phase.death.DeathType;
import hidden.indev0r.game.entity.combat.phase.hit.AbstractCombatHitPhase;
import hidden.indev0r.game.entity.combat.phase.hit.CombatHitPhase;
import hidden.indev0r.game.entity.npc.script.Script;
import hidden.indev0r.game.gui.Cursor;
import hidden.indev0r.game.map.MapDirection;
import hidden.indev0r.game.map.Tile;
import hidden.indev0r.game.sound.SE;
import hidden.indev0r.game.sound.SoundSet;
import hidden.indev0r.game.sound.SoundType;
import hidden.indev0r.game.util.Util;
import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.util.pathfinding.AStarPathFinder;
import org.newdawn.slick.util.pathfinding.Mover;
import org.newdawn.slick.util.pathfinding.Path;
import org.w3c.dom.Element;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Actor extends Entity implements Mover {

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
    private Element aiBlock;

    protected Color nameColor, minimapColor;
    protected boolean isAlive;

    protected AttackType attackType;
    protected String attackTypeParam;

    protected Actor combatTarget;
    protected List<CombatPhase> combatPhaseList = new ArrayList<>();
    protected DeathType deathType = DeathType.crumble;
    protected SoundSet soundSet;
    protected boolean running = false;
    protected long combatLastSwing = 0;
    protected long deathTime;

    public boolean combatHurt = false;
    public long combatHurtTick = System.currentTimeMillis();
    public Color combatHPColor = new Color(1f, 0f, 0f, 1f);
    public Color combatHPLaceColor = new Color(1f, 0.5f, 0f, 1f);
    public int combatHPLaceLength, combatHPBarLength;
    public boolean showingCombatHPGauge = false;

    //Cloning purposes
    public Actor(Actor a) {
        super(a);

        this.interactRange = a.interactRange;
        this.approachRange = a.approachRange;
        this.scripts = a.scripts;
        this.faction = a.faction;
        this.propertyMap = Util.cloneStatMap(a.propertyMap);
        this.aiBlock = a.aiBlock;
        this.ai = AIList.valueOf(aiBlock.getAttribute("type")).getInstance(this);
        this.ai.make(aiBlock);
        this.nameColor = a.nameColor;
        this.minimapColor = a.minimapColor;
        this.isAlive = a.isAlive;
        this.attackType = a.attackType;
        this.combatTarget = a.combatTarget;
        this.combatPhaseList = new ArrayList<>();
        this.combatLastSwing = a.combatLastSwing;
        this.deathType = a.deathType;
        this.running = a.running;
        this.soundSet = a.soundSet;
        this.deathTime = a.deathTime;
        this.attackTypeParam = a.attackTypeParam;
    }

    public Actor(Faction faction, Element aiBlock, Vector2f position) {
		super(position);
		propertyMap = new HashMap<>(0);
        this.aiBlock = aiBlock;
		for (Stat v : Stat.values()) propertyMap.put(v, v.getDefaultValue());
        this.faction = faction;
		isAlive = true;
        nameColor = Color.white;
        minimapColor = Color.blue;
	}

    public void render(Graphics g) {
        boolean shouldRender = true;

        if(!combatPhaseList.isEmpty()) {
            for(int i = 0; i < combatPhaseList.size(); i++) {
                CombatPhase phase = combatPhaseList.get(i);
                if(phase.isInitiator(this) && phase.overrideInitiatorRender()) {
                    shouldRender = false;
                    break;
                }

                if(phase instanceof CombatHitPhase &&
                        phase.overrideInitiatorRender() && ((CombatHitPhase) phase).isTarget(this)) {
                    shouldRender = false;
                    break;
                }
            }
        }

        if(shouldRender && isAlive) {
            super.render(g);
        } else {
            if(actionMap != null)
                getActionMap().get(ActionType.DEATH).getLastFrame().draw(getRenderX(), getRenderY());
        }
    }

    protected void calculateStats() {
        targetMoveSpeed = (getStat(Stat.SPEED) + getStat(Stat.SPEED_BONUS)) / 7;

        if(getHealth() <= 0 && !isDead()) die();
    }

	@Override
	public void tick(GameContainer gc) {
		super.tick(gc);

        if(getX() != moveDestination.x || getY() != moveDestination.y) {
            AStarPathFinder aStar = new AStarPathFinder(map.getPathMap(), 16, false);

            map.getPathMap().setReferenceEntity(this);
            Path path = aStar.findPath(this, (int) getX(), (int) getY(), (int) moveDestination.x, (int) moveDestination.y);
            map.getPathMap().setReferenceEntity(null);

            if(path != null && path.getLength() > 0) {
                Path.Step step = path.getStep(1);
                if(!moving) move(step.getX(), step.getY());
            } else {
                setMoveDestination(getX(), getY());
            }
        }

        if(combatHurt && !(this instanceof Player)) {
            if(System.currentTimeMillis() - combatHurtTick > 50) {
                if(combatHPLaceLength > combatHPBarLength) {
                    combatHPLaceLength -= 2;
                    combatHurtTick = System.currentTimeMillis();
                }
                else {
                    if(System.currentTimeMillis() - combatHurtTick > 1500) combatHurt = false;
                }
            }
        }

        calculateStats();

        if(isAlive){
            if(!(this instanceof Player))
                ai.tick(map);
        }

	}

    public Actor getCombatTarget() {
        return combatTarget;
    }

    public void setCombatTarget(Actor combatTarget) {
        this.combatTarget = combatTarget;
    }

    //Starts attacking a tile, and any entity that walk into it
    public void combatStart(int x, int y) {
        int combatHitInterval = 1200 - (getStat(Stat.DEXTERITY) + getStat(Stat.DEXTERITY_BONUS)) * 20;

        if(System.currentTimeMillis() - combatLastSwing > combatHitInterval) {
            Actor actor = map.getPotentialCombatActor(this, x, y);
            if (actor != null) {

                combatTarget = actor;
                combatChannelStart(attackType);
                combatLastSwing = System.currentTimeMillis();
            }
        }
    }

    public void combatStart(Actor target) {
        if (!withinRange(target, getAttackRange()))
            return;
        this.combatTarget = target;

        int combatHitInterval = 1200 - (getStat(Stat.DEXTERITY) + getStat(Stat.DEXTERITY_BONUS)) * 20;

        if(System.currentTimeMillis() - combatLastSwing > combatHitInterval) {
            setFacingDirection(MapDirection.turnToFace(this, target));
            combatChannelStart(attackType);
            combatLastSwing = System.currentTimeMillis();
        }
    }

    public void combatChannelStart(AttackType type) {
        DamageModel model = new DamageModel();
        //2 is how many attacks actor will deal
        for(int i = 0; i < 1; i++) {
            //PLACEHOLDER
            DamageType damageType = DamageType.normal;

            int damage = getStat(Stat.ATTACK_DAMAGE) + getStat(Stat.ATTACK_DAMAGE_BONUS) / 2
                    + (int) (Math.random() * (getStat(Stat.ATTACK_DAMAGE_BONUS) / 2))
                    + getStat(Stat.STRENGTH_BONUS)
                    + (int) (Math.random() * (getStat(Stat.STRENGTH) / 2) + getStat(Stat.STRENGTH) / 2);

            if(damageType.equals(DamageType.normal))
                damage -= combatTarget.getDefense();
            else
                damage -= combatTarget.getMagicDefense();
            if(damage < 0) damage = 0;

            if(combatTarget != null) {
                damage = damageType.processDamage(damage, combatTarget, this);
            }

            boolean critical = (int) (Math.random() * 100) < getStat(Stat.LUCK) / 4
                    || (int) (Math.random() * 1000) < 3;

            if(critical) {
                damage = (int) (damage * 1.8f);
            }

            model.addHit(DamageType.normal, damage, critical, attackTypeParam);

        }

        AbstractCombatChannelPhase channelPhase = type.getChannelPhase(model, this, combatTarget);
        CombatPhaseManager.get().addCombatPhase(channelPhase);
    }

    public void combatChannelEnd(DamageModel model, AttackType type, int hitIndex) {
        if(combatTarget == null) return;

        AbstractCombatHitPhase hitPhase = type.getHitPhase(model, this, combatTarget, hitIndex);
        CombatPhaseManager.get().addCombatPhase(hitPhase);
    }

    public void combatHurt(Actor dmgDealer, int currentHit, DamageModel model, int damage) {
        if(dmgDealer == null) return;

        if(damage > 0) {

            combatHurt = true;
            combatHurtTick = System.currentTimeMillis();

            int totalLength = getWidth();
            float percentageBefore = (float) getHealth() / (float) getHealthMax();
            combatHPLaceLength = (int) ((float) totalLength * percentageBefore);
            if (combatHPLaceLength < 0) combatHPLaceLength = 0;

            float percentageAfter = (float) (getHealth() - damage) / (float) getHealthMax();
            combatHPBarLength = (int) ((float) totalLength * percentageAfter);
            if (combatHPBarLength < 0) combatHPBarLength = 0;

            if(!showingCombatHPGauge && !(this instanceof Player)) {
                MedievalLauncher.getInstance().getGameState().getMenuOverlay().showActorHPGauge(this);
            }
        }

        deductStat(Stat.HEALTH, damage);
        if (getStat(Stat.HEALTH) < 0) {
            setStat(Stat.HEALTH, 0);
            CombatDeathPhase deathPhase = attackType.getDeathPhase(model, this, combatTarget);
            if(deathPhase != null) {
                CombatPhaseManager.get().addCombatPhase(deathPhase);
            }
        }

        executeScript(Script.Type.hurt);
        if (ai != null)
            ai.onHurt(dmgDealer, model);
    }

    public void combatEnd() {
    }

    public void die() {
        CombatPhaseManager.get().addCombatPhase(deathType.newInstance(null, this, combatTarget));
        playSound(deathType.getSound());
        executeScript(Script.Type.death);
        deathTime = System.currentTimeMillis();
        isAlive = false;
        setStat(Stat.HEALTH, 0);
    }

    public Image getCurrentImage() {
        if(getActionMap() == null) {
            return (getCurrentDirection().equals(MapDirection.LEFT)) ? spriteFlipped : sprite;
        } else {
            return getActionMap().get(action).getCurrentFrame();
        }
    }

    public void addCombatPhase(CombatPhase phase) {
        combatPhaseList.add(phase);
    }

    public void removeCombatPhase(CombatPhase phase) {
        combatPhaseList.remove(phase);
    }

    public boolean withinRange(float x, float y, float w, float h, int range) {
        return new Rectangle(
                getPosition().x - (range - 1) * Tile.TILE_SIZE,
                getPosition().y - (range - 1) * Tile.TILE_SIZE,
                getWidth() - Tile.TILE_SIZE + (range * 2 - 1) * Tile.TILE_SIZE,
                getHeight() - Tile.TILE_SIZE + (range * 2 - 1) * Tile.TILE_SIZE
        ).intersects(new Rectangle(x, y, w, h));
    }

    public boolean withinRange(Actor actor, int range) {
        return withinRange(actor.getPosition().x, actor.getPosition().y, actor.getWidth(), actor.getHeight(), range);
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public DeathType getDeathType() {
        return deathType;
    }

    public void setDeathType(DeathType deathType) {
        this.deathType = deathType;
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

    public void setAttackType(AttackType type, String param) {
        this.attackType = type;
        this.attackTypeParam = param;
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

    public void playSound(SoundType key) {
        if(soundSet == null) return;

        SE se = soundSet.getRandomSound(key);
        if(se != null)
            se.play(this);
    }

    public void onApproach(Actor actor) {
        if(ai != null)
            ai.onApproach(actor);
    }

    public SoundSet getSoundSet() {
        return soundSet;
    }

    public void setSoundSet(SoundSet soundSet) {
        this.soundSet = soundSet;
    }

    public void playSound(SE sound) {
        if(sound != null)
            sound.play(this);
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
        addStat(stat, value, true);
    }

    private void addStat(Stat stat, int value, boolean verbose) {

        if(verbose) {
            Color col = Color.green;
            String msg = "+" + value + " " + stat.getName();

            if(stat.equals(Stat.LEVEL) && getStat(stat) < value) {
                msg = "LEVEL UP!";
                col = Color.yellow;
            }

            MedievalLauncher.getInstance().getGameState().getMenuOverlay() .showStatusVerbose(this, col, msg, (stat.equals(Stat.LEVEL)) ? 10 : 200);
        }

        setStat(stat, getStat(stat) + value);
    }

    public void deductStat(Stat stat, int value) {
        deductStat(stat, value, true);
    }

    public void deductStat(Stat stat, int value, boolean verbose) {

        if(verbose) {
            Color col = stat.getColor();
            String msg = "-" + value + " " + stat.getName();

            if(stat.equals(Stat.HEALTH) && value <= 0) {
                msg = "BLOCKED!";
                col = Color.white;
            }

            MedievalLauncher.getInstance().getGameState().getMenuOverlay() .showStatusVerbose(this, col, msg, 10);
        }
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

    public long getDeathTime() {
        return deathTime;
    }

    public int getDefense() {
        return getStat(Stat.DEFENSE) + getStat(Stat.DEFENSE_BONUS);
    }

    public int getAttackDamage() {
        return getStat(Stat.ATTACK_DAMAGE) + getStat(Stat.ATTACK_DAMAGE_BONUS);
    }

    public int getAccuracy() {
        return getStat(Stat.ACCURACY) + getStat(Stat.ACCURACY_BONUS);
    }

    public int getEvasion() {
        return getStat(Stat.EVASION) + getStat(Stat.EVASION_BONUS);
    }

    public int getMagicDefense() {
        return getStat(Stat.MAGIC_DEFENSE) + getStat(Stat.MAGIC_DEFENSE_BONUS);
    }

    public List<CombatPhase> getCombatPhaseList() {
        return combatPhaseList;
    }

    public enum Stat {
		HEALTH(Color.red,"HP","Your current hit points",1),
        HEALTH_MAX(Color.red,"HP Max","Your maximum hit points",1),
        HEALTH_MAX_BONUS(Color.green,"","",0),

        MANA(new Color(95, 205, 228),"MP","Your current magic points",0),
        MANA_MAX(new Color(95, 205, 228),"MP Max","Your maximum magic points",0),
        MANA_MAX_BONUS(Color.green,"","",0),

        EXPERIENCE(Color.green,"EXP","Your current experience points;towards the next level",0),
        EXPERIENCE_MAX(Color.green,"EXP Max","EXP required to attain;the next level",1),

        LEVEL(Color.white, "Level", "Your current level", 1),
        GOLD(Color.yellow, "Gold", "How much gold in your possession", 0),

        //Affects damage dealt and taken
        ATTACK_DAMAGE(Color.white, "ATT", "Attack:;;Your current attack damage", 0),
        ATTACK_DAMAGE_BONUS(Color.green, "", "", 0),
        DEFENSE(Color.lightGray, "DEF", "Defense:;;Your current resistance to;physical damage", 0),
        DEFENSE_BONUS(Color.green, "", "", 0),
        MAGIC_DEFENSE(Color.cyan, "MDF", "Magic Defense:;;Your current resistance to;magical damage", 0),
        MAGIC_DEFENSE_BONUS(Color.green, "", "", 0),

        //Affects movement speed
        SPEED(Color.white, "SPD", "Speed:;;Affects your movement speed", 10),
        SPEED_BONUS(Color.green, "", "", 0),

        //Affects rate of attack
        DEXTERITY(Color.white, "DEX", "Dexterity:;;Affects your attack speed", 0),
        DEXTERITY_BONUS(Color.green, "", "", 0),

        //Affects ability scaling (wizards)
        INTELLIGENCE(Color.white, "INT", "Affects your magical combat;abilities and damage", 0),
        INTELLIGENCE_BONUS(Color.green, "", "", 0),

        //Affects attack damage
        STRENGTH(Color.white, "STR", "Strength:;;Affects your physical combat;abilities and damage", 0),
        STRENGTH_BONUS(Color.green, "", "", 0),

        //Misc.
        LUCK(Color.magenta, "LUK", "???", 0),
        LUCK_BONUS(Color.green, "", "", 0),

        ACCURACY(Color.white, "ACC", "Accuracy:;;Affects your chance to;successfully deal damage", 0),
        ACCURACY_BONUS(Color.green, "", "", 0),

        EVASION(Color.white, "EVA", "Evasion:;;affects your chance to;block incoming attacks", 0),
        EVASION_BONUS(Color.green, "", "", 0),

        ATTACK_RANGE(Color.white, "ATT R.", "Attack Range:;;Affects how far your;attacks will reach", 1),
        ATTACK_RANGE_BONUS(Color.green, "", "", 0)
        ;

		private int defaultValue;
        private Color color;
        private String name;
        private String description;

		Stat(Color col, String attrName, String description, int standard) {
            this.color = col;
			this.defaultValue = standard;
            this.name =  attrName;
            this.description = description;
		}

		public int getDefaultValue() {
			return defaultValue;
		}

        public String getName() {
            return name;
        }

        public String getDescription() {
            return description;
        }

        public Color getColor() {
            return color;
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
