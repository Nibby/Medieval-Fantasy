package hidden.indev0r.game.entity.player;

import hidden.indev0r.game.BitFont;
import hidden.indev0r.game.MedievalLauncher;
import hidden.indev0r.game.entity.Actor;
import hidden.indev0r.game.entity.ActorJob;
import hidden.indev0r.game.entity.animation.ActionType;
import hidden.indev0r.game.entity.combat.phase.death.DeathType;
import hidden.indev0r.game.gui.component.interfaces.GStatsSupplier;
import hidden.indev0r.game.sound.SoundSet;
import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.*;

public class Player extends Actor implements GStatsSupplier {

	private InventoryPlayer playerInventory;
	private boolean controllable = true;
	private ActorJob job;


	public Player(Faction faction, ActorJob job, int x, int y) {
		super(faction, null, new Vector2f(x, y));

		combatHPColor = new Color(57, 181, 74);
		playerInventory = new InventoryPlayer();
		setSoundSet(SoundSet.player_warrior);
		setJob(job);
		setDeathType(DeathType.action_set);
		setStat(Stat.SPEED, 20);
		setSize(32, 32);
	}

	@Override
	public void render(Graphics g) {
		super.render(g);

		//Debug
		BitFont.render(g, (int) getX() + ", " + (int) getY(), 10, 140);
//        int i =0;
//        for(Stat stat : propertyMap.keySet()) {
//            BitFont.render(g, stat.name() + ": " + getStat(stat), 10, 160 + i * 20);
//            i++;
//        }
	}

	@Override
	public void tick (GameContainer gc) {
		super.tick(gc);

		if (getStat(Stat.EXPERIENCE) >= getStat(Stat.EXPERIENCE_MAX)) {
			setLevel(getLevel() + 1);
		}

		Input input = gc.getInput();

		if (!moving && controllable && MedievalLauncher.getInstance().getGameState().getMenuOverlay().isComponentEmpty()) {
			if (input.isKeyDown(Input.KEY_W) || input.isKeyDown(Input.KEY_UP)) {
				setMoveDestination(getX(), getY() - 1);
			} else if (input.isKeyDown(Input.KEY_A) || input.isKeyDown(Input.KEY_LEFT)) {
				setMoveDestination(getX() - 1, getY());
			} else if (input.isKeyDown(Input.KEY_S) || input.isKeyDown(Input.KEY_DOWN)) {
				setMoveDestination(getX(), getY() + 1);
			} else if (input.isKeyDown(Input.KEY_D) || input.isKeyDown(Input.KEY_RIGHT)) {
				setMoveDestination(getX() + 1, getY());
			}
		}

		if (input.isKeyPressed(Input.KEY_F1)) {
			setLevel(getLevel() + 1);
		}
	}

	@Override
	protected void calculateStats() {
		super.calculateStats();

        if(getStat(Stat.SPEED) > job.getSpeedMax())
            setStat(Stat.SPEED, job.getSpeedMax());

        if(getStat(Stat.STRENGTH) > job.getStrengthMax())
            setStat(Stat.STRENGTH, job.getStrengthMax());

        if(getStat(Stat.DEXTERITY) > job.getDexterityMax())
            setStat(Stat.DEXTERITY, job.getDexterityMax());

        if(getStat(Stat.INTELLIGENCE) > job.getIntelligenceMax())
            setStat(Stat.INTELLIGENCE, job.getIntelligenceMax());

        if(getStat(Stat.ATTACK_DAMAGE) > job.getAttackDamageMax())
            setStat(Stat.ATTACK_DAMAGE, job.getAttackDamageMax());

        if(getStat(Stat.DEFENSE) > job.getDefenseMax())
            setStat(Stat.DEFENSE, job.getDefenseMax());

        if(getStat(Stat.MAGIC_DEFENSE) > job.getMagicDefenseMax())
            setStat(Stat.MAGIC_DEFENSE, job.getMagicDefenseMax());
	}

	public void setJob(ActorJob job) {
		this.job = job;

		setActionSet(job.actionSet);
        setAttackType(job.getDefaultAttackType(), job.getDefaultAttackTypeParam());
		setStat(Stat.ATTACK_RANGE, job.getAttackRange());
	}

	public ActorJob getJob() {
		return job;
	}

    public void setLevel(int level) {
        if(level < 1) return;

        if(level - getLevel() == 1)
            addStat(Stat.LEVEL, 1);
        else
            setStat(Stat.LEVEL, level);

        //Calculate stats
        setStat(Stat.HEALTH_MAX, job.getMaxHPAtLevel(level));
        setStat(Stat.HEALTH, getStat(Stat.HEALTH_MAX));

        setStat(Stat.MANA_MAX, job.getMaxMPAtLevel(level));
        setStat(Stat.MANA, getStat(Stat.MANA_MAX));

        setStat(Stat.EXPERIENCE, 0);
        setStat(Stat.EXPERIENCE_MAX, ActorJob.getRequiredEXPAtLevel(level));

        setStat(Stat.STRENGTH, job.getStrengthAtLevel(level));
        setStat(Stat.DEXTERITY, job.getDexterityAtLevel(level));
        setStat(Stat.INTELLIGENCE, job.getIntelligenceAtLevel(level));
        setStat(Stat.SPEED, job.getSpeedAtLevel(level));
        setStat(Stat.ATTACK_DAMAGE, job.getAttackDamageAtLevel(level));
        setStat(Stat.DEFENSE, job.getDefenseAtLevel(level));
        setStat(Stat.MAGIC_DEFENSE, job.getMagicDefenseAtLevel(level));
    }

	@Override
	public void move(int x, int y) {
		super.move(x, y);
		MedievalLauncher.getInstance().getGameState().getSoundPlayer().setListenerPosition((int) getX(), (int) getY());
	}

	public boolean isControllable() {
		return controllable;
	}

	public void setControllable(boolean controllable) {
		this.controllable = controllable;
	}

	@Override
	public Image getPreviewImage() {
		return actionMap.get(ActionType.STATIC_DOWN).getFrame(0);
	}

	public InventoryPlayer getInventory() {
		return playerInventory;
	}
}




