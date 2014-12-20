package hidden.indev0r.game.state;


import hidden.indev0r.game.Camera;
import hidden.indev0r.game.entity.*;
import hidden.indev0r.game.entity.combat.phase.CombatPhaseManager;
import hidden.indev0r.game.gui.component.interfaces.GMapSupplier;
import hidden.indev0r.game.gui.menu.GGameOverlayMenu;
import hidden.indev0r.game.gui.menu.GMapTransitionOverlay;
import hidden.indev0r.game.gui.menu.GMenuManager;
import hidden.indev0r.game.map.Tile;
import hidden.indev0r.game.map.TileMap;
import hidden.indev0r.game.map.TileMapDatabase;
import hidden.indev0r.game.map.WarpType;
import hidden.indev0r.game.particle.ParticleManager;
import hidden.indev0r.game.reference.References;
import hidden.indev0r.game.sound.SE;
import hidden.indev0r.game.sound.SoundPlayer;
import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import javax.swing.*;
import java.util.List;

public class MainGameState extends BasicGameState implements GMapSupplier {

	//Game objects
	private Camera camera;

	//Currently focused map
	private TileMap map;

	//Globally constant player
	private Player player;

	//2D Elements
	private GMenuManager     menuMgr;
	private GGameOverlayMenu menuOverlay;

    private SoundPlayer soundPlayer;

    private long fadeTick = 0;
    private Color fadeHue = new Color(0f, 0f, 0f, 0f);
    private float fadeTickAlpha = 0f, fadeTickInterval = 0f;
    public static final int FADE_IN = 0, FADE_OUT = 1;
    private int fadeType;
    private boolean fading = false;

	@Override
	public int getID() {
		return GameStateID.MAIN_GAME_STATE.getID();
	}

	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
        soundPlayer = new SoundPlayer();

		camera = new Camera(0, 0);
		player = new Player(Actor.Faction.GLYSIA, ActorJob.WARRIOR, 11, 13);
		player.setLevel(1);
		camera.setTrackObject(player);

		map = TileMapDatabase.getTileMap("battle_arena");
		map.addEntity(player);

		menuMgr = new GMenuManager();
		menuMgr.setDisplayTopMenuOnly(false);
		menuMgr.addMenu((menuOverlay = new GGameOverlayMenu(this, player, this)));
		menuMgr.setTickTopMenuOnly(false);

        for(int i = 0; i < 100; i++) {
            int spawnX, spawnY;
            do {
                spawnX = (int) (Math.random() * 48);
                spawnY = (int) (Math.random() * 48);
            } while(map.isBlocked(null, spawnX, spawnY));

//            if(i == 5) {
//                Monster mon2 = MonsterDatabase.get("skeleton_1");
//                mon2.setPosition(spawnX, spawnY);
//                mon2.setPosition(16, 16);
//                map.addEntity(mon2);
//                continue;
//            }
            Monster mon = MonsterDatabase.get("skeleton");
            mon.setPosition(spawnX, spawnY);
            map.addEntity(mon);

        }

		announceName(map.getName());
        getMenuOverlay().showHint("W,A,S,D to move, left click to attack!", 3500, Color.white, 0);
    }

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		map.render(g, camera);

        CombatPhaseManager.get().render(g);
        ParticleManager.get().render(g);

        g.setColor(fadeHue);
        g.fillRect(0, 0, References.GAME_WIDTH, References.GAME_HEIGHT);

        menuMgr.render(g);
	}


	@Override
	public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int delta) throws SlickException {
		//This means menu wants to pull the focus, no need to do these... (except for debug menu)
		camera.tick();
		if (!menuMgr.isTickingTopMenuOnly() || !menuMgr.hasMenus()) {
			map.tick(gameContainer);
            CombatPhaseManager.get().tick(gameContainer);
            ParticleManager.get().tick(gameContainer);
		}

        if(fading) {
            if(System.currentTimeMillis() - fadeTick > fadeTickInterval) {
                switch(fadeType) {
                    case FADE_IN:
                        if(fadeHue.a < 1f) {
                            fadeHue.a += fadeTickAlpha;
                        }
                        else {
                            fading = false;
                            if(fadeHue.a > 1f) fadeHue.a = 1f;
                        }
                        break;
                    case FADE_OUT:
                        if(fadeHue.a > 0f) {
                            fadeHue.a -= fadeTickAlpha;
                        }
                        else {
                            fading = false;
                            if(fadeHue.a < 0f) fadeHue.a = 0f;
                        }
                        break;
                }
                fadeTick = System.currentTimeMillis();
            }
        }

        menuMgr.tick(gameContainer);
//        soundPlayer.getSoundSystem().checkFadeVolumes();
	}

	public void levelTransition(Entity entity, TileMap targetMap, int x, int y, WarpType type) {
		if (entity instanceof Player) {
			getMenuManager().addMenu(new GMapTransitionOverlay(getMenuManager(), targetMap, type, x, y,
					map.propertyExists("showName") && map.getProperty("showName").equals("true") ? targetMap.getName() : "", Color.white));
			return;
		}

		//For other entities
		forceWarpEntity(entity, targetMap, x, y, type);
	}

	public void forceWarpEntity(Entity entity, TileMap targetMap, int x, int y, WarpType type) {
		if (targetMap == null) {
			JOptionPane.showMessageDialog(null, "Specified warp map is null!", "Internal Error", JOptionPane.ERROR_MESSAGE);
			return;
		}

		this.map.removeEntity(entity);
		entity.setPosition(x, y);
		this.map = targetMap;
		targetMap.addEntity(entity);
	}

    public void fadeIn(Color color, int duration) {
        this.fadeHue = new Color(color.r, color.g, color.b, 0f);
        this.fadeTick = System.currentTimeMillis();
        this.fadeTickAlpha = (1 - fadeHue.a) / 100;
        fadeTickInterval = duration / 100;
        fadeType = FADE_IN;
        fading = true;
    }

    public void fadeOut(int duration) {
        fading = true;
        fadeTick = System.currentTimeMillis();
        this.fadeTickAlpha = fadeHue.a / 100;
        fadeTickInterval = duration / 100;
        fadeType = FADE_OUT;
    }

    public void setFadeHue(Color fadeHue) {
        this.fadeHue = fadeHue;
    }

	public void announceName(String text) {
		getMenuOverlay().showAnimatedScroll(text, text.split(" ").length * 750);
	}

	public Camera getCamera() {
		return camera;
	}

	public GGameOverlayMenu getMenuOverlay() {
		return menuOverlay;
	}

	public GMenuManager getMenuManager() {
		return menuMgr;
	}

    @Override
	public List<Entity> getEntitiesOnMap() {
		if (map == null) return null;
		return map.getEntities();
	}

	@Override
	public Tile getTile(int layer, Vector2f position) {
		if (map == null) return null;
		return map.getTile(layer, position);
	}

	@Override
	public boolean blockedAt(Vector2f position) {
		if (map == null) return true;
		return map.tileBlocked((int) (position.x), (int) (position.y));
	}

	@Override
	public Actor getCenterEntity() {
		return player;
	}

	@Override
	public int[][][] getTiles() {
		return map.getTileData();
	}

	@Override
	public boolean isNullTile(Vector2f vector2f) {
		if (map == null) return true;
		return map.isNullTile((int) vector2f.x, (int) vector2f.y);
	}

	public Player getPlayer() {
		return player;
	}

    public SoundPlayer getSoundPlayer() {
        return soundPlayer;
    }
}

