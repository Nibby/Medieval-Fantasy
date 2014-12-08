package hidden.indev0r.core.gui.component.hud;

import hidden.indev0r.core.entity.Entity;
import hidden.indev0r.core.gui.component.base.GComponent;
import hidden.indev0r.core.gui.component.interfaces.GMapSupplier;
import hidden.indev0r.core.map.Tile;
import hidden.indev0r.core.reference.References;
import hidden.indev0r.core.texture.Textures;
import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.*;
import org.newdawn.slick.geom.Circle;

import java.util.List;

public class GComponent$CircularMap extends GComponent {

    private static int MAP_PIXEL_SIZE = 3;

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
	private Entity       centerEntity;

    private float offsetX = 0, offsetY = 0;

	public GComponent$CircularMap(Vector2f pos, GMapSupplier supplier) {
		super(pos);
		this.width = 100;
		this.height = 100;
		this.mapSupplier = supplier;
		entities = mapSupplier.getEntitiesOnMap();
        centerEntity = mapSupplier.getCenterEntity();

        mapTileWidth = width / MAP_PIXEL_SIZE;
        mapTileHeight = height / MAP_PIXEL_SIZE;
	}

	@Override
	public void render(Graphics g) {
		g.setColor(Color.black);
		g.fillOval(position.x, position.y, width, height);

        offsetX = -(centerEntity.getX() * MAP_PIXEL_SIZE - width / 2);
        offsetY = -(centerEntity.getY() * MAP_PIXEL_SIZE - height / 2);

        Image minimapImg = Textures.UI.MINIMAP_IMAGE;
        Graphics mapG = null;
        try {
            mapG = minimapImg.getGraphics();

            mapG.setColor(COLOR_CLEAR);
            mapG.fillOval(0, 0, minimapImg.getWidth(), minimapImg.getHeight());

            //Map
            int mix = (int) -(Math.abs(offsetX) / MAP_PIXEL_SIZE);
            int miy = (int) -(Math.abs(offsetY) / MAP_PIXEL_SIZE);
            int max = mix + width / MAP_PIXEL_SIZE + 3;
            int may = miy + height / MAP_PIXEL_SIZE + 3;

            int[][][] mapTiles = mapSupplier.getTiles();
            if(mapTiles != null) {
                for(int x = mix; x < max; x++) {
                    for(int y = miy; y < may; y++) {
                        if(mapSupplier.isNullTile(new Vector2f(x, y))) {
                            mapG.setColor(COLOR_NULL_TILE);
                        } else if(mapSupplier.blockedAt(new Vector2f(x, y))) {
                            mapG.setColor(COLOR_BLOCKED_TILE);
                        } else {
                            mapG.setColor(COLOR_NON_BLOCKED_TILE);
                        }

                        mapG.fillRect(x * MAP_PIXEL_SIZE + offsetX, y * MAP_PIXEL_SIZE + offsetY, MAP_PIXEL_SIZE, MAP_PIXEL_SIZE);
                    }
                }
            }


            //Player entity
            mapG.setColor(Color.white);
            mapG.fillRect(centerEntity.getX() * MAP_PIXEL_SIZE + offsetX,
                    centerEntity.getY() * MAP_PIXEL_SIZE + offsetY, MAP_PIXEL_SIZE, MAP_PIXEL_SIZE);

//            System.out.println(mix * MAP_PIXEL_SIZE + " - " + centerEntity.getX() * MAP_PIXEL_SIZE);
        } catch (SlickException e) {
            e.printStackTrace();
        }

        g.drawImage(minimapImg, position.x + 1, position.y + 2);
	}

	@Override
	public void tick(GameContainer gc) {
		entities = mapSupplier.getEntitiesOnMap();

	}

	private void setCenterEntity(Entity entity) {
		this.centerEntity = entity;
	}

    public void scaleUp() {
        if(MAP_PIXEL_SIZE + 1 <= 5) MAP_PIXEL_SIZE++;
    }

    public void scaleDown() {
        if(MAP_PIXEL_SIZE - 1 >= 2) MAP_PIXEL_SIZE--;
    }
}
