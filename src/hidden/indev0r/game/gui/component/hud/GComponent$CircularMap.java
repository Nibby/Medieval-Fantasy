package hidden.indev0r.game.gui.component.hud;

import hidden.indev0r.game.entity.Actor;
import hidden.indev0r.game.entity.Entity;
import hidden.indev0r.game.entity.FactionUtil;
import hidden.indev0r.game.entity.NPC;
import hidden.indev0r.game.gui.component.base.GComponent;
import hidden.indev0r.game.gui.component.interfaces.GMapSupplier;
import hidden.indev0r.game.map.Tile;
import hidden.indev0r.game.texture.Textures;
import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.*;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

import java.awt.*;
import java.util.List;

public class GComponent$CircularMap extends GComponent {

    private static int MAP_PIXEL_SIZE = 3;

    private static final Color COLOR_ENEMY = Color.red;
    private static final Color COLOR_SELF = Color.green;
    private static final Color COLOR_FRIENDLY = new Color(0, 138, 255);

    private static final Color COLOR_LIQUID_WATER = new Color(0, 155, 211);
    private static final Color COLOR_LIQUID_LAVA = new Color(237, 28, 36);

    private static final Color COLOR_CLEAR = Color.black;
    private static final Color COLOR_NULL_TILE = Color.black;
    private static final Color COLOR_BLOCKED_TILE = Color.lightGray;
    private static final Color COLOR_NON_BLOCKED_TILE = Color.darkGray;
    private static final Color COLOR_CENTER_ENTITY = Color.white;
    private static final Color COLOR_HOSTILE_ENTITY = Color.red;
    private static final Color COLOR_NEUTRAL_ENTITY = Color.yellow;

    //tile dimension that the map shows
    private int mapTileWidth, mapTileHeight;

	private GMapSupplier mapSupplier;
	private List<Entity> entities;
	private Actor centerActor;

    private float offsetX = 0, offsetY = 0;

	public GComponent$CircularMap(Vector2f pos, GMapSupplier supplier) {
		super(pos);
		this.width = 100;
		this.height = 100;
		this.mapSupplier = supplier;
		entities = mapSupplier.getEntitiesOnMap();
        centerActor = mapSupplier.getCenterEntity();

        mapTileWidth = width / MAP_PIXEL_SIZE;
        mapTileHeight = height / MAP_PIXEL_SIZE;
	}

	@Override
	public void render(Graphics g) {
		g.setColor(Color.black);
		g.fillOval(position.x, position.y, width, height);

        offsetX = -(centerActor.getX() * MAP_PIXEL_SIZE - width / 2);
        offsetY = -(centerActor.getY() * MAP_PIXEL_SIZE - height / 2);

        Image minimapImg = Textures.UI.MINIMAP_IMAGE;
        Graphics mapG = null;
        try {
            mapG = minimapImg.getGraphics();

            mapG.setColor(COLOR_CLEAR);
            mapG.fillOval(0, 0, minimapImg.getWidth(), minimapImg.getHeight());


            //Map
            int mix = (int) -(offsetX / MAP_PIXEL_SIZE);
            int miy = (int) -(offsetY / MAP_PIXEL_SIZE);
            int max = mix + Math.round((float) width / MAP_PIXEL_SIZE) + 3;
            int may = miy + Math.round((float) height / MAP_PIXEL_SIZE) + 3;

            int[][][] mapTiles = mapSupplier.getTiles();
            if(mapTiles != null) {
                for(int x = mix; x < max; x++) {
                    for(int y = miy; y < may; y++) {
                        if (x < 0 || x > mapTiles[0].length - 1 || y < 0 || y > mapTiles[0][0].length - 1) continue;

                        if(mapSupplier.isNullTile(new Vector2f(x, y))) {
                            mapG.setColor(COLOR_NULL_TILE);
                        } else if(mapSupplier.blockedAt(new Vector2f(x, y))) {
                            mapG.setColor(COLOR_BLOCKED_TILE);
                        } else {
                            mapG.setColor(COLOR_NON_BLOCKED_TILE);
                        }
                        
                        for(int l = 0; l < mapTiles.length; l++) {
                            Tile tile = Tile.getTile(mapTiles[l][x][y]);
                            if(tile == null) continue;
                            if(tile.getId() == 337 || tile.getId() == 401) {
                                mapG.setColor(COLOR_LIQUID_WATER);
                            }
                            if(tile.getId() == 335 || tile.getId() == 399) {
                                mapG.setColor(COLOR_LIQUID_LAVA);
                            }
                        }

                        mapG.fillRect(x * MAP_PIXEL_SIZE + offsetX, y * MAP_PIXEL_SIZE + offsetY, MAP_PIXEL_SIZE, MAP_PIXEL_SIZE);
                    }
                }
            }

            //NPC & monsters
            List<Entity> entities = mapSupplier.getEntitiesOnMap();
            for(Entity e : entities) {
                if(e.getX() > mix && e.getX() < max && e.getY() > miy && e.getY() < may) {
                    if(e instanceof NPC) {
                        boolean isEnemy = FactionUtil.isEnemy(centerActor.getFaction(), ((NPC) e).getFaction());
                        boolean isAlly = FactionUtil.isAlly(centerActor.getFaction(), ((NPC) e).getFaction());

                        if(((NPC) e).getMinimapColor() != null) {
                            mapG.setColor(((NPC) e).getMinimapColor());
                        } else if(isEnemy) {
                            mapG.setColor(COLOR_ENEMY);
                        } else {
                            mapG.setColor(COLOR_FRIENDLY);
                        }
                    }

                    if(e.isVisibleOnScreen())
                        mapG.fillOval(e.getX() * MAP_PIXEL_SIZE + offsetX, e.getY() * MAP_PIXEL_SIZE + offsetY,
                            MAP_PIXEL_SIZE * e.getWidth() / Tile.TILE_SIZE, MAP_PIXEL_SIZE * e.getHeight() / Tile.TILE_SIZE);
                }
            }


            //Player entity
            mapG.setColor(COLOR_SELF);
            mapG.fillRect(centerActor.getX() * MAP_PIXEL_SIZE + offsetX,
                    centerActor.getY() * MAP_PIXEL_SIZE + offsetY, MAP_PIXEL_SIZE, MAP_PIXEL_SIZE);
        } catch (SlickException e) {
            e.printStackTrace();
        }

        g.drawImage(minimapImg, position.x + 1, position.y + 2);
	}

	@Override
	public void tick(GameContainer gc) {
		entities = mapSupplier.getEntitiesOnMap();

	}

	private void setCenterActor(Actor actor) {
		this.centerActor = actor;
	}

    public void scaleUp() {
        if(MAP_PIXEL_SIZE + 1 <= 5) MAP_PIXEL_SIZE++;
    }

    public void scaleDown() {
        if(MAP_PIXEL_SIZE - 1 >= 2) MAP_PIXEL_SIZE--;
    }
}
