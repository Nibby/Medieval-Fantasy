package hidden.indev0r.core.entity;

import hidden.indev0r.core.entity.animation.ActionType;
import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.GameContainer;

import java.util.HashMap;
import java.util.Map;

public class Actor extends Entity {

	//TODO: AI Object

	// Actor Attributes
	protected Map<STATS, String> propertyMap;

	private float   health;
	private float   healthMax;
	private float   mana;
	private float   manaMax;
	private float   experience;
	private float   experienceMax;
	private boolean isAlive;

	public Actor(Vector2f position) {
		super(position);
		propertyMap = new HashMap<>(0);
		for (STATS v : STATS.values()) propertyMap.put(v, v.getDefaultValue());


		health = 100;
		healthMax = 100;
		mana = 100;
		manaMax = 100;
		experience = 100;
		experienceMax = 100;
		isAlive = true;
	}

	@Override
	public void tick(GameContainer gc) {
		super.tick(gc);
		if(isAlive){
			//AI.create();
			//AI.tick();
		} else {
			forceActAction(ActionType.DEATH);
		}
	}

	public String getProperty(STATS property) {
		return propertyMap.get(property);
	}

	public float getHealth() {
		return health;
	}

	public float getHealthMax() {
		return healthMax;
	}

	public float getMana() {
		return mana;
	}

	public float getManaMax() {
		return manaMax;
	}

	public float getExperience() {
		return experience;
	}

	public float getExperienceMax() {
		return experienceMax;
	}

	public void setHealth(float health) {
		this.health = health;
	}

	public void setHealthMax(float healthMax) {
		this.healthMax = healthMax;
	}

	public void setMana(float mana) {
		this.mana = mana;
	}

	public void setManaMax(float manaMax) {
		this.manaMax = manaMax;
	}

	public void setExperience(float experience) {
		this.experience = experience;
	}

	public void setExperienceMax(float experienceMax) {
		this.experienceMax = experienceMax;
	}

	public enum STATS {
		HEALTH("100"),
		MANA("100"),
		EXPERIENCE("100");


		private String defaultValue;

		STATS(String standard) {
			this.defaultValue = standard;
		}

		public String getDefaultValue() {
			return defaultValue;
		}
	}


}
