package hidden.indev0r.game.entity;

import hidden.indev0r.game.Camera;
import hidden.indev0r.game.MedievalLauncher;
import hidden.indev0r.game.entity.animation.ActionSet;
import hidden.indev0r.game.entity.animation.ActionSetDatabase;
import hidden.indev0r.game.entity.animation.ActionType;
import hidden.indev0r.game.entity.combat.AttackType;
import hidden.indev0r.game.entity.combat.DamageModel;
import hidden.indev0r.game.entity.combat.phase.AbstractCombatChannelPhase;
import hidden.indev0r.game.gui.Cursor;
import hidden.indev0r.game.gui.component.interfaces.GStatsSupplier;
import hidden.indev0r.game.map.MapDirection;
import hidden.indev0r.game.map.Tile;
import hidden.indev0r.game.texture.Textures;
import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.*;

import javax.swing.*;

public class Player extends Actor implements GStatsSupplier {

    private boolean controllable = true;
    private Job job;

    public Player(Faction faction, Job job, int x, int y) {
        super(faction, new Vector2f(x, y));

        setJob(job);
        setStat(Stat.SPEED, 20);
        setSize(32, 32);
    }

	@Override
	public void render(Graphics g) {
		super.render(g);
	}

	@Override
	public void tick
            (GameContainer gc) {
		super.tick(gc);

        if(getStat(Stat.EXPERIENCE) >= getStat(Stat.EXPERIENCE_MAX)) {
            setLevel(getLevel() + 1);
        }

        Input input = gc.getInput();

        if(controllable && input.isMousePressed(Input.MOUSE_LEFT_BUTTON)
                && MedievalLauncher.getInstance().getGameState().getMenuOverlay().isComponentEmpty()
                && Cursor.INTERACT_INSTANCE == null) {

            int mx = input.getMouseX();
            int my = input.getMouseY();
            Camera camera = MedievalLauncher.getInstance().getGameState().getCamera();
            int tileX = (int) (mx - camera.getOffsetX()) / Tile.TILE_SIZE;
            int tileY = (int) (my - camera.getOffsetY()) / Tile.TILE_SIZE;
            boolean blocked = map.isBlocked(this, tileX, tileY);

            if(!blocked) {
                setMoveDestination(tileX, tileY);
            }
        }

        if(!moving && controllable) {
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

        //Debug
        {
            if (input.isKeyPressed(Input.KEY_F1)) {
//					forceActAction(ActionType.ATTACK_RIGHT);
                setLevel(getLevel() + 1);
            }
            if (input.isKeyPressed(Input.KEY_F2)) {
//					forceActAction(ActionType.ATTACK_LEFT);
            }
            if (input.isKeyPressed(Input.KEY_F3)) {
                forceActAction(ActionType.ATTACK_UP);
            }
            if (input.isKeyPressed(Input.KEY_F4)) {
                forceActAction(ActionType.ATTACK_DOWN);
            }

            if (input.isKeyPressed(Input.KEY_F5)) {
                forceActAction(ActionType.CAST_RIGHT);
            }
            if (input.isKeyPressed(Input.KEY_F6)) {
                forceActAction(ActionType.CAST_LEFT);
            }
            if (input.isKeyPressed(Input.KEY_F7)) {
                forceActAction(ActionType.CAST_UP);
            }
            if (input.isKeyPressed(Input.KEY_F8)) {
                forceActAction(ActionType.CAST_DOWN);
            }

            if (input.isKeyPressed(Input.KEY_F9)) {
                forceActAction(ActionType.USE_SPECIAL);
            }
            if (input.isKeyPressed(Input.KEY_F10)) {
                forceActAction(ActionType.DEATH);
            }

            if (input.isKeyPressed(Input.KEY_F12)) {
                String option = JOptionPane.showInputDialog(null, "Enter action setStat id: ");
                if (option != null && !option.isEmpty() && !option.contains(" ")) {
                    setActionSet(ActionSetDatabase.get(Integer.parseInt(option)));
                }
            }
        }
	}

    public void setJob(Job job) {
        this.job = job;

        setActionSet(job.actionSet);

        setStat(Stat.ATTACK_RANGE, job.getAttackRange());
    }

    public Job getJob() {
        return job;
    }

    public void setLevel(int level) {
        if(level < 1) return;

        setStat(Stat.LEVEL, level);
        //Calculate stats
        setStat(Stat.HEALTH_MAX, job.getMaxHPAtLevel(level));
        setStat(Stat.HEALTH, getStat(Stat.HEALTH_MAX));

        setStat(Stat.MANA_MAX, job.getMaxMPAtLevel(level));
        setStat(Stat.MANA, getStat(Stat.MANA_MAX));

        setStat(Stat.EXPERIENCE, 0);
        setStat(Stat.EXPERIENCE_MAX, Job.getRequiredEXPAtLevel(level));

        setStat(Stat.STRENGTH, job.getStrengthAtLevel(level));
        setStat(Stat.DEXTERITY, job.getDexterityAtLevel(level));
        setStat(Stat.INTELLIGENCE, job.getIntelligenceAtLevel(level));
        setStat(Stat.SPEED, job.getSpeedAtLevel(level));

        System.out.println(getStat(Stat.STRENGTH));
    }

	@Override
	public void move(int x, int y) {
		super.move(x, y);
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

    public enum Job {
        MAGE(0, "Mage") {
            @Override
            public int getMaxHPAtLevel(int level) {
                return 14 + level + (int) Math.pow(level, 2) / 10;
            }

            @Override
            public int getMaxMPAtLevel(int level) {
                return 17 + level + (int) (Math.pow(level, 2) / 2);
            }

            @Override
            public int getAttackRange() {
                return 5;
            }

            @Override
            public int getStrengthAtLevel(int level) {
                return 0;
            }

            @Override
            public int getDexterityAtLevel(int level) {
                return 0;
            }

            @Override
            public int getIntelligenceAtLevel(int level) {
                return 0;
            }

            @Override
            public int getSpeedAtLevel(int level) {
                return 0;
            }

            @Override
            public AttackType getDefaultAttackType() {
                return AttackType.normal_mage;
            }
        },

        WARRIOR(1, "Warrior") {
            @Override
            public int getMaxHPAtLevel(int level) {
                return 18 + level + ((int) Math.pow(level, 2) / 3);
            }

            @Override
            public int getMaxMPAtLevel(int level) {
                return 9 + level + (level + (int) Math.pow(level, 2) / 13);
            }

            @Override
            public int getAttackRange() {
                return 1;
            }

            @Override
            public int getStrengthAtLevel(int level) {
                return 5 + (level - 1) / 2;
            }

            @Override
            public int getDexterityAtLevel(int level) {
                return 3 + (level - 1) / 3;
            }

            @Override
            public int getIntelligenceAtLevel(int level) {
                return 1 + (level - 2) / 4;
            }

            @Override
            public int getSpeedAtLevel(int level) {
                return 15 + (level - 1) / 3;
            }

            @Override
            public AttackType getDefaultAttackType() {
                return AttackType.normal_warrior;
            }
        },

        ROGUE(2, "Rogue") {
            @Override
            public int getMaxHPAtLevel(int level) {
                return 17 + level + (int) Math.pow(level, 2) / 4;
            }

            @Override
            public int getMaxMPAtLevel(int level) {
                return 10 + level + (int) Math.pow(level, 2) / 9;
            }

            @Override
            public int getAttackRange() {
                return 1;
            }

            @Override
            public int getStrengthAtLevel(int level) {
                return 0;
            }

            @Override
            public int getDexterityAtLevel(int level) {
                return 0;
            }

            @Override
            public int getIntelligenceAtLevel(int level) {
                return 0;
            }

            @Override
            public int getSpeedAtLevel(int level) {
                return 0;
            }

            @Override
            public AttackType getDefaultAttackType() {
                return AttackType.normal_rogue;
            }
        };

        private ActionSet actionSet;
        private String    name;

        private Job(int actionSetID, String name) {
            this.actionSet = ActionSetDatabase.get(actionSetID);
            this.name = name;
        }

        public abstract int getMaxHPAtLevel(int level);
        public abstract int getMaxMPAtLevel(int level);

        public abstract int getAttackRange();
        public abstract int getStrengthAtLevel(int level);
        public abstract int getDexterityAtLevel(int level);
        public abstract int getIntelligenceAtLevel(int level);
        public abstract int getSpeedAtLevel(int level);

        public abstract AttackType getDefaultAttackType();

        public static int getRequiredEXPAtLevel(int level) {
            switch(level) {
                case 1:     return 100;
                case 2:     return 150;
                case 3:     return 200;
                case 4:     return 350;
                case 5:     return 470;
                case 6:     return 600;
                case 7:     return 800;
                case 8:     return 1200;
                case 9:     return 1650;
                case 10:    return 2000;

                case 11:    return 2800;
                case 12:    return 3700;
                case 13:    return 4650;
                case 14:    return 5950;
                case 15:    return 6500;
                case 16:    return 8500;
                case 17:    return 10500;
                case 18:    return 12100;
                case 19:    return 14500;
                case 20:    return 17500;

                case 21:    return 23000;
                case 22:    return 28000;
                case 23:    return 35000;
                case 24:    return 47000;
                case 25:    return 60000;
                case 26:    return 75000;
                case 27:    return 88000;
                case 28:    return 100000;
                case 29:    return 150000;
                case 30:    return 210000;

                case 31:    return 300000;
                case 32:    return 400000;
                case 33:    return 520000;
                case 34:    return 650000;
                case 35:    return 800000;
                case 36:    return 1000000;
                case 37:    return 1500000;
                case 38:    return 2200000;
                case 39:    return 3500000;
                case 40:    return 5000000;

                case 41:    return 7500000;
                case 42:    return 12500000;
                case 43:    return 18500000;
                case 44:    return 26500000;
                case 45:    return 39500000;
                case 46:    return 50000000;
                case 47:    return 83000000;
                case 48:    return 100000000;
                case 49:    return 150000000;
                case 50:    return 250000000;

                case 51:    return 380000000;
                case 52:    return 490000000;
                case 53:    return 600000000;
                case 54:    return 800000000;
                default:    return 1000000000;
            }
        }
    }
}




