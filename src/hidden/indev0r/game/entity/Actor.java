package hidden.indev0r.game.entity;

import hidden.indev0r.game.entity.animation.ActionType;
import hidden.indev0r.game.entity.npc.script.Script;
import hidden.indev0r.game.gui.Cursor;
import hidden.indev0r.game.map.Tile;
import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.util.pathfinding.AStarPathFinder;
import org.newdawn.slick.util.pathfinding.Path;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

public class Actor extends Entity {

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

    //TODO: AI Object
    private static final int INTERACT_TILE_DISTANCE = 2;
    /*
        Actor scripts are kept here rather than solely stored in NPC class
        because certain special monsters will have scripts also.
     */
    protected Map<Script.Type, Script> scripts = new HashMap<>();

    // Actor Attributes
	protected Map<Stat, Integer> propertyMap;
    protected Faction faction;
    protected AI ai;

    private boolean isAlive;


	public Actor(Faction faction, Vector2f position) {
		super(position);
		propertyMap = new HashMap<>(0);
		for (Stat v : Stat.values()) propertyMap.put(v, v.getDefaultValue());
        this.faction = faction;
		isAlive = true;
	}

	@Override
	public void tick(GameContainer gc) {
		super.tick(gc);
        if(!(this instanceof Player))
            ai.tick(map, this);

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

        if(isAlive){
            //AI.create();
            //AI.tick();
        } else {
            if(actionMap != null && action != ActionType.DEATH) {
                forceActAction(ActionType.DEATH);
            }
        }

	}

    private void calculateStats() {
        targetMoveSpeed = (getStat(Stat.SPEED) + getStat(Stat.SPEED_BONUS)) / 10;
    }

    public boolean withinInteractRange(Actor actor) {

        Rectangle bounds = new Rectangle(getX() - INTERACT_TILE_DISTANCE  + 1, getY() - INTERACT_TILE_DISTANCE + 1,
                                         getWidth() / Tile.TILE_SIZE + INTERACT_TILE_DISTANCE, getHeight() / Tile.TILE_SIZE + INTERACT_TILE_DISTANCE);
        Rectangle other  = new Rectangle(actor.getX(), actor.getY(), actor.getWidth() / Tile.TILE_SIZE, actor.getHeight() / Tile.TILE_SIZE);

        return other.intersects(bounds);
    }

    @Override
    public void setPosition(int x, int y) {
        super.setPosition(x, y);
    }

    public void setAI(AI ai) {
        this.ai = ai;
    }

    public void addScript(Script script) {
        if(scripts.get(script.getType()) != null) {
            JOptionPane.showMessageDialog(null, "'" + script.getType() + "' script already exists for actor!",
                    "Internal Error", JOptionPane.ERROR_MESSAGE);
            throw new IllegalArgumentException("Script already exists!");
        }

        scripts.put(script.getType(), script);
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
		return getStat(Stat.HEALTH_MAX);
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
		MANA(0),
        MANA_MAX(0),
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
        EVASION_BONUS(0)
        ;


		private int defaultValue;

		Stat(int standard) {
			this.defaultValue = standard;
		}

		public int getDefaultValue() {
			return defaultValue;
		}
	}
}
