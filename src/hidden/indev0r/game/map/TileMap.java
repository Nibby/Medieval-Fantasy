package hidden.indev0r.game.map;

import hidden.indev0r.game.Camera;
import hidden.indev0r.game.MedievalLauncher;
import hidden.indev0r.game.entity.Actor;
import hidden.indev0r.game.entity.Entity;
import hidden.indev0r.game.entity.Player;
import hidden.indev0r.game.entity.npc.script.Script;
import hidden.indev0r.game.reference.References;
import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.util.pathfinding.PathFindingContext;
import org.newdawn.slick.util.pathfinding.TileBasedMap;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A TileMap is a map on which actors and other instances are able to move and interact with.
 * It provides a graphic 'stage'.
 *
 * Created by MrDeathJockey on 14/12/3.
 */
public class TileMap implements TileBasedMap {

	//Each map houses a series of 'zones' or 'regions' denoted with a special ID
	private Map<String, TileMapZone> mapZones      = new HashMap<>();
	//Map properties
	private Map<String, String>      properties    = new HashMap<>();
	//Map warp points
	private List<MapWarpPoint>       warpPointList = new ArrayList<>();

	//
	private List<Entity> entities = new ArrayList<>();
	private Player player;

	private String name, identifierName;
	private int width, height, layers;
	private int[][][] tileData;

	/**
	 * Each tiled map instance is a playable map
	 *
	 * @param name     The name of map to be displayed upon enter (if applicable)
	 * @param id       A unique identifier to register the map with (in the database)
	 * @param tileData The 3-dimensional tile ID information, with [] in order of layer, x, y
	 */
	public TileMap(String name, String id, int[][][] tileData, String property) {
		this.name = name;
		this.identifierName = id;
		this.tileData = tileData;

		layers = tileData.length;
		width = tileData[0].length;
		height = tileData[0][0].length;

		//Assigning properties
		String[] propertySegments = property.split(";");
		for (String segment : propertySegments) {
			String[] kvPair = segment.split("=");

			properties.put(kvPair[0], (kvPair.length > 1) ? kvPair[1] : "");
		}
	}

	public void tick(GameContainer gc) {
        for(int i = 0; i < entities.size(); i++) {
            Entity e = entities.get(i);
            if(e != null) {
                e.tick(gc);
            }
        }
	}

	public void render(Graphics g, Camera camera) {

        //Camera bounds check, we don't want to render tiles that are out of the observable region
		int mix = (int) -camera.getOffsetX() / Tile.TILE_SIZE - 1;
		int miy = (int) -camera.getOffsetY() / Tile.TILE_SIZE - 1;
		int max = mix + (References.GAME_WIDTH / References.DRAW_SCALE / Tile.TILE_SIZE + 3);
		int may = miy + (References.GAME_HEIGHT / References.DRAW_SCALE / Tile.TILE_SIZE + 3);

		for (int layer = 0; layer < tileData.length; layer++) {
			for (int x = mix; x < max; x++) {
				for (int y = miy; y < may; y++) {

                    //Because the camera bounds will yield invalid results, this will check for only valid results
                    if (x < 0 || x > tileData[0].length - 1 || y < 0 || y > tileData[0][0].length - 1) continue;

                    Tile tile = Tile.getTile(tileData[layer][x][y]);
                    if (tile != null) {
                        tile.render(g,
                                x * Tile.TILE_SIZE + camera.getOffsetX(), y * Tile.TILE_SIZE + camera.getOffsetY());
                    }
                }
			}

			//Second layer are where entities are rendered
			if (layer == 1) {
				for (Entity e : entities) {
                    if(e.getX() > mix && e.getX() < max && e.getY() > miy && e.getY() < may) {
                        //Depth sorting needed
                        e.render(g);
                    }
				}
			}
		}
	}

	public void addEntity(Entity e) {
		if (e == null) return;
		entities.add(e);
		e.setCurrentMap(this);
        
        stepOn(e, (int) e.getX(), (int) e.getY(), (int) e.getX(), (int) e.getY());

		if (e instanceof Player) {
			this.player = (Player) e;
		}
	}

	public boolean isBlocked(Entity reference, int x, int y) {
		if (x < 0 || x > tileData[0].length - 1 || y < 0 || y > tileData[0][0].length - 1) return true;
        if(tileBlocked(x, y)) return true;
        if(entityBlocked(reference, x, y)) return true;
		return false;
	}

    private boolean entityBlocked(Entity reference, int x, int y) {
        if (x < 0 || x > tileData[0].length - 1 || y < 0 || y > tileData[0][0].length - 1) return true;
        for(Entity e : entities) {
            Vector2f[] blockedTiles = e.getBlockedTiles();
            for(Vector2f pos : blockedTiles) {
                if(pos == null) continue;
                if(!e.equals(reference) && e.isSolid() && pos.x == x && pos.y == y) return true;
            }
        }
        return false;
    }

