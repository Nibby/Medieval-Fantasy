package hidden.indev0r.game.entity.npc.script;

import hidden.indev0r.game.MedievalLauncher;
import hidden.indev0r.game.entity.Actor;
import hidden.indev0r.game.sound.BGM;
import org.w3c.dom.Element;

/**
 * Created by MrDeathJockey on 14/12/17.
 */
public class Command$BGM extends Command {

    private String mode;
    private String bgmRef;
    private int fadeDuration = 5000;
    private boolean playLooped = false;

    @Override
    public void make(CommandBlock block, Actor actor, Element e) {
        super.make(block, actor, e);

        mode = e.getAttribute("action");
        bgmRef = e.getAttribute("bgm");

        if(mode.equals("PLAY")) {
            if(e.hasAttribute("loop"))
                playLooped = Boolean.parseBoolean(e.getAttribute("loop"));

        }
        if(e.hasAttribute("duration"))
            fadeDuration = Integer.parseInt(e.getAttribute("duration"));
    }

    @Override
    public void exec(Actor actor, CommandBlock block) {
        super.exec(actor, block);
        BGM bgm = BGM.valueOf(bgmRef);
        switch(mode) {
            case "PLAY":
                MedievalLauncher.getInstance().getGameState().getSoundPlayer().playBGM(bgm, playLooped);
                MedievalLauncher.getInstance().getGameState().getMenuOverlay().showBGMTrackInfo(bgm);
                break;
            case "STOP":
                if(fadeDuration > 0)
                    MedievalLauncher.getInstance().getGameState().getSoundPlayer().fadeOutBGM(bgm, fadeDuration);
                else
                    MedievalLauncher.getInstance().getGameState().getSoundPlayer().stopBGM();
                break;
        }

        block.executeNext(actor);
    }
}
