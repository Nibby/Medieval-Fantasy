package hidden.indev0r.game.gui.component;

import hidden.indev0r.game.BitFont;
import hidden.indev0r.game.Colors;
import hidden.indev0r.game.gui.component.base.GComponent;
import hidden.indev0r.game.gui.menu.GMenu;
import hidden.indev0r.game.reference.References;
import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

/**
 * Created by MrDeathJockey on 14/12/16.
 */
public class GComponent$CinematicText extends GComponent {

    public static final int TYPE_FADING = 0, TYPE_CREDIT = 1;
    private int type;
    private String[] textContent;
    private Color[] textColor;

    private long timerTick;
    private int timerDuration;
    private float fadeAlpha = 0f;
    private int phase = 0;
    private int tick = 0;

    public GComponent$CinematicText(int type, int duration, String[] textContent) {
        super(new Vector2f(0, 0));
        this.type = type;

        this.textContent = new String[textContent.length];
        this.timerDuration = duration;
        this.textColor = new Color[textContent.length];
        int i = 0;
        for(String text : textContent) {
            String[] textData = text.split("\\|");
            if(textData.length == 2) {
                textColor[i] = Colors.valueOf(textData[0]).getColor();
                this.textContent[i] = textData[1];
            } else {
                textColor[i] = Color.white;
                this.textContent[i] = textData[0];
            }
            i++;
        }
    }

    @Override
    public void onAdd(GMenu menu) {
        super.onAdd(menu);
        timerTick = System.currentTimeMillis();
        tick = 0;
    }

    public void render(Graphics g) {
        switch(type) {
            case TYPE_FADING:
                for(int i = 0; i < textContent.length; i++) {
                    int fx = References.GAME_WIDTH / 2 - BitFont.widthOf(textContent[i], 16) / 2;
                    int fy = References.GAME_HEIGHT / 2 - BitFont.heightOf(textContent) / 2 + i * 20;

                    BitFont.render(g, textContent[i], fx, fy, textColor[i], 16, fadeAlpha);
                }
                break;
        }
    }

    public void tick(GameContainer gc) {
        switch(type) {
            case TYPE_FADING:
                if(phase == 0) {
                    if(System.currentTimeMillis() - timerTick > 10) {
                        if(fadeAlpha < 1f) fadeAlpha += 0.05f;
                        else phase++;
                        timerTick = System.currentTimeMillis();
                    }
                } else if(phase == 1) {
                    if(System.currentTimeMillis() - timerTick > timerDuration) {
                        phase++;
                        timerTick = System.currentTimeMillis();
                    }
                } else if(phase == 2) {
                    if(System.currentTimeMillis() - timerTick > 10) {
                        if(fadeAlpha > 0f) fadeAlpha -= 0.05f;
                        else setRemoved(true);
                        timerTick = System.currentTimeMillis();
                    }
                }
                break;
        }
    }
}