    public boolean tileBlocked(int x, int y) {
        if (x < 0 || x > tileData[0].length - 1 || y < 0 || y > tileData[0][0].length - 1) return true;
        boolean[] solid = new boolean[layers];

        int nulls = 0;
        for(int l= 0; l < layers; l++) {
            Tile tile = Tile.getTile(tileData[l][x][y]);
            solid[l] = (tile != null && tile.isSolid());
            if(tile == null) nulls++;
        }

        if(nulls == layers) return true;

        for(boolean b : solid)  {
            if(b) return true;
        }

        return false;
    }

    public boolean isNullTile(int x, int y) {

        if (x < 0 || x > tileData[0].length - 1 || y < 0 || y > tileData[0][0].length - 1) return true;
        int nullCount = 0;
        for(int l= 0; l < layers; l++) {
            Tile tile = Tile.getTile(tileData[l][x][y]);

            if(tile == null) nullCount++;
        }

        return nullCount == layers;
    }

    /*
        When entity steps on a given x, y tile
     */
	public void stepOn(Entity entity, int oldX, int oldY, int x, int y) {
		if (x < 0 || x > tileData[0].length - 1 || y < 0 || y > tileData[0][0].length - 1) return;
        if (oldX < 0 || oldX > tileData[0].length - 1 || oldY < 0 || oldY > tileData[0][0].length - 1) return;

		for (int layer = tileData.length - 1; layer > -1; layer--) {
            Tile tileOld = Tile.getTile(tileData[layer][oldX][oldY]);
            if(tileOld != null) {
                tileOld.steppedOut(entity);
            }

            Tile tile = Tile.getTile(tileData[layer][x][y]);
            if (tile != null) {
                tile.steppedOn(entity);
            }
		}

        //Warp
        if(entity instanceof Player) {
            for(MapWarpPoint warp : warpPointList) {
                Point origin = warp.getOrigin();
                if(player == null) return;
                if(origin.x == player.getX() && origin.getY() == player.getY()) {
                    Point target = warp.getTarget();
                    TileMap targetMap = TileMapDatabase.getTileMap(warp.getTargetMap());
                    MedievalLauncher.getInstance().getGameState().levelTransition(entity, targetMap, target.x, target.y, WarpType.MOVEMENT);
                    return;
                }
            }
        }

        if(entity instanceof Player) {
            for(Entity e : entities) {
                if(e instanceof Actor) {
                    boolean near = ((Actor) e).withinApproachRange((Player) entity);
                    if(near)
                        ((Actor) e).executeScript(Script.Type.approach);
                }
            }

        }

        for(String zoneKey : mapZones.keySet()) {
            TileMapZone zone = mapZones.get(zoneKey);
            if(!zone.isEntityInZone(entity)
                    && zone.inBounds(x, y, entity.getWidth() / Tile.TILE_SIZE, entity.getHeight() /  Tile.TILE_SIZE)) {
                zone.enterZone(entity);
            }

            if(zone.isEntityInZone(entity) && !zone.inBounds(x, y, entity.getWidth() / Tile.TILE_SIZE, entity.getHeight() / Tile.TILE_SIZE)) {
                zone.leaveZone(entity);
            }
        }
	}

	public boolean propertyExists(String propertyKey) {
		return properties.get(propertyKey) != null;
	}

	public String getProperty(String propertyKey) {
		return properties.get(propertyKey);
	}

	public String getName() {
		return name;
	}

	public String getIdentifierName() {
		return identifierName;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public int getLayers() {
		return layers;
	}

	public int[][][] getTileData() {
		return tileData;
	}

	public TileMapZone getZone(String zoneID) {
		return mapZones.get(zoneID);
	}

	public void addZone(TileMapZone zone) {
		if (mapZones.get(zone.getZoneID()) != null) {
			JOptionPane.showMessageDialog(null,
					"Another zone with identifier '" + zone.getZoneID() + "' was already registered!",
					"Internal Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		mapZones.put(zone.getZoneID(), zone);
	}

	public void addWarpPoint(MapWarpPoint warp) {
		warpPointList.add(warp);
	}

	public List<MapWarpPoint> getWarpPointList() {
		return warpPointList;
	}

    public List<Entity> getEntities() {
        return entities;
    }

    public Tile getTile(int layer, Vector2f position) {
        if(layer < 0 || layer > tileData.length - 1) return null;
        if(position.x < 0 || position.x > tileData[0].length - 1 || position.y < 0 || position.y > tileData[0][0].length - 1) return null;

        return Tile.getTile(tileData[layer][((int) position.x)][((int) position.y)]);
    }

    public void removeEntity(Entity entity) {
        entities.remove(entity);
        
        if(entity instanceof Player) {
            this.player = null;
        }
    }

    @Override
    public int getWidthInTiles() {
        return width;
    }

    @Override
    public int getHeightInTiles() {
        return height;
    }

    @Override
    public void pathFinderVisited(int x, int y) {

    }

    @Override
    public boolean blocked(PathFindingContext context, int x, int y) {
        return isBlocked(null, x, y);
    }

    @Override
    public float getCost(PathFindingContext context, int x, int y) {
        return 0;
    }
}
