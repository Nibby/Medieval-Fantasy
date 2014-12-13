package hidden.indev0r.game.entity.npc.script;

import hidden.indev0r.game.entity.Actor;

/**
 * Created by MrDeathJockey on 14/12/13.
 */
public interface CommandBlock {

    public void execute(Actor actor);
    public void executeNext(Actor actor);
    public void executeStep(Actor actor, int step);
    public void executePrev(Actor actor);

    public void storeTemp(String key, Object object);
    public Object getTemp(String key);

    public void finish(Actor actor);
    public boolean isFinished();

}
