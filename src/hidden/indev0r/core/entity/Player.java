package hidden.indev0r.core.entity;

import hidden.indev0r.core.MedievalLauncher;
import hidden.indev0r.core.entity.animation.ActionSet;
import hidden.indev0r.core.entity.animation.ActionSetDatabase;
import hidden.indev0r.core.entity.animation.ActionType;
import hidden.indev0r.core.gui.component.hud.GComponent$AnimatedScroll;
import hidden.indev0r.core.gui.component.interfaces.GStatsSupplier;
import hidden.indev0r.core.map.MapDirection;
import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;

import javax.swing.*;

/*
    I hope you don't mind me testing some of my own code.
    Although we might be branching soon by the looks of the progress ^_^
 */
public class Player extends Actor implements GStatsSupplier {

    private Job job;

    public Player(Faction faction, Job job, int x, int y) {
        super(faction, new Vector2f(x, y));

        setJob(job);
        setSize(32, 32);
    }

	@Override
	public void render(Graphics g) {
		super.render(g);
	}

	@Override
	public void tick(GameContainer gc) {
		super.tick(gc);

		Input input = gc.getInput();
		if (!moving) {
			int x = (int) getX();
			int y = (int) getY();

			if (input.isKeyDown(Input.KEY_W)) {
				currentDirection = MapDirection.UP;
				setMotion(ActionType.WALK_UP);
				moving = true;
				if (!map.isBlocked(this, x, y - 1)) {
					move(x, y - 1);
				}
			}
			if (input.isKeyDown(Input.KEY_A)) {
				currentDirection = MapDirection.LEFT;
				setMotion(ActionType.WALK_LEFT);
				moving = true;
				if (!map.isBlocked(this, x - 1, y)) {
					move(x - 1, y);
				}
			}
			if (input.isKeyDown(Input.KEY_S)) {
				currentDirection = MapDirection.DOWN;
				setMotion(ActionType.WALK_DOWN);
				moving = true;
				if (!map.isBlocked(this, x, y + 1)) {
					move(x, y + 1);
				}
			}
			if (input.isKeyDown(Input.KEY_D)) {
				currentDirection = MapDirection.RIGHT;
				setMotion(ActionType.WALK_RIGHT);
				moving = true;
				if (!map.isBlocked(this, x + 1, y)) {
					move(x + 1, y);
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
                    MedievalLauncher.getInstance().getGameState().getMenuOverlay().showAnimatedScroll("Woof", 5000);
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
	}

    public void setJob(Job job) {
        this.job = job;

        setActionSet(job.actionSet);
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
    }

	@Override
	public void move(int x, int y) {
		super.move(x, y);
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
        };

        private ActionSet actionSet;
        private String    name;
        private Job(int actionSetID, String name) {
            this.actionSet = ActionSetDatabase.get(actionSetID);
            this.name = name;
        }

        public abstract int getMaxHPAtLevel(int level);

        public abstract int getMaxMPAtLevel(int level);
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




